/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: GuardIntrospection.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.exceptions.*;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;

import sun.tools.javac.*;
import sun.tools.java.*;

/**
 * Introspective implementation of guard
 */
public class GuardIntrospection implements Guard
{
	/**
	 * Begining of compositive class code
	 */
	private static final String CODE_BEGIN      =
	        "import com.ness.fw.flower.core.*;" +
	        "" +
	        "public class Resolver" +
	        "{" +
	        "   public boolean resolve(Context ctx)  throws ContextException" +
	        "   {" +
	        "       return ";

	/**
	 * End of compositive class code
	 */
	private static final String CODE_END        =
	        ";" +
	        "   }" +
	        "}\n";

	/**
	 * Actual guard object
	 */
	private Object resolver = null;

	/**
	 * The resolve method
	 */
	private Method method = null;

	/**
	 * Expression to be parsed
	 */
	private String expression;

	public GuardIntrospection(String expression) throws GuardException
	{
		long startTime = System.currentTimeMillis();
		this.expression = expression;

		processExpression();

		String code = CODE_BEGIN + this.expression + CODE_END;

		try
		{
			resolver = createResolver(code);
			method = resolver.getClass().getMethod("resolve", new Class[]{Context.class});

			Logger.debug("GUARD", "Introspective guard created in [" + (System.currentTimeMillis() - startTime) + "]ms.");
		} catch (Throwable ex)
		{
			throw new GuardException("Unable to initialize Guard [" + expression + "]", ex);
		}
	}

	/**
	 * Called by framework while chaeking guard condition
	 *
	 * @param ctx Context to check condition on
	 * @return true or false
	 * @throws GuardException
	 */
	public boolean check(Context ctx) throws GuardException
	{
		try
		{
			return ((Boolean)method.invoke(resolver, new Object[]{ctx})).booleanValue();
		}catch (InvocationTargetException ex)
		{
			throw new GuardException("Unable to check guard [" + expression + "]", ex.getTargetException());
		} catch (Throwable ex)
		{
			throw new GuardException("Unable to check guard [" + expression + "]", ex);
		}
	}

	public void initialize(ParameterList parameterList) throws GuardException
	{
		throw new RuntimeException("Method not implemented");
	}

	public String toString()
	{
		return "IntroGuard expr [" + expression + "]";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Expression preprocessing
	 */
	private void processExpression()
	{
		char arr[] = expression.toCharArray();

		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] == '\'')
			{
				arr[i] = '\"';
			}
		}

		expression = String.valueOf(arr);
	}


	/**
	 * Creating resolver object
	 *
	 * @param code combined resolver class code
	 * @return resolver object
	 * @throws Exception
	 */
	private Object createResolver(String code) throws Exception
	{
		Logger.debug("GUARD_INTROSPECTOR", code);

        BatchEnvironment batchEnvironment = new BatchEnvironment(new ClassPath());


		batchEnvironment.parseFile(new MyClassFile(class2code(GeneralException.class).getBytes("UTF-8"), GeneralException.class.getName().substring(GeneralException.class.getName().lastIndexOf(".") + 1)));

		batchEnvironment.parseFile(new MyClassFile(class2code(FlowerException.class).getBytes("UTF-8"), FlowException.class.getName().substring(FlowException.class.getName().lastIndexOf(".") + 1)));

		batchEnvironment.parseFile(new MyClassFile(class2code(Context.class).getBytes("UTF-8"), Context.class.getName().substring(Context.class.getName().lastIndexOf(".") + 1)));

		batchEnvironment.parseFile(new MyClassFile(class2code(ContextException.class).getBytes("UTF-8"), ContextException.class.getName().substring(ContextException.class.getName().lastIndexOf(".") + 1)));

		batchEnvironment.parseFile(new MyClassFile(code.getBytes("UTF-8"), "Resolver"));

		ByteArrayOutputStream os = new ByteArrayOutputStream();
        for(Enumeration enum = batchEnvironment.getClasses(); enum.hasMoreElements();)
		{
			ClassDeclaration classdeclaration = (ClassDeclaration)enum.nextElement();
			if(classdeclaration.getStatus() == 4 && !classdeclaration.getClassDefinition().isLocal())
			{
				SourceClass sourceclass = (SourceClass)classdeclaration.getClassDefinition(batchEnvironment);

				if (sourceclass.getAbsoluteName().equals("Resolver"))
				{
                    sourceclass.compile(os);
				}

				batchEnvironment.flushErrors();
			}
		}

		byte out[] = os.toByteArray();

        MyClassLoader cl = new MyClassLoader(this.getClass().getClassLoader());
        Class clazz = cl._defineClass("Resolver", out, 0, out.length);

		Object o = clazz.newInstance();

		return o;
	}

	/**
	 * Perform primitive (very limited) class decompilation
	 *
	 * @param clazz class to decompile
	 * @return class source code
	 */
	private static String class2code(Class clazz)
	{
		StringBuffer sb = new StringBuffer(3072);
		sb.append("package " + clazz.getPackage().getName() + ";\n");

		//security modifyer
		sb.append("public ");

		if (clazz.isInterface())
		{
			sb.append("interface ");
		}else
		{
			if (Modifier.isAbstract(clazz.getModifiers()))
			{
				sb.append("abstract ");
			}

			sb.append("class ");
		}

		sb.append(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + " ");


		//extends
		Class[] interfaces = clazz.getInterfaces();
		if (interfaces.length > 0)
		{
			if (clazz.isInterface())
			{
				sb.append("extends ");
			}
			else
			{
				sb.append("implements ");
			}

			for (int i = 0; i < interfaces.length; i++)
			{
				Class anInterface = interfaces[i];
				sb.append(anInterface.getName());

				if (i != interfaces.length - 1)
				{
					sb.append(", ");
				}
				else
				{
					sb.append(" ");
				}
			}
		}


		if (!clazz.isInterface())
		{
			Class superClass = clazz.getSuperclass();
			if (superClass != null)
			{
				sb.append("extends " + superClass.getName());
			}
		}


		sb.append("\n{\n");

		//constructors
		Constructor constructors[] = clazz.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++)
		{
			//mod
			Constructor constructor = constructors[i];
			if (Modifier.isPublic(constructor.getModifiers()))
			{
				sb.append("public ");
			}

			//name
			sb.append(constructor.getName().substring(constructor.getName().lastIndexOf(".") + 1) + "(");

			//params
			Class params[] = constructor.getParameterTypes();
			{
				for (int j = 0; j < params.length; j++)
				{
					Class param = params[j];
					sb.append(param.getName() + " p" + j);
					if (j != (params.length - 1))
					{
						sb.append(",");
					}
				}
			}

			sb.append(")\n{\n");

			//body
			Constructor superConstructors[] = clazz.getSuperclass().getConstructors();
			if (superConstructors.length > 0)
			{
				Constructor superConstructor = superConstructors[0];
				sb.append("super(");

				//super params
				Class superConstrTypes[] = superConstructor.getParameterTypes();
				for (int j = 0; j < superConstrTypes.length; j++)
				{
					Class superConstrType = superConstrTypes[j];
					if (superConstrType.isPrimitive())
					{
						sb.append("-1");
					}
					else
					{
						sb.append("null");
					}

					if (j != (superConstrTypes.length - 1))
					{
						sb.append(",");
					}
				}

				sb.append(");\n}\n");

			}
		}

		//methods
		Method methods[] = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method method = methods[i];
			if (Modifier.isAbstract(method.getModifiers()))
			{
				//write method modifier
				if (Modifier.isPublic(method.getModifiers()))
				{
					sb.append("public ");
				}

				sb.append( "abstract ");

				//return type
				sb.append(method.getReturnType().getName() + " ");

				//method name
				sb.append(method.getName() + "(");

				//params
				Class params[] = method.getParameterTypes();
				for (int j = 0; j < params.length; j++)
				{
					Class param = params[j];
					sb.append(param.getName() + " p" + j);
					if (j != (params.length - 1))
					{
						sb.append(",");
					}
				}

				sb.append(");\n");
			}
		}

		sb.append("\n}");
		return sb.toString();
	}

	/**
	 * Overrides class loader
	 */
	private class MyClassLoader extends ClassLoader
	{
		public MyClassLoader(ClassLoader parent)
		{
			super(parent);
		}

        public Class _defineClass(String name, byte[] b, int off, int len)
		{
            return defineClass(name, b, off, len);
		}
	}

	/**
	 * Overrides class file
	 */
	private class MyClassFile extends ClassFile
	{
		private InputStream is;
		private byte arr[];
		private String className;

		public MyClassFile(byte arr[], String className)
		{
			super(null);

			this.arr = arr;
			this.className = className;
			this.is = new ByteArrayInputStream(arr);;
		}

        public boolean isZipped()
		{
			return false;
		}

        public InputStream getInputStream()	throws IOException
		{
			return is;
		}

        public boolean exists()
		{
			return true;
		}

        public boolean isDirectory()
		{
			return false;
		}

		public long lastModified()
		{
			return 0;
		}

		public String getPath()
		{
			return "";
		}

		public String getName()
		{
			return className + ".java";
		}

		public String getAbsoluteName()
		{
			return className;
		}

		public long length()
		{
			return arr.length;
		}

		public String toString()
		{
			return "Pseudo java file";
		}
	}
}
