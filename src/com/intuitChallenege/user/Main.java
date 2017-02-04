package com.intuitChallenege.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {

	static HashMap<String, String[]> oldCategory = new HashMap<String, String[]>();
	static HashMap<String, String> category = new HashMap<String, String>();

	// user count for processing
	static int userId = 0;

	public List<UserInterests> execute() throws IOException {

		String workingDirectory = System.getProperty("catalina.home");
		String target_dir = workingDirectory + "/transaction-data/";
		File dir = new File(target_dir);

		File[] files = dir.listFiles();

		HashMap<Integer, List<String>> map = new HashMap<Integer, List<String>>();

		int index = 0;
		for (File f : files) {

			if (f.isFile()) {
				BufferedReader inputStream = null;

				try {
					inputStream = new BufferedReader(new FileReader(f));
					String line;

					while ((line = inputStream.readLine()) != null) {
						// List<String> list = Arrays.asList(line.split(","));

						if (map.containsKey(index)) {
							map.get(index).add(line);
						} else {
							List<String> list = new ArrayList<String>();
							list.add(line);
							map.put(index, list);
						}

					}
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
				}

			}

			index++;
		}

		generateCategories();
		List<UserInterests> interests = new ArrayList<UserInterests>();

		AggregatedInfo res = new AggregatedInfo();
		for (int i = 1; i < map.size() - 1; i++) {
			UserInfo val = process(i, map.get(i));
			interests.add(findInterests(val));
			res.userList.add(process(i, map.get(i)));
		}

		return interests;
	}

	public static UserInterests findInterests(UserInfo info) {
		UserInterests result = new UserInterests();

		result.setArts(info.getCategoryMap().get("arts") != null);
		result.setFood(info.getCategoryMap().get("food") != null);
		result.setGaming(info.getCategoryMap().get("gaming") != null);
		result.setHasPet(info.getCategoryMap().get("pet") != null);
		result.setMovies(info.getCategoryMap().get("movies") != null);
		result.setMusic(info.getCategoryMap().get("music") != null);
		result.setOutgoing(info.getCategoryMap().get("outgoing") != null);
		result.setPunctual(info.getCategoryMap().get("financiallyUnstable") == null);
		result.setReading(info.getCategoryMap().get("reading") != null);
		result.setSports(info.getCategoryMap().get("athletic") != null);
		result.setTraveling(info.getCategoryMap().get("traveling") != null);
		result.setStringId(userId++);

		return result;
	}

	public static void generateCategories() {
		oldCategory.put("student", new String[] { "student loans", "student",
				"online education", "courses" });
		oldCategory.put("financiallyUnstable", new String[] { "late fee",
				"bank fee", "negative balance", "late payment", "penalty" });
		oldCategory.put("annualIncome", new String[] { "paycheck" });
		oldCategory.put("music", new String[] { "guitar", "music", "piano",
				"concert", "podcast" });
		oldCategory.put("movies", new String[] { "star wars", "netflix",
				"on demand tv", "on demand movie", "hbo" });
		oldCategory.put("food", new String[] { "food delivery", "grubhub",
				"restaurant", "starbucks", "coffee" });
		oldCategory.put("traveling", new String[] { "flight", "cancun",
				"resort" });
		oldCategory.put("reading", new String[] { "book", "library" });
		oldCategory.put("isParent",
				new String[] { "babies", "baby", "prenatal" });
		oldCategory.put("outgoing", new String[] { "party", "night club",
				"brewery", "wine", "bar" });
		oldCategory.put("utilities", new String[] { "time warner", "water",
				"sewer" });
		oldCategory.put("arts", new String[] { "art", "museum", "art auction",
				"painting", "craft", "paint", "canvas" });
		oldCategory.put("publicTransport", new String[] { "lyft", "uber",
				"taxi", "bus", "train" });
		oldCategory.put("grocery", new String[] { "market", "whole foods" });
		oldCategory.put("gaming", new String[] { "video game", "play station",
				"wii" });
		oldCategory.put("entertainment", new String[] { "bowling",
				"karaoke bar" });
		oldCategory.put("athletic", new String[] { "sport", "fitness",
				"bike rental", "athletic", "gnc", "nba", "nfl", "gym",
				"vitamin" });
		oldCategory.put("pet", new String[] { "cat food", "pet" });

		// ren cat
		Iterator entries = oldCategory.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String) entry.getKey();
			String[] values = (String[]) entry.getValue();

			for (int i = 0; i < values.length; i++) {
				category.put(values[i], key);
			}
		}

	}

	public static UserInfo process(int userId, List<String> list) {
		UserInfo result = new UserInfo();

		HashMap<String, Float> cateInfo = new HashMap<String, Float>();
		float totalExpenses = 0;

		for (int i = 1; i < list.size(); i++) {
			String[] listStr = list.get(i).split(",");
			String vendorName = listStr[2];

			Float amount = Float.parseFloat(listStr[3]);
			totalExpenses = +amount;

			Iterator entries = category.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();

				if (vendorName.toLowerCase().contains(key.toLowerCase())) {
					if (!cateInfo.containsKey(value)) {
						cateInfo.put(value, amount);
					} else {
						cateInfo.put(value, cateInfo.get(value) + amount);
					}
				}

			}

		}

		// edit the total expenses
		if (cateInfo.containsKey("annualIncome")) {
			totalExpenses = Math.abs(totalExpenses)
					+ cateInfo.get("annualIncome");
		}

		result.setCategoryMap(cateInfo);
		result.setTotalExpenses(totalExpenses);

		return result;

	}

}
