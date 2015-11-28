package rcooper.bookmanager.model;

public class FictionalBook extends Book
{

	private String genre;
	
	public FictionalBook(int id, String title, String author, String publisher,
			String pubDate, double retailPrice, int type, String genre)
	{
		super(id, title, author, publisher, pubDate, retailPrice, type);
		this.genre = genre;
	}

	/* ACCESSORS */
	
	public String getGenre()
	{
		return genre;
	}

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