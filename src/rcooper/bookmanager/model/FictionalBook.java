package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

public class FictionalBook extends Book
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Genre:";
	private final String TYPE = "Fictional";
	
	public FictionalBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}

}