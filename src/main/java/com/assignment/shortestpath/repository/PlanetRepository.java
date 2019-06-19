package com.assignment.shortestpath.repository;

import com.assignment.shortestpath.entity.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Long> {
    Planet findPlanetByNode(String node);
}
