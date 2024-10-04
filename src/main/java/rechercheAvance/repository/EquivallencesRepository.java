package rechercheAvance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rechercheAvance.modele.Equivallences;

import java.util.List;

public interface EquivallencesRepository extends JpaRepository<Equivallences, Integer> {

    @Query("select s from Equivallences s where s.reference in :liste")
    List<Equivallences> getEquivallencesFromList(List<String> liste);

    @Query("select s from Equivallences s where s.reference='ASC' or s.reference='DESC' ")
    List<Equivallences> getOrderByEquivalences();

}