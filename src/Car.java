import java.util.ArrayList;

class Car {
	public ArrayList<Coordinate> Paths = new ArrayList<Coordinate>();
	public int num;
	
	public Car(int num,Coordinate Paths) {
		this.num = num;
		this.Paths.add(Paths);
	}

}
