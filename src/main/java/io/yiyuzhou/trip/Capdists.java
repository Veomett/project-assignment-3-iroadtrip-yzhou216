package io.yiyuzhou.trip;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Capdists {
	public HashMap<List<String>, Integer> capdists = new HashMap<>();

	public Capdists() throws IOException {
		capdists = new HashMap<List<String>, Integer>();
		parseCSV("capdist.csv");
	}

	public int getDistance(String country0, String country1) {
		int ret;
		if (capdists.get(Arrays.asList(country0, country1)) == null)
			ret = -1;
		else
			ret = capdists.get(Arrays.asList(country0, country1));

		return ret;
	}

	private void parseCSV(String fileName) throws IOException {
		/* Access the file as a resource */
		try (InputStream is = Capdists.class.getClassLoader().getResourceAsStream(fileName)) {
			if (is == null)
				throw new IOException("Resource not found: " + fileName);

			InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

			try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
				for (CSVRecord record : csvParser) {
					if (record.getRecordNumber() > 1) /* skip the first line of csv file which doesn't contain data */
						capdists.put(Arrays.asList(record.get(1), record.get(3)), Integer.parseInt(record.get(4)));
				}
			}
		}
	}
}
