package io.yiyuzhou.trip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Borders {
	private HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();

	public HashMap<String, HashMap<String, Integer>> getGraph() {
		return graph;
	}

	public Borders(String borderPath, String capdistPath, String stateNamePath) throws IOException, ParseException {
		String[] lines = getBorderFileLines(borderPath);
		parseCountryBorders(lines, capdistPath, stateNamePath);
	}

	private String[] getBorderFileLines(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists())
			throw new IllegalArgumentException("File not found!");

		List<Object> linesList;
		try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
			linesList = reader.lines().collect(Collectors.toList());
		}

		String[] lines = new String[linesList.size()];
		for (int i = 0; i < lines.length; i++)
			lines[i] = (String) linesList.get(i);

		return lines;
	}

	private List<String> extractCountries(String input) {
		List<String> ret;
		if (input == null) {
			ret = null;
		} else {
			List<String> countries = new ArrayList<>();
			ret = countries;
			Pattern pattern = Pattern.compile("[A-Za-z\\s\\(\\)]+(?=\\s\\d+[,.]?\\d*\\s*km)");
			Matcher matcher = pattern.matcher(input);

			while (matcher.find()) {
				String country = matcher.group().trim(); /* trim to remove leading/trailing spaces */
				countries.add(country);
			}
		}

		return ret;
	}

	private void parseCountryBorders(String[] lines, String capdistPath, String stateNamePath) throws IOException {
		Capdists capdists = new Capdists(capdistPath);
		StateName stateName = new StateName(stateNamePath);

		for (String line : lines) {
			String[] parts = line.split(" = ");
			String country = parts[0];
			String adjacencies;
			if (parts.length > 1)
				adjacencies = parts[1];
			else
				adjacencies = null;

			List<String> adjs = extractCountries(adjacencies);

			if (adjs != null) {
				HashMap<String, Integer> dest = new HashMap<>(); /* value for graph HashMap */
				for (int i = 0; i < adjs.size(); i++) {
					/* replace with edge cases in the HashMap */
					HashMap<String, String> edgeCases = edgeCases();
					if (edgeCases.get(country) != null)
						country = edgeCases.get(country);

					if (edgeCases.get(adjs.get(i)) != null)
						adjs.set(i, edgeCases.get(adjs.get(i)));

					String countryId = stateName.getId(country);
					String adjId = stateName.getId(adjs.get(i));
					int dist = capdists.getDistance(countryId, adjId);
					if (dist != -1)
						dest.put(adjs.get(i), capdists.getDistance(countryId, adjId));
				}

				graph.put(country, dest);
			}
		}
	}

	private HashMap<String, String> edgeCases() {
		HashMap<String, String> edgeCases = new HashMap<>();
		edgeCases.put("United States", "United States of America");
		edgeCases.put("US", "United States of America");
		edgeCases.put("Canada 1.3 km", "Canada");
		edgeCases.put("Czechia", "Czech Republic");
		edgeCases.put("Turkey (Turkiye)", "Turkey (Ottoman Empire");
		edgeCases.put("Turkey", "Turkey (Ottoman Empire");
		edgeCases.put("Korea, South", "Korea, Republic of");
		edgeCases.put("Korea, North", "Korea, People's Republic of");
		edgeCases.put("Cambodia", "Cambodia (Kampuchea");
		edgeCases.put("Vietnam", "Vietnam, Democratic Republic of");
		edgeCases.put("Russia (Kaliningrad)", "Russia (Soviet Union");
		edgeCases.put("Russia", "Russia (Soviet Union");
		edgeCases.put("Russia", "Russia (Soviet Union)");
		edgeCases.put("Timor-Leste", "East Timor");
		edgeCases.put("Cabo Verde", "Cape Verde");
		edgeCases.put("Cote d'Ivoire", "Ivory Coast");
		edgeCases.put("Gambia, The", "Gambia");
		edgeCases.put("Bahamas, The", "Bahamas");
		edgeCases.put("Czechia", "Czech Republic");
		edgeCases.put("North Macedonia", "Macedonia (Former Yugoslav Republic of");
		edgeCases.put("Belarus", "Belarus (Byelorussia");
		edgeCases.put("Macedonia", "Macedonia (Former Yugoslav Republic of");
		edgeCases.put("The Central African Republic", "Central African Republic");
		edgeCases.put("Congo, Democratic Republic of the", "Congo, Democratic Republic of (Zaire");
		edgeCases.put("Congo, Republic of the", "Congo");
		edgeCases.put("The Republic of the Congo", "Congo");
		edgeCases.put("Democratic Republic of the Congo", "Congo, Democratic Republic of (Zaire");
		edgeCases.put("The Slovak Republic", "Slovakia");
		edgeCases.put("Zimbabwe", "Zimbabwe (Rhodesia");
		edgeCases.put("Iran", "Iran (Persia");
		edgeCases.put("Botswana 0.15 km", "Botswana");
		edgeCases.put("Zambia 0.15 km", "Zambia");
		edgeCases.put("Denmark (Greenland) 1.3 km", "Denmark");
		edgeCases.put("Gibraltar 1.2 km", "Gibraltar");
		edgeCases.put("Holy See (Vatican City) 3.4 km", "Holy See (Vatican City)");
		edgeCases.put("Denmark (Greenland)", "Denmark");
		edgeCases.put("Yemen (Arab Republic of Yemen", "Yemen");
		edgeCases.put("Tanzania", "Tanzania/Tanganyika");
		edgeCases.put("The Solomon Islands", "Solomon Islands");
		edgeCases.put("UK", "United Kingdom");
		edgeCases.put("Germany", "German Federal Republic");
		edgeCases.put("Spain (Ceuta)", "Spain");
		edgeCases.put("Morocco (Cueta)", "Morocco");
		edgeCases.put("Spain 1.2 km", "Spain");
		edgeCases.put("Italy", "Italy/Sardinia");
		edgeCases.put("Burkina Faso", "Burkina Faso (Upper Volta");
		return edgeCases;
	}
}
