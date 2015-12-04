package rcooper.bookmanager.model;

public class HistoryBook extends Book
{

	private final String INFO_LABEL = "Period:";
	private String period;

	public HistoryBook(String title, String author, String publisher,
			String pubDate, double retailPrice, String type, String period)
	{
		super(title, author, publisher, pubDate, retailPrice, type);
		this.info = new AdditionalInfo(INFO_LABEL, period);
	}

}