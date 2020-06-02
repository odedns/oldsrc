
package fwpilot.general.vo;

import java.io.Serializable;

import com.ness.fw.bl.ValueObject;

public interface CodeTableVO extends ValueObject, Serializable
{
	public Integer getId();
	public String getName();
}
