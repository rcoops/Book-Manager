package rcooper.bookmanager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JListBinding;
import org.jdesktop.swingbinding.SwingBindings;

import rcooper.bookmanager.components.JTextFieldLimited;
import rcooper.bookmanager.model.Book;
import rcooper.bookmanager.model.FictionalBook;
import rcooper.bookmanager.model.HistoryBook;
import rcooper.bookmanager.model.Library;
import rcooper.bookmanager.model.TextBook;

public class BookManagerApp extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	private final int SAVE_DIALOG = 0, OPEN_DIALOG = 1, EDIT_BOOK = 2, ADD_BOOK = 3;
	private final Object[] TYPES = {"Fictional", "History", "Textbook"};
	
	private JPanel detailsPanel;
	private JLabel lblInfo;
	private JTextField txtTitle, txtAuthor, txtPublisher, txtPubDateYear, txtPubDateMonth, txtPubDateDay, txtPrice, txtInfo, txtType;
	private JList<String> list;
	private JButton btnAdd, btnEdit, btnRemove;
	private Library library;
	
	private Book lastBook;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					BookManagerApp frame = new BookManagerApp();
					frame.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();					
				}
			}
		});
	}
	
	public BookManagerApp()
	{
		library = new Library();
		library.addBook(new FictionalBook("The Colour of Magic", "Terry Pratchett", "Corgi", new GregorianCalendar(1984, 1, 18), 5.50 , "Fantasy/Comedy"));
		library.addBook(new TextBook("Objects First with Java", "David Barnes & Michael Kölling", "Pearson", new GregorianCalendar(2011, 9, 30), 59.99 , "Java Programming"));
		library.addBook(new HistoryBook("SPQR: A history of Ancient Rome", "Professor Mary Beard", "Profile Books", new GregorianCalendar(2015, 10, 20), 17.00 , "Ancient History"));
		init(new JTabbedPane(), new JMenuBar()); 
		
		pack();
		initDataBindings();
	}
	
	/* INITIALISATION */
	
	private void init(JTabbedPane mainPane, JMenuBar menuBar)
	{
		setTitle("Book Manager");
		setSize(800, 600);
		setMinimumSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane(mainPane);
		initMenu(menuBar); // app.menuBar setup
		initTabs(mainPane); // app.tabbedPane setup
	}
	
	private void initMenu(JMenuBar menuBar)
	{
		
		//// Menu bar menus ////
		JMenu mnFile = new JMenu("File");
		JMenu mnHelp = new JMenu("Help");
		
		//// File menu items ////
		JMenuItem mnIOpen = new JMenuItem("Open...");
		JMenuItem mnISave = new JMenuItem("Save");
		JMenuItem mnIClose = new JMenuItem("Close");
		
		//// Help menu items ////
		JMenuItem mnIReadMe = new JMenuItem("View Help");
		JMenuItem mnIAbout = new JMenuItem("About Book Manager");
		
		//// app.menuBar setup ////
		menuBar.add(mnFile);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnHelp);
		
		//// app.menuBar.file setup ////
		mnFile.add(mnIOpen);
		mnFile.add(mnISave);
		mnFile.add(mnIClose);
		
		//// app.menuBar.help setup ////
		mnHelp.add(mnIReadMe);
		mnHelp.add(mnIAbout);

		mnIAbout.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed( ActionEvent e )
			{
				showAbout();
			}
			
		});
		
		mnISave.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFile(SAVE_DIALOG);
			}
			
		});
		
		mnIOpen.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFile(OPEN_DIALOG);
			}
			
		});
		
		mnIClose.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				closeLibrary();
			}
			
		});
	}
	
	private void initTabs(JTabbedPane mainPane)
	{
		JSplitPane detailsView = new JSplitPane();
		JPanel reportView = new JPanel();
		
		mainPane.addTab("Details View", detailsView);
		mainPane.addTab("Report View", reportView);
		
		initDetailsView(detailsView, reportView);
	}
	
	private void initDetailsView(JSplitPane detailsView, JPanel reportView)
	{
		detailsPanel = new JPanel();
		JScrollPane listPane = new JScrollPane();
		
		detailsView.setLeftComponent(listPane);
		detailsView.setRightComponent(detailsPanel);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		initListPane(listPane);
		
		//// app.tabbedPane.detailsView.detailsPanel setup ////
		initDetailsPanel();
	}
	
	private void initDetailsPanel()
	{
		JLabel lblType = new JLabel("Type:");
		JLabel lblTitle = new JLabel("Title:");
		JLabel lblAuthor = new JLabel("Author:");
		JLabel lblPublisher = new JLabel("Publisher:");
		JLabel lblPubDate = new JLabel("Publication Date:");
		JLabel lblPrice = new JLabel("Retail Price:");
		JLabel lblSeparator1 = new JLabel("/");
		JLabel lblSeparator2 = new JLabel("/");
		JPanel pnlControls = new JPanel(new GridBagLayout());
		JPanel pnlDate = new JPanel();
		lblInfo = new JLabel();
		txtType = new JTextField();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDateYear = new JTextFieldLimited(4);
		txtPubDateMonth = new JTextFieldLimited(2);
		txtPubDateDay = new JTextFieldLimited(2);
		txtPrice = new JTextField();
		txtInfo = new JTextField();

		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[] {0.15, 0.4, 0.3, 0.15};
		layout.rowWeights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
		detailsPanel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		//gbc.gridy = GridBagConstraints.RELATIVE;
		detailsPanel.add(lblTitle, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblAuthor, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPublisher, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblInfo, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 2;
		detailsPanel.add(lblType, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPrice, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPubDate, gbc);

		gbc = (GridBagConstraints) gbc.clone();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		detailsPanel.add(txtTitle, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtAuthor, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtPublisher, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtInfo, gbc);
		
		
		
		pnlDate.add(txtPubDateDay);
		pnlDate.add(lblSeparator1);
		pnlDate.add(txtPubDateMonth);
		pnlDate.add(lblSeparator2);
		pnlDate.add(txtPubDateYear);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.insets = new Insets(0,0,0,35);
		gbc.gridx = 3;
		detailsPanel.add(txtType, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtPrice, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(pnlDate, gbc);
		
		pnlControls.setName("pnlControls");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.BOTH;
		detailsPanel.add(pnlControls, gbc);

		txtPubDateDay.setColumns(2);
		txtPubDateDay.addFocusListener(new MyFocusListener(txtPubDateDay, Calendar.DAY_OF_MONTH, null));
		txtPubDateMonth.setColumns(2);
		txtPubDateMonth.addFocusListener(new MyFocusListener(txtPubDateMonth, Calendar.MONTH, null));
		txtPubDateYear.setColumns(4);
		txtPubDateYear.addFocusListener(new MyFocusListener(txtPubDateYear, Calendar.YEAR, null));
		
		txtType.setEditable(false);
		setDetailsVisible(false);
		setFieldsEditable(false);
		initDetailControls(pnlControls);
	}
	
	private void initDetailControls(JPanel pnlControls)
	{
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnRemove = new JButton("Remove");
		
		Insets buttonInsets = new Insets(10, 20, 10, 20);
		btnAdd.setMargin(buttonInsets);
		btnAdd.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				// Don't use ADD_BOOK but easier to see what's happening
				addEditBook(ADD_BOOK); 
			}
			
		});
		
		btnEdit.setMargin(buttonInsets);
		btnEdit.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				addEditBook(EDIT_BOOK);
			}
			
		});
		
		btnRemove.setMargin(buttonInsets);
		btnRemove.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				removeBook();
			}
			
		});
		
		btnEdit.setEnabled(false);
		btnRemove.setEnabled(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.3;
		pnlControls.add(btnAdd, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 1;
		pnlControls.add(btnEdit, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 2;
		pnlControls.add(btnRemove, gbc);
	}
	
	private void initListPane(JScrollPane listPane)
	{
		list = new JList<String>();
		
		listPane.setViewportView( list );

		list.setFont(new Font("Arial", Font.PLAIN, 10));
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(list.getSelectedIndex() == -1) { // No item selected
					setDetailsVisible(false);
					for(Component component : detailsPanel.getComponents()) {
						if( component instanceof JTextField) {
							JTextField textField = (JTextField) component;
							textField.setText("");
						}
					}
					btnEdit.setEnabled(false);
					btnRemove.setEnabled(false);
				} else {
					
					if(true) {
						list.setSelectedIndex(library.getItems().indexOf(lastBook));
						setBookDate(txtField, fieldType, book);
						lastBook = null;
					}
					
					
					
					
					// If you have a selected book
					
					Book book = library.getBook(list.getSelectedIndex());
					if(book != null) {
						txtPubDateYear.setText(getDateStr(book.getPubDate().get(Calendar.YEAR)));
						txtPubDateMonth.setText(getDateStr(book.getPubDate().get(Calendar.MONTH)));
						txtPubDateDay.setText(getDateStr(book.getPubDate().get(Calendar.DAY_OF_MONTH)));
					}
					setDetailsVisible(true);
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
					lastBook = library.getBook(list.getSelectedIndex());
					
					
					
					
					
					
					
					
					
					
					
					
				}
			}
		});
	}

	private String getDateStr(int intDate)
	{
		return (intDate < 10) ? "0" + intDate : "" + intDate;
	}
	
	private void addEditBook(int editingBook)
	{
		boolean edit = editingBook == EDIT_BOOK;
		Book book = null, oldBook = null;
		
		if(edit) { // Need to get the default type
			oldBook = library.getBook(list.getSelectedIndex());
		}
		String choice = (String) JOptionPane.showInputDialog(this, "Please select a book type:", "Type Selection", JOptionPane.PLAIN_MESSAGE, null, TYPES, (edit) ? oldBook.getType() : TYPES[0]);
		if(choice != null) { // Cancel not pressed
			switch(choice) {
				case "Fictional":	book = new FictionalBook();
									break;
				case "History":		book = new HistoryBook();
									break;
				case "Textbook":	book = new TextBook();
			}
			if(edit) { // Need to get the fields from the original
				book.setTitle(oldBook.getTitle());
				book.setAuthor(oldBook.getAuthor());
				book.setPublisher(oldBook.getPublisher());
				book.setPubDate(oldBook.getPubDate());
				book.setPrice(oldBook.getPrice());
				book.setInfoValue(oldBook.getInfoValue());
				txtInfo.setText("");
				library.removeBook(oldBook);
			}
			
			if(book != null) { // book should never be null as we limit the options in the dropdown
				library.addBook(book);
				list.setSelectedIndex(list.getModel().getSize() -1);
				setFieldsEditable(true);
			}
		}
	}
	
	private void removeBook()
	{
		Object[] options = {"Confirm", "Cancel"};
		int option = JOptionPane.showOptionDialog(this, "Are you sure you want to remove this book?", "Remove Book", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(option == JOptionPane.YES_OPTION) {
			if (list.getSelectedIndex() > -1) { // Shouldn't happen
				library.removeBook(library.getItems().get(list.getSelectedIndex()));
			}
			list.setSelectedIndex(list.getModel().getSize() -1);
		}
	}
	
	private void selectFile(int dialogChoice)
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Book Library File", "blf");
	    JFileChooser fc = new JFileChooser();
	    fc.setFileFilter(filter);
	    fc.setAcceptAllFileFilterUsed(false);
	    int action = -1; // Non-value
		if(dialogChoice == SAVE_DIALOG) {
			action = fc.showSaveDialog(this);
		} else if(dialogChoice == OPEN_DIALOG) {
			action = fc.showOpenDialog(this);
		}
	    
	    if(action == JFileChooser.APPROVE_OPTION) {
	    	File file = fc.getSelectedFile();
	    	if(!file.getName().endsWith(".blf")) {
	    		file = new File(file + ".blf");
	    	}
	    	String path = file.getAbsolutePath();
	    	LibraryReaderWriter lrw = new LibraryReaderWriter(path);
	    	if(dialogChoice == SAVE_DIALOG) {
	    		saveLibrary(lrw, path, file.getName());
	    	} else if(dialogChoice == OPEN_DIALOG) {
	    		openLibrary(lrw, path, file.getName());
	    	}
	    } else {
	    	//TODO log file?
	    }
	}
	
	private void saveLibrary(LibraryReaderWriter lrw, String path, String fileName)
	{
    	try {
    		lrw.writeObjects(library.getItems());
    	} catch(IOException ioe) {
    		fatalException(ioe);
    	}
	}
	
	private void openLibrary(LibraryReaderWriter lrw, String path, String fileName)
	{
		List<Book> books = library.getItems();
    	try {
    		library.replaceBooks(lrw.readObjects());
    	} catch(IOException ioe) { // Closing stream fail
    		fatalException(ioe);
    	} catch(NullPointerException npe) {
    		String message = "Error opening file: ";
    		String extension = fileName.split("\\.")[1];
    		if(extension.equals("blf")) {
    			message += "Library corrupt or deleted.";
    		} else {
    			message += "Not a Book Library File.";
    		}
    		JOptionPane.showMessageDialog(this, message, "File Error", JOptionPane.WARNING_MESSAGE);
    		library.replaceBooks(books); // Reopen original library
    	}
	}
	
	private void closeLibrary()
	{
		if(library.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You have no library open", "Empty library", JOptionPane.INFORMATION_MESSAGE);
		} else {
			String message = "If you close the library you will lose any "
					+ "unsaved changes! Do you wish to save?";
			int choice = JOptionPane.showConfirmDialog(this, message, 
					"Close Library", JOptionPane.YES_NO_CANCEL_OPTION);
			
			switch(choice) {
				case JOptionPane.YES_OPTION:	selectFile(SAVE_DIALOG);
				case JOptionPane.NO_OPTION:		library.replaceBooks(new ArrayList<Book>());
			}
		}
	}
	
	private void setBookDate(JTextField field, int fieldType, Book book)
	{
		boolean success = true;
		GregorianCalendar temp = new GregorianCalendar();
		
		temp.setLenient(false);
		try {
			temp.set(fieldType, Integer.parseInt(field.getText()));
		} catch(NumberFormatException e) {
			success = false;
			dateParseError(book);
		}
		try {
			temp.getTime();
		} catch(IllegalArgumentException e) {
			success = false;
			dateParseError(book);
		}
		if(success && book != null) {
			book.getPubDate().set(fieldType, temp.get(fieldType));
		}
	}
	
	private void setDetailsVisible(boolean isVisible)
	{
		for(Component component : detailsPanel.getComponents()) {
			component.setVisible(isVisible);
			if(component.getName() != null && component.getName().equals("pnlControls")) {
				component.setVisible(true);
			}
		}
	}
	
	private void setFieldsEditable(boolean isEditable)
	{
		for(Component component : detailsPanel.getComponents()) {
			if(component instanceof JTextField && !component.equals(txtType)) {
				JTextField textField = (JTextField) component;
				textField.setEditable(isEditable);
			}
			if(component instanceof JPanel) {
				JPanel panel = (JPanel) component; 
				for(Component txtComponent : panel.getComponents()) {
					if(txtComponent instanceof JTextField) {
						JTextField textField = (JTextField) txtComponent;
						textField.setEditable(isEditable);	
					}
				}
			}
		}	
	}
	
	private void showAbout()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version 0.1"); //TODO version number from package?
	}
	
	// Convert the exception to a stack trace and display in a dialog
	private void fatalException(Exception e)
	{
		StringBuilder sb = new StringBuilder(e.toString());
	    for (StackTraceElement ste : e.getStackTrace()) {
	        sb.append("\n\tat ");
	        sb.append(ste);
	    }
		JOptionPane.showMessageDialog(this, sb.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
	
	private void dateParseError(Book book)
	{
		txtPubDateYear.setText(book.getPubDate().get(Calendar.YEAR) + "");
		txtPubDateMonth.setText(book.getPubDate().get(Calendar.MONTH) + "");
		txtPubDateDay.setText(book.getPubDate().get(Calendar.DAY_OF_MONTH) + "");
		String message = "Please enter in the correct format: DD-MM-YYYY\nThe book's date has not been updated!";
		JOptionPane.showMessageDialog(this, message, "Date format error", JOptionPane.ERROR_MESSAGE);
	}
	
	@SuppressWarnings("rawtypes")
	protected void initDataBindings() {
		BeanProperty<Library, List<Book>> libraryBeanProperty = BeanProperty.create("items");
		JListBinding<Book, Library, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty, list);
		//
		ELProperty<Book, Object> bookEvalutionProperty = ELProperty.create("${title} - ${author}");
		jListBinding.setDetailBinding(bookEvalutionProperty);
		//
		jListBinding.bind();
		//
		BeanProperty<JList, String> jListBeanProperty = BeanProperty.create("selectedElement.title");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty, txtTitle, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_1 = BeanProperty.create("selectedElement.author");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_1, txtAuthor, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_2 = BeanProperty.create("selectedElement.publisher");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_2, txtPublisher, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_3 = BeanProperty.create("selectedElement.type");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_3, txtType, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<JList, Double> jListBeanProperty_4 = BeanProperty.create("selectedElement.price");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JList, Double, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_4, txtPrice, jTextFieldBeanProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_8 = BeanProperty.create("selectedElement.infoLabel");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JLabel, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_8, lblInfo, jLabelBeanProperty);
		autoBinding_8.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_9 = BeanProperty.create("selectedElement.infoValue");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_9 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_9, txtInfo, jTextFieldBeanProperty_9);
		autoBinding_9.bind();
	}
	
	private class MyFocusListener implements FocusListener
	{

		JTextField txtField;
		int fieldType;
		Book book;
		
		public MyFocusListener(JTextField txtField, int fieldType, Book book)
		{
			this.txtField = txtField;
			this.fieldType = fieldType;
			this.book = book;
		}
		
		@Override
		public void focusGained(FocusEvent e)
		{
			book = library.getBook(list.getSelectedIndex());
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			setBookDate(txtField, fieldType, book);
			book = null;
		}
		
	}
}