package goeuro;

import java.util.HashMap;
import java.util.Set;

public interface BusRouteLoader {

    HashMap<Integer, Set<Integer>> loadCache();

}
