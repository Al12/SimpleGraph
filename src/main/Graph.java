package main;

import java.util.List;

public interface Graph<T> {

    /**
     * Adds vertex to the graph.
     * @return returns 'true' if this graph did not already contain the specified vertex.
     */
    boolean addVertex(T vertex);

    /**
     * Adds edge to the graph.
     */
    boolean addEdge(T first, T second);

    /**
     * Returns a list of edges between 2 vertices (path doesn’t have to be optimal).
     */
//    TODO: think on best representation of "a list of edges"
    List<T> getPath(T first, T second);
}
