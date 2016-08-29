package goeuro;

public interface BusRouteCacheManager {

    boolean hasDirectConnection(Integer departureStationId, final Integer arrivalStationId);

}
