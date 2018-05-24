package puzzleModel;
/**
 * Coordinate consisting of coordinates 
 * of a car : (x1,y1) and (x2,y2)
 * @author Ziqing Yan, Huiyue Zhang
 *
 */
class Coordinate {
    public int x1;
    public int y1;
    public int x2;
    public int y2;
    
    /**
     * Constructor 
     * @precondition all param is valid 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public Coordinate(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * get function for a copy of the coordinate
     * @return new Coordinate
     */
    public Coordinate getCopy() {
        return new Coordinate(this.x1, this.y1, this.x2, this.y2);
    }
}