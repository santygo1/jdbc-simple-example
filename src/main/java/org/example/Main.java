package org.example;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Danil
 * @created 28.12.2022 - 3:08
 */
public class Main {

    public static void main(String[] args) {

        PersonDAO personDAO;
        try {
            personDAO = new PersonDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input command: add, find, showAll");
        while (scanner.hasNext()){
            String currentLine = scanner.nextLine();
            switch (currentLine) {
                case "add" -> {
                    System.out.print("Input data to add person: ");
                    String name = scanner.next();
                    String surname = scanner.next();
                    int age = Integer.parseInt(scanner.next());
                    Person person = new Person(0, name, surname, age);

                    personDAO.add(person);
                }

                case "find" -> {
                    System.out.print("Input data to find person:");
                    String name = scanner.next();
                    String surname = scanner.next();
                    int age = Integer.parseInt(scanner.next());
                    Person person = new Person(0, name, surname, age);

                    int foundedID = personDAO.getID(person);
                    System.out.println(foundedID != 0 ?
                            "Person was found at " + foundedID + " line" : "Nothing was found");
                }
                case "showAll" ->{
                    personDAO.getAll().forEach(System.out::println);
                }
                default -> {
                    System.out.println("Wrong command");
                }
            }


        }
    }
}
