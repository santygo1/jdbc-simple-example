package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Danil
 * @created 28.12.2022 - 3:08
 */
public class PersonDAO {

    private Connection connection;

    /* bad practice config */

    // jdbc:db://localhost:port/db_name
    private static final String URL = "jdbc:postgresql://localhost:5432/DVFUHomework";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "spira2002";

    // Статический блок подключения драйвера базы
    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    PersonDAO() throws SQLException {
        // Инициализация подключения к базе
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public List<Person> getAll(){
        List<Person> personsDataFromTable = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM person");

            while(rs.next()){
                int ID = rs.getInt("ID");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                int age = rs.getInt("Age");

                Person person = new Person(ID, name, surname, age);
                personsDataFromTable.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return personsDataFromTable;
    }

    public void add(Person person){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO person VALUES (DEFAULT,?, ?, ? )");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setInt(3, person.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getID(Person person){
        int result = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT ID FROM person WHERE" +
                    " name = ? AND " +
                    " surname=? AND " +
                    " age=?");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSurname());
            preparedStatement.setInt(3, person.getAge());

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            result = rs.getInt("ID");
        } catch (SQLException e) {
            result = 0;
        }
        return result;
    }
}
