package rcooper.bookmanager.model;

/**
 * Models a concrete realisation of the an abstract <code>Book</code> object
 * with the category "Fictional" and an additional piece of information storing
 * genre.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class FictionalBook extends Book
{

	private static final long serialVersionUID = 1L;
	private final String INFO_LABEL = "Genre";
	private final String TYPE = "Fictional";

	/**
	 * Creates a new <code>FictionalBook</code> object.
	 */
	public FictionalBook()
	{
		super();
		setType(TYPE);
		setInfoLabel(INFO_LABEL);
	}

}