
import java.io.File;
import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPATH {

    public String EjecutaXPath(File archivo, String consulta) {

        String salida = "";
        Node node;

        try {

//Crea el objeto XPathFactory
            XPath xpath = XPathFactory.newInstance().newXPath();
//Crea un objeto DocumentBuilderFactory para el DOM (JAXP)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//Crear un árbol DOM (parsear) con el archivo LibrosXML.xml
            Document XMLDoc = factory.newDocumentBuilder().parse(new InputSource(new FileInputStream(archivo)));
//Crea un XPathExpression con la consulta deseada
            XPathExpression exp = xpath.compile(consulta);
//Ejecuta la consulta indicando que se ejecute sobre el DOM y que devolverá
//el resultado como una lista de nodos.
            Object result = exp.evaluate(XMLDoc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;

//Ahora recorre la lista para sacar los resultados
            for (int i = 0; i < nodeList.getLength(); i++) {

                node = nodeList.item(i);

                NodeList listaHijos = node.getChildNodes();
                

                
               //Para que cuando haga una consulta sobre el nodo libro me coja su atributo
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("Libro")) {

                    salida = salida + "\n" + "Publicado en: " + nodeList.item(i).getAttributes().getNamedItem("publicado_en").getNodeValue() + "\n";

                }

                

                
                //Para que cuando hago una consulta sobre el nodo Libro o sobre Libros
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getChildNodes().getLength() > 1) {

                    //bucle for para que me recorra todos los hijos de Libro o Libros
                    for (int alex = 0; alex < listaHijos.getLength(); alex++) {

                        node = listaHijos.item(alex);
                        
                        //Para que solo entre en los nodos tipo elemento
                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            //me añade a la cadena salido el nombre del nodo y el contenido
                            salida = salida + "\n" + node.getNodeName() + ": "
                                    + node.getTextContent().trim() + "\n";

                        }

                    }
                    salida = salida.trim() + "\n" + "-----------------------------------------------------------------------------------";

                } 
                
                //Para cuando hago una consulta sobre el Atributo
                else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {

                    salida = salida + "\n" + "Publicado en: "
                            + node.getTextContent().trim() + "\n";

                    salida = salida.trim() + "\n" + "-----------------------------------------------------------------------------------";

                } 
                
                //Para que cuando hago una cosulta sobre un nodo hijo de Libro
                else if (node.getNodeType() == Node.ELEMENT_NODE && node.getParentNode().getNodeName().equals("Libro")) {
                    System.out.println(node.getNodeName());
                    
                    salida = salida + "\n" + node.getNodeName() + ": "
                            + node.getTextContent().trim() + "\n";

                    salida = salida.trim() + "\n" + "-----------------------------------------------------------------------------------";

                }

            }
            

            return salida;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return "Error";
        }
    }

}
