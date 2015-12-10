package rcooper.bookmanager.converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.jdesktop.beansbinding.Converter;

/**
 * Converts a list of <code>GregorianCalendar</code> objects to and from a
 * formatted list of <code>String</code>s for use by a data binding.
 * 
 * @version 0.1
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class ListDateConverter extends Converter<List<GregorianCalendar>, List>
{
	private SimpleDateFormat formatter; // Formats and parses the calendar

	/**
	 * Constructs a new <code>ListDateConverter</code> linked to a parent for
	 * the display of error messages.
	 * 
	 * @param parent
	 *            The parent frame able to display the error message.
	 */
	public ListDateConverter()
	{
		formatter = new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * Converts the provided calendar list into a list of formatted
	 * <code>String</code>s.
	 * 
	 * @param calendar
	 *            The list of calendars to be converted.
	 * @return A list of formatted <code>String</code>s representing each
	 *         calendar.
	 * @see org.jdesktop.beansbinding.Converter#convertForward(java.lang.Object)
	 */
	@Override
	public List convertForward(List<GregorianCalendar> dateList)
	{
		List strings = new ArrayList();
		for(GregorianCalendar date : dateList) {
			strings.add(formatter.format(date.getTime()));
		}
		return strings;
	}
	
	/**
	 * Converts the provided <code>String</code> into a calendar.
	 * 
	 * @param calendar The calendar object to be converted.
	 * @return A formatted <code>String</code> representation of the calendar.
	 * @see org.jdesktop.beansbinding.Converter#convertReverse(java.lang.Object)
	 */
	@Override
	public List<GregorianCalendar> convertReverse(List arg0)
	{
		// No need to implement this as we wont change dates via list
		return null;
	}

}