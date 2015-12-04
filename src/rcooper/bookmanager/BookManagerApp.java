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
import javax.swing.JSplitPane;
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
	private JMenu mnFile, mnHelp;
	private JMenuItem mnINew, mnIOpen, mnIClose, mnIAbout, mnIReadMe;
	private JTabbedPane mainPane;
	private JScrollPane listPane;
	private JSplitPane detailsView;
	private JPanel reportView, detailsPanel, pnlControls;
	private JLabel lblTitle, lblAuthor, lblPublisher, lblPubDate,
		lblPrice, lblType, lblInfo;
	private JTextField txtTitle, txtAuthor, txtPublisher, txtPubDate,
		txtPrice, txtInfo, txtType;
	private JList<String> list;
	private JButton btnAdd, btnEdit, btnRemove;
	private List<JComponent> detailsComponents;
	private List<JButton> buttons;
	private List<String> types;
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
		types = new ArrayList<String>();
		types.add("Fictional");
		types.add("History");
		types.add("Textbook");
//		public FictionalBook(int id, String title, String author, String publisher,
//				String pubDate, double retailPrice, int type, String genre)
		library.addBook(new FictionalBook("Soul Music", "Terry Pratchett", "P.W. Books", "20.01.1999", 5.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new FictionalBook("The Color of Magic", "Terry Pratchett", "TS Publishing", "09.05.1992", 4.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new FictionalBook("The Light Fantastic", "Terry Pratchett", "BooksALot", "12.02.1987", 6.50 , "Fictional", "Fantasy/Comedy"));
		library.addBook(new TextBook("Java For Beginners", "A Dude", "BooksALot", "12.02.1987", 6.50 , "Textbook", "Magic"));
		library.addBook(new HistoryBook("The History of Design Patterns", "Some O. Dude", "BooksALot", "12.02.1987", 6.50 , "History", "20th Century"));
		//// Frame menu bar ////
		menuBar = new JMenuBar();
		
		//// Menu bar menus ////
		mnFile = new JMenu("File");
		mnHelp = new JMenu("Help");
		
		//// File menu items ////
		mnINew = new JMenuItem("New");
		mnIOpen = new JMenuItem("Open...");
		mnIClose = new JMenuItem("Close");
		
		//// Help menu items ////
		mnIReadMe = new JMenuItem("View Help");
		mnIAbout = new JMenuItem("About Book Manager");
		
		//// app child ////
		mainPane = new JTabbedPane();
		
		//// Tabs ////
		detailsView = new JSplitPane();
		reportView = new JPanel();
		
		//// List pane stuff ////
		list = new JList<String>();
		
		//// detailsView children ////
		listPane = new JScrollPane();
		detailsPanel = new JPanel();
		
		//// detailsPanel children ////
		lblType = new JLabel("Type:");
		lblTitle = new JLabel("Title:");
		lblAuthor = new JLabel("Author:");
		lblPublisher = new JLabel("Publisher:");
		lblPubDate = new JLabel("Publication Date:");
		lblPrice = new JLabel("Retail Price:");
		lblInfo = new JLabel("Additional info:");
		txtType = new JTextField();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDate = new JTextField();
		txtPrice = new JTextField();
		txtInfo = new JTextField();
		detailsComponents = new ArrayList<JComponent>();
		
		//// buttonPanel children ////
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnRemove = new JButton("Remove");
		buttons = new ArrayList<JButton>();
		
		//// Set Up Elements ////
		setFrameProperties(); // app
		setUpMenu(); // app.menuBar setup
		setUpTabs(); // app.tabbedPane setup
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
		setContentPane(mainPane);
	}
	
	private void setUpMenu()
	{
		menuBar.add(mnFile);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(mnHelp);
		
		//// app.menuBar.file setup ////
		mnFile.add(mnINew);
		mnFile.add(mnIOpen);
		mnFile.add(mnIClose);
		
		//// app.menuBar.help setup ////
		mnHelp.add(mnIReadMe);
		mnHelp.add(mnIAbout);

		//// app.menuBar.help.about setup ////
		mnIAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e )
			{
				showAbout();
			}
		});
	}
	
	private void setUpTabs()
	{
		mainPane.addTab("Details View", detailsView);
		mainPane.addTab("Report View", reportView);
	}
	
	private void setUpDetailsView()
	{
		detailsView.setLeftComponent(listPane);
		detailsView.setRightComponent(detailsPanel);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		setListProperties();
		
		setDetailsPanel();
	}
	
	private void setDetailsPanel()
	{
		lblType = new JLabel("Type:");
		lblTitle = new JLabel("Title:");
		lblAuthor = new JLabel("Author:");
		lblPublisher = new JLabel("Publisher:");
		lblPubDate = new JLabel("Publication Date:");
		lblPrice = new JLabel("Retail Price:");
		lblInfo = new JLabel();
		txtType = new JTextField();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDate = new JTextField();
		txtPrice = new JTextField();
		txtInfo = new JTextField();
		pnlControls = new JPanel(new GridBagLayout());

		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[] {0.15, 0.4, 0.3, 0.15};
		layout.rowWeights = new double[] {0.2, 0.2, 0.2, 0.2, 0.2};
		detailsPanel.setLayout(layout);
		GridBagConstraints gbcLabels = new GridBagConstraints();
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 0;
		gbcLabels.gridy = GridBagConstraints.RELATIVE;
		detailsPanel.add(lblTitle, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		detailsPanel.add(lblAuthor, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		detailsPanel.add(lblPublisher, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		detailsPanel.add(lblInfo, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		gbcLabels = new GridBagConstraints();
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 2;
		gbcLabels.gridy = GridBagConstraints.RELATIVE;
		detailsPanel.add(lblType, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		detailsPanel.add(lblPrice, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		detailsPanel.add(lblPubDate, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		
		GridBagConstraints gbcTextFields = new GridBagConstraints();
		gbcTextFields.anchor = GridBagConstraints.WEST;
		gbcTextFields.gridx = 1;
		gbcTextFields.fill = GridBagConstraints.HORIZONTAL;
		detailsPanel.add(txtTitle, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		detailsPanel.add(txtAuthor, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		detailsPanel.add(txtPublisher, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		detailsPanel.add(txtInfo, gbcTextFields);
		
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		gbcTextFields.insets = new Insets(0,0,0,35);
		gbcTextFields.gridx = 3;
		detailsPanel.add(txtType, gbcTextFields); // margin right
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		detailsPanel.add(txtPrice, gbcTextFields); // margin right
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		detailsPanel.add(txtPubDate, gbcTextFields); // margin right
		GridBagConstraints gbcCtrlPanel = new GridBagConstraints();
		gbcCtrlPanel.gridx = 0;
		gbcCtrlPanel.gridy = 4;
		gbcCtrlPanel.gridwidth = 4;
		gbcCtrlPanel.fill = GridBagConstraints.BOTH;
		detailsPanel.add(pnlControls, gbcCtrlPanel);
		
		addDetailControls();
	}
	
	private void addDetailControls()
	{
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
		GridBagConstraints btnGbc1 = new GridBagConstraints();
		btnGbc1.weightx = 0.3;
		pnlControls.add(btnAdd, btnGbc1);
		GridBagConstraints btnGbc2 = (GridBagConstraints) btnGbc1.clone();
		btnGbc2.gridx = 1;
		pnlControls.add(btnEdit, btnGbc2);
		GridBagConstraints btnGbc3 = (GridBagConstraints) btnGbc2.clone();
		btnGbc3.gridx = 2;
		pnlControls.add(btnRemove, btnGbc3);
	}
	
	private void setListProperties()
	{
		listPane.setViewportView( list );

		list.setFont(new Font("Arial", Font.PLAIN, 10));
		list.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
		{
			
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if(list.getSelectedIndex() == -1) {
					for(JComponent component : detailsComponents) {
						if( component instanceof JTextField) {
							JTextField textField = (JTextField) component;
							textField.setText("");
						}
					}
				} else {
					Book book = library.getItems().get(list.getSelectedIndex());
					String bookType = book.getType();
					btnEdit.setEnabled(true);
					btnRemove.setEnabled(true);
				}
			}
		});
	}
	
	private void showAbout()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version 0.1");
	}
	
	private void addBook()
	{
		Book book = new FictionalBook();
		AddEditBookDialog dialog = new AddEditBookDialog(types, book);
		library.addBook(new FictionalBook());
		list.repaint();
	}
	
	private void editBook()
	{
		for(JComponent component : detailsComponents) {
			if( component instanceof JTextField) {
				JTextField textField = (JTextField) component;
				textField.setEditable(true);
			}
		}
	}
	
	private void removeBook()
	{
		Object[] options = {"Confirm", "Cancel"};
		int option = JOptionPane.showOptionDialog(this,
				"Are you sure you want to remove this book?",
				"Remove Book", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options,
				options[1]);
		if( option == 0 ) {
			if (list.getSelectedIndex() > -1) {
				library.removeBook(library.getItems()
						.get(list.getSelectedIndex()));
			}
		}
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
		BeanProperty<JList, String> jListBeanProperty = BeanProperty.create("selectedElement.title");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty, txtTitle, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_1 = BeanProperty.create("selectedElement.author");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_1, txtAuthor, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_2 = BeanProperty.create("selectedElement.publisher");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_2, txtPublisher, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_3 = BeanProperty.create("selectedElement.type");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_3, txtType, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<JList, Double> jListBeanProperty_4 = BeanProperty.create("selectedElement.price");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<JList, Double, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_4, txtPrice, jTextFieldBeanProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_5 = BeanProperty.create("selectedElement.pubDate");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_5, txtPubDate, jTextFieldBeanProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_6 = BeanProperty.create("selectedElement.infoLabel");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<JList, String, JLabel, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_6, lblInfo, jLabelBeanProperty);
		autoBinding_6.bind();
		//
		BeanProperty<JList, String> jListBeanProperty_7 = BeanProperty.create("selectedElement.infoValue");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<JList, String, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, list, jListBeanProperty_7, txtInfo, jTextFieldBeanProperty_6);
		autoBinding_7.bind();
	}
}