import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class Login extends JFrame implements ActionListener {

    JTextField tusername;
    JPasswordField tpassword;
    JButton login, newEmployee;

    Login() {
        JFrame frame = new JFrame("Warehouse Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Added the label for "Warehouse Management System"
        JLabel systemLabel = new JLabel("Warehouse Management System");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        systemLabel.setBounds(100, 0, 300, 30);
        add(systemLabel);

        JLabel username = new JLabel("Username");
        username.setBounds(40, 50, 100, 30);
        add(username);

        tusername = new JTextField();
        tusername.setBounds(150, 50, 150, 30);
        add(tusername);

        JLabel password = new JLabel("Password");
        password.setBounds(40, 90, 100, 30);
        add(password);

        tpassword = new JPasswordField();
        tpassword.setBounds(150, 90, 150, 30);
        add(tpassword);

        login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.black);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        newEmployee = new JButton("New Employee");
        newEmployee.setBounds(150, 180, 150, 30);
        newEmployee.setBackground(Color.black);
        newEmployee.setForeground(Color.WHITE);
        newEmployee.addActionListener(this);
        add(newEmployee);

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
        if (e.getSource() == login) {
            try {
                String username = tusername.getText();
                String password = new String(tpassword.getPassword());

                if (authenticateUser(username, password)) {
                    setVisible(false);
                    new HomePage();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }

            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == newEmployee) {
            setVisible(false);
            new EmployeeAdditionPage();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employee_data.ser"))) {
            Object obj;
            while ((obj = ois.readObject()) != null) {
                if (obj instanceof Employee) {
                    Employee employee = (Employee) obj;
                    if (employee.getName().equals(username) && employee.getPassword().equals(password)) {
                        return true;
                    }
                } else if (obj instanceof ArrayList<?>) {
                    // Handle the case where the file contains an ArrayList of Employee objects
                    ArrayList<Employee> employees = (ArrayList<Employee>) obj;
                    for (Employee employee : employees) {
                        if (employee.getName().equals(username) && employee.getPassword().equals(password)) {
                            return true;
                        }
                    }
                }
            }
        } catch (EOFException e) {
            // Ignore, end of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login();
        });
    }
}