package rcooper.bookmanager.model;

/**
 * Models a concrete realisation of the an abstract <code>Book</code> object
 * with the category "Textbook" and an additional piece of information storing
 * the book's subject.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class TextBook extends Book
{
	
	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Subject";
	private final String TYPE = "Textbook";
	
	/**
	 * Creates a new <code>HistoryBook</code> object.
	 */
	public TextBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}
	
}