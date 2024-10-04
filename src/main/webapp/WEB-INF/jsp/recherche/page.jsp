<%@ page import="rechercheAvance.modele.Produit" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Recherches</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>

<%
    List<Produit> produitList=new ArrayList<>();
    if(request.getAttribute("produit")!=null){
        produitList= (List<Produit>) request.getAttribute("produit");
    }
%>
<div class="container">
    <div class="card mt-5 rounded-3 p-5">
        <div>
            <h1 class="title h3">Recherches avancés </h1>
        </div>
        <form action="${pageContext.request.contextPath}/recherche" method="post">
            <div class="row">
                <div class="form-group col-8">
                    <label for="search"> recherche</label>
                    <input type="text" id="search" name="search"  class="form-control col-8">
                </div>
                <div class="col-3">
                    <input type="submit" class="btn btn-dark mt-4" value="valider">
                </div>
            </div>
        </form>

        <div class="tab-pane mt-3">
            <h4>liste des produits</h4>
            <p><span>recherche :</span><% if (request.getAttribute("requette")!=null) out.print(request.getAttribute("requette"));%></p>
            <table class="table ">
                <tr>
                    <th>id produit</th>
                    <th>prix</th>
                    <th>qualite</th>
                    <th>categorie</th>
                    <% if (Produit.displayRapport(produitList)){ %>
                    <th>rapport</th>
                    <% }%>
                </tr>
                <% for (int i = 0; i < produitList.size(); i++) { %>
                <tr>
                    <td><%=produitList.get(i).getId()%></td>
                    <td><%=produitList.get(i).getPrix()%></td>
                    <td><%=produitList.get(i).getQualite()%></td>
                    <td><%=produitList.get(i).getCategorie().getNomCategorie()%></td>
                    <% if (Produit.displayRapport(produitList)){ %>
                    <td><%=produitList.get(i).getRapport()%></td>
                    <% }%>

                </tr>
               <% }%>

            </table>
        </div>
    </div>


</div>

</body>
</html>