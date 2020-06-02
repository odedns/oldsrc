import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.util.jar.*;

public class ClassVersionLimiter {
   public static void main(String[] args) {
      ClassVersionLimiter classVersionLimiter = new ClassVersionLimiter("C:/j2sdk1.4.0", 46, 0);
   }

   private long _maxAllowedVersion;

   public ClassVersionLimiter(String rootDirectory, int mainVersion, int minorVersion) {
      _maxAllowedVersion = mainVersion * 65536 + minorVersion;
      File root = new File(rootDirectory);
      search(root);
   }

   private void patchJar(File file) {
      System.out.print("Patching " + file);

      try {
         final int MAX_ENTRY_SIZE = 1024 * 1024;
         byte[]   classBytes  = new byte[MAX_ENTRY_SIZE];
         File     newFile     = new File(file.getAbsolutePath() + ".NEW");

         CRC32             crc32          = new CRC32();
         JarFile           oldJarFile     = new JarFile(file);
         Manifest          manifest       = oldJarFile.getManifest();
         FileOutputStream  outputStream   = new FileOutputStream(newFile);
         JarOutputStream   jarOutput      = new JarOutputStream(outputStream, manifest);
         Enumeration       entries        = oldJarFile.entries();
         while(entries.hasMoreElements()) {
            JarEntry oldEntry = (JarEntry) entries.nextElement();
            JarEntry newEntry = (JarEntry) oldEntry.clone();
            System.out.print(".");
            if(oldEntry.getName().equals("META-INF/MANIFEST.MF")) continue;
            InputStream    in             = oldJarFile.getInputStream(oldEntry);

            int            totalLen       = 0;
            int            readLen        = 0;
            do {
               totalLen += readLen;
               readLen = in.read(classBytes, totalLen, MAX_ENTRY_SIZE - totalLen);
            } while(readLen > 0);

            if(totalLen == MAX_ENTRY_SIZE) {
               System.err.println("patchJAR: MAX_ENTRY_SIZE too small."); return;
            }
            if(totalLen > 0 && oldEntry.getName().endsWith(".class")) {
               int         mainVersion    = classBytes[6] * 256 + classBytes[7];
               int         minorVersion   = classBytes[4] * 256 + classBytes[5];
               long        version        = mainVersion * 65536 + minorVersion;
               if(version > _maxAllowedVersion) {
                  mainVersion    = (int) _maxAllowedVersion / 65536;
                  minorVersion   = (int) _maxAllowedVersion % 65536;
                  classBytes[4]  = (byte) (minorVersion / 256);
                  classBytes[5]  = (byte) (minorVersion % 256);
                  classBytes[6]  = (byte) (mainVersion / 256);
                  classBytes[7]  = (byte) (mainVersion % 256);

                  crc32.reset();
                  crc32.update(classBytes, 0, totalLen);
                  newEntry.setCrc(crc32.getValue());
                  newEntry.setCompressedSize(-1); // The compressed size can differ if the uncompressed data is changed. -1 means unknown.
               }
            }
            jarOutput.putNextEntry(newEntry);
            if(totalLen > 0) jarOutput.write(classBytes, 0, totalLen);
            jarOutput.closeEntry();
         }
         jarOutput.close();
         outputStream.close();
         oldJarFile.close();

         System.out.println("");

         File bakFile = new File(file.getAbsolutePath() + ".BAK");
         if(!file.renameTo(bakFile)) {
            System.err.println("Cannot rename " + file + " to " + bakFile + ". EXITING.");
            System.exit(99);
         }
         if(!newFile.renameTo(file)) {
            System.err.println("Cannot rename " + newFile + " to " + file + ". EXITING.");
            System.exit(99);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private void checkJar(File file) {
      byte[]   header         = new byte[8];
      long     maxVersion     = 0;
      boolean  foundClassFiles = false;
      try {
         JarFile jarFile = new JarFile(file);
         Enumeration entries = jarFile.entries();
         while(entries.hasMoreElements()) {
            JarEntry entry = (JarEntry) entries.nextElement();
            if(entry.getName().endsWith(".class")) {
               foundClassFiles = true;
               InputStream stream = jarFile.getInputStream(entry);
               stream.read(header);
               int mainVersion = header[6] * 256 + header[7];
               int minorVersion = header[4] * 256 + header[5];
               long version = mainVersion * 65536 + minorVersion;
               if(version > maxVersion) maxVersion = version;
            }
         }
         jarFile.close();
         if(foundClassFiles && maxVersion > _maxAllowedVersion) {
            patchJar(file);
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private void search(File dir) {
      dir.listFiles(new FileFilter() {
         public boolean accept(File pathname) {
            if(pathname.getName().endsWith(".jar")) checkJar(pathname);
            if(pathname.isDirectory()) search(pathname);
            return false;
         }
      });
   }
}
