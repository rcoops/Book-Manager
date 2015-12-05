package rcooper.bookmanager.model;

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
			String pubDate, double retailPrice, String subject)
	{
		super(title, author, publisher, pubDate, retailPrice);
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
		setInfoValue(subject);
	}
	
}