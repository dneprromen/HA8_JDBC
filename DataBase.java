import java.sql.*;

public class DataBase {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    public void establishConnectionAndRead() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Driver is loaded.");
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/cdphw", "root", "admin");
        System.out.println("Connection is established.");
        statement = connect.createStatement();
        refreshResultSet();
    }

    private void refreshResultSet() throws SQLException {
        resultSet = statement.executeQuery("select * from test_table");
    }

    public void createNewUserInDataBase(String userName, int age, int salary) throws SQLException, InterruptedException {
        preparedStatement = connect.prepareStatement("INSERT INTO test_table (name, age, salary) values (?, ?, ?)");
        preparedStatement.setString(1, userName);
        preparedStatement.setInt(2, age);
        preparedStatement.setInt(3, salary);
        preparedStatement.executeUpdate();
        Thread.sleep(2000);
        refreshResultSet();
        System.out.println("New user " + userName + " was created");
    }

    public void printDataBase() throws SQLException {
        resultSet.first();
        while (resultSet.next()) {
            String userName = resultSet.getString("name");
            int age = resultSet.getInt("age");
            int salary = resultSet.getInt("salary");
            System.out.println("___\nUser: " + userName);
            System.out.println("Age: " + age);
            System.out.println("Salary: " + salary + "\n");
        }
    }

    public int getUserSalary(String userName) throws SQLException {
        resultSet.first();
        while (resultSet.next()) {
            if (resultSet.getString("name").equals(userName)) {
                return resultSet.getInt("salary");
            }
        }
        throw new IllegalStateException("Name " + userName + " doesn't exist");
    }

    public void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
}
