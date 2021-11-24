package app.routeplanner.services;

import app.routeplanner.models.Route;
import app.routeplanner.repositories.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RoutesRepository routesRepository;

    //Save route
    public void saveRoute(Route route){
        routesRepository.save(route);
    }

    //Get all user`s routes
    public List<Route> getAllByUserIdAndId(Long userId){
        List<Route> listOfUser = new ArrayList<>();

        for(Route r: routesRepository.findAll()){
            if(r.getUser_id().equals(userId))
                listOfUser.add(r);
        }

        return listOfUser;
    }

    //Remove route
    public void removeRouteById(Long id){
        routesRepository.deleteById(id);
    }

}
