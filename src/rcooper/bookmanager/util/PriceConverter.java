package rcooper.bookmanager.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdesktop.beansbinding.Converter;

/**
 * Converts between a monetry value represented in pence and a
 * <code>String</code> representation of the value in pounds.
 * 
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 * @version 0.7
 */
public class PriceConverter extends Converter<Integer, java.lang.String>
{

	/**
	 * Converts the value in pence forward to a String value in pounds.
	 * 
	 * @param priceInPence
	 *            The monetry value in pence.
	 * @return String representation of value in pounds.
	 * @see org.jdesktop.beansbinding.Converter#convertForward(java.lang.Object)
	 */
	@Override
	public String convertForward(Integer priceInPence)
	{
		double priceInPounds = priceInPence / 100d;
		DecimalFormat dF = new DecimalFormat("########0.00");
		return "£" + dF.format(priceInPounds);
	}

	/**
	 * Converts the String value in pounds back to a value in pence.
	 * 
	 * @param strPrice
	 *            String representation of value in pounds.
	 * @return The monetry value in pence.
	 * @see org.jdesktop.beansbinding.Converter#convertReverse(java.lang.Object)
	 */
	@Override
	public Integer convertReverse(String strPrice)
	{
		if(strPrice.contains("£")) {
			strPrice = strPrice.replace("£", "");
		}
		try {
			Integer.parseInt(strPrice.replace(".", ""));
		} catch(NumberFormatException e) {
			String message = "This box only accepts numbers, decimal points and £ characters.\nIf you leave this page before correcting, your price will be reset to £0.00";
			JOptionPane.showMessageDialog(null, message, "Incorrect Price Format", JOptionPane.ERROR_MESSAGE);
		}
		// Use BigDecimal to ensure accuracy, * 100 to get pence value
		BigDecimal bd = new BigDecimal(strPrice).multiply(new BigDecimal(100));
		return bd.intValue();
	}
}