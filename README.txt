README FOR TERM PROJECT		Liz Demin

All functionality of the program is described in the project report.
Code details are discussed here.

Contents:
	ParserTest.java
	Parser.java
	Element.java
	Test Files
	Attribution

//------------------------------------------------------------------------------------
ParserTest.java

IMPORTANT: This is the file that the program is run from. The program has two options: 

************************************************************
OPTION 1: The default method of reading in the file is as follows:

File file = new File(“fileName.xml");
BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

This is the default method of running the program, and will only run the file specified. In this method, if you want to parse a different file, the file name in the program must be changed. Note that it needs to be in the correct directory.
************************************************************
OPTION 2: (probably preferred) If it is run from the command line, then the method of reading in the file must be as follows: 

BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

This method allows the program to take in whatever input from the standard input, whether its direct text or a file. 
************************************************************

I apologize for having to do it this way — I wrote the program in Eclipse and later realized that most people would probably run it from the command line, so I figured it would be wise to add in the option of having standard input possible. 


//------------------------------------------------------------------------------------
Parser.java 

The parser works as intended with no known errors or bugs. All input errors are handled with the throwError() method. 
To avoid messy code, I did not put in excessive comments in the parseXml method. The way it works is explained in the project report. The state machine is implemented exactly as described. All of the places where something is added to the tree (a child or an attribute) is noted by a small comment.


//------------------------------------------------------------------------------------
Element.java

The element class works as intended with no known errors or bugs. Whitespace, line breaks, and carriage returns are properly handled.

IMPORTANT NOTE: All XML documents have a starting element that is invisible and not a part of the main text body — this element is called the document element. The document element can have at most one child, which is the root element of the document. My implementation is conscious of the document element and always expects it as the first element of a document, and this is evident in the code. (in case there is confusion as to why the “first” element can mysteriously only have one child and why it doesn’t show up in the text)

OTHER IMPORTANT NOTE: In XML, there are two ways to close tags. For example,
	<foo></foo> and
	<foo/>
Since with the modified syntax there cannot be any text inside of the tag, the parser will abbreviate <foo></foo> to just </foo> because they syntactically mean the same thing (both are empty tags). However, the case is different if there is a tag inside of a tag. For example: 
	<foo>
		<bar>
		</bar>
	</foo>
The parser will output this as this: 
	<foo>
		<bar/>
	</foo>
Abbreviating the <bar> tag but not the <foo> tag, because the <foo> tag is not empty in this case.


//------------------------------------------------------------------------------------
EXPLANATION OF TEST FILES

testFile1.xml
This XML file is a mockup of what a real config file could look like. The file itself is completely random and is not meant to make any sense by its contents. It is used to show that indeed the parser can properly handle a realistic file with realistic information, where the final output is accurate and useful.

testFile2.xml
This XML file is nonsensical but it demonstrates possible combinations of tags and attributes allowed in the modified syntax (in order as they appear in the file): a self-closing tag, a self-closing tag with an attribute, an empty opening and closing tag, a tag with an attribute and another tag inside of it, a single character tag, and a self-closing tag with multiple attributes. In addition to this, the tags have various spacing and line breaks to show that whitespace is properly handled.

all files in directory ‘Test Files’ labeled with “error-……” 
These XML files are each labeled with a type of error that the parser can encounter. These test files serve as an exhaustive test for the parser’s error handling. Each file contains a short snippet with one particular error that the parser will catch when passing them. The file’s name corresponds to the type of error that it holds.

 
//------------------------------------------------------------------------------------
ATTRIBUTION

All parts of this project I wrote myself, including Parser.java, Element.java, TestParser.java, the project report, all test files, and this README.

I used numerous stackoverflow topics to look up simple syntax/concept things but did borrow code from any other source.

Thank you for reading!

