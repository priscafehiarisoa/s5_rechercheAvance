package rechercheAvance.modele;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Entity
@Data
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    double prix ;
    int qualite;
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    Categorie categorie;
    @Transient
    double rapport;

    public static boolean displayRapport(List<Produit> produitList){
        double somme = 0 ;
        for (int i = 0; i < produitList.size() ; i++) {
            somme+=produitList.get(i).getRapport();
        }
        if(somme==0){
            return false;
        }
        return true;
    }


}
