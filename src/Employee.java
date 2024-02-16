import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String dob;
    private String position;
    private String salary;
    private String password;

    public Employee() {
        // Default constructor
    }

    public Employee(String name, String dob, String position, String salary, String password) {
        this.name = name;
        this.dob = dob;
        this.position = position;
        this.salary = salary;
        this.password = password;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", position='" + position + '\'' +
                ", salary='" + salary + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
