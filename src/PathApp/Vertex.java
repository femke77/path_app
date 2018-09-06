/*
Name: Megan Meyers
Date: 11/22/15
CS 282 Project 3 - Directed Weighted Graphs
Class description: Class Vertex creates the vertices that make up the graph. Each vertex has two variables, a label and a boolean for its in-tree status. I added one extra method, getLabel(), which will return the char label associated with the vertex. This class implements serializable for object output and input streams. 
  
 */
package PathApp;

import java.io.*;

public class Vertex implements Serializable {

    public char label;        // label (e.g. 'A')
    public boolean isInTree;
// -------------------------------------------------------------

    public Vertex(char lab) // constructor
    {
        label = lab;
        isInTree = false;
    }
// -------------------------------------------------------------

    public char getLabel() { //my method used for display of the entire path to specified vertex. 
        return label;
    }
//--------------------------------------------------------------
    
}  // end class Vertex
 


////////////////////////////////////////////////////////////////
