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

public class LibraryReaderWriter
{

	private String fileName;

	public LibraryReaderWriter(String fileName)
	{
		this.fileName = fileName;
	}

	public void writeObjects(List<Book> library) throws IOException 
	{
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;
		// Better to catch in library after opening/closing attempted
		try { 
			file = new FileOutputStream(fileName);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(library);
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

	@SuppressWarnings("unchecked")
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