import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;

public class WarehouseManagementSystem extends JFrame {
    private Product data;
    private JTable table;
    private JTextField nameField;
    private JTextField companyField;
    private JTextField numberField;
    private JTextField categoryField;
    private JTextField locationField;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel addProductPanel;

    private void initComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Panel for showing the table
        JPanel showTablePanel = new JPanel(new BorderLayout());

        table = new JTable(new ProductTableModel(data));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        showTablePanel.add(scrollPane, BorderLayout.CENTER);

        cardPanel.add(showTablePanel, "showTable");

        // Panel for adding new products
        addProductPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel nameLabel = new JLabel("Name:");
        JLabel neptunLabel = new JLabel("Neptun:");

        nameField = new JTextField(20);
        companyField = new JTextField(6);
        numberField = new JTextField(6);
        categoryField = new JTextField(6);
        locationField = new JTextField(6);

        JButton addButton = new JButton("Add Product");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = nameField.getText();
                String company = companyField.getText();
                String stockNumberText = numberField.getText();
                String category = categoryField.getText();
                String location = locationField.getText();

                if (!productName.isEmpty() && !company.isEmpty() && !stockNumberText.isEmpty() && !category.isEmpty() && !location.isEmpty()) {
                    int stockNumber = Integer.parseInt(stockNumberText);
                    data.addProduct(productName, company, stockNumber, category, location);
                    nameField.setText("");
                    companyField.setText("");
                    numberField.setText("");
                    categoryField.setText("");
                    locationField.setText("");
                }
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(neptunLabel);
        inputPanel.add(companyField);
        inputPanel.add(numberField);
        inputPanel.add(categoryField);
        inputPanel.add(locationField);
        inputPanel.add(addButton);

        addProductPanel.add(inputPanel, BorderLayout.SOUTH);

        cardPanel.add(addProductPanel, "addProduct");

        this.add(cardPanel);

        // Button to switch between panels
        JButton switchButton = new JButton("Switch to Add Product");
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "addProduct");
            }
        });

        this.add(switchButton, BorderLayout.NORTH);
    }

    @SuppressWarnings("unchecked")
    public WarehouseManagementSystem() {
        super("Warehouse Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            data = new Product();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("product.ser"));
            data.products = (List<Object[]>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("product.ser"));
                    oos.writeObject(data.products);
                    oos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setMinimumSize(new Dimension(400, 300));
        initComponents();
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WarehouseManagementSystem sf = new WarehouseManagementSystem();
            sf.setVisible(true);
        });
    }

    // Custom TableModel
    class ProductTableModel extends AbstractTableModel {
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
            return 5;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Object[] product = data.products.get(row);
            return product[col];
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            Object[] product = data.products.get(row);
            product[col] = value;
            fireTableCellUpdated(row, col);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("product.ser"));
                oos.writeObject(data.products);
                oos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Product implements Serializable {
    List<Object[]> products;

    public Product() {
        products = new java.util.ArrayList<>();
    }

    public void addProduct(String name, String company, int number, String category, String location) {
        Object[] newProduct = {name, company, number, category, location};
        products.add(newProduct);
    }
}
