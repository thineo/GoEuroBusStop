package goeuro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static String busStationFile = null;

    public static void main(String[] args) {

        if(args.length < 1){
            throw new IllegalArgumentException("Bus Route data file needs to be informed");
        }

        busStationFile = args[0];

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BusRouteCacheManager busStopCacheManager() {

        final BusRouteLoader busRouteLoader = new BusRouteLoaderFile(busStationFile);

        return new BusRouteCacheManagerInMemoryCache(busRouteLoader);
    }
}
