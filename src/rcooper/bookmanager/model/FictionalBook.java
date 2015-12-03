package rcooper.bookmanager.model;

public class FictionalBook extends Book
{
	private final String INFO = "Genre";
	private String genre;
	
	public FictionalBook()
	{
		super();
	}
	
	public FictionalBook(String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String genre)
	{
		super(title, author, publisher, pubDate, retailPrice, type, genre);
	}

	/* ACCESSORS */

	@Override
	public String toString()
	{
		return super.toString() + genre + " ";
	}

	/* MUTATORS */
	
	public void setGenre(String genre)
	{
		this.genre = genre;
	}

}