package rechercheAvance;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import rechercheAvance.modele.*;
import rechercheAvance.repository.CategorieRepository;
import rechercheAvance.repository.EquivallencesRepository;
import rechercheAvance.repository.ProduitRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceProject {
    private final HikariDataSource dataSource;
    private final CategorieRepository categorieRepository;
    private final ProduitRepository produitRepository;
    private final EquivallencesRepository equivallencesRepository;

    public ServiceProject(HikariDataSource dataSource,
                          CategorieRepository categorieRepository,
                          ProduitRepository produitRepository,
                          EquivallencesRepository equivallencesRepository) {
        this.dataSource = dataSource;
        this.categorieRepository = categorieRepository;
        this.produitRepository = produitRepository;
        this.equivallencesRepository = equivallencesRepository;
    }
    // get limites

    public String getLimite (String requette){
        System.out.println("========");
        String[] req=requette.split(" ");
        String returns="";
        if(req.length>0){
            try{
                int limite=Integer.valueOf(req[0]);
                returns="limit "+limite;
                return returns;
            }
            catch (Exception e){
                System.out.println(e.getMessage());;
            }
        }
        for (int i = 0; i < req.length; i++) {
            if(req[i].equals("top") || req[i].equals("les")){
                try{
                    int limite=Integer.valueOf(req[i+1]);
                    returns="limit "+limite;
                    System.out.println("misy top");
                }catch (NumberFormatException |IndexOutOfBoundsException exception){
                    break;
                }
            }
        }
        System.out.println("resultat : "+returns);
        return returns;
    }

    public  Where getEgalites (String query){
        // enlever les categories de la requette
        List<String> egalite=List.of("<",">","=");
        // replacer les equivalences
        List<Equivallences> equivallences= equivallencesRepository.getEquivallencesFromList(egalite);
        for (int i = 0; i < equivallences.size(); i++) {
            if(query.contains(equivallences.get(i).getPossibilite())){
                query=query.replace(equivallences.get(i).getPossibilite(),equivallences.get(i).getReference());
            }
        }
        // former les confitions
        String [] queryTab=query.split(" ");
        String egalites="<>=";
        List<String> column=produitRepository.obtenirLesColonnes();
        Where where=  new Where();
        where.setWhere(new ArrayList<>());

        for (int i = 0; i < queryTab.length ; i++) {
            if(egalites.contains(queryTab[i])) {
                String eg=queryTab[i];
                String selectedColumn="";
                double nombre=0;
                try {
                    // test s'il y a un nombre aloha apres
                    nombre= Double.valueOf(queryTab[i+1]);
                    // obtenir les colonnes
                    List<Pair> distance=new ArrayList<>();
                    for (int j = i; j >=0 ; j--) {
                        for (int k = 0; k < column.size(); k++) {
                            if(queryTab[j].equals(column.get(k))){
                                distance.add(new Pair(j,column.get(k)));
                            }
                        }
                    }
                    try{
                        distance=new Pair().sortPair(distance);
                        selectedColumn=distance.get(0).getStringValue();
                        String res= " "+selectedColumn+" "+eg+" "+nombre;
                        where.getWhere().add(res);
                    }catch (Exception e){
                        System.out.println("error");
                        System.out.println(e.getMessage());
                    }

                } catch (IndexOutOfBoundsException | NumberFormatException exception) {

                }
            }
        }
        System.out.println(query);
        return where;
    }

    public List<OrderBy> getOrderByFromQuery(String query,TraitementRequette traitementRequetteR){
        List<Equivallences> orderByEquivalences=equivallencesRepository.getEquivallencesFromList(List.of("ASC","DESC"));
        List<OrderBy> orderByList=new ArrayList<>();
        List<String> listeAnaColonne = produitRepository.obtenirLesColonnes();

        for (int i = 0; i < orderByEquivalences.size(); i++) {
            if(query.contains(orderByEquivalences.get(i).getPossibilite())){
                // eto zany izy mahazo hoe ASC sa DESC ilay order by
                String order= orderByEquivalences.get(i).getReference();
                String conditionOrderBy="";
                List<Equivallences> listerapportEquivalences=equivallencesRepository.getEquivallencesFromList(List.of("rapport"));
                // ty le equivalent alaina avy any anaty base
                String equivalence=orderByEquivalences.get(i).getPossibilite();
                for (int j = 0; j < listerapportEquivalences.size(); j++) {
                    equivalence=equivalence.replace(listerapportEquivalences.get(j).getPossibilite().strip(),listerapportEquivalences.get(j).getReference());
                 }
                if(equivalence.contains("rapport")) {
                    List<String> azoAnaovanaRapport = new ArrayList<>();
                    String rapport = query.split(orderByEquivalences.get(i).getPossibilite())[1];
                    for (int j = 0; j < listeAnaColonne.size(); j++) {
                        if(rapport.contains(listeAnaColonne.get(j))){
                            azoAnaovanaRapport.add(listeAnaColonne.get(j));
                        }
                    }
                    if(azoAnaovanaRapport.size()==2){
                        conditionOrderBy=" (" +azoAnaovanaRapport.get(1)+"/"+azoAnaovanaRapport.get(0) + ") ";
                        String reponses=" , (" + String.valueOf(azoAnaovanaRapport.get(1)) + "/" + String.valueOf(azoAnaovanaRapport.get(0)) + ") as rapport";

                        traitementRequetteR.setReponsesVoulues(List.of("*",reponses));
                    }
                }
                else{
                    for (int j = 0; j < listeAnaColonne.size(); j++) {
                        if(equivalence.contains(listeAnaColonne.get(j))){
                            conditionOrderBy=listeAnaColonne.get(j);
                            break;
                        }
                        else{
                            if(query.contains(listeAnaColonne.get(j))){
                                conditionOrderBy=listeAnaColonne.get(j);
                            }
                        }
                    }
                }
                OrderBy orderBy=new OrderBy();
                orderBy.setOrders(conditionOrderBy);
                orderBy.setAsc_desc(order);
                orderByList.add(orderBy);
            }
        }
        return orderByList;
    }

    public Where between(String query){
        String[]queryTab=query.split(" ");
        Where wheres=new Where();
        wheres.setWhere(new ArrayList<>());
        for (int i = 0; i < queryTab.length; i++) {
            if(queryTab[i].equals("entre")){
                List<Double> prix=new ArrayList<>();
                // get prix 1
                try{
                    double prix1 = Double.valueOf(queryTab[i+1]);
                    prix.add(prix1);
                    double prix2 = Double.valueOf(queryTab[i+3]);
                    prix.add(prix2);
                    String selectedColumn="";
                    // get column
                    List<String> column=produitRepository.obtenirLesColonnes();
                    List<Pair> distance=new ArrayList<>();
                    for (int j = i; j >=0 ; j--) {
                        for (int k = 0; k < column.size(); k++) {
                            if(queryTab[j].equals(column.get(k))){
                                distance.add(new Pair(j,column.get(k)));
                            }
                        }
                    }
                    try{
                        distance=new Pair().sortPair(distance);
                        selectedColumn=distance.get(0).getStringValue();
                    }catch (Exception e){
                        System.out.println("error");
                        System.out.println(e.getMessage());
                    }

                    if(selectedColumn!="") {
                        List<String> we=wheres.getWhere();
                        we.add(" "+selectedColumn+" between "+prix1+" and "+prix2);
                        wheres.setWhere(we);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }


            }
        }
        return wheres;
    }

    public Where getWhereFromCategories(List<Categorie> categories){
        Where where=new Where();
        where.setWhere(new ArrayList<>());
        for (int i = 0; i < categories.size(); i++) {
            where.getWhere().add(" categorie_id = "+categories.get(i).getId());
        }
        return where;
    }
    public TraitementRequette traiterUneRequette(String query){
        TraitementRequette traitementRequette=new TraitementRequette();
        // verification limites
        String limite=getLimite(query);

        //get liste categorie
        List<Categorie> categorieslist=new Categorie().getCategorieFromString(query,categorieRepository);
        // where comparaisons
        Where comparaison=getEgalites(query);
        System.out.println("))))");
        System.out.println(comparaison);
        // where categories
        Where categories = getWhereFromCategories(categorieslist);
        // where between
        Where between=between(query);
        // order by
        List<OrderBy> orderByList=getOrderByFromQuery(query,traitementRequette);
        Where realWhere=new Where();
        realWhere.setWhere(new ArrayList<>());
        realWhere.getWhere().addAll(categories.getWhere());
        realWhere.getWhere().addAll(comparaison.getWhere());
        realWhere.getWhere().addAll(between.getWhere());
        System.out.println("***");
        System.out.println(limite);
        traitementRequette.setQuery(query);
        traitementRequette.setLimite(limite);
        traitementRequette.setOrderByList(orderByList);
        traitementRequette.setWhere(realWhere);
        return traitementRequette;
    }

    public List<Produit> executerUneRequette (String requette){
        TraitementRequette traitementRequette=traiterUneRequette(requette);
        String where=traitementRequette.getWhere().buildWhere();
        String orderBy=new OrderBy().formerOrderBy(traitementRequette.getOrderByList());
        System.out.println("query : "+"SELECT * from produit "+where+ " "+traitementRequette.getLimite()+" "+orderBy);
        System.out.println(traitementRequette);
        System.out.println(":::");
        List<Produit> produitList= new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT "+ traitementRequette.formerReponses()+" from produit "+where+" "+orderBy + " "+traitementRequette.getLimite());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Produit produit= new Produit();
                produit.setId(resultSet.getInt("id"));
                Categorie categorie= new Categorie();
                try {
                    categorie =
                            new Categorie().getCategorieById(resultSet.getInt("categorie_id"), categorieRepository);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                produit.setCategorie(categorie);
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setQualite(resultSet.getInt("qualite"));
                try {
                    produit.setRapport(resultSet.getDouble("rapport"));
                }catch (Exception e){
                    System.out.println("error :"+e.getMessage());
                }
                produitList.add(produit);
//                System.out.println(resultSet.getDouble("p"));
                System.out.println( produit.getCategorie().getNomCategorie());
            }
            System.out.println("resultat de la requette ");
//            produitList.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produitList;
    }

    public String creerUneRequette(TraitementRequette traitementRequette){
        String where=traitementRequette.getWhere().buildWhere();
        String orderBy=new OrderBy().formerOrderBy(traitementRequette.getOrderByList());
        String requette = "SELECT "+ traitementRequette.formerReponses()+" from produit "+where+" "+orderBy + " "+traitementRequette.getLimite();
        return requette;
    }
    public List<Produit> executerLaRequette(String requette){
        List<Produit> produitList= new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(requette);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Produit produit= new Produit();
                produit.setId(resultSet.getInt("id"));
                Categorie categorie= new Categorie();
                try {
                    categorie =
                            new Categorie().getCategorieById(resultSet.getInt("categorie_id"), categorieRepository);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                produit.setCategorie(categorie);
                produit.setPrix(resultSet.getDouble("prix"));
                produit.setQualite(resultSet.getInt("qualite"));
                try {
                    produit.setRapport(resultSet.getDouble("rapport"));
                }catch (Exception e){
                    System.out.println("error :"+e.getMessage());
                }
                produitList.add(produit);
//                System.out.println(resultSet.getDouble("p"));
                System.out.println( produit.getCategorie().getNomCategorie());
            }
            System.out.println("resultat de la requette ");
//            produitList.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produitList;
    }

}
