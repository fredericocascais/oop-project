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

    public static HamiltonianCycle bestCycle(ArrayList<HamiltonianCycle> hamiltonianCycleArrayList, HamiltonianCycle bestHamiltonianCycle){
        for (HamiltonianCycle hamiltonian_cycle : hamiltonianCycleArrayList){
            if(bestHamiltonianCycle.getTotalWeight() == 0){
                bestHamiltonianCycle = new HamiltonianCycle(hamiltonian_cycle.getPath(),hamiltonian_cycle.getTotalWeight());
            }
            else if(bestHamiltonianCycle.getTotalWeight()>hamiltonian_cycle.getTotalWeight()){
                bestHamiltonianCycle = new HamiltonianCycle(hamiltonian_cycle.getPath(),hamiltonian_cycle.getTotalWeight());
            }
        }
        return bestHamiltonianCycle;
    }

    public static void topCycles(ArrayList<HamiltonianCycle> hamiltonian_cycles, ArrayList<HamiltonianCycle> top_hamiltonian_cycles, HamiltonianCycle bestHamiltonianCycle){
        for (HamiltonianCycle hamiltonian_cycle : hamiltonian_cycles){
            boolean add = true;
            if(top_hamiltonian_cycles.size()<5){
                for (HamiltonianCycle topHamiltonianCycle : top_hamiltonian_cycles) {
                    //check if the cycle we want to add is already a top cycle
                    if (hamiltonian_cycle.equals(topHamiltonianCycle)) {
                        add = false;
                        break;
                    }
                }
                //if its not, add it
                if(add) {
                    if (!hamiltonian_cycle.equals(bestHamiltonianCycle)) {
                        top_hamiltonian_cycles.add(hamiltonian_cycle);
                    }
                }
            }
            else for (int i=0; i<top_hamiltonian_cycles.size(); i++){
                if (hamiltonian_cycle.getTotalWeight()<top_hamiltonian_cycles.get(i).getTotalWeight()){
                    for (HamiltonianCycle topHamiltonianCycle : top_hamiltonian_cycles) {
                        //check if the cycle we want to add is already a top cycle
                        if (hamiltonian_cycle.equals(topHamiltonianCycle)) {
                            add = false;
                            break;
                        }
                    }
                    //if its not, add it
                    if(add) {
                        if (!hamiltonian_cycle.equals(bestHamiltonianCycle)) {
                            top_hamiltonian_cycles.remove(top_hamiltonian_cycles.get(i));
                            top_hamiltonian_cycles.add(hamiltonian_cycle);
                        }
                    }
                }
            }
        }

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

