package rechercheAvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rechercheAvance.modele.Produit;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
//    @Query(value = "select s from Produit s where :wheres ")
//    List<Produit> getProduit(@Param("wheres") String wheres);
    
    @Query(value = "SELECT column_name " +
            "FROM information_schema.columns " +
            "WHERE table_name =  'produit' and column_name!='categorie_id'and column_name!='id'",nativeQuery = true)
    List<String> obtenirLesColonnes();
}