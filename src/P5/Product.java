package P5;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<OVChipkaart> ovChipkaart;

    public Product(int product_nummer, String naam, String beschrijving, double prijs) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
        ovChipkaart = new ArrayList<>();
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void setOvChipkaart(List<OVChipkaart> ovChipkaart) {
        this.ovChipkaart = ovChipkaart;
    }

    public void addOvChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaart.add(ovChipkaart);
        ovChipkaart.getProduct().add(this);
    }

    public void removeOvChipkaart(OVChipkaart ovChipkaart) {
        this.ovChipkaart.remove(ovChipkaart);
        ovChipkaart.getProduct().remove(this);
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    @Override
    public String toString() {
        return "Product{" + "\n" +
                "product_nummer=" + product_nummer + "\n" +
                ", naam='" + naam + "\n" +
                ", beschrijving='" + beschrijving + "\n" +
                ", prijs=" + prijs +
                '}';
    }
}
