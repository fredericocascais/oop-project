package graph;
import java.util.ArrayList;
import java.util.Objects;

public class HamiltonianCycle{

    private final ArrayList<Integer> path;

    private final int totalWeight;



    public HamiltonianCycle(ArrayList<Integer> path, int totalWeight) {

        this.path = new ArrayList<>(path);
        this.totalWeight = totalWeight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }
    @Override
    public String toString(){
        ArrayList<Integer> printPath = new ArrayList<>(path);
        printPath.replaceAll(integer -> integer + 1);

        return printPath.toString().replace("[","{").replace("]","}") + ":" + totalWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HamiltonianCycle otherCycle = (HamiltonianCycle) obj;
        return Objects.equals(path, otherCycle.getPath());
    }

}

