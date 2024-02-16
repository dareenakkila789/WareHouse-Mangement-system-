import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddSalePage extends JFrame implements ActionListener {

    private JComboBox<String> categoryDropdown;
    private JComboBox<Integer> saleDropdown;
    private JButton saveButton, backButton, viewHistoryButton;
    private JFormattedTextField startDateField, endDateField;

    public AddSalePage() {
        setTitle("Add Sale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel systemLabel = new JLabel("Sale Addition");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        systemLabel.setBounds(100, 0, 300, 30);
        add(systemLabel);

        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(40, 40, 100, 30);
        add(categoryLabel);

        String[] categories = {"Men", "Women", "Kids"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.setBounds(160, 40, 150, 30);
        add(categoryDropdown);

        JLabel saleLabel = new JLabel("Sale");
        saleLabel.setBounds(40, 80, 100, 30);
        add(saleLabel);

        Integer[] saleValues = new Integer[21];
        for (int i = 0; i <= 100; i += 5) {
            saleValues[i / 5] = i;
        }
        saleDropdown = new JComboBox<>(saleValues);
        saleDropdown.setBounds(160, 80, 80, 30);
        add(saleDropdown);

        JLabel startDateLabel = new JLabel("Start Date (yyyy-MM-dd)");
        startDateLabel.setBounds(40, 120, 150, 30);
        add(startDateLabel);

        try {
            MaskFormatter dobFormatter = new MaskFormatter("####-##-##");
            startDateField = new JFormattedTextField(dobFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            startDateField = new JFormattedTextField();
        }
        startDateField.setBounds(200, 120, 150, 30);
        add(startDateField);

        JLabel endDateLabel = new JLabel("End Date (yyyy-MM-dd)");
        endDateLabel.setBounds(40, 160, 150, 30);
        add(endDateLabel);

        try {
            MaskFormatter dobFormatter = new MaskFormatter("####-##-##");
            endDateField = new JFormattedTextField(dobFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            endDateField = new JFormattedTextField();
        }
        endDateField.setBounds(200, 160, 150, 30);
        add(endDateField);

        saveButton = new JButton("Save");
        saveButton.setBounds(160, 200, 100, 30);
        saveButton.addActionListener(this);
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.WHITE);
        add(saveButton);

        backButton = new JButton("Back");
        backButton.setBounds(160, 240, 100, 30);
        backButton.addActionListener(this);
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.WHITE);
        add(backButton);

        viewHistoryButton = new JButton("Sale History");
        viewHistoryButton.setBounds(160, 280, 150, 30);
        viewHistoryButton.addActionListener(this);
        viewHistoryButton.setBackground(Color.black);
        viewHistoryButton.setForeground(Color.WHITE);
        add(viewHistoryButton);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i22 = i11.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel imgg = new JLabel(i33);
        imgg.setBounds(350, 10, 600, 400);
        add(imgg);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/LoginB.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(0, 0, 600, 300);
        add(img);

        setSize(600, 350);
        setLocation(450, 200);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveSaleHistory();
        } else if (e.getSource() == backButton) {
            new HomePage().setVisible(true);
            dispose();
        } else if (e.getSource() == viewHistoryButton) {
            List<SaleHistory> loadedHistory = SaleHistoryPage.deserializeHistory();
            new SaleHistoryPage(loadedHistory).setVisible(true);
        }
    }

    private void saveSaleHistory() {
        String category = (String) categoryDropdown.getSelectedItem();
        int salePercentage = (int) saleDropdown.getSelectedItem();
        String startDateText = startDateField.getText();
        String endDateText = endDateField.getText();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateText);
            Date endDate = dateFormat.parse(endDateText);

            SaleHistory saleHistory = new SaleHistory(category, salePercentage, startDate, endDate);
            SaleHistoryPage.serializeHistory(saleHistory);

            System.out.println("Category: " + category);
            System.out.println("Sale Percentage: " + salePercentage);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);

            // Clear text inputs after saving
            clearTextInputs();

        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearTextInputs() {
        startDateField.setValue(null);
        endDateField.setValue(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddSalePage();
        });
    }
}
