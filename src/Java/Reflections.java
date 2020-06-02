

import java.lang.reflect.*;


class Mybean {
		String name;
		int id;

		Mybean()
		{
		}
		Mybean(String name, int id)
		{
			this.name = name;
			this.id = id;
		}

		void setName(String name)
		{
			this.name = name;
		}

		String getName()
		{
			return(name);
		}

		void setId(int id)
		{
			this.id = id;
		}

		int getId()
		{
			return(id);
		}
}

/*

   <Mybean.class>
   	<fields>
		<field name="id" type="int" value="10" />
		<field name="name" type="java.lang.String" value="tulip" />
   	</fields>

*/
class Reflections {



	public static void main(String argv[])
	{
		try {
			Class main = Class.forName("A");
			String s[] = new String[1];
			Class args[] = new Class[1];
			args[0] =  s.getClass();
			A a = (A) main.newInstance();
			Method m = main.getMethod("main",args);
			Object margs[] =  new Object[1];
			margs[0] = s;
			m.invoke(a,margs);

			Class c = Class.forName("Mybean");
			Mybean mb = (Mybean) c.newInstance();
			c = mb.getClass();
			Field f = c.getDeclaredField("id");
			System.out.println("id type: " + f.getType().getName());
			Field f2 = c.getDeclaredField("name");
			System.out.println("name type: " + f2.getType().getName());
			f.setInt(mb,10);
			System.out.println("mb.id = " + mb.getId());

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
	
