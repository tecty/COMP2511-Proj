import java.util.ArrayList;

class Car {
	//public ArrayList<Coordinate> Paths = new ArrayList<Coordinate>();
	public int num;
	public ArrayList<Coordinate> Paths;
	
	public Car(int num, ArrayList<Coordinate> Paths) {
		this.num = num;
		this.Paths = Paths;
	}
	
	public Car getCopy() {
		ArrayList<Coordinate> p = new ArrayList<Coordinate>();
		for(int i = 0; i < this.Paths.size(); i ++) {
			p.add( this.Paths.get(i).getCopy() );
		}
		Car c = new Car(this.num, p);
		return c;
	}
	
//	@Override
//	protected Car clone() throws CloneNotSupportedException {
//		return (Car) super.clone();
//	}
}
