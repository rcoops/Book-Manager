package rcooper.bookmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
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
	
	public int getFictionalCount()
	{
		int count = 0;
		for(Book book : items) {
			if(book instanceof FictionalBook) {
				count++;
			}
		}
		return count;
	}
	
	public int getHistoryCount()
	{
		int count = 0;
		for(Book book : items) {
			if(book instanceof HistoryBook) {
				count++;
			}
		}
		return count;
	}
	
	public int getTextCount()
	{
		int count = 0;
		for(Book book : items) {
			if(book instanceof TextBook) {
				count++;
			}
		}
		return count;
	}

	public List<Book> getBooks() {
		return items;
	}
	
	public List<Book> getBooksFilteredByDate(GregorianCalendar startDate, GregorianCalendar endDate)
	{
		List<Book> books = new ArrayList<Book>();
		for(Book book : items) {
			GregorianCalendar date = book.getPubDate();
			boolean beforeEnd = true;
			if(endDate != null) {
				beforeEnd = date.compareTo(endDate) == -1;
			}
			boolean afterStart = date.compareTo(startDate) > -1;
			if( beforeEnd && afterStart ) {
				books.add(book);
			}
		}
		return books;
	}
	
	public int getTotalPrices()
	{
		int total = 0;
		if(!isEmpty()) {
			for(Book book : items) {
				total += book.getPriceInPence();
			}
		}
		return total;
	}
	
	public Book getBook(int index)
	{
		Book book = null;
		try {
			book = items.get(index); 
		} catch(IndexOutOfBoundsException e) {}
		return book;
	}
	
	public boolean isEmpty()
	{
		return items.isEmpty();
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for(Book item : items) {
			sb.append( item.toString() + "\n");
		}
		
		return sb.toString();
	}
	
	public void addBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.add(book);
		firePropertyChange("books", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}
	
	public void addBook(int index, Book book)
	{
		if(index == -1) {
			index++;
		}
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.add(index, book);
		firePropertyChange("books", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}
	
	public void removeBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.remove(book);
		firePropertyChange("books", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}

	public void replaceBooks(List<Book> newItems) {
		List<Book> oldValue = items;
		items = newItems;
		firePropertyChange("books", oldValue, items);
		try {
			firePropertyChange("itemsCount", oldValue.size(), items.size());
		} catch( NullPointerException npe) {
			firePropertyChange("itemsCount", 0, items.size());
		}
	}
	
	public void sortAscending()
	{	
		List<Book> oldItems = items;
		items = new ArrayList<Book>( items );
		Collections.sort( items );
		firePropertyChange( "books", oldItems, items );
	}
	
	public void sortDescending()
	{
		List<Book> oldItems = items;
		items = new ArrayList<Book>( items );
		Collections.sort( items, Collections.reverseOrder() );
		firePropertyChange( "books", oldItems, items );
	}

}