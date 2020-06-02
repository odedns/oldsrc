package mataf.override;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface AuthorizationConstants
{
	static final int AUTH_OK 					= 0x00;
	static final int AUTH_GERROR				= 0x01;
	static final int AUTH_BADPASS 			= 0x02;
	static final int AUTH_BADPASSATTEMPT1  	= 0x03;
	static final int AUTH_BADPASSATTEMPT2 	= 0x04;
	static final int AUTH_EXPIRED				= 0x05;
	static final int AUTH_LOCKED				= 0x06;
	static final int AUTH_TEMPEXPIRED			= 0x07;
	static final int AUTH_INVALID				= 0x08;
	static final int AUTH_OVEDAHER			= 0x09;
	static final int AUTH_PRIMARYPASS			= 0x0A;
	static final int AUTH_TOBEEXPIRED			= 0x0B;
	static final int AUTH_UNKNOWNERR			= 0x7F;
}
