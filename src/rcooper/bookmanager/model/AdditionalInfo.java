package rcooper.bookmanager.model;

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
		this("","");
	}
	
	public AdditionalInfo(String label, String value)
	{
		this.label = label;
		this.value = value;
	}

	public String getLabel()
	{
		return label;
	}

	public String getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return label + " " + value;
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