package P5;

import java.sql.Date;
import java.util.List;

public class Reiziger {

    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaart;

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public List<OVChipkaart> getOvChipkaart() {
        return ovChipkaart;
    }

    public void setOvChipkaart(List<OVChipkaart> ovChipkaart) {
        this.ovChipkaart = ovChipkaart;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getNaam() {
        if (tussenvoegsel == null) {
            return voorletters + " " + achternaam;
        }
        return voorletters + " " + tussenvoegsel + " " + achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    @Override
    public String toString() {
        return "De naam van de reiziger is: " + getNaam() + "\n" +
                "Deze reiziger is geboren op: " + geboortedatum.toString() + "\n" +
                "Het ID van deze reiziger is: " + reiziger_id + "\n" +
                "Het adres van de reiziger is: " + adres.toString() + "\n" +
                "De chipkaarten van deze reiziger zijn: " + ovChipkaart;
    }
}
