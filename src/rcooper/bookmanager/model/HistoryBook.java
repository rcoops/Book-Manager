package rcooper.bookmanager.model;

public class HistoryBook extends Book
{

	private String period;

	public HistoryBook(int id, String author, String publisher,
			String publicationDate, double retailPrice, int type, String period)
	{
		super(id, author, publisher, publicationDate, period, retailPrice,
				type);
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