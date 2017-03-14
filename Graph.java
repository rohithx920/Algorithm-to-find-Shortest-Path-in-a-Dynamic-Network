/**
     *
     * @author Rohith
     */
    // Graph.java
    // Graph code, modified from code by Mark A Weiss.
    // Computes Unweighted shortest paths.

    //import graph.HeapSort;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.StringTokenizer;
    import java.util.Map.Entry;

    import java.util.Collection;
    import java.util.Collections;
    import static java.util.Collections.list;
    import java.util.List;
    import java.util.Queue;
    import java.util.Map;
    import java.util.LinkedList;
    import java.util.HashMap;
    import java.util.Iterator;
    import java.util.ListIterator;
    import java.util.NoSuchElementException;
    import java.util.Scanner;
    import java.util.TreeMap;



    // Used to signal violations of preconditions for
    // various shortest path algorithms.
class HeapSort 
{
   
   public void buildMinHeap(Vertex[] v, int size) {
		
		for( int i=size/2; i>=1;i--){
			
			minHeapify(v,i,size);
		}
	}

	public void minHeapify(Vertex[] v, int i, int n) {
		Vertex[] A =v;
		int left=(2*i);
		int right=left+1;
		int min = i;
		if(left<=n ){
			
			if( A[left].dist<A[i].dist ){
			min=left;
			}
		}
		if(right<=n  ){
			if( A[right].dist<A[min].dist){
			min=right;
			}
		}
		if(min!=i){
			
			Vertex x = A[i];
			A[i]=A[min];
			A[min]=x;
			minHeapify(A,min,n);
		}
	}
	public Vertex heapExtractMin(Vertex[] v, int n){
		if(n<1){
			
		return null;
		}
		Vertex min=v[1];
		v[1]=v[n];
		return min;
	}
    
    
    
    
}
    class GraphException extends RuntimeException
    {
        public GraphException( String name )
        {
            super( name );
        }
    }

    // Represents a vertex in the graph.
    class Vertex
    {
        public String     name;   // Vertex name
        public List<Vertex> adj;    // Adjacent vertices
        public Vertex     prev;   // Previous vertex on shortest path
        public float        dist;   // Distance of path
        
        public Vertex( String nm )
          { name = nm; adj = new LinkedList<Vertex>( ); reset( ); }

        public void reset( )
          { dist = Graph.INFINITY; prev = null; }    

    }
    //Edge class 
    class Edge
    {
        public String name; //Edge name
        public Vertex tail;// Edge tail
        public Vertex head;// Edge head
        public float t_t;// Edge time
        public String status;// Status of Edge 
        //public List<Edge> listofedges=new LinkedList<Edge>() ;
        //public ArrayList<Edge> listofedges = new ArrayList<Edge>();

        public Edge()
        {        
        }
        //parmaterized constructor 
         public Edge(String name,Vertex tail,Vertex head,float t_t,String status){

             this.name=name;
             this.tail=tail;
             this.head=head;
             this.t_t=t_t;
             this.status=status;

        }


    }
    // Graph class: evaluate shortest paths.
    //
    // CONSTRUCTION: with no parameters.
    //
    // ******************PUBLIC OPERATIONS**********************
    // void addEdge( String v, String w )
    //                              --> Add additional edge
    // void printPath( String w )   --> Print path after alg is run
    // void unweighted( String s )  --> Single-source unweighted
    // ******************ERRORS*********************************
    // Some error checking is performed to make sure graph is ok,
    // and to make sure graph satisfies properties needed by each
    // algorithm.  Exceptions are thrown if errors are detected.
    
     //Graph class
    public class Graph
    {
        public static final int INFINITY = Integer.MAX_VALUE;
        //maps Vertexname to Vertex
        private Map<String,Vertex> vertexMap = new TreeMap<String,Vertex>( );
       //Helps to get Vertex status 
        private Map<Vertex,String> vertex_status=new HashMap<Vertex,String>( );
        //Maps Edgename to Edge
        private Map<String,Edge> edgeMap=new TreeMap<String,Edge>( );
        //vertex color
        //private Map<Vertex,String> vertex_color=new HashMap<Vertex,String>( );
        int time=0;
        //private Map<Vertex,String> vertex_dt=new HashMap<Vertex,String>( );
        //private Map<Vertex,String> vertex_ft=new HashMap<Vertex,String>( );
        static ArrayList<String> reachable;
        static ArrayList<String> templist;
        String temparray[];
        static ArrayList<String> knownvertex=new ArrayList<String>();
        /**
         * Add a new edge to the graph.
         */
        //This method Creates a new edge
        public void addEdge( String tailvertex, String headvertex, Float transmit_time )
        {
            Vertex v = getVertex( tailvertex );
            Vertex w = getVertex( headvertex );
            String edgename=tailvertex+headvertex;
            Edge edge1=edgeMap.get(edgename);
            //if Edge already exists update it's trasmit time
            if(edge1 != null)
            {
                 edge1.t_t=transmit_time;
             
            }
            //create a new Edge 
            else{
                
            v.adj.add( w );
            vertex_status.put(v, "UP");
            vertex_status.put(w,"UP");
            Edge edge=new Edge(v.name+w.name,v,w,transmit_time,"UP");
            edgeMap.put(tailvertex+headvertex, edge);
            
        }
        }
        //This method deleteEdge
        public void deleteEdge( String tailvertex, String headvertex)
        {
            Vertex v = getVertex( tailvertex );
            Vertex w = getVertex( headvertex );
            v.adj.remove(w);
            System.out.println("removed edge");
            System.out.println(tailvertex+" to "+headvertex);
            System.out.println("----------");
            

        }
        //This method sets Edgestatus to down
        public void edgeDown( String tailvertex, String headvertex)
        {
            Vertex v = getVertex( tailvertex );
            Vertex w = getVertex( headvertex );
            Edge edge=edgeMap.get(tailvertex+headvertex);
            System.out.println(edge.status);
            //Set edgestatus to "Down"
            edge.status="DOWN";
            System.out.println(edge.status);
        }
        //This method sets edgestatus to UP
        public void edgeUp( String tailvertex, String headvertex)
        {
            Vertex v = getVertex( tailvertex );
            Vertex w = getVertex( headvertex );
            Edge edge=edgeMap.get(tailvertex+headvertex);
            System.out.println(edge.status);
            edge.status="UP";
        }
        //This mehod sets vertexstatus to Down 
        public void vertexDown(String vertex)
        {  
            Vertex v = getVertex( vertex );
            vertex_status.put(v, "DOWN");

        }
        //This method sets vertexstatus to Up
        public void vertexUp(String vertex)
        {
            Vertex v = getVertex( vertex );
            vertex_status.put(v, "UP");
        }


        /**
         * Driver routine to print total distance.
         * It calls recursive routine to print shortest path to
         * destNode after a shortest path algorithm has run.
         */
        //This method is used to print the shortest path
        public void printPath( String destName )
        {
            Vertex w = vertexMap.get( destName );
            if( w == null )
                throw new NoSuchElementException( "Destination vertex not found" );
            else if( w.dist == INFINITY )
                System.out.println( destName + " is unreachable" );
            else
            {
                System.out.print( "(Distance is: " + w.dist + ") " );
                printPath( w );
                System.out.println( );
            }

        }

        /**
         * If vertexName is not present, add it to vertexMap.
         * In either case, return the Vertex.
         */
        /*This method is used to get vertex from it's name and creates new 
        vertex if not available */
        private Vertex getVertex( String vertexName )
        {
            Vertex v = vertexMap.get( vertexName );
            if( v == null )
            {
                v = new Vertex( vertexName );
                vertexMap.put( vertexName, v );
            }
                
            return v;
        }
        //This is used to get Edge from its name
            private Edge getEdge( String edgeName )
        {
            Edge e = edgeMap.get(edgeName);
            return e;
        }

        /**
         * Recursive routine to print shortest path to dest
         * after running shortest path algorithm. The path
         * is known to exist.
         */
            //This prints the shortest path
        private void printPath( Vertex dest )
        {
            if( dest.prev != null )
            {
                printPath( dest.prev );
                System.out.print( " to " );
            }
            System.out.print( dest.name );
        }
        //This method prints the Graph
        private void printGraph() 
	{
            
		for(Entry<String, Vertex> entry : vertexMap.entrySet()){
    		String v= entry.getKey();
                //v hold current vertex
    		System.out.print(v);

                if(vertex_status.get(entry.getValue()).equals("DOWN"))
    			System.out.println(" DOWN");
    		else
		{
    			System.out.println("\t");
    		}
                String tailname=entry.getValue().name;
                Vertex x=entry.getValue();
                templist=new ArrayList<String>();
                   
                for(Vertex v1 : x.adj)
                {
                    templist.add(v1.name);
                }
                
                Collections.sort(templist);
               
                for(String name: templist){
			
		Vertex v1=vertexMap.get(name); 
                    String edgename=tailname+v1.name;
                    Edge edge=edgeMap.get(edgename);
                    String headname = edgename.replaceAll(tailname,"");
                    if(tailname.equals(v))
				{
        			System.out.print("\t");
        			System.out.print(headname);
        			System.out.print("\t"+edge.t_t);
        			if(edge.status.equals("DOWN"))
						System.out.println(" DOWN");
        			else
					{
            			System.out.println("\t");
            		}
                }
    		
    		
    	
                }
	}
        }

        /**
         * Initializes the vertex output info prior to running
         * any shortest path algorithm.
         */
        private void clearAll( )
        {
            for( Vertex v : vertexMap.values( ) )
                v.reset( );
            
        }
        public void remove(){
            knownvertex.clear();
        }

        /**
         * Single-source unweighted shortest-path algorithm.
         */

        public void toFindShortestPath(String startName){
		for(Entry<String, Vertex> entry : vertexMap.entrySet()){
    		Vertex v= entry.getValue();
                
    		v.reset();
		}
		Vertex v= vertexMap.get(startName);
		if( v == null )
                throw new NoSuchElementException( "Start vertex not found" );
                
                v.dist=0.0f;
		
		int size=0;
		for(Entry<String, Vertex> entry : vertexMap.entrySet()){
    		Vertex w= entry.getValue();
    		if(vertex_status.get(w).equals("UP"))
    			size=size+1;
                
    		
		}
		
		Vertex[] vertex_array =new Vertex[size+1];
		int i=1;
		for(Entry<String, Vertex> entry : vertexMap.entrySet()){
    		Vertex w= entry.getValue();
    		if(vertex_status.get(w).equals("UP")){
    		vertex_array[i]=w;
    		i++;
    		}
		}
                
                HeapSort obj=new HeapSort();
                
                while(true){
		v= obj.heapExtractMin(vertex_array,size);
                
                Edge e=edgeMap.get(startName+v.name);
		//v.dist=v.prev.dist+e.t_t;
                if(v!=null){
			size=size-1;
		
			for( Vertex p : v.adj){
                            String edgename=v.name+p.name;
                            Edge edge=edgeMap.get(edgename);
				if(edge.status.equals("UP")){
    			Vertex w = vertexMap.get(p.name);
    			if(vertex_status.get(w).equals("UP")){
    			float f=edge.t_t+v.dist;
                        //System.out.println("---------------");
                        //System.out.println(f);
                            if(w.dist>f){
    			w.dist=edge.t_t+v.dist;
    			
    			//System.out.println(edge.name);
    			//System.out.println(w.dist+"="+v.dist+"+"+edge.t_t);
    			
    			w.prev=v;
    			}
    			}
			}
			}
			if(size==0){
                            //Break the while loop
                            break;
                            
                        }
                        else{
                            obj.buildMinHeap(vertex_array, size);
                        }
			
		}
		
	
	}
                
        }


        /**
         * Process a request; return false if end of file.
         */
        
public static boolean processRequest( String Headname,String Tailname, Graph g )
        {
            try
            {
                //System.out.print( "Enter start node: " );
                String startName = Headname;

                //System.out.print( "Enter destination node: " );
                String destName = Tailname;

                g.toFindShortestPath( startName );
                g.printPath( destName );
            }
            catch( NoSuchElementException e )
              { 
                  System.out.println(e);
                  return false; }
            catch( GraphException e )
              { System.err.println( e ); }
            return true;
        }
/*
This method tell what all verticies are reachable.
Ex: Assume Edges from  a->b, a->c ,b->c
If edge from a->c is down still a can reach c i.e(a->b->c)
logic: Take vertex from vertexmap
1)check status="up"
2)if status is up call adj(); 
3)In adj():vertex.adj is called and we select first vertex.
4)if its status is "up". we check for whether that vertex is already known
5)if it is not then  we will check status of edge
6)we will check reachable list wheter vertex already exist or not 
7)Again we call the adj()
Running time :O(v+e)*v => O(v*v)
Big O(v square)

*/
private void reachableVertices() 
	{
		for(Entry<String, Vertex> entry : vertexMap.entrySet())
		{
                    
                    knownvertex.clear();
                   
	   	if(vertex_status.get(entry.getValue()).equals("UP"))
                {	System.out.println(entry.getKey());
    		
    		adj(entry.getValue());
    		System.out.println("");
    	}
	}
        }
public  void adj(Vertex ver){
    reachable = new ArrayList<String>();
    templist=new ArrayList<>();
    knownvertex.add(ver.name); 
    for(Vertex v1 : ver.adj )
    {
        templist.add(v1.name);
    }
    Collections.sort(templist);
    for(String name: templist){
        Vertex v=vertexMap.get(name);
        if(vertex_status.get(v).equals("UP")){
        if(!knownvertex.contains(v.name)){
                           String e=ver.name+v.name;
                           
    			if(edgeMap.get(e).status.equals("UP"))
				{
					  //add "v" to arraylist of type string
                                    if(!reachable.contains(v.name))      
                                    {
                                        reachable.add(v.name);                                   
					System.out.println("    "+v.name);
                                    }
                                        adj(v);
        		}
                        
        	}
        }
    }
}
/*public void dfs(){
    
    
    for(Entry<String, Vertex> entry : vertexMap.entrySet())
    {
                    if(vertex_status.get(entry.getValue()).equals("UP")){
                        //System.out.println(entry.getKey());
                    vertex_color.put(entry.getValue(), "white");
                    entry.getValue().prev=null;
                    }
    			
    		
    }
    for(Entry<String, Vertex> entry : vertexMap.entrySet())
    {
    if(vertex_status.get(entry.getValue()).equals("UP")){
        if(vertex_color.get(entry.getValue()).equals("white")){
            dfs_vist(entry.getValue());
        }
    }
                    
}
}
public void dfs_vist(Vertex vertex){
    vertex_color.put(vertex, "gray");
    time=time+1;
    String st=Integer.toString(time);
    vertex_dt.put(vertex,st);
    for(Vertex v:vertex.adj)
    {
    if(vertex_status.get(v).equals("UP")){
        if(vertex_color.get(v).equals("white")){
            v.prev=vertex;
            dfs_vist(v);
        }
        vertex_color.put(vertex, "black");
        time=time+1;
        String st1=Integer.toString(time);
        vertex_ft.put(v, st1);
    }
}
} 
*/
         public static void main( String [ ] args )
        {
            Graph g = new Graph( );
            try
            {
                FileReader fin = new FileReader( args[0] );
                Scanner graphFile = new Scanner( fin );
                Scanner s= new Scanner(System.in);

                // Read the edges and insert
                String line;
                while( graphFile.hasNextLine( ) )
                {
                    line = graphFile.nextLine( );
                    StringTokenizer st = new StringTokenizer( line );

                    try
                    {
                        if( st.countTokens( ) != 3 )
                        {
                            System.err.println( "Skipping ill-formatted line " + line );
                            continue;
                        }
                        String source  = st.nextToken( );
                        String dest    = st.nextToken( );
                        float dist=Float.parseFloat(st.nextToken());
                        g.addEdge( source, dest,dist);
                        g.addEdge(dest,source, dist);

                    }
                    catch( NumberFormatException e )
                      { System.err.println( "Skipping ill-formatted line " + line ); }
                     
                }
                        System.out.println( "File read..." );
                        System.out.println( g.vertexMap.size( ) + " vertices" );
                        System.out.println(g.edgeMap.size()+"edges");
                       
                        while(true)
			{
				System.out.println("Enter Query");
				String str1 = s.nextLine();
				StringTokenizer str = new StringTokenizer( str1 );
			    try{
				switch(str.nextToken( ).toLowerCase( ))
				{
					case "addedge":
					  if (str.countTokens( ) ==3) 
					  {
					  g.addEdge(str.nextToken( ), str.nextToken( ), Float.parseFloat(str.nextToken( )));
						break;
					  }
					  else{
						  System.out.println("arguments does not match");
                                                  break;
					  }
					case "deleteedge":
					   if(str.countTokens()== 2){	
                                            g.deleteEdge(str.nextToken( ), str.nextToken( ));
						break;
                                           }
                                           else{
						  System.out.println("arguments does not match");
                                                  break;
					  }
					case "edgedown":
					    if(str.countTokens()==2){	
                                            g.edgeDown(str.nextToken( ), str.nextToken( ));
						break;
                                            }
                                            else{
						  System.out.println("arguments does not match");
                                                  break;
					    }
					case "edgeup":
                                            if(str.countTokens()==2){
						g.edgeUp(str.nextToken( ), str.nextToken( ));
						break;
                                            }
                                            else{
						  System.out.println("arguments does not match");
                                                  break;
					  }
					case "vertexdown":
						if(str.countTokens()==1){
                                                g.vertexDown(str.nextToken( ));
						break;
                                                }
                                                else{
						  System.out.println("arguments does not match");
                                                  break;
					  }
					case "vertexup":
						if(str.countTokens()==1){
                                                g.vertexUp(str.nextToken( ));
						break;
                                                }
                                                else{
						  System.out.println("arguments does not match");
                                                  break;
					        }
					case "path":
                                            if (str.countTokens( ) ==2) 
					  {
                                              processRequest(str.nextToken(),str.nextToken(),g);
                                              break;
                                          }
                                            else{
                                                System.out.println("Arguments does not match ");
                                                break;
                                            }
                                        case "print":
						if(str.hasMoreElements()){
                                                    System.out.println("arguments does not match");
                                                    break;
                                                }
                                                else{
                                                g.printGraph();
						break;
                                                }
					case "reachable":
					      if(str.hasMoreElements()){
                                                    System.out.println("arguments does not match");
                                                    break;
                                                }	
                                              else{
                                              g.reachableVertices();
						break;
                                              }
					case "quit":
					   if(str.hasMoreElements()){
                                                    System.out.println("arguments does not match");
                                                    break;
                                                }
                                           else{
                                            System.exit(0);
						break;
                                            }
					default:
						System.out.println("Entered Query is Unknown");
				}
                            }
                            catch(Exception e){
                                System.err.println(e);
                            }
			}
                    
                                }
            catch( IOException e )
               { 
                   System.err.println( e ); 
               }
        }
             
             
            /*
             Scanner in = new Scanner( System.in );
             while( processRequest( in, g ) )
                 ;
            */
            
        }
    