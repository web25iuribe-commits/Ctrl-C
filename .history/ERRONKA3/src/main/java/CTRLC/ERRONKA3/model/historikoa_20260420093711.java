package CTRLC.ERRONKA3.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "historikoa")
public class historikoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_hist;
    private String taula;
    private String ekintza;
    private Date data_aldaketa;
    private String oharra;





    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)

    public Integer getId_hist() {
        return id_hist;
    }

    public void setId_hist(Integer id_hist) {
        this.id_hist = id_hist;
    }

    public String getTaula() {
        return taula;
    }

    public void setTaula(String taula) {
        this.taula = taula;
    }

    public String getEkintza() {
        return ekintza;
    }

    public void setEkintza(String ekintza) {
        this.ekintza = ekintza;
    }

    public Date getData_aldaketa() {
        return data_aldaketa;
    }

    public void setData_aldaketa(Date data_aldaketa) {
        this.data_aldaketa = data_aldaketa;
    }

    public String getOharra() {
        return oharra;
    }

    public void setOharra(String oharra) {
        this.oharra = oharra;
    }

}
    
