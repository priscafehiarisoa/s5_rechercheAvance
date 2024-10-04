package rechercheAvance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rechercheAvance.ServiceProject;
import rechercheAvance.modele.Produit;
import rechercheAvance.modele.TraitementRequette;
import rechercheAvance.repository.ProduitRepository;

import java.util.List;

@Controller
public class rechercheController {

    private final ProduitRepository produitRepository;
    private final ServiceProject serviceProject;

    public rechercheController(ProduitRepository produitRepository, ServiceProject serviceProject) {
        this.produitRepository = produitRepository;
        this.serviceProject = serviceProject;
    }

    @GetMapping({"/recherche","/"})
    public String getPage(Model model){
        List<Produit> listeProduit=produitRepository.findAll();
        model.addAttribute("produit",listeProduit);
        return "recherche/page";
    }
    @PostMapping("/recherche")
    public String effectuerRecherche(Model model, @RequestParam("search") String search)
    {
        List<Produit> produitList=serviceProject.executerUneRequette(search);
        model.addAttribute("produit",produitList);
        model.addAttribute("requette",search);
        return "recherche/page";
    }

//    @PostMapping("/recherche2")
//    public String effectuerRecherche2(Model model, @RequestParam("search") String search)
//    {
//        TraitementRequette traitementRequette=serviceProject.traiterUneRequette(search);
//        String query = serviceProject.creerUneRequette(traitementRequette);
//        List<Produit> produitList= serviceProject.executerUneRequette(query);
////        List<Produit> produitList=serviceProject.executerUneRequette(search);
//        model.addAttribute("produit",produitList);
//        model.addAttribute("traitement",traitementRequette);
//        return "recherche/page";
//    }
}
