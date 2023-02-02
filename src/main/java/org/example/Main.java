package org.example;

import Repo.Repository;
import Views.MenuBase;

import java.sql.SQLException;
import java.util.*;

public class Main {
    static MenuBase menuBase;
    static Repository repo;
    public static void main(String[] args) throws SQLException {
        menuBase = new MenuBase();
        repo = new Repository();
        System.out.println("Welcome to Venue Management System");
        System.out.println("Here is the list of hall available");
        try {
            repo.fetchHall();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    Main m = new Main();
        m.menu();

    }
    public void menu() throws SQLException {
        menuBase = new MenuBase();
        repo = new Repository();
        System.out.println("Press as per following");
        System.out.println("a. Book Hall");
        System.out.println("b. View booked Hall");
        System.out.println("c. Edit Hall");
        System.out.println("d. Delete booked Hall");
        System.out.println("e. View most booked hall");
        System.out.print("Enter what you want to do:  ");
        Scanner inp = new Scanner(System.in);
        String option = inp.nextLine();
        System.out.println("You have selected: " + option);
        switch (option) {
            case "a":
                menuBase.create();
                System.out.println("Successfully Booked!!!");
                call();
                break;

            case "b":
                System.out.println("Here are booking done");
                repo.fetchAllBooking();
                call();
                break;

            case "c":
//                System.out.println("You can now edit booking");
                try {
                    menuBase.edit();
                    System.out.println("Successfully Updated!!!");
                    call();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "d":
//                System.out.println("You can now delete booking");
                try {
                    menuBase.delete();
                    System.out.println("Successfully deleted");
                    call();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "e":
                System.out.println("Here is the list of booking done per hall");
                try {
                    repo.mostBookedHall();
                    call();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    public void call() throws SQLException {
        Scanner inp = new Scanner(System.in);
        System.out.println("Do you want to continue? \t Enter yes or no");
        String co = inp.nextLine();
        if(co.equals("yes"))
            menu();
        else
            System.out.println("Thank you");
    }
}
