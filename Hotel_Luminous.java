//import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Scanner;

public class Hotel_Luminous {

//    private static final String url = "";
    private static final String url = "jdbc:mysql://localhost:3306/hotel_bookings";
    private static final String username = "root";
    private static final String password = "unknownbuddy";

    static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Class Loaded successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Established Successfully");

            while(true){
                System.out.println("Wel-Come to Luminous Hotel's");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Booking");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("6. Exit");
                System.out.println("Enter the choice : ");
                int ch = sc.nextInt();

                switch (ch){
                    case 1:
                        booKings(conn,sc);
                        break;
                    case 2:
                        showReservations(conn,sc);
                        break;
                    case 3:
                        roomNumber(conn,sc);
                        break;
                    case 4:
                        updateBookings(conn,sc);
                        break;
                    case 5:
                        deleteBookings(conn,sc);
                        break;
                    case 6:
                        exit(conn,sc);
                       return;
                    default :
                        System.out.println("Enter the valid choice");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void booKings(Connection conn, Scanner sc) {

        System.out.println("Enter the Name : ");
        String name = sc.next();

        System.out.println("Enter the Room number : ");
        int room_num = sc.nextInt();

        System.out.println("Enter Contact Number : ");
        String contact_num = sc.next();

        String query = "Insert into reservations(guest_name,room_number,contact_number) values('"+name+"',"+room_num+",'"+contact_num+"');";

        try{
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);

            if(result > 0){
                System.out.println("Insertion Successfully done "+result+" row(s) affected");
            }else {
                System.out.println("Insertion Failed");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void showReservations(Connection conn, Scanner sc) {

        String Query = "SELECT * FROM RESERVATIONS; ";
       try{
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(Query);

           System.out.println();
           System.out.println("================================");
           System.out.println();
           while(rs.next()){

               int id = rs.getInt("reservation_id");
               String g_name = rs.getString("guest_name");
               int r_number = rs.getInt("room_number");
               String c_number = rs.getString("contact_number");
               String date = rs.getTimestamp("reservation_date").toString();


               System.out.println("Reservation_id = "+id);
               System.out.println("Guest_Name = "+g_name);
               System.out.println("Room_number = "+r_number);
               System.out.println("Contact_number = "+c_number);
               System.out.println("Reservation_Date = "+date);

               System.out.println();
               System.out.println("================================");
               System.out.println();
           }


       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
    }

    private static void roomNumber(Connection conn, Scanner sc) {
        System.out.println("Enter Reservation id = ");
        int id = sc.nextInt();
        String Query = "SELECT room_number,guest_name FROM reservations WHERE reservation_id = "+id+";";

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Query);

            while (rs.next()) {
                int r_id = rs.getInt("room_number");
                String g_name = rs.getString("guest_name");
                System.out.println("Room Number = " + r_id);
                System.out.println("Guest_name = "+g_name);
            }

        } catch (SQLException e) {
            System.out.println("Exception : "+e.getMessage());
        }
    }

    private static void deleteBookings(Connection conn, Scanner sc) {

        System.out.println("Enter the reservation id to delete record : ");
        int r_id = sc.nextInt();
        String Query = "DELETE FROM reservations WHERE reservation_id = '"+r_id+"';";

        try{
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(Query);

            if(rs > 0){
                System.out.println("Deletion is completed");
            }
            else{
                System.out.println("Deletion is not completed");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateBookings(Connection conn, Scanner sc) {

        System.out.println("Enter the choice for Updation of data");
        System.out.println("1. Name Updation");
        System.out.println("2. Room number Updation");
        System.out.println("3. Contact number");
        int ch = sc.nextInt();

        switch(ch){
            case 1:
                nameUpdate(conn,sc);
                break;
            case 2:
                roomNumberUpdate(conn,sc);
                break;
            case 3:
                contactNumber(conn,sc);
                break;
            default :
                System.out.println("Select right choice");
        }

    }

    private static int exit(Connection conn, Scanner sc) throws SQLException {
        conn.close();
        sc.close();
        return 0;
    }

    // Updation Methods of updateBookings
    private static void contactNumber(Connection conn, Scanner sc) {

        System.out.println("Enter the reservation id : ");
        int r_id = sc.nextInt();
        System.out.println("Enter the New Contact number");
        String new_contact = sc.next();
        String Query = "UPDATE reservations SET contact_number = '"+new_contact+"' WHERE reservation_id = "+r_id;

        try{

            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(Query);

            if(rs > 0){
                System.out.println("Updation is done");
            }
            else{
                System.out.println("Updation is not done");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void roomNumberUpdate(Connection conn, Scanner sc) {
        System.out.println("Enter the reservation id : ");
        int r_id = sc.nextInt();
        System.out.println("Enter the New room number");
        String new_room_number = sc.next();
        String Query = "UPDATE reservations SET room_number = '"+new_room_number+"' WHERE reservation_id = "+r_id;

        try{

            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(Query);

            if(rs > 0){
                System.out.println("Updation is done");
            }
            else{
                System.out.println("Updation is not done");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void nameUpdate(Connection conn, Scanner sc) {
        System.out.println("Enter the reservation id : ");
        int r_id = sc.nextInt();
        System.out.println("Enter the New Name : ");
        String new_name = sc.next();
        String Query = "UPDATE reservations SET guest_name = '"+new_name+"' WHERE reservation_id = "+r_id;

        try{

            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(Query);

            if(rs > 0){
                System.out.println("Updation is done");
            }
            else{
                System.out.println("Updation is not done");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
