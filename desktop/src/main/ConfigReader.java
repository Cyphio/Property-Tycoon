package main;

import Tiles.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class ConfigReader {

    private Tile[] tileList;
    private ArrayList<Tile> cardList;




    public Tile[] ConfigReader() {


        tileList = new Tile[40];

        try {

            File file = new File("resources/config/property-config.xml");

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList tileNodeList = document.getElementsByTagName("tile");
            // NodeList cardNodeList = document.getElementsByTagName("card");


            for (int i = 0; i < 40; i++) {

                Node aNode = tileNodeList.item(i);

                if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element tileElement = (Element) aNode;
                    Tile tile = new Tile();


                    try {
                        switch (tileElement.getElementsByTagName("type").item(0).getTextContent()) {

                            case "property":

                                List<String> houses = Arrays.asList("one-house", "two-house", "three-house", "four-house", "hotel");

                                tile.setTileName(tileElement.getElementsByTagName("name").item(0).getTextContent());
                                tile = new Property();
                                tile.setBuyable(true);

                                ((Property) tile).setColour(tileElement.getElementsByTagName("colour").item(0).getTextContent());

                                for (String house : houses) {
                                    ((Property) tile).addHousePrice(Integer.parseInt(tileElement.getElementsByTagName(house).item(0).getTextContent()));
                                }
                                break;

                            case "go":

                                tile = new Go();

                                break;
                            case "jail":

                                tile = new Jail();
                                break;
                            case "tax":

                                tile = new Tax();
                                break;
                            case "opportunityknockst":

                                tile = new OpportunityKnocks();
                                break;
                            case "station":

                                tile = new Station();
                                break;
                            case "parking":

                                tile = new FreeParking();
                                break;
                            case "gotojail":

                                tile = new GoToJail();

                                break;
                            case "potluck":
                                tile = new PotLuck();
                                break;
                            case "utility":
                                tile = new PotLuck();
                                break;


                        }
                        //ASK IF WATSON GAMES WANTS CHANGEABLE NAMES FOR JAIL FOR THEMABLE GAMES
                        //tile.setTileName(tileElement.getElementsByTagName("name").item(0).getTextContent());


                        tile.setTilePos(i);
                        tileList[i] = tile;

                    } catch (Exception e) {

                        System.out.println("error in config file setup");

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tileList;

    }


}
