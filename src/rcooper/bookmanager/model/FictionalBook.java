package rcooper.bookmanager.model;

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
	
	public FictionalBook(String title, String author, String publisher,
			String pubDate, double price, String genre)
	{
		super(title, author, publisher, pubDate, price);
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
		setInfoValue(genre);
	}

}