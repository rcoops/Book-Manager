package rcooper.bookmanager.model;

public class TextBook extends Book
{
	
	private String subject;
	
	public TextBook(int id, String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String subject)
	{
		super(id, title, author, publisher, pubDate, retailPrice, type);
		this.subject = subject;
	}

	/* ACCESSORS */
	
	public String getSubject()
	{
		return subject;
	}

	@Override
	public String toString()
	{
		return super.toString() + subject + " ";
	}
	
	/* MUTATORS */

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
	
}