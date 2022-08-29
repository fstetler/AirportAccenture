import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Airport {
    private static final String COMMA_DELIMITER = ",";
    private static final int COUNTRY_COLUMN = 8;
    private static final int NUMBER_OF_TOP_COUNTRIES = 10;

    public static void main(String [] args) throws FileNotFoundException {
        List<List<String>> airports = scanAndReturnCvsfile("C:\\Programming\\Java\\AirportAccenture\\airport\\resources\\airports.csv");
        List<String> countryList = setListofCountries(airports);
        List<Integer> numberOfCountriesInList = setNumberOfCountriesInList(countryList, airports);
        List<String> topTenCountriesWithAirports = setTopTenCountriesWithAirports(countryList, numberOfCountriesInList);
        System.out.println(topTenCountriesWithAirports);
    }

    /**
     * Creates a list of the top ten countries that appear most times in the original list, going from highest to lowest
     * @param countryList
     * @param numberOfCountriesInList
     * @return
     */
    private static List<String> setTopTenCountriesWithAirports(List<String> countryList, List<Integer> numberOfCountriesInList) {
        List<String> topTenCountriesWithAirports = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TOP_COUNTRIES; i++) {
            Integer maxVal = Collections.max(numberOfCountriesInList);
            topTenCountriesWithAirports.add(countryList.get(numberOfCountriesInList.indexOf(maxVal)));
            countryList.remove(numberOfCountriesInList.indexOf(maxVal));
            numberOfCountriesInList.remove(numberOfCountriesInList.indexOf(maxVal));
        }
        return topTenCountriesWithAirports;
    }

    /**
     * Creates a list of integers where each Integer represents how many times that country appears in the original airport list
     * @param countryList
     * @param airports
     * @return
     */
    private static List<Integer> setNumberOfCountriesInList(List<String> countryList, List<List<String>> airports) {
        List<Integer> numberOfCountriesInList = new ArrayList<>();
        for (int i = 0; i < countryList.size(); i++) {
            int value = 0;
            for (int j = 0; j < airports.size(); j++) {
                if (Objects.equals(countryList.get(i), airports.get(j).get(8))) {
                    value += 1;
                }
            }
            numberOfCountriesInList.add(value);
        }
        return numberOfCountriesInList;
    }

    /**
     * Creates a list of all the unique countries in airports csv
     * @param airports
     * @return
     */
    private static List<String> setListofCountries(List<List<String>> airports) {
        List<String> countryList = new ArrayList<>();
        for (int i = 0; i < airports.size(); i++) {
            if (!countryList.contains(airports.get(i).get(COUNTRY_COLUMN))) {
                countryList.add(airports.get(i).get(COUNTRY_COLUMN));
            }
        }
        return countryList;
    }

    /**
     * Scans the csv file and returns its content as a List of Lists
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    private static List<List<String>> scanAndReturnCvsfile(String filePath) throws FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath));) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        }
        return records;
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
