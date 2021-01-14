// Jacob Schwartz - 11/27/2020
// This is the demo dungeon rebranded as the slime cave

import java.util.Scanner;

public class SlimeCave {

    static String input;
    static Scanner inputReader = new Scanner(System.in);

    static int O = 0;

    static Data data = new Data();
    static Inventory inven = new Inventory();

    static int charSpeed = 50;
    static boolean speedOn = false;
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

    // This is the default speak method (waits 50 ms between characters)
    public static void speak(String dialog) throws InterruptedException {
        speedOn = true;
        charSpeed = 50;

        for (int i = 0; i < dialog.length(); i++) {
            System.out.print(dialog.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();

        speedOn = false;
        charSpeed = 50;
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

    public static void demoDungeon(int startRoom) throws InterruptedException {
        int room = startRoom;
        boolean exit = false;
        boolean l1 = false;
        boolean l2 = false;
        boolean e1 = false;
        boolean e2 = false;

        Combat combat = new Combat();

        speak("You walk into the damp cave. This place is probably filled with slime.");
        while (!exit) {

            // Room 0
            if (room == 0) {
                speak("You're in the entrance hall. A split passage way lays before you.");
                if (l1 && !l2) {
                    System.out.println(">\tDo you want to head (1) right (2) or exit the dungeon?");
                    userOption();
                    if (O == 1) {
                        room = 2;
                    } else if (O == 2) {
                        exit = true;
                    }

                } else if (l2 && !l1) {
                    System.out.println(">\tDo you want to head (1) left (2) or exit the dungeon?");
                    userOption();
                    if (O == 1) {
                        room = 1;
                    } else if (O == 2) {
                        exit = true;
                    }
                } else if (l1 && l2) {
                    speak("A large section of the wall has opened, revealing a staircase deeper into the dungeon!");
                    System.out.println(">\tDo you want to head (1) down (2) or exit the dungeon?");
                    userOption();
                    if (O == 1) {
                        room = 3;
                    } else if (O == 2) {
                        exit = true;
                    }
                } else {
                    System.out.println(">\tDo you want to head (1) left (2) right (3) or exit the dungeon?");
                    userOption();
                    if (O == 1) {
                        room = 1;
                    } else if (O == 2) {
                        room = 2;
                    } else if (O == 3) {
                        exit = true;
                    }
                }

            }

            // Room 1
            if (room == 1) {
                boolean goOut = false;
                if (e1) {
                    speak("You already beat the slime that was in this room, but the lever is still here. Do you want to pull it? (1) Yes (2) No");
                }

                if (!e1) {
                    speak("This room is pretty rank, with a small Grey Slime in the center, looking gelatinous.");
                    System.out.println(">\t Engage combat? (1) Yes (2) Retreat");
                    userOption();
                    if (O == 1) {
                        if (combat.enemy("Grey_Slime")) {
                            e1 = true;
                            speak("Now that the slime is gone, you notice a lever on the wall.");
                            speak("You're not sure what it'll do if you pull it...");
                            System.out.println(">\t Give it a try? (1) Yes (2) No");
                        } else {
                            if (combat.ran) {
                                speak("You ran back to the entrance hall.");
                                room = 0;
                                continue;
                            } else {
                                // Game over
                                exit = true;
                            }

                        }
                    } else if (O == 2) {
                        room = 0;
                        speak("You returned to the entrance hall.");
                    }
                }
                userOption();
                if (O == 1) {

                    speak("You walked over and pulled the lever. \"HRRRG!\" It was pretty stiff, but you pulled it down and heard a distant click!");
                    l1 = true;
                    System.out.println(">\tDo you want to (1) return to the entrance hall (2) undo the lever?");

                    while (!goOut) {
                        userOption();
                        if (O == 1) {
                            room = 0;
                            speak("You returned to the entrance hall.");
                            goOut = true;
                        } else if (O == 2) {
                            speak("You walked over and undid the lever. \"HRRRG!\" It was still pretty stiff, but you pulled it back up.");
                            l1 = false;
                            System.out.println(
                                    ">\tDo you want to (1) return to the entrance hall (2) pull the lever again?");
                            userOption();
                            if (O == 1) {
                                room = 0;
                                speak("You returned to the entrance hall.");
                                goOut = true;
                            } else if (O == 2) {
                                speak("You walked over and pulled the lever again. \"HRRRG!\" It was still pretty stiff, but you pulled it back down and heard a distant click!.");
                                l1 = true;
                                System.out.println(
                                        ">\tDo you want to (1) return to the entrance hall (2) undo the lever?");
                            }
                        }
                    }
                } else if (O == 2) {
                    room = 0;
                    speak("You left the lever untouched and returned to the entrance hall.");
                }
                if (goOut && l1) {
                    speak("As you left the room, a door slammed behind you. Guess you won't be going back in there!");
                }
            }

            // Room 2
            if (room == 2) {
                boolean goOut = false;
                if (e2) {
                    speak("You already beat the slime that was in this room, but the lever is still here.");
                    System.out.println(">\tDo you want to pull it? (1) Yes (2) No");
                }

                if (!e2) {
                    speak("This room is pretty rank, with a small Grey Slime in the center, looking gelatinous.");
                    System.out.println(">\tEngage combat? (1) Yes (2) Retreat");
                    userOption();
                    if (O == 1) {
                        if (combat.enemy("Grey_Slime")) {
                            e2 = true;
                            speak("Now that the slime is gone, you notice a lever on the wall.");
                            speak("You're not sure what it'll do if you pull it.");
                            System.out.println("Give it a try? (1) Yes (2) No");
                        } else {
                            // Game over
                            exit = true;
                        }
                    } else if (O == 2) {
                        room = 0;
                        speak("You returned to the entrance hall.");
                    }
                }
                userOption();
                if (O == 1) {
                    speak("You walked over and pulled the lever. \"HRRRG!\" It was pretty stiff, but you pulled it down and heard a distant click!");
                    l2 = true;
                    System.out.println("What do you want to do? (1) Return to the entrance hall (2) Undo the lever");

                    while (!goOut) {
                        userOption();
                        if (O == 1) {
                            room = 0;
                            speak("You returned to the entrance hall.");
                            goOut = true;
                        } else if (O == 2) {
                            speak("You walked over and undid the lever. \"HRRRG!\" It was still pretty stiff, but you pulled it back up.");
                            l2 = false;
                            System.out.println(
                                    "What do you want to do? (1) Return to the entrance hall (2) Pull the lever again");
                            userOption();
                            if (O == 1) {
                                room = 0;
                                speak("You returned to the entrance hall.");
                                goOut = true;
                            } else if (O == 2) {
                                speak("You walked over and pulled the lever again. \"HRRRG!\" It was still pretty stiff, but you pulled it back down and heard a distant click!.");
                                l2 = true;
                                System.out.println(
                                        "What do you want to do? (1) Return to the entrance hall (2) Undo the lever");
                            }
                        }
                    }
                } else if (O == 2) {
                    room = 0;
                    speak("You left the lever untouched and returned to the entrance hall.");
                }
                if (goOut && l2) {
                    speak("As you left the room, a door slammed behind you. Guess you won't be going back in there!");
                }
            }

            // Room 3
            if (room == 3) {
                boolean goOut = false;
                boolean mural = true;

                speak("Walking into this large room, the air is so moist you can almost feel a film of slime on your skin...");
                if (mural) {
                    speak("Looking around, you see a large mural directly ahead of you, and two doorways on either side of the room.");
                } else {
                    speak("Looking around, you see the dark, slimy hole directly ahead of you, and two doorways on either side of the room.");
                }

                while (!goOut) {
                    if (mural) {
                        System.out.println(
                                ">\tWhat do you want to do? (1) Go left (2) Go right (3) Go back (4) Look at the mural");
                    } else {
                        System.out.println(">\tWhat do you want to do? (1) Go left (2) Go right (3) Go back");
                    }

                    userOption();
                    if (O == 1) {
                        room = 4;
                        goOut = true;
                    } else if (O == 2) {
                        room = 5;
                        goOut = true;
                    } else if (O == 3) {
                        room = 0;
                        goOut = true;
                    } else if (O == 4 && mural == true) {
                        speak("The mural is large and most of the details are faded, but you can still make out the general shapes:");
                        // ███ Ö◙ ⌂ ♦∙••
                        System.out.println("╒════════♦════════╕");
                        System.out.println("♦                 ♦");
                        System.out.println("│    ▲ + ■ → ♦    │");
                        System.out.println("♦                 ♦");
                        System.out.println("╘════════♦════════╛");
                        speak("Hmm. The material of the wall here seems like a different material than the rest of the wall.");
                        System.out.println("Hint: you can press \"i\" during any decision to open your inventory... ");

                    } else if (O == 100) {
                        if (inven.itemExist(3, 1) && inven.itemExist(3, 2)) {
                            speak("You combined the green and red slimes to make a blob of orange slime. You put it on the mural, and the wall breaks down.");
                            mural = false;
                            speak("In front of you is a dark tunnel dripping with slime. A chill runs down your spine.");
                            System.out.println(">\tDo you enter? (1) Yes (2) No");
                            userOption();
                            if (O == 1) {
                                room = 6;
                                goOut = true;
                            } else if (O == 2) {
                                speak("Maybe now isn't the best time to go in there... You backup a few steps.");
                            }
                        } else {
                            speak("You seem to be missing something... Maybe try exploring more.");
                        }

                    }
                }

            }

            // Room 4
            if (room == 4) {
                boolean goOut = false;
                speak("This room is small and dark, but you can make out the shape of a triangle protruding from the wall.");
                System.out.println("Looking at it closer, it looks like a button.");
                speak(">\tDo you want to press it? (1) Yes (2) No");
                userOption();
                if (O == 1) {
                    speak("The button makes a sliding noise as you press it, and you feel a draft of air from above.");
                    speak("Looking up, you see the source of the air. A hole has opened up in the ceiling, and it looks like something is");
                    speak("... falling?", 90);
                    speak("You realize what it is a moment too late: A red slime!");
                    while (!goOut) {
                        if (combat.enemy("Red_Slime")) {
                            speak("You beat the slime, but it looks like the button could be pressed again.");
                            System.out.println(">\tWhat do you want to do? (1) Press the button again (2) Leave");
                            userOption();
                            if (O == 1) {
                                speak("Another red slime dropped from the hole when you pressed the button!");
                            } else if (O == 2) {
                                speak("You left the room.");
                                room = 3;
                                goOut = true;
                            }
                        } else {
                            // Game Over
                            exit = true;
                        }
                    }

                } else if (O == 2) {
                    speak("Deciding against pressing the button, you leave the room.");
                    room = 3;
                    goOut = true;
                }
            }

            // Room 5
            if (room == 5) {
                boolean goOut = false;
                speak("The ceiling in this room is very short, and there are dark holes in the floor.\nYou can make out the shape of a square protruding from the wall.");
                speak("Looking at it closer, it looks like a button.");
                System.out.println(">\tDo you want to press it? (1) Yes (2) No");
                userOption();
                if (O == 1) {
                    speak("The button makes a sliding noise as you press it, and you look down at the holes in the floor.");
                    speak("Green slime is emerging from the holes in the floor and is rolling across the floor to join together!");
                    speak("Now in front of you is a green slime!");
                    while (!goOut) {
                        if (combat.enemy("Green_Slime")) {
                            speak("You beat the slime, but it looks like the button could be pressed again.");
                            System.out.println(">\tWhat do you want to do? (1) Press the button again (2) Leave");
                            userOption();
                            if (O == 1) {
                                speak("Another green slime dropped emerged from the holes in the ground when you pressed the button!");
                            } else if (O == 2) {
                                speak("You left the room.");
                                room = 3;
                                goOut = true;
                            }
                        } else {
                            // Game Over
                            exit = true;
                        }
                    }

                } else if (O == 2) {
                    speak("Deciding against pressing the button, you leave the room.");
                    room = 3;
                    goOut = true;
                }
            }

            // Room 6
            if (room == 6) {
                // Boss Fight
                speak("You walk into the final room, and the door slams behind you.\nLooking towards the center of the room, you see two normal slimes: a red and a green.");
                speak("They suddenly lunge at each other and merge, transforming into a massive orange slime!", 40);
                speak("This new creature shifts around, looking for something to destroy with its new strength. You have to face it!");
                if (combat.enemy("Boss_Slime")) {
                    speak("As the orange goop disipates from the room, a golden key is left behind. Where could this key unlock?");
                    System.out.println(">\tThis is the end of the Demo Dungeon. Congrats!");
                    System.out.println("═════════════════════════════════════════════════════════════════════");
                    return;
                } else {
                    // Game over
                    exit = true;
                }
            }

        } // This is the end of the while loop
    } // This is the end of the dungeon method
}
