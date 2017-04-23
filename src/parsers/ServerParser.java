package parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vahriin on 3/18/17.
 */


/**request:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <request>
 *     <weather>
 *         <item>
 *             <name>temperature</name>
 *         </item>
 *         <item>
 *             <name>humidity</name>
 *         </item>
 *         <item>
 *             <name>pressure</name>
 *         </item>
 *         <item>
 *             <name>rain</name>
 *         </item>
 *     </weather>
 * </request>
 *
 *
 * response:
 * <?xml version="1.0" encoding="UTF-8"?>
 * <response>
 *     <weather>
 *         <item>
 *             <name>temperature</name>
 *             <value>25.7</value>
 *         </item>
 *         <item>
 *             <name>humidity</name>
 *             <value>25.7</value>
 *         </item>
 *         <item>
 *             <name>pressure</name>
 *             <value>133000</value>
 *         </item>
 *         <item>
 *             <name>rain</name>
 *             <value>dry</value>
 *         </item>
 *     </weather>
 * </response>
 *
 * if request has an error, then response will be:
 *
 * <response>
 *     <weather>
 *         <error>Wrong request</error>
 *     </weather>
 * </response>
 */


public class ServerParser {
    public ServerParser() throws ParserConfigurationException, TransformerConfigurationException {
        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    }

    public static ArrayList<String> parse(String request) {
        ArrayList<String> mapWeatherRequest = new ArrayList<>(4);
        try {
            Document docRequest = builder.parse(new InputSource(new StringReader(request)));
            NodeList listLevel1 = docRequest.getDocumentElement().getChildNodes();

            for (int i = 0 ; i < listLevel1.getLength(); i++) {
                Node currentNode = listLevel1.item(i);
                if (currentNode.getNodeName().equals("weather")) {
                    NodeList weatherItems = currentNode.getChildNodes();
                    for (int j = 0; j < weatherItems.getLength(); j++) {
                        mapWeatherRequest.add(weatherItems.item(j).getChildNodes().item(0).getTextContent());
                    }
                }

            }

        } catch (org.xml.sax.SAXException ex) {
            System.err.println("ServerParserEx: SAXException " + ex.getMessage());
            mapWeatherRequest.add("error");
        } catch (IOException ex) {
            System.err.println("ServerParserEx: parse fail " + ex.getMessage());
            mapWeatherRequest.add("error");
        }

        return mapWeatherRequest;
    }

    public static String createResponse(Map<String, String> valuesMap) {
        Document document = builder.newDocument();
        Element root = document.createElement("response");
        Element weather = document.createElement("weather");

        document.appendChild(root);
        root.appendChild(weather);

        for (Map.Entry<String, String> currentEntry : valuesMap.entrySet()) {
            if (!currentEntry.getKey().equals("error")) {
                Element item = document.createElement("item");

                Element name = document.createElement("name");
                name.setTextContent(currentEntry.getKey());

                Element value = document.createElement("value");
                value.setTextContent(currentEntry.getValue());

                item.appendChild(name);
                item.appendChild(value);
                weather.appendChild(item);
            } else {
                Element error = document.createElement(currentEntry.getKey());
                error.setTextContent(currentEntry.getValue());
                weather.appendChild(error);
            }
        }

        StringWriter out = new StringWriter();

        try{
            transformer.transform(new DOMSource(document), new StreamResult(out));
        } catch (TransformerException ex) {
            System.err.println("ServerParserEx: fail tranform to string" + ex.getMessage());
            //sorry, long string
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<response>\n<weather><error>Internal server error</error>\n</weather>\n</response>\n");
        }
        return out.toString();
    }

    private static DocumentBuilder builder;
    private static Transformer transformer;
}
