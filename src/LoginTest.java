import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.*;
import java.util.ArrayList;

public class LoginTest {

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        // Create a mock input stream with serialized data of a valid employee
        ByteArrayInputStream mockInput = new ByteArrayInputStream("validUsername\nvalidPassword".getBytes());
        System.setIn(mockInput);

        // Create a mock serialized file with the same data
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee_data.ser"))) {
            Employee mockEmployee = new Employee("validUsername", "01-01-1990", "Manager", "50000", "validPassword");
            oos.writeObject(mockEmployee);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test setup");
        }

        // Run the main method to simulate user input
        Login.main(new String[]{});

        // Check if the static method authenticateUser returns true for the provided valid credentials
        assertTrue(Login.authenticateUser("validUsername", "validPassword"));
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() {
        // Create a mock input stream with serialized data of an invalid employee
        ByteArrayInputStream mockInput = new ByteArrayInputStream("invalidUsername\ninvalidPassword".getBytes());
        System.setIn(mockInput);

        // Create a mock serialized file with different data
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee_data.ser"))) {
            Employee mockEmployee = new Employee("validUsername", "01-01-1990", "Manager", "50000", "validPassword");
            oos.writeObject(mockEmployee);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test setup");
        }

        // Run the main method to simulate user input
        Login.main(new String[]{});

        // Check if the static method authenticateUser returns false for the provided invalid credentials
        assertFalse(Login.authenticateUser("invalidUsername", "invalidPassword"));
    }

    @Test
    public void testAuthenticateUser_MultipleEmployees_ValidCredentials() {
        // Create a mock input stream with serialized data of multiple employees
        ByteArrayInputStream mockInput = new ByteArrayInputStream("validUsername\nvalidPassword".getBytes());
        System.setIn(mockInput);

        // Create a mock serialized file with multiple employees
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employee_data.ser"))) {
            Employee mockEmployee1 = new Employee("validUsername", "01-01-1990", "Manager", "50000", "validPassword");
            Employee mockEmployee2 = new Employee("anotherUser", "02-02-1990", "Supervisor", "45000", "anotherPassword");

            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(mockEmployee1);
            employees.add(mockEmployee2);

            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred during test setup");
        }

        // Run the main method to simulate user input
        Login.main(new String[]{});

        // Check if the static method authenticateUser returns true for the provided valid credentials
        assertTrue(Login.authenticateUser("validUsername", "validPassword"));
    }
}