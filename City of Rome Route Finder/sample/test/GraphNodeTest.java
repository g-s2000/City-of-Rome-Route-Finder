import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.CustomGraph;
import sample.GraphNode2;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GraphNodeTest {

GraphNode2 n1, n2, n3;
CustomGraph graph;
ArrayList list= new ArrayList();

    @BeforeEach
    public void setUp() throws Exception { //sets up 4 new node objects before each test.
   n1= new GraphNode2("Node 1");
   n2= new GraphNode2("Node 2");
   n3= new GraphNode2("Node 3");
   graph= new CustomGraph();
    }

    @AfterEach
    public void tearDown() throws Exception { //sets all test node values to null after each test.

        n1=n2=n3=null;
        graph= null;
    }

    @Test //tests that correct data is retrieved from nodes.
    public void getData(){
        assertEquals("Node 1", n1.getData());
        assertEquals("Node 3", n3.getData());
        assertNotEquals("Node 2", n1.getData());
    }

    @Test //tests that x and y coordinates of nodes are set properly.
    public void getCoordinates(){
        n1.setX(30);
        n1.setY(40);
        n2.setX(50);
        n2.setY(400);
        assertEquals(30, n1.getX());
        assertEquals(40, n1.getY());
        assertEquals(400, n2.getY());
        assertNotEquals(20, n2.getX());
    }

    @Test //tests that landmark name is set and retrieved properly.
    public void getLandmarkName(){
    n1.setLandmarkName("Eiffel Tower");
    n2.setLandmarkName("Big Ben");
    n3.setLandmarkName("Machu Picchu");
    assertEquals("Eiffel Tower", n1.getLandmarkName());
    assertEquals("Machu Picchu", n3.getLandmarkName());
    assertNotEquals("London Eye", n2.getLandmarkName());
    }

    public void dijkstraTest(){
        n1.connectToNodeUndirected(n2, 1);
        //graph.findCheapestPathDijkstra(n1, "Big Ben");
    }

    public void bfsTest(){
        list.add(n1);
        list.add(n2);
        list.add(n3);
     //graph.findPathBreadthFirst(list, n2,)
    }


}
