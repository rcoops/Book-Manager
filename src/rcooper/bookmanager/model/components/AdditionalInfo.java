package rcooper.bookmanager.model.components;

import java.io.Serializable;

/**
 * Stores an additional piece of information, and a description of the type to  
 * be displayed when used by a concrete realisation of a <code>Book</code> 
 * object.
 * 
 * @version 0.1
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class AdditionalInfo implements Serializable
{

	private static final long serialVersionUID = -1280072677273346755L;
	private String label;
	private String value;
	
	/**
	 * Creates a new AdditionalInfo object with empty values.
	 */
	public AdditionalInfo()
	{
		label = "";
		value = "";
	}

	/**
	 * Gets the category that the item falls into.
	 * 
	 * @return The category type to be displayed in a label.
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Gets the value of the additional information.
	 * 
	 * @return The additional information.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the value of the category that the information falls into.
	 * 
	 * @param label The category to be assigned to the info.
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Sets the value of the additional information.
	 * 
	 * @param value The value of the information.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

}