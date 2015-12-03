package rcooper.bookmanager.model;

public class AdditionalInfo
{
	String label;
	String value;
	
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

	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}