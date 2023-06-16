package graph;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The HamiltonianCycle class represents a Hamiltonian cycle in a graph.
 * It contains information such as the total weight of the cycle and the path of nodes.
 * It implements the Comparable interface to enable comparison between cycles based on their total weight.
 */
public class HamiltonianCycle implements Comparable<HamiltonianCycle>{
    /**
     * The total weight of the path.
     */
    private final int totalWeight;

    /**
     * The list of node IDs representing the path.
     */
    private final ArrayList<Integer> path;

    /**
     * Constructs a HamiltonianCycle object with the specified total weight and path.
     *
     * @param totalWeight The total weight of the cycle.
     * @param path The path of nodes in the cycle.
     */
    public HamiltonianCycle(int totalWeight, ArrayList<Integer> path){
        this.path = path;
        this.totalWeight = totalWeight;
    }

    /**
     * Gets the path of nodes in the cycle.
     *
     * @return The path of nodes in the cycle.
     */
    public ArrayList<Integer> getPath() {
        return path;
    }

    /**
     * Gets the total weight of the cycle.
     *
     * @return The total weight of the cycle.
     */
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Returns a string representation of the Hamiltonian cycle.
     *
     * @return A string representation of the Hamiltonian cycle.
     */
    @Override
    public String toString(){
        ArrayList<Integer> stringablePath = new ArrayList<>();
        for (int node: path) {
            int stringableNode = node + 1;
            stringablePath.add(stringableNode);
        }
        String pathString = stringablePath.toString().replace('[','{');
        pathString = pathString.replace(']','}');

        return pathString + ":" + totalWeight;
    }

    /**
     * Checks if this Hamiltonian cycle is equal to the specified object.
     *
     * @param obj The object to compare against.
     * @return true if the cycles are equal, false otherwise.
     */
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

    /**
     * Compares this Hamiltonian cycle with the specified cycle based on their total weight.
     *
     * @param o The Hamiltonian cycle to compare against.
     * @return A negative integer if this cycle is less than the specified cycle,
     *         zero if they are equal, or a positive integer if this cycle is greater.
     */
    @Override
    public int compareTo(HamiltonianCycle o){
        return Integer.compare(this.totalWeight, o.getTotalWeight());
    }
}
