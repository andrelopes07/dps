package pt.eseig.dps.controllers;

import pt.eseig.dps.models.DeviceType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DeviceTypeController {

    private static ArrayList<DeviceType> deviceTypes = new ArrayList<>();

    public static ArrayList<DeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    public static void loadDeviceTypesFromFile() {
        Scanner inputFile = null;
        File file = new File("mockDB/deviceTypes.txt");
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        deviceTypes.clear();
        assert inputFile != null;
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            DeviceType deviceType = new DeviceType(line);
            deviceTypes.add(deviceType);
        }
    }

    public static void saveDeviceTypesToFile() {
        PrintWriter out = null;
        try {
            out = new PrintWriter ("mockDB/deviceTypes.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (DeviceType deviceType : deviceTypes) {
            String deviceTypeName = deviceType.getName();
            assert out != null;
            out.println(deviceTypeName);
        }
        assert out != null;
        out.close();
    }

}
