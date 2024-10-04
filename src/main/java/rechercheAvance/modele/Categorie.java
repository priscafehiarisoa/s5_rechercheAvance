package rechercheAvance.modele;

import jakarta.persistence.*;
import lombok.Data;
import rechercheAvance.repository.CategorieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    String nomCategorie;

    public List<Categorie> getCategorieFromString(String requete , CategorieRepository categorieRepository){
        List<Categorie> listeInitiale= categorieRepository.findAll();
        List<Categorie> listeFinale=new ArrayList<>();
        for (int i = 0; i < listeInitiale.size(); i++) {
            if(requete.contains(listeInitiale.get(i).getNomCategorie())){
                listeFinale.add(listeInitiale.get(i));
            }
        }
        return listeFinale;
    }
    public Categorie getCategorieById(int id , CategorieRepository categorieRepository) throws Exception {
        Optional<Categorie> optionalCategorie= categorieRepository.findById(id);
        if(optionalCategorie.isPresent()){
            return optionalCategorie.get();
        }
        else{
            throw new Exception("categorie Introuvable");
        }
    }
//    public static String getWhereFrom



}
