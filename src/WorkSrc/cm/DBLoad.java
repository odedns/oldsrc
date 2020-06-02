
import java.io.*;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class DBLoad {

        public DBLoad() {
        }
        public static void main(String[] args) {
                DBLoad DBLoad1 = new DBLoad();

		System.out.println("in DBLoad ....");
		try {
			FileInputStream fis = new FileInputStream("c:/tmp/GAMESMESSAGES1.TXT");
		        InputStreamReader isr = new InputStreamReader(fis, "Unicode");
			BufferedReader br = new BufferedReader(isr);
			/**
			 * The output file
			 */
			FileOutputStream fos = new FileOutputStream("c:/tmp/gamesmessages.sql");
			Writer out = new OutputStreamWriter(fos,"Unicode");

			String line=null;

			String token;
		        char c;
			while(null != (line = br.readLine())) {
				StringBuffer sb = new StringBuffer();
				StringTokenizer st = new StringTokenizer(line,"\t");
				sb.append("insert into games_messages values ( ");
				if(st.hasMoreElements()) {
					token = st.nextToken();

					sb.append(token);
			        }
				while(st.hasMoreElements()) {
					sb.append(',');
					token = st.nextToken();
					c = token.charAt(0);
					if(c >= '0' && c <= '9') {
						sb.append(token);

					} else {
						sb.append("'");
					        sb.append(token);
						sb.append("'");
					}

				}
				sb.append(");\n");

			        System.out.println(sb);
				out.write(sb.toString());
			}
			out.write("commit;\n");
			br.close();
			out.close();


		}
		catch (Exception ex) {
		        ex.printStackTrace();
		}

        }
}