import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;

public class ViewProductsPage extends JFrame {

    Product data;
    JTable table;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Nested class for the custom table model
    private static class ProductTableModel extends AbstractTableModel {
        private Product data;

        public ProductTableModel(Product data) {
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.products.size();
        }

        @Override
        public int getColumnCount() {
            return 5; // 5 columns (Name, Company, Number, Category, Location)
        }

        @Override
        public Object getValueAt(int row, int col) {
            Object[] product = data.products.get(row);
            return product[col];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 2) { // Check if the edited column is the third column (Number)
                data.products.get(row)[col] = value;
                fireTableCellUpdated(row, col); // Notify the table that the cell has been updated
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 2; // Allow editing only for the third column (Number)
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Name";
                case 1:
                    return "Company";
                case 2:
                    return "Number";
                case 3:
                    return "Category";
                case 4:
                    return "Location";
                default:
                    return ""; // Return an empty string for unknown columns
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return super.getColumnClass(columnIndex);
        }
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());

        // Create an instance of Product
        data = new Product();

        // Load data at startup
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("products.ser"));
            data.products = (List<Object[]>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Save data at shutdown
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
            }
        });

        // Create a CardLayout to switch between panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Panel for viewing products
        JPanel viewPanel = createViewPanel();
        cardPanel.add(viewPanel, "VIEW");

        // Panel for adding new products
        JPanel addPanel = createAddPanel();
        cardPanel.add(addPanel, "ADD");

        // Add the cardPanel to the center of the frame
        this.add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createViewPanel() {
        JPanel viewPanel = new JPanel(new BorderLayout());

        // Create the table
        table = new JTable(new ProductTableModel(data));

        // Make the third column (Number) editable
        TableColumn numberColumn = table.getColumnModel().getColumn(2);
        numberColumn.setCellEditor(new DefaultCellEditor(new JTextField()));

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Add the scroll pane to the center of the viewPanel
        viewPanel.add(scrollPane, BorderLayout.CENTER);

        // Button to switch to the addPanel
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ADD");
            }
        });

        // Button to delete selected rows
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();

                // Delete selected rows from the data
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    data.products.remove(selectedRows[i]);
                }

                // Save the data to the serialized file
                saveData();

                // Refresh the table
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });

        // Button to go back to the HomePage
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
            dispose();
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add the button panel to the south of the viewPanel
        viewPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add a mouse listener to handle the editing event when the user double-clicks on a cell
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    int selectedColumn = table.getSelectedColumn();
                    if (selectedRow != -1 && selectedColumn != -1 && table.isCellEditable(selectedRow, selectedColumn)) {
                        table.editCellAt(selectedRow, selectedColumn);
                    }
                }
            }
        });

        return viewPanel;
    }

    private JPanel createAddPanel() {
        JPanel addPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels and text fields for input
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField nameField = new JTextField(10);
        nameField.setPreferredSize(new Dimension(150, 20));

        addPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addPanel.add(new JLabel("Company:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField companyField = new JTextField(10);
        companyField.setPreferredSize(new Dimension(150, 20));
        addPanel.add(companyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addPanel.add(new JLabel("Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField numberField = new JTextField(10);
        numberField.setPreferredSize(new Dimension(150, 20));
        addPanel.add(numberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addPanel.add(new JLabel("Category:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        String[] categories = {"Men", "Women", "Kids"}; // Options for the drop-down
        JComboBox<String> categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setPreferredSize(new Dimension(150, 20));
        addPanel.add(categoryDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addPanel.add(new JLabel("Location:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JTextField locationField = new JTextField(10);
        locationField.setPreferredSize(new Dimension(150, 20));
        addPanel.add(locationField, gbc);

        // "Save" button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.WHITE);
        saveButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the button

        // Add ActionListener to capture the selected category
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capture the selected category from the JComboBox
                String selectedCategory = (String) categoryDropdown.getSelectedItem();

                // Add the data to your Product instance
                Object[] productData = {nameField.getText(), companyField.getText(), numberField.getText(),
                        selectedCategory, locationField.getText()};

                data.products.add(productData);

                // Save the data to the serialized file
                saveData();

                // Clear the input fields after saving
                nameField.setText("");
                companyField.setText("");
                numberField.setText("");
                locationField.setText("");

                // Refresh the table
                ((AbstractTableModel) table.getModel()).fireTableDataChanged();
            }
        });

        // "Back" button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(150, 30));

        // Add ActionListener to switch back to the "VIEW" panel
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "VIEW");
            }
        });

        addPanel.add(saveButton, gbc);
        addPanel.add(backButton, gbc);

        addPanel.setBackground(Color.PINK);
        return addPanel;
    }

    private void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("products.ser"));
            oos.writeObject(data.products);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ViewProductsPage() {
        super("Products Administration");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();

        setMinimumSize(new Dimension(400, 300));
        cardLayout.show(cardPanel, "VIEW"); // Start with the viewPanel
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewProductsPage viewProductsPage = new ViewProductsPage();
            viewProductsPage.setVisible(true);
        });
    }
}
