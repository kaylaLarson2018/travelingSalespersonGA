//This class creates the GA object which holds an array list of possible paths between the 8 cities
//This class contains the methods to run the pairing and crossover algorithms that generate the
//child paths. The pairing algorithms are top down and tournament, top down pairs up the adjacent
//paths and tournament randomly pairs 2 paths. The crossover algorithms are single point and double point.
//Single point switches the cities between 2 parents at one point and double point switches the cities
//at two points. This class also contains the elimination method which sorts the paths based on distance
//and eliminates the half of the paths that have the longest distance. We will continually run the pairing and
//crossover algorithms in combination with the elimination method for a set number of generations.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GA {
    ArrayList<City[]> paths = new ArrayList<>(); //array list holding unique sets of paths between the cities
    double finalDistance; //variable to hold the distance of the path between each city


    //create 8 cities to become the first path
    //a path is a set of cities that the "salesman" will stop at starting with index 0 and ending at index 7
    public void generateFirstSetOfPaths(){
        City[] parent1 = new City[8]; //Array to hold the cities we will be creating our path with
        //Create the initial path
        parent1[0] = (new City(50,50, "a"));
        parent1[1] = (new City(20, 89, "b"));
        parent1[2] = (new City(50, 60, "c"));
        parent1[3] = (new City(2,3, "d"));
        parent1[4] = (new City(20,79, "e"));
        parent1[5] = (new City(69, 30, "f"));
        parent1[6] = (new City(48,0, "g"));
        parent1[7] = (new City(30,99, "h"));

        //add this path to the array list of paths
        paths.add(parent1);
        int counter = 0;
        //generate 19 more paths so that we have a population of 20 possible paths
        while(counter < 19){
            //copy parent 1 into parent 2
            City[] parent2;
            parent2 = parent1.clone();

            //shuffle parent2 so that it is different than parent1
            Collections.shuffle(Arrays.asList(parent2));
            boolean doNotAdd = false; //boolean is true if parent2 is not a unique path (not already in paths)
            //loop through the array list and see if parent 2 is already in the list,
            //if it is not, we add it
            for (City[] path:paths) {
                if(Arrays.equals(path,parent2)){
                    doNotAdd = true;
                }
            }
            if(!doNotAdd){
                paths.add(parent2);//add the parent2 path to paths
                counter++;//increment counter
            }
        }
        Cities cities = new Cities(); //create new Cities object to calculate distances of a path
        System.out.println("************************");
        System.out.println("Initial distance: " + cities.calculateDistanceForAPath(paths.get(0)));
        System.out.println(Arrays.toString(paths.get(0)));
        System.out.println("************************");
    }

    //method to generate children through top down pairing and double point crossover
    //Each set of parents will generate 2 children that will be held in a ChildSet object
    //A child will only be added to paths if it is not already in paths
    public void generateChildrenDoublePoint(){
        Children childSet; //object to hold the child that is generated by 2 parents
        DoublePtCrossover doublePtCross = new DoublePtCrossover(); //create a DoublePtCrossover so we can run it
        int pairs = paths.size()/2;

        int counter = 0; //variable to hold number of children added to paths
        while(counter < (pairs * 2)){ //We want to double the number of paths we have
            //generate 2 children from the first two paths
            childSet = doublePtCross.doubleCrossover(paths.get(counter),paths.get(counter+1));
            //ensure the 2 children are not equal
            if(!Arrays.equals(childSet.getFirstChild(),childSet.getSecondChild())) {
                boolean doNotAdd = false;
                //check if child1 and child2 are in the list of paths
                for (City[] path:paths) {
                    if(Arrays.equals(path,childSet.getFirstChild())){
                        doNotAdd = true;
                    }
                }
                for (City[] path:paths) {
                    if(Arrays.equals(path,childSet.getSecondChild())){
                        doNotAdd = true;
                    }
                }
                //if child1 and child2 are not in the list of paths we can add it to the array list, paths
                if(!doNotAdd){
                    paths.add(childSet.getSecondChild());
                    paths.add(childSet.getFirstChild());
                    //increment counter by 2 so that each parent is only mated once
                    //and to indicate that we have added 2 children to paths
                    counter = counter + 2;
                }
            }
        }

    }

    //generate children through top down pairing and single point crossover
    //Each set of parents will generate 2 children that will be held in a ChildSet object
    //A child will only be added to paths if it is not already in paths
    public void generateChildrenSinglePoint() {
        Children childSet; //Create a childset object to hold the children generated by a parent set
        singlePtCrossover singlePoint = new singlePtCrossover(); //create a singlePtCrossover object so we can run it
        int pairs = paths.size() / 2;

        int counter = 0; //counter to indicate the number of children added to paths
        while(counter < (pairs * 2)){//we want to double the number of paths we have

            //generate 2 children from each set of parents
            childSet = singlePoint.crossover(paths.get(counter),paths.get(counter+1)); //get the first 2 children
            if(!Arrays.equals(childSet.getFirstChild(),childSet.getSecondChild())) { //make sure the children are not equal
                boolean doNotAdd = false;
                //If the children paths are not already existing in our set of paths, add them
                for (City[] path:paths) {
                    if(Arrays.equals(path,childSet.getFirstChild())){
                        doNotAdd = true;
                    }
                }
                for (City[] path: paths) {
                    if(Arrays.equals(path,childSet.getSecondChild())){
                        doNotAdd = true;
                    }
                }
                if(!doNotAdd){
                    //add the child paths
                    paths.add(childSet.getFirstChild());
                    paths.add(childSet.getSecondChild());
                    counter = counter + 2; //increment the counter
                }
            }
        }
    }

    //generate the children through tournament pairing and double point crossover
    //Each set of parents will generate 2 children that will be held in a ChildSet object
    //A child will only be added to paths if it is not already in paths
    public void generateChildrenDoublePointTournament(){
        Children childSet;//object to hold 2 children
        City[] parent1; //array to hold 1 parent
        City[] parent2; //array to hold another parent
        int pairs = paths.size() / 2;
        Random random = new Random(); //create a random object since tournament pairing requires randomized pairing
        int randomNum; //hold randomNum, which will be the index of the parent we choose
        int randBound = pairs * 2;
        DoublePtCrossover doublePt = new DoublePtCrossover(); //create a DoublePtCrossover object so we can run its methods
        int counter = 0; //variable to hold number of children added
        ArrayList<City[]> pathsCopy = new ArrayList<>(); //arraylist to hold copy of paths so we don't alter the original paths arraylist
        pathsCopy.addAll(paths);
        while (counter < (pairs * 2)){
            randomNum = random.nextInt(randBound);//generate randome number to determine which path becomes parent1
            parent1 = pathsCopy.get(randomNum);
            pathsCopy.remove(randomNum); //remove parent1 from pathscopy so we don't grab it again
            randBound = pathsCopy.size();
            randomNum = random.nextInt(randBound);
            parent2 = pathsCopy.get(randomNum); //get parent2
            pathsCopy.remove(randomNum);//remove parent2 from pathscopy so we don't grab it again
            randBound = pathsCopy.size();
            childSet = doublePt.doubleCrossover(parent1, parent2);//generate children
            if(!Arrays.equals(childSet.getFirstChild(),childSet.getSecondChild())) {//check if children are equal
                boolean doNotAdd = false;
                //check if the children already exist in paths
                for (City[] path : paths) {
                    if (Arrays.equals(path, childSet.getFirstChild())) {
                        doNotAdd = true;
                    }
                }
                for (City[] path : paths) {
                    if (Arrays.equals(path, childSet.getSecondChild())) {
                        doNotAdd = true;
                    }
                }
                if (!doNotAdd) {
                    //the children are unique so we add them to paths
                    paths.add(childSet.getFirstChild());
                    paths.add(childSet.getSecondChild());
                    counter = counter + 2;
                }else{
                    //the children aren't unique so we put the parents back in the pathscopy array list
                    randBound = randBound + 2;
                    pathsCopy.add(parent1);
                    pathsCopy.add(parent2);
                }
            }else{
                //the children are the same so we put the parents back in the pathscopy array list
                randBound = randBound + 2;
                pathsCopy.add(parent1);
                pathsCopy.add(parent2);
            }
        }
    }

    //generate the children through tournament pairing and single point crossover
    //Each set of parents will generate 2 children that will be held in a ChildSet object
    //A child will only be added to paths if it is not already in paths
    public void generateChildrenSinglePointTournament(){
        Children childSet; //create childset object to hold the children generated by each parent set
        City[] parent1; //hold parent1 path
        City[] parent2; //hold parent2 path
        int pairs = paths.size() / 2;
        //need to generate random number do determine which paths are paired as parents
        Random random = new Random();
        int randomNum;//variable to hold the random number generated as the index
        int randBound = pairs * 2;
        singlePtCrossover singlePt = new singlePtCrossover(); //create singlePtCrossover object so we can run the singlept crossover method
        int counter = 0;//variable to hold the number of children added to the paths
        ArrayList<City[]> pathsCopy = new ArrayList<>(); //array list to hold a copy of our paths
        pathsCopy.addAll(paths);
        while (counter < (pairs * 2)){//we want to double the number of paths we have
            randomNum = random.nextInt(randBound); //use randomNum to randomize pairing of paths
            parent1 = pathsCopy.get(randomNum); //grab a path
            pathsCopy.remove(randomNum); //remove that path from pathscopy so we do not grab it again
            randBound = pathsCopy.size();
            randomNum = random.nextInt(randBound); //generate new random number for index of parent2
            parent2 = pathsCopy.get(randomNum);//grab another path to be parent2
            pathsCopy.remove(randomNum);//remove that path from pathscopy so we don't grab it again
            randBound = pathsCopy.size();
            childSet = singlePt.crossover(parent1, parent2);//put the children from the two random parents in childset
            if(!Arrays.equals(childSet.getFirstChild(),childSet.getSecondChild())) {//make sure the children are not equal
                //make sure the children are not already existing paths, if they are not add them
                boolean doNotAdd = false;
                for (City[] path : paths) {
                    if (Arrays.equals(path, childSet.getFirstChild())) {
                        doNotAdd = true;
                    }
                }
                for (City[] path : paths) {
                    if (Arrays.equals(path, childSet.getSecondChild())) {
                        doNotAdd = true;
                    }
                }
                if (!doNotAdd) {
                    //add the children to our set of paths
                    paths.add(childSet.getFirstChild());
                    paths.add(childSet.getSecondChild());
                    counter = counter + 2;
                }else{
                    //add the parents back since we did not find 2 unique children paths
                    randBound = randBound + 2;
                    pathsCopy.add(parent1);
                    pathsCopy.add(parent2);
                }
            }else{
                //add the parents back since we did not find 2 unique children paths
                randBound = randBound + 2;
                pathsCopy.add(parent1);
                pathsCopy.add(parent2);
            }
        }
    }

    //eliminate the half of the paths with the longest traversal distance
    public void elimination(){
        Cities cities = new Cities();
        int size = paths.size()/2; //set size to half of the current number of paths we have
        //bubble sort algorithm to sort from least to greatest distance
        for (int i = 0; i < paths.size() - 1; i++) {
            for(int j = 0; j < paths.size() - i - 1; j++){
                double distance1 = cities.calculateDistanceForAPath(paths.get(j));
                double distance2 = cities.calculateDistanceForAPath(paths.get(j + 1));
                if(distance1 < distance2){
                    City[] temp = Arrays.copyOf(paths.get(j), 8);
                    paths.set(j, paths.get(j+1));
                    paths.set(j+1, temp);
                }
            }
        }
        for(int i = 0; i < size; i++){//remove half of the paths
            paths.remove(i);
        }
    }

    //run the method to generate the children through top down pairing and double point crossover
    //then run the elimination method and do this for the specified number of generations
    public void runTopDownDoublePt(int numberOfGenerations){
        System.out.println("Running top down double point crossover with " + numberOfGenerations + " generations...");
        Cities cities = new Cities();
        for(int i = 0; i < numberOfGenerations; i++){
            generateChildrenDoublePoint();
            elimination();
        }
        City[] path = paths.get(paths.size() - 1);//grab the last path in the list since it should have the shortest distance
        for (City city:path) {
            System.out.print(city.getPoint());//display the cities in order
        }
        finalDistance = cities.calculateDistanceForAPath(paths.get(paths.size() - 1));
        System.out.println("  Final distance = " + finalDistance);
    }
    //run the method to generate the children through top down pairing and single point crossover
    //then run the elimination method and do this for the specified number of generations
    public void runTopDownSinglePt(int numberOfGenerations){
        System.out.println("Running top down single point crossover with " + numberOfGenerations + " generations...");
        Cities cities = new Cities();
        for(int i = 0; i < numberOfGenerations; i++){
            generateChildrenSinglePoint();
            elimination();
        }
        City[] path = paths.get(paths.size() - 1);//grab the last path in the list since it should have the shortest distance
        for (City city:path) {
            System.out.print(city.getPoint());//display the cities in order
        }
        finalDistance = cities.calculateDistanceForAPath(paths.get(paths.size() - 1));
        System.out.println("  Final distance = " + finalDistance);
    }
    //run the method to generate the children through tournament pairing and single point crossover
    //then run the elimination method and do this for the specified number of generations
    public void runTournamentSinglePt(int numberOfGenerations){
        System.out.println("Running Tournament Single point crossover with " + numberOfGenerations + " generations...");
        Cities cities = new Cities();
        for(int i = 0; i < numberOfGenerations; i++){
            generateChildrenSinglePointTournament();
            elimination();
        }
        City[] path = paths.get(paths.size() - 1);//grab the last path in the list since it should have the shortest distance
        for (City city:path) {
            System.out.print(city.getPoint());//display the cities in order
        }
        finalDistance = cities.calculateDistanceForAPath(paths.get(paths.size() - 1));
        System.out.println("  Final distance = " + finalDistance);
    }
    //run the method to generate the children through tournament pairing and double point crossover
    //then run the elimination method and do this for the specified number of generations
    public void runTournamentDoublePt(int numberOfGenerations){
        System.out.println("Running Tournament Double Pt crossover with " + numberOfGenerations + " generations...");
        Cities cities = new Cities();
        for(int i = 0; i < numberOfGenerations; i++){
            generateChildrenDoublePointTournament();
            elimination();
        }
        City[] path = paths.get(paths.size() - 1);//grab the last path in the list since it should have the shortest distance
        for (City city:path) {
            System.out.print(city.getPoint());//display the cities in order
        }
        finalDistance = cities.calculateDistanceForAPath(paths.get(paths.size() - 1));
        System.out.println("  Final distance = " + finalDistance);
    }

    public void printList(){
        City[] path = paths.get(paths.size() - 1);
        for (City city:path) {
            System.out.print(city.getPoint());
        }
    }

    public double getFinalDistance() {
        return finalDistance;
    }
}