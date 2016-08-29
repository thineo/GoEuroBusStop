package goeuro;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusRouteResponse {

    private final Integer dep_sid;
    private final Integer arr_sid;
    private final boolean direct_bus_route;

}
