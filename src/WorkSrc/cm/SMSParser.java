
import java.util.*;
import java.io.*;

public class SMSParser {
	static final int INITIAL_STATE = 1;
	static final int REGULAR_STATE = 2;
	static final int T10_STATE = 3;
	static final int PC_STATE = 4;
	static final int PL_STATE = 5;
	static final int BL_STATE = 6;
	static final int HELP_STATE = 7;
	static final int MG_STATE = 8;
	static final int GAME_STATE = 9;
	static final int SHORT_CUT_STATE = 3;

	static final int MG_CNT = 0;

	static final String MG = "mg";
	static final String T10 = "t10";
	static final String PC = "pc";
	static final String PL = "pl";
	static final String BL = "bl";
	static final String LO = "lo";
	static final String HELP = "h";

	int m_state;
	int m_currToken = 0;
	String m_tokens[] = null;


	public SMSParser()
	{
		m_state = INITIAL_STATE;

	}

	public void parse(String cmd)
		throws SMSParserException 
	{
		StringTokenizer st = new StringTokenizer(cmd);
		m_tokens = new String[st.countTokens()];
		int cnt = 0;
		m_currToken = 0;

		while(st.hasMoreTokens()) {
			m_tokens[cnt++] = st.nextToken();
		}

		for(int i=0; i<cnt; ++i) {
			System.out.println("m_token[" + i + "]= " + m_tokens[i]);
		}

		if(!m_tokens[m_currToken].equals(MG)) {
			throw new SMSParserException("no  MG !!");
		}

		if(m_tokens.length < 2) {
			handleHELP();
			return;
		}
		++m_currToken;

		if(m_tokens[m_currToken].equals(MG)) {
			++m_currToken;
			handleMG();
			return;
		}
		if(m_tokens[m_currToken].equals(HELP)) {
			++m_currToken;
			handleHELP();
			return;
		}
		if(m_tokens[m_currToken].equals(PC)) {
			++m_currToken;
			handlePC();
			return;
		}
		if(m_tokens[m_currToken].equals(PL)) {
			++m_currToken;
			handlePL();
			return;
		}
		if(m_tokens[m_currToken].equals(T10)) {
			++m_currToken;
			handleT10();
			return;
		}
		if(m_tokens[m_currToken].equals(BL)) {
			++m_currToken;
			handleBL();
			return;
		}
		/*
		 * if the token cannot be matched transfer control to
		 * the application by the last state.
		 */
		System.out.println(m_tokens[m_currToken] + ": not an application transferring control according to previous state ..");

		switch(m_state) {
			case MG_STATE:
				handleMG();
				break;
			case HELP_STATE:
				handleHELP();
				break;
			case BL_STATE:
				handleBL();
				break;
			case PL_STATE:
				handlePL();
				break;
			case PC_STATE:
				handlePC();
				break;
			case T10_STATE:
				handleT10();
				break;
			default:
				handleHELP();
				break;
		}
	}


	void handlePC()
	{
		m_state = PC_STATE;
		System.out.println("Prize Conversion");
	}

	void handlePL()
	{
		m_state = PL_STATE;
		System.out.println("Prize List");
	}

	void handleT10()
	{
		m_state = T10_STATE;
		System.out.println("TOP Ten");
	}

	void handleMG()
	{
		m_state = MG_STATE;
		System.out.println("Game Arena");
	}

	void handleHELP()
	{
		m_state = HELP_STATE;
		System.out.println("Help");
	}

	void handleBL()
	{
		m_state = BL_STATE;
		System.out.println("Buddy List");
	}

	boolean hasMoreTokens()
	{
		return(m_currToken < m_tokens.length);
	}

	public static void main(String argv[]) 
	{
		String cmd = "";

		try {

			BufferedReader br = new BufferedReader(new 
					InputStreamReader(System.in));
		
			SMSParser parser = new SMSParser();
			System.out.print("SMSParser >> ");
			cmd = br.readLine();
			while(!cmd.equals("quit")) {
				parser.parse(cmd);
				System.out.print("SMSParser >> ");
				cmd = br.readLine();

			}
			System.out.print('\n');
		} catch(Exception ie) {
			ie.printStackTrace();
		}
			

	}


}
