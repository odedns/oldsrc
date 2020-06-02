package mataf.srika.panels;

import java.awt.LayoutManager;

import javax.swing.BorderFactory;

import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafLabel;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/02/2004 12:22:16).  
 */
public class SrikaPirteyIska extends MatafEmbeddedPanel
{
	private MatafLabel stationHeaderLabel;
	
	private mataf.types.MatafLabel stationLabel = null;
	private mataf.types.MatafLabel mishmeretLabel = null;
	private mataf.types.MatafLabel hourLabel = null;
	private mataf.types.MatafLabel dateLabel = null;
	private mataf.types.MatafLabel clerkHeaderLabel = null;
	private mataf.types.MatafLabel clerkLabel = null;
	private mataf.types.MatafLabel clerkIDLabel = null;
	private mataf.types.MatafLabel completedCodeLabel = null;
	private mataf.types.MatafLabel handleSrikaCodeLabel = null;
	private mataf.types.MatafLabel cancelSerialLabel = null;
	private mataf.types.MatafLabel screenApprovalLabel = null;
	private mataf.types.MatafLabel managerIDLabel = null;
	private mataf.types.MatafLabel managerIDDescLabel = null;
	private mataf.types.MatafLabel journalSerialHeaderfLabel = null;
	private mataf.types.MatafLabel journalSerialLabel = null;
	private mataf.types.MatafLabel recordHeaderLabel = null;
	private mataf.types.MatafLabel fromRecordLabel = null;
	private mataf.types.MatafLabel totalRecordsLabel = null;
	private mataf.types.MatafLabel separatorLabel = null;
	/**
	 * 
	 */
	public SrikaPirteyIska()
	{
		super();
		initialize();
	
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getStationHeaderLabel(), null);
        this.add(getStationLabel(), null);
        this.add(getMishmeretLabel(), null);
        this.add(getHourLabel(), null);
        this.add(getDateLabel(), null);
        this.add(getClerkHeaderLabel(), null);
        this.add(getClerkLabel(), null);
        this.add(getClerkIDLabel(), null);
        this.add(getCompletedCodeLabel(), null);
        this.add(getHandleSrikaCodeLabel(), null);
        this.add(getCancelSerialLabel(), null);
        this.add(getScreenApprovalLabel(), null);
        this.add(getManagerIDLabel(), null);
        this.add(getManagerIDDescLabel(), null);
        this.add(getJournalSerialHeaderfLabel(), null);
        this.add(getJournalSerialLabel(), null);
        this.add(getRecordHeaderLabel(), null);
        this.add(getFromRecordLabel(), null);
        this.add(getTotalRecordsLabel(), null);
        this.add(getSeparatorLabel(), null);
        this.setBounds(0, 0, 780, 90);
        this.setBorder(BorderFactory.createTitledBorder("פרטי העסקה"));
			
	}
	/**
	 * This method initializes stationHeaderLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getStationHeaderLabel() {
		if(stationHeaderLabel == null) {
			stationHeaderLabel = new mataf.types.MatafLabel();
			stationHeaderLabel.setBounds(729, 18, 43, 20);
			stationHeaderLabel.setText("תחנה :");
		}
		return stationHeaderLabel;
	}
	/**
	 * This method initializes stationLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getStationLabel() {
		if(stationLabel == null) {
			stationLabel = new mataf.types.MatafLabel();
			stationLabel.setBounds(701, 18, 26, 20);
			stationLabel.setText("XXX");
			stationLabel.setDataName("GL_MISPAR_TAHANA");
		}
		return stationLabel;
	}
	/**
	 * This method initializes mishmeretLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMishmeretLabel() {
		if(mishmeretLabel == null) {
			mishmeretLabel = new mataf.types.MatafLabel();
			mishmeretLabel.setBounds(636, 18, 50, 20);
			mishmeretLabel.setText("XXXXXX");
			mishmeretLabel.setDataName("GL_MISHMERET_MSACH");
		}
		return mishmeretLabel;
	}
	/**
	 * This method initializes hourLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getHourLabel() {
		if(hourLabel == null) {
			hourLabel = new mataf.types.MatafLabel();
			hourLabel.setBounds(590, 18, 40, 20);
			hourLabel.setText("XX:XX");
			hourLabel.setDataName("GL_SHAA_MSACH");
		}
		return hourLabel;
	}
	/**
	 * This method initializes dateLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getDateLabel() {
		if(dateLabel == null) {
			dateLabel = new mataf.types.MatafLabel();
			dateLabel.setBounds(528, 18, 56, 20);
			dateLabel.setText("XX/XX/XX");
			dateLabel.setDataName("GL_TR_MARECHET");
		}
		return dateLabel;
	}
	/**
	 * This method initializes clerkHeaderLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getClerkHeaderLabel() {
		if(clerkHeaderLabel == null) {
			clerkHeaderLabel = new mataf.types.MatafLabel();
			clerkHeaderLabel.setBounds(729, 43, 43, 20);
			clerkHeaderLabel.setText("פקיד :");
		}
		return clerkHeaderLabel;
	}
	/**
	 * This method initializes clerkLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getClerkLabel() {
		if(clerkLabel == null) {
			clerkLabel = new mataf.types.MatafLabel();
			clerkLabel.setBounds(678, 43, 49, 20);
			clerkLabel.setText("XXXXXX");
			clerkLabel.setDataName("GL_ZIHUI_PAKID_MSACH");
		}
		return clerkLabel;
	}
	/**
	 * This method initializes clerkIDLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getClerkIDLabel() {
		if(clerkIDLabel == null) {
			clerkIDLabel = new mataf.types.MatafLabel();
			clerkIDLabel.setBounds(605, 43, 65, 20);
			clerkIDLabel.setText("XXXXXXXXX");
			clerkIDLabel.setDataName("GL_ZIHUI_PAKID_MSACH_DESC");
		}
		return clerkIDLabel;
	}
	/**
	 * This method initializes completedCodeLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getCompletedCodeLabel() {
		if(completedCodeLabel == null) {
			completedCodeLabel = new mataf.types.MatafLabel();
			completedCodeLabel.setBounds(696, 68, 76, 20);
			completedCodeLabel.setText("XXXXXXXXXX");
			completedCodeLabel.setDataName("GL_KOD_HUSHLEMA");
		}
		return completedCodeLabel;
	}
	/**
	 * This method initializes handleSrikaCodeLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getHandleSrikaCodeLabel() {
		if(handleSrikaCodeLabel == null) {
			handleSrikaCodeLabel = new mataf.types.MatafLabel();
			handleSrikaCodeLabel.setBounds(594, 68, 76, 20);
			handleSrikaCodeLabel.setText("XXXXXXXXXX");
			handleSrikaCodeLabel.setDataName("GL_KOD_TIPUL_SRIKA");
		}
		return handleSrikaCodeLabel;
	}
	/**
	 * This method initializes cancelSerialLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getCancelSerialLabel() {
		if(cancelSerialLabel == null) {
			cancelSerialLabel = new mataf.types.MatafLabel();
			cancelSerialLabel.setBounds(552, 68, 32, 20);
			cancelSerialLabel.setText("XXXX");
			cancelSerialLabel.setDataName("GL_SIDURI_BITUL");
		}
		return cancelSerialLabel;
	}
	/**
	 * This method initializes screenApprovalLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getScreenApprovalLabel() {
		if(screenApprovalLabel == null) {
			screenApprovalLabel = new mataf.types.MatafLabel();
			screenApprovalLabel.setBounds(322, 18, 76, 20);
			screenApprovalLabel.setText("XXXXXXXXXX");
			screenApprovalLabel.setDataName("GL_ISHUR_MSACH");
		}
		return screenApprovalLabel;
	}
	/**
	 * This method initializes managerIDLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerIDLabel() {
		if(managerIDLabel == null) {
			managerIDLabel = new mataf.types.MatafLabel();
			managerIDLabel.setBounds(253, 18, 43, 20);
			managerIDLabel.setText("XXXXXX");
			managerIDLabel.setDataName("GL_ZIHUI_MENAEL_MSCH");
		}
		return managerIDLabel;
	}
	/**
	 * This method initializes managerIDDescLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getManagerIDDescLabel() {
		if(managerIDDescLabel == null) {
			managerIDDescLabel = new mataf.types.MatafLabel();
			managerIDDescLabel.setBounds(139, 18, 71, 20);
			managerIDDescLabel.setText("XXXXXXXXX");
			managerIDDescLabel.setDataName("GL_ZIHUI_MENAEL_MSCH_DESC");
		}
		return managerIDDescLabel;
	}
	/**
	 * This method initializes journalSerialHeaderfLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getJournalSerialHeaderfLabel() {
		if(journalSerialHeaderfLabel == null) {
			journalSerialHeaderfLabel = new mataf.types.MatafLabel();
			journalSerialHeaderfLabel.setBounds(354, 43, 44, 20);
			journalSerialHeaderfLabel.setText("ס.יומן :");
		}
		return journalSerialHeaderfLabel;
	}
	/**
	 * This method initializes journalSerialLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getJournalSerialLabel() {
		if(journalSerialLabel == null) {
			journalSerialLabel = new mataf.types.MatafLabel();
			journalSerialLabel.setBounds(322, 43, 32, 20);
			journalSerialLabel.setText("XXXX");
			journalSerialLabel.setDataName("GL_SIDURI_YOMAN");
		}
		return journalSerialLabel;
	}
	/**
	 * This method initializes recordHeaderLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getRecordHeaderLabel() {
		if(recordHeaderLabel == null) {
			recordHeaderLabel = new mataf.types.MatafLabel();
			recordHeaderLabel.setBounds(205, 43, 47, 20);
			recordHeaderLabel.setText("רשומה :");
		}
		return recordHeaderLabel;
	}
	/**
	 * This method initializes fromRecordLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getFromRecordLabel() {
		if(fromRecordLabel == null) {
			fromRecordLabel = new mataf.types.MatafLabel();
			fromRecordLabel.setBounds(144, 43, 17, 20);
			fromRecordLabel.setText("XX");
			fromRecordLabel.setDataName("GL_MISPAR_RES_MTOCH");
		}
		return fromRecordLabel;
	}
	/**
	 * This method initializes totalRecordsLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getTotalRecordsLabel() {
		if(totalRecordsLabel == null) {
			totalRecordsLabel = new mataf.types.MatafLabel();
			totalRecordsLabel.setBounds(171, 43, 15, 20);
			totalRecordsLabel.setText("XX");
			totalRecordsLabel.setDataName("GL_MISPAR_RES_TOT");
		}
		return totalRecordsLabel;
	}
	/**
	 * This method initializes separatorLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getSeparatorLabel() {
		if(separatorLabel == null) {
			separatorLabel = new mataf.types.MatafLabel();
			separatorLabel.setBounds(161, 43, 8, 20);
			separatorLabel.setText("/");
		}
		return separatorLabel;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
