package rcooper.bookmanager.model;

/**
 * Models a concrete realisation of the an abstract <code>Book</code> object
 * with the category "History" and an additional piece of information storing
 * the period that the book is about.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class HistoryBook extends Book
{
	
	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Period";
	private final String TYPE = "History";
	
	/**
	 * Creates a new <code>HistoryBook</code> object.
	 */
	public HistoryBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}

}