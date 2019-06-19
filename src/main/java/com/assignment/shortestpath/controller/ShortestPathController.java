package com.assignment.shortestpath.controller;

import com.assignment.shortestpath.model.Criteria;
import com.assignment.shortestpath.service.ShortestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class ShortestPathController {
    @Autowired
    private ShortestPathService shortestPathService;

    @RequestMapping(value = "/v1/shortestpath")
    public String getShortestPath(@Valid Criteria criteria,BindingResult result, Model model){

        if (result.hasErrors()){
            return "find-shortest-path";
        }
        model.addAttribute("path",shortestPathService.getPath(criteria.getOrigin(),criteria.getDestination()));
        return "shortest-path-result";
    }
}
