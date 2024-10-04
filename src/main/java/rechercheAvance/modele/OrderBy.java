package rechercheAvance.modele;

import lombok.Data;

import java.util.List;

@Data
public class OrderBy {
    String orders;
    String asc_desc;

    public String formerOrderBy(List<OrderBy> orderBy){
        String result ="";
        if(!orderBy.isEmpty()){
            result = " order by " + orderBy.get(0).getOrders() +" "+ orderBy.get(0).getAsc_desc();
            if(orderBy.size()>1) {
                for (int i = 1; i < orderBy.size(); i++) {
                    result+=" , "+orderBy.get(i).getOrders() +" " + orderBy.get(i).getAsc_desc();
                }
            }
        }
        return result;
    }


}
