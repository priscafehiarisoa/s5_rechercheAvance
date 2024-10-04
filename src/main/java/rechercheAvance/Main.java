package rechercheAvance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rechercheAvance.modele.Categorie;
import rechercheAvance.modele.OrderBy;
import rechercheAvance.modele.TraitementRequette;
import rechercheAvance.repository.CategorieRepository;
import rechercheAvance.repository.EquivallencesRepository;
import rechercheAvance.repository.ProduitRepository;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Main {

    private final CategorieRepository categorieRepository;
    private final ServiceProject serviceProject;

    public Main(CategorieRepository categorieRepository, ServiceProject serviceProject) {
        this.categorieRepository = categorieRepository;
        this.serviceProject = serviceProject;
    }

    @Bean
    CommandLineRunner commandLineRunner(ProduitRepository produitRepository,
                                        EquivallencesRepository equivallencesRepository){
        return args -> {
            String requette = "mon telephone est à plat mais les chaises sont vides";
          new Categorie().getCategorieFromString(requette,categorieRepository).forEach(System.out::println);
            System.out.println("test limite");
            System.out.println(serviceProject.getLimite("top 6 des vetements de luxes"));
//            System.out.println("test builded query ");
////            produitRepository.getProduit("1=1").forEach(System.out::println);
//            System.out.println("query builder");
////            String persistenceUnitName = "Produit";
////            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
////            EntityManager entityManager = entityManagerFactory.createEntityManager();
//            String where=" where categorie_id=1 ";
//            String order=" order by prix asc ";
//
//            String requettes = "SELECT * FROM produit "+where+"  "+order+"";
//            System.out.println(requettes);
//            serviceProject.executerUneRequette(requettes);
//            produitRepository.obtenirLesColonnes().forEach(System.out::println);
//            equivallencesRepository.getEquivallencesFromList(List.of("<",">","=")).forEach(System.out::println);
//            System.out.println(serviceProject.getEgalites("prix est egal à 2000 et superieur a 10"));
//            System.out.println("test order by");
//            serviceProject.getOrderByFromQuery("telephone prix le plus cher et meilleur ratio qualite prix ").forEach(System.out::println);
//            System.out.println("test entre ");
//            System.out.println(serviceProject.between(" prix telephone entre 1000 et 2000 et qualite entre 4 et 9 "));
//            List<Categorie> categories=new Categorie().getCategorieFromString(" telephone avec le pire prix",categorieRepository);
//            System.out.println("categories");
//            serviceProject.getWhereFromCategories(categories).getWhere().forEach(System.out::println);

            String query=" meilleur ratio qualite prix";
//            TraitementRequette traitementRequette=serviceProject.traiterUneRequette(query);
//            System.out.println(new OrderBy().formerOrderBy(traitementRequette.getOrderByList()));
//            System.out.println(traitementRequette.getWhere().buildWhere());
            serviceProject.executerUneRequette(query).forEach(System.out::println);
        };
    }
    public static void main(String[] args) {
        OrderBy orderBy= new OrderBy();
//        List<String> order=new ArrayList<>();
//        order.add(" prix ");
//        order.add(" qualite / prix ");
////        orderBy.setOrders(order);
//        orderBy.setAsc_desc(" ASC ");
//        System.out.println(orderBy.formerOrderBy());

        System.out.println("les roses".contains("le"));
    }

}
