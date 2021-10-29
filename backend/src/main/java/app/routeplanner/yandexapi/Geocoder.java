package app.routeplanner.yandexapi;

import app.routeplanner.entities.Place;
import app.routeplanner.entities.UserAddress;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Service
public class Geocoder {
    private final String API_KEY;
    private final String URL;
    private final RestTemplate restTemplate;
    private List<UserAddress> addresses;

    public Geocoder(RestTemplateBuilder restTemplateBuilder){
        this.API_KEY = System.getenv("YANDEX_API_KEY");
        this.restTemplate = restTemplateBuilder.build();
        this.URL = "https://search-maps.yandex.ru/v1/";
        this.addresses = new ArrayList<>();
    }

    public List<UserAddress> parseRequest(String request){
        List<UserAddress> userAddresses = new ArrayList<>();
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
                userAddresses.add(new UserAddress(address.toString(), type));
                address.setLength(0);
            }
        }

        return userAddresses;
    }

    public Place geocodeExact(String address){
        String request = URL + "?text=" + address + "&type=biz&lang=ru_RU&results=1&apikey=" + API_KEY;
        JSONObject response = new JSONObject(this.restTemplate.getForObject(request, String.class));
        String coords = response
                .getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .get("coordinates").toString();

        double longitude = Double.parseDouble(coords.substring(coords.indexOf('[') + 1, coords.indexOf(',')));
        double latitude = Double.parseDouble(coords.substring(coords.indexOf(',') + 1, coords.indexOf(']')));

        String description = response
                .getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .get("description").toString();
        String name = response
                .getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .get("name").toString();
        return new Place(longitude, latitude, name, description);
    }

    public Place geocodeNearest(String address){
        return null;
    }

    public void setAddresses(List<UserAddress> addresses) {
        this.addresses = addresses;
    }
}
