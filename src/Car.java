import java.util.ArrayList;

class Car {
	public ArrayList<Coordinate> Paths = new ArrayList<Coordinate>();
	public int num;
	
	public Car(int num,Coordinate Paths) {
		this.num = num;
		this.Paths.add(Paths);
	}
	
//	public Car getCopy() {
//		ArrayList<Coordinate> p = new ArrayList<Coordinate>();
//		for(int i = 0; i < this.Paths.size(); i ++) {
//			p.add( this.Paths.get(i).getCopy() );
//		}
//		return new Car(this.num, );
//	}
}
