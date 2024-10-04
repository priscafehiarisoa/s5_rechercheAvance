package rechercheAvance.modele;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Where {
    List<String> where=new ArrayList<>();

    public String buildWhere(){
        String wherepart="";
        if(!getWhere().isEmpty()){
            wherepart+=" where "+getWhere().get(0);
            if(getWhere().size()>1){
                for (int i = 1; i < getWhere().size(); i++) {
                    if(getWhere().get(i).contains("categorie_id")){
                        wherepart+=" or "+getWhere().get(i);
                    }
                    else {
                        wherepart += " and " + getWhere().get(i);
                    }
                }
            }
        }
        return wherepart;
    }
}
