/*
 * Created on 23/09/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fwpilot.utils;

import java.util.HashMap;
import java.util.Map;

import com.ness.fw.persistence.SqlService;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.LocalizedResources;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utils
{

	private static Map like = null;

	public static boolean isEmpty(Object obj)
	{
		if (obj == null || obj.toString().equals(""))
		{
			return true;
		}
		
		return false;
	}
	
	public static Map getLikeOptions(LocalizedResources localizable) throws ResourceException
	{
		if (like == null)
		{
			like = new HashMap();
			String like_contains = localizable.getString("like_contains");
			String like_startWith = localizable.getString("like_startWith");
			String like_endWith = localizable.getString("like_endWith");
			like.put(new Integer(SqlService.LIKE_CONTAINS), like_contains);
			like.put(new Integer(SqlService.LIKE_STARTS_WITH), like_startWith);
			like.put(new Integer(SqlService.LIKE_ENDS_WITH), like_endWith);
		}
		return like;
	}
}
