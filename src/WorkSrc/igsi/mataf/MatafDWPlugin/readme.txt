In order to install MatafDWPlugin:
1) Run the script mataf_oe.sql from the scripts folder on the repository DB
   to create the open editions lock table.
2) Produce a jar called matafdw.jar from the source files under the src folder.
3) Copy the matafdw.jar to the com.ibm.dse.dw.gui directory under wstools/plugins/eclipse dir.
4) Modify the plugin.xml file in the same dir :

 <runtime>
add:	   <library name="matafdw.jar" />
      <library name="dwgui.jar">
         <export name="*"/>
      </library>
      <library name="dwplugininfo.jar"/>
   </runtime>
   

5) Copy the file GroupsTreeMenu.xml from the data folder to the menus directory in the plugin
   directory: com.ibm.dse.dw.gui/menus/GroupsTreeMenu.xml
   
6) In the plugin directory com.ibm.dse.dw.dw.impl change plugin.xml:


   <runtime>
      <library name="dwimpl.jar">
         <export name="*"/>
      </library>
add:      <library name="db2java.zip">
add:         <export name="*"/>
add:      </library>
delete:	<library name="db2java.zip"/>
   </runtime>
   
   
7) Restart WSAD and run the workbench plugin.