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
			case '�' : return KeyEvent.VK_T;
			case '�' : return KeyEvent.VK_C;
			case '�' : return KeyEvent.VK_D;
			case '�' : return KeyEvent.VK_S;
			case '�' : return KeyEvent.VK_V;
			case '�' : return KeyEvent.VK_U;
			case '�' : return KeyEvent.VK_Z;
			case '�' : return KeyEvent.VK_J;
			case '�' : return KeyEvent.VK_Y;
			case '�' : return KeyEvent.VK_H;
			case '�' : return KeyEvent.VK_F;
			case '�' : return KeyEvent.VK_K;
			case '�' : return KeyEvent.VK_N;
			case '�' : return KeyEvent.VK_B;
			case '�' : return KeyEvent.VK_X;
			case '�' : return KeyEvent.VK_G;
			case '�' : return KeyEvent.VK_P;
			case '�' : return KeyEvent.VK_M;
			case '�' : return KeyEvent.VK_E;
			case '�' : return KeyEvent.VK_R;
			case '�' : return KeyEvent.VK_A;
			case '�' : return KeyEvent.VK_LESS;
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
			case KeyEvent.VK_T : return '�'; 
			case KeyEvent.VK_C : return '�';
			case KeyEvent.VK_D : return '�';
			case KeyEvent.VK_S : return '�';
			case KeyEvent.VK_V : return '�';
			case KeyEvent.VK_U : return '�';
			case KeyEvent.VK_Z : return '�';
			case KeyEvent.VK_J : return '�';
			case KeyEvent.VK_Y : return '�';
			case KeyEvent.VK_H : return '�';
			case KeyEvent.VK_F : return '�';
			case KeyEvent.VK_K : return '�';
			case KeyEvent.VK_N : return '�';
			case KeyEvent.VK_B : return '�';
			case KeyEvent.VK_X : return '�';
			case KeyEvent.VK_G : return '�';
			case KeyEvent.VK_P : return '�';
			case KeyEvent.VK_M : return '�';
			case KeyEvent.VK_E : return '�';
			case KeyEvent.VK_R : return '�';
			case KeyEvent.VK_A : return '�';
			case KeyEvent.VK_LESS : return '�';
			default  : return KeyEvent.VK_DEAD_GRAVE; // :-(
		}
	}
}
