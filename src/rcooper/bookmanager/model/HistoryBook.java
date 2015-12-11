package rcooper.bookmanager.model;

import java.util.GregorianCalendar;

/**
 * A concrete realisation of a <code>Book</code> object with an extra field for 
 * period in history the book is about.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class HistoryBook extends Book
{

	private static final long serialVersionUID = -4488018034847809439L;
	private final String CATEGORY = "Period";
	private final String TYPE = "History";
	
	/**
	 * Creates a new <code>HistoryBook</code> and with type of 'History' 
	 * and an additional period field. 
	 */
	public HistoryBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(CATEGORY);
	}
	
	public HistoryBook(String isbn, String title, String author, String publisher, GregorianCalendar pubDate, int priceInPence, String genre)
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