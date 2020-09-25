package P5;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static void testProductDAO(OVChipkaartDAO ovdao, ProductDAO pdao) {
        System.out.println("\n------- Test ProductDAO -------");

        // Haal alle producten op uit de database
        List<Product> productList = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : productList) {
            System.out.println(p.toString());
            System.out.println();
        }
        System.out.println();

        // Maak een nieuw product aan en persisteer deze in de database
        Product myProd = new Product(19, "Test abonnement", "Dit abonnement is alleen om mee te testen", 19.16);
        System.out.println("[Test] Eerst " + productList.size() + " producten, na ProductDAO.save():");
        pdao.save(myProd);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");
        Product myProd2 = new Product(16, "Tweede Test", "Dit is het tweede abonnement", 16.19);

        OVChipkaart myOV = ovdao.findByKaartnummer(110504);
        OVChipkaart myOV2 = ovdao.findByKaartnummer(68514);
        myProd2.addOvChipkaart(myOV);
        myProd2.addOvChipkaart(myOV2);
        productList = pdao.findAll();
        System.out.println("[Test] Eerst " + productList.size() + " producten, na ProductDAO.save():");
        pdao.save(myProd2);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");

        // Tweede keer de ProductDAO.findAll() methode testen
        productList = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft nu:\n");
        for (Product p : productList) {
            System.out.println(p.toString() + "\n");
        }
        System.out.println();

        // Methode ProductDAO.findByOVChipkaart() testen
        List<Product> products = pdao.findByOVChipkaart(myOV);
        System.out.println("[Test] ProductDAO.findByOVChipkaart(myOV) geeft de volgende producten:\n");
        for (Product p : products) {
            System.out.println(p.toString());
        }
        System.out.println();
        myOV.addProduct(myProd);
        // OVChipkaartDAO.update() wordt hier ook getest!!! \/ \/ \/
        ovdao.update(myOV);
        System.out.println("[Test] ProductDAO.findByOVChipkaart(myOV) geeft de volgende producten na het toevoegen van een ander product via de OVChipkaartDAO:\n");
        products = pdao.findByOVChipkaart(myOV);
        for (Product p : products) {
            System.out.println(p.toString());
        }
        System.out.println();


        // Verwijder de toegevoegde producten
        System.out.println("[Test] Eerst " + productList.size() + " producten, na ProductDAO.delete() ");
        pdao.delete(myProd);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");

        System.out.println("[Test] Eerst " + productList.size() + " producten, na ProductDAO.delete() ");
        pdao.delete(myProd2);
        productList = pdao.findAll();
        System.out.println(productList.size() + " producten\n");
    }

    public static void main(String[] args) throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "postgres");
        OVChipkaartDAO ovdao = new OVChipkaartDAOPsql(myConn);
        ProductDAO pdao = new ProductDAOPsql(myConn);
        testProductDAO(ovdao, pdao);
        myConn.close();
    }
}
