package rcooper.bookmanager.model;

import java.util.ArrayList;

public class Library
{
	private ArrayList<Book> library;
	
	public Library()
	{
		library = new ArrayList<Book>();
	}
	
	public Book getBook(int index)
	{
		return library.get(index);
	}
	
	public void addBook(Book book)
	{
		library.add(book);
	}
	
	public int getSize()
	{
		return library.size();
	}
	
	public boolean isEmpty()
	{
		return library.isEmpty();
	}
	
	public void removeBook(Book book)
	{
		
	}

}