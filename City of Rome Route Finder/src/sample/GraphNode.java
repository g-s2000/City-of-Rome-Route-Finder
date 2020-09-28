package sample;



import java.util.ArrayList;
import java.util.List;

public class GraphNode<T> {
    public T data;
    public List<GraphNode<T>> adjList=new ArrayList<>(); //Could use any List implementation
    public GraphNode(T data) {
        this.data=data;
    }
    public void connectToNodeDirected(GraphNode<T> destNode) {
        adjList.add(destNode);
    }
    public void connectToNodeUndirected(GraphNode<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }
}
