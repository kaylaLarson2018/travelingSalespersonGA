//This class creates the city objects, each object has an x and y coordinate representing its location
//and a string representing its name.
public class City {
    int x_coor;
    int y_coor;
    String point;

    @Override
    public String toString() {
        return ("x= " + x_coor + " y= " + y_coor);
    }

    public String getPoint() {
        return point;
    }
    //The constructor to create the City object and set the fields
    public City(int x_coor, int y_coor, String point) {
        this.x_coor = x_coor;
        this.y_coor = y_coor;
        this.point = point;

    }

}
