
// Muttalip Alperen Izbudak
// 2023400078

import java.awt.*;

/**
 * The Map class represents the game environment including obstacles, spikes, buttons, and doors.
 * It handles the physical interactions between the player and the environment.
 */
public class Map {

    private Player player; // Reference to the player object
    private Stage stage;   // Reference to the current stage
    private Game game;     // Reference to the game controller

    /**
     * Constructor for creating a map with references to the current stage, player, and game
     *
     * @param stage  The current stage
     * @param player The player object
     * @param game   The game controller
     */
    public Map(Stage stage, Player player, Game game){
        this.stage = stage;
        this.player = player;
        this.game = game;
    }

    // State variables for door and button mechanics
    public boolean isDoorOpenedBoolean = false; // Whether the door is open
    public boolean isDoorSliding = false;       // Whether the door is currently sliding open
    private boolean isPlayerOnButton = false;   // Whether the player is on the button
    private boolean isPlayerInsideButton = false; // Whether the player is visually inside the button area

    /**
     * Resets all door and button statuses to their initial state
     */
    public void resetDoorStatus(){
        isDoorSliding = false;
        isDoorOpenedBoolean = false;
        isPlayerInsideButton = false;
        isPlayerOnButton = false;
        game.howManyTimesButtonPressed = 0;
    }

    // Map element coordinates

    /**
     * Array of spike coordinates [x1, y1, x2, y2] where (x1,y1) is the bottom-left and (x2,y2) is the top-right
     */
    private int[][] spikes = {
            new int[]{30, 333, 50, 423}, new int[]{121, 150, 207, 170},
            new int[]{441, 150, 557, 170}, new int[]{591, 180, 621, 200},
            new int[]{750, 301, 770, 419}, new int[]{680, 490, 710, 510},
            new int[]{401, 550, 521, 570}};

    /**
     * Array of obstacle coordinates [x1, y1, x2, y2] where (x1,y1) is the bottom-left and (x2,y2) is the top-right
     */
    private int[][] obstacles = {
            new int[]{0, 120, 120, 270}, new int[]{0, 270, 168, 330},
            new int[]{0, 330, 30, 480}, new int[]{0, 480, 180, 600},
            new int[]{180, 570, 680, 600}, new int[]{270, 540, 300, 570},
            new int[]{590, 540, 620, 570}, new int[]{680, 510, 800, 600},
            new int[]{710, 450, 800, 510}, new int[]{740, 420, 800, 450},
            new int[]{770, 300, 800, 420}, new int[]{680, 240, 800, 300},
            new int[]{680, 300, 710, 330}, new int[]{770, 180, 800, 240},
            new int[]{0, 120, 800, 150}, new int[]{560, 150, 800, 180},
            new int[]{530, 180, 590, 210}, new int[]{530, 210, 560, 240},
            new int[]{320, 150, 440, 210}, new int[]{350, 210, 440, 270},
            new int[]{220, 270, 310, 300}, new int[]{360, 360, 480, 390},
            new int[]{530, 310, 590, 340}, new int[]{560, 400, 620, 430}};

    /**
     * Coordinates for the button's floor/base
     */
    private int[] buttonFloor = new int[]{400, 390, 470, 400};

    /**
     * Coordinates for the interactive button
     */
    private int[] button = new int[]{400, 390, 470, 410};

    /**
     * Coordinates for the entrance pipe
     */
    private int[][] startPipe = {new int[]{115, 450, 145, 480},
            new int[]{110, 430, 150, 450}};

    /**
     * Coordinates for the exit pipe
     */
    private int[][] exitPipe = {new int[]{720, 175, 740, 215},
            new int[]{740, 180, 770, 210}};

    /**
     * Coordinates for the door that blocks access to the exit pipe
     */
    private int[] door = new int[]{685, 180, 700, 240};

    /**
     * @return The array of spike coordinates
     */
    public int[][] getSpikes() {
        return spikes;
    }

    /**
     * Draws all spike obstacles on the screen with appropriate rotations
     */
    public void printSpikes() {
        String imageName;

        for (int i = 0; i < 7; i++) {
            int[] spike = spikes[i];
            // Different rotations based on spike position
            if (i == 0) {
                StdDraw.picture((spike[0] + spike[2]) / 2.0, (spike[1] + spike[3]) / 2.0, "misc/Spikes.png", spike[3] - spike[1], spike[2] - spike[0], 90);
            } else if (i == 1 || i == 2 || i == 3) {
                StdDraw.picture((spike[0] + spike[2]) / 2.0, (spike[1] + spike[3]) / 2.0, "misc/Spikes.png", spike[2] - spike[0], spike[3] - spike[1], 180);
            } else if (i == 4) {
                StdDraw.picture((spike[0] + spike[2]) / 2.0, (spike[1] + spike[3]) / 2.0, "misc/Spikes.png", spike[3] - spike[1], spike[2] - spike[0], 270);
            } else {
                StdDraw.picture((spike[0] + spike[2]) / 2.0, (spike[1] + spike[3]) / 2.0, "misc/Spikes.png", spike[2] - spike[0], spike[3] - spike[1]);
            }
        }
    }

    /**
     * Draws all obstacles with the specified color
     *
     * @param color The color to draw the obstacles with
     */
    public void printObstacles(Color color) {
        for (int[] obstacle : obstacles) {
            StdDraw.setPenColor(color);
            StdDraw.filledRectangle((obstacle[0] + obstacle[2]) / 2.0, (obstacle[1] + obstacle[3]) / 2.0, (obstacle[2] - obstacle[0]) / 2.0, (obstacle[3] - obstacle[1]) / 2.0);
        }
    }

    /**
     * Draws the entrance and exit pipes
     */
    public void printPipes() {
        StdDraw.setPenColor(StdDraw.ORANGE);
        // Draw exit pipe
        for (int[] pipe : exitPipe) {
            StdDraw.filledRectangle((pipe[0] + pipe[2]) / 2.0, (pipe[1] + pipe[3]) / 2.0, (pipe[2] - pipe[0]) / 2.0, (pipe[3] - pipe[1]) / 2.0);
        }
        // Draw entrance pipe
        for (int[] pipe : startPipe) {
            StdDraw.filledRectangle((pipe[0] + pipe[2]) / 2.0, (pipe[1] + pipe[3]) / 2.0, (pipe[2] - pipe[0]) / 2.0, (pipe[3] - pipe[1]) / 2.0);
        }
    }

    /**
     * Draws the button and its floor/base
     * The button is only visible when the player is not on it
     */
    public void printButtons() {
        StdDraw.setPenColor(StdDraw.RED);
        if (!isPlayerInsideButton) {
            StdDraw.filledRectangle((button[0] + button[2]) / 2.0, (button[1] + button[3]) / 2.0, (button[2] - button[0]) / 2.0, (button[3] - button[1]) / 2.0);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle((buttonFloor[0] + buttonFloor[2]) / 2.0, (buttonFloor[1] + buttonFloor[3]) / 2.0, (buttonFloor[2] - buttonFloor[0]) / 2.0, (buttonFloor[3] - buttonFloor[1]) / 2.0);
    }

    /**
     * Draws the door that blocks access to the exit pipe
     */
    public void printDoor() {
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.filledRectangle((door[0] + door[2]) / 2.0, (door[1] + door[3]) / 2.0, (door[2] - door[0]) / 2.0, (door[3] - door[1]) / 2.0);
    }

    /**
     * Updates the door position for the sliding animation
     * If the door is in sliding state, it reduces its height by 1 pixel per frame
     */
    public void updateDoorForSliding(){
        if (isDoorSliding && door[3] > 180){
            door[3] -= 1;
            door[1] -= 1;
        }
    }

    /**
     * Makes the player jump constantly
     * Used specifically for stage 3 where the player jumps automatically
     */
    public void jumpConstantly(){
        if (isOnGround()){
            player.velocityY = stage.getVelocityY();
        }
    }

    /**
     * Moves the player based on input direction and stage
     *
     * @param direction   Character representing movement direction ('U' for up, 'L' for left, 'R' for right)
     * @param stageIndex  Current stage index (controls special behavior)
     */
    public void movePlayer(char direction, int stageIndex){
        if (stageIndex != 4) { // Normal movement controls (not mouse control)
            if (direction == 'U' && isOnGround() && stageIndex != 2) {
                player.velocityY = stage.getVelocityY();
            }
            if (direction == 'L') {
                if (!checkCollision(player.getX() - stage.velocityX, player.getY(), obstacles, door)) {
                    player.setX(player.getX() - stage.velocityX);
                    game.isRightOrLeft = false;
                    if (stageIndex == 1) {
                        // In stage 2 (index 1), the direction is reversed
                        game.isRightOrLeft = true;
                    }
                }
            }
            if (direction == 'R') {
                if (!checkCollision(player.getX() + stage.getVelocityX(), player.getY(), obstacles, door)) {
                    player.setX(player.getX() + stage.getVelocityX());
                    game.isRightOrLeft = true;
                    if (stageIndex == 1) {
                        // In stage 2 (index 1), the direction is reversed
                        game.isRightOrLeft = false;
                    }
                }
            }
        }
    }

    /**
     * Special movement for stage 5 where the player is controlled by mouse
     *
     * @param stageIndex Current stage index
     */
    public void movePlayerModified(int stageIndex){
        if (stageIndex == 4) { // Mouse control in stage 5
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();
            if (StdDraw.isMousePressed()) {
                if (!checkCollision(mouseX, mouseY, obstacles, door)) {
                    player.setX(mouseX);
                    player.setY(mouseY);
                }
            }
        }
    }

    /**
     * Checks if the player would collide with any obstacle or door at the next position
     *
     * @param nextX     The next x position to check
     * @param nextY     The next y position to check
     * @param obstacles Array of obstacle coordinates
     * @param door      Door coordinates
     * @return true if there would be a collision, false otherwise
     */
    public boolean checkCollision(double nextX, double nextY, int[][] obstacles, int[] door){
        // Check collision with all obstacles
        for (int[] obstacle : obstacles){
            if (nextX - 10 <= obstacle[2] && nextX + 10 >= obstacle[0] &&
                    nextY - 10 <= obstacle[3] && nextY + 10 >= obstacle[1]){
                return true;
            }
        }

        // Check collision with door
        double left = door[0];
        double right = door[2];
        double down = door[1];
        double up = door[3];

        return nextX - 10 <= right && nextX + 10 >= left &&
                nextY - 10 <= up && nextY + 10 >= down;
    }

    /**
     * Checks if the player is touching any spike
     *
     * @param nextX   The x position to check
     * @param nextY   The y position to check
     * @param spikes  Array of spike coordinates
     * @return true if the player is touching a spike, false otherwise
     */
    public boolean checkSpikes(double nextX, double nextY, int[][] spikes){
        for (int[] spike: spikes){
            if (nextX - 10 < spike[2] && nextX + 10 > spike[0] &&
                    nextY - 10 < spike[3] && nextY + 10 > spike[1]){
                game.howManyTimesButtonPressed = 0;
                game.deathNumber++;
                game.isRightOrLeft = true;
                player.velocityY = 0;
                isDoorOpenedBoolean=false;
                isDoorSliding= false;
                door[3] = 240;
                door[1] = 180;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player is standing on any obstacle
     *
     * @return true if the player is on the ground, false if in the air
     */
    public boolean isOnGround(){
        for (int[] obstacle : obstacles) {
            if (player.getY() - 10 >= obstacle[3] &&
                    player.getY() - 10 <= obstacle[3] - player.velocityY + 0.1 &&
                    obstacle[0] <= 10 + player.getX() &&
                    player.getX() - 10 <= obstacle[2]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player is hitting a ceiling (obstacle from below)
     *
     * @return true if the player is hitting a ceiling, false otherwise
     */
    public boolean isOnCeiling(){
        for (int[] obstacle : obstacles) {
            if (player.getY() + 10 <= obstacle[1] &&
                    player.getY() + 10 >= obstacle[1] - player.velocityY + 0.1 &&
                    obstacle[0] <= 10 + player.getX() &&
                    player.getX() - 10 <= obstacle[2]) {
                player.velocityY = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player has reached the exit pipe to change to the next stage
     *
     * @param stageIndex Current stage index
     * @return true if the player has reached the exit, false otherwise
     */
    public boolean changeStage(int stageIndex){
        if (player.getX() - 10 < exitPipe[1][2] && player.getX() + 10 > exitPipe[1][0] &&
                player.getY() - 5 < exitPipe[1][3] && player.getY() + 5 > exitPipe[1][1]){
            stage.stageNumber++;
            if (stageIndex == 3){
                game.isGameOver = true;
                stage.stageNumber = 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Handles the button press logic to open the door
     * Different stages have different requirements for how many times the button must be pressed
     *
     * @param stageIndex Current stage index
     */
    public void isDoorOpened(int stageIndex) {
        // Check if player is on the button
        if (player.getX() - 10 < button[2] && player.getX() + 10 > button[0] &&
                player.getY() - 10 < button[3] && player.getY() + 10 > button[1]){
            isPlayerInsideButton = true;
        }
        else {
            isPlayerInsideButton = false;
        }

        // Count button presses
        if (isPlayerInsideButton && !isPlayerOnButton) {
            game.howManyTimesButtonPressed++;
            isPlayerOnButton = true;
        }
        else if (!isPlayerInsideButton) {
            isPlayerOnButton = false;
        }

        // Open door based on stage requirements
        if (stageIndex == 3 && game.howManyTimesButtonPressed >= 5) {
            // Stage 4 requires 5 button presses
            isDoorOpenedBoolean = true;
            isDoorSliding = true;
        }
        else if ((stageIndex == 0 || stageIndex == 1 || stageIndex == 2 || stageIndex == 4)
                && game.howManyTimesButtonPressed >= 1) {
            // Other stages require just 1 button press
            isDoorOpenedBoolean = true;
            isDoorSliding = true;
        }
    }

    /**
     * Checks if the restart button is clicked and resets the current stage if it is
     */
    public void restartStage(){
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();

        if (x >= 510 && x <= 590 && y >= 70 && y <= 100 && StdDraw.isMousePressed()){
            StdDraw.pause(200);
            player.respawn(new double[]{130, 465});
            isDoorSliding = false;
            isDoorOpenedBoolean = false;
            game.deathNumber++;
            game.howManyTimesButtonPressed = 0;

            // Reset door position
            door[3] = 240;
            door[1] = 180;
        }
    }

    /**
     * Checks if the help button is clicked
     *
     * @return true if the help button is clicked, false otherwise
     */
    public boolean isHelpRequested(){
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        if (x >= 210 && x <= 290 && y >= 70 && y <= 100 && StdDraw.isMousePressed()){
            StdDraw.pause(200);
            return true;
        }
        return false;
    }

    /**
     * Applies gravity to the player and updates vertical position
     * Handles vertical collision detection with obstacles
     */
    public void handleGravity(){
        // Reset velocity if on ground
        if (isOnGround()){
            player.velocityY = 0;
        }

        // Apply gravity if not on ground
        if (!isOnGround()){
            player.velocityY += stage.getGravity();
        }

        // Update position if no collision
        if (!checkCollision(player.getX(), player.getY() + player.velocityY, obstacles, door)) {
            player.setY(player.getY() + player.velocityY);
        }

        // Reset velocity if hitting ceiling
        if (isOnCeiling()){
            player.velocityY = 0;
        }
    }
}