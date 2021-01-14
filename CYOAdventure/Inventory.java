// Jacob Schwartz - 9/28/2020
// This is the inventory system for my CYO Adventure game.

/* Original Idea

    My idea is to use a charAt scanner to read through the inventoryData String: 10.2.24.12 
    It will move through the String, appending each char as a new variable: '1' found + '0' found -> int armorID = 10.
    If a '.' is found as the charAt, it will move on to the next value
    This will allow me to create more than only 10 sets of armor, boots, etc.
    I could also use this for the storage for the count array

    *** For now I'll have 5 inventory spots, but once I change it, I'll have to remember to change everything in data as well.

*/

/* Just an Idea For a New System:

    What if I made a inventory constructor that could store the item and the number?
    A different object for each item in the inventory?
    Inventory class (data.inventoryData, data.inventoryCount) ---> individual objects (ID, count, )
    I would have to run though the inventoryData and create a new object for each item.
    Can I do that in this class, or do I need a new class? Use methods?

    The data.inventoryData shouldn't have to be a certain length.
    The scanner should just populate as many times as it is told.

*/

// I would like to add shields and speed
// MAYBE I could combine shields with the weapon slot e.g. Age of Calamity: two handed swords, one handed sword + shield, or spear

// Add item descriptions

// This is a temp import (Actually, now I'm not sure, but I think it's needed...)
import java.util.Arrays;

public class Inventory {

    // Instance variables
    Data data = CYOAdventure.data; // Get the data object from the main method
    int bag = data.bagSize;

    // Arrays that I don't need to set the size of? Idk, it works somehow :P
    // int armorID = 0, weaponID = 0, bootID = 0, slot1 = 0, slot2 = 0, slot3 = 0;
    int[] inventoryID; // = new int[bag]; // This is the int array for inventory
    String[] inventory; // = new String[bag]; // This is the String array for the inventory
    String[] details;
    int[] counts; // = new int[bag]; // This is the int array for the counts

    // Where the first empty slot is:
    int emptySlot;

    public void loadInventory() {

        int bag = data.bagSize;
        // System.out.println(bag);

        int armorID = 0, weaponID = 0, bootID = 0, slot1 = 0, slot2 = 0, slot3 = 0;
        // int[] inventoryID = new int[bag]; // This is the int array for inventory
        // String[] inventory = new String[bag]; // This is the String array for the
        // inventory
        // int[] counts = new int[bag]; // This is the int array for the counts

        inventoryID = new int[bag]; // This is the int array for inventory
        inventory = new String[bag]; // This is the String array for the inventory
        details = new String[bag]; // This is the String array for the inventory details
        counts = new int[bag]; // This is the int array for the counts

        // Inventory data scanner that uses charAt()
        inventoryID = data.inventoryScan("id");

        // Assign the count values
        counts = data.inventoryScan("count");

        armorID = inventoryID[0];
        weaponID = inventoryID[1];
        bootID = inventoryID[2];
        slot1 = inventoryID[3];
        slot2 = inventoryID[4];
        slot3 = inventoryID[5];

        // System.out.println(Arrays.toString(inventoryID));

        // inventory = {"armor", "boots", "sword", "greenSlime", "redSlime"};

        // Armor switch
        switch (armorID) {
            case 0:
                inventory[0] = "No Armor";
                details[0] = "Just your good ol' skin and bones. And clothes of course.";
                data.armor = 0;
                break;
            case 1:
                inventory[0] = "Leather Armor";
                details[0] = "Some decent padded leather armor. Not the most effective, but it looks kinda cool!";
                data.armor = 2;
                data.speed += 1;
                break;
            case 2:
                inventory[0] = "Slime Armor";
                details[0] = "It may not be comfortable, or give much protection, but... Wait, why are you wearing this again?";
                data.armor = 1;
                break;
            case 3:
                inventory[0] = "Flight Armor";
                details[0] = "This light weight suit helps you move quickly while also protecting you.";
                data.armor = 2;
                data.speed += 1;
                break;
            default:
                inventory[0] = "Defaulted";
        }

        // Armor switch
        switch (weaponID) {
            case 0:
                inventory[1] = "No Weapon";
                details[1] = "You have no weapon besides your trusty fists.";
                data.attack = 1;
                break;
            case 1:
                inventory[1] = "Wooden Sword";
                details[1] = "This is a wooden practice sword. Not too sharp, but can still do some damage.";
                data.attack = 4;
                break;
            case 2:
                inventory[1] = "Wooden Sword and Shield";
                details[1] = "This is a wooden practice sword and shield from your village. Not the best handiwork, but it'll do.";
                data.attack = 4;
                break;
            case 3:
                inventory[1] = "Smith's Work";
                details[1] = "This is a metal sword made by Smith. It's pretty average.";
                data.attack = 6;
                break;
            case 4:
                inventory[1] = "Smith's Pride";
                details[1] = "This is Smith's best work! Look how sharp it is!";
                data.attack = 8;
                break;

            default:
                inventory[1] = "Defaulted";
        }

        // Boot switch
        // Should I add speed?
        switch (bootID) {
            case 0:
                inventory[2] = "No Boots";
                details[2] = "Walking around on your bare feet kinda hurts.";
                data.speed -= 5;
                break;
            case 1:
                inventory[2] = "Leather Boots";
                details[2] = "These boots are lightweight and give a bit of protection.";
                data.speed += 10;
                data.armor += 2;
                break;

            default:
                inventory[2] = "Defaulted";
        }

        // These lines are just to get rid of some unused code errors
        if (slot1 == 1) {
        }
        if (slot2 == 1) {
        }
        if (slot3 == 1) {
        }

        // Item switch
        // This will run through each of the additional slots to see what item they
        // hold.
        for (int i = 3; i <= (inventoryID.length - 1); i++) {
            switch (inventoryID[i]) {

                case 0:
                    inventory[i] = "Empty";
                    break;
                case 1:
                    inventory[i] = "Green Slime";
                    break;
                case 2:
                    inventory[i] = "Red Slime";
                    break;
                case 3:
                    inventory[i] = "Orange Slime";
                    break;

                default:
                    inventory[i] = "Defaulted";
            }
        }

    }

    public void printInventory() {
        String[] inventory = CYOAdventure.inven.inventory;
        int[] counts = CYOAdventure.inven.counts;
        counts = data.inventoryScan("count");

        // Print the inventory:
        System.out.println("╔════════════════════════════╗");
        System.out.println("║ " + data.bagSize + "              Item      # ║");
        System.out.println("╠════════════════════════════╣");

        int l = 0;
        for (String item : inventory) {
            // System.out.println("- " + counts[l] + " " + item);
            System.out.printf("║ " + (l + 1) + " %20s %3s ║%n", item, counts[l]);
            l++;
        }
        System.out.println("╚════════════════════════════╝");
    }

    public void printDetailedInventory() {
        String[] inventory = CYOAdventure.inven.inventory;
        String[] details = CYOAdventure.inven.details;
        int[] counts = CYOAdventure.inven.counts;
        counts = data.inventoryScan("count");

        // Print the inventory:
        System.out.println(
                "╔════════════════════════════╦═══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + data.bagSize
                + "              Item      # ║                                        Details                                        ║");
        System.out.println(
                "╠════════════════════════════╬═══════════════════════════════════════════════════════════════════════════════════════╣");

        int l = 0;
        for (String item : inventory) {
            // System.out.println("- " + counts[l] + " " + item);
            System.out.printf("║ " + (l + 1) + " %20s %3s ║ %85s ║%n", item, counts[l], details[l]);
            l++;
        }
        System.out.println(
                "╚════════════════════════════╩═══════════════════════════════════════════════════════════════════════════════════════╝");
    }

    // This will run throught the inventoryID's looking for 0's and returns the
    // first slot it is found. If full it will return -1
    public int invenFull() {

        int[] inventoryID = data.inventoryScan("id"); // This is the int array for inventory

        // This runs through the inventoryIDs looking for any 0's, and returns the first
        // empty slot found
        for (int i = 0; i < inventoryID.length; i++) {
            if (inventoryID[i] == 0) {
                // System.out.println("Inventory spot available at slot " + i);
                emptySlot = i;
                return i;
            }
        }

        // System.out.println("Inventory full.");
        emptySlot = -1;
        return -1;

    }

    // This will check if an item already exists in the inventory
    public boolean itemExist(int type, int ID) {
        inventoryID = data.inventoryScan("id");
        counts = data.inventoryScan("count");
        // normal items
        if (type >= 3) {
            for (int i = 3; i < inventoryID.length; i++) {
                if (ID == inventoryID[i]) {
                    // System.out.println("That item was found. It has a count of " + counts[i]);
                    emptySlot = i;
                    return true;
                }
            }
        }
        // equipment items
        else if (type < 3) {
            for (int i = 0; i < 3; i++) {
                if (ID == inventoryID[i]) {
                    // System.out.println("That equipment item was found. It has a count of " +
                    // counts[i]);
                    emptySlot = i;
                    return true;
                }
            }
        }

        // System.out.println("None of that item was found");
        return false;
    }

    // Types: 0=armor, 1=weapon, 2=boots, 3 and up = other items
    public void itemGet(int type, int ID, int count) {

        int index = 0; // The index that will be replaced
        String builder = "";

        // Check the type to put it in the right spot
        if (type == 0) {
            index = 0;
        } else if (type == 1) {
            index = 1;
        } else if (type == 2) {
            index = 2;
        } else {
            index = emptySlot;
        }

        inventoryID[index] = ID; // Setting the right index to the new item ID
        // Changing the inventoryID array to strings so it can be saved
        for (int i = 0; i < inventoryID.length; i++) {
            if (i == 0) {
                builder = builder + String.valueOf(inventoryID[i]);
            } else {
                builder = builder + "." + String.valueOf(inventoryID[i]);
            }
        }

        // System.out.println(" New inventoryData:" + builder);
        // Set the inventoryData variable of data to the new value
        data.inventoryData = builder;

        builder = "";
        counts[index] += count; // Setting the right index to the new item count
        // Changing the inventoryID array to strings so it can be saved
        for (int i = 0; i < inventoryID.length; i++) {
            if (i == 0) {
                builder = builder + String.valueOf(counts[i]);
            } else {
                builder = builder + "." + String.valueOf(counts[i]);
            }
        }

        // System.out.println(" New inventoryCount:" + builder);
        // Set the inventoryCount variable of data to the new value
        data.inventoryCount = builder;

    }

    // This will use and remove items from the inventory
    public void itemUse(int type, int ID, int count) {
        int index = 0; // The index that will be used
        String builder = "";

        // Check the type to put it in the right spot
        if (type == 0) {
            index = 0;
        } else if (type == 1) {
            index = 1;
        } else if (type == 2) {
            index = 2;
        } else {
            index = emptySlot;
        }

        counts[index] -= count; // removing count from the right index
        System.out.println("Used " + count + " " + itemIntToString(type, ID));

        // What the usage does
        if (type < 3) {
            System.out.println("You used a piece of equipment?");
        } else {
            switch (ID) {

                case 0:
                    System.out.println("You used nothing to do nothing. Good job?");
                    break;
                case 1:
                    // "Green Slime";
                    System.out.println(
                            "Green slime is slightly beneficial when consumed, although it tastes disgusting! Health +2");
                    data.health += 2;
                    break;
                case 2:
                    // "Red Slime";
                    System.out.println(
                            "Red slime is slightly beneficial when consumed, although it tastes disgusting! Health +2");
                    data.health += 2;
                    break;
                case 3:
                    // "Orange Slime";
                    System.out.println("Orange slime is highly acidic, so you burned yourself! You took 2 damage!");
                    data.health -= 2;
                    break;

                default:
                    // "Defaulted";
                    System.out.println("Defaulted");
            }
        }

        if (counts[index] <= 0) {
            inventoryID[index] = 0;
            counts[index] = 0;
        }

        // Changing the inventoryID array to strings so it can be saved
        for (int i = 0; i < inventoryID.length; i++) {
            if (i == 0) {
                builder = builder + String.valueOf(inventoryID[i]);
            } else {
                builder = builder + "." + String.valueOf(inventoryID[i]);
            }
        }

        System.out.println(" New inventoryData:" + builder);
        // Set the inventoryData variable of data to the new value
        data.inventoryData = builder;

        builder = "";
        // Changing the inventoryID array to strings so it can be saved
        for (int i = 0; i < inventoryID.length; i++) {
            if (i == 0) {
                builder = builder + String.valueOf(counts[i]);
            } else {
                builder = builder + "." + String.valueOf(counts[i]);
            }
        }

        System.out.println(" New inventoryCount:" + builder);
        // Set the inventoryCount variable of data to the new value
        data.inventoryCount = builder;

        data.save();
    }

    // This method returns a string of what the item int is.
    public String itemIntToString(int type, int item) {
        String loot = "";
        if (type < 3) {
            if (type == 0) {
                switch (item) {
                    case 0:
                        loot = "No Armor";
                        break;
                    case 1:
                        loot = "Leather Armor";
                        break;

                    default:
                        loot = "Defaulted";
                }
            } else if (type == 1) {
                switch (item) {
                    case 0:
                        loot = "No Weapon";
                        break;
                    case 1:
                        loot = "Wooden Sword";
                        break;

                    default:
                        loot = "Defaulted";
                }
            } else if (type == 2) {
                switch (item) {
                    case 0:
                        loot = "No Boots";
                        break;
                    case 1:
                        loot = "Leather Boots";
                        break;

                    default:
                        loot = "Defaulted";
                }
            }

        } else {
            switch (item) {

                case 0:
                    loot = "Empty";
                    break;
                case 1:
                    loot = "Green Slime";
                    break;
                case 2:
                    loot = "Red Slime";
                    break;
                case 3:
                    loot = "Orange Slime";
                    break;

                default:
                    loot = "Defaulted";
            }
        }

        return loot;

    }

    public void openInventory() {

        String[] inventory = CYOAdventure.inven.inventory;
        int[] counts = CYOAdventure.inven.counts;
        counts = data.inventoryScan("count");

        printInventory();
        System.out.println("What do you want to do? (1) Use item (2) Show details (3) Exit inventory");
        CYOAdventure.userOption();
        if (CYOAdventure.O == 1) {
            System.out.println("What item do you want to use?");
            CYOAdventure.userOption();
            if (CYOAdventure.O == 1) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(0, inventoryID[0], 1);
            } else if (CYOAdventure.O == 2) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(1, inventoryID[1], 1);
            } else if (CYOAdventure.O == 3) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(2, inventoryID[2], 1);
            } else if (CYOAdventure.O == 4) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(3, inventoryID[3], 1);
            } else if (CYOAdventure.O == 5) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(3, inventoryID[4], 1);
            } else if (CYOAdventure.O == 6) {
                System.out.println("How much do you want to use? [not implemented - defaults to 1]");
                itemUse(3, inventoryID[5], 1);
            }
        } else if (CYOAdventure.O == 2) {
            printDetailedInventory();
        } else if (CYOAdventure.O == 3) {
            return;
        }
    }

}
