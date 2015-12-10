package rcooper.bookmanager.model;

import java.io.Serializable;
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
	
	protected String type, title, author, publisher;
	protected GregorianCalendar pubDate;
	protected int priceInPence;
	protected AdditionalInfo info;

	/**
	 * 
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

	
	public String getInfoValue()
	{
		return info.getValue();
	}
	
	public String getInfoLabel()
	{
		return info.getLabel();
	}
	
	public String getLabel()
	{
		return info.getLabel();
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getTitle()
	{
		return title;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getPublisher()
	{
		return publisher;
	}

	public GregorianCalendar getPubDate()
	{
		return pubDate;
	}

	public int getPriceInPence()
	{
		return priceInPence;
	}

	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(type + " ");
		sb.append(title + " ");
		sb.append(author + " ");
		sb.append(publisher + " ");
		sb.append(pubDate + " ");
		sb.append(priceInPence + " ");
		return sb.toString() + info.toString();
	}

	/* MUTATORS */
	
	public void setType(String type)
	{
		String old = this.type;
		this.type = type;
		firePropertyChange("type", old, this.type);
	}
	
	public void setTitle(String title)
	{
		String old = this.title;
		this.title = title;
		firePropertyChange("title", old, this.title);
	}

	public void setAuthor(String author)
	{
		String old = this.author;
		this.author = author;
		firePropertyChange("author", old, this.author);
	}

	public void setPublisher(String publisher)
	{
		String old = this.publisher;
		this.publisher = publisher;
		firePropertyChange("publisher", old, this.publisher);
	}
	
	public void setPubDate(GregorianCalendar pubDate)
	{
		GregorianCalendar old = this.pubDate;
		this.pubDate = pubDate;
		firePropertyChange("pubDate", old, this.pubDate);
	}

	public void setPriceInPence(int priceInPence)
	{
		int old = this.priceInPence;
		this.priceInPence = priceInPence;
		firePropertyChange("priceInPence", old, this.priceInPence);
	}

	public void setInfoLabel(String label)
	{
		String old = this.info.getLabel();
		this.info.setLabel(label);
		firePropertyChange("infoLabel", old, this.info.getLabel());
	}

	public void setInfoValue(String value)
	{
		String old = info.getValue();
		info.setValue(value);
		firePropertyChange("infoValue", old, info.getValue());
	}
}