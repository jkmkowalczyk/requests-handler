package jkmkowalczyk.coreservicestask.utils;

import jkmkowalczyk.coreservicestask.request.RequestDto;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides method for loading csv file into the application.
 */
@Component
public
class XmlLoader {

    /**
     * Loads the xml files into the application.
     *
     * @param files list of xml files to load
     * @return list of loaded requests
     */
    public final List<RequestDto> load(final List<File> files) {
        List<RequestDto> requests = new ArrayList<>();

        for (File file : files) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("request");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        try {
                            requests.add(createRequestFromXmlElement(element));
                        } catch (NullPointerException e) {
                            System.err.println("Invalid record: " + (i + 1) + " in file: " + file.getName()
                                    + ", moving to the next line.");
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("File: " + file.getName() + " not found!");
            } catch (ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        }
        return requests;
    }


    /**
     * Creates RequestDto object from Xml element.
     *
     * @param xmlElement node element to create request object from
     * @return created RequestDto object
     */
    private RequestDto createRequestFromXmlElement(Element xmlElement) {
        return new RequestDto.Builder()
                .clientId(getTagValue(xmlElement, "clientId"))
                .requestId(Long.parseLong(getTagValue(xmlElement, "requestId")))
                .name(getTagValue(xmlElement, "name"))
                .quantity(Integer.parseInt(getTagValue(xmlElement, "quantity")))
                .price(Double.parseDouble(getTagValue(xmlElement, "price")))
                .build();
    }

    /**
     * Gets the value of specified xml tag.
     *
     * @param element node element
     * @param tagName name of the desired xml tag
     * @return value of the specified xml tag
     */
    private String getTagValue(final Element element, final String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

}
