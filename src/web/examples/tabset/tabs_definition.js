/*
 +-------------------------------------------------------------------------------------+
 |                                                                                     |
 | DHTML Tabsets                                                                       |
 |                                                                                     |
 | Copyright and Legal Notices:                                                        |
 |                                                                                     |
 |   All source code, images, programs, files included in this distribution            |
 |   Copyright (c) 1996,1997,1998,1999,2000                                            |
 |                                                                                     |
 |          John C. Cokos  iWeb, Inc.                                                  |
 |          All Rights Reserved.                                                       |
 |                                                                                     |
 |                                                                                     |
 |   Web: http://www.iwebsoftware.com      Email: info@iwebsoftware.com                |
 |                                                                                     |
 +-------------------------------------------------------------------------------------+

    ** 
       Original Tabset Scripts were obtained from another source.  Cannot remember
       where I got them from.  I've manipulated the daylights out of it, to make it
       work in all browsers, and behave the way that I wanted it to.   If you are,
       or if you know the originater, please email me at the address noted above, and
       I will be happy to change the copyright notices herein to include you as
       the original source.
    **

*/


/*  
    Change the variables below as desired.
*/


   var rows = new Array;
   var num_rows = 2;
   var top = 270;
   var left = 25;
   var width = 585;

   var tab_off = "#e0e0e0";
   var tab_on = "silver";

   // Do NOT delete or change this line !!
   for ( var x = 1; x <= num_rows; x++ ) { rows[x] = new Array; }
 

   /*  
      Define as many ROWS as you like here.
      Note that for each row, you must have a corresponding "DIV"
      in your HTML, matching the row id. 

      ie  row[1][5] would need a div with and id of "15"

      Samples are embedded within the definitions below.
   */


   rows[1][1] = "Tab-1";     // Requires: <div id="T11" class="tab-body">  ... </div>
   rows[1][2] = "Tab-2";     // Requires: <div id="T12" class="tab-body">  ... </div>
   rows[1][3] = "Tab-3";     // Requires: <div id="T13" class="tab-body">  ... </div>
   rows[1][4] = "Tab-4";     // Requires: <div id="T14" class="tab-body">  ... </div>

   rows[2][1] = "Tab-5";     // Requires: <div id="T21" class="tab-body">  ... </div>
   rows[2][2] = "Tab-6";     // Requires: <div id="T22" class="tab-body">  ... </div>
   rows[2][3] = "Tab-7";     // Requires: <div id="T23" class="tab-body">  ... </div>
   rows[2][4] = "Tab-8";    // Requires: <div id="T24" class="tab-body">  ... </div>
   rows[2][5] = "Tab-9";    // Requires: <div id="T24" class="tab-body">  ... </div>