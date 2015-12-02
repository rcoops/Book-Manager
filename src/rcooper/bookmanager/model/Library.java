package rcooper.bookmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Library extends AbstractModelObject implements Serializable
{
	public static final int FICTIONAL = 0;
	public static final int HISTORY = 1;
	public static final int TEXT = 2;
	private static final long serialVersionUID = 1L;
	private List<Book> items;
	private int idCount;
	
	public Library()
	{
		idCount = 0;
		items = new ArrayList<Book>();
	}
	
	public List<Book> getItems() 
	{
		return items;
	}
	
	public int getCount()
	{
		return idCount;
	}
	
	public Book getBook(int index)
	{
		return items.get(index);
	}
	
	public void addBook(Book book)
	{
		items.add(book);
		idCount++;
	}
	
	public int getSize()
	{
		return items.size();
	}
	
	public boolean isEmpty()
	{
		return items.isEmpty();
	}
	
	public void removeBook(Book book)
	{
		if(items.contains(book)) {
			items.remove(book);
		}
	}
	
	public double calculateTotalValue()
	{
		double total = 0;
		
		for(Book book : items) {
			total += book.getRetailPrice();
		}
		
		return total;
	}

}