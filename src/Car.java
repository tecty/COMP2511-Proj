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
	
	
	
	@Override
	public int hashCode() {
		//System.out.println("CALLING HashCar");
		int hash = 1;
		Coordinate co = this.Paths.get( this.Paths.size()-1);
		hash = hash * 2 + this.num;
		//hash horizontal or vertical
		hash = hash * 3 + ((co.x1 == co.x2) ? 0 : 1);
		//hash length
		if(co.x1 == co.x2) {
			hash = hash * 5 + co.y2-co.y1+1;
		}else {
			hash = hash * 5 + co.x2-co.x1+1;
		}
		hash = hash * 7 + co.x1;
		hash = hash * 11 + co.y1;
		return hash;
	}
	
	@Override
	public boolean equals(Object o) {
		//System.out.println("CALLING EqualsCar");

        if (o == this) return true;
 
        if (o == null || !(o instanceof Car)) return false;
        
        Car c = (Car) o;
        if (this.num != c.num) return false;
			
        Coordinate co = this.Paths.get( this.Paths.size()-1);
		Coordinate oco = c.Paths.get( c.Paths.size()-1);
		
		//this is same as code below
//		if(co.x1 == oco.x1 && co.x2 == oco.x2  && co.y1 == oco.y1 && co.y2 == oco.y2 ) {
//			return true;
//		}
//		return false;
		
		
		//horizontal or vertical equal
		if((co.x1 == co.x2) != (oco.x1 == oco.x2))  return false;
			
		//length equal
		if(co.x1 == co.x2 && co.y2-co.y1+1 != oco.y2-oco.y1+1) {
			return false;
		}else if (co.x2-co.x1+1 != oco.x2-oco.x1+1){
			return false;
		}
		
		if(co.x1 !=oco.x1 || co.y1 !=oco.y1) {
			return false;
		}

		return true;
	}
	
//	@Override
//	protected Car clone() throws CloneNotSupportedException {
//		return (Car) super.clone();
//	}
}
