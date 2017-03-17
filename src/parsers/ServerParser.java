package parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vahriin on 3/13/17.
 */


/**request:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <request>
 *     <weather>
 *         <item name="temperature" format="celsius"/>
 *         <item name="pressure" format="mm"/>
 *         <item name="humidity" format="percent"/>
 *         <item name="rain" format="status"/>
 *     </weather>
 * </request>
 *
 *
 * response:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <response>
 *     <weather>
 *         <item name="temperature" format="celsius">25.4</item>
 *         <item name="pressure" format="mm">766.25</item>
 *         <item name="humidity" format="percent">55.9</item>
 *         <item name="rain" format="status">Dry</item>
 *     </weather>
 * </response>
 */


public class ServerParser {
    public ServerParser () throws ParserConfigurationException {
        factory = DocumentBuilderFactory.newInstance();
        setDocumentBuilder();
    }

    public static Map<String, String> parse(InputStream istream) {
        Map<String, String> mapWeatherRequest = null;
        try {
            Document request = builder.parse(istream);
            NodeList listLevel1 = request.getChildNodes();
            for (int i = 0 ; i < listLevel1.getLength(); i++) {
                Node currentNode = listLevel1.item(i);
                if (currentNode.getNodeName().equals("weather")) {
                    NodeList weatherChildren = currentNode.getChildNodes();
                    mapWeatherRequest = new HashMap<>(weatherChildren.getLength(), 1);
                    for (int j = 0; j < weatherChildren.getLength(); j++) {
                        Element currentElement = (Element) weatherChildren.item(i);
                        mapWeatherRequest.put(currentElement.getAttribute("name"), "0");
                    }
                }

            }
        } catch (org.xml.sax.SAXException ex) {
            System.out.println("serverParseEx: SAXException " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("serverParseEx: parse fail " + ex.getMessage() );
        } finally {
            if (mapWeatherRequest == null) {
                mapWeatherRequest = new HashMap<>(0,1);
            }
        }
        return mapWeatherRequest;
    }

    /*public static String composeResponse(Map<String, String> request) {
        Document response = builder.newDocument();
        response.
    }*/

    private static void setDocumentBuilder()
            throws ParserConfigurationException {
        DocumentBuilder builder = factory.newDocumentBuilder();
    }

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
}
