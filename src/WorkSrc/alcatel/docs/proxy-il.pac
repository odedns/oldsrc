function FindProxyForURL(url, host) {
// Alcanet only supports HTTP and HTTPS through proxy
if ( url.substring(0, 5) != "http:" && url.substring(0, 6) != "https:" && url.substring(0, 4) != "ftp:")
        return "DIRECT";
else if (shExpMatch (host, "127.0.0.1"))
	return "DIRECT";
else if (isPlainHostName(host))
	return "DIRECT";
else if (shExpMatch (host, "*.ca.newbridge.com"))
	return "DIRECT";
else if (shExpMatch (host, "192.168.210.*"))
	return "DIRECT";
else if (shExpMatch (host, "138.203.*"))
	return "DIRECT";
else if (shExpMatch (host, "139.54.61.118"))
	return "DIRECT";
else if (shExpMatch (host, "172.24.208.11"))
	return "DIRECT";
else if (shExpMatch (host, "www.ind.alcatel.com"))
	return "DIRECT";
else if (shExpMatch (host, "*.bel.alcatel.be"))
	return "DIRECT";
else if (shExpMatch (host, "aww.alcatel-sbell.com.cn"))
	return "DIRECT";
else if (shExpMatch (host, "www2.alcatel.com"))
        return "DIRECT";
else if (shExpMatch (host, "www4.alcatel.com"))
        return "DIRECT";
else if (shExpMatch (host, "www20.alcatel.com"))
        return "DIRECT";
else if (shExpMatch (host, "www.cid.alcatel.com"))
        return "DIRECT";
else if (shExpMatch (host, "www.cng.alcatel.com"))
        return "DIRECT";
else if (isInNet(host, "10.1.0.0", "255.255.0.0"))
        return "DIRECT";
else if (shExpMatch (host, "www*.*"))
        return "PROXY ilsntfw1.alcatel.co.il:80";
else if (shExpMatch (host, "cid.alcatel.be"))
        return "PROXY ilsntfw1.alcatel.co.il:80";
else if (shExpMatch (host, "world74.alcatel.be"))
        return "PROXY ilsntfw1.alcatel.co.il:80";
else if (shExpMatch (host, "ebuy.businesspartner.alcatel.com"))
        return "PROXY ilsntfw1.alcatel.co.il:80";
else if (shExpMatch (host, "*.alcatel.*"))
	return "DIRECT";
else if (shExpMatch (host, "*.*.alcatel"))
        return "DIRECT";
else if (shExpMatch (host, "*.*.*.alcatel"))
        return "DIRECT";
else
        return "PROXY ilsntfw1.alcatel.co.il:80";
}
