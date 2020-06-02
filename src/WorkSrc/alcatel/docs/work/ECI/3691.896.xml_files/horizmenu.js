// This code Copyright IONA Technologies 2003
// Cannot be reused without permission
// Contact webmaster@iona.com for permission

// Specify your menu options. Each child menu gets an array of strings
// The format of each string is
//           <text to appear>@<url>

// Implementation code for menu

var AgntUsr=navigator.userAgent.toLowerCase();
var DomYes=document.getElementById?1:0;
var NavYes=AgntUsr.indexOf('mozilla')!=-1&&AgntUsr.indexOf('compatible')==-1?1:0;
var ExpYes=AgntUsr.indexOf('msie')!=-1?1:0;
var Opr=AgntUsr.indexOf('opera')!=-1?1:0;
var DomNav=DomYes&&NavYes?1:0;
var DomExp=DomYes&&ExpYes?1:0;
var Nav4=NavYes&&!DomYes&&document.layers?1:0;
var Exp4=ExpYes&&!DomYes&&document.all?1:0;

var Par=parent.frames[0]&&FirstLineFrame!=SecLineFrame?parent:window;
var Doc=Par.document;
var Bod=Doc.body;
var Trigger=NavYes?Par:Bod;

if (Trigger.onload) originalOnload=Trigger.onload;

Trigger.onload=initNav;

function highlight(optIndex) {
  dontReset();
  var par=document.getElementById? document.getElementById("navbar_tr") : document.all? document.all.navbar_tr : null;
  if (par!=null) {
    for (i=0; i<par.cells.length; i++) {
      if (i==optIndex) {
        par.cells[i].className="navhighlight";
      } else
        par.cells[i].className="navplain";
    }
  }
  var txt='';
  for (i=0; i<options[optIndex].length; i++) {
    if (i>0)
      txt=txt+"| ";
    linktxt=options[optIndex][i];
    linkurl=linktxt.substring(linktxt.indexOf("@")+1);
    linktxt=linktxt.substring(0,linktxt.indexOf("@"));
    txt=txt+'<a href="' + linkurl + '">';
    if (Nav4)
      txt=txt+'<font color="#ffffff">';
    txt=txt+linktxt;
    if (Nav4)
      txt=txt+'</font>';
    txt=txt+'</a>';
  }
  var secondaryBar=document.getElementById? document.getElementById("subnavbar") : document.all? document.all.subnavbar : document.layers? document.dep1.document.dep2 : ""
  if (document.getElementById||document.all) {
    document.getElementById("subnavbar").className="highlight";
    secondaryBar.innerHTML=txt;
  }
  else if (document.layers){
    secondaryBar.document.write('<div style="font-family:Verdana,Arial,Helvetica; text-align:center; font-size:10px; padding:2px; color:#ffffff; width:760;">' + txt + "</div>");
    secondaryBar.document.close();
  }
}

function unhighlight() {
  aboutToReset=setTimeout("resetNav()",1000);
}

function resetNav() {
  var optIndex;
  var par=document.getElementById? document.getElementById("navbar_tr") : document.all? document.all.navbar_tr : null;
  if (par!=null) {
    for (i=0; i<par.cells.length; i++) {
      if (i==defaultOpt)
        par.cells[i].className="navhighlight";
      else
        par.cells[i].className="navplain";
    }
  }
  var txt='';
  if (defaultOpt>=0) {
    for (i=0; i<options[defaultOpt].length; i++) {
      if (i>0)
        txt=txt+"| ";
      linktxt=options[defaultOpt][i];
      linkurl=linktxt.substring(linktxt.indexOf("@")+1);
      linktxt=linktxt.substring(0,linktxt.indexOf("@"));
      txt=txt+'<a ' + (i==defaultSubOpt?'class="selectedsub" ':'') + 'href="' + linkurl + '">';
      if (Nav4)
        txt=txt+'<font color="' + (i==defaultSubOpt?'#cc9900':'#ffffff') + '">';
      txt=txt+linktxt;
      if (Nav4)
        txt=txt+'</font>';
      txt=txt+'</a>';
    }
    var secondaryBar=document.getElementById? document.getElementById("subnavbar") : document.all? document.all.subnavbar : document.layers? document.dep1.document.dep2 : ""
    if (document.getElementById||document.all) {
      document.getElementById("subnavbar").className="highlight";
      secondaryBar.innerHTML=txt;
    }
    else if (document.layers){
      secondaryBar.document.write('<div style="font-family:Verdana,Arial,Helvetica; text-align:center; font-size:10px; padding:2px; color:#ffffff; width:760;">' + txt + "</div>");
      secondaryBar.document.close();
    }
  } else if (document.getElementById||document.all) {
    document.getElementById("subnavbar").innerHTML="&nbsp;"
    document.getElementById("subnavbar").className="";
  }
}

function dontReset() {
  if (window.aboutToReset) {
    clearTimeout(aboutToReset);
  }
}

function originalOnload() {
  return;
}

function initNav() {
  if (document.getElementById && document.getElementById("tertiary")) {
    var tert=document.getElementById("tertiary").childNodes[0];
    var thisNode;
    for (i=0; i<tert.childNodes.length; i++) {
      thisNode=tert.childNodes[i];
      if (thisNode.tagName && thisNode.tagName.toUpperCase()=='A' && currentTert.toUpperCase()==thisNode.innerHTML.toUpperCase()) {
        thisNode.className="highlighted";
      }
    }
  }
  originalOnload();
  resetNav();
}

function swapImg(imgId,newSrc){
  document.images[imgId].src='/images/' + newSrc;
}

// given a name, returns the value from the parameter passed to the page
// if the parameter wasn't passed, returns the empty string

function getParameter(name) {
  var params=unescape(location.search.substring(1)).split('&');
  for (var i=0; i<params.length; i++) {
    if (params[i].indexOf(name+"=")==0)
      return params[i].substring(name.length+1);
  }
  return "";
}

  // Put in a bar for Netscape 4, it is absolutely positioned layer

  if (Nav4) {
    document.writeln('<layer width=765 height=16 top=108 left=10 name="dep1" bgColor="#3a3a3a">');
    document.writeln('<layer name="dep2" height=12 id="dep2">');
    document.writeln('</layer>');
    document.writeln('</layer>');
    document.dep1.onmouseout=unhighlight;
  }

