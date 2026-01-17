// Muttalip Alperen Izbudak
// 2023400078

/**
 * The Player class represents the character controlled by the user.
 * It handles player position, dimensions, and rendering.
 */
public class Player {
    public double x;          // Player's x coordinate
    public double y;          // Player's y coordinate
    public double width = 20; // Player's width
    public double height = 20; // Player's height
    public double velocityY;  // Player's current vertical velocity (affected by gravity)

    /**
     * Constructor to create a player at a specified position
     *
     * @param x Initial x coordinate
     * @param y Initial y coordinate
     */
    Player(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return The player's current x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * @return The player's current y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the player's x coordinate
     *
     * @param x The new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the player's y coordinate
     *
     * @param y The new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Resets the player's position to the spawn point
     *
     * @param spawnPoint Array containing [x, y] coordinates of the spawn point
     */
    public void respawn(double[] spawnPoint){
        this.x = spawnPoint[0];
        this.y = spawnPoint[1];
    }

    /**
     * Draws the player character on the screen as an elephant
     *
     * @param isRightOrLeft Direction the player is facing (true for right, false for left)
     *                      In stage 2, this behavior is reversed
     */
    public void draw(boolean isRightOrLeft){
        // Elephant drawing based on direction
        // right = true / left = false
        if (isRightOrLeft) {
            StdDraw.picture(x, y, "misc/ElephantRight.png", width, height);
        }
        else {
            StdDraw.picture(x, y, "misc/ElephantLeft.png", width, height);
        }
    }
}