package app.routeplanner.controllers;

import app.routeplanner.entities.Place;
import app.routeplanner.entities.RequestAddress;
import app.routeplanner.mapboxapi.Mapbox;
import app.routeplanner.yandexapi.Geocoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DirectionsController {
    @Autowired
    private Geocoder geocoder;

    @Autowired
    private Mapbox mapbox;

    @GetMapping("/geocode")
    public @ResponseBody
    String geocodeRequest(@RequestParam String request, @RequestParam String city){
        List<RequestAddress> addresses = geocoder.parseRequest(request);
        List<Place> places = geocoder.getPlacesByRequestAddresses(addresses, mapbox.bboxByCity(city));
        JSONObject jsonResponse = geocoder.placesToJson(places);
        return jsonResponse.toString();
    }

    @GetMapping("/direction")
    public @ResponseBody
    String getDirectionByCoords(@RequestParam double lon1,@RequestParam double lat1,@RequestParam double lon2,@RequestParam double lat2,@RequestParam String routingProfile){
        return mapbox.getDirection(lon1, lat1, lon2, lat2, routingProfile);
    }
}
