package rcooper.bookmanager.model;

public abstract class Book extends AbstractModelObject implements Comparable<Book>
{
	
	protected final String INFO = "";
	protected String type, title, author, publisher, pubDate;
	protected double price;
	protected AdditionalInfo info;

	public Book()
	{
		this("", "", "", "", 0, "", "");
	}
	
	public Book(String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String info)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.price = retailPrice;
		this.type = type;
		this.info = new AdditionalInfo(INFO, "");
	}

	/* ACCESSORS */

	public AdditionalInfo getInfo()
	{
		return info;
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

	public String getPublicationDate()
	{
		return pubDate;
	}

	public double getRetailPrice()
	{
		return price;
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
		this.price = retailPrice;
	}

	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}
}