package pt.eseig.dps.controllers;

import pt.eseig.dps.models.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RoomController {

    private static ArrayList<Room> rooms = new ArrayList<>();

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static void loadRoomsFromFile() {
        Scanner inputFile = null;
        File file = new File("mockDB/rooms.txt");
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rooms.clear();
        assert inputFile != null;
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String [] fields = line.split("#");
            Room room = new Room(fields[0], fields[1]);
            rooms.add(room);
        }
    }

    public static void saveRoomToFile(){
        PrintWriter out = null;
        try {
            out = new PrintWriter ("mockDB/rooms.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Room room : rooms) {
            String roomDataString = room.getName() + "#" + room.getBuilding();
            assert out != null;
            out.println(roomDataString);
        }
        assert out != null;
        out.close();
    }

}
