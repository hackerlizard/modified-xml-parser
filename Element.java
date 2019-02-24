/* Liz Demin    Element.java
 * This class is used to represent an XML element.
 * 
 * See detailed comments in README
 */

package project;
//------------------------------------------------------------------------------------
import java.util.*;
//------------------------------------------------------------------------------------
public class Element {
    String name;                                                        //name of element
    Map<String, String> attributes = new HashMap<String, String>();     //map of element's attributes
    List<Element> children = new ArrayList<Element>();                  //list of element's children
    //------------------------------------------------------------------------------------
    //default constructor
    public Element() {
        name = "";
    }
    //------------------------------------------------------------------------------------
    //parameterized constructor
    public Element(String n) {
        name = n;
    }
    //------------------------------------------------------------------------------------
    //checks if element is the document element
    boolean isDocument() {
        if(name.equals("")) {
            return true;
        }
        return false;
    }
    //------------------------------------------------------------------------------------
    //returns element name
    String getName() {
        return name;
    }
    //------------------------------------------------------------------------------------
    //sets attribute and attribute value
    void setAttribute(String name, String value) {
        if(isDocument()) {
            System.out.println("Cannot set attribute on document element");
        }
        attributes.put(name, value);
    }
    //------------------------------------------------------------------------------------
    //adds a child to the element
    void addChild(Element child) {
        if (isDocument() && !children.isEmpty()) {
            System.out.println("Cannot add more than one child to document element");
        }
        children.add(child);
    }
    //------------------------------------------------------------------------------------
    //converts tree to XML text and prints with formatting
    void toXml(int indentWidth, int indentLevel) {
        if (isDocument()) {
            if (!children.isEmpty()) {
                (children.get(0)).toXml(indentWidth, 0);
                //children_.begin()->toXml(stream, indentWidth, 0);
            }
        } else {
            if (indentWidth >= 0) {
                if (indentLevel > 0) {
                    System.out.println();
                }
                for(int i = 0; i < indentLevel*indentWidth; i++) {
                    System.out.print(" ");
                }
            }
            System.out.print("<"+name);
            for (String name : attributes.keySet()) {
                System.out.print(" "+name+"=\""+attributes.get(name)+"\"");
            }
            if (children.isEmpty()) {
                System.out.print("/>");
            } else {
                System.out.print('>');
                for (Element child : children) {
                    child.toXml(indentWidth, indentLevel + 1);
                }
                if (indentWidth >= 0) {
                    System.out.println();
                    for(int i = 0; i < indentLevel*indentWidth; i++) {
                        System.out.print(" ");
                    }
                }
                System.out.print("</"+name+'>');
            }
            if ((indentWidth >= 0) && (indentLevel == 0)) {
                System.out.println();
            }
        }
    }
}
