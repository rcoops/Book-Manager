package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

public class HistoryBook extends Book
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Period:";
	private final String TYPE = "History";
	
	public HistoryBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}

}