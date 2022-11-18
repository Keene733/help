package help;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Keene Cabahug - 2444791 CIT 4423 01 Nov 13, 2022 Windows 11 Home
 */

public class Display extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Sets up scrollwheel and width/height of frame
	private static final int HEIGHT = 500;
	private static final int WIDTH = 1000;
	private final int SCROLL_X = 0;
	private static final int SCROLL_Y = 0;

	// Sets up the pieces for the display
	private GridLayout grid = new GridLayout(10, 2, 5, 20);
	private JPanel scrollingPanel = new JPanel(grid);
	private JPanel buttonPanel = new JPanel();
	private JScrollPane scroll = new JScrollPane(this.scrollingPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JButton checkout = new JButton("Checkout");
	StringBuilder message;
	JButton remove = new JButton("Remove");
	JPanel panel;
	Products info = new Products();
	Double productTotal;

	// Sets up the dropdown menu and checkbox
	private JCheckBox[] checkBoxes = new JCheckBox[10];
	@SuppressWarnings("unchecked")
	private JComboBox<Integer>[] combos = new JComboBox[10];
	private Integer[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private StoredProducts myInventory = new StoredProducts();
	private ArrayList<Products> selectedItems = new ArrayList<Products>();

	public Display() {

		// Default constructor for display, puts the pieces together

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		checkout.addActionListener(this);
		buttonPanel.add(checkout);

		this.add(buttonPanel);

		for (int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i] = new JCheckBox();
			combos[i] = new JComboBox<>(numbers);

			checkBoxes[i].setVisible(true);
			checkBoxes[i].setToolTipText(hoverOver(myInventory.getProducts(i)));
			checkBoxes[i].setText(setTextBox(myInventory.getProducts(i)));

			scrollingPanel.add(checkBoxes[i]);
			scrollingPanel.add(combos[i]);
		}
		this.add(scroll);
		buttonPanel.setBackground(Color.black);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			this.setSize(WIDTH, HEIGHT);
		}

	}

	private void addOne(ArrayList<String> list, int index) {
		if (!selectedItems.get(index).getProductName().equals(selectedItems.get(index - 1).getProductName())) {
			list.add(selectedItems.get(index).getProductName());
		}
	}

	private String setTextBox(Products product) {
		// Price and name for textbox
		StringBuilder build = new StringBuilder(product.getPriceAsString());
		build.append(String.format(": %s", product.getProductName()));
		return build.toString();
	}

	private String hoverOver(Products product) {
		// When hovering over text it shows description/quantity
		StringBuilder bld = new StringBuilder(product.getDescription());
		bld.append(String.format(" %,d units in stock", product.getQuantity()));
		return bld.toString();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Paints the panels.

		scrollingPanel.setSize(this.getWidth(), (int) (this.getHeight() * 0.8));
		scroll.setBounds(SCROLL_X, SCROLL_Y, (this.getWidth()), (int) (this.getHeight() * 0.8));
		buttonPanel.setBounds(0, (int) (this.getHeight() * 0.8), this.getWidth(), (int) (this.getHeight() * 0.2));

		int buttonWidth = (buttonPanel.getWidth() / 9);
		int buttonHeight = (buttonPanel.getHeight() / 2);
		int buttonY = (buttonHeight / 4);
		int checkoutX = (buttonPanel.getWidth() / 2) - (buttonWidth / 2);
		checkout.setBounds(checkoutX, buttonY, buttonWidth, buttonHeight);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// actionPerformed method for checkout button
		double subtotal = 0;

		final double TAX = 0.0825;
		ArrayList<String> purchased = new ArrayList<String>();
		message = new StringBuilder("");
		for (int i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].isSelected()) {
				StringBuilder bld = new StringBuilder(checkBoxes[i].getText());
				String name = bld.substring(bld.indexOf(": ") + 2);
				for (int j = 0; j < combos[i].getSelectedIndex(); j++) {
					selectedItems.add(this.myInventory.getProducts(name));
				}

			}
		}
		if (!selectedItems.isEmpty()) {
			for (int i = 0; i < selectedItems.size(); i++) {

				subtotal += (selectedItems.get(i).getPrice());
				productTotal = (selectedItems.get(i).getPrice());

				if (i > 0) {
					addOne(purchased, i);
				} else {
					purchased.add(selectedItems.get(i).getProductName());
				}
			}
		}
		for (int i = 0; i < purchased.size(); i++) {
			// message.append(String.format("%s%n", purchased.get(i)));

		}

		// message.append(String.format(" Subtotal: $%,.2f", subtotal));
		// message.append(String.format(" Tax: $%,.2f",TAX*subtotal));
		// message.append(String.format(" Total: $%,.2f",subtotal+(TAX*subtotal)));
		JFrame dis = new JFrame("CheckOut");

		panel = new JPanel();

		dis.setSize(500, 500);
		for (int i = 0; i < checkBoxes.length; i++) {
			if (checkBoxes[i].isSelected()) {
				checkBoxes[i] = new JCheckBox();
				checkBoxes[i].setText(setTextBox(myInventory.getProducts(i)) + " Quantity: "
						+ combos[i].getSelectedIndex() + " Total Product Price: ");
				checkBoxes[i].setVisible(true);
				panel.add(checkBoxes[i]);

			}
		}
			panel.add(new Edit());
			dis.add(panel);
			dis.setVisible(true);

			this.dispose();

		

	}
}