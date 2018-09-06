/*
 Name: Megan Meyers
 Date: 11/22/15
 CS 282 Project 3 - Directed Weighted Graphs
 Class description: PathApp class contains main method. A default graph is created on start-up and displayed, followed by a menu to give the user various options for altering, writing, reading graphs. The read and write methods use object serialization to read and write objects to/from a file. There is a method included for turning a char user input into the appropriate array index number, a method for changing edgeweights, as well as methods for adding and deleting edges. The user will not be allowed to add or delete vertices in this version. 
  
 */
package PathApp;

import java.io.*;

public class PathApp {

    protected static Graph theGraph = new Graph();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        theGraph.addVertex('A');     // 0  (start)
        theGraph.addVertex('B');     // 1
        theGraph.addVertex('C');     // 2
        theGraph.addVertex('D');     // 3
        theGraph.addVertex('E');     // 4

        theGraph.addEdge(0, 1, 50);  // AB 50
        theGraph.addEdge(0, 3, 80);  // AD 80
        theGraph.addEdge(1, 2, 60);  // BC 60
        theGraph.addEdge(1, 3, 90);  // BD 90
        theGraph.addEdge(2, 4, 40);  // CE 40
        theGraph.addEdge(3, 2, 20);  // DC 20
        theGraph.addEdge(3, 4, 70);  // DE 70
        theGraph.addEdge(4, 1, 50);  // EB 50

        System.out.println("Shortest paths: ");
        theGraph.path();             // shortest paths
        System.out.println();        //blank line

        while (true) {

            System.out.print("Enter first letter of ");
            System.out.print("(c)hange edge weight, (a)dd edge, (d)elete edge, (f)ind paths, (r)ead from disk, (w)rite to disk, (q)uit: ");
            char choice = UserInput.getChar();
            switch (choice) {
                case 'c':
                    System.out.println("Selected: change edge weight.");
                    change();
                    break;
                case 'a':
                    System.out.println("Selected: add edge");
                    addEdge();
                    break;
                case 'd':
                    System.out.println("Selected: delete edge.");
                    deleteEdge();
                    break;
                case 'q':
                    System.out.println("Goodbye");
                    System.exit(0);
                    break;
                case 'f':
                    System.out.println("Selected: find paths.");
                    findPath();
                    break;
                case 'w':
                    System.out.println("Selected: Write to disk");
                    write();
                    break;
                case 'r':
                    System.out.println("Selected: Read from disk");
                    read();
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }  // end switch
        }  // end while

    }  // end main()
//--------------------------------------------------------------------------

    public static void change() { //user-defined method to change the edgeweight
        System.out.println("Enter 'from' vertex:");
        char fvertex = UserInput.getChar('a', 'e');  //not changing the graph so restrict user input options
        int fvnum = charToInt(fvertex); //user inputs char but program requires int for array index
        System.out.println("Enter 'to' vertex:");
        char tvertex = UserInput.getChar('a', 'e');
        int tvnum = charToInt(tvertex);

        while (theGraph.verifyEdge(fvnum, tvnum) == false) { //check to see that the edge exists. 
            System.out.println("Edge does not exist. ");
            System.out.println("Enter 'from' vertex:");
            fvertex = UserInput.getChar('a', 'e');  //not changing the graph so restrict user input options
            fvnum = charToInt(fvertex); //user inputs char but program requires int for array index
            System.out.println("Enter 'to' vertex:");
            tvertex = UserInput.getChar('a', 'e');
            tvnum = charToInt(tvertex);
        }

        System.out.println("Enter new edge weight: ");
        int eweight = UserInput.getInt(0, theGraph.INFINITY); //make edgeweight zero or postive number not exceeding our INFINITY value. 
        theGraph.addEdge(fvnum, tvnum, eweight);
        System.out.println("The edge from " + fvertex + " to " + tvertex + " has a new edge weight of: " + eweight + ".");
        //format to make it print fvertex and tvertex in uppercase. 
    }
//------------------------------------------------------------------------------

    public static int charToInt(char v) {
        int vertexnum = (int) v - 97;  //convert the letter to ascii - 97: gives you 0 for a, 1 for b, etc....       
        return vertexnum;
    }
//-------------------------------------------------------------------------------

    public static void addEdge() { //adds a new edge, alerts user if edge is already in existence. similar to change method
        System.out.println("Enter 'from' vertex:");
        char fvertex = UserInput.getChar('a', 'e');
        int fvnum = charToInt(fvertex);
        System.out.println("Enter 'to' vertex:");
        char tvertex = UserInput.getChar('a', 'e');
        int tvnum = charToInt(tvertex);

        while (theGraph.verifyEdge(fvnum, tvnum) == true) {
            System.out.println("Edge already exists. ");
            System.out.println("Enter 'from' vertex:");
            fvertex = UserInput.getChar('a', 'e');
            fvnum = charToInt(fvertex);
            System.out.println("Enter 'to' vertex:");
            tvertex = UserInput.getChar('a', 'e');
            tvnum = charToInt(tvertex);
        }
        System.out.println("Enter the weight for the new edge:");
        int weight = UserInput.getInt(0, theGraph.INFINITY);
        theGraph.addEdge(fvnum, tvnum, weight);
        System.out.println("Edge added.");
    }
//----------------------------------------------------------------------------------    

    public static void deleteEdge() { //delete an edge. verify edge exists prior to delete. 
        System.out.println("Enter 'from' vertex:");
        char fvertex = UserInput.getChar('a', 'e');
        int fvnum = charToInt(fvertex);
        System.out.println("Enter 'to' vertex:");
        char tvertex = UserInput.getChar('a', 'e');
        int tvnum = charToInt(tvertex);

        while (theGraph.verifyEdge(fvnum, tvnum) == false) {
            System.out.println("Edge does not exist. ");
            System.out.println("Enter 'from' vertex:");
            fvertex = UserInput.getChar('a', 'e');
            fvnum = charToInt(fvertex);
            System.out.println("Enter 'to' vertex:");
            tvertex = UserInput.getChar('a', 'e');
            tvnum = charToInt(tvertex);
        }
        theGraph.deleteEdge(fvnum, tvnum);
        System.out.println("Edge deleted.");
    }
//----------------------------------------------------------------------------------   

    public static void findPath() {  //can input any vertex in the graph and find the best path to that vertex 
        System.out.println("Enter vertex to find: ");
        char find = UserInput.getChar('b', 'e'); //exclude 'a' because its unnecessary.
        int vnum = charToInt(find);
        theGraph.path(vnum);
    }
//------------------------------------------------------------------------------------

    public static void read() throws IOException, ClassNotFoundException { //read an object that was written to file 
        System.out.println("Enter the name of the file to read: ");
        String filename = UserInput.getString();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        theGraph = (Graph) in.readObject(); //get the object
        theGraph.displayPaths(); //display it
        in.close();
    }

//------------------------------------------------------------------------------------    
    public static void write() throws IOException { //write an object to file using serialization
        System.out.println("Enter a name for your file: ");
        String filename = UserInput.getString();
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(theGraph);
        out.close();
        System.out.println("Your graph has been written to file: " + filename);
    }

//------------------------------------------------------------------------------------    
  
    
    
}  // end class PathApp
////////////////////////////////////////////////////////////////

