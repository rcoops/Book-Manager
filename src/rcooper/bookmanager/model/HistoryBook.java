package rcooper.bookmanager.model;

public class HistoryBook extends Book
{

	private String period;

	public HistoryBook(int id, String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String period)
	{
		super(id, title, author, publisher, pubDate, retailPrice, type);
		this.period = period;
	}

	/* ACCESSORS */
	
	public String getPeriod()
	{
		return period;
	}

	@Override
	public String toString()
	{
		return super.toString() + period + " ";
	}
	
	/* MUTATORS */

	public void setPeriod(String period)
	{
		this.period = period;
	}

}