package rechercheAvance.modele;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Equivallences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    String reference;
    String possibilite;



}
