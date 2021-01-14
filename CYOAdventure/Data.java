// Jacob Schwartz - 9/18/2020
// This is the class for all the data methods

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {

    static String input;
    static Scanner inputReader = new Scanner(System.in);
    static boolean found = false;

    String Password;
    String gender = "";
    String location;
    String currentLoc;
    int attack, armor, health, speed, bagSize;
    int eAttack, eArmor, eHealth, loot, chance, item;
    String inventoryData, inventoryCount;

    public void newGame() {
        boolean passAccept = false;
        //String gender = "";

        // Input password, gender, and starting location
        System.out.println(
                "New Game! Please enter your desired password: \n(NOTE! This is case sensitive and important to remember.) \n No spaces or semicolons allowed.");
        while (!passAccept) {
            input = inputReader.nextLine();

            fileScan(input);
            if (found) {
                System.out.println("Error! Your password is already used! Please try again:");
            } else {
                System.out.println("Password accepted. It was " + input + ". Remember it!");
                passAccept = true;
                Password = input;
            }
        }

        System.out.println("Alrighty! Now are you a (1) Male or (2) Female?");
        input = inputReader.nextLine();
        if (input.equals("1")) {
            gender = "male";
        } else if (input.equals("2")){
            gender = "female";
        } else {
            System.out.println("Input not recognized, gender temporarily set to unknown.");
            gender = "unknown";
        }
        location = "home";
        attack = 4;
        armor = 2;
        health = 10;
        speed = 10;
        inventoryData = "1.1.1.0.0.0";
        inventoryCount = "1.1.1.0.0.0";
        bagSize = 6;
        newSave(Password, gender, location, attack, armor, health, speed, inventoryData, inventoryCount, bagSize);
        System.out.println("New save succesfully made! Have fun on your adventure!");
    }

    // This only looks for the Password
    public void fileScan(String Password) {

        String s = null;
        found = false;

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File("SaveData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Two scanners: One for the lines, and one for the words on each line
        while (s1.hasNextLine()) {
            Scanner s2 = new Scanner(s1.nextLine());

            while (s2.hasNext() && !found) {
                s = s2.next();

                if (s.equals(Password)) {
                    found = true;

                    // System.out.println("Password found!");
                    // gender = s2.next();
                    // location = s2.next();
                    // System.out.println("Gender is " + gender + ". " + location + " is the
                    // location");
                }
            }

        }
    }

    public void enemyFileScan(String Password) {

        String s = null;
        found = false;

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File("EnemyData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Two scanners: One for the lines, and one for the words on each line
        while (s1.hasNextLine()) {
            Scanner s2 = new Scanner(s1.nextLine());

            while (s2.hasNext() && !found) {
                s = s2.next();

                if (s.equals(Password)) {
                    found = true;

                    // Attack
                    eAttack = s2.nextInt();

                    // Armor
                    eArmor = s2.nextInt();

                    // Health
                    eHealth = s2.nextInt();

                    // Loot?
                    loot = s2.nextInt();

                    // Chance
                    chance = s2.nextInt();

                    // Item
                    item = s2.nextInt();

                }
            }

        }
    }

    public int[] inventoryScan(String type) {
        int bag = bagSize;

        // Inventory data scanner that uses charAt()
        int j = 0; // Counter for going through the inventoryID array
        String builder = ""; // A string/char builder to parse the complete number IDs
        int[] inventoryID = new int[bag]; // This is the int array for inventory
        int[] counts = new int[bag]; // This is the int array for the counts

        if (type.equals("id")) {

            for (int i = 0; i < inventoryData.length(); i++) {

                if (inventoryData.charAt(i) != '.') {
                    // System.out.println(j + " " + inventoryID[j]);
                    builder = builder + String.valueOf(inventoryData.charAt(i));
                    // System.out.println(j + " " + inventoryID[j]);

                } else { // If it is a '.' then we move on to the next index by j++ and we reset the
                         // builder string
                    j++;
                    builder = "";
                }
                if (!builder.equals("")) {
                    inventoryID[j] = Integer.parseInt(builder);
                }

            }
            return inventoryID;
        }

        if (type.equals("count")) {

            for (int i = 0; i < inventoryCount.length(); i++) {

                if (inventoryCount.charAt(i) != '.') {

                  //  System.out.println(j + " " + counts[j]);
                    builder = builder + String.valueOf(inventoryCount.charAt(i));

                } else { // If it is a '.' then we move on to the next index by j++ and we reset the
                         // builder string
                         // If the count is 0 then change the ID to 0 too.
                    if (counts[j] == 0) {
                        inventoryID[j] = 0;
                    }
                    j++;
                    builder = "";
                }
                if (!builder.equals("")) {
                    counts[j] = Integer.parseInt(builder);
                }

            }
            return counts;
        }

        int[] error = new int[1];
        System.out.println("You've reached Error, please leave a message!");
        return error;
    }

    public void newSave(String Password, String gender, String location, int attack, int armor, int health, int speed,
            String inventoryData, String inventoryCount, int bagSize) {

        // This method needs to scan to the end, then append the new file save

        String s = null;
        String replace = "";

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File("SaveData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Two scanners: One for the lines, and one for the words on each line
        while (s1.hasNextLine()) {
            Scanner s2 = new Scanner(s1.nextLine());

            while (s2.hasNext()) {
                s = s2.next();

                replace = replace + s + " ";

                // New lines
                if (s.equals(";")) {
                    replace = replace + System.lineSeparator();
                }

            }
        }

        // This is where I'll add the new save
        replace = String.format( replace + "%15s %15s %15s %15s %15s %15s %15s %15s %15s %15s ; ", Password, gender, location, attack, armor, health, speed, inventoryData, inventoryCount, bagSize);

        // Write the replacement:
        FileWriter w = null;
        try {
            w = new FileWriter("SaveData.txt");
            w.write(replace);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPass() {
        // This method needs to scan to the password, then load the data

        boolean passAccept = false;

        System.out.print(">\tEnter your password: ");

        while (!passAccept) {
            input = inputReader.nextLine();
            fileScan(input);
            if (found) {
                System.out.println(">\tPassword accepted");
                Password = input;
                passAccept = true;
            } else {
                System.out.println(">\tError! Password not found. Please try again:");
            }
        }

        String s = null;

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File("SaveData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Two scanners: One for the lines, and one for the words on each line
        while (s1.hasNextLine()) {
            Scanner s2 = new Scanner(s1.nextLine());

            while (s2.hasNext()) {
                s = s2.next();

                // When password is found:
                if (s.equals(Password)) {

                    // Gender
                    gender = s2.next();

                    // Location
                    location = s2.next();

                    // Attack
                    attack = s2.nextInt();

                    // Armor
                    armor = s2.nextInt();

                    // Health
                    health = s2.nextInt();

                    // Speed
                    speed = s2.nextInt();

                    // Inventory Data
                    inventoryData = s2.next();

                    // Inventory Count
                    inventoryCount = s2.next();

                    // Bag Size
                    bagSize = s2.nextInt();

                    System.out.println(gender + " " + location + " " + attack + " " + armor + " " + health + " "
                            + inventoryData + " " + inventoryCount + " " + bagSize);
                }

            }
        }

    }

    public void save() {
        // This method needs to scan to the password, update the data, and then keep
        // scanning

        String s = null;
        String replace = "";

        Scanner s1 = null;
        try {
            s1 = new Scanner(new File("SaveData.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Two scanners: One for the lines, and one for the words on each line
        while (s1.hasNextLine()) {
            Scanner s2 = new Scanner(s1.nextLine());

            while (s2.hasNext()) {
                s = s2.next();

                replace = String.format(replace + "%15s ", s);
               
                // When password is found:
                if (s.equals(Password)) {

                    // Clear out the old save
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();
                    s = s2.next();

                    System.out.println("At this time, gender is: " + gender);

                    // Gender
                    //replace = replace + gender + " ";
                    replace = String.format(replace + "%15s ", gender);

                    // Location
                    // location = location.replace(location, currentLoc);
                    replace = String.format(replace + "%15s ", location);

                    // Attack
                    replace = String.format(replace + "%15s ", attack);

                    // Armor
                    replace = String.format(replace + "%15s ", armor);

                    // Health
                    replace = String.format(replace + "%15s ", health);

                    // Speed
                    replace = String.format(replace + "%15s ", speed);

                    // Inventory Data
                    replace = String.format(replace + "%15s ", inventoryData);

                    // Inventory Count
                    replace = String.format(replace + "%15s ", inventoryCount);

                    // Bag Size
                    replace = String.format(replace + "%15s ", bagSize);
                }

                // New lines
                if (s.equals(";")) {
                    replace = replace + System.lineSeparator();
                }

            }
        }

        // Write the replacement:
        FileWriter w = null;
        try {
            w = new FileWriter("SaveData.txt");
            w.write(replace);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
