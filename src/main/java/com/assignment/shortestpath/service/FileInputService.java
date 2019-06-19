package com.assignment.shortestpath.service;

import com.assignment.shortestpath.entity.Planet;
import com.assignment.shortestpath.entity.Route;
import com.assignment.shortestpath.entity.Traffic;
import com.assignment.shortestpath.repository.PlanetRepository;
import com.assignment.shortestpath.repository.RouteRepository;
import com.assignment.shortestpath.repository.TrafficRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FileInputService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileInputService.class);

    private List planets = new ArrayList<Planet>();
    private List routes = new ArrayList<Route>();
    private List traffic = new ArrayList<Traffic>();

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TrafficRepository trafficRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public FileInputService() {
    }

    public void init(){
        //TODO: add better error checking and handling.
        planets = loadObjectList(Planet.class,"planets.csv");
        LOGGER.info("Loading..."+ planets.size());
        planetRepository.saveAll(planets);
        LOGGER.info("Saved all planets.");

        routes = loadObjectList(Route.class,"routes.csv");
        LOGGER.info("Loading..."+ routes.size());
        routeRepository.saveAll(routes);
        LOGGER.info("Saved all routes.");

        traffic = loadObjectList(Traffic.class,"traffic.csv");
        LOGGER.info("Loading..."+ traffic.size());
        trafficRepository.saveAll(traffic);
        LOGGER.info("Saved all traffic.");
    }

    private <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();

            Resource resource = resourceLoader.getResource("classpath:"+fileName);
            InputStream inputStream  = resource.getInputStream();

            MappingIterator<T> readValues =
                    mapper.reader(type).with(bootstrapSchema).readValues(inputStream);
            return readValues.readAll();
        } catch (Exception e) {
            LOGGER.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }
}
