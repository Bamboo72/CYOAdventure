// Jacob Schwartz - 11/27/2020
// This is my attempt to speed up text being displayed with the speak() method by pressing enter.
// It's not how I would like it to be, but I'm trying it out.

import java.io.IOException;

public class TextSpeederThread implements Runnable {

    public void run() {

        while (CYOAdventure.listen) {
            if (SlimeCave.speedOn) {
                try {
                    System.in.read(); // This is reading the input from the keyboard
                    System.out.print("\010"); // This comes from https: stackoverflow.com/questions/10819469/hide-input-on-command-line
                   
                    SlimeCave.charSpeed = 10;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
