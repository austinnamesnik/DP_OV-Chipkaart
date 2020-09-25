package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "insert into reiziger "
                    + " values ('" + reiziger.getReiziger_id() + "', '" + reiziger.getVoorletters() + "', '" + reiziger.getTussenvoegsel() + "', '" + reiziger.getAchternaam()
                    + "', '" + reiziger.getGeboortedatum() + "')";
            return stmt.executeUpdate(sql) > 0;

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "update reiziger set voorletters = '" + reiziger.getVoorletters() + "', tussenvoegsel = '" + reiziger.getTussenvoegsel() + "', achternaam = '" + reiziger.getAchternaam() + "', geboortedatum = '" + reiziger.getGeboortedatum().toString() +
                    "' where reiziger_id = " + reiziger.getReiziger_id();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "delete from reiziger where reiziger_id = " + reiziger.getReiziger_id();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from reiziger where reiziger_id in ('" + id + "')";
            ResultSet myRs = stmt.executeQuery(sql);

            if (myRs.next()) {
                int rId = myRs.getInt("reiziger_id");
                String rVl = myRs.getString("voorletters");
                String rTv = myRs.getString("tussenvoegsel");
                String rAn = myRs.getString("achternaam");
                Date rGb = myRs.getDate("geboortedatum");

                return new Reiziger(rId, rVl, rTv, rAn, rGb);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from reiziger where geboortedatum in ('" + datum + "')";
            ResultSet myRs = stmt.executeQuery(sql);
            List<Reiziger> list = new ArrayList<>();

            return getReizigers(myRs, list);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigerList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from reiziger";
            ResultSet myRs = stmt.executeQuery(sql);
            return getReizigers(myRs, reizigerList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Reiziger> getReizigers(ResultSet myRs, List<Reiziger> list) throws SQLException {
        while (myRs.next()) {
            int rId = myRs.getInt("reiziger_id");
            String rVl = myRs.getString("voorletters");
            String rTv = myRs.getString("tussenvoegsel");
            String rAn = myRs.getString("achternaam");
            Date rGb = myRs.getDate("geboortedatum");

            Reiziger current = new Reiziger(rId, rVl, rTv, rAn, rGb);
            list.add(current);
        }
        return list;
    }
}
