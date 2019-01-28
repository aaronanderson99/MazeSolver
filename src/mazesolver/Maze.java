package mazesolver;

/**
 *  A simple maze-solving program.
 *
 *  The class below uses int[][] array to store tha data for a maze, with
 *      0 = wall
 *      1 = space
 *      2 = path
 *      3 = ship
 *      4 = goal.
 *
 *  The basic algorithm this class follows:
 *      if right turn is possible, go right,
 *      else go forward
 *      else go left
 *      else turn around.
 *
 *  The takeStep() method move the ship one step towards the goal,
 *  and the findExit() method repeats this algorithm until the exit is found.
 *
 * @author  Aaron Anderson
 *          10/21/2017
 */



import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Maze {
    private int[][] field = new int[100][100];
    private int[] solution;
    private SimpleIntegerProperty posX;
    private SimpleIntegerProperty posY;
    private SimpleIntegerProperty direction;
    private SimpleBooleanProperty backtracking;
    private SimpleDoubleProperty complete;

    /**
     *  Constructor for Maze object. Takes an int[][] array for the maze field, with
     *      0 = wall
     *      1 = space
     *      2 = path
     *      3 = ship
     *      4 = goal.
     *
     * @param field:    array representation of maze.
     */
    public Maze(int[][] field) {
        this.field = field;
        this.posX = new SimpleIntegerProperty();
        this.posY = new SimpleIntegerProperty();
        this.direction = new SimpleIntegerProperty(2);
        this.backtracking = new SimpleBooleanProperty(false);
        this.complete = new SimpleDoubleProperty(0);
        this.solution = new int[2];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == 3) {
                    this.posX.set(j);
                    this.posY.set(i);
                }
                if (field[i][j] == 4) {
                    solution[0] = i;
                    solution[1] = j;
                }
            }
        }
    }

    /**
     * Accessor for field
     * @return field.
     */
    public int[][] getField() {
        return this.field;
    }

    /**
     * Accessor for x coordinate
     * @return posX:    x coordinate of the ship.
     */
    public SimpleIntegerProperty getPosX() {
        return this.posX;
    }

    /**
     * Accessor for y coordinate
     * @return posY:    y coordinate of the ship.
     */
    public SimpleIntegerProperty getPosY() {
        return this.posY;
    }

    /**
     * Accessor for direction
     * @return direction.
     */
    public SimpleIntegerProperty getDirection() {
        return this.direction;
    }

    /**
     * Accessor for isBacktracking
     * @return backtracking:    whether ship is going over a previous path.
     */
    public boolean isBacktracking() {
        return this.backtracking.get();
    }

    /**
     * Accessor for complete
     * @return complete:    int representing whether the maze is complete (ship is at goal).
     */
    public SimpleDoubleProperty isComplete() {
        return this.complete;
    }

    /**
     * Mutator for field. Takes an int[][] array for the maze field, with
     *      *  0 = wall
     *      *  1 = space
     *      *  2 = path
     *      *  3 = ship
     *      *  4 = goal.
     * @param field:    array representation of maze.
     */
    public void setField(int[][] field) {
        this.field = field;
    }

    /**
     * Mutator for direction. Takes an int for ship direction, with
     *      *  0 = N
     *      *  1 = W
     *      *  2 = S
     *      *  3 = E
     * @param direction:    int representing ship direction.
     */

    public void setDirection(int direction) {
        this.direction.set(direction);
    }

    /**
     * Mutator for posX
     * @param posX:    x coordinate of the ship.
     */
    public void setPosX(int posX) {
        this.posX.set(posX);
    }

    /**
     * Mutator for posY
     * @param posY:    y coordinate of the ship.
     */
    public void setPosY(int posY) {
        this.posY.set(posY);
    }

    /**
     * Mutator for backtracking
     * @param backtracking:    whether ship is going over a previous path.
     */
    public void setIsBacktracking(boolean backtracking) {
        this.backtracking.set(backtracking);
    }

    /**
     *  Prints basic graphic of maze to command line, with
     *  # = wall
     * " "= space
     *  ~ = path
     *  @ = ship
     *  $ = goal.
     */
    public void displayMaze() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                switch (field[i][j]) {
                    case 0:
                        System.out.print("#");
                        break;
                    case 1:
                        System.out.print(" ");
                        break;
                    case 2:
                        System.out.print("~");
                        break;
                    case 3:
                        System.out.print("@");
                        break;
                    case 4:
                        System.out.print("$");
                }
            }
            System.out.println("");
        }
    }

    /**
     *  Takes a step towards the goal of the maze.
     *  The basic algorithm this method follows:
     *      if right turn is possible, go right,
     *      else go forward
     *      else go left
     *      else turn around.
     *      (Implemented for each starting orientation of the ship).
     */
    public void takeStep() {
        if (complete.get() != 1) {
            if (posX.get() == solution[1] && posY.get() == solution[0]) {
                complete.set(1);
            } else if (direction.get() == 0) {
                if (field[posY.get()][posX.get() + 1] != 0) {
                    direction.set(3);
                    backtracking.set(field[posY.get()][posX.get() + 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else if (field[posY.get() - 1][posX.get()] != 0) {
                    backtracking.set(field[posY.get() - 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get()][posX.get() - 1] != 0)) {
                    direction.set(1);
                    backtracking.set(field[posY.get()][posX.get() - 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else {
                    direction.set(2);
                    backtracking.set(field[posY.get() + 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                }
            } else if (direction.get() == 1) {
                if ((field[posY.get() - 1][posX.get()] != 0)) {
                    direction.set(0);
                    backtracking.set(field[posY.get() - 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get()][posX.get() - 1] != 0)) {
                    backtracking.set(field[posY.get()][posX.get() - 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get() + 1][posX.get()] != 0)) {
                    direction.set(2);
                    backtracking.set(field[posY.get() + 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else {
                    direction.set(3);
                    backtracking.set(field[posY.get()][posX.get() + 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                }
            } else if (direction.get() == 2) {
                if (field[posY.get()][posX.get() - 1] != 0) {
                    direction.set(1);
                    backtracking.set(field[posY.get()][posX.get() - 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get() + 1][posX.get()] != 0)) {
                    backtracking.set(field[posY.get() + 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get()][posX.get() + 1] != 0)) {
                    direction.set(3);
                    backtracking.set(field[posY.get()][posX.get() + 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else {
                    direction.set(0);
                    backtracking.set(field[posY.get() - 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                }
            } else if (direction.get() == 3) {
                if ((field[posY.get() + 1][posX.get()] != 0)) {
                    direction.set(2);
                    backtracking.set(field[posY.get() + 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get()][posX.get() + 1] != 0)) {
                    backtracking.set(field[posY.get()][posX.get() + 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() + 1);
                    field[posY.get()][posX.get()] = 3;
                } else if ((field[posY.get() - 1][posX.get()] != 0)) {
                    direction.set(0);
                    backtracking.set(field[posY.get() - 1][posX.get()] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posY.set(posY.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                } else {
                    direction.set(1);
                    backtracking.set(field[posY.get()][posX.get() - 1] == 2);
                    if (backtracking.get()) {
                        field[posY.get()][posX.get()] = 1;
                    } else {
                        field[posY.get()][posX.get()] = 2;
                    }
                    posX.set(posX.get() - 1);
                    field[posY.get()][posX.get()] = 3;
                }
            }
            displayMaze();
        }
    }

    /**
     *  Finds the exit of the maze by repeating the step algorithm until the goal is reached.
     */
    public void findExit() {
        while (complete.get() != 1) {
            takeStep();
        }
    }
}

/*  Values:
 * 		0 = wall  = #
 * 		1 = space =" "
 * 		2 = path  = ~
 * 		3 = ship  = @
 * 	    4 = goal  = $
 *
 * 		0 = N
 * 		1 = W
 * 		2 = S
 * 		3 = E
 */