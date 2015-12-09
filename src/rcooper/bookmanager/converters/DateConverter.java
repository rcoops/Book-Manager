package rcooper.bookmanager.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.beansbinding.Converter;

public class DateConverter extends Converter<GregorianCalendar, java.lang.String>
{
	
	private JFrame parent;
	private SimpleDateFormat formatter;
	
	public DateConverter(JFrame parent)
	{
		this.parent = parent;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public String convertForward(GregorianCalendar date)
	{	
		return formatter.format(date.getTime());
	}

	@Override
	public GregorianCalendar convertReverse(String strDate)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			Date date = formatter.parse(strDate);
			calendar.setTime(date);
		} catch (ParseException e) {
			String message = "This box only accepts dates written in the format DD/MM/YYYY\nIf you leave this page before correcting, your date will be reset to today's date";
			JOptionPane.showMessageDialog(parent, message, "Incorrect Date Format", JOptionPane.ERROR_MESSAGE);
		}
		return calendar;
	}

}