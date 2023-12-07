package io.yiyuzhou.trip;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Capdists {
	public HashMap<List<String>, Integer> capdists = new HashMap<>();

	public Capdists(String path) throws IOException {
		capdists = new HashMap<List<String>, Integer>();
		parseCSV(path);
	}

	/**
	 * Returns the distance between two countries.
	 *
	 * @param country0 the name of the first country
	 * @param country1 the name of the second country
	 * @return the distance between country0 and country1
	 */
	public int getDistance(String country0, String country1) {
		int ret;
		if (capdists.get(Arrays.asList(country0, country1)) == null)
			ret = -1;
		else
			ret = capdists.get(Arrays.asList(country0, country1));

		return ret;
	}

	/**
	 * Parses the CSV file and stores the data in the capdists hashmap.
	 *
	 * @param filePath the path to the CSV file
	 * @throws IOException
	 */
	private void parseCSV(String filePath) throws IOException {
		/* Open the file from the provided file path */
		try (Reader reader = new FileReader(filePath, StandardCharsets.UTF_8)) {

			try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
				for (CSVRecord record : csvParser) {
					if (record.getRecordNumber() > 1) /* skip the first line of csv file which doesn't contain data */
						capdists.put(Arrays.asList(record.get(1), record.get(3)), Integer.parseInt(record.get(4)));
				}
			}
		}
	}
}
