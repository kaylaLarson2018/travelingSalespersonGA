//This class generates children using double point crossover
//This means that 2 indices are randomly chosen and the cities between index 0 and the first point,
//the first point and the second point, and the second point and the last index are swapped.
//Then fill in the gaps in each child.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class DoublePtCrossover {
    //Constant to hold mutation rate
    public static int MUTATION_RATE = 2;//2 percent of the time we will randomly swap to cities to add some more variation

    //method to generate 2 children using doublePtCrossover
    public Children doubleCrossover(City[] array1, City[] array2){
        //Arrays to hold new children
        City[] child = new City[8];
        City[] child2 = new City[8];
        //Generate random numbers to represent the points that crossover will occur
        Random rand = new Random();
        int size = array1.length;
        //Counter to keep track of points added
        int pointsAdded = 0;
        ArrayList<Integer> points = new ArrayList();
        while(pointsAdded < 2){//need to get 2 points that are not the same
            int point = rand.nextInt(size);
            if(!Arrays.asList(points).contains(point)){//check if the point is already in the list
                points.add(point);
                pointsAdded++;
            }
        }

        Collections.sort(points);

        //child gets indices, 0 to the first point of the first parent
        for(int i = 0; i < points.get(0); i++){
            child[i] = array1[i];
        }
        //child gets indices between second point and last index of the parent
        for(int i = points.get(1); i < child2.length; i++){
            child[i] = array1[i];
        }
        //child 2 gets indices between point 1 and point 2 of the parent
        for(int i = points.get(0); i < points.get(1); i++){
            child2[i] = array1[i];
        }

        int childIndex = 0;
        int parentIndex = 0;
        int childIndex2 = 0;
        int parentIndex2 = 0;

        //while loop to fill in the gaps of child with values in parent 1
        while(childIndex < 8 && parentIndex < 8) {
            if (!Arrays.asList(child).contains(array2[parentIndex])) {
                if (child[childIndex] == null) {
                    child[childIndex] = array2[parentIndex];
                    childIndex++;
                    parentIndex++;
                } else {
                    childIndex++;
                }
            } else {
                if (child[childIndex] != null) {
                    childIndex++;
                    parentIndex++;
                } else {
                    parentIndex++;
                }
            }
        }
        //while loop to fill in the gaps of the child2 with values in parent2
        while(childIndex2 < 8 && parentIndex2 < 8) {
            if (!Arrays.asList(child2).contains(array2[parentIndex2])) {
                if (child2[childIndex2] == null) {
                    child2[childIndex2] = array2[parentIndex2];
                    childIndex2++;
                    parentIndex2++;
                } else {
                    childIndex2++;
                }
            } else {
                if (child2[childIndex2] != null) {
                    childIndex2++;
                    parentIndex2++;
                } else {
                    parentIndex2++;
                }
            }
        }

        int mutate;
        //determine if mutation should occur
        for (int num = 0; num < child.length - 2; num++) {
            //random number generated to see if mutation occurs
            mutate = rand.nextInt(100);
            //if mutate < MUTATION_RATE then swap current index with second index
            if(mutate < MUTATION_RATE){
                City city1 = child[num];
                child[num] = child[num + 1];
                child[num + 1] = city1;
            }
        }
        //same for loop as above, but for 2nd child
        for (int num = 0; num < child2.length - 2; num++) {
            mutate = rand.nextInt(100);
            if(mutate < MUTATION_RATE){
                City city1 = child2[num];
                child2[num] = child2[num + 1];
                child2[num + 1] = city1;
            }
        }
        Children children = new Children(child, child2);
        return children;
    }

}