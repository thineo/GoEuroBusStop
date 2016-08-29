package goeuro;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BusRouteResponseControllerTests {

    public static final String STATION_ONE = "1";
    public static final String STATION_TWO = "2";

    public static final Integer STATION_ONE_NUMBER = 1;
    public static final Integer STATION_TWO_NUMBER = 2;

    public static final boolean HAS_DIRECT_CONNECTION = true;
    public static final boolean HAS_NOT_DIRECT_CONNECTION = false;

    @Mock
    private BusRouteCacheManager busStopCacheManager;

    private BusRouteServiceController busRouteServiceController;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.busRouteServiceController = new BusRouteServiceController(busStopCacheManager);
    }

    @After
    public void after() throws Exception {
        verify(busStopCacheManager, only()).hasDirectConnection(eq(STATION_ONE_NUMBER), eq(STATION_TWO_NUMBER));
    }

    @Test
    public void whenBusStopCacheManagerReturnsHasDirectConnection() throws Exception {
        when(busStopCacheManager.hasDirectConnection(STATION_ONE_NUMBER, STATION_TWO_NUMBER)).thenReturn(HAS_DIRECT_CONNECTION);

        final BusRouteResponse busRouteResponse =
                this.busRouteServiceController.hasDirectConnectionPath(STATION_ONE, STATION_TWO);

        Assert.assertEquals(STATION_ONE_NUMBER, busRouteResponse.getDep_sid());
        Assert.assertEquals(STATION_TWO_NUMBER, busRouteResponse.getArr_sid());
        Assert.assertEquals(HAS_DIRECT_CONNECTION, busRouteResponse.isDirect_bus_route());

    }

    @Test
    public void whenBusStopCacheManagerReturnsHasNotDirectConnection() throws Exception {
        when(busStopCacheManager.hasDirectConnection(STATION_ONE_NUMBER, STATION_TWO_NUMBER)).thenReturn(HAS_NOT_DIRECT_CONNECTION);

        final BusRouteResponse busRouteResponse =
                this.busRouteServiceController.hasDirectConnectionRequest(STATION_ONE, STATION_TWO);

        Assert.assertEquals(STATION_ONE_NUMBER, busRouteResponse.getDep_sid());
        Assert.assertEquals(STATION_TWO_NUMBER, busRouteResponse.getArr_sid());
        Assert.assertEquals(HAS_NOT_DIRECT_CONNECTION, busRouteResponse.isDirect_bus_route());

    }

}