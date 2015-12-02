package rcooper.bookmanager;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rcooper.bookmanager.model.Book;
import rcooper.bookmanager.model.Library;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

public class AddBookDialog extends JDialog
{

	private JLabel titleLabel, authorLabel, publisherLabel, genreLabel, 
		subjectLabel, typeLabel, priceLabel,pubDateLabel, periodLabel;
	private JTextField titleField, authorField, publisherField, genreField, 
		subjectField, priceField,pubDateField, periodField;
	private JComboBox<String> typeField;
	private JPanel filler;
	private Library library;
	private Book book;
	
	public AddBookDialog(Frame owner, Library library, Book book)
	{
		super(owner);
		setTitle("Add Book");
		setSize(640, 480);
		setLocationRelativeTo(this);
		getContentPane().setLayout(new GridBagLayout());
		setVisible(true);
		
		this.library = library;
		this.book = book;
		
		typeLabel = new JLabel("Type:");
		titleLabel = new JLabel("Title:");
		authorLabel = new JLabel("Author:");
		publisherLabel = new JLabel("Publisher:");
		pubDateLabel = new JLabel("Publication Date:");
		priceLabel = new JLabel("Retail Price:");
		genreLabel = new JLabel("Genre:");
		periodLabel = new JLabel("Period:");
		subjectLabel = new JLabel("Subject:");
		typeField = new JComboBox<String>();
		titleField = new JTextField();
		authorField = new JTextField();
		publisherField = new JTextField();
		pubDateField = new JTextField();
		priceField = new JTextField();
		genreField = new JTextField();
		periodField = new JTextField();
		subjectField = new JTextField();
		filler = new JPanel();
		
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
//		for(int i=0; i<5; i++) {
//			gbc.gridy=i;
//			add(leftFields[i], gbc);
//		}
		
		// Filler
		gbc = new GridBagConstraints();
		gbc.gridx=5;
		gbc.gridy=0;
		gbc.weightx = 1;
		gbc.gridheight = 5;
		getContentPane().add(filler, gbc);
		initDataBindings();
	}
	
	protected void initDataBindings() {
		BeanProperty<Book, Double> bookBeanProperty = BeanProperty.create("retailPrice");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<Book, Double, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty, priceField, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<Book, String> bookBeanProperty_1 = BeanProperty.create("publicationDate");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<Book, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_1, pubDateField, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<Book, Integer> bookBeanProperty_2 = BeanProperty.create("period");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<Book, Integer, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_2, periodField, jTextFieldBeanProperty_2);
		autoBinding_2.bind();
		//
		BeanProperty<Book, String> bookBeanProperty_3 = BeanProperty.create("title");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<Book, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_3, titleField, jTextFieldBeanProperty_3);
		autoBinding_3.bind();
		//
		BeanProperty<Book, String> bookBeanProperty_4 = BeanProperty.create("author");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<Book, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_4, authorField, jTextFieldBeanProperty_4);
		autoBinding_4.bind();
		//
		BeanProperty<Book, String> bookBeanProperty_5 = BeanProperty.create("publisher");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<Book, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_5, publisherField, jTextFieldBeanProperty_5);
		autoBinding_5.bind();
		//
		BeanProperty<Book, Integer> bookBeanProperty_6 = BeanProperty.create("genre");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<Book, Integer, JTextField, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_6, genreField, jTextFieldBeanProperty_6);
		autoBinding_6.bind();
		//
		BeanProperty<Book, Integer> bookBeanProperty_7 = BeanProperty.create("subject");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_7 = BeanProperty.create("text");
		AutoBinding<Book, Integer, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, book, bookBeanProperty_7, subjectField, jTextFieldBeanProperty_7);
		autoBinding_7.bind();
	}
}