//This class is used to calculate the distance between each city in each path and then returns the
//total distance someone would travel to get through the entire path while stopping at each city
import java.lang.Math;

public class Cities {

    public double calculateDistanceForAPath(City[] cities){
        int x = 0;
        int y = 0;
        double totalDistance = 0;
        for(int i = 0; i < cities.length - 1; i++){
            x = (cities[i].x_coor - cities[i+1].x_coor) * (cities[i].x_coor - cities[i+1].x_coor);
            y = (cities[i].y_coor - cities[i+1].y_coor) * (cities[i].y_coor - cities[i+1].y_coor);
            totalDistance += Math.sqrt(x+y);
        }
        return totalDistance;
    }

}
