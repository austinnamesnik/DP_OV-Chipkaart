package P4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static void testOVChipkaartDAO(OVChipkaartDAO ovdao, ReizigerDAO rdao) {
        System.out.println("\n------- Test OVChipkaartDAO -------");

        // Haal alle chipkaarten op uit de database
        List<OVChipkaart> ovs = ovdao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende chipkaarten:");
        for (OVChipkaart ov : ovs) {
            System.out.println(ov.toString());
        }
        System.out.println();

        // Maak een nieuwe chipkaart aan en persisteer deze in de database
        OVChipkaart mine = new OVChipkaart(110504, Date.valueOf("2024-07-27"), 2, 25, 27);
        System.out.print("[Test] Eerst " + ovs.size() + " chipkaarten, na OVChipkaartDAO.save() ");
        ovdao.save(mine);
        ovs = ovdao.findAll();
        System.out.println(ovs.size() + " chipkaarten\n");

        // Verwijder de toegevoegde chipkaart uit de database
        System.out.print("[Test] Eerst " + ovs.size() + " adressen, na OVChipkaart.delete() ");
        ovdao.delete(mine);
        ovs = ovdao.findAll();
        System.out.println(ovs.size() + " chipkaarten\n");

        // Updaten van een bestaand chipkaart in de database
        Reiziger reiziger = rdao.findById(2);
        OVChipkaart ov = new OVChipkaart(35283, Date.valueOf("2018-05-31"), 2, 50.00, 2);
        System.out.print("[Test] Chipkaart is eerst:\n" + ovdao.findByKaartnummer(35283) + "\n[Test] En na ReizigerDAO.update():\n");
        ovdao.update(ov);
        ov = ovdao.findByKaartnummer(35283);
        System.out.println(ov + "\n");
    }

    public static void main(String[] args) throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        ReizigerDAO rdao = new ReizigerDAOPsql(myConn);
        OVChipkaartDAO ovdao = new OVChipkaartDAOPsql(myConn);
        testOVChipkaartDAO(ovdao, rdao);
    }
}
