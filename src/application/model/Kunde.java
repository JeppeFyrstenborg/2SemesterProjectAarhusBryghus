package application.model;

public class Kunde {
    private String kundenavn;
    private String adresse;
    private String email;
    private String telefonNr;
    private final int kundeNr;
    private static int naesteKundeNr = 1;

    // Ved opretning af et kunde objekt sættes adresse, email og telefonNr til "Ikke sat"
    public Kunde(String kundenavn) {
        this.kundenavn = kundenavn;
        this.adresse = "Ikke sat";
        this.email = "Ikke sat";
        this.telefonNr = "Ikke sat";
        this.kundeNr = naesteKundeNr;
        naesteKundeNr++;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public String getKundenavn() {
        return kundenavn;
    }

    public int getKundeNr() {
        return kundeNr;
    }

    public void setKundenavn(String kundenavn) {
        this.kundenavn = kundenavn;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefonNr(String telefonNr) {
        this.telefonNr = telefonNr;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefonNr() {
        return telefonNr;
    }

    @Override
    public String toString() {
        return kundenavn;
    }
}
