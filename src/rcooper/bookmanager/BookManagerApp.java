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
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem newBook, openBook, closeBook, about, readMe;
	private JTabbedPane tabbedPane;
	private JScrollPane listPane;
	private JPanel detailsView, reportView, detailsPanel, 
		controlPanel, filler;
	private JLabel titleLabel, authorLabel, publisherLabel, pubDateLabel,
		priceLabel, typeLabel, genreLabel, periodLabel, subjectLabel;
	private JTextField titleField, authorField, publisherField, pubDateField,
		priceField, typeField, genreField, periodField, subjectField, 
		addTitleField, addAuthorField, addPublisherField, addPubDateField,
		addPriceField, addTypeField, addGenreField, addPeriodField,
		addSubjectField;
	private JList<String> list;
	private JButton addButton, editButton, removeButton;
	private JDialog addBookDialog;
	
	private Library library;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
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
//		public FictionalBook(int id, String title, String author, String publisher,
//				String pubDate, double retailPrice, int type, String genre)
		library.addBook(new FictionalBook(library.getCount(), "Soul Music", "Terry Pratchett", "P.W. Books", "20.01.1999", 5.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new FictionalBook(library.getCount(), "The Color of Magic", "Terry Pratchett", "TS Publishing", "09.05.1992", 4.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new FictionalBook(library.getCount(), "The Light Fantastic", "Terry Pratchett", "BooksALot", "12.02.1987", 6.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new TextBook(library.getCount(), "Java For Beginners", "A Dude", "BooksALot", "12.02.1987", 6.50 , "Textbook", "Magic"));
		library.addBook(new HistoryBook(library.getCount(), "The History of Design Patterns", "Some O. Dude", "BooksALot", "12.02.1987", 6.50 , "History", "20th Century"));
		//// Frame menu bar ////
		menuBar = new JMenuBar();
		
		//// Menu bar menus ////
		file = new JMenu("File");
		help = new JMenu("Help");
		
		//// File menu items ////
		newBook = new JMenuItem("New");
		openBook = new JMenuItem("Open...");
		closeBook = new JMenuItem("Close");
		
		//// Help menu items ////
		readMe = new JMenuItem("View Help");
		about = new JMenuItem("About Book Manager");
		
		//// app child ////
		tabbedPane = new JTabbedPane();
		
		//// Tabs ////
		detailsView = new JPanel(new GridBagLayout());
		reportView = new JPanel();
		
		//// List pane stuff ////
		list = new JList<String>();
		
		//// detailsView children ////
		listPane = new JScrollPane();
		detailsPanel = new JPanel(new GridBagLayout());
		controlPanel = new JPanel(new GridBagLayout());
		
		//// detailsPanel children ////
		typeLabel = new JLabel("Type:");
		titleLabel = new JLabel("Title:");
		authorLabel = new JLabel("Author:");
		publisherLabel = new JLabel("Publisher:");
		pubDateLabel = new JLabel("Publication Date:");
		priceLabel = new JLabel("Retail Price:");
		genreLabel = new JLabel("Genre:");
		periodLabel = new JLabel("Period:");
		subjectLabel = new JLabel("Subject:");
		typeField = new JTextField();
		titleField = new JTextField();
		authorField = new JTextField();
		publisherField = new JTextField();
		pubDateField = new JTextField();
		priceField = new JTextField();
		genreField = new JTextField();
		periodField = new JTextField();
		subjectField = new JTextField();
		filler = new JPanel();
		
		//// buttonPanel children ////
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		removeButton = new JButton("Remove");
		
		//// Set Up Elements ////
		setFrameProperties(); // app
		setUpMenu(); // app.menuBar setup
		setUpTabs(); // app.tabbedPane setup
		list.setFont(new Font("Arial", Font.PLAIN, 10)); // list
		setUpDetailsView(); // app.tabbedPane.detailsView setup
		
		pack();
		initDataBindings();
	}
	
	private void setFrameProperties()
	{
		setTitle("Book Manager");
		setSize(800, 600);
		setMinimumSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane( tabbedPane );
	}
	
	private void setUpMenu()
	{
		menuBar.add(file);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(help);
		
		//// app.menuBar.file setup ////
		file.add(newBook);
		file.add(openBook);
		file.add(closeBook);
		
		//// app.menuBar.help setup ////
		help.add(readMe);
		help.add(about);

		//// app.menuBar.help.about setup ////
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				showAbout();
			}
		});
	}
	
	private void setUpTabs()
	{
		tabbedPane.addTab("Details View", detailsView);
		tabbedPane.addTab("Report View", reportView);
	}
	
	private void setUpDetailsView()
	{		
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 2, 0.25, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
		detailsView.add(listPane, gbc);
		
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0.75;
		detailsView.add(detailsPanel, gbc);
		
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 1;
		gbc.weighty = 0.2;
		detailsView.add(controlPanel, gbc);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		listPane.setViewportView( list );
		
		//// app.tabbedPane.detailsView.detailsPanel setup ////
		
		addDetailLabels();
		
		addDetailFields();
		
		// Filler
		addDetailFiller();
		
		// Set bulk properties for text containers
		Component[] components = detailsPanel.getComponents();
		ArrayList<JComponent> detailsComponents = new ArrayList<JComponent>();
		for(Component component : components) {
			detailsComponents.add((JComponent) component);
		}
		for(JComponent component : detailsComponents) {
			if(!(component instanceof JPanel)) {
				component.setFont(new Font("Arial", Font.PLAIN, 14));
				if(component instanceof JTextField) {
					JTextField textComp = (JTextField) component;
					textComp.setEditable(false);
				}
			}
		}
		
		//// app.tabbedPane.detailsView.controlPanel setup ////
		addDetailControls();
	}
	
	private void addDetailLabels()
	{
		JLabel[] leftLabels = {titleLabel, authorLabel, publisherLabel,
				genreLabel, subjectLabel};
		JLabel[] rightLabels = {typeLabel, priceLabel, null, pubDateLabel, 
				periodLabel};

		// Left labels
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		
		for(int i = 0; i < 5; i++) {
			gbc.gridy = i;
			detailsPanel.add(leftLabels[i], gbc);
		}
		
		// Right labels
		gbc.gridx = 2;
		for(int i = 0; i < 5; i++) {
			if(i != 2) {
				gbc.gridy = i;
				detailsPanel.add(rightLabels[i], gbc);
			}
		}
	}
	
	private void addDetailFields()
	{
		// Right fields
		GridBagConstraints gbc = new GridBagConstraints(3, 0, 1, 1, 0.2, 1,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0);
		detailsPanel.add(typeField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 1;
		detailsPanel.add(priceField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 3;
		detailsPanel.add(pubDateField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 4;
		detailsPanel.add(periodField, gbc);
		
		// Left fields
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 1;
		gbc.weightx = 0.5;
		gbc.gridy = 0;
		detailsPanel.add(titleField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 1;
		detailsPanel.add(authorField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 2;
		detailsPanel.add(publisherField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 3;
		detailsPanel.add(genreField, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridy = 4;
		detailsPanel.add(subjectField, gbc);
	}
	
	private void addDetailFiller()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=5;
		gbc.gridy=0;
		gbc.weightx = 0.1;
		gbc.gridheight = 5;
		detailsPanel.add(filler, gbc);
	}
	
	private void addDetailControls()
	{
		Insets buttonInsets = new Insets(5, 10, 5, 10);
		addButton.setMargin(buttonInsets);
		addButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addBook();
			}
		});
		editButton.setMargin(buttonInsets);
		editButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				editBook();
			}
		});
		
		removeButton.setMargin(buttonInsets);
		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				removeBook();
			}
		});
		
		editButton.setEnabled(false);
		removeButton.setEnabled(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.3;
		controlPanel.add(addButton, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 1;
		controlPanel.add(editButton, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 2;
		controlPanel.add(removeButton, gbc);
	
		list.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				String bookType = "";
				
				editButton.setEnabled(true);
				removeButton.setEnabled(true);
				
				try {
					bookType = getSelectedBook().getType(); 
				} catch(NullPointerException npe) {}
				
				if(!bookType.equals("Fictional")) {
					genreField.setText("N/A");
					genreField.setEnabled(false);
				} else {
					genreField.setEnabled(true);
				}
				if(!bookType.equals("Textbook")) {
					subjectField.setText("N/A");
					subjectField.setEnabled(false);
				} else {
					subjectField.setEnabled(true);
				}
				if(!bookType.equals("History")) {
					periodField.setText("N/A");
					periodField.setEnabled(false);
				} else {
					periodField.setEnabled(true);
				}
			}
		});
	}
	
	private Book getSelectedBook()
	{
		Book book = null;
		if( list.getSelectedIndex() != -1) {
			book = library.getBook(list.getSelectedIndex());
		}
		return book;
	}
	
	private void showAbout()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version 0.1");
	}
	
	private void addBook()
	
	{
		addBookDialog = new JDialog();
		addBookDialog.setTitle("Add Book");
		addBookDialog.setSize(640, 480);
		addBookDialog.setLocationRelativeTo(this);
		addBookDialog.getContentPane().setLayout(new GridBagLayout());
		addBookDialog.setVisible(true);
		
		JLabel typeLabel = new JLabel("Type:");
		JLabel titleLabel = new JLabel("Title:");
		JLabel authorLabel = new JLabel("Author:");
		JLabel publisherLabel = new JLabel("Publisher:");
		JLabel pubDateLabel = new JLabel("Publication Date:");
		JLabel priceLabel = new JLabel("Retail Price:");
		JLabel genreLabel = new JLabel("Genre:");
		JLabel periodLabel = new JLabel("Period:");
		JLabel subjectLabel = new JLabel("Subject:");
		JComboBox typeField = new JComboBox<String>();
		addTitleField = new JTextField();
		addAuthorField = new JTextField();
		addPublisherField = new JTextField();
		addPubDateField = new JTextField();
		addPriceField = new JTextField();
		addGenreField = new JTextField();
		addPeriodField = new JTextField();
		addSubjectField = new JTextField();
		JPanel filler = new JPanel();
		
		//// app.tabbedPane.detailsView.setup ////
		JLabel[] leftLabels = {titleLabel, authorLabel, publisherLabel,
				genreLabel, subjectLabel};
		JLabel[] rightLabels = {typeLabel, priceLabel, null, pubDateLabel, 
				periodLabel};
		
		// Left labels
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		
		for(int i = 0; i < 5; i++) {
			gbc.gridy = i;
			getContentPane().add(leftLabels[i], gbc);
		}
		
		// Right labels
		gbc.gridx = 2;
		for(int i = 0; i < 5; i++) {
			if(i != 2) {
				gbc.gridy = i;
				getContentPane().add(rightLabels[i], gbc);
			}
		}
		
		// Right fields
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3;
		gbc.weightx = 2;
		gbc.weighty = 1;
		getContentPane().add(typeField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.weightx = 2;
		gbc.weighty = 1;
		getContentPane().add(priceField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.weightx = 2;
		gbc.weighty = 1;
		getContentPane().add(pubDateField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.weightx = 2;
		gbc.weighty = 1;
		getContentPane().add(periodField, gbc);
		
		// Left fields
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.weightx = 4;
		gbc.gridy = 0;
		getContentPane().add(titleField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 4;
		getContentPane().add(authorField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.weightx = 4;
		gbc.gridy = 2;
		getContentPane().add(publisherField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.weightx = 4;
		gbc.gridy = 3;
		getContentPane().add(genreField, gbc);
		gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 4;
		getContentPane().add(subjectField, gbc);
		
		// Filler
		gbc = new GridBagConstraints();
		gbc.gridx=5;
		gbc.gridy=0;
		gbc.weightx = 1;
		gbc.gridheight = 5;
		getContentPane().add(filler, gbc);
	}
	
	private void editBook()
	{
		
	}
	
	private void removeBook()
	{
		library.removeBook(getSelectedBook());
	}
	
	protected void initDataBindings() {
		BeanProperty<Library, List<Book>> libraryBeanProperty = BeanProperty.create("items");
		JListBinding<Book, Library, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty, list);
		//
		ELProperty<Book, Object> bookEvalutionProperty = ELProperty.create("${title} - ${author}");
		jListBinding.setDetailBinding(bookEvalutionProperty);
		//
		jListBinding.bind();
		//
		BeanProperty<JList, String> jListBeanProperty = BeanProperty.create("selectedElement.type");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty, typeField, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JList, Double> jListBeanProperty_1 = BeanProperty.create("selectedElement.retailPrice");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JList, Double, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_1, priceField, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_2 = BeanProperty.create("selectedElement.publicationDate");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_2, pubDateField, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<JList, Integer> jListBeanProperty_3 = BeanProperty.create("selectedElement.period");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JList, Integer, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_3, periodField, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_4 = BeanProperty.create("selectedElement.title");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_4, titleField, jTextFieldBeanProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_5 = BeanProperty.create("selectedElement.author");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_5, authorField, jTextFieldBeanProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_6 = BeanProperty.create("selectedElement.publisher");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_6, publisherField, jTextFieldBeanProperty_6);
		autoBinding_6.bind();
		//
		BeanProperty<JList, Integer> jListBeanProperty_7 = BeanProperty.create("selectedElement.genre");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_7 = BeanProperty.create("text");
		AutoBinding<JList, Integer, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_7, genreField, jTextFieldBeanProperty_7);
		autoBinding_7.bind();
		//
		BeanProperty<JList, Integer> jListBeanProperty_8 = BeanProperty.create("selectedElement.subject");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_8 = BeanProperty.create("text");
		AutoBinding<JList, Integer, JTextField, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_8, subjectField, jTextFieldBeanProperty_8);
		autoBinding_8.bind();
	}
}