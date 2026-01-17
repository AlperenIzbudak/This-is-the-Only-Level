// Muttalip Alperen Izbudak
// 2023400078

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The Main class serves as the entry point for the game "This is the Only Level".
 * It initializes the game window, creates all five stages with their unique properties,
 * and starts the game loop.
 */
public class MuttalipAlperenIzbudak {
    public static void main(String[] args){
        // Set up the drawing canvas with dimensions 800x600
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setTitle("This is the Only Level");
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);
        StdDraw.enableDoubleBuffering();


        // Parameters: gravity, velocityX, velocityY, stageNumber, rightKey, leftKey, [upKey], clue, help

        // Stage 1: Standard controls with normal arrow key mappings
        Stage s1 = new Stage(-0.05, 2, 3.5, 0, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Arrow keys are required",
                "Arrow keys move player, press button and enter the second pipe");

        // Stage 2: Reversed left/right controls to confuse the player
        Stage s2 = new Stage(-0.05, 2, 3.5, 1, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,
                "Not always straight forward",
                "Right and left buttons reversed");

        // Stage 3: Auto-jumping mechanics - player jumps constantly without pressing up
        Stage s3 = new Stage(-0.2, 2, 7.0, 2, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT,
                "A bit bouncy here",
                "You jump constantly");

        // Stage 4: Multi-press requirement - button must be pressed 5 times to open door
        Stage s4 = new Stage(-0.05, 2, 3.5, 3, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Never gonna give you up",
                "Press button 5 times ");

        // Stage 5: Mouse control instead of keyboard
        Stage s5 = new Stage(-0.05, 2, 3.5, 4, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP,
                "Your keyboard is broken",
                "Use your mouse instead");

        // Store all stages in an ArrayList for easy sequential access
        ArrayList<Stage> stages = new ArrayList<Stage>();
        stages.add(s1);
        stages.add(s2);
        stages.add(s3);
        stages.add(s4);
        stages.add(s5);

        // Create game object with all stages and start the game loop
        Game game = new Game(stages);
        game.play();


        //if player quits the game play method returns nothing and simple text "You have quit the game!" appears.
        StdDraw.clear();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(400,300,"You have quit the game!");
        StdDraw.show();
    }
}