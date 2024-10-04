package rechercheAvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rechercheAvance.modele.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}