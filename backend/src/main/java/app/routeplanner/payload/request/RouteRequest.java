package app.routeplanner.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class RouteRequest {
    @NotBlank
    private String request;

    @NotBlank
    private String points;

    @NotBlank
    private String profile;
}
