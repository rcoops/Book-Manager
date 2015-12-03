package rcooper.bookmanager.model;

public class HistoryBook extends Book
{

	private final String INFO = "Period";
	private String period;

	public HistoryBook(String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String period)
	{
		super(title, author, publisher, pubDate, retailPrice, type, period);
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