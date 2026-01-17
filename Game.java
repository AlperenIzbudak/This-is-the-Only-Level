// Muttalip Alperen Izbudak
// 2023400078

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Game class is the main controller for the game.
 * It manages the game loop, stage transitions, player input, and game state.
 */
public class Game {

    Random random = new Random(); // For generating random obstacle colors
    public int stageIndex;        // Current stage index
    public int deathNumber;       // Number of player deaths
    public double gameTime;       // Current game time
    public boolean resetGame;     // Flag to reset the entire game
    public boolean isGameOver;    // Flag to indicate game completion
    public boolean isRightOrLeft = true; // Direction the player is facing (true = right, false = left)
    public int howManyTimesButtonPressed = 0; // Counter for button presses
    public boolean isQuitPressed = false; //whether player wants to quit the game

    // Initial player spawn position
    public double[] initialPlayerPositions = {130, 465};

    // Collection of all game stages
    public ArrayList<Stage> stages = new ArrayList<Stage>();

    /**
     * Constructor that initializes the game with a list of stages
     *
     * @param stages List of Stage objects representing the game levels
     */
    public Game(ArrayList<Stage> stages){
        this.stages = stages;
    }

    /**
     * @return The current stage index
     */
    public int getStageIndex() {
        return stageIndex;
    }

    /**
     * Processes player input based on the current stage's control scheme
     *
     * @param map         The map of the current stage
     * @param stage       The current stage
     * @param stageIndex  Index of the current stage
     */
    public void handleInput(Map map, Stage stage, int stageIndex){
        // Handle mouse control for stage 5
        map.movePlayerModified(stageIndex);

        // Apply gravity to the player
        map.handleGravity();

        // Handle auto-jumping for stage 3
        if (stageIndex == 2){
            map.jumpConstantly();
        }

        // Handle keyboard input based on the current stage's control scheme
        if (StdDraw.isKeyPressed(stage.getKeyCodes()[0])){
            map.movePlayer('R', stageIndex);
        }
        if (StdDraw.isKeyPressed(stage.getKeyCodes()[1])){
            map.movePlayer('L', stageIndex);
        }
        if (StdDraw.isKeyPressed(stage.getKeyCodes()[2])){
            map.movePlayer('U', stageIndex);
        }
    }

    /**
     * Calculates and formats the elapsed game time
     *
     * @param startingTime The time when the game started
     * @param stageIndex   The current stage index
     * @return A formatted string showing minutes:seconds:milliseconds
     */
    public String getTime(long startingTime, int stageIndex){
        long currentTime = System.currentTimeMillis();
        // Subtract transition delays (2 seconds per stage transition)
        long eclipsedTime = currentTime - startingTime - 2000 * stageIndex;

        int minutes = (int) eclipsedTime / 60000;
        int seconds = (int) (eclipsedTime % 60000) / 1000;
        int milliseconds = (int) (eclipsedTime % 1000) / 10;

        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }

    /**
     * Checks if the reset button is pressed and sets the resetGame flag if it is
     */
    public void isResetButtonPressed(){
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        if (x >= 320 && x <= 480 && y >= 5 && y <= 35 && StdDraw.isMousePressed()){
            StdDraw.pause(200);
            resetGame = true;
        }
    }

    /**
     * Displays a transition banner between stages
     *
     * @param stageIndex Current stage index
     */
    public void stageOverBanner(int stageIndex){
        if (stageIndex != 0) {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(400, 250, 400, 75);
            StdDraw.setPenColor(StdDraw.WHITE);

            StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
            StdDraw.text(400, 275, "You passed the stage");
            StdDraw.text(400, 225, "But is the level over?!");
            StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 16));

            StdDraw.show();
            StdDraw.pause(2000);
        }
    }

    /**
     * Displays the game completion banner with stats
     *
     * @param isGameOver   Flag indicating if the game is over
     * @param deathNumber  Number of player deaths
     * @param time         Formatted string showing completion time
     */
    public void gameOverBanner(boolean isGameOver, int deathNumber, String time) {
        if (isGameOver) {
            StdDraw.pause(1000);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(400, 250, 400, 75);
            StdDraw.setPenColor(StdDraw.WHITE);

            StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
            StdDraw.text(400, 275, "CONGRATULATIONS YOU FINISHED THE LEVEL");
            StdDraw.text(400, 225, "PRESS A TO PLAY AGAIN");
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));
            StdDraw.text(400, 200, String.format("You finished with %d deaths in ", deathNumber) + time);

            StdDraw.setFont(new Font("Arial", Font.PLAIN, 16));

            StdDraw.show();
        }
    }

    /**
     * Resets the entire game and starts it from the beginning
     *
     * @param isGameReset Flag indicating if the game should be reset
     */
    public void resetGameTotally(boolean isGameReset){
        if (isGameReset) {
            deathNumber = 0;
            resetGame = false;
            stageIndex = 0;
            isRightOrLeft = true;
            howManyTimesButtonPressed = 0;

            play(); // Restart the game
        }
    }

    /**
     * Handles the game state after completion
     * Waits for the player to press 'A' to restart
     *
     * @param isGameOver    Flag indicating if the game is over
     * @param startingTime  The time when the game started
     * @param currentIndex  Current stage index
     */
    public void resetGameAfterWin(boolean isGameOver, long startingTime, int currentIndex){
        if (isGameOver){
            StdDraw.clear();
            gameOverBanner(isGameOver, deathNumber, getTime(startingTime, currentIndex));

            while(true) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                    deathNumber = 0;
                    isGameOver = false;
                    stageIndex = 0;
                    isRightOrLeft = true;
                    howManyTimesButtonPressed = 0;
                    play(); // Restart the game
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_Q)){
                    isQuitPressed = true;
                    break;

                }
            }
        }
    }

    /**
     * The main game loop that runs the entire game
     * Handles stage transitions, game state, and renders the game
     */
    public void play(){
        if (isQuitPressed){//if player presses Q after the game finished, method returns nothing.
            return;
        }
        long startingTime = System.currentTimeMillis(); // Start the timer

        // Loop through all stages
        for (int i = 0; i < stages.size(); i++) {
            stageIndex = i;

            // Check if game should be reset
            if (resetGame){
                stageIndex = 0;
                break;
            }

            // Show stage transition banner
            stageOverBanner(stageIndex);

            // Generate random color for obstacles
            int r = random.nextInt(1, 255);
            int g = random.nextInt(1, 255);
            int b = random.nextInt(1, 255);
            Color currentObstacleColor = new Color(r, g, b);

            // Set up current stage objects
            stageIndex = i;
            Stage currentStage = stages.get(stageIndex);
            Player player = new Player(initialPlayerPositions[0], initialPlayerPositions[1]);
            Map map = new Map(currentStage, player, this);

            map.resetDoorStatus();

            System.out.println("current stage is: " + (stageIndex + 1));

            player.respawn(initialPlayerPositions);

            boolean isHelpRequested = false;

            // Main stage loop
            while (!map.changeStage(stageIndex)) {
                StdDraw.clear();

                // Draw the game area
                gameTime = getStageIndex();
                currentStage.printUserScreen(deathNumber, isHelpRequested, getTime(startingTime, stageIndex), stageIndex);

                // Check for help button pressed
                if (map.isHelpRequested()){
                    isHelpRequested = true;
                }

                // Check for reset button pressed
                isResetButtonPressed();
                if (resetGame){
                    break;
                }

                // Update and draw game elements
                map.isDoorOpened(stageIndex);
                map.updateDoorForSliding();
                map.printDoor();
                map.printSpikes();
                map.printObstacles(currentObstacleColor);
                map.printButtons();

                // Draw player
                player.draw(isRightOrLeft);

                // Draw pipes
                map.printPipes();

                // Check for restart button pressed
                map.restartStage();

                // Handle player input
                handleInput(map, currentStage, stageIndex);

                // Check if player hit spikes
                if (map.checkSpikes(player.getX(), player.getY(), map.getSpikes())){
                    player.respawn(new double[]{130, 465});
                }

                // Update display
                StdDraw.show();
                StdDraw.pause(5); // 5ms delay between frames
            }
        }

        // Handle game reset or completion
        resetGameTotally(resetGame);
        resetGameAfterWin(isGameOver, startingTime, stageIndex);
    }
}