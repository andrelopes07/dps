package pt.eseig.dps.controllers;

import pt.eseig.dps.models.Building;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BuildingController {
	
	private static ArrayList<Building> buildings = new ArrayList<>();

	public static ArrayList<Building> getBuildings() {
		return buildings;
	}
	
	public static void loadBuildingsFromFile() {
		Scanner inputFile = null;
		File file = new File("mockDB/buildings.txt");
		file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildings.clear();
		assert inputFile != null;
		while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            Building building = new Building(line);
            buildings.add(building);
        }
	}

	public static void saveBuildingsToFile(){
		PrintWriter out = null;
		try {
			out = new PrintWriter ("mockDB/buildings.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Building building : buildings) {
			String buildingString = building.getName();
			assert out != null;
			out.println(buildingString);
		}
		assert out != null;
		out.close();
	}
	
}
