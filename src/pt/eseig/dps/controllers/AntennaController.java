package pt.eseig.dps.controllers;

import pt.eseig.dps.models.Antenna;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AntennaController {
	
	private static ArrayList<Antenna> antennas = new ArrayList<>();
	
	public static ArrayList<Antenna> getAntennas() {
		return antennas;
	}

    public static void loadAntennaDataFromFile() {
        Scanner inputFile = null;
        File file = new File("mockDB/antennas.txt");
		file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        antennas.clear();
		assert inputFile != null;
		while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] fields = line.split("==");
            Antenna antenna = new Antenna(fields[0], fields[1], fields[2], fields[3], fields[4]);
            antennas.add(antenna);
        }
    }

	public static void saveAntennaDataToFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter ("mockDB/antennas.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Antenna antenna : antennas) {
			String antennaString = antenna.getId() + "==" + antenna.getName() + "==" + antenna.getBuilding() + "==" + antenna.getRoom() + "==" + antenna.getState();
			assert out != null;
			out.println(antennaString);
		}
		assert out != null;
		out.close();
	}

}
