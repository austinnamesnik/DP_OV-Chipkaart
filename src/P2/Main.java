package P2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Verwijder de toegevoegde reiziger uit de database
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Updaten van een bestaande reiziger in de database
        String gbdatum2 = "2002-09-17";
        Reiziger GvR = new Reiziger(1, "G", "van", "Reewijk", Date.valueOf(gbdatum2));
        System.out.print("[Test] Reiziger is eerst:\n" + rdao.findById(1) + "\n[Test] En na ReizigerDAO.update():\n");
        rdao.update(GvR);
        GvR = rdao.findById(1);
        System.out.println(GvR + "\n");

        // Zoeken naar reizigers met een bepaalde geboortedatum uit de database
        String gbdatum3 = "2002-12-03";
        List<Reiziger> reizigerList = rdao.findByGbDatum(gbdatum3);
        System.out.println("[Test] Alle reizigers met geboortedatum '2002-12-03': ");
        for (Reiziger reiziger : reizigerList) {
            System.out.println(reiziger.toString());
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        ReizigerDAOPsql newone = new ReizigerDAOPsql(myConn);
        testReizigerDAO(newone);
    }
}
