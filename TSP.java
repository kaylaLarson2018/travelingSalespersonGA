//@Author Kayla Larson

//This is the main class that runs the genetic algorithm to solve the traveling salesman problem
//This program takes a set of cities and their corresponding locations (x and y coordinates) and finds
//the shortest path to take in order to hit every city once. There are four different methods being used
//to solve this problem, Top down single point crossover, top down double point crossover, tournament
//single point crossover, and tournament double point crossover.
//Each one is run for 1000 generations and the final distance is then calculated.
//In the future I would like to incorporate the google maps API so a user could choose different cities
//and then calculate the shortest distance the person could travel to hit every city.


public class TSP {
    private static int NUM_GENERATIONS = 1000;
    //Create 4 new GA objects and run each genetic algorithm
    public static void main(String args[]) {

        GA ga = new GA();
        ga.generateFirstSetOfPaths();
        System.out.print("TOP DOWN SINGLE POINT CROSSOVER: ");
        ga.runTopDownSinglePt(NUM_GENERATIONS);
        System.out.print(" 1 " + NUM_GENERATIONS + " generations ");
        ga.printList();
        System.out.println(" " + ga.getFinalDistance());

        GA ga2 = new GA();
        ga2.generateFirstSetOfPaths();
        System.out.println("TOP DOWN DOUBLE POINT CROSSOVER: ");
        ga2.runTopDownDoublePt(NUM_GENERATIONS);
        System.out.print(" 2 " + NUM_GENERATIONS + " generations ");
        ga2.printList();
        System.out.println(" " + ga2.getFinalDistance());

        GA ga3 = new GA();
        ga3.generateFirstSetOfPaths();
        System.out.println("TOURNAMENT SINGLE POINT CROSSOVER: ");
        ga3.runTournamentSinglePt(NUM_GENERATIONS);
        System.out.print(" 3 " + NUM_GENERATIONS + " generations ");
        ga3.printList();
        System.out.println(" " + ga3.getFinalDistance());

        GA ga4 = new GA();
        ga4.generateFirstSetOfPaths();
        System.out.println("TOURNAMENT DOUBLE POINT CROSSOVER: ");
        ga4.runTournamentDoublePt(NUM_GENERATIONS);
        System.out.print(" 4 " + NUM_GENERATIONS + " generations ");
        ga4.printList();
        System.out.println(" " + ga4.getFinalDistance());

        System.out.println();
        System.out.println();
        System.out.println("Run   Num Gens    Circuit  Cost of circuit");
        System.out.print(" 1 " + NUM_GENERATIONS + " gens  ");
        ga.printList();
        System.out.println(" " + ga.getFinalDistance());
        System.out.print(" 2 " + NUM_GENERATIONS + " gens  ");
        ga2.printList();
        System.out.println(" " + ga2.getFinalDistance());
        System.out.print(" 3 " + NUM_GENERATIONS + " gens  ");
        ga3.printList();
        System.out.println(" " + ga3.getFinalDistance());
        System.out.print(" 4 " + NUM_GENERATIONS + " gens  ");
        ga4.printList();
        System.out.println(" " + ga4.getFinalDistance());


    }

}