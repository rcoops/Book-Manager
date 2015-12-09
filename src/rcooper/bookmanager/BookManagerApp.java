package rcooper.bookmanager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
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

import rcooper.bookmanager.converters.DateConverter;
import rcooper.bookmanager.converters.ListDateConverter;
import rcooper.bookmanager.converters.PriceConverter;
import rcooper.bookmanager.model.Book;
import rcooper.bookmanager.model.FictionalBook;
import rcooper.bookmanager.model.HistoryBook;
import rcooper.bookmanager.model.Library;
import rcooper.bookmanager.model.TextBook;

public class BookManagerApp extends JFrame
{
	
	private static final long serialVersionUID = -3904857322897679210L;
	private final int SAVE_DIALOG = 0, OPEN_DIALOG = 1;
	private final Object[] BOOK_TYPES = {"Fictional", "History", "Textbook"};
	private final static String VERSION = "0.7";
	private JMenuItem mnIApplyFilter, mnIRemoveFilter;
	private JPanel detailsPanel;
	private JScrollPane listPane;
	private JLabel lblInfo, valTotalBooks, valTotalVal, valTotalFict, valTotalHist, valTotalText, lblFilterApplied;
	private JTextField txtTitle, txtAuthor, txtPublisher, txtPrice, txtInfo, txtType, txtPubDate;
	private JButton btnAdd, btnEdit, btnRemove;
	private JList<String> list, lstAuthors, lstPublishers;
	private JList<Date> lstPubDates;
	private Library library;
	private List<Book> temp;
	
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
	
	/* INITIALISATION */
	
	public BookManagerApp()
	{
		library = new Library();
		library.addBook(new FictionalBook("Treasure Island", "Robert Louis Stevenson", "Cassel & Co.", new GregorianCalendar(1883, 11, 14), 599 , "Historical Fiction"));
		library.addBook(new FictionalBook("Robinson Crusoe", "Daniel Defoe", "W. Taylor", new GregorianCalendar(1719, 4, 25), 599 , "Historical Fiction"));
		library.addBook(new FictionalBook("The Colour of Magic", "Terry Pratchett", "Corgi", new GregorianCalendar(1984, 1, 18), 550 , "Fantasy/Comedy"));
		library.addBook(new TextBook("Objects First with Java", "David Barnes & Michael Kolling", "Pearson", new GregorianCalendar(2011, 9, 30), 5999 , "Java Programming"));
		library.addBook(new TextBook("Levison's Textbook for Dental Nurses", "Carole Hollins", "Wiley-Blackwell", new GregorianCalendar(2013, 7, 5), 2564 , "Dentistry"));
		library.addBook(new TextBook("Equilibrium Thermodynamics", "Mario J. de Oliveira", "Springer", new GregorianCalendar(2013, 5, 17), 6799, "Physics"));
		library.addBook(new HistoryBook("SPQR: A history of Ancient Rome", "Professor Mary Beard", "Profile Books", new GregorianCalendar(2015, 10, 20), 1700 , "Ancient History"));
		library.addBook(new HistoryBook("The Secret War: Spies, Codes and Guerillas 1939-1945", "Max Hastings", "William Collins", new GregorianCalendar(2015, 9, 10), 1199 , "20th Century"));
		library.addBook(new HistoryBook("The Brother Gardeners: Botany, Empire and the Birth of an Obsession", "Andrea Wulf", "Windmill Books", new GregorianCalendar(2009, 2, 5), 998 , "18th Century"));
		
		init(new JTabbedPane(), new JMenuBar()); 
		
		pack();
		initDataBindings();
	}
	
	private void init(JTabbedPane mainPane, JMenuBar menuBar)
	{
		setTitle("Book Manager");
		setSize(800, 600);
		setMinimumSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane(mainPane);
		initTabs(mainPane); // app.tabbedPane setup
		initMenu(menuBar); // app.menuBar setup
	}
	
	private void initMenu(JMenuBar menuBar)
	{
		//// Menu bar menus ////
		JMenu mnFile = new JMenu("File");
		JMenu mnSearch = new JMenu("Search");
		JMenu mnHelp = new JMenu("Help");
		
		//// File menu items ////
		JMenuItem mnIOpen = new JMenuItem("Open...");
		JMenuItem mnISave = new JMenuItem("Save");
		JMenuItem mnIClose = new JMenuItem("Close");
		
		//// Search menu item ////
		mnIApplyFilter = new JMenuItem("Apply Filter");
		mnIRemoveFilter = new JMenuItem("Remove Filter");
		
		//// Help menu items ////
		JMenuItem mnIReadMe = new JMenuItem("View Help");
		JMenuItem mnIAbout = new JMenuItem("About Book Manager");
		
		//// app.menuBar setup ////
		menuBar.add(mnFile);
		menuBar.add(mnSearch);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnHelp);
		
		//// app.menuBar.file setup ////
		mnFile.add(mnIOpen);
		mnFile.add(mnISave);
		mnFile.add(mnIClose);

		//// app.menuBar.file setup ////
		mnSearch.add(mnIApplyFilter);
		mnSearch.add(mnIRemoveFilter);
		
		//// app.menuBar.help setup ////
		mnHelp.add(mnIReadMe);
		mnHelp.add(mnIAbout);

		mnIRemoveFilter.setEnabled(false);
		
		initActionListeners(mnIOpen, mnISave, mnIClose, mnIApplyFilter, mnIRemoveFilter, mnIReadMe, mnIAbout);
	}
	
	private void initTabs(JTabbedPane mainPane)
	{
		JSplitPane detailsView = new JSplitPane();
		JPanel reportView = new JPanel();
		
		mainPane.addTab("Details View", detailsView);
		mainPane.addTab("Report View", reportView);
		
		initDetailsView(detailsView);
		initReportView(reportView);
	}
	
	private void initDetailsView(JSplitPane detailsView)
	{
		detailsPanel = new JPanel();
		listPane = new JScrollPane();
		
		detailsView.setLeftComponent(listPane);
		detailsView.setRightComponent(detailsPanel);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		initListPane(listPane);
		
		lblFilterApplied = new JLabel();
		
		lblFilterApplied.setVisible(false);
		
		//// app.tabbedPane.detailsView.detailsPanel setup ////
		initDetailsPanel();
	}
	
	private void initReportView(JPanel reportView)
	{
		lstAuthors = new JList<String>();
		lstPublishers = new JList<String>();
		lstPubDates = new JList<Date>();
		valTotalBooks = new JLabel("totalBooks");
		valTotalFict = new JLabel("totalFict");
		valTotalHist = new JLabel("totalHist");
		valTotalText = new JLabel("totalText");
		valTotalVal = new JLabel("totalVal");
		
		Font lblFont = new Font("Arial", Font.BOLD, 12);
		Font valFont = new Font("Arial", Font.PLAIN, 12);
		GridBagLayout gbl_reportView = new GridBagLayout();
		JScrollPane scrAuthors = new JScrollPane();
		JScrollPane scrPublishers = new JScrollPane();
		JScrollPane scrDates = new JScrollPane();
		JLabel lblAuthors = new JLabel("Authors");
		JLabel lblPublishers = new JLabel("Publishers");
		JLabel lblDates = new JLabel("Dates");
		JLabel lblTotalBooks = new JLabel("Total No. of Books:");
		JLabel lblTotalFict = new JLabel("Total No. of Fictional Books:");
		JLabel lblTotalHistory = new JLabel("Total No. of History Books:");
		JLabel lblTotalText = new JLabel("Total No. of Text Books:");
		JLabel lblTotalValue = new JLabel("Total Value of all Books:");

		reportView.setLayout(gbl_reportView);
		
		GridBagConstraints gbc_scrAuthors = new GridBagConstraints();
		gbc_scrAuthors.weighty = 1.0;
		gbc_scrAuthors.insets = new Insets(0, 0, 0, 5);
		gbc_scrAuthors.gridheight = 5;
		gbc_scrAuthors.fill = GridBagConstraints.BOTH;
		gbc_scrAuthors.weightx = 0.2;
		gbc_scrAuthors.gridx = 0;
		gbc_scrAuthors.gridy = 0;
		reportView.add(scrAuthors, gbc_scrAuthors);
		
		GridBagConstraints gbc_scrPublishers = new GridBagConstraints();
		gbc_scrPublishers.weighty = 1.0;
		gbc_scrPublishers.insets = new Insets(0, 0, 0, 5);
		gbc_scrPublishers.weightx = 0.1;
		gbc_scrPublishers.gridheight = 5;
		gbc_scrPublishers.fill = GridBagConstraints.BOTH;
		gbc_scrPublishers.gridx = 1;
		gbc_scrPublishers.gridy = 0;
		reportView.add(scrPublishers, gbc_scrPublishers);
		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 0.2;
		gbc_scrollPane.weightx = 0.2;
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		reportView.add(scrDates, gbc_scrollPane);
		
		GridBagConstraints gbc_lblTotalBooks = new GridBagConstraints();
		gbc_lblTotalBooks.anchor = GridBagConstraints.LINE_END;
		gbc_lblTotalBooks.weighty = 0.2;
		gbc_lblTotalBooks.weightx = 0.2;
		gbc_lblTotalBooks.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalBooks.gridx = 3;
		gbc_lblTotalBooks.gridy = 0;
		reportView.add(lblTotalBooks, gbc_lblTotalBooks);
		
		GridBagConstraints gbc_valTotalbooks = new GridBagConstraints();
		gbc_valTotalbooks.fill = GridBagConstraints.HORIZONTAL;
		gbc_valTotalbooks.anchor = GridBagConstraints.LINE_START;
		gbc_valTotalbooks.weighty = 0.2;
		gbc_valTotalbooks.weightx = 0.2;
		gbc_valTotalbooks.insets = new Insets(0, 0, 5, 0);
		gbc_valTotalbooks.gridx = 4;
		gbc_valTotalbooks.gridy = 0;
		reportView.add(valTotalBooks, gbc_valTotalbooks);
		
		GridBagConstraints gbc_lblTotalFict = new GridBagConstraints();
		gbc_lblTotalFict.anchor = GridBagConstraints.LINE_END;
		gbc_lblTotalFict.weighty = 0.2;
		gbc_lblTotalFict.weightx = 0.2;
		gbc_lblTotalFict.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalFict.gridx = 3;
		gbc_lblTotalFict.gridy = 1;
		reportView.add(lblTotalFict, gbc_lblTotalFict);
		
		GridBagConstraints gbc_valTotalFict = new GridBagConstraints();
		gbc_valTotalFict.fill = GridBagConstraints.HORIZONTAL;
		gbc_valTotalFict.anchor = GridBagConstraints.LINE_START;
		gbc_valTotalFict.weightx = 0.2;
		gbc_valTotalFict.weighty = 0.2;
		gbc_valTotalFict.insets = new Insets(0, 0, 5, 0);
		gbc_valTotalFict.gridx = 4;
		gbc_valTotalFict.gridy = 1;
		reportView.add(valTotalFict, gbc_valTotalFict);
		
		GridBagConstraints gbc_lblTotalHistory = new GridBagConstraints();
		gbc_lblTotalHistory.anchor = GridBagConstraints.LINE_END;
		gbc_lblTotalHistory.weighty = 0.2;
		gbc_lblTotalHistory.weightx = 0.2;
		gbc_lblTotalHistory.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalHistory.gridx = 3;
		gbc_lblTotalHistory.gridy = 2;
		reportView.add(lblTotalHistory, gbc_lblTotalHistory);
		
		GridBagConstraints gbc_valTotalHist = new GridBagConstraints();
		gbc_valTotalHist.fill = GridBagConstraints.HORIZONTAL;
		gbc_valTotalHist.anchor = GridBagConstraints.LINE_START;
		gbc_valTotalHist.weighty = 0.2;
		gbc_valTotalHist.weightx = 0.2;
		gbc_valTotalHist.insets = new Insets(0, 0, 5, 0);
		gbc_valTotalHist.gridx = 4;
		gbc_valTotalHist.gridy = 2;
		reportView.add(valTotalHist, gbc_valTotalHist);
		
		GridBagConstraints gbc_lblTotalText = new GridBagConstraints();
		gbc_lblTotalText.anchor = GridBagConstraints.LINE_END;
		gbc_lblTotalText.weighty = 0.2;
		gbc_lblTotalText.weightx = 0.2;
		gbc_lblTotalText.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalText.gridx = 3;
		gbc_lblTotalText.gridy = 3;
		reportView.add(lblTotalText, gbc_lblTotalText);
		
		GridBagConstraints gbc_valTotalText = new GridBagConstraints();
		gbc_valTotalText.anchor = GridBagConstraints.LINE_START;
		gbc_valTotalText.fill = GridBagConstraints.HORIZONTAL;
		gbc_valTotalText.weighty = 0.2;
		gbc_valTotalText.weightx = 0.2;
		gbc_valTotalText.insets = new Insets(0, 0, 5, 0);
		gbc_valTotalText.gridx = 4;
		gbc_valTotalText.gridy = 3;
		reportView.add(valTotalText, gbc_valTotalText);
		
		GridBagConstraints gbc_lblTotalValue = new GridBagConstraints();
		gbc_lblTotalValue.anchor = GridBagConstraints.LINE_END;
		gbc_lblTotalValue.weighty = 0.2;
		gbc_lblTotalValue.weightx = 0.2;
		gbc_lblTotalValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblTotalValue.gridx = 3;
		gbc_lblTotalValue.gridy = 4;
		reportView.add(lblTotalValue, gbc_lblTotalValue);
		
		GridBagConstraints gbc_valTotalVal = new GridBagConstraints();
		gbc_valTotalVal.anchor = GridBagConstraints.LINE_START;
		gbc_valTotalVal.fill = GridBagConstraints.HORIZONTAL;
		gbc_valTotalVal.weighty = 0.2;
		gbc_valTotalVal.weightx = 0.2;
		gbc_valTotalVal.gridx = 4;
		gbc_valTotalVal.gridy = 4;
		reportView.add(valTotalVal, gbc_valTotalVal);

		valTotalBooks.setFont(valFont);
		valTotalBooks.setHorizontalAlignment(SwingConstants.CENTER);
		valTotalBooks.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		valTotalFict.setFont(valFont);
		valTotalFict.setHorizontalAlignment(SwingConstants.CENTER);
		valTotalFict.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		valTotalHist.setFont(valFont);
		valTotalHist.setHorizontalAlignment(SwingConstants.CENTER);
		valTotalHist.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		valTotalText.setFont(valFont);
		valTotalText.setHorizontalAlignment(SwingConstants.CENTER);
		valTotalText.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		valTotalVal.setFont(valFont);
		valTotalVal.setHorizontalAlignment(SwingConstants.CENTER);
		valTotalVal.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		lblAuthors.setFont(new Font("Arial", Font.PLAIN, 14));
		lblAuthors.setHorizontalAlignment(SwingConstants.CENTER);
		lblPublishers.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPublishers.setHorizontalAlignment(SwingConstants.CENTER);
		lblDates.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDates.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalBooks.setFont(lblFont);
		lblTotalFict.setFont(lblFont);
		lblTotalHistory.setFont(lblFont);
		lblTotalText.setFont(lblFont);
		lblTotalValue.setFont(lblFont);

		scrAuthors.setColumnHeaderView(lblAuthors);
		scrAuthors.setViewportView(lstAuthors);
		scrPublishers.setColumnHeaderView(lblPublishers);
		scrPublishers.setViewportView(lstPublishers);
		scrDates.setColumnHeaderView(lblDates);
		scrDates.setViewportView(lstPubDates);
	}
	
	private void initDetailsPanel()
	{
		JLabel lblType = new JLabel("Type:");
		JLabel lblTitle = new JLabel("Title:");
		JLabel lblAuthor = new JLabel("Author:");
		JLabel lblPublisher = new JLabel("Publisher:");
		JLabel lblPubDate = new JLabel("Publication Date:");
		JLabel lblPrice = new JLabel("Retail Price:");
		JPanel pnlControls = new JPanel(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		lblInfo = new JLabel();
		txtType = new JTextField();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDate = new JTextField();
		txtPrice = new JTextField();
		txtInfo = new JTextField();
		
		layout.columnWeights = new double[] {0.15, 0.4, 0.3, 0.15};
		layout.rowWeights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
		detailsPanel.setLayout(layout);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		detailsPanel.add(lblTitle, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblAuthor, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPubDate, gbc);
		detailsPanel.add(lblInfo, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		gbc.gridx = 2;
		detailsPanel.add(lblType, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPrice, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(lblPublisher, gbc);

		gbc = (GridBagConstraints) gbc.clone();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		detailsPanel.add(txtTitle, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtAuthor, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtPubDate, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtInfo, gbc);

		gbc = (GridBagConstraints) gbc.clone();
		gbc.insets = new Insets(0,0,0,35);
		gbc.gridx = 3;
		detailsPanel.add(txtType, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtPrice, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		detailsPanel.add(txtPublisher, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.BOTH;
		detailsPanel.add(pnlControls, gbc);
		
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
		btnEdit.setEnabled(false);
		btnEdit.setMargin(buttonInsets);
		btnRemove.setMargin(buttonInsets);
		btnRemove.setEnabled(false);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.3;
		pnlControls.add(btnAdd, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		pnlControls.add(btnEdit, gbc);
		gbc = (GridBagConstraints) gbc.clone();
		pnlControls.add(btnRemove, gbc);
	}
	
	private void initActionListeners(JMenuItem mnIOpen, JMenuItem mnISave, JMenuItem mnIClose, JMenuItem mnIApplyFilter, JMenuItem mnIRemoveFilter, JMenuItem mnIReadMe, JMenuItem mnIAbout)
	{
		mnISave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFile(SAVE_DIALOG);
			}
		});
		
		mnIOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFile(OPEN_DIALOG);
			}
		});
		
		mnIClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				closeLibrary();
			}
		});

		mnIApplyFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				applyFilter();
			}
		});

		mnIRemoveFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				removeFilter();
			}
		});

		mnIReadMe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				
			}
		});

		mnIAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				aboutDialog();
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int index = list.getSelectedIndex();
				if(list.isSelectionEmpty()) {
					index++;
				}
				addBook(index, BOOK_TYPES[0]);
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFieldsEditable(true);;
			}
		});
		
		
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				removeBook();
			}
		});
		
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				setFieldsEditable(false);
				if(!list.isSelectionEmpty()) {
					setDetailsVisible(true);
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
				} else {
					setDetailsVisible(false);
					btnEdit.setEnabled(false);
					btnRemove.setEnabled(false);
				}
			}
		});
	}
	
	private void initListPane(JScrollPane listPane)
	{
		list = new JList<String>();
		
		listPane.setViewportView( list );
		listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Arial", Font.PLAIN, 10));
	}
	
	/* ADD/REMOVE BOOK */
	
	private Book addBook(int index, Object booktype)
	{
		Book book = null;
		String choice = (String) JOptionPane.showInputDialog(this, "Please select a book type:", "Type Selection", JOptionPane.PLAIN_MESSAGE, null, BOOK_TYPES, booktype);
		if(choice != null) { // Cancel not pressed
			switch(choice) {
			case "Fictional":
				book = new FictionalBook();
				break;
			case "History":
				book = new HistoryBook();
				break;
			case "Textbook":
				book = new TextBook();
				break;
			}
			
			library.addBook(index, book);
			list.setSelectedIndex(index);
			setFieldsEditable(true);
		}
		return book;
	}
	
	private void removeBook()
	{
		int currentIndex = list.getSelectedIndex();
		Object[] options = {"Confirm", "Cancel"};
		int option = JOptionPane.showOptionDialog(this, "Are you sure you want to remove this book?", "Remove Book", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(option == JOptionPane.YES_OPTION) {
			library.removeBook(library.getBook(currentIndex));
			list.setSelectedIndex(currentIndex);
		}
		if(!library.isEmpty()) {
			list.setSelectedIndex(currentIndex - 1);
		}
	}
	
	/* Filter Functionality */
	
	private void applyFilter()
	{
		JTextField txtStartDate = new JTextField();
		JTextField txtEndDate = new JTextField();
		
		if(!library.isEmpty()) {
			int option = getDateRange(txtStartDate, txtEndDate);
			if(option == JOptionPane.YES_OPTION) {
				GregorianCalendar startCal = getDateFromString(txtStartDate.getText(), false);
				GregorianCalendar endCal = getDateFromString(txtEndDate.getText(), true);
				GregorianCalendar now = new GregorianCalendar();
				
				if(startCal != null) {
					if(startCal.compareTo(now) < 1) {
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						String start = formatter.format(startCal.getTime());
						String end = formatter.format(endCal.getTime());
						temp = library.getBooks();
						
						library.replaceBooks(library.getBooksFilteredByDate(startCal, endCal));
						lblFilterApplied.setText("Filter applied: " + start + " - " + end);
						indicateFilter(true);
					} else {
						dateErrorDialog();
					}
				}
			}
		} else {
			noBooksDialog();
		}
	}
	
	private void removeFilter()
	{
			library.replaceBooks(temp);
			indicateFilter(false);
	}
	
	private GregorianCalendar getDateFromString(String strDate, boolean isEnd)
	{
		SimpleDateFormat all = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat monthYear = new SimpleDateFormat("MM/yyyy");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		GregorianCalendar calendar = null;
		Date date = null;
		//TODO check if endDate is empty
		try {
			date = all.parse(strDate);
		} catch (ParseException e) {
			try {
				date = monthYear.parse(strDate);
			} catch (ParseException f) {
				try {
					date = year.parse(strDate);
				} catch (ParseException g) {
					if(!isEnd) {
						dateErrorDialog();
					}
				}
			} 
		}
		if(date != null || isEnd) {
			calendar = new GregorianCalendar();
		}
		if(date != null) {
			calendar.setTime(date);
		}
		return calendar;
	}
	
	/* SAVE OPEN CLOSE */
	
	private void saveLibrary(LibraryReaderWriter lrw, String path, String fileName)
	{
    	try {
    		lrw.writeObjects(library.getBooks());
    	} catch(IOException ioe) {
    		fatalException(ioe);
    	}
	}
	
	private void openLibrary(LibraryReaderWriter lrw, String path, String fileName)
	{
		List<Book> books = library.getBooks();
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
			noBooksDialog();
		} else {
			String message = "If you close the library you will lose any "
					+ "unsaved changes! Do you wish to save?";
			int choice = JOptionPane.showConfirmDialog(this, message, 
					"Close Library", JOptionPane.YES_NO_CANCEL_OPTION);
			
			switch(choice) {
			case JOptionPane.YES_OPTION:
				selectFile(SAVE_DIALOG);
				/* falls through */
			case JOptionPane.NO_OPTION:
				library.replaceBooks(new ArrayList<Book>());
				break;
			}
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
	    }
	}

	/* GUI ADJUSTMENTS */
	
	private void indicateFilter(boolean filterApplied)
	{
		mnIApplyFilter.setEnabled(!filterApplied);
		lblFilterApplied.setVisible(filterApplied);
		mnIRemoveFilter.setEnabled(filterApplied);
		if(filterApplied) {
			listPane.setColumnHeaderView(lblFilterApplied);
		} else {
			listPane.setColumnHeaderView(null);
		}
	}
	
	private void setDetailsVisible(boolean isVisible)
	{
		for(Component component : detailsPanel.getComponents()) {
			if(!(component instanceof JPanel)) {
				component.setVisible(isVisible);
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
		}	
	}
	
	/* DIALOGS */
	
	private int getDateRange(JTextField txtStartDate, JTextField txtEndDate)
	{
		String message = "Please enter a start date and an end date. Leave the end date blank to search to the current day.";
		Object[] options = new Object[] {"OK", "Cancel"};
		JPanel mainPanel = new JPanel(new GridLayout(2,0));
		JPanel optionPanel = new JPanel(new GridLayout(0,3));
		JLabel dash = new JLabel("-");
		
		dash.setHorizontalAlignment(SwingConstants.CENTER);
		optionPanel.add(new JLabel("Start Date (dd/mm/yyyy):"));
		optionPanel.add(Box.createHorizontalStrut(5));
		optionPanel.add(new JLabel("End Date (dd/mm/yyyy):"));
		optionPanel.add(txtStartDate);
		optionPanel.add(dash);
		optionPanel.add(txtEndDate);
		mainPanel.add(new JLabel(message));
		mainPanel.add(optionPanel);
		
		return JOptionPane.showOptionDialog(this, mainPanel, "Pick Date Range", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	
	private void aboutDialog()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version " + VERSION);
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
	
	private void noBooksDialog()
	{
		JOptionPane.showMessageDialog(this, "There are no books in this library.", "Empty library", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void dateErrorDialog()
	{
		String message = "Dates must be entered in dd/mm/yyyy, mm/yyyy or yyyy format.\nPlease try again.";
		JOptionPane.showMessageDialog(this, message, "Date Error", JOptionPane.ERROR_MESSAGE);
	}
	
	protected void initDataBindings() {
		BeanProperty<Library, List<Book>> libraryBeanProperty = BeanProperty.create("books");
		JListBinding<Book, Library, JList> jListBinding = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty, list);
		ELProperty<Book, Object> bookEvalutionProperty = ELProperty.create("${title} - ${author}");
		jListBinding.setDetailBinding(bookEvalutionProperty);
		jListBinding.bind();
		
		BeanProperty<JList, String> jListBeanProperty = BeanProperty.create("selectedElement.title");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty, txtTitle, jTextFieldBeanProperty);
		autoBinding.bind();
		
		BeanProperty<JList, String> jListBeanProperty_1 = BeanProperty.create("selectedElement.author");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_1, txtAuthor, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		
		BeanProperty<JList, String> jListBeanProperty_2 = BeanProperty.create("selectedElement.publisher");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_2, txtPublisher, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		
		BeanProperty<JList, String> jListBeanProperty_3 = BeanProperty.create("selectedElement.type");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_3, txtType, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		
		BeanProperty<JList, Integer> jListBeanProperty_4 = BeanProperty.create("selectedElement.priceInPence");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JList, Integer, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_4, txtPrice, jTextFieldBeanProperty_4);
		autoBinding_4.setConverter(new PriceConverter());
		autoBinding_4.bind();
		
		BeanProperty<JList, String> jListBeanProperty_5 = BeanProperty.create("selectedElement.infoLabel");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JLabel, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_5, lblInfo, jLabelBeanProperty);
		autoBinding_5.bind();
		
		BeanProperty<JList, String> jListBeanProperty_6 = BeanProperty.create("selectedElement.infoValue");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_6, txtInfo, jTextFieldBeanProperty_5);
		autoBinding_6.bind();
		
		BeanProperty<JList, GregorianCalendar> jListBeanProperty_7 = BeanProperty.create("selectedElement.pubDate");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<JList, GregorianCalendar, JTextField, String> autoBinding_12 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, list, jListBeanProperty_7, txtPubDate, jTextFieldBeanProperty_6);
		autoBinding_12.setConverter(new DateConverter(this));
		autoBinding_12.bind();
		
		BeanProperty<Library, Integer> libraryBeanProperty_1 = BeanProperty.create("bookCount");
		AutoBinding<Library, Integer, JLabel, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, library, libraryBeanProperty_1, valTotalBooks, jLabelBeanProperty);
		autoBinding_7.bind();
		
		BeanProperty<Library, Integer> libraryBeanProperty_2 = BeanProperty.create("totalPrices");
		AutoBinding<Library, Integer, JLabel, String> autoBinding_8 = Bindings.createAutoBinding(UpdateStrategy.READ, library, libraryBeanProperty_2, valTotalVal, jLabelBeanProperty);
		autoBinding_8.setConverter(new PriceConverter());
		autoBinding_8.bind();
		
		BeanProperty<Library, Integer> libraryBeanProperty_3 = BeanProperty.create("fictionalCount");
		AutoBinding<Library, Integer, JLabel, String> autoBinding_9 = Bindings.createAutoBinding(UpdateStrategy.READ, library, libraryBeanProperty_3, valTotalFict, jLabelBeanProperty);
		autoBinding_9.bind();
		
		BeanProperty<Library, Integer> libraryBeanProperty_4 = BeanProperty.create("historyCount");
		AutoBinding<Library, Integer, JLabel, String> autoBinding_10 = Bindings.createAutoBinding(UpdateStrategy.READ, library, libraryBeanProperty_4, valTotalHist, jLabelBeanProperty);
		autoBinding_10.bind();
		
		BeanProperty<Library, Integer> libraryBeanProperty_5 = BeanProperty.create("textCount");
		AutoBinding<Library, Integer, JLabel, String> autoBinding_11 = Bindings.createAutoBinding(UpdateStrategy.READ, library, libraryBeanProperty_5, valTotalText, jLabelBeanProperty);
		autoBinding_11.bind();
		
		BeanProperty<Library, List<String>> libraryBeanProperty_6 = BeanProperty.create("authors");
		JListBinding<String, Library, JList> jListBinding_1 = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty_6, lstAuthors);
		jListBinding_1.bind();
		
		BeanProperty<Library, List<String>> libraryBeanProperty_7 = BeanProperty.create("publishers");
		JListBinding<String, Library, JList> jListBinding_2 = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty_7, lstPublishers);
		jListBinding_2.bind();
		
		BeanProperty<Library, List<GregorianCalendar>> libraryBeanProperty_8 = BeanProperty.create("dates");
		JListBinding<GregorianCalendar, Library, JList> jListBinding_3 = SwingBindings.createJListBinding(UpdateStrategy.READ, library, libraryBeanProperty_8, lstPubDates);
		jListBinding_3.setConverter(new ListDateConverter());
		jListBinding_3.bind();
	}
}