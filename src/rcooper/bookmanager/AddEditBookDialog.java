package rcooper.bookmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JComboBoxBinding;
import org.jdesktop.swingbinding.SwingBindings;

import rcooper.bookmanager.model.Book;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.Bindings;

public class AddEditBookDialog extends JDialog
{

	private JLabel lblTitle, lblAuthor, lblPublisher, lblType, lblPrice, 
		lblPubDate, lblInfo;
	private JTextField txtTitle, txtAuthor, txtPublisher, txtPrice, txtPubDate,
		txtInfo;
	private JComboBox<String> cBxType;
	private JPanel pnlControls;
	private Book book;
	private List<String> types;
	private JButton btnSave;
	private JButton btnCancel;
	
	public AddEditBookDialog(List<String> types, Book book)
	{
		this.book = book;
		this.types = types;
		setTitle("Add Book");
		setSize(640, 480);
		setLocationRelativeTo(this);
		setVisible(true);
		
		lblType = new JLabel("Type:");
		lblTitle = new JLabel("Title:");
		lblAuthor = new JLabel("Author:");
		lblPublisher = new JLabel("Publisher:");
		lblPubDate = new JLabel("Publication Date:");
		lblPrice = new JLabel("Retail Price:");
		lblInfo = new JLabel();
		cBxType = new JComboBox<String>();
		txtTitle = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtPubDate = new JTextField();
		txtPrice = new JTextField();
		txtInfo = new JTextField();
		pnlControls = new JPanel(new GridBagLayout());
		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");

		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[] {0.2, 0.4, 0.2, 0.2};
		layout.rowWeights = new double[] {0.1875, 0.1875, 0.1875, 0.1875, 0.25};
		getContentPane().setLayout(layout);
		GridBagConstraints gbcLabels = new GridBagConstraints();
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 0;
		gbcLabels.gridy = GridBagConstraints.RELATIVE;
		getContentPane().add(lblTitle, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		getContentPane().add(lblAuthor, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		getContentPane().add(lblPublisher, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		getContentPane().add(lblInfo, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		gbcLabels = new GridBagConstraints();
		gbcLabels.anchor = GridBagConstraints.EAST;
		gbcLabels.gridx = 2;
		gbcLabels.gridy = GridBagConstraints.RELATIVE;
		getContentPane().add(lblType, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		getContentPane().add(lblPrice, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		getContentPane().add(lblPubDate, gbcLabels);
		gbcLabels = (GridBagConstraints) gbcLabels.clone();
		
		GridBagConstraints gbcTextFields = new GridBagConstraints();
		gbcTextFields.anchor = GridBagConstraints.WEST;
		gbcTextFields.gridx = 1;
		gbcTextFields.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(txtTitle, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		getContentPane().add(txtAuthor, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		getContentPane().add(txtPublisher, gbcTextFields);
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		getContentPane().add(txtInfo, gbcTextFields);
		
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		gbcTextFields.insets = new Insets(0,0,0,50);
		gbcTextFields.gridx = 3;
		getContentPane().add(cBxType, gbcTextFields); // margin right
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		getContentPane().add(txtPrice, gbcTextFields); // margin right
		gbcTextFields = (GridBagConstraints) gbcTextFields.clone();
		getContentPane().add(txtPubDate, gbcTextFields); // margin right
		GridBagConstraints gbcCtrlPanel = new GridBagConstraints();
		gbcCtrlPanel.gridx = 0;
		gbcCtrlPanel.gridwidth = 4;
		gbcCtrlPanel.fill = GridBagConstraints.BOTH;
		getContentPane().add(pnlControls, gbcCtrlPanel);
		
		GridBagConstraints ctrlButtonGbc = new GridBagConstraints();
		Insets buttonInsets = new Insets(10, 20, 10, 20);; 
		ctrlButtonGbc.weightx = 0.5;
		btnSave.setMargin(buttonInsets);
		btnCancel.setMargin(buttonInsets);
		pnlControls.add(btnSave, ctrlButtonGbc);
		pnlControls.add(btnCancel, ctrlButtonGbc);
		initDataBindings();
	}
	
	protected void initDataBindings() {
		JComboBoxBinding<String, List<String>, JComboBox> jComboBinding = SwingBindings.createJComboBoxBinding(UpdateStrategy.READ, types, cBxType);
		jComboBinding.bind();
		//
		BeanProperty<Book, String> jListBeanProperty_6 = BeanProperty.create("infoLabel");
		BeanProperty<JLabel, String> jLabelBeanProperty = BeanProperty.create("text");
		AutoBinding<Book, String, JLabel, String> autoBinding_6 = Bindings.createAutoBinding(UpdateStrategy.READ, book, jListBeanProperty_6, lblInfo, jLabelBeanProperty);
		autoBinding_6.bind();
		//
		BeanProperty<Book, String> jListBeanProperty_7 = BeanProperty.create("infoValue");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_6 = BeanProperty.create("text");
		AutoBinding<Book, String, JTextField, String> autoBinding_7 = Bindings.createAutoBinding(UpdateStrategy.READ, book, jListBeanProperty_7, txtInfo, jTextFieldBeanProperty_6);
		autoBinding_7.bind();
	}
}