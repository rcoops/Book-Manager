package rcooper.bookmanager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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

import javax.swing.JOptionPane;

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
		OutputStream fileOutput = null;
		OutputStream bufferedOutput = null;
		ObjectOutput objectOutput = null;
		// Better to catch in library after opening/closing attempted
		try {
			fileOutput = new FileOutputStream(fileName);
			bufferedOutput = new BufferedOutputStream(fileOutput);
			objectOutput = new ObjectOutputStream(bufferedOutput);
			objectOutput.writeObject(books);
		} finally {
			if(objectOutput != null) {
				objectOutput.close();
			}
			if(bufferedOutput != null) {
				bufferedOutput.close();
			}
			if(fileOutput != null) {
				fileOutput.close();
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
		InputStream fileInput = null;
		InputStream bufferedInput = null;
		ObjectInput objectInput = null;
		try {
			fileInput = new FileInputStream(fileName);
			bufferedInput = new BufferedInputStream(fileInput);
			objectInput = new ObjectInputStream(bufferedInput);
			return (List<Book>) objectInput.readObject();
		} catch(ClassNotFoundException | ClassCastException e) {
			return null; // Handle this in manager
		} finally {
			if(objectInput != null) {
				objectInput.close();
			}
			if(bufferedInput != null) {
				bufferedInput.close();
			}
			if(fileInput != null) {
				fileInput.close();
			}
		}
	}
}