package mataf.services.chequereader;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.FormatElement;

public class ChequeMateReader extends CheckReader {
	
	private String messageRead;
	private String MessageWrite;
	public SimpleWriter simpleWriter;
	private SimpleReader simpleReader;

    private static final int   READ_BYTES	    	 = 255;
    private static final int   BUF_SIZE			 	= 256;
    private static final int   CARD_STR_LEN     	 =  70;
    private static final int   MIN_CHECK_INFO_LEN	 = 25;
    private static final int   MIN_SHOVAR_LINE_LEN  = 9;

    private static final int    PARSING_ERRORS		  =200;	
    private static final int    ILLEGAL_INFO_LENGTH   =PARSING_ERRORS + 1;
    private static final int    ILLEGAL_FIRST_CHAR   = PARSING_ERRORS + 2;
    private static final int    FIELD_ERROR			  =PARSING_ERRORS + 3;
    private static final int    ILLEGAL_LENGTH        =PARSING_ERRORS + 4;
    private static final int    NOT_DIGIT_CHAR       = PARSING_ERRORS + 5;
	public ChequeMateReader () {
//		System.out.println("ChequeMateReader() ");
//		try{
//			open();
//		} catch (Exception e) {
//			System.out.println("ChequeMateReader Exception");
//			e.printStackTrace();
//		}
		
		simpleWriter = SimpleWriter.getInstance();
		simpleReader = new SimpleReader(simpleWriter.getSerialPort());
		
	}
	public String WriteAndRead(String command) {
		simpleReader = new SimpleReader(simpleWriter.getSerialPort());
		simpleReader.run();
		simpleWriter.write(command);
		messageRead = (String)simpleReader.getReadData();
		simpleReader = null;
		
		return messageRead;
	}
	
//	public void writeToDevice(String command) {
//		System.out.println("writeToDevice(1): ");
//		writeCommand(command);
////		simpleWriter.write(command);
//		System.out.println("writeToDevice(2): ");
//	}
	
//	public void readFromDevice () {
//		
//		try {
//		messageRead = (String)readAndWait();
//		} catch (Exception e) {
//			System.out.println("readFromDevice: Exception!!");
//			e.printStackTrace();
//		}
////		messageRead = (String)simpleReader.getReadData();
//		System.out.println("readFromDevice: "+messageRead);
//	}
//	




	public void readAndUnformat(Context context, FormatElement fe){
		int ret;
		System.out.println("readAndUnformat: start");
		try {
			messageRead = (String)readAndWait();
			ret = parse_check_info(messageRead);
			System.out.println("unformatMessageReceived: ");
			if(ret == 0){
				unformatMessageReceived(context, fe);
				System.out.println("readAndUnformat: unformatMessageReceived");
			}
			else{
				System.out.println("parse_check_info ret: "+ret);
			}
		} catch (Exception e) {
			System.out.println("readAndUnformat: Exception!!");
			e.printStackTrace();
		}
		
	}

	
	private void unformatMessageReceived(Context context, FormatElement formatelement)
					        throws DSEInvalidRequestException, DSEInvalidArgumentException {
    
		//messageRead = "112222222<44444444>666666<";
        System.out.println("messageRead: " + messageRead +" ("+messageRead.length()+")");
        formatelement.unformat(messageRead, context);
    }

	private int parse_check_info(String  check_info)
	{
//		char *pstart, temp[4], check_str[BUF_SIZE];
		byte check_str[] = new byte[BUF_SIZE];
		int pstart,prevStart;
		char temp[] = new char[4];
		int len;
		int disp_flag;
		int cmr_global_error = 0;
		//   :mispar-check<cmr_bank:cmr_snif(kod-bitachon)>cmr_cheshbon
		//  :2659060<12:75600>198399
//		strcpy(check_str,check_info);
		check_str = check_info.getBytes();
		//----------------------------------------------------
		// Check legality of check informaion.
		//----------------------------------------------------
//		len = strlen(check_str);
		len = check_info.length();
		if(len < MIN_CHECK_INFO_LEN)
		{
			return(ILLEGAL_INFO_LENGTH);
		}
		// First character MUST be ':' !!
		if(check_str[0] != ':')
		{
			return(ILLEGAL_FIRST_CHAR);
		}
		//----------------------------------------------------
		// ASMACHTA field.
		//----------------------------------------------------
		
//		pstart = strtok(check_str,"<");
		pstart = check_info.indexOf('<');
//		len = strlen(&pstart[1]);
		len = pstart - 1;
		System.out.println("len1: "+len);
		// ASMACHTA length : 7-10 chars
		if(len < 7 || len > 10)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= ASMACHTA_ERROR;
			return(ILLEGAL_LENGTH);
		} 
		if(check_legal_number(check_str,1,pstart-1) != 0)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= ASMACHTA_ERROR;
//			sm_cmrd1_bufp->cmr_mispar_asmachta = 0;
		}
	   // Long can't contain all the numbers with 10 digits
	   // In such a case we have to omit the first left digit
	   // and leave 9 digits as 'asmachta' number.
//	   if(len == 10) // omit the first digit (keren 20-10-96)
//			sm_cmrd1_bufp->cmr_mispar_asmachta = atol(&pstart[2]);
//	   else
//			sm_cmrd1_bufp->cmr_mispar_asmachta = atol(&pstart[1]);
		//----------------------------------------------------
		// BANK field.
		//----------------------------------------------------
		prevStart = pstart;
		pstart = check_info.indexOf(':',prevStart);
//		pstart = strtok(NULL,":");
//		len = strlen(pstart);
		len = pstart - prevStart - 1;
		System.out.println("len2: "+len);
		if(len != 2)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= BANK_ERROR;
			return(ILLEGAL_LENGTH);
		}
//		if(check_legal_number(pstart))
		if(check_legal_number(check_str,prevStart+1,pstart-1) != 0)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= BANK_ERROR;
//			sm_cmrd1_bufp->cmr_bank=0;
		}
//		sm_cmrd1_bufp->cmr_bank=atoi(pstart);
		//----------------------------------------------------
		// SNIF field.
		//----------------------------------------------------
		prevStart = pstart;
		pstart = check_info.indexOf('>',prevStart);
		len = pstart - prevStart - 1;
		System.out.println("len2: "+len);
//		pstart = strtok(NULL,">");
//		len = strlen(pstart);
		if(len != 5)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= SNIF_ERROR;
			return(ILLEGAL_LENGTH);
		}
//		strncpy(temp,pstart,3);
//		temp[3] = '\0';
		check_info.getChars(pstart+1,pstart+3,temp,0);
		System.out.println("temp: "+new String(temp));
		if(check_legal_number(check_str,pstart+1,pstart+3) != 0)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= SNIF_ERROR;
//			sm_cmrd1_bufp->cmr_snif = 0;
		}
//		sm_cmrd1_bufp->cmr_snif = atoi(temp);
		if(check_legal_number(check_str,pstart+4,pstart+5) != 0)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= KOD_PEULA_ERROR;
//			sm_cmrd1_bufp->cmr_kod_peula_check=0;
		}
//		sm_cmrd1_bufp->cmr_kod_peula_check=atoi(&pstart[3]);
		//----------------------------------------------------
		// CHESHBON field.
		//----------------------------------------------------
		prevStart = pstart;
		pstart = check_info.indexOf('<',prevStart);
		len = pstart - prevStart - 1;
		System.out.println("len3: "+len);
//		pstart = strtok(NULL,"<");
//		len = strlen(pstart);
		if(len < 6 || len > 10)
		{
//			sm_cmrd1_bufp->cmr_global_error = FIELD_ERROR;
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= CHESHBON_ERROR;
			return(ILLEGAL_LENGTH);
		}
		if(check_legal_number(check_str,prevStart+1,pstart-1) != 0)
		{
			cmr_global_error = FIELD_ERROR;
//			sm_cmrd1_bufp->cmr_field_error |= CHESHBON_ERROR;
//			sm_cmrd1_bufp->cmr_cheshbon = 0;
		}
//	   if(len == 10)
//			sm_cmrd1_bufp->cmr_cheshbon = atol(&pstart[1]);
//		else
//			sm_cmrd1_bufp->cmr_cheshbon = atol(pstart);
	
//		WinToWin(sm_cmrd1_win, win);
		if(cmr_global_error != 0){
	   		return(FIELD_ERROR);
		}
		return(0);
	}
	//----------------------------------------------------
	// ?‰˜?…? ?‰……? —˜ ?‰?‰‰— ?†…˜‡??™ ?—ƒ…?	
	//----------------------------------------------------
	int check_legal_number(byte str[],int start,int end)
	{
//		char *p;
//	
//		p = str;
		for(int i = start;i<end;i++){		
			if(!Character.isDigit((char)str[i]))
			{
//				if(str[i] == '?')
//	         		*str = '\0';
				return(-1);
			}
//			else
//	      	p++;
		}
		return(0);
	}
	
//	int check_legal_number(char *str)
//	{
//		char *p;
//	
//		p = str;
//		while(*p)
//			if(!isdigit(*p))
//			{
//				if(*p == '?')
//	         	*str = '\0';
//				return(-1);
//			}
//			else
//	      	p++;
//		return(0);
//	}
	



}

