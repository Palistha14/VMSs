package Views;

import Repo.Repository;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuBase {
    Repository repo;
    Scanner in;
    public void create() throws SQLException {
        repo = new Repository();
        in = new Scanner(System.in);
        System.out.println("At first, please enter date of booking");
        String date =  in.nextLine();
        System.out.println("Please enter hall number");
        int id = Integer.parseInt(in.nextLine());
        System.out.println("Checking the availability!!!");
        repo.bookVenue(date, id);
    }
    public void delete() throws SQLException {
        repo = new Repository();
        System.out.println("Please enter the id of the booking you want to delete");
        in = new Scanner(System.in);
        int id = Integer.parseInt(in.nextLine());
        repo = new Repository();
        try {
            repo.deleteBooking(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void edit() throws SQLException {
        repo = new Repository();
        System.out.println("Here is the list of bookings");
        repo.fetchAllBooking();
        System.out.println("Please enter the id of the booking you want to edit");
        in = new Scanner(System.in);
        int id = Integer.parseInt(in.nextLine());
        repo = new Repository();
        System.out.println("Here is your previous data:");
        repo.fetchByID(id);
        System.out.println("Please enter the name of person to book");
        String name =  in.nextLine();
        System.out.println("Please enter description for booking");
        String description =  in.nextLine();
        System.out.println("Please enter your phone number");
        String phone =  in.nextLine();
        System.out.println("Please enter date of booking");
        String date =  in.nextLine();
        System.out.println("Please enter hall number");
        int ids = Integer.parseInt(in.nextLine());
        repo.updateVenue(name, description, phone, date, ids, id);

    }

}
