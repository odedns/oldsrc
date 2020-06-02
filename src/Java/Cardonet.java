
interface Expression {
	public float eval();
	public void print();
}

class Sum implements Expression {
	Expression m_a1;
	Expression m_a2;

	Sum(Expression a1, Expression a2 )
	{
		m_a1 = a1;
		m_a2 = a2;
	}


	public float eval()
	{
		return(m_a1.eval() + m_a2.eval());
	}

	public void print()
	{
		System.out.print( "(" );
		m_a1.print();
		System.out.print( "+" );
		m_a2.print();
		System.out.print( ")" );
	}

}

class Product implements Expression {
	Expression m_a1;
	Expression m_a2;
	
	Product(Expression a1, Expression a2)
	{
		m_a1 = a1;
		m_a2 = a2;
	}

	public float eval()
	{
		return(m_a1.eval() * m_a2.eval());
	}

	public void print()
	{
		System.out.print("(" );
		m_a1.print();
		System.out.print("*" );
		m_a2.print();
		System.out.print(")");
	}
}


class Literal implements Expression {
	float m_float_value;

	Literal(float a1)
	{
		m_float_value = a1;
	}
	
	public float eval()
	{
		return(m_float_value);
	}

	public void print()
	{
		System.out.print(m_float_value);
	}
}


class Cardonet {
	

	public static void main(String argv[])
	{
		Expression p = new Sum(new Literal(2), 
				new Product(new Literal((float) 1.5),new Literal(3)));

		p.print();
		System.out.println(" = " + p.eval());
	}
}
