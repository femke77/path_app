/*
Name: Megan Meyers
Date: 11/22/15
CS 282 Project 3 - Directed Weighted Graphs
Class description: Class DisPar creates DisPar objects with two variables. DisPar objects hold the shortest path final weight total and the parent vertex through which the final vertex is reached. Implements serializable for object output and input streams. 
  
 */
package PathApp;

import java.io.*;

////////////////////////////////////////////////////////////////
public class DistPar implements Serializable // distance and parent
{                      // items stored in sPath array

    public int distance;   // distance from start to this vertex
    public int parentVert; // current parent of this vertex
// -------------------------------------------------------------

    public DistPar(int pv, int d) // constructor
    {
        distance = d;
        parentVert = pv;
    }
// -------------------------------------------------------------
}  // end class DistPar
///////////////////////////////////////////////////////////////
