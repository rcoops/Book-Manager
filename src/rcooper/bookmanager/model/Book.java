package rcooper.bookmanager.model;

import java.io.Serializable;

import rcooper.bookmanager.model.AbstractModelObject;

public abstract class Book extends AbstractModelObject implements Serializable, Comparable<Book>
{
	
	public static final int FICTIONAL = 0;
	public static final int HISTORY = 1;
	public static final int TEXT = 2;
	protected String type, title, author, publisher, pubDate;
	protected double retailPrice;
	protected int id;

	public Book(int id, String title, String author, String publisher,
			String pubDate, double retailPrice, String type)
	{
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.retailPrice = retailPrice;
		this.type = type;
	}

	/* ACCESSORS */

	public String getType()
	{
		return type;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getId()
	{
		return id;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getPublisher()
	{
		return publisher;
	}

	public String getPublicationDate()
	{
		return pubDate;
	}

	public double getRetailPrice()
	{
		return retailPrice;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(type + " ");
		sb.append(title + " ");
		sb.append(author + " ");
		sb.append(publisher + " ");
		sb.append(pubDate + " ");
		sb.append(retailPrice + " ");
		
		return sb.toString();
	}

	/* MUTATORS */

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	public void setPublicationDate(String pubDate)
	{
		this.pubDate = pubDate;
	}

	public void setRetailPrice(double retailPrice)
	{
		this.retailPrice = retailPrice;
	}

	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}
}