package Repo;

import java.sql.*;

public class Repository {
    public Connection con;
    public PreparedStatement p;
    public ResultSet rs;
    public Statement st;
    public int total;

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
        System.out.println("Name\t\tDescription\t\tSize\t\tPrice");
        while (rs.next()) {

//            int id = rs.getInt("id");
            String name = rs.getString("name");
            int size = rs.getInt("size");
            int price = rs.getInt("Price");
            String description = rs.getString("Description");
            System.out.println(name
                    + "\t\t" + description + "\t" + size + " people\t"+price);
        }
        con.close();
    }
    public void bookVenue(String name, String description, String phone, String date, int id) {
        connection();
        String query1 = "INSERT INTO venue(name, description, phone, date, area_id, isDeleted) VALUES ('" + name + "', '" + description + "', '" + phone + "', '" + date + "', " + id + ", 0)";
        String count = "SELECT count FROM area WHERE id = " + id;
        try {
            st = con.createStatement();
            st.executeUpdate(query1);
            p = con.prepareStatement(count);
            rs = p.executeQuery();
            while (rs.next()) {
                total = rs.getInt("count");
                total++;
            }
            String countUpdate = "UPDATE area SET count = (" + total + ")WHERE area.id = " + id;
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

