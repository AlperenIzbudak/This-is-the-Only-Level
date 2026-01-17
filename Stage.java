// Muttalip Alperen Izbudak
// 2023400078
import java.awt.*;

/**
 * The Stage class represents a game level with specific physics, controls, and information.
 * Each stage has unique properties that affect player movement and interaction.
 */
public class Stage {
    public int stageNumber;       // The stage's number/index
    public double gravity;        // Gravity value affecting the player's vertical movement
    public double velocityX;      // Player's horizontal movement speed
    public double velocityY;      // Player's initial jump velocity
    public int rightCode;         // Key code for moving right
    public int leftCode;          // Key code for moving left
    public int upCode;            // Key code for jumping
    public String help;           // Help text displayed when requested
    public String clue;           // Clue text displayed by default
    public Color color;           // Color for the stage (unused in this implementation)

    /**
     * Constructor for stages that use all three movement keys (right, left, up)
     *
     * @param gravity     The gravity constant for the stage
     * @param velocityX   Player's horizontal movement speed
     * @param velocityY   Player's initial jump velocity
     * @param stageNumber The stage number
     * @param rightCode   Key code for moving right
     * @param leftCode    Key code for moving left
     * @param upCode      Key code for jumping
     * @param clue        Hint shown on the screen
     * @param help        Detailed help text shown when requested
     */
    Stage(double gravity, double velocityX, double velocityY, int stageNumber, int rightCode, int leftCode, int upCode, String clue, String help){
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.help = help;
        this.clue = clue;
    }

    /**
     * Constructor for stages that don't use the up key (e.g., auto-jump stages)
     *
     * @param gravity     The gravity constant for the stage
     * @param velocityX   Player's horizontal movement speed
     * @param velocityY   Player's initial jump velocity
     * @param stageNumber The stage number
     * @param rightCode   Key code for moving right
     * @param leftCode    Key code for moving left
     * @param clue        Hint shown on the screen
     * @param help        Detailed help text shown when requested
     */
    Stage(double gravity, double velocityX, double velocityY, int stageNumber, int rightCode, int leftCode, String clue, String help){
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.help = help;
        this.clue = clue;
    }

    /**
     * @return The current stage number
     */
    public int getStageNumber() {
        return stageNumber;
    }

    /**
     * @return The horizontal movement speed
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * @return The initial jump velocity
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * @return The gravity value for this stage
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * @return An array containing the key codes for right, left, and up movements
     */
    public int[] getKeyCodes(){
        return new int[]{rightCode, leftCode, upCode};
    }

    /**
     * Draws the user interface at the bottom of the screen
     *
     * @param deathNumber     Number of times the player has died
     * @param isHelpRequested Whether the help text should be displayed instead of the clue
     * @param time            Current game time as a formatted string
     * @param stageIndex      Current stage index
     */
    public void printUserScreen(int deathNumber, boolean isHelpRequested, String time, int stageIndex){
        StdDraw.setPenColor(new Color(56, 93, 172)); // Color of the bottom UI area
        StdDraw.filledRectangle(400, 60, 400, 60);   // Drawing bottom part
        StdDraw.setPenColor(StdDraw.WHITE);

        // Help button
        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15);

        // Restart button
        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15);

        // Reset button
        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.rectangle(400, 20, 80, 15);

        // Game statistics
        StdDraw.text(700, 75, "Deaths: " + deathNumber);
        StdDraw.text(700, 50, "Stage: " + (stageIndex + 1));
        StdDraw.text(100, 50, time);
        StdDraw.text(100, 75, "Level: 1");

        // Display either clue or help text based on user request
        if (!isHelpRequested) {
            StdDraw.text(400, 85, "Clue:");
            StdDraw.text(400, 55, clue);
        }
        else {
            StdDraw.text(400, 85, "Help:");
            StdDraw.text(400, 55, help);
        }
    }
}