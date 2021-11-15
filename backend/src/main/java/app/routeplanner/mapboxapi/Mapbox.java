package app.routeplanner.mapboxapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapbox {
    private final String APY_KEY;
    private final RestTemplate restTemplate;
    private final String RESOURCE_URL;
    public Mapbox (RestTemplateBuilder restTemplateBuilder){
        this.APY_KEY = System.getenv("MAPBOX_APY_KEY");
        this.restTemplate = restTemplateBuilder.build();
        this.RESOURCE_URL = "https://api.mapbox.com/";
    }

    public List<Double> bboxByCity(String city){
        String url = RESOURCE_URL + "geocoding/v5/mapbox.places/" + city + ".json?access_token=" + APY_KEY;
        JSONObject responseJson = new JSONObject(this.restTemplate.getForObject(url, String.class));
        JSONArray bboxArr = responseJson.getJSONArray("features").getJSONObject(0).getJSONArray("bbox");
        List <Double> bboxCity = new ArrayList<>();
        bboxCity.add(Double.parseDouble(bboxArr.get(0).toString()));
        bboxCity.add(Double.parseDouble(bboxArr.get(1).toString()));
        bboxCity.add(Double.parseDouble(bboxArr.get(2).toString()));
        bboxCity.add(Double.parseDouble(bboxArr.get(3).toString()));
        return bboxCity;
    }

    public String getDirection(double lon1, double lat1, double lon2, double lat2, String routingProfile){
        String url = RESOURCE_URL + "directions/v5/mapbox/" + routingProfile + "/" +
                lon1 + ","
                + lat1 + ";"
                + lon2 + ","
                + lat2
                + "?alternatives=true&geometries=geojson&language=en&overview=simplified&steps=true&access_token="
                + APY_KEY;
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}
