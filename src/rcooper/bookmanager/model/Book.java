package rcooper.bookmanager.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Book extends AbstractModelObject implements Comparable<Book>, Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type, title, author, publisher;
	protected GregorianCalendar pubDate;
	protected double price;
	protected AdditionalInfo info;

	public Book()
	{
		this("", "", "", new GregorianCalendar(), 0);
	}
	
	public Book(String title, String author, String publisher,
			GregorianCalendar pubDate, double price)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.price = price;
		this.type = "";
		this.info = new AdditionalInfo("", "");
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

	public double getPrice()
	{
		return price;
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
		sb.append(price + " ");
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
		this.pubDate = pubDate;
	}

	public void setPrice(double price)
	{
		double old = this.price;
		this.price = price;
		firePropertyChange("price", old, this.price);
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