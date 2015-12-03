package rcooper.bookmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library extends AbstractModelObject implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private List<Book> items;
	
	public Library()
	{
		items = new ArrayList<Book>();
	}
	
	public int getBookCount()
	{
		return items.size();
	}

	public List<Book> getItems() {
		return items;
	}
	
	public Book getBook(int index)
	{
		return items.get(index);
	}
	
	public void addBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.add(book);
		firePropertyChange("items", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}
	
	public void removeBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.remove(book);
		firePropertyChange("items", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}

	public void replaceBooks(List<Book> newItems) {
		List<Book> oldValue = items;
		items = newItems;
		firePropertyChange("items", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}
	
	public double calculateTotalValue()
	{
		double total = 0;
		
		for(Book book : items) {
			total += book.getRetailPrice();
		}
		
		return total;
	}
	
	public void sortAscending()
	{	
		List<Book> oldItems = items;
		items = new ArrayList<Book>( items );
		Collections.sort( items );
		firePropertyChange( "items", oldItems, items );
	}
	
	public void sortDescending()
	{
		List<Book> oldItems = items;
		items = new ArrayList<Book>( items );
		Collections.sort( items, Collections.reverseOrder() );
		firePropertyChange( "items", oldItems, items );
	}

}