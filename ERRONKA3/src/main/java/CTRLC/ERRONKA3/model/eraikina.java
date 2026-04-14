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
@Table(name = "eraikina")
public class eraikina {
    @Id
    private String id_eraikina;
    private String izena;
    private String helbidea;
    private String hiria;
    private String posta_kodea;







    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)

    public String getId_eraikina() {
        return id_eraikina;
    }

    public void setId_eraikina(String id_eraikina) {
        this.id_eraikina = id_eraikina;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getHelbidea() {
        return helbidea;
    }

    public void setHelbidea(String helbidea) {
        this.helbidea = helbidea;
    }

    public String getHiria() {
        return hiria;
    }

    public void setHiria(String hiria) {
        this.hiria = hiria;
    }

    public String getPosta_kodea() {
        return posta_kodea;
    }

    public void setPosta_kodea(String posta_kodea) {
        this.posta_kodea = posta_kodea;
    }


}
    
