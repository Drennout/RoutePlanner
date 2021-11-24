package app.routeplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "routes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Size(max = 20)
    String request;

    @NotBlank
    String points;

    @NotBlank
    String profile;

    @NotBlank
    Long user_id;

    public Route(String request, Long user_id, String points, String profile){
        this.request = request;
        this.user_id = user_id;
        this.points = points;
        this.profile = profile;
    }
}
