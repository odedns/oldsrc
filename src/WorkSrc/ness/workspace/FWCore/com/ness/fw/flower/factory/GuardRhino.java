/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: GuardRhino.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;

public class GuardRhino implements com.ness.fw.flower.core.Guard
{
	private String expression;

	public GuardRhino(String equation) throws GuardException
	{
		this.expression = equation;

		//processExpression();
	}

//	private void processExpression() throws GuardException
//	{
//        char arr[] = expression.toCharArray();
//		StringBuffer sb = new StringBuffer();
//
//		boolean inToken = false;
//		int lastTokenPos = -1;
//		for (int i = 0; i < arr.length; i++)
//		{
//			char c = arr[i];
//			if (c == EXPRESSION_DELIMITER)
//			{
//				if (inToken)
//				{
//					sb.append("ctx.getField(\"");
//					sb.append(arr, lastTokenPos + 1, i - lastTokenPos - 1);
//					sb.append("\")");
//
//					inToken = false;
//					lastTokenPos = i;
//				}
//				else
//				{
//					sb.append(arr, lastTokenPos + 1, i - lastTokenPos - 1);
//					lastTokenPos = i;
//
//					inToken = true;
//				}
//			}
//		}
//
//		if (inToken)
//		{
//			throw new GuardException("Unablr to parse expression [" + expression + "]. Not pare delimiters");
//		}
//
//		sb.append(arr, lastTokenPos + 1, arr.length - lastTokenPos - 1);
//
//		expression = sb.toString();
//	}

	public boolean check(com.ness.fw.flower.core.Context ctx) throws GuardException
	{
		org.mozilla.javascript.Context scriptableContext = RhinoScriptEngineFactory.getScriptableContext();

		org.mozilla.javascript.Scriptable scope = scriptableContext.initStandardObjects(null);
		org.mozilla.javascript.Scriptable obj = org.mozilla.javascript.Context.toObject(ctx, scope);

		scope.put("ctx", scope, obj);

		Object result;
		try
		{
			result = scriptableContext.evaluateString(scope, expression, "<cmd>", 1, null);
		} catch (org.mozilla.javascript.JavaScriptException ex)
		{
			throw new com.ness.fw.flower.core.GuardException("Unable to check guard condition [" + expression + "].", ex);
		}

		return Boolean.valueOf(org.mozilla.javascript.Context.toString(result)).booleanValue();
	}

	public void initialize(ParameterList parameterList) throws GuardException
	{
		throw new RuntimeException("Method not implemented");
	}
}
