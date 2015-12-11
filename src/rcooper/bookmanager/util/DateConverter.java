package rcooper.bookmanager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.jdesktop.beansbinding.Converter;

/**
 * Converts a <code>GregorianCalendar</code> object to and from a formatted
 * <code>String</code> for use by a data binding.
 * 
 * @version 0.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class DateConverter extends Converter<GregorianCalendar, java.lang.String>
{
	
	private SimpleDateFormat formatter; // Formats and parses the calendar
	
	public DateConverter()
	{
		formatter = new SimpleDateFormat("dd/MM/yyyy");
	}

	/**
	 * Converts the provided calendar into a formatted <code>String</code>.
	 * The produced format is dd/MM/yyyy.
	 * @param calendar
	 *            The calendar to be converted.
	 * @return A formatted <code>String</code> representation of the calendar.
	 * @see org.jdesktop.beansbinding.Converter#convertForward(java.lang.Object)
	 * @see java.text.SimpleDateFormat
	 */
	@Override
	public String convertForward(GregorianCalendar calendar)
	{
		return formatter.format(calendar.getTime());
	}

	/**
	 * Converts the provided <code>String</code> into a <code>Calendar</code>
	 * object.
	 * 
	 * @param strDate
	 *            A formatted <code>String</code> representation of the
	 *            calendar.
	 * @return A <code>Calendar</code> object with the date in the
	 *         <code>String</code>
	 * @see org.jdesktop.beansbinding.Converter#convertReverse(java.lang.Object)
	 */
	@Override
	public GregorianCalendar convertReverse(String strDate)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			Date date = formatter.parse(strDate);
			calendar.setTime(date);
		} catch(ParseException e) {
			String message = "This box only accepts dates written in the format DD/MM/YYYY\nIf you leave this page before correcting, your date will be reset to today's date.";
			JOptionPane.showMessageDialog(null, message, "Incorrect Date Format", JOptionPane.ERROR_MESSAGE);
		}
		return calendar;
	}

}