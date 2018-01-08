package pt.eseig.dps.controllers;

import pt.eseig.dps.models.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TagController {
	
	private static ArrayList<Tag> tags = new ArrayList<>();

	public static ArrayList<Tag> getTags() {
	    return tags;
	}
	
    public static void loadTagDataFromFile() {
		Scanner inputFile = null;
        File file = new File("mockDB/tags.txt");
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tags.clear();
        assert inputFile != null;
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] fields = line.split("==");
            Tag tag = new Tag(fields[0], fields[1], fields[2], fields[3], fields[4]);
            tags.add(tag);

        }
	}

    public static void saveTagDataToFile() {
        PrintWriter out = null;
        try {
            out = new PrintWriter ("mockDB/tags.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Tag tag : tags) {
            String tagString = tag.getId() + "==" + tag.getName() + "==" + tag.getDeviceName() + "==" + tag.getDeviceType() + "==" + tag.getState();
            assert out != null;
            out.println(tagString);
        }
        assert out != null;
        out.close();
    }

}
