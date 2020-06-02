void fnPRN_END_JOB(JNIEnv *env, jobject &obj)
{
    jobject         PRNEvnObj = NULL;
    jclass          tcClz;
    jmethodID       ConstructID;
    jfieldID        nPagesID;

    BOOL            Res;
    DOC_INFO_1      docinfo1;
    JOB_INFO_1      *jobinfo1;
    JOB_INFO_3      jobinfo3;
    DWORD           pcbWritten;
    DWORD           pcbNedded;
    BYTE            jobinfo[1000];
    DWORD           nPages;
    char            *strdest;
//    char            msg[255];

    strcpy (func,"fnPRN_END_JOB"); // 08/08/02

    MyLog ("Starting fnPRN_END_JOB"); // 02/12/01

    Res = OpenPrinter("HP4000_CARD_BANK", &hPrinter,  NULL);

    if(!Res)
    {
        CalldevRply(env, obj, DEVICE_TYPE_PRN, PRN_OPEN_FAILURE, PRNEvnObj);
        delete PrinterBuffer; // 03/07/02
        return;
    }


    CalldevRply(env, obj, DEVICE_TYPE_PRN, PRN_OPEN_OK, PRNEvnObj);

    nPages = 0;

    strdest = strstr((char*)PrinterBuffer, "~&l1F~=~&l65F");

    if(strdest != NULL)
    {
        nPages++;
        strdest++;
        while((strdest = strstr(strdest, "~&l1F~=~&l65F")) != NULL)
        {
            strdest++;
            nPages++;
        }
    }

//START: Alex P. 19/06/01   - replacing text2 occourance with 0x0c
//                            (Form Feed character)
{
 int i = 0;

 char* text1 = PrinterBuffer;
 char* text2 = "~&l1F~=~&l65F";

 char* pos = NULL;

 char*  newText = new char[strlen(text1)];
 char*  tempStr = new char[strlen(text1)];
 int    length, index=0 ;
 memset(newText, NULL, strlen(text1));

 strcpy (tempStr,text1);
 while (pos = strstr(tempStr,text2)) {
   length =(int)(pos- tempStr);
   strncpy(&newText[index],tempStr,length);    // from i to pos index
   index+=length;
   newText[index] = 0x0c;
   index++;
   strcpy(tempStr,pos+strlen(text2));
 }

 strncpy(PrinterBuffer,newText,strlen(newText));
 PrinterBuffer[strlen(newText)]=NULL;

 delete newText;
 delete tempStr;
}
// END: Alex P. 19/06/01

    strdest = NULL;
    while((strdest = strchr((char*)PrinterBuffer, '~')) != NULL)
        *strdest = (char)27;

    while((strdest = strchr((char*)PrinterBuffer, '|')) != NULL)
        *strdest = (char)179;


    docinfo1.pDocName  = "MyDoc";
    docinfo1.pDatatype = NULL;
    docinfo1.pOutputFile = NULL;

    jobId = StartDocPrinter(hPrinter, 1, (LPBYTE)&docinfo1);
    StartPagePrinter(hPrinter);
/*
    // pdf tests - start
    MyLog ("clearing PrinterBuffer");
    PrinterBuffer[0]='.';
    PrinterBuffer[1]=27;
    PrinterBuffer[2]='E';
    PrinterBuffer[3]='\0'; // try to print an empty string, does a page comes out / data recieved !?
    // pdf tests - end
*/
    WritePrinter(hPrinter, PrinterBuffer, strlen(PrinterBuffer), &pcbWritten);

    Res = EndPagePrinter(hPrinter);
    Res = EndDocPrinter(hPrinter);

    DWORD l = 1000;
/*
    { // 07/11/02 - pdf - send the printer status to the log
        DWORD               cByteNeeded,cByteUsed;
        JOB_INFO_2          *pJobStorage = NULL;
        PRINTER_INFO_2      *pPrinterInfo = NULL;

        MyLog ("PDF ------------> calling GetPrinter()");

        // Get the buffer size needed.
        if (!GetPrinter(hPrinter, 2, NULL, 0, &cByteNeeded))
        {
            if (GetLastError() != ERROR_INSUFFICIENT_BUFFER)
                MyLog ("PDF ------------> GetLastError() != ERROR_INSUFFICIENT_BUFFER");
            else
            {
                pPrinterInfo = (PRINTER_INFO_2 *)malloc(cByteNeeded);
                if (!(pPrinterInfo)) {
                    MyLog ("PDF ------------> pPrinterInfo allocation failed");
                } else {
                    // Get the printer information.
                    if (!GetPrinter(hPrinter,2,(unsigned char*)(LPSTR)pPrinterInfo,cByteNeeded,&cByteUsed))
                    {
                        MyLog ("PDF ------------> Failure to access the printer.");
                    } else {
                        sprintf (msg,"PDF ------------> Printer status: %d",pPrinterInfo->Status);
                        MyLog (msg);
                    }
                    free(pPrinterInfo);
                }
            }
        }
    }
*/

    Res = true;

//    unsigned int maxStatus=0; // pdf, 07/11/02

    while(GetJob(hPrinter, jobId, 1,  jobinfo, l, &pcbNedded))
    {

        Sleep(55); // Alex P. 24/01/02
        jobinfo1 = (JOB_INFO_1*)jobinfo;

//        if (jobinfo1->Status>maxStatus) // pdf, 07/11/02
//            maxStatus=jobinfo1->Status; // pdf, 07/11/02

//        sprintf (msg,"PDF ------------> GetJob info, status = %d , meaning: %s",jobinfo1->Status,jobinfo1->pStatus); // pdf, 07/11/02
//        MyLog (msg); // pdf, 07/11/02

        switch(jobinfo1->Status)
        {
        case 18:
        case 48:
        case 80:
//        case 132: // pdf, 07/11/02
            Res = false;
            SetJob(hPrinter, jobId, 3, (LPBYTE)&jobinfo3, JOB_CONTROL_CANCEL);
            CalldevRply(env, obj, DEVICE_TYPE_PRN, PRN_PRINT_ERROR, PRNEvnObj);
            MyLog ("PDF ------------> PRN_PRINT_ERROR"); // pdf, 07/11/02

        default:;
        }
    }

//    if ( (maxStatus>20) // pdf, 07/11/02
//        &&
//        ((maxStatus!=18)&&(maxStatus!=48)&&(maxStatus!=80)/*&&(maxStatus!=132)*/) )
//    {
//        Res = false;
//        CalldevRply(env, obj, DEVICE_TYPE_PRN, PRN_PRINT_ERROR, PRNEvnObj);
//        MyLog ("PDF ------------> PRN_PRINT_ERROR"); // pdf, 07/11/02
//    }
//    else { // just cancel the "." print job
  //      MyLog ("PDF ------------> Printing of point succeeded, canceling job."); // pdf, 07/11/02
//    }
//    SetJob(hPrinter, jobId, 3, (LPBYTE)&jobinfo3, JOB_CONTROL_CANCEL);

//    ClosePrinter(hPrinter);

//    if(!Res) { // if the printing was unsuccessful, delete and return
//      delete PrinterBuffer; // 03/07/02
//        return;
//  }

    // pdf, 07/11/02 - the printing of the "." was OK, now print the pdf document
//    MyLog ("PDF ------------> PRINTING THE PDF DOCUMENT");
//    system ("d:\\acrobat3\\reader\\AcroRd32.exe /p /h c:\\emdatl\\255.pdf");

    //  Create PRNEvent.class
    tcClz = env->FindClass("multicon/device/prn1/PRNEvent"); // 14/02//00

    if(tcClz == 0) {
        delete PrinterBuffer; // 03/07/02
        return;
    }

    ConstructID = env->GetMethodID(tcClz, "<init>", "()V");

    PRNEvnObj = env->NewObject(tcClz, ConstructID, "");

    if(PRNEvnObj == 0) {
        delete PrinterBuffer; // 03/07/02
        return;
    }

    // Set PRNEvent.numPages
    nPagesID = env->GetFieldID(tcClz, "numPages", "I");
    env->SetIntField(PRNEvnObj, nPagesID ,nPages);

    delete PrinterBuffer;

    CalldevRply(env, obj, DEVICE_TYPE_PRN, PRN_PRINT_DONE, PRNEvnObj);

    MyLog ("PDF ------------> PRN_PRINT_DONE"); // pdf tests

    MyLog ("end of fnPRN_END_JOB"); // 02/12/01

/*
// 03/07/02 - start
    PP_RESP     resp;
    char myStr[100];
    resp = Pinpad32Poll(NULL);
    if(resp==PP_PENDING)
        resp=WaitForFunc(TIMEOUT,"PRN_END_JOB-poll");
    sprintf (myStr, "After Pinpad32Poll(NULL) = %d", resp);
    MyLog(myStr);
// 03/07/02 - end
*/
}
