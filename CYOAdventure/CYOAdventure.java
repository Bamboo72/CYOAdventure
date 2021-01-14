// Jacob Schwartz - 8/16/2020
// I wanted to create a text based adventure where you can make decisions that alter the ending.

/*
    Things to add:
        DONE - Start new game method
        DONE - Password method (to load character data)
        DONE - Save method (to record character data)
        DONE - Battle mode
        DONE - Dungeon prototype
        DONE - Delay between text and maybe display text one char at a time?
        - additions to text system: a way to fast forward text, make sure only 1 char is taken in for UserOptions
        DONE - Inventory system
        - additions to inventory: useable items, item descriptions, fix the bug written down in Combat.java
        - A "run away" system that keeps track of the last room you were in so you can go back to it, but won't work if doors are locked.
        - Multiple attacks for enemies, with different chances of happening?
        - Player names along with their passwords?
        - Story mode
        - Format save files better

    Things to learn:
       DONE - recording and reading data
       DONE - timer method
       DONE - random number gen (Check Mastermind project)
       DONE - wait to progress until space or left click is done
       DONE - One char at a time text

    Things to fix:
        - Inconsistencies in the dungeon
        - Balance enemy fights
        - Format text better
        - Change variable access specifiers? (Make instance variables private)
        - I need to add loot counts and type to the enemy loot data
        - While escaping a battle, I got stuck trying to flee
        - Incorrect input handling

*/

//import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class CYOAdventure {

    static String input;
    static Scanner inputReader = new Scanner(System.in);

    static int O = 0;

    static Data data = new Data();
    static Inventory inven = new Inventory();

    static boolean listen = true;

    public static void userOption() {
        boolean goodInput = false;
        input = inputReader.nextLine();
        if (input.equals("i")) {
            inven.loadInventory();
            inven.openInventory();
            O = 100;
        } else {
            while (!goodInput) {
                try {
                    O = Integer.parseInt(input);
                    goodInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Input error, please try again.");
                    input = inputReader.nextLine();
                    goodInput = false;
                }

            }

        }
    }

    public static void delay(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waiter(String waitFor) {
        // System.in.read();
        input = inputReader.nextLine();
        while (!input.equals(waitFor)) {
            System.out.println("Incorrect input!");
            input = inputReader.nextLine();
        }
    }

    // This is the default speak method (waits 50 ms between characters)
    public static void speak(String dialog) throws InterruptedException {
        for (int i = 0; i < dialog.length(); i++) {
            System.out.print(dialog.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    // This speak method takes in an int for the spacing between characters
    public static void speak(String dialog, int sec) throws InterruptedException {
        for (int i = 0; i < dialog.length(); i++) {
            System.out.print(dialog.charAt(i));
            try {
                Thread.sleep(sec);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static int randomNum(int numMax) {
        Random rand = new Random();
        int num = rand.nextInt(numMax);
        return num;
    }

    public static void main(String[] args) throws InterruptedException {
        boolean goodInput = false;

        System.out.println("═════════════════════════════════════════════════════════════════════");

        System.out.println("Welcome to CYO Adventure! Are you a (1) new or (2) returning player?");

        while (!goodInput) {
            userOption();
            if (O == 1) {
                data.newGame();
                goodInput = true;
            } else if (O == 2) {
                data.loadPass();
                System.out.println("\t Health is " + data.health + " and location is " + data.location + ".");
                goodInput = true;
            } else {
                System.out.println("Input error, please try again.");
            }
        }

        inven.loadInventory();
        inven.printDetailedInventory();
        data.save();
        System.out.println("Press Enter to Start");
        waiter("");

        // Start the listener thread
        /*
         * TextSpeederThread r1 = new TextSpeederThread(); Thread t1 = new Thread(r1);
         * t1.start();
         */

        SlimeCave.demoDungeon(0);

        // data.loadPass();
        // inven.loadInventory();
        // inven.invenFull();
        // inven.itemExist(2);
        // // type ID count
        // // inven.itemGet(3, 2, 1);
        // inven.itemUse(3, 2, 22);
        // data.save();

    }

}