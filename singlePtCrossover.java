//This class generates children using single point crossover
//This means that a random index is chosen and the cities between index 0 and the first point
//and the first point and the last index are swapped. Then the gaps in each child path are
//filled in.
import java.util.Arrays;
import java.util.Random;

public class singlePtCrossover {
    public static int MUTATION_RATE = 2;//2 percent of the time we will randomly swap to cities to add some more variation
    public Children crossover(City[] p1, City[] p2){
        Random rand = new Random(); //create a random integer so we can grab a random point to do the crossover on
        int size = p1.length;
        int point1 = rand.nextInt(size);
        City[] child = new City[8];//create city array to hold child 1
        City[] child2 = new City[8];//create city array to hold child 2
        for(int i = 0; i < point1; i++){//set child1 equal to parent 1 from indices 0 to point1
            child[i] = p1[i];
        }
        for(int i = point1; i < child2.length; i++){//set child 2 equal to parent 2 from indeces point1 to the last index
            child2[i] = p1[i];
        }
        int childIndex1 = 0;
        int parentIndex1 = 0;
        int childIndex2 = 0;
        int parentIndex2 = 0;
        while(childIndex1 < 8 && parentIndex1 < 8){//cycle through child1 and fill in the gaps
            if(!Arrays.asList(child).contains(p2[parentIndex1])){
                if(child[childIndex1] == null) {
                    child[childIndex1] = p2[parentIndex1];
                    childIndex1++;
                    parentIndex1++;
                }else {
                    childIndex1++;
                }
            }else {
                if(child[childIndex1] != null){
                    childIndex1++;
                    parentIndex1++;
                }else {
                    parentIndex1++;
                }
            }
        }

        while(childIndex2 < 8 && parentIndex2 < 8){//cycle through child2 and fill in the gaps
            if(!Arrays.asList(child2).contains(p2[parentIndex2])){
                if(child2[childIndex2] == null) {
                    child2[childIndex2] = p2[parentIndex2];
                    childIndex2++;
                    parentIndex2++;
                }else {
                    childIndex2++;
                }
            }else {
                if(child2[childIndex2] != null){
                    childIndex2++;
                    parentIndex2++;
                }else {
                    parentIndex2++;
                }
            }
        }

        int mutate;//check if mutation should occur, there is a 2% chance for every index
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

        Children children = new Children(child, child2);//create the child set and return
        return children;
    }
}
