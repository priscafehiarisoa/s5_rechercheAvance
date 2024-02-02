package rechercheAvance;
@org.springframework.stereotype.Service
public class Service {
    // get limites

    public String getLimite (String requette){
        String[] req=requette.split(" ");
        String returns="";
        for (int i = 0; i < req.length; i++) {
            if(req[i].equals("top")){
                try{
                    int limite=Integer.valueOf(req[i+1]);
                }catch (NumberFormatException numberFormatException,IndexOutOfBoundsException indexOutOfBoundsException){

                }
            }
        }
        return returns;
    }
}
