import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.MaskFormatter;

public class EmployeeAdditionPage extends JFrame {

    JTextField tEmployeeName, tEmployeeDOB, tEmployeeSalary, tPassword;
    JComboBox<String> positionDropdown;
    JButton saveButton, backButton;

    // List to store multiple employees
    private List<Employee> employeeList;

    public EmployeeAdditionPage() {
        setTitle("Employee Addition");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize the employee list
        employeeList = new ArrayList<>();

        // Added the label for "Warehouse Management System"
        JLabel systemLabel = new JLabel("Employee Addition");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        systemLabel.setBounds(100, 0, 300, 30);
        add(systemLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(40, 30, 100, 30);
        add(nameLabel);

        tEmployeeName = new JTextField();
        tEmployeeName.setBounds(150, 30, 150, 30);
        add(tEmployeeName);

        JLabel dobLabel = new JLabel("Date of Birth ");
        dobLabel.setBounds(40, 70, 200, 30);
        add(dobLabel);

        try {
            MaskFormatter dobFormatter = new MaskFormatter("####-##-##");
            tEmployeeDOB = new JFormattedTextField(dobFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            tEmployeeDOB = new JTextField();
        }
        tEmployeeDOB.setBounds(150, 70, 150, 30);
        add(tEmployeeDOB);

        JLabel positionLabel = new JLabel("Position");
        positionLabel.setBounds(40, 110, 100, 30);
        add(positionLabel);

        String[] positions = {"Manager", "Staff Employee"};
        positionDropdown = new JComboBox<>(positions);
        positionDropdown.setBounds(150, 110, 150, 30);
        add(positionDropdown);

        JLabel salaryLabel = new JLabel("Salary");
        salaryLabel.setBounds(40, 150, 100, 30);
        add(salaryLabel);

        tEmployeeSalary = new JTextField();
        tEmployeeSalary.setBounds(150, 150, 150, 30);
        add(tEmployeeSalary);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(40, 190, 100, 30);
        add(passwordLabel);

        tPassword = new JPasswordField();
        tPassword.setBounds(150, 190, 150, 30);
        add(tPassword);

        saveButton = new JButton("Save");
        saveButton.setBounds(150, 230, 150, 30);
        saveButton.setBackground(Color.black);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method to handle save functionality
                saveButtonClicked();
            }
        });
        add(saveButton);

        backButton = new JButton("Back");
        backButton.setBounds(150, 270, 150, 30);
        backButton.setBackground(Color.black);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current frame (EmployeeAdditionPage)
                dispose();

                // Show the login frame
                new Login();
            }
        });
        add(backButton);

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

        setSize(600, 340);
        setLocation(700, 400);
        setLayout(null);
        setVisible(true);
    }

    private void saveButtonClicked() {
        // Implement the logic to save employee data
        String name = tEmployeeName.getText();
        String dob = tEmployeeDOB.getText();
        String position = (String) positionDropdown.getSelectedItem();
        String salary = tEmployeeSalary.getText();
        String password = tPassword.getText();

        // Create an Employee object
        Employee employee = new Employee(name, dob, position, salary, password);

        // Add the employee to the list
        employeeList.add(employee);

        // Save the list of employees to the file
        saveEmployeeData();

        // For now, let's print the data
        System.out.println("Employee data saved:");
        System.out.println(employee);

        // You can add more logic here as needed

        // Go back to the login page
        navigateBackToLogin();
    }

    private void navigateBackToLogin() {
        // Close the current frame (EmployeeAdditionPage)
        dispose();

        // Show the login frame
        new Login();
    }


    private void saveEmployeeData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee_data.ser"))) {
            // Write the entire list of employees to the file
            oos.writeObject(employeeList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeAdditionPage();
        });
    }
}