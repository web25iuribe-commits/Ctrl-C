package CTRLC.ERRONKA3.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;




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
@Table(name = "kontsulta")
public class kontsulta {
    @Id
    private String id_kontsulta;
    private String deskribapena;
    private Date data_kontsulta;
    private String egoera;
    private String id_erab;





    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)

    public String getId_kontsulta() {
        return id_kontsulta;
    }

    public void setId_kontsulta(String id_kontsulta) {
        this.id_kontsulta = id_kontsulta;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public void setDeskribapena(String deskribapena) {
        this.deskribapena = deskribapena;
    }

    public Date getData_kontsulta() {
        return data_kontsulta;
    }

    public void setData_kontsulta(Date data_kontsulta) {
        this.data_kontsulta = data_kontsulta;
    }

    public String getEgoera() {
        return egoera;
    }

    public void setEgoera(String egoera) {
        this.egoera = egoera;
    }

    public String getId_erab() {
        return id_erab;
    }

    public void setId_erab(String id_erab) {
        this.id_erab = id_erab;
    }

}
    

