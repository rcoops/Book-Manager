package rcooper.bookmanager;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import rcooper.bookmanager.model.Book;

/**
 * Permanently stores and retrieves the Books held within a <code>List</code> as
 * a file.
 * 
 * @version 0.2
 * @author Rick Cooper r.p.cooper1@edu.salford.ac.uk
 */
public class LibraryReaderWriter
{

	private String fileName; // File name to read to and write from

	/**
	 * Creates a <code>LibraryReaderWriter</code> object able to permanently
	 * store and retrieve <code>Book</code> data held within a
	 * <code>Library</code>.
	 * 
	 * @param fileName
	 *            The file name that the data can be stored and retrieved from.
	 */
	public LibraryReaderWriter(String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * Writes all <code>Book</code> data held within a <code>List</code>.
	 * 
	 * @param books
	 *            The list of <code>Book</code>s to be written.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public void writeObjects(List<Book> books) throws IOException
	{
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;
		// Better to catch in library after opening/closing attempted
		try {
			file = new FileOutputStream(fileName);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(books);
		} finally {
			if(output != null) {
				output.close();
			}
			if(buffer != null) {
				buffer.close();
			}
			if(file != null) {
				file.close();
			}
		}
	}

	/**
	 * Reads all <code>Book</code> data held within a <code>List</code> of
	 * <code>Book</code> objects.
	 * 
	 * @return A list containing <code>Book</code> objects.
	 * @throws IOException
	 *             If an I/O error occurs.
	 */
	public List<Book> readObjects() throws IOException
	{
		InputStream file = null;
		InputStream buffer = null;
		ObjectInput input = null;
		try {
			file = new FileInputStream(fileName);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			return (List<Book>) input.readObject();
		} catch(ClassNotFoundException | ClassCastException e) {
			return null; // Handle this in manager
		} finally {
			if(input != null) {
				input.close();
			}
			if(buffer != null) {
				buffer.close();
			}
			if(file != null) {
				file.close();
			}
		}
	}
}