package Repo;

import Views.MenuBase;
import org.example.Main;

import java.sql.*;
import java.util.Scanner;

public class Repository {
    public Connection con;
    public PreparedStatement p;
    public Statement stmt;
    public ResultSet rs;
    public Statement st;
    public int total;
    public Main m;
    Scanner in;

    public void connection() {
        con = null;
        String connectionUrl = "jdbc:mysql://localhost:3306/venue";
        try {
            con = DriverManager.getConnection(connectionUrl, "root", "");
            if (con == null)
                System.out.println("Database connection issue");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fetchHall() throws SQLException {
        connection();
        String sql = "SELECT name, size, Description, Price from area";
        try {
            p = con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs = p.executeQuery();
        System.out.println("Name\t\tDescription\t\tSize");
        while (rs.next()) {

            String name = rs.getString("name");
            int size = rs.getInt("size");
            String description = rs.getString("Description");
            System.out.println(name
                    + "\t\t" + description + "\t" + size + " people\t");
        }
        con.close();
    }
    public void bookVenue(String date, int id) throws SQLException {
        connection();
        String check = "SELECT id FROM venue WHERE date ='"+ date +"' AND area_id =" +id+" AND isDeleted= 0";
        m = new Main();
        stmt = con.createStatement();
        rs = stmt.executeQuery(check);
        if(rs.next() == false)
        {
            try {
                in = new Scanner(System.in);
                System.out.println("Available!!!");
                System.out.println("Please enter the name of person to book");
                String name =  in.nextLine();
                System.out.println("Please enter description for booking");
                String description =  in.nextLine();
                System.out.println("Please enter your phone number");
                String phone =  in.nextLine();
                System.out.println("Please enter your number of people");
                int people = Integer.parseInt(in.nextLine());
                System.out.println("Please enter per plate cost");
                int per= Integer.parseInt(in.nextLine());
                int gross = (people * per);
                System.out.println("Total cost without discount is Rs. "+gross);
                System.out.println("Please enter discount amount");
                int discount = Integer.parseInt(in.nextLine());
                int totals = gross - discount;
                System.out.println("Total cost after discount is Rs. "+total);
                String query1 = "INSERT INTO venue(name, description, phone, date, area_id, isDeleted, total_cost, per_plate, discount, " +
                        "people) VALUES ('" + name + "', '" + description + "', '" + phone + "" +
                        "', '" + date + "', " + id + ", 0,"+totals+", "+per+", "+discount+", "+people+" )";
                String count = "SELECT count FROM area WHERE id = " + id;
                st = con.createStatement();
                st.executeUpdate(query1);
                p = con.prepareStatement(count);
                rs = p.executeQuery();
                while (rs.next()) {
                    total = rs.getInt("count");
                    total++;
                }
                String countUpdate = "UPDATE area SET count = (" + total + ")WHERE area.id = " + id;
                System.out.println("Here is the booking detail");
                System.out.println("Name: "+name);
                System.out.println("Description: "+description);
                System.out.println("Phone number: "+phone);
                System.out.println("Hall no: "+id);
                System.out.println("Per_Plate: "+per);
                System.out.println("Total People: "+people);
                System.out.println("Total Discount: "+discount);
                System.out.println("Total cost after discount: "+totals);
                st.executeUpdate(countUpdate);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            System.out.println("Hall "+ id+ " already booked for this "+ date + " date");
            m.call();
        }

    }
    public void updateVenue(String name, String description, String phone, String date, int id, int ids) {
        connection();
        String query1 = "UPDATE venue SET name = '" + name + "', description = '" + description + "',phone= '" + phone + "',date = '" + date + "',area_id = " + id + " WHERE id = "+ids;
        try {
            st = con.createStatement();
            st.executeUpdate(query1);
            System.out.println("Here is the updated data:");
            fetchAllBooking();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void fetchAllBooking() {
        connection();
        String sql = "SELECT id, name, description, phone, date, area_id from venue WHERE isDeleted = 0";
        try {
            p = con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs = p.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Id\tName\t\tDescription\t\tPhone\t\tDate\t\tHall");
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String name = null;
            String description = null;
            String phone = null;
            String date = null;
            try {
                int id = rs.getInt("id");
                name = rs.getString("name");
                description = rs.getString("description");
                phone = rs.getString("phone");
                date = rs.getString("date");
                int hall = rs.getInt("area_id");
                System.out.println(id + "\t" + name
                        + "\t" + description + "\t\t" + phone + "\t" + date + "\tHall " + hall);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void fetchByID(int d_id) {
        connection();
        String sql = "SELECT id, name, description, phone, date, area_id from venue WHERE isDeleted = 0 AND id = "+d_id;
        try {
            p = con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            rs = p.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Id\tName\t\tDescription\t\tPhone\t\tDate\t\tHall");
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String name = null;
            String description = null;
            String phone = null;
            String date = null;
            try {
                int id = rs.getInt("id");
                name = rs.getString("name");
                description = rs.getString("description");
                phone = rs.getString("phone");
                date = rs.getString("date");
                int hall = rs.getInt("area_id");
                System.out.println(id+"\t"+name
                        + "\t" + description + "\t\t" + phone+"\t" + date+"\tHall " + hall);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteBooking(int id) throws SQLException {
        connection();
        String del = "UPDATE venue SET isDeleted = 1 WHERE venue.id ="+id;
        st = con.createStatement();
        st.executeUpdate(del);
        con.close();
    }
    public void mostBookedHall() throws SQLException{
        connection();
        String sql = "SELECT name, size, count from area";
        try {
            p = con.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rs = p.executeQuery();
        System.out.println("Name\tSize\t\tBooked times");
        while (rs.next()) {

            String name = rs.getString("name");
            int size = rs.getInt("size");
            int count = rs.getInt("count");
            System.out.println(name
                    + "\t" + size + " people\t" + count +" times");
        }
        con.close();
    }
}

