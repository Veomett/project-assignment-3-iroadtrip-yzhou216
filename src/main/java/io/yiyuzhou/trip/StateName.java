package io.yiyuzhou.trip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class StateName {
	private HashMap<String, String> countryNameIds;

	public StateName(String path) {
		countryNameIds = new HashMap<>();
		parseStateNames(path);
	}

	public String getId(String countryName) {
		return countryNameIds.get(countryName);
	}

	/**
	 * Parses the CSV file and stores the data in the countryNameIds hashmap.
	 *
	 * @param filePath the path to the CSV file
	 */
	private void parseStateNames(String filePath) {
		try {
			/* open the file from the provided file path */
			InputStream inputStream = new FileInputStream(filePath);
			Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			/* define the TSV format and create the parser */
			CSVFormat format = CSVFormat.TDF.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim();
			try (CSVParser csvParser = new CSVParser(reader, format)) {
				for (CSVRecord record : csvParser) {
					String stateId = record.get("stateid");

					String countryName = record.get("countryname");
					countryName = countryName.replaceAll(" \\(.*?\\)", ""); /* excludes parentheses */
					countryNameIds.put(countryName, stateId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
