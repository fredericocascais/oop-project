package graph;

public class Edge implements IEdge{
    private final int destination;

    private final double weight;
    private int id;
    private static int count = 0;
    public Edge(int dest, double weight){
        this.destination = dest;
        this.id = count;
        this.weight = weight;
        count++;
    }

    public int getDestination() {
        return destination;
    }

    public int getId() {

        //System.out.println(id);
        return id;
    }

    public double getWeight() {
        return weight;
    }

   /* @Override
    public String toString(){

        return " --> " + (destination + 1) + "\n  " + weight;
    }*/
   @Override
   public String toString(){

       return "edge id: " + id+ " with weight = " +weight;
   }
}
