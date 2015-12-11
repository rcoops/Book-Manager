package rcooper.bookmanager.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Models a library able to hold a list of three types of book (fictional,
 * history and textbook) and peform various statistical operations on the
 * collection.
 * 
 * @version 1.4
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class Library extends AbstractModelObject
{

	private List<Book> items;

	/**
	 * Creates a new <code>Library</code> object.
	 */
	public Library()
	{
		items = new ArrayList<Book>();
	}

	/**
	 * @return The number of <code>Book</code>s held in the <code>Library</code>.
	 */
	public int getBookCount()
	{
		return items.size();
	}

	/**
	 * @return The total number of <code>FictionalBook</code>s held in the <code>Library</code>.
	 */
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

	/**
	 * @return The total number of <code>HistoryBook</code>s held in the <code>Library</code>.
	 */
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

	/**
	 * @return The total number of <code>TextBook</code>s held in the <code>Library</code>.
	 */
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

	/**
	 * @return All the <code>Book</code>s held in the <code>Library</code>.
	 */
	public List<Book> getBooks()
	{
		return items;
	}

	/**
	 * Returns a list of <code>Book</code>s held in the library that were published between the two dates provided.
	 * 
	 * @param startDate The lower bound of the filter
	 * @param endDate The upper bound of the filter
	 * @return A list of the <code>Book</code>s held in the <code>Library</code> filtered by date published.
	 */
	public List<Book> getBooksFilteredByDate(GregorianCalendar startDate, GregorianCalendar endDate)
	{
		List<Book> books = new ArrayList<Book>();
		if(startDate.compareTo(endDate) != -1) {
			books = null;
		}
		for(Book book : items) {
			GregorianCalendar date = book.getPubDate();
			boolean beforeEnd = true;
			if(endDate != null) {
				beforeEnd = date.compareTo(endDate) == -1;
			}
			boolean afterStart = date.compareTo(startDate) > -1;
			if(beforeEnd && afterStart) {
				books.add(book);
			}
		}
		return books;
	}

	/**
	 * @return The total retail price of all <code>Book</code>s held in the <code>Library</code>.
	 */
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

	/**
	 * Returns the <code>Book</code> held at the specified index.
	 * 
	 * @param index The list position of the required <code>Book</code>.
	 * @return The <code>Book</code> held at the specified index.
	 */
	public Book getBook(int index)
	{
		Book book = null;
		try {
			book = items.get(index);
		} catch(IndexOutOfBoundsException e) {
		}
		return book;
	}

	/**
	 * @return true if the <code>Library</code> is not holding any books.
	 */
	public boolean isEmpty()
	{
		return items.isEmpty();
	}

	/**
	 * Adds a <code>Book</code> to the <code>Library</code> at the end of the list.
	 * 
	 * @param book The <code>Book</code> to be added.
	 */
	public void addBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.add(book);
		firePropertyChange("books", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}

	/**
	 * Adds a <code>Book</code> to the <code>Library</code> at the specified index.
	 * 
	 * @param index The list position to add the <code>Book</code> to.
	 * @param book The <code>Book</code> to be added.
	 */
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

	/**
	 * Removes a <code>Book</code> from the <code>Library</code>.
	 * 
	 * @param book The <code>Book</code> to be removed.
	 */
	public void removeBook(Book book)
	{
		List<Book> oldValue = items;
		items = new ArrayList<Book>(items);
		items.remove(book);
		firePropertyChange("books", oldValue, items);
		firePropertyChange("itemsCount", oldValue.size(), items.size());
	}


	/**
	 * Replaces all <code>Library</code> contents with another list of <code>Book</code>s.
	 * 
	 * @param newItems The replacement list of <code>Book</code>s.
	 */
	public void replaceBooks(List<Book> newItems)
	{
		List<Book> oldValue = items;
		items = newItems;
		firePropertyChange("books", oldValue, items);
		try {
			firePropertyChange("itemsCount", oldValue.size(), items.size());
		} catch(NullPointerException npe) {
			firePropertyChange("itemsCount", 0, items.size());
		}
	}

	/**
	 * Sorts the list of books by title in ascdending alphabetical order.
	 */
	public void sortAscending()
	{
		List<Book> oldItems = items;
		items = new ArrayList<Book>(items);
		Collections.sort(items);
		firePropertyChange("books", oldItems, items);
	}

	/**
	 * Sorts the list of books by title in descdending alphabetical order.
	 */
	public void sortDescending()
	{
		List<Book> oldItems = items;
		items = new ArrayList<Book>(items);
		Collections.sort(items, Collections.reverseOrder());
		firePropertyChange("books", oldItems, items);
	}

}