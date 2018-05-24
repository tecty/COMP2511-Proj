package game;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import puzzleModel.Coordinate;

/**
 * Car for project a hint.
 */
public class DummyCar extends StackPane{
	//animation transition
    private TranslateTransition transition;
    //generate a dummy car as a hint notation
    //attach the same car image of nextCar to the dummy car
    private ImageView dummyCarImg;


	/**
	 * initial basic element of this car's construction.
	 */
	public DummyCar(){
    	transition = new TranslateTransition();
    	dummyCarImg = new ImageView();
    	
		getChildren().add(dummyCarImg);
    }

	/**
	 * Project a animation of this car.
	 * @param car Car which hint is next step.
	 * @param nextStep The next step of this car would go to.
	 */
	public void project(Car car, Coordinate nextStep) {
	    //the dummy car originally inherits all physical locations and appearance from nextCar
	    double dummyX = car.getGridX() * GameController.GRID_SIZE;
	    double dummyY = car.getGridY() * GameController.GRID_SIZE;
	    
	    String nextCarAppearance = car.getAppearance();
	    if(car.getDir() == MoveDir.HORIZONTAL) {
	    	//physical location
	    	dummyCarImg.setFitWidth(car.getLen() * GameController.GRID_SIZE);
	    	dummyCarImg.setFitHeight(GameController.GRID_SIZE);
	    }
	    else {
	    	//physical location
	    	dummyCarImg.setFitWidth(GameController.GRID_SIZE);
	    	dummyCarImg.setFitHeight(car.getLen() * GameController.GRID_SIZE);
	     }
	    //appearance
	    dummyCarImg.setImage(new Image(getClass().getResourceAsStream("../img/"+nextCarAppearance)));
	    dummyCarImg.setOpacity(0.5);
	    this.relocate(dummyX, dummyY);
	    
	    //add animation transition to the dummy car
	    transition.setDuration(Duration.millis(1000));
	    transition.setNode(this);
	    
	    //remind: the axis in Coordinate class is different from the axis in Grid
	    if(car.getDir()==MoveDir.HORIZONTAL) {
	    	transition.setByX((nextStep.y1-car.getGridX()) * GameController.GRID_SIZE);
	    	transition.setByY(0);
	    }
	    else{
	    	transition.setByY((nextStep.x1-car.getGridY())*GameController.GRID_SIZE);
	    	transition.setByX(0);
	    }
	    transition.setCycleCount(Animation.INDEFINITE);
	    transition.playFromStart();
    }
    
}
