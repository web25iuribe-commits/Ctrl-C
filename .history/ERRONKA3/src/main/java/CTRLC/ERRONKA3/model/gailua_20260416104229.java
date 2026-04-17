package CTRLC.ERRONKA3.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;




// Jakarta Persistence API. Hibernatek datu-basearekin lan egiteko erabiltzen dituen arau multzoak.




// import jakarta.persistence.Entity;
// @Entity etiketa erabili ahal izateko. EZ da klase arrunt bat, datu-baseko taula baten irudikapena da".
// Hibernatek hau ikusten duenean, badaki klase horrekin MySQL taula bat kudeatu behar duela.




// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// GenerationType eta GeneratedValue batera doaz. Erabiltzaile berri bat sortzen den bakoitzean balio bat sortuko du.




// import jakarta.persistence.Id; @Id etiketa erabili ahal izateko. Gako nagusia zein den adierazten da.




// import jakarta.persistence.Table; @Table etiketa erabili ahal izateko.
// Datu baseko zein taula erabili behar duen adieraziko zaio.





@Entity
@Table(name = "gailua")
public class gailua {
    @Id
    private String id_gailua;
    private String marka;
    private String modeloa;
    private String serie_zenb;
    private String neurriak;
    private String gela_zenb;





    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)

    public String getId_gailua() {
        return id_gailua;
    }

    public void setId_gailua(String id_gailua) {
        this.id_gailua = id_gailua;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModeloa() {
        return modeloa;
    }

    public void setModeloa(String modeloa) {
        this.modeloa = modeloa;
    }

    public String getSerie_zenb() {
        return serie_zenb;
    }

    public void setSerie_zenb(String serie_zenb) {
        this.serie_zenb = serie_zenb;
    }

    public String getNeurriak() {
        return neurriak;
    }

    public void setNeurriak(String neurriak) {
        this.neurriak = neurriak;
    }

    public String getGela_zenb() {
        return gela_zenb;
    }

    public void setGela_zenb(String gela_zenb) {
        this.gela_zenb = gela_zenb;
    }
}


