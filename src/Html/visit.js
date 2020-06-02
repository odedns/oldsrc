!-- FOUR STEPS TO INSTALL VISITOR MONITOR:

   1. Put the first code into the HEAD of your HTML document
   2. Add the onLoad event handler to the BODY tag
   3. Paste the last code into the BODY of your HTML document
   4. Change the 'dummy' e-mail address to your instead.  -->

<!-- STEP ONE: paste this code into the HEAD of your HTML document -->

<HEAD>

<SCRIPT LANGUAGE="JavaScript">

<!-- This script and many more are available online from -->
<!-- The JavaScript Source!! http://javascriptsource.com --> 

<!--
var startTime = new Date();
startTime = startTime.getTime();
var submissions = 0;

function checkForDuplicate() {
 if (document.form1) {
  document.form1.REFERRER.value = document.referrer;
  document.form1.PLATFORM.value = navigator.appName
   + " " + navigator.appVersion;
  submissions++;
  if (submissions > 1)
   return false;
  else
   return true;
 } else {
  return false;
 }
} // goes with function

function doneLoading() {
 var stopTime = new Date();
 stopTime = stopTime.getTime();
 document.form1.LOADING_TIME.value = ((stopTime - startTime) / 1000)
  + " seconds";
 document.form1.PAGE.value = document.title;
 document.form1.SUBMITTER.click(); // triggers submission of form
 // equivalent to form.submit(), but
 // Netscape blocks form.submit() calls to forms with mailto actions
 // this is a workaround for that problem
}

// -->
</script>

</HEAD>

<!-- STEP TWO: Add the onLoad event handler to your BODY tag -->

<BODY onLoad="doneLoading()">

<!-- STEP THREE:  Paste this code into the BODY of your HTML document  -->

<FORM name="form1"
	METHOD=post 
	action="mailto:antispammer@earthling.net?SUBJECT=Devious Visitor Monitor"
	enctype="text/plain"
	onSubmit="return checkForDuplicate()">
<input type="hidden" name="PAGE" value="none">
<input type="hidden" name="REFERRER" value="none">
<input type="hidden" name="PLATFORM" value="none">
<input type="hidden" name="LOADING_TIME" value="none">
<input type="submit"
	name="SUBMITTER"
	value="Click me to let me know you were here">
</form>

<!-- STEP FOUR:  Change the 'dummy' e-mail address to yours.  =-)  -->

<!-- Script Size:  2.16 KB  -->
