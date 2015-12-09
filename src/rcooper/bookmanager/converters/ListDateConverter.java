package rcooper.bookmanager.converters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.jdesktop.beansbinding.Converter;

@SuppressWarnings("rawtypes")
public class ListDateConverter extends Converter<List<GregorianCalendar>, List>
{
	private SimpleDateFormat formatter;
	
	public ListDateConverter()
	{
		formatter = new SimpleDateFormat("dd/MM/yyyy");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List convertForward(List<GregorianCalendar> dateList)
	{
		List strings = new ArrayList();
		for(GregorianCalendar date : dateList) {
			strings.add(formatter.format(date.getTime()));
		}
		return strings;
	}
	
	// No need to implement this as we wont change dates via list
	@Override
	public List<GregorianCalendar> convertReverse(List arg0)
	{
		return null;
	}

}