/* Liz Demin    ParserTest.java
 * This class reads in input from a file or from standard input, parses it into a tree, and outputs
 * the resulting tree as XML text back to the console.
 * 
 * See detailed comments in README
 */

package project;

import java.io.*; 

public class ParserTest {

    public static void main(String[] args) throws IOException {
    	
    	//use the following two lines if you want to read in a file directly
        File file = new File("testFile1.xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
        //use the following line to read in from standard input
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Parser parser = new Parser();
        Element document = new Element();
        parser.parseXml(document, reader);	//parses XML into tree
        document.toXml(2,0);				//prints XML from tree
    }

}
