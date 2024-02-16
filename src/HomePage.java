import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame implements ActionListener {

    JButton addProductButton, viewProductsButton, addSaleButton, logoutButton;

    public HomePage() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Added the label for "Warehouse Management System"
        JLabel systemLabel = new JLabel("Warehouse Management System");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        systemLabel.setBounds(100, 0, 300, 30);
        add(systemLabel);

        
        viewProductsButton = new JButton("Products");
        viewProductsButton.setBounds(50, 50, 200, 30);
        viewProductsButton.addActionListener(this);
        viewProductsButton.setBackground(Color.black);
        viewProductsButton.setForeground(Color.WHITE);
        add(viewProductsButton);

        addSaleButton = new JButton("Add Sale");
        addSaleButton.setBounds(50, 100, 200, 30);
        addSaleButton.addActionListener(this);
        addSaleButton.setBackground(Color.black);
        addSaleButton.setForeground(Color.WHITE);
        add(addSaleButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 150, 200, 30);
        logoutButton.addActionListener(this);
        logoutButton.setBackground(Color.black);
        logoutButton.setForeground(Color.WHITE);
        add(logoutButton);

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

        setSize(600, 300);
        setLocation(450, 200);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProductsButton) {
            // Navigate to ViewProductsPage when the "View Products" button is pressed
            new ViewProductsPage().setVisible(true);
        } else if (e.getSource() == addSaleButton) {
            new AddSalePage().setVisible(true);
        } else if (e.getSource() == logoutButton) {
            new Login().setVisible(true);
            dispose();

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePage();
        });
    }
}