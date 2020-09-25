package P5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {

    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "insert into ov_chipkaart "
                    + " values ('" + ovChipkaart.getKaart_nummer() + "', '" + ovChipkaart.getGeldig_tot() + "', '" + ovChipkaart.getKlasse() + "', '" + ovChipkaart.getSaldo()
                    + "', '" + ovChipkaart.getReiziger_id() + "')" + "; ";
            if (ovChipkaart.getProduct() != null) {
                for (Product p : ovChipkaart.getProduct()) {
                    sql += "INSERT INTO ov_chipkaart_product VALUES (" + ovChipkaart.getKaart_nummer() + ", " + p.getProduct_nummer() + ", " + null + ", " + null + "); ";
                }
            }
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "update ov_chipkaart set reiziger_id = '" + ovChipkaart.getReiziger_id() + "', geldig_tot = '" + ovChipkaart.getGeldig_tot() + "', klasse = '" + ovChipkaart.getKlasse() + "', saldo = '" + ovChipkaart.getSaldo() +
                    "' where kaart_nummer = " + ovChipkaart.getKaart_nummer() + "; ";
            if (ovChipkaart.getProduct() != null) {
                for (Product p : ovChipkaart.getProduct()) {
                    sql += "INSERT INTO ov_chipkaart_product VALUES (" + ovChipkaart.getKaart_nummer() + ", " + p.getProduct_nummer() + ", " + null + ", " + null + ") ON CONFLICT DO NOTHING; ";
                }
            }
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "delete from ov_chipkaart_product where kaart_nummer = " + ovChipkaart.getKaart_nummer() + "; " +
                    "DELETE FROM ov_chipkaart WHERE kaart_nummer = " + ovChipkaart.getKaart_nummer() + ";";
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OVChipkaart findByKaartnummer(int kaartnummer) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from ov_chipkaart where kaart_nummer = " + kaartnummer;
            ResultSet myRs = stmt.executeQuery(sql);
            if (myRs.next()) {
                int kn = myRs.getInt("kaart_nummer");
                Date gt = myRs.getDate("geldig_tot");
                int kl = myRs.getInt("klasse");
                int sa = myRs.getInt("saldo");
                int rid = myRs.getInt("reiziger_id");

                return new OVChipkaart(kn, gt, kl, sa, rid);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> chipList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String sql = "select * from ov_chipkaart";
            ResultSet myRs = stmt.executeQuery(sql);
            while (myRs.next()) {
                int kn = myRs.getInt("kaart_nummer");
                Date gt = myRs.getDate("geldig_tot");
                int kl = myRs.getInt("klasse");
                int sa = myRs.getInt("saldo");
                int rid = myRs.getInt("reiziger_id");

                OVChipkaart current = new OVChipkaart(kn, gt, kl, sa, rid);
                chipList.add(current);
            }
            return chipList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> chipList = new ArrayList<>();
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "select * from ov_chipkaart where reiziger_id in ('" + reiziger.getReiziger_id() + "')";
            ResultSet myRs = stmt.executeQuery(sql);

            while (myRs.next()) {
                int aId = myRs.getInt("kaart_nummer");
                Date aPc = myRs.getDate("geldig_tot");
                int aHn = myRs.getInt("klasse");
                int aSt = myRs.getInt("saldo");
                int aWp = myRs.getInt("reiziger_id");

                chipList.add(new OVChipkaart(aId, aPc, aHn, aSt, aWp));
            }
            return chipList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
