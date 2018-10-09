//Class to create the Children object which holds 2 arrays of City objects representing the 2 children
//that will be created by the parent paths
public class Children {
    City[] firstChild;
    City[] secondChild;

    public Children() {
        this.firstChild = new City[10];
        this.secondChild = new City[10];
    }

    public Children(City[] firstChild, City[] secondChild) {
        this.firstChild = firstChild;
        this.secondChild = secondChild;
    }

    public City[] getFirstChild() {
        return firstChild;
    }

    public City[] getSecondChild() {
        return secondChild;
    }
}
