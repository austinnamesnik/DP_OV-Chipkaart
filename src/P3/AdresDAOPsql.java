package P3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "insert into adres "
                    + " values ('" + adres.getAdres_id() + "', '" + adres.getPostcode() + "', '" + adres.getHuisnummer() + "', '" + adres.getStraat()
                    + "', '" + adres.getWoonplaats() + "', '" + adres.getReiziger_id() + "')";
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "update adres set postcode = '" + adres.getPostcode() + "', huisnummer = '" + adres.getHuisnummer() + "', straat = '" + adres.getStraat() + "', woonplaats = '" + adres.getWoonplaats() +
                    "' where adres_id = " + adres.getAdres_id();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "delete from adres where adres_id = " + adres.getAdres_id();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from adres where reiziger_id in ('" + reiziger.getReiziger_id() + "')";
            ResultSet myRs = stmt.executeQuery(sql);

            if (myRs.next()) {
                int aId = myRs.getInt("adres_id");
                String aPc = myRs.getString("postcode");
                String aHn = myRs.getString("huisnummer");
                String aSt = myRs.getString("straat");
                String aWp = myRs.getString("woonplaats");

                return new Adres(aId, aPc, aHn, aSt, aWp, reiziger.getReiziger_id());
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Adres findById(int id) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from adres where adres_id in ('" + id + "')";
            ResultSet myRs = stmt.executeQuery(sql);

            if (myRs.next()) {
                int aId = myRs.getInt("adres_id");
                String aPc = myRs.getString("postcode");
                String aHn = myRs.getString("huisnummer");
                String aSt = myRs.getString("straat");
                String aWp = myRs.getString("woonplaats");
                int rId = myRs.getInt("reiziger_id");

                return new Adres(aId, aPc, aHn, aSt, aWp, rId);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        List<Adres> adresList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from adres";
            ResultSet myRs = stmt.executeQuery(sql);
            while (myRs.next()) {
                int aId = myRs.getInt("adres_id");
                String aPc = myRs.getString("postcode");
                String aHn = myRs.getString("huisnummer");
                String aSt = myRs.getString("straat");
                String aWp = myRs.getString("woonplaats");
                int rId = myRs.getInt("reiziger_id");

                Adres current = new Adres(aId, aPc, aHn, aSt, aWp, rId);
                adresList.add(current);
            }
            return adresList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
