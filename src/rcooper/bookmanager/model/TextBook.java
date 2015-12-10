package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

public class TextBook extends Book
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Subject:";
	private final String TYPE = "Textbook";
	
	public TextBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}
	
}