package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

/**
 * A concrete realisation of a <code>Book</code> object with an extra field for 
 * the subject that the book is on.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class TextBook extends Book
{
	
	private static final long serialVersionUID = 8731385304374156499L;
	private final String CATEGORY = "Subject";
	private final String TYPE = "Textbook";
	
	/**
	 * Creates a new <code>TextBook</code> and with type of 'Textbook' 
	 * and an additional subject field. 
	 */
	public TextBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(CATEGORY);
	}
	
	public TextBook(String isbn, String title, String author, String publisher, GregorianCalendar pubDate, int priceInPence, String genre)
	{
		this();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.priceInPence = priceInPence;
		setInfoValue(genre);
	}
	
}