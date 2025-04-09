package appareil;

import java.sql.Connection;

public abstract class Appareil {
    private Integer numero_de_serie;
    private String nom_machine;
    private String image;

    public Appareil(Integer numero,String nom,String image) {
        this.numero_de_serie = numero;
        this.nom_machine = nom;
        this.image = image;
    }

    public void setNumero_de_serie(Integer numero_de_serie) {
        this.numero_de_serie = numero_de_serie;
    }

    public void setNom_machine(String nom_machine) {
        this.nom_machine = nom_machine;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getNumero_de_serie() {
        return numero_de_serie;
    }

    public String getNom_machine() {
        return nom_machine;
    }

    public String getImage() {
        return image;
    }

    // Methode pour charger l'appareil dans la BD
    public abstract boolean charger(Connection con);
}
