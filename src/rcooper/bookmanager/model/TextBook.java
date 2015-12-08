package rcooper.bookmanager.model;

import java.util.Date;

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
	
	public TextBook(String title, String author, String publisher,
			Date pubDate, int price, String subject)
	{
		super(title, author, publisher, pubDate, price);
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
		setInfoValue(subject);
	}
	
}