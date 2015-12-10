package rcooper.bookmanager.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import rcooper.bookmanager.model.components.AdditionalInfo;

/**
 * Models a book with various properties that all varieties of book shar.
 * 
 * @version 1.2
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public abstract class Book extends AbstractModelObject implements Comparable<Book>, Serializable
{
	private static final long serialVersionUID = 1L;
	protected String type, title, author, publisher;
	protected GregorianCalendar pubDate;
	protected int priceInPence;
	protected AdditionalInfo info;

	/**
	 * Creates a new book with empty values.
	 */
	public Book()
	{
		type = "";
		title = "";
		author = "";
		publisher = "";
		pubDate = new GregorianCalendar();
		priceInPence = 0;
		info = new AdditionalInfo();
	}

	/* ACCESSORS */

	/**
	 * Gets an additional piece of information to be held in a concrete
	 * realisation of the book.
	 * 
	 * @return The information held in the book
	 */
	public String getInfoValue()
	{
		return info.getValue();
	}

	/**
	 * Gets the type of information held in the book
	 * 
	 * @return The type name of the additional information in the book.
	 */
	public String getInfoLabel()
	{
		return info.getLabel();
	}

	/**
	 * Gets the type of book.
	 * 
	 * @return The book type.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Gets the title of the book.
	 * 
	 * @return The book's title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Gets the author of the book.
	 * 
	 * @return The book's author.
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * Gets the organisation that published the book.
	 * 
	 * @return The book's publisher.
	 */
	public String getPublisher()
	{
		return publisher;
	}

	/**
	 * Gets the book's was publication date a raw calendar.
	 * 
	 * @return The date the book was published.
	 */
	public GregorianCalendar getPubDate()
	{
		return pubDate;
	}

	/**
	 * Gets the book's was publication date as a formatted String.
	 * 
	 * @return The date the book was published.
	 */
	public String getDateString()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(pubDate.getTime());
	}

	/**
	 * Gets the book's price in pence as a whole number.
	 * 
	 * @return The book's retail price.
	 */
	public int getPriceInPence()
	{
		return priceInPence;
	}

	/**
	 * Compare's the book to another book by book title.
	 * 
	 * @param otherBook
	 *            The book to be compared with.
	 * @return -1 if the book's title alphabetically earlier than the other
	 *         book, 0 if they have the same title, or 1 if it is alpabetically
	 *         later
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}

	/* MUTATORS */
	
	
	/**
	 * Set's the book type.
	 * 
	 * @param type	The type of book.
	 */
	protected void setType(String type)
	{
		String old = this.type;
		this.type = type;
		firePropertyChange("type", old, this.type);
	}

	/**
	 * Set's the book title.
	 * 
	 * @param title The book's title.
	 */
	protected void setTitle(String title)
	{
		String old = this.title;
		this.title = title;
		firePropertyChange("title", old, this.title);
	}

	/**
	 * Set's the book's author.
	 * 
	 * @param author The book's author.
	 */
	protected void setAuthor(String author)
	{
		String old = this.author;
		this.author = author;
		firePropertyChange("author", old, this.author);
	}

	/**
	 * Set's the book's publisher.
	 * 
	 * @param publisher The book's publisher.
	 */
	protected void setPublisher(String publisher)
	{
		String old = this.publisher;
		this.publisher = publisher;
		firePropertyChange("publisher", old, this.publisher);
	}


	/**
	 * Set's the book's publication date.
	 * 
	 * @param pubDate The date the book was published.
	 */
	protected void setPubDate(GregorianCalendar pubDate)
	{
		GregorianCalendar old = this.pubDate;
		this.pubDate = pubDate;
		firePropertyChange("pubDate", old, this.pubDate);
	}

	/**
	 * Set's the book's price.
	 * 
	 * @param priceInPence The book's price as a whole number in pence.
	 */
	protected void setPriceInPence(int priceInPence)
	{
		int old = this.priceInPence;
		this.priceInPence = priceInPence;
		firePropertyChange("priceInPence", old, this.priceInPence);
	}

	/**
	 * Set's the type name of an additional piece of information held within 
	 * the book.
	 * 
	 * @param label The label used to display the additional information type.
	 */
	protected void setInfoLabel(String label)
	{
		String old = this.info.getLabel();
		this.info.setLabel(label + ":");
		firePropertyChange("infoLabel", old, this.info.getLabel());
	}

	/**
	 * Set's the additional piece of information held within the book. 
	 * 
	 * @param value The additional piece of information to be held.
	 */
	protected void setInfoValue(String value)
	{
		String old = info.getValue();
		info.setValue(value);
		firePropertyChange("infoValue", old, info.getValue());
	}
}