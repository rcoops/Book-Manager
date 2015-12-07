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
	
	public HistoryBook(String title, String author, String publisher,
			GregorianCalendar pubDate, double price, String period)
	{
		super(title, author, publisher, pubDate, price);
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
		setInfoValue(period);
	}

}