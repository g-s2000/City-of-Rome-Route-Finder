package sample;

//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

    public class XMLNodes {

        public static void main(String[] args) throws Exception {
            addSignificantNodes();
            //DijkstraTest();
            //CostedPath thePath = CostedPath.searchGraphDepthFirstCheapestPath(nodes.get(2), null, 1000, endingGraphNodeActual.getData());
        }
        public static Landmark[] landmarks = new Landmark[9];

       static ArrayList<GraphNodeAL2> nodes= new ArrayList();

        public static void addSignificantNodes() throws Exception {
            Landmark spanishStepsLandmark= new Landmark("Spanish Steps",  3); GraphNodeAL2<Landmark> spanishSteps= new GraphNodeAL2<>(spanishStepsLandmark);
            Landmark castleAngelLandmark= new Landmark("Castle of the Holy Angel",  2); GraphNodeAL2<Landmark> castleAngel= new GraphNodeAL2<>(castleAngelLandmark);
            Landmark peoplesParkLandmark= new Landmark("People's Park",  5); GraphNodeAL2<Landmark> peoplesPark= new GraphNodeAL2<>(peoplesParkLandmark);
            Landmark treviFountainLandmark= new Landmark("Trevi Fountain",  2); GraphNodeAL2<Landmark> treviFountain= new GraphNodeAL2<>(treviFountainLandmark);
            Landmark fountainFourRiversLandmark= new Landmark("Fountain of the Four Rivers",  4); GraphNodeAL2<Landmark> fountainFourRivers= new GraphNodeAL2<>(fountainFourRiversLandmark);
            Landmark pantheonLandmark= new Landmark("The Pantheon",  5); GraphNodeAL2<Landmark> pantheon= new GraphNodeAL2<>(pantheonLandmark);
            Landmark colosseumLandmark= new Landmark("The Colosseum",  5); GraphNodeAL2<Landmark> colosseum= new GraphNodeAL2<>(colosseumLandmark);
            Landmark forumLandmark= new Landmark("Roman Forum",  4); GraphNodeAL2<Landmark> forum= new GraphNodeAL2<>(forumLandmark);
            Landmark palatineHillLandmark= new Landmark("Palatine Hill",  3); GraphNodeAL2<Landmark> palatineHill= new GraphNodeAL2<>(palatineHillLandmark);

            spanishSteps.connectToNodeUndirected(castleAngel, 800); spanishSteps.connectToNodeUndirected(treviFountain, 1600); spanishSteps.connectToNodeUndirected(peoplesPark, 850);
            treviFountain.connectToNodeUndirected(pantheon, 650);
            fountainFourRivers.connectToNodeUndirected(colosseum, 2300);
            pantheon.connectToNodeUndirected(fountainFourRivers, 550);
            colosseum.connectToNodeUndirected(palatineHill, 450);
            forum.connectToNodeUndirected(colosseum, 100);



            landmarks[0] = spanishStepsLandmark;
            landmarks[1] = castleAngelLandmark;
            landmarks[2] = peoplesParkLandmark;
            landmarks[3] = treviFountainLandmark;
            landmarks[4] = fountainFourRiversLandmark;
            landmarks[5] = pantheonLandmark;
            landmarks[6] = colosseumLandmark;
            landmarks[7] = forumLandmark;
            landmarks[8] = palatineHillLandmark;

            nodes.add(spanishSteps);
            nodes.add(treviFountain);
            nodes.add(pantheon);
            nodes.add(fountainFourRivers);
            nodes.add(forum);
            nodes.add(colosseum);
            nodes.add(palatineHill);
            nodes.add(castleAngel);
            nodes.add(peoplesPark);
        }


        public static void load() throws Exception
        {
            XStream xstream = new XStream(new DomDriver());
            ObjectInputStream is = xstream.createObjectInputStream(new FileReader("LOCATIONS.xml"));
            nodes = (ArrayList) is.readObject();
            System.out.println(nodes);
            is.close();
        }

        public static void save() throws Exception { //xml save method.
            {
                XStream xstream = new XStream(new DomDriver());
                ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("LOCATIONS.xml"));
                out.writeObject(nodes);
                out.close();
            }
        }

        public static GraphNodeAL2 nodes(int i) {
            return nodes.get(i);
        }


        @Override
        public String toString() {
            return "Node data"+ nodes.toString();
        }


    }

