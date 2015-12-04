package rcooper.bookmanager.model;

public abstract class Book extends AbstractModelObject implements Comparable<Book>
{
	
	protected String type, title, author, publisher, pubDate;
	protected double price;
	protected AdditionalInfo info;

	public Book()
	{
		this("", "", "", "", 0, "");
	}
	
	public Book(String title, String author, String publisher,
			String pubDate, double price, String type)
	{
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubDate = pubDate;
		this.price = price;
		this.type = type;
		this.info = new AdditionalInfo("", "");
	}

	/* ACCESSORS */

	
	public String getInfoValue()
	{
		return info.value;
	}
	
	public String getInfoLabel()
	{
		return info.label;
	}
	
	public String getLabel()
	{
		return info.getLabel();
	}
	
	public String getGenre()
	{
		return info.getValue();
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

	public String getPubDate()
	{
		return pubDate;
	}

	public double getPrice()
	{
		return price;
	}

	/* MUTATORS */
	
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

	public void setPubDate(String pubDate)
	{
		String old = this.pubDate;
		this.pubDate = pubDate;
		firePropertyChange("pubDate", old, this.pubDate);
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
		String old = this.info.getValue();
		this.info.setValue(value);
		firePropertyChange("infoValue", old, this.info.getValue());
	}

	@Override
	public int compareTo(Book otherBook)
	{
		return getTitle().compareTo(otherBook.getTitle());
	}
}