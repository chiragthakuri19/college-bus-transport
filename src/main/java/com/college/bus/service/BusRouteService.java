package com.college.bus.service;

import com.college.bus.model.BusRoute;
import com.college.bus.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BusRouteService {

    @Autowired
    private BusRouteRepository busRouteRepository;

    public BusRoute createBusRoute(BusRoute busRoute) {
        return busRouteRepository.save(busRoute);
    }

    public Optional<BusRoute> getBusRouteById(Long id) {
        return busRouteRepository.findById(id);
    }

    public List<BusRoute> getAllActiveBusRoutes() {
        return busRouteRepository.findByIsActiveTrue();
    }

    public List<BusRoute> getAllBusRoutes() {
        return busRouteRepository.findAll();
    }

    public BusRoute updateBusRoute(BusRoute busRoute) {
        if (!busRouteRepository.existsById(busRoute.getId())) {
            throw new RuntimeException("Bus route not found");
        }
        return busRouteRepository.save(busRoute);
    }

    public void deactivateBusRoute(Long id) {
        busRouteRepository.findById(id).ifPresent(route -> {
            route.setActive(false);
            busRouteRepository.save(route);
        });
    }

    public List<BusRoute> findRoutesByStartAndEndLocation(String startLocation, String endLocation) {
        return busRouteRepository.findByStartLocationAndEndLocation(startLocation, endLocation);
    }
} 