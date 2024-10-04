package rechercheAvance.modele;

import lombok.Data;

import java.util.List;
@Data
public class TraitementRequette {
    String query;
    Where where;
    List<OrderBy> orderByList;
    String limite;
    List<String> reponsesVoulues=List.of("*");

    public String formerReponses(){
       String requette="";
        for (int i = 0; i < getReponsesVoulues().size(); i++) {
            requette+=getReponsesVoulues().get(i);
        }
        return requette;
    }



}
