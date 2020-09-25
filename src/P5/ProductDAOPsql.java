package P5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "insert into product " +
                    "values (" + product.getProduct_nummer() + ", '" + product.getNaam() + "', '" + product.getBeschrijving() + "', " + product.getPrijs() + "); ";
            if (product.getOvChipkaart() != null) {
                for (OVChipkaart ov : product.getOvChipkaart()) {
                    sql += "INSERT INTO ov_chipkaart_product VALUES (" + ov.getKaart_nummer() + ", " + product.getProduct_nummer() + ", " + null + ", " + null + "); ";
                }
            }
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "update product set naam = '" + product.getNaam() + "', beschrijving = '" + product.getBeschrijving() + "', prijs = " + product.getPrijs() +
                    " where product_nummer = " + product.getProduct_nummer() + "; ";
            if (product.getOvChipkaart() != null) {
                for (OVChipkaart ov : product.getOvChipkaart()) {
                    sql += "INSERT INTO ov_chipkaart_product VALUES (" + ov.getKaart_nummer() + ", " + product.getProduct_nummer() + ", " + null + ", " + null + ") ON CONFLICT DO NOTHING; ";
                }
            }
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "delete from ov_chipkaart_product where product_nummer = " + product.getProduct_nummer() + ";" +
                    " delete from product where product_nummer = " + product.getProduct_nummer() + ";";
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            List<Product> productList = new ArrayList<>();
            Statement stmt = this.conn.createStatement();
            String sql = "SELECT * FROM product WHERE product.product_nummer IN (SELECT ov_chipkaart_product.product_nummer FROM ov_chipkaart_product WHERE ov_chipkaart_product.kaart_nummer = " + ovChipkaart.getKaart_nummer() + ");";
            ResultSet myRs = stmt.executeQuery(sql);
            while (myRs.next()) {
                int pNr = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String pBsch = myRs.getString("beschrijving");
                double prijs = myRs.getDouble("prijs");

                productList.add(new Product(pNr, naam, pBsch, prijs));
            }
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            List<Product> productList = new ArrayList<>();
            Statement stmt = this.conn.createStatement();
            String sql = "SELECT * FROM product";
            ResultSet myRs = stmt.executeQuery(sql);
            while (myRs.next()) {
                int pNr = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String pBsch = myRs.getString("beschrijving");
                double prijs = myRs.getDouble("prijs");

                productList.add(new Product(pNr, naam, pBsch, prijs));
            }
            return productList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product findByID(int id) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "SELECT * FROM product WHERE product_nummer = " + id;
            ResultSet myRs = stmt.executeQuery(sql);
            if (myRs.next()) {
                int pNr = myRs.getInt("product_nummer");
                String naam = myRs.getString("naam");
                String pBsch = myRs.getString("beschrijving");
                double prijs = myRs.getDouble("prijs");

                return new Product(pNr, naam, pBsch, prijs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
