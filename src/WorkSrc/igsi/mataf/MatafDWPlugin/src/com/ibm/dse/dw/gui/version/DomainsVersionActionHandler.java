// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DomainsVersionActionHandler.java

package com.ibm.dse.dw.gui.version;

import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.gui.editors.DWTextEditor;
import com.ibm.dse.dw.gui.mb.ActionHandler;
import com.ibm.dse.dw.gui.repository.RepositoryExplorer;
import com.ibm.dse.dw.gui.views.domains.DomainsTreePart;
import com.ibm.dse.dw.gui.views.domains.DomainsTreeViewer;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.externalizers.*;
import com.ibm.dse.dw.plugin.DevelopmentWorkbenchPlugin;
import java.util.Vector;

import mataf.dwplugin.OpenEditionManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

// Referenced classes of package com.ibm.dse.dw.gui.version:
//            RequestVersionNameAndPoliciesDialog, ProgressBarOperation, AddObjectDialog, RequestVersionNameFromListDialog

public class DomainsVersionActionHandler
    implements ActionHandler
{

    public DomainsVersionActionHandler()
    {
        activeRep = null;
        activeWks = null;
        selection = null;
        cm = null;
        cm = DevelopmentWorkbenchPlugin.getDefault().getConnectionManager();
    }

    public void arm()
    {
    }

    public boolean isEnabled(String action)
    {
        boolean versionedDomain = false;
        if(isSingleDomain())
            versionedDomain = ((Domain)selection[0]).isVersioned();
        if(action != null && cm != null && cm.getActiveRepositoryConnection() != null && cm.getActiveRepositoryConnection().isConnected())
        {
            activeRep = cm.getActiveRepositoryConnection().getRepository();
            if(action.equals("VERSION_CONTENTS"))
                return !versionedDomain;
            if(action.equals("REPLACE_DOMAIN_VERSION"))
                return true;
            if(action.equals("ADD_DOMAIN_FROM_REPOSITORY"))
                return true;
            if(action.equals("CREATE_OPEN_EDITION_DOMAIN"))
                return versionedDomain;
            else
                return true;
        }
        if(action != null && action.equals("VERSION_CONTENTS"))
            return false;
        if(action.equals("REPLACE_DOMAIN_VERSION"))
            return false;
        if(action.equals("ADD_DOMAIN_FROM_REPOSITORY"))
            return false;
        if(action.equals("CREATE_OPEN_EDITION_DOMAIN"))
            return versionedDomain;
        else
            return true;
    }

    protected boolean isSingleDomain()
    {
        return selection != null && selection.length == 1 && (selection[0] instanceof Domain);
    }

    protected boolean isSingleDomainElement()
    {
        return selection != null && selection.length == 1 && (selection[0] instanceof DomainElement);
    }

    public void launch(String action)
    {
        boolean refreshView = true;
        boolean refreshRepositoryExplorer = false;
        org.eclipse.swt.widgets.Shell treeShell = DevelopmentWorkbenchPlugin.getDefault().getDomainsTreePart().getDomainsTreeViewer().getTree().getShell();
        if("VERSION_CONTENTS".equals(action))
        {
            if(isSingleDomain())
            {
                Domain selDomain = (Domain)selection[0];
                RequestVersionNameAndPoliciesDialog rvnapd = new RequestVersionNameAndPoliciesDialog(DevelopmentWorkbenchPlugin.getDefault().getDomainsTreePart().getDomainsTreeViewer().getTree().getShell());
                rvnapd.setVersionableObject(selDomain);
                rvnapd.setDefaultNextVersionName(activeRep.getVersionManager().getProposedNextVersionName(selDomain));                
                if(0 == rvnapd.open())
                {
                    try
                    {
                        IVersioner versioner = DevelopmentWorkbenchPlugin.getDefault().getVersioner();
                        versioner.setRepository(activeRep);
                        versioner.setModelObjectList(new ModelObject[] {
                            selDomain
                        });
                        if(rvnapd.isAutoVersion())
                            versioner.setVersionName(activeRep.getVersionManager().getProposedNextVersionName(selDomain));
                        else
                            versioner.setVersionName(rvnapd.getVersionName());
                        ProgressBarOperation pbo = new ProgressBarOperation(versioner, 1000);
                        DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                        showMessages(pbo.errorMsg, pbo.warningMsg);
//                        MessageDialog.openInformation(treeShell,"", "Close Open edition Domain");
                        OpenEditionManager.closeOpenEdition(selDomain);
                    }
                    catch(Exception e)
                    {
                        DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                    }
                    refreshView = true;
                    refreshRepositoryExplorer = true;
                }
            }
        } else
        if("ADD_DOMAIN_FROM_REPOSITORY".equals(action))
        {
            AddObjectDialog dialog = new AddObjectDialog(DevelopmentWorkbenchPlugin.getDefault().getDomainsTreePart().getDomainsTreeViewer().getTree().getShell(), null, 3003);
            if(dialog.getItemsCount() > 0)
                if(0 == dialog.open())
                {
                    try
                    {
                        String domainName = dialog.getObjectName();
                        String domainVersion = dialog.getObjectVersion();
                        Domain repDomain = activeRep.getDomain(domainName, domainVersion);
                        XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRuntimeFilesExporter();
                        myRepInfo.setModelObjectList(new Domain[] {
                            repDomain
                        });
                        myRepInfo.setMode((short)2004);
                        XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getRuntimeFilesImporter();
                        myWksInfo.setModelAccessContainer(cm.getActiveWorkspaceConnection().getWorkspace());
                        ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1001);
                        DevelopmentWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().run(true, false, pbo);
                        showMessages(pbo.errorMsg, pbo.warningMsg);
                    }
                    catch(Exception e)
                    {
                        DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
                    }
                    refreshView = true;
                } else
                {
                    MessageDialog.openInformation(treeShell, MultiLanguage.ADDING_OBJECT_mDTitle, MultiLanguage.ADDING_OBJECT_mDMssg);
                }
        } else
        if("CREATE_OPEN_EDITION_DOMAIN".equals(action))
        {
            try
            {
                Domain selDomain = (Domain)selection[0];
                if(selDomain.isVersioned())
                {
                	String owner =OpenEditionManager.hasOpenEditionOwner(selDomain);
		            if(null != owner) {
		            	String s = "Instance: " + selDomain.getName() + " has openEdition by " + owner;	            	
	    	        	MessageDialog.openInformation(treeShell, "Open Edition", s);	
	        	    	return;           
	            } else {
                    selDomain.createOpenEdition();
                    selDomain.save();
//                    MessageDialog.openInformation(treeShell,"", "Create Open edition Domain");
                    // Oded 09-04-2003
                    OpenEditionManager.createOpenEdition(selDomain);
                    // Oded 09-04-2003
	            } //else
              }
            }
            catch(Exception e)
            {
                DevelopmentWorkbenchPlugin.getDefault().log(4, 0, e.getMessage(), e);
            }
            refreshView = true;
        } else
        if("REPLACE_DOMAIN_VERSION".equals(action) && isSingleDomain())
        {
            Domain selDomain = (Domain)selection[0];
            RequestVersionNameFromListDialog rvnfld = new RequestVersionNameFromListDialog(DevelopmentWorkbenchPlugin.getDefault().getDomainsTreePart().getDomainsTreeViewer().getTree().getShell());
            String availableVersionEditions[] = getAvailableVersionNames(selDomain);
            rvnfld.setAvailableVersionNames(availableVersionEditions);
            if(availableVersionEditions != null && availableVersionEditions.length > 0)
            {
                if(rvnfld.open() == 0 && rvnfld.getSelectedVersionName() != null)
                {
                    try
                    {
                        activeWks = cm.getActiveWorkspaceConnection().getWorkspace();
                        Domain repDomain = activeRep.getDomain(selDomain.getName(), rvnfld.getSelectedVersionName());
                        XMLExporter myRepInfo = DevelopmentWorkbenchPlugin.getDefault().getRepositoryExporter();
                        myRepInfo.setModelObjectList(new Domain[] {
                            repDomain
                        });
                        myRepInfo.setMode((short)2004);
                        XMLImporter myWksInfo = DevelopmentWorkbenchPlugin.getDefault().getWorkspaceImporter();
                        myWksInfo.setModelAccessContainer(activeWks);
                        ProgressBarOperation pbo = new ProgressBarOperation(myRepInfo, myWksInfo, 1002, selDomain);
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
        if(refreshView)
        {
            treeShell.setCursor(null);
            DevelopmentWorkbenchPlugin.getDefault().getDomainsTreePart().getDomainsTreeViewer().refresh();
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
    public static final String ACTION_VERSION_DOMAIN = "VERSION_CONTENTS";
    public static final String ACTION_REPLACE_DOMAIN_VERSION = "REPLACE_DOMAIN_VERSION";
    public static final String ACTION_ADD_DOMAIN = "ADD_DOMAIN_FROM_REPOSITORY";
    public static final String ACTION_CREATE_OPEN_EDITION = "CREATE_OPEN_EDITION_DOMAIN";
    protected Repository activeRep;
    protected Workspace activeWks;
    protected Object selection[];
    protected ConnectionManager cm;
}
