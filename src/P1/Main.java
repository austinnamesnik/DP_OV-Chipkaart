package P1;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try {
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from reiziger");
            while (myRs.next()) {
                if (myRs.getString("tussenvoegsel") != null) {
                    System.out.println("#" + myRs.getString("reiziger_id") + ": " + myRs.getString("voorletters")
                            + ". " + myRs.getString("tussenvoegsel") + " " + myRs.getString("achternaam")
                            + " (" + myRs.getDate("geboortedatum") + ")");
                } else {
                    System.out.println("#" + myRs.getString("reiziger_id") + ": " + myRs.getString("voorletters")
                            + ". " + myRs.getString("achternaam")
                            + " (" + myRs.getDate("geboortedatum") + ")");
                }
            }
            myRs.close();
            myStmt.close();

            System.out.println();

            Statement myStmt2 = myConn.createStatement();
            ResultSet myRs2 = myStmt2.executeQuery("select * from reiziger where geboortedatum='2002-12-03'");
            while (myRs2.next()) {
                System.out.println(myRs2.getString("achternaam"));
            }
            myRs2.close();
            myStmt.close();

            myConn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
