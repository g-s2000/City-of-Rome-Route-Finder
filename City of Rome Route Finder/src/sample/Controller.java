package sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class Controller {
    public static Image image;
    public WritableImage image2;
    @FXML ImageView imgImage= new ImageView();
    @FXML MenuItem load;
    GraphNode2 startingGraphNode;
    GraphNode2 endingGraphNode;
    @FXML MenuItem plot;
    @FXML MenuItem exit;
    @FXML MenuItem save;
    @FXML MenuItem loadXML;
    @FXML Button setPoint= new Button();
    @FXML Button setEnd= new Button();
    @FXML CheckBox BFS;
    @FXML CheckBox Dijkstra;
    @FXML ComboBox<String> StartCombo;
    @FXML ComboBox<String> EndCombo;
    @FXML
    Text OutputDij;

    boolean[] white;
    public ArrayList<GraphNode2> validPath= new ArrayList<>(); //Changed to AL2
    @FXML Button printArray= new Button();
    public boolean firstClick = false;
    public boolean secondClick = false;
    GraphNode2 firstPixel;
    GraphNode2 currentPixel;
    GraphNode2  endingGraphNodeActual;

    private PixelReader pixelReader;
    private Landmark landmark;
    private XMLNodes xmlNodes;
    int startPoint = 0;
    int endPoint = 0;


    @FXML
    public void initialize() throws Exception {
        XMLNodes.addSignificantNodes();
        if(StartCombo != null) {
            handleCombo();
        }

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { //handles the drawing of new start and endpoint nodes.
                //   boolean flag= false;
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                double startXCoord = event.getSceneX(); //starting node's x coordinate is the point of the screen clicked.
                double startYCoord = event.getSceneY(); //starting node's y coordinate is the point of the screen clicked.
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && !firstClick) { //if mouse is pressed....
                    System.out.println("Start X:" + event.getX()); //starting x is event.getX()
                    System.out.println("Start Y:" + event.getY()); //starting y is event.getY()
                }
                else if(event.getEventType() == MouseEvent.MOUSE_RELEASED && !firstClick) { //if mouse is released....
                    Circle c = new Circle(); //a new circle is drawn
                    c.setCenterX(startXCoord); //centerX value of circle is set to startXCoord.
                    c.setCenterY(startYCoord); //centerY value is set to startYCoord
                    c.setRadius(5.00); //radius is set to 5 (arbitrary value)
                    c.setFill(Color.PINK); //fill is set to red
                    c.setStroke(Color.BLACK); //stroke outline is set to black

                    ((Pane) imgImage.getParent()).getChildren().add(c); //circle is added to pane.

                    Bounds b = imgImage.getLayoutBounds();
                    double xScale = b.getWidth()/width;
                    double yScale = b.getHeight()/height;

                    startXCoord /= xScale;
                    startYCoord /= yScale;

                    int yCoord = (int) startXCoord;
                    int xCoord = (int) startYCoord;
                    System.out.println(xCoord);
                    System.out.println(yCoord);

                    startPoint = (xCoord + width*yCoord); //Creates the index of the selected pixel

                    firstClick = true;
                    System.out.println(startPoint);
                }
            }

        };
        imgImage.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseHandler); //eventFilter is added to ImageView when mouse is pressed.
        imgImage.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseHandler); //eventFilter is added to ImageView when mouse is released.
    }

    @FXML
    public void handleCombo() {
        for(int i = 0; i < XMLNodes.nodes.size(); i++) {
            StartCombo.getItems().add(XMLNodes.landmarks[i].getLandmarkName()); //Fills the ComboBox with the names of landmarks
            EndCombo.getItems().add(XMLNodes.landmarks[i].getLandmarkName());
        }
    }

    @FXML
    public void drawEndPoint(){ //handles drawing the end point of the clickable path.

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();
                double   endXCoord = event.getSceneX(); //x Coord is equal to x coordinate of mouse on screen on click.
                double   endYCoord = event.getSceneY(); //y coord is equal to y coordinate of mouse on screen on click.
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && !secondClick) {//if mouse is pressed....
                    if(!imgImage.isPreserveRatio()) {
                        imgImage.setPreserveRatio(true);
                        System.out.println("End X:" + event.getX()); //end X is event.getX();
                        System.out.println("End Y:" + event.getY()); //end Y is event.getY(); this is for printing/testing purposes.
                    }
                }
                else if(event.getEventType()== MouseEvent.MOUSE_RELEASED && !secondClick){ //if mouse is released....
                    Circle c = new Circle(); //new circle is drawn
                    c.setCenterX(endXCoord); //circle centerX is endXCoord
                    c.setCenterY(endYCoord); //circle centerY is endYCoord
                    c.setRadius(5.00); //radius is set to 5 (arbitrary)
                    c.setFill(Color.BLUE);//fill is set to blue
                    c.setStroke(Color.BLACK); //stroke outline is set to black.
                    ((Pane)imgImage.getParent()).getChildren().add(c); //circle is added to pane.
                    Bounds b = imgImage.getLayoutBounds(); //Gets the layout bounds of the image
                    double xScale = b.getWidth()/width;
                    double yScale = b.getHeight()/height;

                    endXCoord /= xScale;
                    endYCoord /= yScale;

                    int yCoord = (int) endXCoord;
                    int xCoord = (int) endYCoord;
                    System.out.println(xCoord);
                    System.out.println(yCoord);

                    endPoint = (xCoord + width*yCoord); //Creates the index of the selected pixel
                    secondClick = true;
                    System.out.println(endPoint);
                }
            }
        };
        imgImage.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseHandler); //eventFilter added to ImageView when mouse is pressed.
        imgImage.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseHandler); //eventFilter added to ImageView when mouse is released.
    }

    //Java likes this don't delete it or java will be angry.
    public Controller() throws ParserConfigurationException {
    }

    @FXML
    public Image uploadImage(ActionEvent event) throws Exception {
        XMLNodes.addSignificantNodes();
        FileChooser fc = new FileChooser(); //fileChooser is instantiated.
        Window ownerWindow = null; //window ownerWindow is set to null.
        File selectedFile = fc.showOpenDialog(ownerWindow);
        //the file selectedFile is equal to fileChooser's showOpenDialog with ownerWindow passed as a parameter.

        if (selectedFile != null) { //if the selected file isn't null..
            System.out.println(selectedFile.toURI().toString());
            //selected file's path is printed to the console as a string.
            image = new Image(selectedFile.toURI().toString());
            //new image is instantiated and given this url, and a height + width of 400.
            imgImage.setImage(image);
            imgImage.setPreserveRatio(true);
            imgImage.fitHeightProperty();
            imgImage.fitWidthProperty();
            //new image is set in the imageView imgImage.

        } else { //if selectedFile is null..
            System.out.println("Not a valid file."); //print to console that selection is invalid.
        }
        return image; //image is returned.
    }

    @FXML
    public void selectAlgorithm() {
        if(BFS.isSelected()) {
            generateRouteBFS();
        }
        if(Dijkstra.isSelected()) {
            generateRouteDijkstra();
        }
    }

    @FXML
    public void generateRouteDijkstra() {
        StringBuilder routePath = new StringBuilder();
        GraphNodeAL2<?> StartNode1 = null;

        for(int i = 0; i < XMLNodes.nodes.size(); i++) { //Taking out the string value of the combo box I want to try check what node it equals to and take out that node and set it as StartNode1 which would get input into the Dijkstra
            GraphNodeAL2<?> TempNode = XMLNodes.nodes.get(i);
            if(TempNode.data.toString().equals(StartCombo.getSelectionModel().getSelectedItem())) {
                StartNode1 = TempNode;
            }
              //Not sure how to grab the nameof the landmark and compare it to the string of the combo box
        }
        //Get the ToString of the node and if the ToString is equal to the ComboBox String then it must be that node.


        assert StartNode1 != null;
        CostedPath cpa=CostedPath.findCheapestPathDijkstra(StartNode1,EndCombo.getSelectionModel().getSelectedItem());
        System.out.println("Route");
        for(GraphNodeAL2<?> n : cpa.pathList)  //Should check for possible null value
            routePath.append(n.data + "\n");

        OutputDij.setText("The cheapest path from: " + StartCombo.getSelectionModel().getSelectedItem() + " to: \n " + EndCombo.getSelectionModel().getSelectedItem() + "\n" +
                "using Dijkstra's algorithm:\n" + "------------------------------------- \n" + routePath + "\n" + "The total path distance is: " + cpa.pathCost + " Meters");
    }

    @FXML
    public void generateRouteBFS() {
        int height = (int) image.getHeight(); //height is equal to image height.
        int width = (int) image.getWidth(); //width is equal to image width.
        PixelReader pixelReader = image.getPixelReader();
        white = new boolean[width * height];
        int j = 0;
        for (int y = 0; y < height; y++) { //for int row, where row is 0 and less than height, and increments..
            for (int x = 0; x < width; x++) { //for int column, where column is 0 and less than width, and increments..
                Color color = pixelReader.getColor(x, y); //new colour which gets the colour values for row and col.
                double Red = color.getRed();
                double Green = color.getGreen();
                double Blue = color.getBlue();
                if (Red > 0.92 && Green > 0.92 && Blue > 0.92) { //if the colour is white, all arrays are given a -1 value (nothing there)
                    white[j] = true;
                } else {
                    white[j] = false;
                }
                j++;
            }
        }
        /**
         * Using a ArrayList of GraphNodeAL objects for each element in the map
         * Where the map element is a road, a GraphNodeAL<Integer> is added
         *   This will be used to connect adjacent road pixels using their map index(thus the Integer)
         * Where the map element is not a road a null element is added.
         * This is so that I can use the get(x) method later where x is the element in the map
         * I could/should use a different Data Structure which would not require the storing of the null elements.
         *   Go figure it out if you wish!
         */
        ArrayList<GraphNodeAL> mapAL =new ArrayList<>();
        for(int currentNode = 0; currentNode< white.length; currentNode++) { //Build the overall structure
            if (white[currentNode]) {
                GraphNodeAL<Integer> tmp = new GraphNodeAL(currentNode);
                mapAL.add(tmp);
            }
            else {
                mapAL.add(null);
            }
        }
        /**
         * Next -- Populate the connections
         * I am only looking at the previous adjacent map elements (so that I don't have duplicate connections)
         * The trick in all these 2D arrays is to know how to calculate the adjacent element references
         * and to check for possible out of bounds before accessing the Array element
         */
        for(GraphNodeAL<Integer> pixel: mapAL) { //for all the elements in the array of GraphNodes
            if (pixel != null) {    //ignore the null (non-road) elements
                if ((pixel.data - width) >= 0 && white[pixel.data - width]) {//above
                    pixel.connectToNodeUndirected(mapAL.get(pixel.data - width));
                    System.out.println("connecting " + pixel.data + " to " + (pixel.data - width));
                }
                if ((pixel.data - width + 1) > 0 && (pixel.data % width + 1) < width && white[pixel.data - width + 1]) {//above right
                    pixel.connectToNodeUndirected(mapAL.get(pixel.data - width + 1));
                    System.out.println("connecting " + pixel.data + " to " + (pixel.data - width + 1));
                }
                if ((pixel.data % width) != 0 && white[pixel.data - 1]) { //left
                    pixel.connectToNodeUndirected(mapAL.get(pixel.data - 1));
                    System.out.println("connecting " + pixel.data + " to " + (pixel.data - 1));
                }
                if (((pixel.data - width - 1) >= 0) && (pixel.data % width) != 0 && (white[pixel.data - width - 1])) {//above left
                    pixel.connectToNodeUndirected(mapAL.get(pixel.data - width - 1));
                    System.out.println("connecting " + pixel.data + " to " + (pixel.data - width - 1));
                }
            }
        }
        System.out.println("The (shortest) breath-first search solution path from index: " + startPoint);
        System.out.println("(object s) to index: " + endPoint);
        System.out.println("------------------------------------------");
        GraphNodeAL<Integer> s = mapAL.get(startPoint); //start is the selected element with the start point dot
        if(s !=null) {
            System.out.println("s ="+s.data); //just checking!!
            List<GraphNodeAL<?>> bfsGridPath = findPathBreadthFirst(s, endPoint); //looking for end Point path
            //Drawing the path on screen.
            WritableImage wImage = new WritableImage(
                    (int) image.getWidth(),
                    (int) image.getHeight());
            PixelWriter pixelWriter = wImage.getPixelWriter();

            for (GraphNodeAL<?> n : bfsGridPath) {
                for (int y = 0; y < height; y++) { //for int row, where row is 0 and less than height, and increments..
                    for (int x = 0; x < width; x++) { //for int column, where column is 0 and less than width, and increments..
                        Color color = pixelReader.getColor(x, y); //new colour which gets the colour values for row and col.
                        if(n.data.equals(x + width*y)) {
                            wImage.getPixelWriter().setColor(y,x,Color.RED); // if the index matches the current pixel set it to red
                        }
                        else if(!white[x + width * y]) {
                            wImage.getPixelWriter().setColor(x,y,image.getPixelReader().getColor(x,y)); //Let the background remain the same as it was before by setting its color to the original image
                        }
                        else wImage.getPixelWriter().setColor(x,y,Color.WHITE); //If it doesn't then set it to white
                    }
                }
                System.out.println(n.data); //display the path
            }
            imgImage.setImage(wImage);
        }
    }


//The following code is directly from Dr.Peter Carews notes which you should be familiar with:

//Interface method to allow just the starting node and the goal node data to match to be specified
public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(GraphNodeAL<?> startNode, T lookingfor){
        List<List<GraphNodeAL<?>>> agenda=new ArrayList<>(); //Agenda comprised of path lists here!
        List<GraphNodeAL<?>> firstAgendaPath=new ArrayList<>(),resultPath;
        firstAgendaPath.add(startNode);
        agenda.add(firstAgendaPath);
        resultPath=findPathBreadthFirst(agenda,null,lookingfor); //Get single BFS path (will be shortest)
        Collections.reverse(resultPath); //Reverse path (currently has the goal node as the first item)
        return resultPath;
        }

//Agenda list based breadth-first graph search returning a single reversed path (tail recursive)
public static <T> List<GraphNodeAL<?>> findPathBreadthFirst(List<List<GraphNodeAL<?>>> agenda, List<GraphNodeAL<?>> encountered, T lookingfor){
        if(agenda.isEmpty()) return null; //Search failed
        List<GraphNodeAL<?>> nextPath=agenda.remove(0); //Get first item (next path to consider) off agenda
        GraphNodeAL<?> currentNode=nextPath.get(0); //The first item in the next path is the current node
        if(currentNode.data.equals(lookingfor)) return nextPath; //If that's the goal, we've found our path (so return it)
        if(encountered==null) encountered=new ArrayList<>(); //First node considered in search so create new (empty)encountered list
        encountered.add(currentNode); //Record current node as encountered so it isn't revisited again
        for(GraphNodeAL<?> adjNode : currentNode.adjList) //For each adjacent node
        if(!encountered.contains(adjNode)) { //If it hasn't already been encountered
        List<GraphNodeAL<?>> newPath=new ArrayList<>(nextPath); //Create a new path list as a copy of
//the current/next path
        newPath.add(0,adjNode); //And add the adjacent node to the front of the new copy
        agenda.add(newPath); //Add the new path to the end of agenda (end->BFS!)
        }
        return findPathBreadthFirst(agenda,encountered,lookingfor); //Tail call
        }












    /*public void generateRoute() {
        int height = (int) image.getHeight(); //height is equal to image height.
        int width = (int) image.getWidth(); //width is equal to image width.
        int[] data = new int[height * width]; //array data is a one dimensional int array the size of height x width.
        white = new int[height * width]; //white is a one dimensional int array of same size.
        PixelReader pixelReader = image.getPixelReader();
        int i = 0;
    for (int j = 0; i < data.length; i++) {
        //notwhite[i] = -1; //notwhite iteratively populates with i.
        white[i] = i; //white iteratively populates with i.
    }
        for (int readY = 0; readY < height; readY++) { //for int row, where row is 0 and less than height, and increments..
            for (int readX = 0; readX < width; readX++) { //for int column, where column is 0 and less than width, and increments..
                Color color = pixelReader.getColor(readX, readY); //new colour which gets the colour values for row and col.
                double Red = color.getRed();
                double Green = color.getGreen();
                double Blue = color.getBlue();
                if (Red > 0.92 && Green > 0.92 && Blue > 0.92) { //if the colour is white, all arrays are given a -1 value (nothing there)
                    data[i] = 1;
                    white[i] = 1; //SR WED: added as you are using white array below and it hasn't been given any values
                } else {
                    data[i] = 0;
                    white[i] = -1; //SR WED: added as you are using white array below and it hasn't been given any values, beolow you use -1 as not road
                }
                //System.out.print(data[i]); //for printing image
                i++;
            }
            //System.out.println(); //for printing image
        }
        for (i = 0; i < data.length; i++) {
            //PixelWriter pixelWriter = image.getPixelWriter();
            if (white[i] != -1) { //if a white value isn't -1 (something is there)
                firstPixel = new GraphNode2(i);
                firstPixel.setX(i%width);
                firstPixel.setY(i/width);
              //  pixelWriter.setColor(firstPixel.getX(), firstPixel.getY(), Color.RED);
                GraphLink firstLink = new GraphLink(firstPixel, 1);
                firstPixel.getAdjList().add(firstLink);
                validPath.add(firstPixel); //this is added to the ArrayList of nodes.
                if ((i + 1) < white.length && white[i + 1] != -1 && (i + 1) % width != 0) {
                    //if i+1 is less than white's length and white at i+1 is not -1 (something is there) and i+1 mod width isn't 0
                    //(this is for handling row/col edge cases)
                    GraphNode2 nextPixel = new GraphNode2(i);
                    nextPixel.setX(i+1%width);
                    nextPixel.setY(i+1/width);
                    GraphLink secondLink = new GraphLink(nextPixel, 1);
                    //int reference2 = nextPixel.getCoordinates();
                    nextPixel.getAdjList().add(secondLink);
                    validPath.add(nextPixel);
                    firstPixel.connectToNodeUndirected(nextPixel, 1);
                   // pixelWriter.setColor(nextPixel.getX(), nextPixel.getY(), Color.RED);
                }
                if ((i + width) < white.length && white[i + width] != -1 && (i+width) % width !=0) {//added mod width to handle edges.
                    //if i + width is less than white's length and white at i+ width isn't -1 (something is there)
                    GraphNode2 widthWiseNextPixel = new GraphNode2(i);
                    widthWiseNextPixel.setX(i+width%width);
                    widthWiseNextPixel.setY(i+width/width);
                    GraphLink thirdLink = new GraphLink(widthWiseNextPixel, 1);
                    //int reference3 = widthWiseNextPixel.getCoordinates();
                    validPath.add(widthWiseNextPixel);
                    widthWiseNextPixel.getAdjList().add(thirdLink);
                    firstPixel.connectToNodeUndirected(widthWiseNextPixel, 1);
                }
                if ((i + height) < white.length && white[i + height] != -1 && (i+height)% width!=0) {
                    GraphNode2 heightWiseNextPixel = new GraphNode2(i);
                    heightWiseNextPixel.setX(i+height%width);
                    heightWiseNextPixel.setY(i+height/width);
                    GraphLink fourthLink = new GraphLink(heightWiseNextPixel, 1);
                    //int reference4 = heightWiseNextPixel.getCoordinates();
                    validPath.add(heightWiseNextPixel);
                    heightWiseNextPixel.getAdjList().add(fourthLink);
                    firstPixel.connectToNodeUndirected(heightWiseNextPixel, 1);
                }
                if (i + width - 1 < white.length && white[i + width - 1] != -1 && (i+width-1)%width!=0) {//diagonal 1: left diagonal above
                    GraphNode2 diagonalLeftAboveNextPixel = new GraphNode2(i);
                    diagonalLeftAboveNextPixel.setX(i+width-1%width);
                    diagonalLeftAboveNextPixel.setY(i+width-1/width);
                    GraphLink fifthLink = new GraphLink(diagonalLeftAboveNextPixel, 1);
                    //int reference5 = diagonalLeftAboveNextPixel.getCoordinates();
                    validPath.add(diagonalLeftAboveNextPixel);
                    diagonalLeftAboveNextPixel.getAdjList().add(fifthLink);
                    firstPixel.connectToNodeUndirected(diagonalLeftAboveNextPixel, 1);
                }
                //SR WED : added > =0
                if ((((i - width + 1) >= 0) && (i - width + 1) < white.length) && (white[i - width + 1] != -1 && (i-width+1)%width!=0)) {//diagonal 2: right diagonal above
                    GraphNode2 diagonalRightAboveNextPixel = new GraphNode2(i);
                    diagonalRightAboveNextPixel.setX(i-width+1%width);
                    diagonalRightAboveNextPixel.setY(i-width+1/width);
                    GraphLink sixthLink = new GraphLink(diagonalRightAboveNextPixel, 1);
                    validPath.add(diagonalRightAboveNextPixel);
                    diagonalRightAboveNextPixel.getAdjList().add(sixthLink);
                    firstPixel.connectToNodeUndirected(diagonalRightAboveNextPixel, 1);
                }
                if (i + width - 1 < white.length && white[i + width - 1] != -1 && (i+width-1)%width!=0) {//diagonal 3: left diagonal below
                    GraphNode2 diagonalLeftBelowNextPixel = new GraphNode2(i);
                    diagonalLeftBelowNextPixel.setX(i+width-1%width);
                    diagonalLeftBelowNextPixel.setY(i+width-1/width);
                    GraphLink seventhLink = new GraphLink(diagonalLeftBelowNextPixel, 1);
                    validPath.add(diagonalLeftBelowNextPixel);
                    diagonalLeftBelowNextPixel.getAdjList().add(seventhLink);
                    firstPixel.connectToNodeUndirected(diagonalLeftBelowNextPixel, 1);
                }
                if (i + width + 1 < white.length && white[i + width + 1] != -1 && (i+width+1)%width!=0) {//diagonal 4: right diagonal below
                    GraphNode2 diagonalRightBelowNextPixel = new GraphNode2(i);
                    diagonalRightBelowNextPixel.setX(i+width+1%width);
                    diagonalRightBelowNextPixel.setY(i+width+1/width);
                    GraphLink eighthLink = new GraphLink(diagonalRightBelowNextPixel, 1);
                    validPath.add(diagonalRightBelowNextPixel);
                    diagonalRightBelowNextPixel.getAdjList().add(eighthLink);
                    firstPixel.connectToNodeUndirected(diagonalRightBelowNextPixel, 1);
                }
            }
        }
        //SR: MOved your print out stmt
       for (i = 0; i < validPath.size(); i++) {
           for (i = 0; i < 10; i++) { //SR Wed: Just to see first 100
               System.out.println(validPath.get(i).toString());
           }
       }
//        CostedPath thePath = CostedPath.searchGraphDepthFirstCheapestPath(firstPixel, null, 0, endingGraphNodeActual.getData());
    }*/

    public void exit(ActionEvent e){
        Runtime.getRuntime().exit(0); //exits the program.

    }

    public void load() throws Exception{
        XMLNodes.load(); //loads arraylist of node landmarks.

    }

    public void save() throws Exception{
        XMLNodes.save(); //saves arraylist of node landmarks.
    }


}
