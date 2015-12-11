package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

/**
 * A concrete realisation of a <code>Book</code> object with an extra field for 
 * genre.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class FictionalBook extends Book
{
	
	private static final long serialVersionUID = 2070793460478286240L;
	private final String CATEGORY = "Genre";
	private final String TYPE = "Fictional";
	
	/**
	 * Creates a new <code>FictionalBook</code> and with type of 'Fictional' 
	 * and an additional genre field. 
	 */
	public FictionalBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(CATEGORY);
	}
		
	public FictionalBook(String isbn, String title, String author, String publisher, GregorianCalendar pubDate, int priceInPence, String genre)
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