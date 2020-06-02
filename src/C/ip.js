<!-- ONE STEP TO INSTALL IP ADDRESS:

   1.  Put the code into the BODY of your HTML document  -->

   <!-- STEP ONE: Copy this code into the BODY of your HTML document  -->

   <BODY>

   <SCRIPT LANGUAGE="JavaScript">

   <!-- This script and many more are available online from -->
   <!-- The JavaScript Source!! http://javascriptsource.com -->

   <!-- Begin
   netscapeTest = parseInt(navigator.appVersion)
	explorerTest = navigator.appName.indexOf("Microsoft") + 1
	function netscapeThree() {
		if (navigator.javaEnabled()) {
			userDomain = java.net.InetAddress.getLocalHostName()
				return (userDomain.toString())
		} else {
			return null
				   }
	}
	function netscapeFour() {
		if (navigator.javaEnabled()) {
			baseAddress = java.net.InetAddress.getLocalHost()
				userDomain = baseAddress.getHostName()
				return (userDomain.toString())
		} else {
			return null
				   }
	}
	if ((explorerTest == "0") && (netscapeTest == "3")) {
		domainName = netscapeThree()
	}
	else if ((explorerTest == "0") && (netscapeTest == "4")) {
		domainName = netscapeFour()
	}
	else {
		domainName = "using Internet Explorer?  Then I don\'t know what your IP address is."
	}
	alert('Are you really ' + domainName + ' ?')
	// End -->
	// </SCRIPT>
	//
	// <!-- Script Size:  1.20 KB  -->
	//
