package goeuro;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BusRouteCacheManagerInMemoryTests {

    @Mock
    private BusRouteLoader busRouteLoader;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenMapOfStationsIsNull() throws Exception {

        Mockito.when(busRouteLoader.loadCache()).thenReturn(null);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(busRouteLoader);

        Assert.assertFalse(busRouteCacheManagerInMemoryCache.hasDirectConnection(1, 2));

    }

    @Test
    public void whenDepartureStationDoesntExist() throws Exception {

        final BusRouteLoaderFile realFileBusRouteLoaderFile =
                new BusRouteLoaderFile(BusRouteLoaderFileTests.SAMPLE_TEST_FILE);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(realFileBusRouteLoaderFile);

        Assert.assertFalse(busRouteCacheManagerInMemoryCache.hasDirectConnection(9999, 150));

    }

    @Test
    public void whenArrivalStationDoesntExist() throws Exception {

        final BusRouteLoaderFile realFileBusRouteLoaderFile =
                new BusRouteLoaderFile(BusRouteLoaderFileTests.SAMPLE_TEST_FILE);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(realFileBusRouteLoaderFile);

        Assert.assertFalse(busRouteCacheManagerInMemoryCache.hasDirectConnection(150, 9999));

    }

    @Test
    public void whenDepartureRoutesListIsBigger() throws Exception {

        final BusRouteLoaderFile realFileBusRouteLoaderFile =
                new BusRouteLoaderFile(BusRouteLoaderFileTests.SAMPLE_TEST_FILE);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(realFileBusRouteLoaderFile);

        Assert.assertTrue(busRouteCacheManagerInMemoryCache.hasDirectConnection(114, 153));

    }

    @Test
    public void whenArrivalRoutesListIsBigger() throws Exception {

        final BusRouteLoaderFile realFileBusRouteLoaderFile =
                new BusRouteLoaderFile(BusRouteLoaderFileTests.SAMPLE_TEST_FILE);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(realFileBusRouteLoaderFile);

        Assert.assertTrue(busRouteCacheManagerInMemoryCache.hasDirectConnection(153, 114));

    }

    @Test
    public void whenTwoStationsHasNoDirectConnection() throws Exception {

        final BusRouteLoaderFile realFileBusRouteLoaderFile =
                new BusRouteLoaderFile(BusRouteLoaderFileTests.SAMPLE_TEST_FILE);

        final BusRouteCacheManagerInMemoryCache busRouteCacheManagerInMemoryCache =
                new BusRouteCacheManagerInMemoryCache(realFileBusRouteLoaderFile);

        Assert.assertFalse(busRouteCacheManagerInMemoryCache.hasDirectConnection(114, 106));

    }

}