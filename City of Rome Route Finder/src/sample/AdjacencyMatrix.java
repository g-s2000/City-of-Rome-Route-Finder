package sample;

import java.lang.reflect.Array;

public class AdjacencyMatrix {
    public boolean[][] amat;
    public GraphNodeAM<?>[] nodes;
    public int nodeCount=0;

    public AdjacencyMatrix(int size){
        amat = new boolean [size][size]; //All false values by default
        nodes = (GraphNodeAM<?>[]) Array.newInstance(GraphNodeAM.class, size);
    }

    public boolean[][] getAmat() {
        return amat;
    }

    public void setAmat(boolean[][] amat) {
        this.amat = amat;
    }

    public GraphNodeAM<?>[] getNodes() {
        return nodes;
    }

    public void setNodes(GraphNodeAM<?>[] nodes) {
        this.nodes = nodes;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }


}
