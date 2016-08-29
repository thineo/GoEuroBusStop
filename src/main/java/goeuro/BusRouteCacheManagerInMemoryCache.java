package goeuro;

import java.util.HashMap;
import java.util.Set;

public class BusRouteCacheManagerInMemoryCache implements BusRouteCacheManager {

    private HashMap<Integer, Set<Integer>> mapStationBySetOfRoutes;

    public BusRouteCacheManagerInMemoryCache(final BusRouteLoader busRouteLoader){
        mapStationBySetOfRoutes = busRouteLoader.loadCache();
    }

    public boolean hasDirectConnection(Integer departureStationId, final Integer arrivalStationId){

        if(mapStationBySetOfRoutes == null){
            return false;
        }

        final Set<Integer> setOfRoutesStationOne = mapStationBySetOfRoutes.get(departureStationId);
        final Set<Integer> setOfRoutesStationTwo = mapStationBySetOfRoutes.get(arrivalStationId);

        return hasIntersection(setOfRoutesStationOne, setOfRoutesStationTwo);
    }

    private boolean hasIntersection(final Set<Integer> setOfRoutesOne, final Set<Integer> setOfRoutesTwo){

        if(setOfRoutesOne ==null || setOfRoutesOne.isEmpty()){
            return false;
        }
        if(setOfRoutesTwo ==null || setOfRoutesTwo.isEmpty()){
            return false;
        }

        if(setOfRoutesOne.size() < setOfRoutesTwo.size()){
            return hasIntersectionFromShorter(setOfRoutesOne, setOfRoutesTwo);
        }else{
            return hasIntersectionFromShorter(setOfRoutesTwo, setOfRoutesOne);
        }

    }

    private boolean hasIntersectionFromShorter(final Set<Integer> setOfRoutesShorter, final Set<Integer> setOfRoutesLonger){

        for(Integer routeId:setOfRoutesShorter){
            if(setOfRoutesLonger.contains(routeId)){
                return true;
            }
        }
        return false;
    }

}
