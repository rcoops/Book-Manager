package rcooper.bookmanager;

import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
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
		priceField, 
	typeField, genreField, periodField, subjectField; 
	private JList<String> list;
	private JButton addButton, editButton, removeButton, saveButton;
	
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
		//Book book1 = new FictionalBook(title, title, title, securityWarningPointX, defaultCloseOperation, title);
		//// Frame menu bar//
		menuBar = new JMenuBar();
		
		//// Menu bar menus//
		file = new JMenu("File");
		help = new JMenu("Help");
		
		//// File menu items//
		newBook = new JMenuItem("New");
		openBook = new JMenuItem("Open...");
		closeBook = new JMenuItem("Close");
		
		//// Help menu items//
		readMe = new JMenuItem("View Help");
		about = new JMenuItem("About Book Manager");
		
		//// app child//
		tabbedPane = new JTabbedPane();
		
		//// Tabs//
		detailsView = new JPanel(new GridBagLayout());
		reportView = new JPanel();
		
		//// List pane stuff//
		list = new JList<String>();
		
		//// detailsView children
		listPane = new JScrollPane();
		detailsPanel = new JPanel(new GridBagLayout());
		controlPanel = new JPanel(new GridBagLayout());
		
		//// detailsPanel children//
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
		saveButton = new JButton("Save");
		
		//// app ////
		setTitle("Book Manager");
		setSize(800, 600);
		setMinimumSize(new Dimension(800,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		setContentPane( tabbedPane );
		
		//// app.menuBar setup ////
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
		
		//// app.tabbedPane setup ////
		tabbedPane.addTab("Details View", detailsView);
		tabbedPane.addTab("Report View", reportView);
		
		//// app.tabbedPane.detailsView setup ////
		GridBagConstraints gbc = new GridBagConstraints(1, 0, 1, 1, 2, 4,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0);
		
		detailsView.add(detailsPanel, gbc);
		
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		detailsView.add(controlPanel, gbc);
		
		gbc.weightx = 1;
		gbc.weighty = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		detailsView.add(listPane, gbc);
		
		//// app.tabbedPane.detailsView.listPane setup ////
		listPane.setViewportView( list );
		
		//// app.tabbedPane.detailsView.detailsPanel setup ////
		JLabel[] leftLabels = {titleLabel, authorLabel, publisherLabel,
				genreLabel, subjectLabel};
		JLabel[] rightLabels = {typeLabel, priceLabel, null, pubDateLabel, 
				periodLabel};
		JTextField[] leftFields = {titleField, authorField, publisherField,
				genreField, subjectField};
		JTextField[] rightFields = {typeField, priceField, null, pubDateField, 
				periodField};
		
		// Left labels
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		
		for(int i=0; i<5; i++) {
			gbc.gridy=i;
			detailsPanel.add(leftLabels[i], gbc);
		}
		
		// Right labels
		gbc.gridx = 2;
		for(int i=0; i<5; i++) {
			if(i!=2) {
				gbc.gridy=i;
				detailsPanel.add(rightLabels[i], gbc);
			}
		}
		
		// Right fields
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 3;
		gbc.weightx = 2;
		for(int i=0; i<5; i++) {
			if(i!=2) {
				gbc.gridy=i;
				detailsPanel.add(rightFields[i], gbc);
			}
		}
		
		// Left fields
		gbc.gridx = 1;
		gbc.weightx = 4;
		for(int i=0; i<5; i++) {
			gbc.gridy=i;
			detailsPanel.add(leftFields[i], gbc);
		}
		
		// Filler
		gbc.gridx=5;
		gbc.gridy=0;
		gbc.weightx = 1;
		gbc.gridheight = 5;
		detailsPanel.add(filler, gbc);
		
		// Set bulk properties for text containers
		Component[] components = detailsPanel.getComponents();
		ArrayList<JComponent> detailsComponents = new ArrayList<JComponent>();
		for(Component component : components) {
			detailsComponents.add((JComponent) component);
		}
		for(JComponent component : detailsComponents) {
			if(!(component instanceof JPanel)) {
				component.setFont(new Font("Arial", Font.PLAIN, 14));
				component.setEnabled(false);
			}
		}
		
		//// app.tabbedPane.detailsView.controlPanel setup ////
		Insets buttonInsets = new Insets(5, 10, 5, 10);
		addButton.setMargin(buttonInsets);
		editButton.setMargin(buttonInsets);
		removeButton.setMargin(buttonInsets);
		saveButton.setMargin(buttonInsets);
		editButton.setEnabled(false);
		removeButton.setEnabled(false);
		saveButton.setEnabled(false);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		controlPanel.add(addButton, gbc);
		gbc.gridx = 1;
		controlPanel.add(editButton, gbc);
		gbc.gridx = 2;
		controlPanel.add(removeButton, gbc);
		gbc.gridx = 3;
		controlPanel.add(saveButton, gbc);
		
		pack();
	}
	
	private void showAbout()
	{
		JOptionPane.showMessageDialog(this, "Book Manager App Version 0.1");
	}
	
}