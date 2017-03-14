*************************************************************************************
Name:Mogili Rohith Sagar

*************************************************************************************
README:
programming language: JAVA
compiler version: jdk1.8.0_71
How to run the program:
Graph:
1)To compile 
javac Graph.java
2)To Run
java Graph filename.txt EX:java Graph network.txt

NOTE: Input files must be in the working the directory.

Class Files:

1)Graph
2)Vertex
3)Edge
4)HeapSort

All this class are present only in one file namly Graph.java but when we compile
we can see all the class files in the working directory

Program design:

1)Vertex class will have name its adjecency list and previous vertex and its distance.
  stautus is present in hashmap.
2)Edge class will have edgename(Tailname+Headname) status and distance
3)Graph contains methods like 
addEdge( String tailvertex, String headvertex, Float transmit_time )
deleteEdge( String tailvertex, String headvertex)
edgeDown( String tailvertex, String headvertex)
edgeUp( String tailvertex, String headvertex)
vertexDown(String vertex)
vertexUp(String vertex)
printPath( String destName )
getVertex( String vertexName )
printPath( Vertex dest )
printGraph()
toFindShortestPath(String startName)
processRequest( String Headname,String Tailname, Graph g )
reachableVertices()
logic:

1)Read the argumented file and create Graph
2)Graph methods are used to modify the graph 
3)To find the shortest path between two vertex use toFindShortestPath method.
which implements dijkstra algorithm
4)priorityqueue is implemented using binaryheap
5)reachable algorithm is explained in comments above the method.

Summary:

Program works good.

Data structure:

Used Hashmap,Treemap,Arraylist,Linkedlist(adj list).

Treemap are used for sorting in alphabetical order
and also used sort() of arrayList.
