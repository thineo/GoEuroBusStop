package goeuro;

import goeuro.exception.CacheLoadException;
import goeuro.exception.UnexpectedFileFormatException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Set;

@RunWith(SpringRunner.class)
public class BusRouteLoaderFileTests {

    public static final String SAMPLE_TEST_FILE = "src/test/resources/SimpleTestLoadFile.txt";
    public static final String FILE_WITH_LESS_THAN_EXPECTED_LINES = "src/test/resources/FileWithLessThanExpectedLines.txt";
    public static final String FILE_WITH_LETTER_WHERE_IT_IS_EXPECTED_NUMBERS = "src/test/resources/FileWithLetterWhereItIsExpectedNumbers.txt";
    public static final String FILE_WITH_EMPTY_ROUTE_LINE = "src/test/resources/FileWithRouteLineEmpty.txt";
    public static final String FILE_THAT_DOES_NOT_EXIT = "FileThatDoesNotExit";

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSimpleTestLoadFile() throws Exception {
        final BusRouteLoaderFile busRouteLoaderFile =
                new BusRouteLoaderFile(SAMPLE_TEST_FILE);

        final HashMap<Integer, Set<Integer>> mapStationsByRoutes =
                busRouteLoaderFile.loadCache();

        final Set<Integer> routes = mapStationsByRoutes.get(153);

        Assert.assertEquals(4,routes.size());
        Assert.assertTrue(routes.contains(1));
        Assert.assertTrue(routes.contains(19));
        Assert.assertTrue(routes.contains(13));
        Assert.assertTrue(routes.contains(18));
    }

    @Test(expected = UnexpectedFileFormatException.class)
    public void whenFileHasLessLinesThanExpected() throws Exception {
        final BusRouteLoaderFile busRouteLoaderFile =
                new BusRouteLoaderFile(FILE_WITH_LESS_THAN_EXPECTED_LINES);

        final HashMap<Integer, Set<Integer>> mapStationsByRoutes =
                busRouteLoaderFile.loadCache();
    }

    @Test(expected = UnexpectedFileFormatException.class)
    public void whenFileContainsLetterWhereItIsExpectedNumbers() throws Exception {
        final BusRouteLoaderFile busRouteLoaderFile =
                new BusRouteLoaderFile(FILE_WITH_LETTER_WHERE_IT_IS_EXPECTED_NUMBERS);

        final HashMap<Integer, Set<Integer>> mapStationsByRoutes =
                busRouteLoaderFile.loadCache();
    }

    @Test(expected = UnexpectedFileFormatException.class)
    public void whenFileHasEmptyRouteLine() throws Exception {
        final BusRouteLoaderFile busRouteLoaderFile =
                new BusRouteLoaderFile(FILE_WITH_EMPTY_ROUTE_LINE);

        final HashMap<Integer, Set<Integer>> mapStationsByRoutes =
                busRouteLoaderFile.loadCache();
    }

    @Test(expected = CacheLoadException.class)
    public void whenFileDoesNotExist() throws Exception {
        final BusRouteLoaderFile busRouteLoaderFile =
                new BusRouteLoaderFile(FILE_THAT_DOES_NOT_EXIT);

        final HashMap<Integer, Set<Integer>> mapStationsByRoutes =
                busRouteLoaderFile.loadCache();
    }

}