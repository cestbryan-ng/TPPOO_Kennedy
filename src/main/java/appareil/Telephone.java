package appareil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Telephone extends Appareil{
    public Telephone(Integer numero,String nom,String image) {
        super(numero, nom, image);
    }

    @Override
    public boolean charger(Connection con) {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("insert into telephone values ("+this.getNumero_de_serie()+", \""+ this.getNom_machine() +"\", \""+ this.getImage() +"\");");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
