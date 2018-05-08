package game;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameController {
    // create game in this controller
    @FXML
    Pane rootPane;

    // store all the car in this game
    Group carGroup = new Group();
    Group gridGroup = new Group();

    // the size of each grid
    public final static int GRID_SIZE = 50;

    @FXML
    private void initialize(){
        // set up all the grid
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                Grid grid = new Grid(x,y);
                // add this grid to group
                gridGroup.getChildren().add(grid);
            }
        }

        // add the group to the pane and group of car
        // to show in the scene
        rootPane.getChildren().addAll(gridGroup,carGroup);
    }


    private void  showGame(Game game){

    }
}
