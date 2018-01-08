package pt.eseig.dps.controllers;

import pt.eseig.dps.models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UserController {

	private static String fileName = "mockDB/users.txt";
	private static ArrayList<User> users = new ArrayList<>();

	public static ArrayList<User> getUsers() {
		return users;
	}

	public static String authenticate(String name, char[] password) {
		for (User user : users) {
			if (user.getName().equals(name) && Arrays.equals(user.getPassword().toCharArray(), password)) {
				return user.getName() + "#" + user.getPassword() + "#" + user.getEmail() + "#" + user.getAccessLevel();
			}
		}
		return "";
	}

    public static String checkUserName(String nome) {
        String line = "";
        for (int i = 0; i < UserController.getUsers().size(); i++) {
            User user = UserController.getUsers().get(i);
            if (user.getName().equals(nome)) {
                line = "Exists";
                return line;
            }
        }
        return line;
    }

    // LOADS 'users' FROM THE FILE './mockDB/users.txt'
    public static void loadUsersFromFile() {
        Scanner inputFile = null;
        File file = new File("mockDB/users.txt");
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            inputFile = new Scanner(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        users.clear();
        assert inputFile != null;
        while (inputFile.hasNextLine()) {
            String line = inputFile.nextLine();
            String[] fields = line.split("#");
            User user = new User(fields[0], fields[1], fields[2], fields[3]);
            users.add(user);
        }
    }

    // SAVES 'users' TO THE FILE './mockDB/users.txt'
	public static void saveUsersToFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter (fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (User user : users) {
			String line = user.getName() + "#" + user.getPassword() + "#" + user.getEmail() + "#" + user.getAccessLevel();
			assert out != null;
			out.println(line);
		}
		assert out != null;
		out.close();
	}

}
