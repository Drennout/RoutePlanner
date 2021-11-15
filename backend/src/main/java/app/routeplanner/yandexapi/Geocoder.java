package app.routeplanner.yandexapi;

import app.routeplanner.entities.Place;
import app.routeplanner.entities.RequestAddress;
import org.json.JSONArray;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import java.util.*;

@Service
public class Geocoder {
    private final String API_KEY;
    private final String URL;
    private final RestTemplate restTemplate;
    private List<Double> bboxCity;

    public Geocoder(RestTemplateBuilder restTemplateBuilder){
        this.API_KEY = System.getenv("YANDEX_API_KEY");
        this.restTemplate = restTemplateBuilder.build();
        this.URL = "https://search-maps.yandex.ru/v1/";
        this.bboxCity = new ArrayList<>();
        this.bboxCity.add(36.803047878);
        this.bboxCity.add(55.142409829);
        this.bboxCity.add(37.967288862);
        this.bboxCity.add(56.020052);
    }

    public List<RequestAddress> parseRequest(String request){
        List<RequestAddress> requestAddresses = new ArrayList<>();
        boolean isType = true;

        int type = 0;
        StringBuilder address = new StringBuilder();

        for (char s: request.toCharArray()){
            if(isType && s != ',') {
                type = Character.getNumericValue(s);
            }
            else if(isType){
                isType = false;
            }
            else if (!isType && s != ';'){
                address.append(s);
            }
            else if(s == ';'){
                isType = true;
                requestAddresses.add(new RequestAddress(address.toString(), type));
                address.setLength(0);
            }
        }

        return requestAddresses;
    }

    private Place getPlace(JSONObject response){
        String coords = response
                .getJSONObject("geometry")
                .get("coordinates").toString();

        double longitude = Double.parseDouble(coords.substring(coords.indexOf('[') + 1, coords.indexOf(',')));
        double latitude = Double.parseDouble(coords.substring(coords.indexOf(',') + 1, coords.indexOf(']')));

        String description = response
                .getJSONObject("properties")
                .get("description").toString();
        String name = response
                .getJSONObject("properties")
                .get("name").toString();
        return new Place(longitude, latitude, name, description);
    }

    public Place geocode(String address){
        String request = URL + "?text=" + address + "&type=biz&lang=ru_RU&results=1&apikey=" + API_KEY;
        JSONObject response = new JSONObject(this.restTemplate.getForObject(request, String.class));
        return getPlace(response.getJSONArray("features").getJSONObject(0));
    }

    public Place geocode(String address, Place near){
        List<Place> places = new ArrayList<>();

        String request = URL + "?text=" + address + "&type=biz&lang=ru_RU&results=200&" + bbox() + "&apikey=" + API_KEY;
        JSONObject response = new JSONObject(this.restTemplate.getForObject(request, String.class));
        JSONArray objects = response.getJSONArray("features");

        for (int i = 0; i < objects.length(); i++){
            places.add(getPlace(objects.getJSONObject(i)));
        }
        class PlaceComparator implements Comparator<Place>{
            @Override
            public int compare(Place o1, Place o2) {
                return Double.compare(o1.getDistance(near.getLongitude(), near.getLatitude()),o2.getDistance(near.getLongitude(), near.getLatitude()));
            }
        }

        places.sort(new PlaceComparator());

        return places.get(0);
    }

    public List<Place> getPlacesByRequestAddresses(List<RequestAddress> addresses){
        List<Place> places = new ArrayList<>();
        Place mainPlace = null;

        for (RequestAddress ua: addresses){
            Place place;
            if(ua.getType() == 1){
                place = geocode(ua.getAddress());
                mainPlace = place;
            } else {
                place = geocode(ua.getAddress(), mainPlace);
            }
            places.add(place);
        }
        return places;
    }

    public JSONObject placesToJson(List<Place> places){
        JSONObject points = new JSONObject();
        JSONArray properties = new JSONArray();
        for (Place p: places){
            JSONObject place = new JSONObject();
            place.put("name", p.getName());
            place.put("description", p.getDescription());
            place.put("lon", p.getLongitude());
            place.put("lat", p.getLatitude());
            properties.put(place);
        }
        points.put("points", properties);
        return points;
    }

    private String bbox(){
        return "bbox=" + this.bboxCity.get(0)
                + "," + this.bboxCity.get(1)
                + "~" + this.bboxCity.get(2)
                + "," + this.bboxCity.get(3);
    }

    public void setBboxCity(List<Double> city) {
        this.bboxCity = city;
    }
}
