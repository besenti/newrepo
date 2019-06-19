package com.assignment.shortestpath.controller;
import com.assignment.shortestpath.entity.Planet;
import com.assignment.shortestpath.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class PlanetController {

    @Autowired
    private PlanetRepository planetRepository;

    @RequestMapping(value = "/v1/planets")
    public String getPlanets(Model model){
        model.addAttribute("planets",planetRepository.findAll());
        return "index";
    }

    @RequestMapping(value = "/v1/addplanet")
    public String addPlanet(@Valid Planet planet, BindingResult result, Model model ){

        if (result.hasErrors()){
            return "add-planet";
        }
        planetRepository.save(planet);

        model.addAttribute("planets",planetRepository.findAll());
        return "index";
    }
}
