package goeuro;

import goeuro.exception.CacheLoadException;
import goeuro.exception.UnexpectedFileFormatException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class BusRouteLoaderFile implements BusRouteLoader {

    private static final String FILE_SEPARATOR = " ";

    @NonNull
    private final String busRoutesFilePath;

    private HashMap<Integer, Set<Integer>> mapStationBySetOfRoutes= new HashMap<>();

    public HashMap<Integer, Set<Integer>> loadCache(){
        try {
            final BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(busRoutesFilePath));

            final Integer amountOfBusRoutes = getAmountOfBusRoute(bufferedReader);

            loadBusRoutes(bufferedReader, amountOfBusRoutes);

            return mapStationBySetOfRoutes;

        } catch (IOException e) {
            throw new CacheLoadException(e);
        }
    }

    private Integer getAmountOfBusRoute(final BufferedReader bufferedReader) throws IOException{
        final String currentLine = readNextValidLine(bufferedReader);

        return Integer.valueOf(currentLine);
    }

    private void loadBusRoutes(final BufferedReader bufferedReader, final Integer amountOfBusRoutes) throws IOException {
        for(int i=0;i<amountOfBusRoutes;i++){
            final String currentLine = readNextValidLine(bufferedReader);

            final String[] route = currentLine.split(FILE_SEPARATOR);

            processRoute(route);
        }
    }

    private void processRoute(final String[] route) throws IOException{
        final Integer routeId = extractNumber(route[0]);
        for(int i=1;i<route.length;i++){
            final Integer station = extractNumber(route[i]);
            processStationOnRoute(routeId, station);
        }
    }

    private void processStationOnRoute(final Integer routeId, final Integer station) throws IOException{
        Set<Integer> listOfRoutes = mapStationBySetOfRoutes.get(station);
        if(listOfRoutes==null){
            listOfRoutes = new HashSet<>();
            mapStationBySetOfRoutes.put(station, listOfRoutes);
        }

        listOfRoutes.add(routeId);
    }

    private String readNextValidLine(final BufferedReader bufferedReader) throws IOException {
        String currentLine = null;

        currentLine = bufferedReader.readLine();

        if(currentLine==null || currentLine.isEmpty()){
            throw new UnexpectedFileFormatException();
        }
        return currentLine;
    }

    private Integer extractNumber(final String str){
        try {
            return Integer.valueOf(str);
        }catch (NumberFormatException e){
            throw new UnexpectedFileFormatException();
        }
    }

}
