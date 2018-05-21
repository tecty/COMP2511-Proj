//package game;
//
//import javafx.animation.AnimationTimer;
//import javafx.beans.property.*;
//import javafx.scene.Group;
//import save.GameSave;
//
//import java.net.MalformedURLException;
//import java.util.Stack;
//
//public class Board {
//
//    // reference to the board
//    private Grid[][] board = new Grid[6][6];
//
//
//    // store all the car in this game
//    Group carGroup = new Group();
//    Group gridGroup = new Group();
//
//    // store the moved car id of each step
//    Stack<Movement> history = new Stack<>();
//
//    //step counter
//    //variables prepared for stepCounter
//    IntegerProperty steps = new SimpleIntegerProperty();
//
//    //timer
//    //variables prepared for timer
//    DoubleProperty time = new SimpleDoubleProperty();
//
//    BooleanProperty running = new SimpleBooleanProperty();
//
//    AnimationTimer timer = new AnimationTimer() {
//        private long startTime ;
//
//        @Override
//        public void start() {
//            startTime = System.currentTimeMillis();
//            running.set(true);
//            super.start();
//        }
//
//        @Override
//        public void stop() {
//            running.set(false);
//            super.stop();
//        }
//
//        @Override
//        public void handle(long timestamp) {
//            long now = System.currentTimeMillis();
//            time.set((now - startTime) / 1000.0);
//        }
//    };
//    // when a click on a car, it would set
//    private double mouseOriginX, mouseOriginY;
//    // cleaner code to handle collision
//    // record the mouse offset of this drag
//    private double mouseOffsetX, mouseOffsetY;
//
//    // the range of the offset of mouse movement can achieve
//    // for clicked particular car
//    private double offsetMax, offsetMin;
//
//    //load car settings from the save slot
//    public void loadSaveSlot(GameSave saveslot, int chosenLevel) throws MalformedURLException {
//        System.out.println("load saveslot");
//        this.saveslot = saveslot;
//        this.chosenLevel = chosenLevel;
//
//        //modify the title
//        title.setText("Gridlock Level "+(chosenLevel+1));
//
//        //check if it is expert mode
//        if(saveslot.expertMode()) {
//            undo.setDisable(true);
//            undo.setVisible(false);
//        }
//
//        //check if there is next level
//        if(chosenLevel >= 8) next.setDisable(true);
//
//        addCars();
//        rootPane.getChildren().addAll(carGroup);
//    }
//}
