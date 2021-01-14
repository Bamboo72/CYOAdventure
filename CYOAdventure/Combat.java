// Jacob Schwartz - 9/18/2020
// This is the class for the combat system

/*

    I'd like to make combat more interesting. Look at games like Paper Mario or Octopath Traveller.
    When combat is required, it should be fun.
    Maybe make multiple attacks for both player and enemies, or add companions to the game
    
    Use inventory during battle

*/

import java.util.Random;
import java.util.Scanner;

public class Combat {

    static String input;
    static Scanner inputReader = new Scanner(System.in);

    static int O = 0;
    boolean ran = false;

    public static void userOption() {
        input = inputReader.nextLine();
        if (input.equals("i")) {
            O = 100;
        } else {
            O = Integer.parseInt(input);
        }
    }

    // This is the default speak method (waits 80 ms between characters)
    public static void speak(String dialog) throws InterruptedException {
        for (int i = 0; i < dialog.length(); i++) {
            System.out.print(dialog.charAt(i));
            try {
                Thread.sleep(80);
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

    // returns true if fight won, false if game over
    public boolean enemy(String type) throws InterruptedException {

        Data data = CYOAdventure.data;
        Inventory inven = CYOAdventure.inven;
        data.enemyFileScan(type);
        Random escapeChance = new Random();
        Random lootChance = new Random();
        boolean alive = true;
        boolean pTurn = true;

        System.out.println("\t \t *** Combat Start! ***");
        System.out.println("The enemy is a(n) " + type);

        while (alive) {
            while (pTurn) {
                System.out.println("Your health is " + data.health);
                System.out.println(">\tDo you want to (1) attack (2) check (3) flee ?");
                userOption();
                if (O == 1) {
                    System.out.println("═════════════════════════════════════════════════════════════════════");
                    data.eHealth -= (data.attack - data.eArmor);
                    System.out.println("You attacked and did " + (data.attack - data.eArmor) + " damage.");
                    pTurn = false;
                } else if (O == 2) {
                    System.out.println("═════════════════════════════════════════════════════════════════════");
                    System.out.println(
                            "Enemy Attack: " + data.eAttack + ", Enemy Armor: " + data.eArmor + ", Enemy Health: " + data.eHealth);
                   // pTurn = false;
                } else if (O == 3) {
                    System.out.println("═════════════════════════════════════════════════════════════════════");
                    int n = escapeChance.nextInt(5);
                    if (n >= 2) {
                        data.health -= n;
                        System.out.print("You escaped, but took " + n + " damage.");
                        if (data.health > 0) {
                            System.out.println(" Luckily you survived.");
                            ran = true;
                        } else {
                            System.out.println(" You were defeated while trying to escape!");
                            speak("~~ GAME OVER ~~", 1000);
                            ran = false;
                        }
                        return false;
                    } else {
                        System.out.println("Your escape attempt failed...");
                        pTurn = false;
                    }
                } else if (O == 100) {
                    System.out.println("Inventory not implemented yet...");
                } else {
                    System.out.println("Unrecognized action, please try again.");
                }
            }

            // Enemy turn
            if (data.eHealth < 0) {
                // Enemy defeated
                speak("*** You beat the enemy! It dissolved into purple smoke. ***");
                alive = false;

                // Loot drops

                /*
                 * 
                 * I need to make sure the player gets the option to pick up the loot In a run
                 * on meggiemoo32 it didn't let me pick up the item. Due to not having the item
                 * already in the inventory?
                 * 
                 */

                if (data.loot == 1) {
                    int n = lootChance.nextInt(100);
                    if (n <= data.chance) {
                        speak("The " + type + " dropped a " + inven.itemIntToString(3, data.item));
                        if (inven.invenFull() > 0) {
                            speak("You have space in your inventory. Do you want to pick it up? (1) Yes (2) No");
                            userOption();
                            if (O == 1) {

                                if (inven.invenFull() >= 0) {
                                  //  System.out.println("debug: the data.item is " + data.item);
                                  //  System.out.println("debug: the emptySlot is " + inven.emptySlot);
                                    inven.itemExist(3, data.item);
                                  //  System.out.println("debug: the emptySlot is now " + inven.emptySlot);
                                    inven.itemGet(3, data.item, 1);
                                    data.save();
                                } else {
                                    speak("Your inventory was full. You lost motivation to pick up the item.");
                                }

                            } else {
                                speak("You left the " + inven.itemIntToString(3, data.item) + " behind.");
                            }
                        }
                    }
                }

            } else {
                // Enemy attack
                speak("The enemy attacked!", 50);
                if((data.eAttack - data.armor) > 0){
                    data.health -= (data.eAttack - data.armor);
                    speak("You took " + (data.eAttack - data.armor) + " damage.", 60);
                } else {
                    speak("You took 0 damage. You're too strong!", 60);
                }
                
                if (data.health < 0) {
                    alive = false;
                    speak("You were defeated!");
                    speak("~~ GAME OVER ~~", 100);
                    return false;
                } else {
                    pTurn = true;
                }
            }
        }

        return true;

    }

}
