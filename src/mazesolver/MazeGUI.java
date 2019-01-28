package mazesolver;

/**
 *  A simple GUI for a maze solving program created using JavaFX.
 *  Uses the mazesolver.Maze class.
 *
 * @author  Aaron Anderson
 *          10/23/2017
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MazeGUI extends Application {

    // Starts the GUI using an instance of Maze with an example field.
    @Override
    public void start(Stage primaryStage) {
        int[][] field =
                        {{0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                        {0,0,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
                        {0,0,1,1,1,1,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0},
                        {0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                        {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0},
                        {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                        {0,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
                        {0,0,0,1,1,1,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
                        {0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
                        {0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
                        {0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        Maze maze  = new Maze(field);

        // Creates the frame, a pane for the maze field and a pane for input buttons.
        BorderPane frame = new BorderPane();
        Pane mazePane = new Pane();
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        // Creates input buttons and the "Maze Complete" label.
        Button stepBtn = new Button("Step");
        stepBtn.setMinWidth(50);
        Button solveBtn = new Button("Solve");
        solveBtn.setMinWidth(50);
        Label complete = new Label("Maze Complete!");
        complete.setTextFill(Color.GREEN);
        complete.opacityProperty().bind(maze.isComplete());
        buttons.getChildren().addAll(stepBtn, solveBtn, complete);

        // Creates maze field pane, and uses grey squares to represent walls and white squares to represent spaces.
        GridPane mazeField = new GridPane();
        mazeField.setAlignment(Pos.CENTER);

        for (int i = 0; i < maze.getField().length; i++) {
            for (int j = 0; j < maze.getField()[0].length; j++) {
                Rectangle rect = new Rectangle(25, 30);
                rect.setStroke(Color.BLACK);
                if (maze.getField()[i][j] == 0) {
                    rect.setFill(Color.GREY);
                }
                else if (maze.getField()[i][j] == 4){
                    rect.setFill(Color.GREEN);
                }
                else {
                    rect.setFill(Color.WHITE);
                }
                mazeField.add(rect, j, i);
            }
        }

        // Creates pane for ship, using Ship.gif image.
        Pane shipPane = new Pane();
        ImageView ship = new ImageView("file:image/Ship.gif");
        ship.rotateProperty().bind(maze.getDirection().multiply(-90));
        shipPane.getChildren().add(ship);
        ship.xProperty().bind(maze.getPosX().multiply(26).add(5));
        ship.yProperty().bind(maze.getPosY().multiply(31).add(7));

        mazePane.getChildren().addAll(mazeField, shipPane);
        frame.setCenter(mazePane);
        frame.setBottom(buttons);

        // Sets action for stepBtn to takeStep() and update maze pane.
        stepBtn.setOnAction(e -> frame.setCenter(new Pane(takeStep(maze), shipPane)));

        // Sets action for solveBtn to findExit() and update maze pane.
        solveBtn.setOnAction(e -> frame.setCenter(new Pane(findExit(maze), shipPane)));

        Scene scene = new Scene(frame, maze.getField()[0].length*26, maze.getField().length*31 + 30);
        primaryStage.setTitle("MazeSolver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Updates maze pane.
    public GridPane displayMaze(Maze maze) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < maze.getField().length; i++) {
            for (int j = 0; j < maze.getField()[0].length; j++) {
                Pane pane = new Pane();
                Rectangle rect = new Rectangle(25, 30);
                rect.setStroke(Color.BLACK);

                Circle circle = new Circle(12.5, 15, 8);
                circle.setFill(Color.RED);

                if (maze.getField()[i][j] == 0) {
                    rect.setFill(Color.GREY);
                    pane.getChildren().add(rect);
                }
                else if (maze.getField()[i][j] == 1) {
                    rect.setFill(Color.WHITE);
                    pane.getChildren().add(rect);
                }
                else if (maze.getField()[i][j] == 4) {
                    rect.setFill(Color.GREEN);
                    pane.getChildren().add(rect);
                }
                else if (maze.getField()[i][j] == 2) {
                    rect.setFill(Color.BLUE);
                    pane.getChildren().add(rect);
                }
                gridPane.add(pane, j, i);
            }
        }
        return gridPane;
    }

    // Takes step toward goal.
    public GridPane takeStep(Maze maze) {
        maze.takeStep();
        return displayMaze(maze);

    }

    // Solves the maze.
    public GridPane findExit(Maze maze) {
        maze.findExit();
        return displayMaze(maze);
    }

    // Main method.
    public static void main(String[] args) {
        Application.launch(args);
    }
}



