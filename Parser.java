/* Liz Demin    Parser.java
 * This class creates a parser that parses an XML document.
 * 
 * See detailed comments in README
 */

package project;
//------------------------------------------------------------------------------------
import java.io.*; 
//------------------------------------------------------------------------------------
public class Parser { 
    State state;    //the parser's state
    //------------------------------------------------------------------------------------
    //all possible states of parser (described fully in report)
    public enum State {
        CONTENT,
        TAG_START,
        TAG_CLOSE,
        TAG_CLOSE_NAME,
        TAG_NAME,
        TAG_BODY,
        ATTR_NAME,
        ATTR_VALUE_START,
        ATTR_VALUE,
        ATTR_VALUE_END,
        TAG_BODY_END
    }
    //------------------------------------------------------------------------------------
    //constructor with default state
    public void Parser() {
        this.state = State.CONTENT;
    }
    //------------------------------------------------------------------------------------
    //checks if current character is a whitespace character
    public boolean isWhitespace(char ch) {
        return (ch == ' ') || (ch == '\t') || (ch == '\r') || (ch == '\n');
    }
    //------------------------------------------------------------------------------------
    //checks if current character is valid for starting a name
    public boolean isNameStartChar(char ch) {
        return ((ch >= 'a') && (ch <= 'z')) || ((ch >= 'A') && (ch <= 'Z')) || (ch == '_');
    }
    //------------------------------------------------------------------------------------
    //checks if current character is valid for using in name
    public boolean isNameChar(char ch) {
        return isNameStartChar(ch) || ((ch >= '0') && (ch <= '9'));
    }
    //------------------------------------------------------------------------------------
    //prints error message
    public void throwError(String message) {
        System.out.println("Error: "+message);
        System.exit(0);
    }
    //------------------------------------------------------------------------------------
    //parses file -- recursive
    public void parseXml(Element parentElement, BufferedReader reader) throws IOException {
        Element childElement = new Element();   //child element of current parent
        String name = "";                       //tag name
        String value = "";                      //attribute value
        state = State.CONTENT;                  //set to default state
        char ch;                                //current character
        int c;									//loop condition
        
        //the following loop reflects the state machine described in the project report 
        //for greater detail on the implementation, see report 
        while((c = reader.read()) != -1) {      //while the document isn't done
            ch = (char)c;
            switch (state) {
                case CONTENT:
                    if (!isWhitespace(ch)) {
                        if (ch == '<') {
                            state = State.TAG_START;
                        } else {
                            throwError("'<' is expected");
                        }
                    }
                    break;
                case TAG_START:
                    if (ch == '/') {
                        state = State.TAG_CLOSE;
                    } else {
                        if (isNameStartChar(ch)) {
                            name = Character.toString(ch);
                            state = State.TAG_NAME;
                        } else {
                            throwError("Tag name is expected");
                        }
                    }
                    break;
                case TAG_CLOSE:
                    if (isNameStartChar(ch)) {
                        name = Character.toString(ch);
                        state = State.TAG_CLOSE_NAME;
                    } else {
                        throwError("Tag name is expected");
                    }
                    break;
                case TAG_CLOSE_NAME:
                    if (isNameChar(ch)) {
                        name += ch;
                    } else {
                        if (ch == '>') {
                            if (!name.equals(parentElement.getName())) {
                                throwError("Closing tag mismatch");
                            }
                            return;
                        } else {
                            throwError("Invalid tag name");
                        }
                    }
                    break;
                case TAG_NAME:
                    if (isNameChar(ch)) {
                        name += ch;
                    } else {
                        childElement = new Element(name);
                        if (isWhitespace(ch)) {
                            state = State.TAG_BODY;
                        } else {
                            if (ch == '/') {
                                state = State.TAG_BODY_END;
                            } else {
                                if (ch == '>') {
                                    parseXml(childElement, reader);			//recursion
                                    parentElement.addChild(childElement);	//add child
                                    state = State.CONTENT;
                                } else {
                                    throwError("Invalid tag name");
                                }
                            }
                        }
                    }
                    break;
                case TAG_BODY:
                    if (!isWhitespace(ch)) {
                        if (isNameStartChar(ch)) {
                            name = Character.toString(ch);
                            state = State.ATTR_NAME;
                        } else {
                            if (ch == '/') {
                                state = State.TAG_BODY_END;
                            } else {
                                if (ch == '>') {
                                    parseXml(childElement, reader);			//recursion
                                    parentElement.addChild(childElement);	//add child
                                    state = State.CONTENT;
                                } else {
                                    throwError("Invalid attribute name");
                                }
                            }
                        }
                    }
                    break;
                case ATTR_NAME:
                    if (isNameChar(ch)) {
                        name += ch;
                    } else {
                        if (ch == '=') {
                            state = State.ATTR_VALUE_START;
                        } else {
                            throwError("Invalid attribute name");
                        }
                    }
                    break;
                case ATTR_VALUE_START:
                    if (ch == '"') {
                        value = "";
                        state = State.ATTR_VALUE;
                    } else {
                        throwError("'\"' is expected");
                    }
                    break;
                case ATTR_VALUE:
                    if (ch == '"') {
                        childElement.setAttribute(name, value);		//add attribute
                        state = State.ATTR_VALUE_END;
                    } else {
                        value += ch;
                    }
                    break;
                case ATTR_VALUE_END:
                    if (isWhitespace(ch)) {
                        state = State.TAG_BODY;
                    } else {
                        if (ch == '/') {
                            state = State.TAG_BODY_END;
                        } else {
                            if (ch == '>') {
                                parseXml(childElement, reader);			//recursion
                                parentElement.addChild(childElement);	//add child
                                state = State.CONTENT;
                            } else {
                                throwError("Invalid tag content");
                            }
                        }
                    }
                    break;
                case TAG_BODY_END:
                    if (ch == '>') {
                        parentElement.addChild(childElement);	//add child
                        state = State.CONTENT;
                    } else {
                        throwError("'>' is expected");
                    }
                    break;
            }
        }
    }
}
