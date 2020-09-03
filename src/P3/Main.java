package P3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n------- Test AdresDAO -------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres thuis = new Adres(88, "3984CH", "9", "Kievitsbloem", "Odijk", 27);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(thuis);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Verwijder het toegevoegde adres uit de database
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(thuis);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");

        // Updaten van een bestaande reiziger in de database
        Reiziger reiziger = new Reiziger(1, "AB", null, "Namesnik", Date.valueOf("2001-09-17"));
        Adres thuis2 = new Adres(1, "3984CH", "9", "Kievitsbloem", "Odijk", 1);
        System.out.print("[Test] Adres is eerst:\n" + adao.findByReiziger(reiziger) + "\n[Test] En na ReizigerDAO.update():\n");
        adao.update(thuis2);
        thuis2 = adao.findByReiziger(reiziger);
        System.out.println(thuis2 + "\n");

        // Haal adres op met adres_id = 4
        System.out.println("[Test] Adres met ID = 4, geeft " + adao.findById(4));
    }

    public static void main(String[] args) throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        AdresDAOPsql newone = new AdresDAOPsql(myConn);
        testAdresDAO(newone);
    }
}
