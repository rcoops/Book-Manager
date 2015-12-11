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

	private static final long serialVersionUID = 3103970292130053229L;
	protected String type, isbn, title, author, publisher;
	protected GregorianCalendar pubDate;
	protected int priceInPence;
	protected AdditionalInfo info;

	/**
	 * For use by concrete subclasses for instantiating a book with no field
	 * values.
	 */
	public Book()
	{
		type = "";
		isbn = "";
		title = "";
		author = "";
		publisher = "";
		pubDate = new GregorianCalendar();
		priceInPence = 0;
		info = new AdditionalInfo();
	}

	/* ACCESSORS */

	/**
	 * 
	 * @return The <code>Book</code>s type.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * 
	 * @return The <code>Book</code>s unique ISBN number.
	 */
	public String getIsbn()
	{
		return isbn;
	}
	
	/**
	 * @return The <code>Book</code>s title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return The <code>Book</code>s author.
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * @return The <code>Book</code>s publisher.
	 */
	public String getPublisher()
	{
		return publisher;
	}

	/**
	 * @return The date that the <code>Book</code> was published.
	 */
	public GregorianCalendar getPubDate()
	{
		return pubDate;
	}

	/**
	 * @return A <code>String</code> representation of the date the book was
	 *         published in 'dd MMM yyyy' format.
	 */
	public String getStrDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		return formatter.format(pubDate.getTime());
	}

	/**
	 * @return The <code>Book</code>'s price in pence.
	 */
	public int getPriceInPence()
	{
		return priceInPence;
	}

	/**
	 * @return The additional piece of information held by concrete objects.
	 */
	public String getInfoValue()
	{
		return info.getValue();
	}

	/**
	 * @return The category of the additional info.
	 */
	public String getInfoLabel()
	{
		return info.getLabel();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}

	/* MUTATORS */

	/**
	 * For use in concrete constructors to apply property changes for date
	 * binding.
	 * 
	 * @param type
	 */
	protected void setType(String type)
	{
		String old = this.type;
		this.type = type;
		firePropertyChange("type", old, this.type);
	}

	/**
	 * @param isbn
	 *            The Book's unique isbn number.
	 */
	public void setIsbn(String isbn)
	{
		String old = this.isbn;
		this.isbn = isbn;
		firePropertyChange("isbn", old, this.isbn);
	}

	/**
	 * @param title
	 *            The Book's title
	 */
	public void setTitle(String title)
	{
		String old = this.title;
		this.title = title;
		firePropertyChange("title", old, this.title);
	}

	/**
	 * @param author
	 *            The <code>Book</code>'s author
	 */
	public void setAuthor(String author)
	{
		String old = this.author;
		this.author = author;
		firePropertyChange("author", old, this.author);
	}

	/**
	 * @param publisher
	 *            The <code>Book</code>'s publisher
	 */
	public void setPublisher(String publisher)
	{
		String old = this.publisher;
		this.publisher = publisher;
		firePropertyChange("publisher", old, this.publisher);
	}

	/**
	 * @param pubDate
	 *            The date that the <code>Book</code> was published
	 */
	public void setPubDate(GregorianCalendar pubDate)
	{
		GregorianCalendar old = this.pubDate;
		this.pubDate = pubDate;
		firePropertyChange("pubDate", old, this.pubDate);
	}

	/**
	 * @param priceInPence
	 *            The <code>Book</code>'s retail price in whole pence.
	 */
	public void setPriceInPence(int priceInPence)
	{
		int old = this.priceInPence;
		this.priceInPence = priceInPence;
		firePropertyChange("priceInPence", old, this.priceInPence);
	}

	/**
	 * @param label
	 *            The category of the additional info to be held by concrete
	 *            objects.
	 */
	public void setInfoLabel(String label)
	{
		String old = this.info.getLabel();
		this.info.setLabel(label);
		firePropertyChange("infoLabel", old, this.info.getLabel());
	}

	/**
	 * @param value
	 *            The additional piece of information to be held by the concrete
	 *            object.
	 */
	public void setInfoValue(String value)
	{
		String old = info.getValue();
		info.setValue(value);
		firePropertyChange("infoValue", old, info.getValue());
	}
}