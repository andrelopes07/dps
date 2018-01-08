package pt.eseig.dps.controllers;

import pt.eseig.dps.models.Reading;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ReadingController {
	
	private static ArrayList<Reading> readings = new ArrayList<>();

	public static ArrayList<Reading> getReadings() {
		return readings;
	}

	public static void loadRegisterDataFromFile() {
		Scanner inputFile = null;
		File file = new File("mockDB/simulator.txt");
		file.getParentFile().mkdirs();
		try {
            file.createNewFile();
			inputFile = new Scanner(file);
		} catch (IOException e) {
            e.printStackTrace();
        }
        readings.clear();
        assert inputFile != null;
        while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String[] fields = line.split("==");
			Reading reading = new Reading(convertStringToLong(fields[0]), fields[1], fields[2]);
			readings.add(reading);
		}
	}

	public static void saveManualReadingToFile(String readingString) {
		FileWriter fileWriter = null;
		File file = new File("mockDB/simulator.txt");
		file.getParentFile().mkdirs();
		try {
			fileWriter = new FileWriter(file,true);
			fileWriter.write(readingString + "\n");
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private static Long convertStringToLong(String date) {
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy#HH:mm:ss");
	    Long data = null;
        try {
            Date dateTime = formatter.parse(date);
            data = dateTime.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

}
