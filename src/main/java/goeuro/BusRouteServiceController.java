package goeuro;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@AllArgsConstructor
public class BusRouteServiceController {

    @Autowired
    BusRouteCacheManager busStopCacheManager;

    @RequestMapping("/api/direct/{dep_sid}/{arr_sid}")
    public BusRouteResponse hasDirectConnectionPath(@PathVariable String dep_sid, @PathVariable String arr_sid) {
        return hasDirectConnection(dep_sid, arr_sid);
    }

    @RequestMapping("/api/direct")
    public BusRouteResponse hasDirectConnectionRequest(@RequestParam String dep_sid, @RequestParam String arr_sid) {
        return hasDirectConnection(dep_sid, arr_sid);
    }

    private BusRouteResponse hasDirectConnection(final String dep_sid,final String arr_sid){
        try {
            final Integer departureStationId = Integer.valueOf(dep_sid);
            final Integer arrivalStationId = Integer.valueOf(arr_sid);

            return new BusRouteResponse(departureStationId, arrivalStationId,
                    busStopCacheManager.hasDirectConnection(departureStationId, arrivalStationId));

        }catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }
}
