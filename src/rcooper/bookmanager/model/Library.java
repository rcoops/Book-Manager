package rcooper.bookmanager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public List<Book> getItems() {
		return items;
	}
	
	public List<String> getAuthors()
	{
		Set<String> authors = null;
		if(!isEmpty()) {
			authors = new HashSet<String>();
			for(Book book : items) {
				authors.add(book.getAuthor());
			}
		}
		return new ArrayList<String>(authors);
	}
	
	public List<String> getPublishers()
	{
		Set<String> publishers = null;
		if(!isEmpty()) {
			publishers = new HashSet<String>();
			for(Book book : items) {
				publishers.add(book.getPublisher());
			}
		}
		return new ArrayList<String>(publishers);
	}
	
	public List<Date> getDates()
	{
		List<Date> dates = null;
		if(!isEmpty()) {
			dates = new ArrayList<Date>();
			for(Book book : items) {
				dates.add(book.getPubDate());
			}
		}
		return dates;
	}
	
	public List<Book> getFilteredDates(Date startDate, Date endDate)
	{
		List<Book> books = new ArrayList<Book>();
		for(Book book : items) {
			Date date = book.getPubDate();
			boolean beforeEnd = date.compareTo(endDate) == -1;
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
			sb.append( item.toString() + " ");
		}
		
		return sb.toString();
	}
	
	public void addBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.add(book);
		firePropertyChange("items", oldValue, items);
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