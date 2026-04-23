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
@Table(name = "solairua")
public class solairua {
    @Id
    private String id_solairua;
    private Integer solairu_zenbakia;
    private String id_eraikina;





    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)

    public String getId_solairua() {
        return id_solairua;
    }

    public void setId_solairua(String id_solairua) {
        this.id_solairua = id_solairua;
    }

    public Integer getSolairu_zenbakia() {
        return solairu_zenbakia;
    }

    public void setSolairu_zenbakia(Integer solairu_zenbakia) {
        this.solairu_zenbakia = solairu_zenbakia;
    }

    public String getId_eraikina() {
        return id_eraikina;
    }

    public void setId_eraikina(String id_eraikina) {
        this.id_eraikina = id_eraikina;
    }



}
    

