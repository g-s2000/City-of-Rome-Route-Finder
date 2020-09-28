package sample;

import java.util.ArrayList;
import java.util.List;

public class GraphNodeAL<T> {
    public T data;
    public List<GraphNodeAL<T>> adjList=new ArrayList<>(); //Could use any List implementation
    public GraphNodeAL(T data) {
        this.data=data;
    }
    public void connectToNodeDirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
    }
    public void connectToNodeUndirected(GraphNodeAL<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public static void main(String[] args) {
        //Create undirected graph
        GraphNodeAL<Integer> a=new GraphNodeAL<>(2);
        GraphNodeAL<Integer> b=new GraphNodeAL<>(5);
        GraphNodeAL<Integer> c=new GraphNodeAL<>(6);
        GraphNodeAL<Integer> d=new GraphNodeAL<>(7);
        GraphNodeAL<Integer> e=new GraphNodeAL<>(1);
        GraphNodeAL<Integer> f=new GraphNodeAL<>(0);
        GraphNodeAL<Integer> g=new GraphNodeAL<>(5);
        GraphNodeAL<Integer> h=new GraphNodeAL<>(2);
        a.connectToNodeUndirected(b); //Pairs either way to connect undirected
        a.connectToNodeUndirected(c);
        b.connectToNodeUndirected(c);
        b.connectToNodeUndirected(g);
        c.connectToNodeUndirected(d);
        g.connectToNodeUndirected(e);
        d.connectToNodeUndirected(e);
        f.connectToNodeUndirected(e);
        e.connectToNodeUndirected(h);

        System.out.println("\nAgenda based breadth first traversal starting at Coconut");
        System.out.println("-------------------------------------------------");
        List<GraphNodeAL<?>> agenda=new ArrayList<>();
        agenda.add(f);
        traverseGraphBreadthFirst(agenda,null);
    }


    //Agenda list based breadth-first graph traversal (tail recursive)
    public static void traverseGraphBreadthFirst(List<GraphNodeAL<?>> agenda, List<GraphNodeAL<?>> encountered ){
        if(agenda.isEmpty()) return;
        GraphNodeAL<?> next=agenda.remove(0);
        System.out.println(next.data);
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for(GraphNodeAL<?> adjNode : next.adjList)
            if(!encountered.contains(adjNode) && !agenda.contains(adjNode)) agenda.add(adjNode); //Add children to
//end of agenda
        traverseGraphBreadthFirst(agenda, encountered ); //Tail call
    }


}
