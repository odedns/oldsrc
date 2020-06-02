
import java.io.*;

public class CharFreq {

public static void main (String[] args) {
InputStream file = null;
int frequencies[] = new int[256];
int ch;

    if (args.length == 0) {
        System.out.println("Usage: java CharFreq <file>");
        System.exit(10);
    }
    try {
        file = new FileInputStream(args[0]) );
    } catch (FileNotFoundException unknownFile) {
        System.out.println("'" + args[0] + "' could not be found.");
        System.exit(100);
    }
    try {
        while ( (ch = file.read()) != -1 ) {
            frequencies[ ch ]++;
        }
    } catch (IOException error) {
        System.out.println("An error occurred during reading: " + error);
        System.exit(100);
    }
    try {
        file.close();
    } catch (IOException ignored) {}
}
} // End of Class CharFreq



