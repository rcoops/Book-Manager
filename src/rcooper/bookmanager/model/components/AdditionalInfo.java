package rcooper.bookmanager.model.components;

import java.io.Serializable;

public class AdditionalInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private String value;
	
	public AdditionalInfo()
	{
		label = "";
		value = "";
	}

	public String getLabel()
	{
		return label;
	}

	public String getValue()
	{
		return value;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}