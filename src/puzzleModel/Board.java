package puzzleModel;

import game.MoveDir;

import java.util.ArrayList;

public class Board {
    public int[][] gridMatrix;
    public ArrayList<Car> carList;
    // record the order of moving car
    public ArrayList<Integer> carID;

    // initialise board
    public Board(ArrayList<Car> c, ArrayList<Integer> carID) {
        this.gridMatrix = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                this.gridMatrix[i][j] = -1;
            }
        }
        this.carList = c;
//        this.carID = new ArrayList<Integer>();
        this.carID = carID;
        updateBoard();
    }

    public void updateBoard() {
        for (int j = 0; j < this.carList.size(); j++) {
            Car c = this.carList.get(j);
            Coordinate co = c.Paths.get(c.Paths.size() - 1);

            // move by row
            if (co.x1 == co.x2) {
                // update new
                for (int i = co.y1; i <= co.y2; i++) {
                    this.gridMatrix[co.x1][i] = c.getCarID();
                }

            // move by column
            } else if (co.y1 == co.y2) {
                // update new
                for (int i = co.x1; i <= co.x2; i++) {
                    this.gridMatrix[i][co.y1] = c.getCarID();
                }
            }
        }
    }

    public int getPathSize() {
        return this.carID.size() + 1;
    }


    public Car popFirst() {
		int id = this.carID.remove(0);
		Coordinate r = this.carList.get(id).Paths.remove(1);
		ArrayList<Coordinate> n = new ArrayList<Coordinate>();
		n.add(r);
		Car c = new Car(id, n);
		return c;
	}
    
    public ArrayList<Car> copyCarList() {
        ArrayList<Car> car = new ArrayList<Car>();
        for (Car c : this.carList) {
            car.add(c.getCopy());
        }
        return car;
    }

    @Override
    public int hashCode() {
        //System.out.println("CALLING HashBoard");
        int result = ((this.carList == null) ? 0 : this.carList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        //System.out.println("CALLING EqualsBoard");
        if (this == o) return true;

        if (o == null || !(o instanceof Board)) return false;

        Board b = (Board) o;
        if ((this.carList == null && b.carList != null)) {
            return false;
        } else if (!this.carList.equals(b.carList)) {
            return false;
        }
        return true;

    }

    // for debug
    public static void printB(Board in) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (in.gridMatrix[i][j] == -1)
                    System.out.print(" " + in.gridMatrix[i][j]);
                else
                    System.out.print("  " + in.gridMatrix[i][j]);
            }
            System.out.println();
        }

        for (int i = 0; i < in.carList.size(); i++) {
            System.out.print("\ncarList " + in.carList.get(i).getCarID() + " : ");

            for (int j = 0; j < in.carList.get(i).Paths.size(); j++) {
                System.out.print(" (" + in.carList.get(i).Paths.get(j).x1 + "," +
                        in.carList.get(i).Paths.get(j).y1 + ") (" +
                        in.carList.get(i).Paths.get(j).x2 + "," +
                        in.carList.get(i).Paths.get(j).y2 + ") + ");
            }
        }

        System.out.print("\nCarID list : ");
        for (int i = 0; i < in.carID.size(); i++) {
            System.out.print(" " + in.carID.get(i));
        }
        System.out.println();
    }
//
//
//    public boolean undo() {
//        // cannot undo at the start of game
//        if (this.carID.size() == 0) {
//            return false;
//        }
//        int cID = this.carID.get(this.carID.size() - 1);
//
//        Car c = this.carList.get(cID);
//        Coordinate co = c.Paths.get(c.Paths.size() - 1);
//        Coordinate preCo = c.Paths.get(c.Paths.size() - 2);
//
//        // move by row
//        if (co.x1 == co.x2) {
//            // reset current
//            for (int i = co.y1; i <= co.y2; i++) {
//                this.gridMatrix[co.x1][i] = -1;
//            }
//
//            // reset previous
//            for (int i = preCo.y1; i <= preCo.y2; i++) {
//                this.gridMatrix[preCo.x1][i] = cID;
//            }
//
//        // move by column
//        } else if (co.y1 == co.y2) {
//            // reset current
//            for (int i = co.x1; i <= co.x2; i++) {
//                this.gridMatrix[i][co.y1] = -1;
//            }
//
//            // add previous
//            for (int i = preCo.x1; i <= preCo.x2; i++) {
//                this.gridMatrix[i][preCo.y1] = cID;
//            }
//
//        }
//
//        // remove form paths
//        c.Paths.remove(c.Paths.size() - 1);
//        // remove from carID
//        this.carID.remove(this.carID.size() - 1);
//
//        return true;
//    }
//
//    // add cID to carID list, reset previous board, move/add new board
//    public void moveCar(int cID) {
//        // add to carID list
//        this.carID.add(cID);
//
//        Car c = this.carList.get(cID);
//        Coordinate co = c.Paths.get(c.Paths.size() - 1);
//        Coordinate preCo = c.Paths.get(c.Paths.size() - 2);
//
//        // move by row
//        if (co.x1 == co.x2) {
//            //reset previous
//            for (int i = preCo.y1; i <= preCo.y2; i++) {
//                this.gridMatrix[preCo.x1][i] = -1;
//            }
//
//            // add new
//            for (int i = co.y1; i <= co.y2; i++) {
//                this.gridMatrix[co.x1][i] = cID;
//            }
//
//        } else if (co.y1 == co.y2) {
//            // reset previous
//            for (int i = preCo.x1; i <= preCo.x2; i++) {
//                this.gridMatrix[i][preCo.y1] = -1;
//            }
//
//            // add new
//            for (int i = co.x1; i <= co.x2; i++) {
//                this.gridMatrix[i][co.y1] = cID;
//            }
//        }
//    }
//
//
//    public boolean isValidMove(int[][] b, Car c) {
//        // boolean isValid = false;
//        Coordinate co = c.Paths.get(c.Paths.size() - 1);
//
//        if (c.carID == 0 && (co.y1 > 5 || co.y2 > 5)) {
//            // congratulations
//            return true;
//        }
//
//        // out of range
//        if (co.x1 > 5 || co.x2 > 5 || co.y2 > 5 || co.y2 > 5 ||
//                co.x1 < 0 || co.x2 < 0 || co.y2 < 0 || co.y2 < 0) {
//            c.Paths.remove(c.Paths.size() - 1);
//            return false;
//        }
//
//        // move by row
//        if (co.x1 == co.x2) {
//            for (int i = co.y1; i <= co.y2; i++) {
//                if (b[co.x1][i] != -1 && b[co.x1][i] != c.getCarID()) {
//                    c.Paths.remove(c.Paths.size() - 1);
//                    return false;
//                }
//            }
//
//        // move by column
//        } else if (co.y1 == co.y2) {
//            for (int i = co.x1; i <= co.x2; i++) {
//                if (b[i][co.y1] != -1 && b[i][co.y1] != c.getCarID()) {
//                    c.Paths.remove(c.Paths.size() - 1);
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    public ArrayList<Board> getPossible() {
//		System.out.println("\n GETTING POSSIBLE BOARD");
        ArrayList<Board> possibleBoards = new ArrayList<Board>();
        for (int i = 0; i < this.carList.size(); i++) {
            Car car = this.carList.get(i);

            // horizontal block move left
            if (car.Paths.get(0).x1 == car.Paths.get(0).x2) {
                if ((car.Paths.get(0).y1 > 0) && this.gridMatrix[car.Paths.get(0).x1][car.Paths.get(0).y1 - 1] == -1) {
                    Coordinate get = new Coordinate(car.Paths.get(0).x1, (car.Paths.get(0).y1 - 1), car.Paths.get(0).x2, (car.Paths.get(0).y2 - 1));
                    ArrayList<Car> cars = new ArrayList<Car>();
                    for (int j = 0; j < this.carList.size(); j++) {
                        if (j == i) {
                            ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
                            paths.add(get);
                            cars.add(new Car(j, paths));
                        } else {
                            cars.add(j, this.carList.get(j).getCopy());
                        }
                    }
                    Board g = new Board(cars, (new ArrayList<Integer>()));
                    possibleBoards.add(g);
                }

                // horizontal block move right
                if ((car.Paths.get(0).y2 < 5) && this.gridMatrix[car.Paths.get(0).x1][car.Paths.get(0).y2 + 1] == -1) {
                    Coordinate get = new Coordinate(car.Paths.get(0).x1, (car.Paths.get(0).y1 + 1), car.Paths.get(0).x2, (car.Paths.get(0).y2 + 1));
                    ArrayList<Car> cars = new ArrayList<Car>();
                    for (int j = 0; j < this.carList.size(); j++) {
                        if (j == i) {
                            ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
                            paths.add(get);
                            cars.add(new Car(j, paths));
                        } else {
                            cars.add(j, this.carList.get(j).getCopy());
                        }
                    }
                    Board g = new Board(cars, (new ArrayList<Integer>()));
                    possibleBoards.add(g);
                }
            } else {

                // vertical block move up
                if ((car.Paths.get(0).x1 > 0) && this.gridMatrix[car.Paths.get(0).x1 - 1][car.Paths.get(0).y1] == -1) {
                    Coordinate get = new Coordinate((car.Paths.get(0).x1 - 1), car.Paths.get(0).y1, (car.Paths.get(0).x2 - 1), car.Paths.get(0).y2);
                    ArrayList<Car> cars = new ArrayList<Car>();
                    for (int j = 0; j < this.carList.size(); j++) {
                        if (j == i) {
                            ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
                            paths.add(get);
                            cars.add(new Car(j, paths));
                        } else {
                            cars.add(j, this.carList.get(j).getCopy());
                        }
                    }
                    Board g = new Board(cars, (new ArrayList<Integer>()));
                    possibleBoards.add(g);
                }

                // vertical block move down
                if ((car.Paths.get(0).x2 < 5) && this.gridMatrix[car.Paths.get(0).x2 + 1][car.Paths.get(0).y2] == -1) {
                    Coordinate get = new Coordinate((car.Paths.get(0).x1 + 1), car.Paths.get(0).y1, (car.Paths.get(0).x2 + 1), car.Paths.get(0).y2);
                    ArrayList<Car> cars = new ArrayList<Car>();
                    for (int j = 0; j < this.carList.size(); j++) {
                        if (j == i) {
                            ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
                            paths.add(get);
                            cars.add(new Car(j, paths));
                        } else {
                            cars.add(j, this.carList.get(j).getCopy());
                        }
                    }
                    Board g = new Board(cars, (new ArrayList<Integer>()));
                    possibleBoards.add(g);
                }
            }
        }
//		System.out.println("Finish possible board ");
        return possibleBoards;
    }

    public ArrayList<Car> getUnmovedCar() {
        // return the unmoved car of the solved map
        ArrayList<Car> return_list = new ArrayList<>();
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getPathSize() == 1) {
                // only have unused car
                return_list.add(carList.get(i));
            }
        }
        return return_list;
    }

    public void deleteCarByList(ArrayList<Car> carList) {
        for (int i = carList.size() - 1; i > 0; i--) {
            this.carList.remove(carList.get(i).getCarID());
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                this.gridMatrix[i][j] = -1;
            }
        }
        // re-render the board.
        updateBoard();
    }

    /**
     * Change the car list in the board to the
     * style use in the front end.
     * @return
     */
    public ArrayList<game.Car> toCarList(){
        ArrayList<game.Car> retList = new ArrayList<>();
        for (int i = 0; i < carList.size(); i++) {
            // all the car in the car list
            Car thisCar = carList.get(i);
            // coordinate of this car
            Coordinate thisCor =  thisCar.Paths.get(0);

            /**
             * the coordinate of this package is opposite
             * from intuitive.
             */
            // check it's direction
            if (thisCor.x1 == thisCor.x2){
                // move horizontally
                retList.add(new game.Car(
                        MoveDir.HORIZONTAL,
                        i,
                        Math.min(thisCor.y1,thisCor.y2),
                        Math.min(thisCor.x1,thisCor.x2),
                        Math.abs(thisCor.y2-thisCor.y1)+1
                ));
            }
            else{
                // move vertically
                retList.add(new game.Car(
                            MoveDir.VERTICAL,
                            i,
                            Math.min(thisCor.y1,thisCor.y2),
                            Math.min(thisCor.x1,thisCor.x2),
                            Math.abs(thisCor.x2-thisCor.x1)+1
                        ));
            }
        }
        System.out.println("The generated board is ");
//        printB(this);
        return retList;
    }
}
