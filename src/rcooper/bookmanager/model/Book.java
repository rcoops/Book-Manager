package rcooper.bookmanager.model;

import java.io.Serializable;

import rcooper.bookmanager.model.AbstractModelObject;

public abstract class Book extends AbstractModelObject implements Serializable
{
	
	public static final int FICTIONAL_BOOK = 0;
	public static final int HISTORY_BOOK = 1;
	public static final int TEXT_BOOK = 2;
	protected String title, author, publisher, publicationDate;
	protected double retailPrice;
	protected int id, type;

	public Book(int id, String title, String author, String publisher,
			String publicationDate, double retailPrice, int type)
	{
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.retailPrice = retailPrice;
		this.type = type;
	}

	/* ACCESSORS */

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

	public String getPublicationDate()
	{
		return publicationDate;
	}

	public double getRetailPrice()
	{
		return retailPrice;
	}

	public int getType()
	{
		return type;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(type + " ");
		sb.append(title + " ");
		sb.append(author + " ");
		sb.append(publisher + " ");
		sb.append(publicationDate + " ");
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

	public void setPublicationDate(String publicationDate)
	{
		this.publicationDate = publicationDate;
	}

	public void setRetailPrice(double retailPrice)
	{
		this.retailPrice = retailPrice;
	}

}