package usermanager;

import javax.naming.directory.*;
import javax.naming.*;

public class UserDetails {
	int    m_uid;
	String m_password;
	String m_givenname;
	String m_username;
	String m_mail;
	String m_cn;
        String m_statusind;
	String m_telephonenumber;
	String m_sex;
	int    m_homenumber;
	String m_street;
	int    m_agegroup;
	int    m_country;
        int    m_state;
	String m_city;
	String m_nickname;
	String m_sn;
	long   m_zip;
	int    m_lang;
        int    m_handset;

	Attributes m_attrs;

	public UserDetails(int uid, String password,
			   String givenname, String username,
			   String mail, String cn,String telephonenumber,
			   String sex, int homenumber,
			   String street, int agegroup, int country,
			   String city, String nickname, String sn,
			   long zip, int lang)
	{
		m_uid=uid;
		m_password=password;
		m_givenname=givenname;
		m_username=username;
		m_mail=mail;
		m_cn=cn;
		m_telephonenumber=telephonenumber;
		m_sex=sex;
		m_homenumber=homenumber;
		m_street=street;
		m_agegroup=agegroup;
		m_country=country;
		m_city=city;
		m_nickname=nickname;
		m_sn=sn;
		m_zip=zip;
		m_lang=lang;

		m_attrs = new BasicAttributes();
		Attribute oc = new BasicAttribute("objectclass");
		oc.add("top");
		oc.add("person");
		oc.add("organizationalPerson");
		oc.add("inetOrgPerson");
		m_attrs.put(oc);
		m_attrs.put("uid",new Integer(m_uid).toString());
		m_attrs.put("password",m_password);
		m_attrs.put("givenname",m_givenname);
		m_attrs.put("username",m_username);
		m_attrs.put("mail",m_mail);
		m_attrs.put("cn",m_cn);
		m_attrs.put("telephonenumber",m_telephonenumber);
		m_attrs.put("sex",m_sex);
		m_attrs.put("homenumber",new Integer(m_homenumber).toString());
		m_attrs.put("street",m_street);
		m_attrs.put("agegroup",new Integer(m_agegroup).toString());
		m_attrs.put("country",new Integer(m_country).toString());
		m_attrs.put("city",m_city);
		m_attrs.put("nickname",m_nickname);
		m_attrs.put("sn",m_sn);
		m_attrs.put("zip",new Long(m_zip).toString());
		m_attrs.put("lang",new Integer(m_lang).toString());
	}


        public Attributes getAttributes()
        {
		return(m_attrs);
        }

        public UserDetails(Attributes attrs) throws NamingException
        {

		m_attrs = attrs;
            Attribute attr = attrs.get("uid");
            if(null != attr) {
              String uidStr = (String) attr.get();
              m_uid = Integer.parseInt(uidStr);
            }
            attr = attrs.get("password");
            if(null != attr) {
              m_password = (String) attr.get();
            }
            attr = attrs.get("givenname");
            if(null != attr) {
              m_givenname = (String) attr.get();
            }
            attr = attrs.get("username");
            if(null != attr) {
              m_username = (String) attr.get();
            }
            if(null != attr) {
              attr = attrs.get("mail");
              m_mail = (String) attr.get();
            }

            attr = attrs.get("cn");
            if(null != attr) {
              m_cn = (String) attr.get();
            }
            attr = attrs.get("telephonenumber");
            if(null != attr) {
              m_telephonenumber = (String) attr.get();
            }
            attr = attrs.get("sex");
            if(null != attr) {
              m_sex = (String) attr.get();
            }
            attr = attrs.get("nickname");
            if(null != attr) {
              m_nickname = (String) attr.get();
            }
            attr = attrs.get("sn");
            if(null != attr) {
              m_sn = (String) attr.get();
            }
            attr = attrs.get("zip");
            if(null != attr) {
              String zipStr = (String) attr.get();
              m_zip = Long.parseLong(zipStr);
            }
            attr = attrs.get("lang");
            if(null != attr) {
              String langStr = (String) attr.get();
              m_lang = Integer.parseInt(langStr);
            }

        }
        public String toString()
        {
          StringBuffer sb = new StringBuffer();
          sb.append("uid = "+ m_uid);
          sb.append(" givenname = "+ m_givenname);
          sb.append(" username = " + m_username);
          sb.append(" mail = "+ m_mail);
          sb.append(" cn = "+ m_cn);
          sb.append(" telephonenumber = "+ m_telephonenumber);
          sb.append(" m_country = "+ m_country);
          return(sb.toString());
        }

}
