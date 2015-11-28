package rcooper.bookmanager.model;

public class TextBook extends Book
{
	
	private String subject;
	
	public TextBook(int id, String author, String publisher,
			String publicationDate, double retailPrice, int type,
			String subject)
	{
		super(id, author, publisher, publicationDate, subject, retailPrice,
				type);
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