package rcooper.bookmanager.model;

public class FictionalBook extends Book
{
	private final String INFO_LABEL = "Genre:";
	
	public FictionalBook()
	{
		super();
	}
	
	public FictionalBook(String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String genre)
	{
		super(title, author, publisher, pubDate, retailPrice, type);
		this.info = new AdditionalInfo(INFO_LABEL, genre);
	}

}