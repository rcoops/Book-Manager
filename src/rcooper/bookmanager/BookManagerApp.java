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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import rcooper.bookmanager.model.Book;
import rcooper.bookmanager.model.FictionalBook;
import rcooper.bookmanager.model.HistoryBook;
import rcooper.bookmanager.model.Library;
import rcooper.bookmanager.model.TextBook;

public class BookManagerApp extends JFrame
{
	
	private static final long serialVersionUID = 1L;
	private final int SAVE_DIALOG = 0, OPEN_DIALOG = 1;
	private final Object[] TYPES = {"Fictional", "History", "Textbook"};
	
	private JPanel detailsPanel;
	private JLabel lblInfo;
	private JTextField txtTitle, txtAuthor, txtPublisher, txtPubDate, txtPrice, txtInfo, txtType;
	private JList<String> list;
	private JButton btnEdit, btnRemove;
	
	private Library library;
	
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
		
		library.addBook(new FictionalBook("The Colour of Magic", "Terry Pratchett", "Corgi", "18.01.1985", 5.50 , "Fantasy/Comedy"));
		library.addBook(new TextBook("Objects First with Java", "David Barnes & Michael Kölling", "Pearson", "30.09.2011", 59.99 , "Java Programming"));
		library.addBook(new HistoryBook("SPQR: A history of Ancient Rome", "Professor Mary Beard", "Profile Books", "20.10.2015", 17.00 , "Ancient History"));
		setUpFrame(new JTabbedPane(), new JMenuBar()); // app
		
		pack();
		initDataBindings();
	}
	
	private void setUpFrame(JTabbedPane mainPane, JMenuBar menuBar)
	{
		setTitle("Book Manager");
		setSize(800, 600);
		setMinimumSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane(mainPane);
		setUpMenu(menuBar); // app.menuBar setup
		setUpTabs(mainPane); // app.tabbedPane setup
	}
	
	private void setUpMenu(JMenuBar menuBar)
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
	
	private void setUpTabs(JTabbedPane mainPane)
	{
		JSplitPane detailsView = new JSplitPane();
		JPanel reportView = new JPanel();
		
		mainPane.addTab("Details View", detailsView);
		mainPane.addTab("Report View", reportView);
		
		setUpDetailsView(detailsView, reportView);
	}
	
	private void setUpDetailsView(JSplitPane detailsView, JPanel reportView)
	{
		detailsPanel = new JPanel();
		JScrollPane listPane = new JScrollPane();
		
		detailsView.setLeftComponent(listPane);
		detailsView.setRightComponent(detailsPanel);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		setListProperties(listPane);
		
		//// app.tabbedPane.detailsView.detailsPanel setup ////
		setDetailsPanel();
	}
	
	private void setDetailsPanel()
	{
		JLabel lblType = new JLabel("Type:");
		JLabel lblTitle = new JLabel("Title:");
		JLabel lblAuthor = new JLabel("Author:");
		JLabel lblPublisher = new JLabel("Publisher:");
		JLabel lblPubDate = new JLabel("Publication Date:");
		JLabel lblPrice = new JLabel("Retail Price:");
		JPanel pnlControls = new JPanel(new GridBagLayout());
		
		lblInfo = new JLabel();
		txtType = new JTextField();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDate = new JTextField();
		txtPrice = new JTextField();
		txtInfo = new JTextField();

		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[] {0.15, 0.4, 0.3, 0.15};
		layout.rowWeights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
		detailsPanel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		detailsPanel.add(lblTitle, gbc);
		detailsPanel.add(lblAuthor, gbc);
		detailsPanel.add(lblPublisher, gbc);
		detailsPanel.add(lblInfo, gbc);
		gbc.gridx = 2;
		detailsPanel.add(lblType, gbc);
		detailsPanel.add(lblPrice, gbc);
		detailsPanel.add(lblPubDate, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		detailsPanel.add(txtTitle, gbc);
		detailsPanel.add(txtAuthor, gbc);
		detailsPanel.add(txtPublisher, gbc);
		detailsPanel.add(txtInfo, gbc);
		
		gbc.insets = new Insets(0,0,0,35);
		gbc.gridx = 3;
		detailsPanel.add(txtType, gbc);
		detailsPanel.add(txtPrice, gbc);
		detailsPanel.add(txtPubDate, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.BOTH;
		detailsPanel.add(pnlControls, gbc);
		
		txtType.setEditable(false);
		setDetailsVisible(false);
		setFieldsEditable(false);
		addDetailControls(pnlControls);
	}
	
	private void addDetailControls(JPanel pnlControls)
	{
		JButton btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnRemove = new JButton("Remove");
		
		Insets buttonInsets = new Insets(10, 20, 10, 20);
		btnAdd.setMargin(buttonInsets);
		btnAdd.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				addBook();
			}
			
		});
		
		btnEdit.setMargin(buttonInsets);
		btnEdit.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				editBook();
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
		gbc.gridx = 1;
		pnlControls.add(btnEdit, gbc);
		gbc.gridx = 2;
		pnlControls.add(btnRemove, gbc);
	}
	
	private void setListProperties(JScrollPane listPane)
	{
		list = new JList<String>();
		listPane.setViewportView( list );

		list.setFont(new Font("Arial", Font.PLAIN, 10));
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				setFieldsEditable(false);
				
				if(list.getSelectedIndex() == -1) {
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
					setDetailsVisible(true);
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
				}
			}
		});
	}
	
	private void showAbout()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version 0.1"); //TODO version number from package?
	}
	
	private void addBook()
	{
		Book book = null;
		String choice = (String) JOptionPane.showInputDialog(this, "Please select a book type:", "Type Selection", JOptionPane.PLAIN_MESSAGE, null, TYPES, TYPES[1]);
		try {
			switch(choice) {
				case "Fictional":	book = new FictionalBook();
									break;
				case "History":		book = new HistoryBook();
									break;
				case "Textbook":	book = new TextBook();
									break;
			}
		} catch(NullPointerException npe) {} // Dealt with
		
		if(book != null) {
			library.addBook(book);
			list.setSelectedIndex(list.getModel().getSize() -1);
			editBook();
		}
	}
	
	private void editBook()
	{
		setFieldsEditable(true);
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
    			message = message + "Library corrupt or deleted.";
    		} else {
    			message = message + "Not a Book Library File.";
    		}
    		JOptionPane.showMessageDialog(this, message, "File Error", JOptionPane.WARNING_MESSAGE);
    		library.replaceBooks(books); // Reopen original library
    	}
	}
	
	private void closeLibrary()
	{
		String message = "If you close the library you will lose any "
				+ "unsaved changes! Do you wish to save?";
		int choice = JOptionPane.showConfirmDialog(this, message, 
				"Close Library", JOptionPane.YES_NO_CANCEL_OPTION);
		
		switch(choice) {
			case JOptionPane.YES_OPTION:	selectFile(SAVE_DIALOG);
			case JOptionPane.NO_OPTION:		library.replaceBooks(new ArrayList<Book>());
		}
	}
	
	// Convert the exception to a stack trace and display in a dialog
	private void fatalException(Exception e)
	{
		StringBuilder sb = new StringBuilder(e.toString());
	    for (StackTraceElement ste : e.getStackTrace()) {
	        sb.append("\n\tat ");
	        sb.append(ste);
	    }
		JOptionPane.showMessageDialog(this, sb.toString(), "Fatal Error", JOptionPane.WARNING_MESSAGE);
		System.exit(1);
	}
	
	private void setDetailsVisible(boolean isVisible) {
		for(Component component : detailsPanel.getComponents()) {
			component.setVisible(isVisible);
			if(component instanceof JPanel) {
				component.setVisible(true);
			}
		}
	}
	
	private void setFieldsEditable(boolean isEditable)
	{
		for(Component component : detailsPanel.getComponents()) {
			if(component instanceof JTextField && !component.equals(txtType)) {
				JTextField textField = (JTextField) component;
				if(isEditable) {
					textField.setEditable(true);
				} else {
					textField.setEditable(false);
				}
			}
		}
		
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
		BeanProperty<JList, String> jListBeanProperty_5 = BeanProperty.create("selectedElement.pubDate");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_5, txtPubDate, jTextFieldBeanProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_6 = BeanProperty.create("selectedElement.infoLabel");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JLabel, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_6, lblInfo, jLabelBeanProperty);
		autoBinding_6.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_7 = BeanProperty.create("selectedElement.infoValue");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_7, txtInfo, jTextFieldBeanProperty_6);
		autoBinding_7.bind();
	}
}