package sample;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixExample {

    public static void main(String[] args) {
        AdjacencyMatrix am = new AdjacencyMatrix(100); //Room for 8 nodes (could use larger!)
        GraphNodeAM<Integer> e = new GraphNodeAM<Integer>(3,am);
        GraphNodeAM<Integer> t = new GraphNodeAM<Integer>(2,am);
        GraphNodeAM<Integer> y = new GraphNodeAM<Integer>(5,am);


        t.connectToNodeUndirectedAM(e);
        e.connectToNodeUndirectedAM(y);

        List<GraphNodeAM<?>> agenda=new ArrayList<>();
        agenda.add(t);
        traverseGraphBreadthFirst(agenda,null);
    }

    //Agenda list based breadth-first graph traversal (tail recursive)
    public static void traverseGraphBreadthFirst(List<GraphNodeAM<?>> agenda, List<GraphNodeAM<?>> encountered ){
        if(agenda.isEmpty()) return;
        GraphNodeAM<?> next=agenda.remove(0);
        System.out.println(next.data);
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(next);
        for(int i=0;i<next.mat.nodeCount;i++)
            if(next.mat.amat[next.nodeId][i] && !encountered.contains(next.mat.nodes[i]) &&
                    !agenda.contains(next.mat.nodes[i])) agenda.add(next.mat.nodes[i]); //Add children to end of agenda
        traverseGraphBreadthFirst(agenda, encountered ); //Tail call
    }
}
