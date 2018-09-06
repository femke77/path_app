/*
Name: Megan Meyers
Date: 11/22/15
CS 282 Project 3 - Directed Weighted Graphs
Class description: The Graph class is where the body of the tree is created. Vertices are attached and detached to/from the tree through use of methods provided here. Edges can be verified and edge weights can be changed. The shortest path through the tree or to any vertex can be found using path() or path(int vertex) [overloaded method]. Additional methods used by both path methods are included that identify the minimum distance and update a sPath array that contains the shortest path information for each vertex. There is also a method to display the graph. 
  
 */
package PathApp;

import java.io.*;

public class Graph implements Serializable {

    private final int MAX_VERTS = 20;
    protected final int INFINITY = 1000000;
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][];      // adjacency matrix
    private int nVerts;          // current number of vertices
    private int nTree;           // number of verts in tree
    private DistPar sPath[];     // array for shortest-path data
    private int currentVert;     // current vertex
    private int startToCurrent;  // distance to currentVert

// -------------------------------------------------------------
    public Graph() // constructor
    {
        vertexList = new Vertex[MAX_VERTS];
        // adjacency matrix
        adjMat = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;
        for (int j = 0; j < MAX_VERTS; j++) // set adjacency
        {
            for (int k = 0; k < MAX_VERTS; k++) //     matrix
            {
                adjMat[j][k] = INFINITY;     //     to infinity
            }
        }
        sPath = new DistPar[MAX_VERTS];    // shortest paths
    }  // end constructor
// -------------------------------------------------------------

    public void addVertex(char lab) {
        vertexList[nVerts++] = new Vertex(lab);
    }
// -------------------------------------------------------------

    public void addEdge(int start, int end, int weight) { 
        adjMat[start][end] = weight;  // (directed)
    }
// -------------------------------------------------------------

    public boolean verifyEdge(int start, int end) { //my method for verifying an edge exists
        return adjMat[start][end] != INFINITY;    //edges exists if path is NOT infinity
    }
//--------------------------------------------------------------

    public void deleteEdge(int start, int end) { //my method for deleting and edge
        adjMat[start][end] = INFINITY;   //set edge to infinity
    }
//--------------------------------------------------------------  

    public void path() // find all shortest paths
    {
        int startTree = 0;             // start at vertex 0 = A
        vertexList[startTree].isInTree = true;
        nTree = 1;                     // put it in tree

        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistPar(startTree, tempDist); //creates the array of DistPar's starting with 0 out to nVerts columns 
        }

        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMin();    // get minimum from sPath
            int minDist = sPath[indexMin].distance;

            if (minDist == INFINITY) // if all infinite
            {                        // or in tree,
                System.out.println("There are unreachable vertices");
                break;                   // sPath is complete
            } else {                        // reset currentVert
                currentVert = indexMin;  // to closest vert
                startToCurrent = sPath[indexMin].distance;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath();             // update sPath[] array
        }  // end while(nTree<nVerts)

        displayPaths();                // display sPath[] contents

        nTree = 0;                     // clear tree
        for (int j = 0; j < nVerts; j++) {
            vertexList[j].isInTree = false;
        }
    }  // end path()
// -------------------------------------------------------------

    public void path(int vertex) // find all shortest paths plus gives you complete path to a specified vertex
    {
        int startTree = 0;             // start at vertex 0 = A
        vertexList[startTree].isInTree = true;  //in this project we always start from A so we don't need to update startTree. 
        nTree = 1;                     // put it in tree

        // transfer row of distances from adjMat to sPath
        for (int j = 0; j < nVerts; j++) {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DistPar(startTree, tempDist); //from row 0, col 0,1,2...
        }
        // until all vertices are in the tree
        while (nTree < nVerts) {
            int indexMin = getMin();    // get minimum from sPath
            int minDist = sPath[indexMin].distance;

            if (minDist == INFINITY) // if all infinite
            {                        // or in tree,
                System.out.println("There are unreachable vertices");
                break;                   // sPath is complete
            } else {                        // reset currentVert
                currentVert = indexMin;  // to closest vert
                startToCurrent = sPath[indexMin].distance;
                // minimum distance from startTree is
                // to currentVert, and is startToCurrent
            }
            // put current vertex in tree
            vertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath();             // update sPath[] array
        }  // end while(nTree<nVerts)

        String path = "";
        String rpath = "A";
        while (vertex != 0) { // work backward to reconstruct the sequence of vertices along the shortest path to any vertex
            path += vertexList[vertex].getLabel();
            vertex = sPath[vertex].parentVert;
        }
        for (int i = path.length() - 1; i >= 0; i--) { //reverse the string to print out in the correct order. 
            rpath += path.charAt(i);
        }
        System.out.println("Complete path to the vertex you specified is: " + rpath);

        System.out.println("All paths: \n");
        displayPaths();                // display sPath[] contents
        System.out.println();
        nTree = 0;                     // clear tree
        for (int j = 0; j < nVerts; j++) {
            vertexList[j].isInTree = false;
        }
    }  // end path(int vertex)
// -------------------------------------------------------------

    public int getMin() // get entry from sPath
    {                              //    with minimum distance
        int minDist = INFINITY;        // assume minimum
        int indexMin = 0;
        for (int j = 1; j < nVerts; j++) // for each vertex,
        {                                  // if it's NOT in tree and
            if (vertexList[j].isInTree == false && // smaller than old one
                    sPath[j].distance < minDist) {
                minDist = sPath[j].distance;
                indexMin = j;            // update minimum
            }
        }  // end for
        return indexMin;               // return index of minimum
    }  // end getMin()
// -------------------------------------------------------------

    public void adjust_sPath() {
        // adjust values in shortest-path array sPath
        int column = 1;                // skip starting vertex
        while (column < nVerts) // go across columns
        {
            // if this column's vertex already in tree, skip it
            if (vertexList[column].isInTree) {
                column++;
                continue;
            }
            // calculate distance for one sPath entry
            // get edge from currentVert to column
            int currentToFringe = adjMat[currentVert][column];
            // add distance from start
            int startToFringe = startToCurrent + currentToFringe;
            // get distance of current sPath entry
            int sPathDist = sPath[column].distance;

            // compare distance from start with sPath entry
            if (startToFringe < sPathDist) // if shorter,
            {                            // update sPath
                sPath[column].parentVert = currentVert;
                sPath[column].distance = startToFringe;
            }
            column++;
        }  // end while(column < nVerts)
    }  // end adjust_sPath()
// -------------------------------------------------------------

    public void displayPaths() {
        for (int j = 0; j < nVerts; j++) // display contents of sPath[]
        {
            System.out.print(vertexList[j].label + "="); // B=
            if (sPath[j].distance == INFINITY) {
                System.out.print("inf");                  // inf
            } else {
                System.out.print(sPath[j].distance);      // 50
            }
            char parent = vertexList[sPath[j].parentVert].label;
            System.out.print("(" + parent + ") ");       // (A)
        }
        System.out.println("");
    }
// -------------------------------------------------------------

}  // end class Graph
////////////////////////////////////////////////////////////////

