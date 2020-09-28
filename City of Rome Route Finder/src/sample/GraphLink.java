package sample;

public class GraphLink {
    public GraphNode2<?> destNode; //Could also store source node if required
    public int cost; //Other link attributes could be similarly stored
    public GraphLink(GraphNode2<?> destNode,int cost) {
        this.destNode=destNode;
        this.cost=cost;
    }
}

