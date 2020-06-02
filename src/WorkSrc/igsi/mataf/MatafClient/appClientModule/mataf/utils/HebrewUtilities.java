package mataf.utils;

import java.awt.event.KeyEvent;

/**
 *
 * Class contains some helper methods for supporting hebrew.
 * 
 * @author Nati Dykstein. Creation Date : (19/08/2003 15:45:43).  
 */
public class HebrewUtilities 
{
	/**
	 * Method maps hebrew characters to thier corresponding VK_XXX constants.
	 */
	public static int mapHebrewKeysToVKConstants(int hebKey)
	{
		switch (hebKey)
		{
			case 'à' : return KeyEvent.VK_T;
			case 'á' : return KeyEvent.VK_C;
			case 'â' : return KeyEvent.VK_D;
			case 'ã' : return KeyEvent.VK_S;
			case 'ä' : return KeyEvent.VK_V;
			case 'å' : return KeyEvent.VK_U;
			case 'æ' : return KeyEvent.VK_Z;
			case 'ç' : return KeyEvent.VK_J;
			case 'è' : return KeyEvent.VK_Y;
			case 'é' : return KeyEvent.VK_H;
			case 'ë' : return KeyEvent.VK_F;
			case 'ì' : return KeyEvent.VK_K;
			case 'î' : return KeyEvent.VK_N;
			case 'ð' : return KeyEvent.VK_B;
			case 'ñ' : return KeyEvent.VK_X;
			case 'ò' : return KeyEvent.VK_G;
			case 'ô' : return KeyEvent.VK_P;
			case 'ö' : return KeyEvent.VK_M;
			case '÷' : return KeyEvent.VK_E;
			case 'ø' : return KeyEvent.VK_R;
			case 'ù' : return KeyEvent.VK_A;
			case 'ú' : return KeyEvent.VK_LESS;
			default  : return KeyEvent.VK_DEAD_GRAVE; // :-(
		}
	}
	
	/**
	 * Method maps US-keys characters to thier corresponding hebrew characters.
	 */
	public static int mapUSKeysToHebrewKeys(int USKey)
	{
		switch (USKey)
		{
			case KeyEvent.VK_T : return 'à'; 
			case KeyEvent.VK_C : return 'á';
			case KeyEvent.VK_D : return 'â';
			case KeyEvent.VK_S : return 'ã';
			case KeyEvent.VK_V : return 'ä';
			case KeyEvent.VK_U : return 'å';
			case KeyEvent.VK_Z : return 'æ';
			case KeyEvent.VK_J : return 'ç';
			case KeyEvent.VK_Y : return 'è';
			case KeyEvent.VK_H : return 'é';
			case KeyEvent.VK_F : return 'ë';
			case KeyEvent.VK_K : return 'ì';
			case KeyEvent.VK_N : return 'î';
			case KeyEvent.VK_B : return 'ð';
			case KeyEvent.VK_X : return 'ñ';
			case KeyEvent.VK_G : return 'ò';
			case KeyEvent.VK_P : return 'ô';
			case KeyEvent.VK_M : return 'ö';
			case KeyEvent.VK_E : return '÷';
			case KeyEvent.VK_R : return 'ø';
			case KeyEvent.VK_A : return 'ù';
			case KeyEvent.VK_LESS : return 'ú';
			default  : return KeyEvent.VK_DEAD_GRAVE; // :-(
		}
	}
}
