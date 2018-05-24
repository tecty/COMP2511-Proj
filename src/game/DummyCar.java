package game;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import puzzleModel.Coordinate;

public class DummyCar extends StackPane{
	//animation transition
    TranslateTransition transition;
    //generate a dummy car as a hint notation
    //attach the same car image of nextCar to the dummy car
    ImageView dummyCarImg;
    
    
    public DummyCar(){
    	transition = new TranslateTransition();
    	dummyCarImg = new ImageView();
    	
		getChildren().add(dummyCarImg);
    }
    
    public void project(Car nextCar, Coordinate nextStep) {
	    //the dummy car originally inherits all physical locations and appearance from nextCar
	    double dummyX = nextCar.getGridX() * GameController.GRID_SIZE;
	    double dummyY = nextCar.getGridY() * GameController.GRID_SIZE;
	    
	    String nextCarAppearance = nextCar.getAppearance();
	    if(nextCar.getDir() == MoveDir.HORIZONTAL) {
	    	//physical location
	    	dummyCarImg.setFitWidth(nextCar.getLen() * GameController.GRID_SIZE);
	    	dummyCarImg.setFitHeight(GameController.GRID_SIZE);
	    }
	    else {
	    	//physical location
	    	dummyCarImg.setFitWidth(GameController.GRID_SIZE);
	    	dummyCarImg.setFitHeight(nextCar.getLen() * GameController.GRID_SIZE); 
	     }
	    //appearance
	    dummyCarImg.setImage(new Image(getClass().getResourceAsStream("../img/"+nextCarAppearance)));
	    dummyCarImg.setOpacity(0.5);
	    this.relocate(dummyX, dummyY);
	    
	    //add animation transition to the dummy car
	    transition.setDuration(Duration.millis(1000));
	    transition.setNode(this);
	    System.out.println();
	    
	    //remind: the axis in Coordinate class is different from the axis in Grid
	    if(nextCar.getDir()==MoveDir.HORIZONTAL) {
	    	transition.setByX((nextStep.y1-nextCar.getGridX()) * GameController.GRID_SIZE);
	    	transition.setByY(0);
	    }
	    else{
	    	transition.setByY((nextStep.x1-nextCar.getGridY())*GameController.GRID_SIZE);
	    	transition.setByX(0);
	    }
	    transition.setCycleCount(Animation.INDEFINITE);
	    transition.playFromStart();
    }
    
}
