package graph;
import java.util.ArrayList;
import java.util.Objects;


public class HamiltoneanCycle {
    private final int totalWeight;
    private final ArrayList<Integer> path;


    public HamiltoneanCycle(int totalWeight, ArrayList<Integer> path){
        this.path = path;
        this.totalWeight = totalWeight;
    }
    public ArrayList<Integer> getPath() {
        return path;
    }


    @Override
    public String toString(){
        String pathString = path.toString().replace('[','{');
        pathString = pathString.replace(']','}');

        return pathString + ":" + totalWeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HamiltoneanCycle otherCycle = (HamiltoneanCycle) obj;
        return Objects.equals(path, otherCycle.getPath());
    }

}
