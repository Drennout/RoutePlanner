package app.routeplanner.controllers;

import app.routeplanner.models.Route;
import app.routeplanner.payload.request.RouteRequest;
import app.routeplanner.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/acc/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @GetMapping("/{id}") //id - user id
    public List<Route> getAllRoutes(@PathVariable Long id){
        return routeService.getAllByUserIdAndId(id);
    }

    @PostMapping("/{id}") //id - user_id
    public void saveRoute(@PathVariable Long id, @Valid @RequestBody RouteRequest routeRequest){
        System.out.println(routeRequest.getRequest());
        System.out.println(routeRequest.getPoints());
        routeService.saveRoute(new Route(routeRequest.getRequest(), id, routeRequest.getPoints(), routeRequest.getProfile()));
    }

    @DeleteMapping("/{id}") //id - route id
    public void deleteRoute(@PathVariable Long id){
        routeService.removeRouteById(id);
    }
}
