package rechercheAvance.modele;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MotClefs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    String motClef;


}
