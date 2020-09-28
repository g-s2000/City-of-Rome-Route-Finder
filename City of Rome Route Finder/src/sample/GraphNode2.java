package sample;

import java.util.ArrayList;
import java.util.List;

public class GraphNode2<T> extends XMLNodes {
        public T data;
        public int nodeValue=Integer.MAX_VALUE;
        public int X;
        public int Y;
        public String landmarkName;
        public List<GraphLink> adjList=new ArrayList<>(); //Adjacency list now contains link objects = key!
        //Could use any concrete List implementation
        public GraphNode2(T data) {
            this.data=data;
        }
        public GraphNode2(){

        }

        public void connectToNodeDirected(GraphNode2<T> destNode,int cost) {
          adjList.add( new GraphLink(destNode,cost) ); //Add new link object to source adjacency list
        }
        public void connectToNodeUndirected(GraphNode2<T> destNode,int cost) {
            adjList.add( new GraphLink(destNode,cost) ); //Add new link object to source adjacency list
            destNode.adjList.add( new GraphLink(this,cost) ); //Add new link object to destination adjacency list
        }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public void setCoordinates(int x, int y){
            X= x;
            Y= y;
    }

    public List<GraphLink> getAdjList() {
        return adjList;
    }

    public void setAdjList(List<GraphLink> adjList) {
        this.adjList = adjList;
    }

    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    @Override
    public String toString() {
        return "GraphNode2{" +
                "data=" + data +
                ", nodeValue=" + nodeValue +
                ", X=" + X +
                ", Y=" + Y +
                ", landmarkName='" + landmarkName + '\'' +
                ", adjList=" + adjList +
                '}';
    }
}


