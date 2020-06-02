// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GroupsVersionActionHandler.java

package com.ibm.dse.dw.gui.version;

import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.gui.editors.DWTextEditor;
import com.ibm.dse.dw.gui.mb.ActionHandler;
import com.ibm.dse.dw.gui.repository.RepositoryExplorer;
import com.ibm.dse.dw.gui.views.groups.*;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.externalizers.*;
import com.ibm.dse.dw.plugin.DevelopmentWorkbenchPlugin;
import java.util.Vector;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import mataf.dwplugin.*;

// Referenced classes of package com.ibm.dse.dw.gui.version:
//            RequestVersionNameAndPoliciesDialog, ProgressBarOperation, RequestVersionNameFromListDialog, AddObjectDialog

public class GroupsVersionActionHandler
    implements ActionHandler
{

    public GroupsVersionActionHandler()
    {
        selection = null;
        activeRep = null;
        activeWks = null;
        cm = null;
    }

    public void arm()
    {
    }

    private void deleteFromChilds(Group group)
    {
        Entity subEntities[] = group.getComposition().getEntities();
        for(int index = 0; index < subEntities.length; index++)
            deleteFromChilds(subEntities[index]);

        Instance subInstances[] = group.getComposition().getInstances();
        for(int index = 0; index < subInstances.length; index++)
            deleteFromChilds(subInstances[index]);

        Group subGroups[] = group.getComposition().getGroups();
        for(int index = 0; index < subGroups.length; index++)
            deleteFromChilds(subGroups[index]);

        group.delete();
        group.save();
    }

    private void deleteFromChilds(Entity entity)
    {
        entity.delete();
        entity.save();
    }

    private void deleteFromChilds(Instance instance)
    {
        Instance subInstances[] = instance.getComposition().getInstances();
        for(int index = 0; index < subInstances.length; index++)
            deleteFromChilds(subInstances[index]);

        instance.delete();
        instance.save();
    }

    public boolean isEnabled(String action)
    {
        cm = DevelopmentWorkbenchPlugin.getDefault().getConnectionManager();
        boolean versionedObject = selection != null && selection.length == 1 && ((Versionable)selection[0]).isVersioned();
        if(action != null && cm != null && cm.getActiveRepositoryConnection() != null && cm.getActiveRepositoryConnection().isConnected())
        {
            activeRep = cm.getActiveRepositoryConnection().getRepository();
            if(action.equals("VERSION_CONTENTS"))
                return !versionedObject;
            if(action.equals("CREATE_OPEN_EDITION_GROUP") || action.equals("CREATE_OPEN_EDITION_INSTANCE") || action.equals("CREATE_OPEN_EDITION_ENTITY"))
                return versionedObject;
            if(action.equals("REPLACE_WITH_VERSION") || action.equals(ACTION_REPLACE_WITH_VERSION))
            {
                Versionable parent = null;
                if(selection[0] instanceof Group)
                    parent = ((Group)selection[0]).getParent();
                else
                if(selection[0] instanceof Instance)
                    parent = (Versionable)((Instance)selection[0]).getParent();
                else
                if(selection[0] instanceof Entity)
                    parent = ((Entity)selection[0]).getGroup();
                return parent == null || parent != null && !parent.isVersioned();
            }
            if(action.equals("ADD_INSTANCE_TO_GROUP") || action.equals("ADD_ENTITY_TO_GROUP") || action.equals("ADD_GROUP_TO_GROUP"))
                return true;
            if(action.equals("ADD_GROUP_AS_ROOT"))
                return true;
            return !action.equals("ADD_INSTANCE_TO_INSTANCE") ? true : true;
        }
        if(action.equals("VERSION_CONTENTS"))
            return false;
        if(action.equals("REPLACE_WITH_VERSION"))
            return false;
        if(action.equals("ADD_INSTANCE_TO_GROUP") || action.equals("ADD_ENTITY_TO_GROUP") || action.equals("ADD_GROUP_TO_GROUP"))
            return false;
        if(action.equals("ADD_INSTANCE_TO_INSTANCE"))
            return false;
        if(action.equals("CREATE_OPEN_EDITION_GROUP") || action.equals("CREATE_OPEN_EDITION_INSTANCE") || action.equals("CREATE_OPEN_EDITION_ENTITY"))
            return versionedObject;
        return !action.equals("ADD_GROUP_AS_ROOT");
    }

    protected boolean isSingleGroup()
    {
        return selection != null && selection.length == 1 && (selection[0] instanceof Group);
    }

    public void launch(String action)
    {
        boolean refreshView = false;
        boolean refreshRepositoryExplorer = false;
        Shell treeShell = DevelopmentWorkbenchPlugin.getDefault().getGroupsTreePart().getGroupsTreeViewer().getTree().getShell();

		// Oded 13/4/2003
		if(ACTION_ADD_INSTANCE_FROM_REP.equals(action)) {		
            MessageDialog.openInformation(treeShell, "Add from rep", "Add instance from rep");	                               					
            try {
	            treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
    	        Workspace activeWks = cm.getActiveWorkspaceConnection().getWorkspace();
        	    XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
    			XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
	            Group selGroup = (Group)selection[0];	            
    	        VersionUtils.addInsFromRep(selGroup, myRepInfo,myWksInfo,activeWks,activeRep,treeShell );
   			    treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                selGroup.validate(true);
                selGroup.save();    
            } catch(Exception e) {
                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                LogFile.getInstance().log("e = " + e);
            }
            treeShell.setCursor(null);
            DevelopmentWorkbenchPlugin.getDefault().getGroupsTreePart().getGroupsTreeViewer().refresh();
			return;	
		}
		
		// add support for replace group with latest version.        
        if(ACTION_REPLACE_WITH_LATEST_VERSION.equals(action)) {
             if(selection != null && selection.length == 1)
            {               
                 try
                     {
                        treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                         Workspace activeWks = cm.getActiveWorkspaceConnection().getWorkspace();
                         XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
 						 XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                          
                         Group selGroup = (Group)selection[0];
                         VersionUtils.groupLatestVersion(selGroup,myRepInfo,myWksInfo,activeWks,activeRep,treeShell);
                        
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
//                			refreshView = true;
			            }

                       }
                       catch(Exception e)
                       {
                            DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                            LogFile.getInstance().log("e = " + e);
                        }
                } else   { // selection
                    MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.REPLACE_WITH_OBJECT_mDMssg);
                }
            treeShell.setCursor(null);
            DevelopmentWorkbenchPlugin.getDefault().getGroupsTreePart().getGroupsTreeViewer().refresh();
           	return;	
        }
// end changes Oded
        
        if("VERSION_CONTENTS".equals(action))
        {        	
            if(selection != null)
            {
                for(int i = 0; i < selection.length; i++)
                    if(selection[i] instanceof Versionable)
                    {
                    	// Oded 9/4/2003
//    		            MessageDialog.openInformation(treeShell, "Open Edition", "Version for Instance:");	                               		
                       	// Oded 9/4/2003
                    	
                        Versionable versionableObject = (Versionable)selection[i];
                        RequestVersionNameAndPoliciesDialog rvnapd = new RequestVersionNameAndPoliciesDialog(treeShell);
                        rvnapd.setVersionableObject(versionableObject);
                        rvnapd.setDefaultNextVersionName(activeRep.getVersionManager().getProposedNextVersionName(versionableObject));
                        if(0 == rvnapd.open())
                        {
                            IVersioner versioner = DevelopmentWorkbenchPlugin.getDefault().getVersioner();
                            versioner.setRepository(activeRep);
                            versioner.setModelObjectList(new ModelObject[] {
                                (ModelObject)selection[i]
                            });
                            versioner.setIsSameForChildren(rvnapd.isSameForChildren());
                            if(rvnapd.isAutoVersion())
                                versioner.setVersionName(activeRep.getVersionManager().getProposedNextVersionName(versionableObject));
                            else
                                versioner.setVersionName(rvnapd.getVersionName());
                            try
                            {
                                ProgressBarOperation pbo = new ProgressBarOperation(versioner, 1000);
                                DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                                showMessages(pbo.errorMsg, pbo.warningMsg);
                                // Oded 9/4/2003
                                if(selection[i] instanceof Instance) {						
						            OpenEditionManager.closeOpenEdition((Instance)selection[i]);
                		    	} else {
                		    		if(selection[i] instanceof Group) {							
     									LogFile.getInstance().log("close open edition for all children of group: " + ((Group)selection[i]).getName());           		    			
										OpenEditionManager.closeOpenEdition((Group)selection[i]);
                		    		}
                                }
                		    	// Oded 9/4/2003

                            }
                            catch(Exception e)
                            {
                                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                            }
                            refreshView = true;
                            refreshRepositoryExplorer = true;
                        }
                    }

            }
        } else
        if("REPLACE_WITH_VERSION".equals(action))
        {
            if(selection != null && selection.length == 1)
            {
                Versionable selObject = (Versionable)selection[0];
                RequestVersionNameFromListDialog rvnfld = new RequestVersionNameFromListDialog(treeShell);
                String availableVersions[] = getAvailableVersionNames(selObject);
                if(availableVersions != null && availableVersions.length > 0)
                {
                    rvnfld.setAvailableVersionNames(availableVersions);
                    if(0 == rvnfld.open() && rvnfld.getSelectedVersionName() != null)
                    {
                        treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                        Workspace activeWks = cm.getActiveWorkspaceConnection().getWorkspace();
                        if(selObject instanceof Group)
                            try
                            {
                                Group selGroup = (Group)selObject;
                                Group repGroup = activeRep.getGroup(selGroup.getPath(), rvnfld.getSelectedVersionName());
                                XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                                myRepInfo.setModelObjectList(new Group[] {
                                    repGroup
                                });
                                myRepInfo.setMode((short)2000);
                                XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                                myWksInfo.setModelAccessContainer(activeWks);
                                ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1002, selGroup);
                                DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                                showMessages(pbo.errorMsg, pbo.warningMsg);
                            }
                            catch(Exception e)
                            {
                                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                            }
                        else
                        if(selObject instanceof Entity)
                            try
                            {
                                Entity selEntity = (Entity)selObject;
                                Entity repEntity = activeRep.getEntity(selEntity.getName(), rvnfld.getSelectedVersionName());
                                XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                                myRepInfo.setModelObjectList(new Entity[] {
                                    repEntity
                                });
                                myRepInfo.setMode((short)2000);
                                XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                                myWksInfo.setModelAccessContainer(activeWks);
                                myWksInfo.setAlteringPathEnabled(true);
                                myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selEntity.getGroup().getPath().toString()));
                                ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1002, selEntity);
                                DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                                showMessages(pbo.errorMsg, pbo.warningMsg);
                            }
                            catch(Exception e)
                            {
                                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                            }
                        else
                        if(selObject instanceof Instance)
                            try
                            {
                                Instance selInstance = (Instance)selObject;
                                Instance repInstance = activeRep.getInstance(selInstance.getPath(), rvnfld.getSelectedVersionName());
                                XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                                myRepInfo.setModelObjectList(new Instance[] {
                                    repInstance
                                });
                                myRepInfo.setMode((short)2000);
                                XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                                myWksInfo.setModelAccessContainer(activeWks);
                                myWksInfo.setAlteringPathEnabled(true);
                                myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selInstance.getParentGroup().getPath().toString()));
                                ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1002, selInstance);
                                DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                                showMessages(pbo.errorMsg, pbo.warningMsg);
                            }
                            catch(Exception e)
                            {
                                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                            }
                        refreshView = true;
                    }
                } else
                {
                    MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.REPLACE_WITH_OBJECT_mDMssg);
                }
            }
        } else
        if("CREATE_OPEN_EDITION_GROUP".equals(action))
        {        
            Group selGroup = (Group)selection[0];
            if(selGroup.isVersioned())
            {
                treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                selGroup.createOpenEdition();
                if(selGroup.getParent() != null)
                    selGroup.getParentGroupsTree()[0].save();
                else
                    selGroup.save();
                refreshView = true;
            }
        } else
        if("CREATE_OPEN_EDITION_ENTITY".equals(action))
        {
            Entity selEntity = (Entity)selection[0];
            if(selEntity.isVersioned())
            {
                treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                selEntity.createOpenEdition();
                if(selEntity.getGroup() != null)
                    if(selEntity.getGroup().getParent() != null)
                        selEntity.getGroup().getParentGroupsTree()[0].save();
                    else
                        selEntity.getGroup().save();
                refreshView = true;
            }
        } else
        if("CREATE_OPEN_EDITION_INSTANCE".equals(action))
        {
        	
            Instance selInstance = (Instance)selection[0];
            if(selInstance.isVersioned())
            {
            	// added Oded
            	
            	String s = "CREATE OPEN EDITION for : " + selInstance.getPath().toString();
//	            MessageDialog.openInformation(treeShell, "Open Edition", s);	           
	            
	            String owner =OpenEditionManager.hasOpenEditionOwner(selInstance);
	            if(null != owner) {
	            	s = "Instance: " + selInstance.getName() + " has openEdition by " + owner;	            	
	            	MessageDialog.openInformation(treeShell, "Open Edition", s);	
	            	return;           
	            } else {
		            OpenEditionManager.createOpenEdition(selInstance) ;
	            }
	             // end addtion
                treeShell.setCursor(new Cursor(treeShell.getDisplay(), 1));
                selInstance.createOpenEdition();
                if(selInstance.getContainerGroup() != null)
                    if(selInstance.getContainerGroup().getParentGroupsTree() != null)
                        selInstance.getContainerGroup().getParentGroupsTree()[0].save();
                    else
                        selInstance.getContainerGroup().save();
                refreshView = true;
            }
        } else
        if("ADD_GROUP_TO_GROUP".equals(action) || "ADD_GROUP_AS_ROOT".equals(action))
        {
            Group selGroup = null;
            if(selection != null && selection.length == 1)
                selGroup = (Group)selection[0];
            if(selGroup != null || "ADD_GROUP_AS_ROOT".equals(action))
            {
                AddObjectDialog dialog = new AddObjectDialog(treeShell, selGroup, 3000);
                if(dialog.getItemsCount() > 0)
                {
                    if(0 == dialog.open())
                    {
                        try
                        {
                            if(GroupsActionHandler.ensureIsOpenEdition(selGroup))
                            {
                                String groupPath = dialog.getObjectName();
                                String groupVersion = dialog.getObjectVersion();
                                Group repGroup = activeRep.getGroup(new DefaultGroupPath(groupPath), groupVersion);
                                XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                                myRepInfo.setModelObjectList(new Group[] {
                                    repGroup
                                });
                                myRepInfo.setMode((short)2004);
                                XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                                myWksInfo.setModelAccessContainer(cm.getActiveWorkspaceConnection().getWorkspace());
                                myWksInfo.setAlteringPathEnabled(true);
                                if(selGroup != null)
                                    myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selGroup.getPath().toString()));
                                ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
                                DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                                showMessages(pbo.errorMsg, pbo.warningMsg);
                            }
                        }
                        catch(Exception e)
                        {
                            DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                        }
                        refreshView = true;
                        selGroup.validate(true);
                        selGroup.save();
                    }
                } else
                {
                    MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.ADDING_OBJECT_mDMssg);
                }
            }
        } else
        if("ADD_INSTANCE_TO_GROUP".equals(action))
        {
            Group selGroup = (Group)selection[0];
            AddObjectDialog dialog = new AddObjectDialog(treeShell, selGroup, 3001);
            if(dialog.getItemsCount() > 0)
            {
                if(0 == dialog.open())
                {
                    try
                    {
                        if(GroupsActionHandler.ensureIsOpenEdition(selGroup))
                        {
                            String instancePath = dialog.getObjectName();
                            String instanceVersion = dialog.getObjectVersion();
                            Instance repInstance = activeRep.getInstance(new DefaultInstancePath(instancePath), instanceVersion);
                            XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                            myRepInfo.setModelObjectList(new Instance[] {
                                repInstance
                            });
                            myRepInfo.setMode((short)2004);
                            XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                            myWksInfo.setModelAccessContainer(cm.getActiveWorkspaceConnection().getWorkspace());
                            if(selGroup != null)
                            {
                                myWksInfo.setAlteringPathEnabled(true);
                                myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selGroup.getPath().toString()));
                            }
                            ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
                            DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                            showMessages(pbo.errorMsg, pbo.warningMsg);
                        }
                    }
                    catch(Exception e)
                    {
                        DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                    }
                    refreshView = true;
                    selGroup.validate(true);
                    selGroup.save();
                }
            } else
            {
                MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.ADDING_OBJECT_mDMssg);
            }
        } else
        if("ADD_ENTITY_TO_GROUP".equals(action))
        {
            Group selGroup = (Group)selection[0];
            AddObjectDialog dialog = new AddObjectDialog(DevelopmentWorkbenchPlugin.getDefault().getGroupsTreePart().getGroupsTreeViewer().getTree().getShell(), selGroup, 3002);
            if(dialog.getItemsCount() > 0)
            {
                if(0 == dialog.open())
                {
                    try
                    {
                        if(GroupsActionHandler.ensureIsOpenEdition(selGroup))
                        {
                            String entityName = dialog.getObjectName();
                            String entityVersion = dialog.getObjectVersion();
                            Entity repEntity = activeRep.getEntity(entityName, entityVersion);
                            XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                            myRepInfo.setModelObjectList(new Entity[] {
                                repEntity
                            });
                            myRepInfo.setMode((short)2000);
                            XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                            myWksInfo.setModelAccessContainer(cm.getActiveWorkspaceConnection().getWorkspace());
                            if(selGroup != null)
                            {
                                myWksInfo.setAlteringPathEnabled(true);
                                myWksInfo.setAlternativeGroupPath(new DefaultGroupPath(selGroup.getPath().toString()));
                            }
                            ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
                            DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                            showMessages(pbo.errorMsg, pbo.warningMsg);
                        }
                    }
                    catch(Exception e)
                    {
                        DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                    }
                    selGroup.validate(true);
                    selGroup.save();
                    refreshView = true;
                }
            } else
            {
                MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.ADDING_OBJECT_mDMssg);
            }
        } else
        if("ADD_INSTANCE_TO_INSTANCE".equals(action))
        {
            Instance selInstance = (Instance)selection[0];
            AddObjectDialog dialog = new AddObjectDialog(treeShell, selInstance, 3001);
            if(dialog.getItemsCount() > 0)
            {
                if(0 == dialog.open())
                {
                    try
                    {
                        if(GroupsActionHandler.ensureIsOpenEdition(selInstance))
                        {
                            String instancePath = dialog.getObjectName();
                            String instanceVersion = dialog.getObjectVersion();
                            Instance repInstance = activeRep.getInstance(new DefaultInstancePath(instancePath), instanceVersion);
                            XMLExporter myRepInfo = new XMLRepositoryExporter(new Instance[] {
                                repInstance
                            }, (short)2004);
                            XMLWorkspaceImporter myWksInfo = new XMLWorkspaceImporter(cm.getActiveWorkspaceConnection().getWorkspace());
                            if(selInstance != null)
                            {
                                myWksInfo.setAlteringInstancePathEnabled(true);
                                myWksInfo.setAlternativeInstancePath(new DefaultInstancePath(selInstance.getPath().toString()));
                            }
                            ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
                            DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                            showMessages(pbo.errorMsg, pbo.warningMsg);
                        }
                    }
                    catch(Exception e)
                    {
                        DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                    }
                    refreshView = true;
                    selInstance.validate(true);
                    selInstance.save();
                }
            } else
            {
                MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.ADDING_OBJECT_mDMssg);
            }
        }
        if(refreshView)
        {
            treeShell.setCursor(null);
            DevelopmentWorkbenchPlugin.getDefault().getGroupsTreePart().getGroupsTreeViewer().refresh();
        }
        if(refreshRepositoryExplorer && DevelopmentWorkbenchPlugin.getDefault().getRepositoryExplorer() != null)
            DevelopmentWorkbenchPlugin.getDefault().getRepositoryExplorer().refresh();
    }

    public void setSelection(Object selection[])
    {
        this.selection = selection;
    }

    public void showMessages(Vector errorMsg, Vector warningMsg)
    {
        if(errorMsg != null)
        {
            String errorMessages = new String();
            String mDTitle = "Process Information";
            String mDMessg = "Some Errors occurred processing your request, Do you wish to check the message console?";
            if(MessageDialog.openQuestion(DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), mDTitle, mDMessg))
            {
                for(int m = 0; m < errorMsg.size(); m++)
                    errorMessages = errorMessages.concat((String)errorMsg.get(m));

                DWTextEditor dwte = new DWTextEditor("Console");
                dwte.setContent(errorMessages);
                dwte.open();
            }
        } else
        if(warningMsg != null)
        {
            String warningMessages = new String();
            String mDTitle = "Process Information";
            String mDMessg = "Some Warnings occurred processing your request, Do you wish to check the message console?";
            if(MessageDialog.openQuestion(DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), mDTitle, mDMessg))
            {
                for(int m = 0; m < warningMsg.size(); m++)
                    warningMessages = warningMessages.concat((String)warningMsg.get(m));

                DWTextEditor dwte = new DWTextEditor("Console");
                dwte.setContent(warningMessages);
                dwte.open();
            }
        }
    }

    private String[] getAvailableVersionNames(Versionable selObject)
    {
        if(selObject != null)
        {
            String vNames[] = activeRep.getVersionManager().getAllVersionNames(selObject);
            if(vNames != null && selObject.isVersioned())
            {
                String actualVName = selObject.getVersion();
                int existsOnList = -1;
                for(int idx = 0; idx < vNames.length; idx++)
                    if(vNames[idx].equals(actualVName))
                        existsOnList = idx;

                if(existsOnList == -1)
                    return vNames;
                String vNamesResult[] = new String[vNames.length - 1];
                for(int idx = 0; idx < existsOnList; idx++)
                    vNamesResult[idx] = vNames[idx];

                for(int idx = existsOnList + 1; idx < vNames.length; idx++)
                    vNamesResult[idx - 1] = vNames[idx];

                return vNamesResult;
            } else
            {
                return vNames;
            }
        } else
        {
            return null;
        }
    }

    private static final String COPYRIGHT = "Licensed Materials - Property of IBM Restricted Materials of IBM 5648-D89 (C) Copyright IBM Corp. 2002, 2003 All Rights Reserved. US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp ";
    public static final String ACTION_VERSION_CONTENTS = "VERSION_CONTENTS";
    public static final String ACTION_REPLACE_WITH_VERSION = "REPLACE_WITH_VERSION";
    public static final String ACTION_CREATE_OPEN_EDITION_GROUP = "CREATE_OPEN_EDITION_GROUP";
    public static final String ACTION_CREATE_OPEN_EDITION_INSTANCE = "CREATE_OPEN_EDITION_INSTANCE";
    public static final String ACTION_CREATE_OPEN_EDITION_ENTITY = "CREATE_OPEN_EDITION_ENTITY";
    public static final String ACTION_ADD_GROUP_TO_GROUP = "ADD_GROUP_TO_GROUP";
    public static final String ACTION_ADD_GROUP_AS_ROOT = "ADD_GROUP_AS_ROOT";
    public static final String ACTION_ADD_INSTANCE_TO_GROUP = "ADD_INSTANCE_TO_GROUP";
    public static final String ACTION_ADD_ENTITY_TO_GROUP = "ADD_ENTITY_TO_GROUP";
    public static final String ACTION_ADD_INSTANCE_TO_INSTANCE = "ADD_INSTANCE_TO_INSTANCE";
    // Oded
    public static final String ACTION_REPLACE_WITH_LATEST_VERSION = "REPLACE_WITH_LATEST_VERSION";
    public static final String ACTION_ADD_INSTANCE_FROM_REP = "ADD_INSTANCE_FROM_REP";
    protected Object selection[];
    protected Repository activeRep;
    protected Workspace activeWks;
    protected ConnectionManager cm;
}
