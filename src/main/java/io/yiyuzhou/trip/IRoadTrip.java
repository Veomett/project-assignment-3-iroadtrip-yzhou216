package io.yiyuzhou.trip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class IRoadTrip {
	public static void main(String[] args) throws IOException, ParseException {
		Borders borders = new Borders(args[0], args[1], args[2]);
		acceptUserInput(borders);
	}

	/**
	 * Finds the shortest path between two countries using Dijkstra's algorithm.
	 * This method computes the shortest path in terms of distance (or cost)
	 * between the specified countries in the given borders graph.
	 *
	 * The method initializes distances to all countries as infinity,
	 * then gradually finds the shortest path by exploring adjacent countries.
	 * If a shorter path to a country is found, it updates the distance and
	 * the path. The path is reconstructed in reverse at the end of the algorithm.
	 *
	 * @param country1 The starting country for the path.
	 * @param country2 The destination country for the path.
	 * @param borders  The Borders object containing the graph of countries and
	 *                 their connections.
	 * @return A list of strings representing the path from country1 to country2,
	 *         each element showing the step in the format "CountryA --> CountryB
	 *         (distance km.)".
	 *         Returns null if no path exists between the two countries.
	 * @throws IllegalArgumentException If the borders graph does not contain
	 *         country1 or country2.
	 */
	public static List<String> findPath(String country1, String country2, Borders borders) {
		PriorityQueue<Node> minDistance = new PriorityQueue<>();
		HashMap<String, Integer> distances = new HashMap<>();
		HashMap<String, String> prevNode = new HashMap<>();
		Set<String> visited = new HashSet<>();
		List<String> ret;

		/* create nodes for every country and set the distance to infinity */
		for (String node : borders.getGraph().keySet())
			distances.put(node, Integer.MAX_VALUE);

		distances.put(country1, 0);
		minDistance.add(new Node(country1, 0));
		List<String> visitedNodes = new ArrayList<>();
		ret = visitedNodes;

		/* Dijkstra's algorithm */
		while (!minDistance.isEmpty()) {
			Node currentNode = minDistance.poll();
			String current = currentNode.node;

			if (!visited.contains(current)) /* add to visited set */
				visited.add(current);
			else
				continue; /* skip */

			/* border country */
			if (distances.get(current) == 1)
				continue;

			if (borders.getGraph().containsKey(current)) {
				for (Map.Entry<String, Integer> neighbor : borders.getGraph().get(current).entrySet()) {
					String adjacentCountry = neighbor.getKey();
					int weight = neighbor.getValue();
					int newDistance = distances.get(current) + weight;
					if (newDistance < distances.getOrDefault(adjacentCountry, Integer.MAX_VALUE)) {
						distances.put(adjacentCountry, newDistance);
						minDistance.add(new Node(adjacentCountry, newDistance));
						prevNode.put(adjacentCountry, current);
					}
				}
			}

			if (current.equals(country2))
				break;
		}

		while (!country2.equals(country1)) {
			String prevCountry = prevNode.get(country2);
			if (distances.get(country2) == null || distances.get(prevCountry) == null) {
				ret = null; /* no path exists */
				break;
			}

			int distance = distances.get(country2) - distances.get(prevCountry);
			visitedNodes.add(prevCountry + " --> " + country2 + " (" + distance + " km.)");
			country2 = prevCountry;
		}

		Collections.reverse(visitedNodes);

		return ret;
	}

	/**
	 * Accepts user input and finds the shortest path between two countries.
	 *
	 * @param borders the borders object
	 * @throws IOException
	 */
	public static void acceptUserInput(Borders borders) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		for (;;) {
			System.out.print("Enter the name of the first country (type EXIT to quit): ");
			String country0 = reader.readLine();
			if (country0.equals("EXIT"))
				break;

			if (borders.getGraph().get(country0) == null) {
				System.err.println("Invalid country name. Please enter a valid country name.");
				continue;
			}

			System.out.print("Enter the name of the second country (type EXIT to quit): ");
			String country1 = reader.readLine();
			if (country0.equals("EXIT"))
				break;

			if (borders.getGraph().get(country1) == null) {
				System.err.println("Invalid country name. Please enter a valid country name.");
				continue;
			}

			System.out.printf("Route from %s to %s: \n", country0, country1);
			List<String> paths = findPath(country0, country1, borders);
			for (int i = 0; i < paths.size(); i++)
				System.out.printf("* %s\n", paths.get(i));
		}
	}
}
