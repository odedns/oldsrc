package mataf.dwplugin;

import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.externalizers.*;
import com.ibm.dse.dw.gui.version.*;
import com.ibm.dse.dw.plugin.DevelopmentWorkbenchPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Cursor;

import java.util.ArrayList;
import java.sql.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VersionUtils {

	/**
	 * Constructor for VersionUtils.
	 */
	private VersionUtils() {
		super();
	}

	/**
	 * get the latest version for the current versionable object.
	 * Only Instance and Group objects are supported.
	 * @param v the Versionable object to get the latest ersion for.
	 * @param rep the Repository to process.
	 * @return String a String reprensenting the latest version.
	 */
	public static String getLatestVersion(Versionable v, Repository rep)
	{
		String verName = null;
		
		if(v instanceof Group) {
			GroupPath path = ((Group) v).getPath();
			Group g = rep.getGroup(path,null);
			verName = g.getVersion();
		} else {
			if(v instanceof Instance) {
				InstancePath path = ((Instance)v).getPath();
				Instance ins = rep.getInstance(path,null);
				verName = ins.getVersion();				
			}	
						
		} // if
		return(verName);		
	}
	
	
	/**
	 * Get the latest version for all instances under
	 * the current group, recursively.
	 * Create open edition for each group processed.
	 * Only instances that do not have open edition
	 * are processed.
	 */
	public static void groupLatestVersion(Group selGroup, XMLExporter myRepInfo,XMLImporter myWksInfo,
		Workspace activeWks, Repository activeRep, Shell treeShell ) throws Exception
	{
		   GroupComposition gc = selGroup.getComposition();
           Instance ins[] = gc.getInstances();
//		   Instance ins[] = getInstancesForGroup(activeRep,selGroup);
           LogFile.getInstance().log("processing group: " + selGroup.getName());
           for(int i=0; i < ins.length; ++i) {
           		/**
           		 * if instance has an open edition, leave it.
           		 * do not retrieve the last version.
           		 */
           	   if(null != OpenEditionManager.hasOpenEdition(ins[i])) {
           	   		LogFile.log("instance with open edition: " + ins[i]);
           	   		continue;
           	   }
		       String verName = VersionUtils.getLatestVersion(ins[i],activeRep);
	    	   LogFile.getInstance().log("replace with version : " + verName);
	           Instance repInstance = activeRep.getInstance(ins[i].getPath(), verName);    
        	   LogFile.log("Got Instance: " + repInstance.getPath());
	           myRepInfo.setModelObjectList(new Instance[] {
                 repInstance
    	       });                	         
        	   myRepInfo.setMode((short)2000);
               myWksInfo.setModelAccessContainer(activeWks);
	           myWksInfo.setAlteringPathEnabled(true);
    	       myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(ins[i].getParentGroup().getPath().toString()));
        	   ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1002, ins[i]);
	           DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
    	      // showMessages(pbo.errorMsg, pbo.warningMsg);    	         	
           } //for
           /**
            * create open edition for the group.
            */
            if(selGroup.isVersioned())
            {
   			    treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                selGroup.createOpenEdition();
   			    if(selGroup.getParent() != null)
	                selGroup.getParentGroupsTree()[0].save();
    		    else
       	            selGroup.save();

            }

           Group g[] = gc.getGroups();           
	   	   for(int i=0; i < g.length; ++i) {
    	      	groupLatestVersion(g[i],myRepInfo,myWksInfo,activeWks,activeRep,treeShell);    	      		
    	   } // for		
	}
		
	/**
	 * get all instances from the repository belonging to 
	 * the specific group.
	 * @param rep the Repository.
	 * @param grp the group to get the instances for.
	 * @return Instance[] and array of instances.
	 */	
	public static Instance[] getInstancesForGroup(Repository rep, Group grp) 
			
	{
		String ver = grp.getVersion();
		String path = grp.getPath().toString();
		Statement st = null;
		ResultSet rs = null;
		Instance ins=null;
		ArrayList ar = new ArrayList();

		try {
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			st = conn.createStatement();
			String sql = "SELECT DISTINCT PATH FROM INSTANCES WHERE PATH LIKE '%"+path + "%'";

			LogFile.getInstance().log("sql = " +sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				path = rs.getString(1);
				LogFile.log("got path = " + path);
				GroupPath gPath = grp.getPath();

				ins = rep.getInstance(new DefaultInstancePath(path),null);
//				System.out.println("got ins = " + ins.getPath() + " version =  " + ins.getVersion());
				ar.add(ins);
			}
	
		} catch(Exception e) {
 			
			e.printStackTrace();		
		}	 finally {
         try {
         	if(null != rs) {
	            rs.close();
         	}
         	if(null != st) {
	            st.close();
         	}
          } catch(SQLException se) {
            se.printStackTrace();
          }
    } //finally
		Instance v[] = new Instance[ar.size()];
		v = (Instance[]) ar.toArray(v);
		return(v);	
	}


	/**
	 * add instance to a group from the repository.
	 */
	public static void addInsFromRep(Group selGroup, XMLExporter myRepInfo,XMLImporter myWksInfo,
		Workspace activeWks, Repository activeRep, Shell treeShell ) throws Exception
	{

         Instance ins[] = getInstancesForGroup(activeRep,selGroup);
         LogFile.getInstance().log("addFromRep processing group: " + selGroup.getName());
         for(int i=0; i < ins.length; ++i) {
	         Instance repInstance = activeRep.getInstance(new DefaultInstancePath(ins[i].getPath().toString()), ins[i].getVersion());
	         LogFile.log("addFromRep : importing from repository: " + repInstance.getName() + 
	         " version = " + repInstance.getVersion());
    	     myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
        	 myRepInfo.setModelObjectList(new Instance[] {
            	    repInstance
         	});
         	myRepInfo.setMode((short)2004);	        
	        myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
     	     myWksInfo.setModelAccessContainer(activeWks);
        	if(selGroup != null)
         	{
   		    	myWksInfo.setAlteringPathEnabled(true);
        	  	myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selGroup.getPath().toString()));
         	}
	         ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
    	     DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
    	     LogFile.log("addFromRep : after importing "  + ins[i].getName());
	         selGroup.validate(true);
    	     selGroup.save();
         } // for

		/**
		 * now loop over all sub groups and process them.
		 */         
         GroupComposition gc = selGroup.getComposition();
         Group grps[] = gc.getGroups();
         for(int i=0; i < grps.length; ++i) {
         	addInsFromRep(grps[i],myRepInfo,myWksInfo,activeWks,activeRep,treeShell);
         }
	}

	
	
}
