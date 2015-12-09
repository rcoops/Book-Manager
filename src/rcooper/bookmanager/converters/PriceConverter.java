package rcooper.bookmanager.converters;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.jdesktop.beansbinding.Converter;

/**
 * Converts between a monetry value represented in pence and a String 
 * representation of the value in pounds.
 * 
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 * @version 0.7
 *
 */
public class PriceConverter extends Converter<Integer, java.lang.String>
{

	/**
	 * Converts the value in pence forward to a String value in pounds.
	 * 
	 *  @param priceInPence monetry value in pence
	 *  @return String representation of value in pounds
	 */
	@Override
	public String convertForward(Integer priceInPence)
	{
		double priceInPounds = ((int) priceInPence) / 100d;
        DecimalFormat dF = new DecimalFormat( "########0.00" );
		return "£" + dF.format(priceInPounds); 
	}
	
	/**
	 * Converts the String value in pounds back to a value in pence.
	 * 
	 *  @param strPrice String representation of value in pounds
	 *  @return monetry value in pence
	 */
	@Override
	public Integer convertReverse(String strPrice)
	{
		if(strPrice.contains("£")) {
			strPrice = strPrice.replace("£", "");
		}
		BigDecimal bd = new BigDecimal(strPrice);
		bd = bd.multiply(new BigDecimal(100));
		return bd.intValue();
	}
}