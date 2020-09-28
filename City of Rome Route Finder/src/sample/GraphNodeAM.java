package sample;

public class GraphNodeAM<T> {
    public T data;
    public AdjacencyMatrix mat;
    public int nodeId; //effectively functions as the "index" number of the node
    public int cost=1; //cost of travelling to the node.
    public GraphNodeAM(T data,AdjacencyMatrix mat){
        this.data=data;
        this.mat=mat;
        mat.nodes[nodeId=mat.nodeCount++]=this; //Add this node to adjacency matrix and record id
        this.cost+=1; //cost increments by 1??
    }

    public void connectToNodeDirectedAM(GraphNodeAM<T> destNode) {
        mat.amat[nodeId][destNode.nodeId]=true;
    }

    public void connectToNodeUndirectedAM(GraphNodeAM<T> destNode) {
        mat.amat[nodeId][destNode.nodeId]=mat.amat[destNode.nodeId][nodeId]=true;
    }

    public void connectToNodeUndirectedAMByIndex(GraphNodeAM<T> beginningNode, int beginningNodeId, GraphNodeAM<T> destNode, int destNodeId){
        if(destNodeId==destNode.getNodeId() && beginningNodeId== beginningNode.getNodeId()){
            mat.amat[nodeId][destNode.nodeId]=mat.amat[destNode.nodeId][nodeId]=true;
        }
        else{
            System.out.println("Please enter valid indexes.");
        }

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AdjacencyMatrix getMat() {
        return mat;
    }

    public void setMat(AdjacencyMatrix mat) {
        this.mat = mat;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "GraphNodeAM{" +
                "data=" + data +
                ", mat=" + mat +
                ", nodeId=" + nodeId +
                ", cost=" + cost +
                '}';
    }
}
