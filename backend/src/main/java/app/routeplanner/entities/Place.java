package app.routeplanner.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    private double longitude;
    private double latitude;
    private String name;
    private String description;

    public double getDistance(double longitude, double latitude){
        double a = Math.pow(longitude - this.longitude, 2);
        double b = Math.pow(latitude - this.latitude, 2);
        double result =  Math.sqrt(a + b);
        return result;
    }
}
