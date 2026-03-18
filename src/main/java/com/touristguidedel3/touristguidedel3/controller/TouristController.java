package com.touristguidedel3.touristguidedel3.controller;

import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.Tags;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import com.touristguidedel3.touristguidedel3.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping
    public String getAllAttractions(Model model) {
        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);

        return "attractionsList";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(Model model, @PathVariable String name) {
        TouristAttraction touristAttraction = touristService.getAttractionByName(name);
        if(touristAttraction == null) {
            throw new IllegalArgumentException("Invalid attraction!" + name);
        }

        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("tags", touristAttraction.getTags());

        return "tags";

    }

    //Display the Attraction edit form
    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction touristAttraction = touristService.getAttractionByName(name);

        if(touristAttraction == null) {
            throw new IllegalArgumentException("Invalid attraction! " + name);
        }

        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("tags", Tags.values());

        return "updateAttraction";
    }

    //Handle the form submission
    @PostMapping("/update")
    public String editAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.updateAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        TouristAttraction touristAttraction = new TouristAttraction();
        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("tags", Tags.values());
        return "addAttraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.addAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/{id}/delete")
    public String deleteAttraction(@PathVariable Long id) {
        touristService.deleteAttraction(id);
        return "redirect:/attractions";
    }
}
