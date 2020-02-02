package test;

import main.DirectedGraph;
import main.UndirectedGraph;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @org.junit.jupiter.api.Test
    void addVertex() {
        DirectedGraph<Integer> emptyGraph = new DirectedGraph<>();

        Integer vertex = 1;

        assertTrue(emptyGraph.addVertex(vertex), "Empty graph should return true on adding new vertex");
        assertFalse(emptyGraph.addVertex(vertex), "Non-empty graph should return false on adding existing vertex");
    }

    @org.junit.jupiter.api.Test
    void addEdge() {
        DirectedGraph<Integer> newGraph = new DirectedGraph<>();
        Integer vertexOne = 1;
        Integer vertexTwo = 2;
        Integer vertexThree = 3;

        newGraph.addVertex(vertexOne);
        newGraph.addVertex(vertexTwo);

        assertTrue(newGraph.addEdge(vertexOne, vertexTwo), "A valid edge should be added to the graph");
        assertFalse(newGraph.addEdge(vertexOne, vertexTwo), "Adding a repeated edge shouldn't fail");
        assertTrue(newGraph.addEdge(vertexOne, vertexOne), "A loop edge should be added to the graph");
        assertThrows(IllegalArgumentException.class,
                () -> newGraph.addEdge(vertexOne, vertexThree),
                "Adding edge to a vertex not in graph should throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class,
                () -> newGraph.addEdge(vertexThree, vertexTwo),
                "Adding edge from a vertex not in graph should throw IllegalArgumentException");
    }

    @org.junit.jupiter.api.Test
    void getPath() {
        DirectedGraph<Integer> newGraph = new DirectedGraph<>();
        Integer vertexOne = 1;
        Integer vertexFirstPathTwo = 12;
        Integer vertexSecondPathTwo = 22;
        Integer vertexSecondPathThree = 23;
        Integer vertexFour = 4;

        assertThrows(IllegalArgumentException.class,
                () -> newGraph.getPath(vertexOne, vertexFirstPathTwo),
                "Finding path between vertices not in graph should throw IllegalArgumentException");

        newGraph.addVertex(vertexOne);
        newGraph.addVertex(vertexFirstPathTwo);
        newGraph.addVertex(vertexSecondPathTwo);
        newGraph.addVertex(vertexSecondPathThree);
        newGraph.addVertex(vertexFour);

//        1 -> 12 -> 4
        newGraph.addEdge(vertexOne, vertexFirstPathTwo);
        newGraph.addEdge(vertexFirstPathTwo, vertexFour);
//        1 -> 22 -> 23 -> 4
        newGraph.addEdge(vertexOne, vertexSecondPathTwo);
        newGraph.addEdge(vertexSecondPathTwo, vertexSecondPathThree);
        newGraph.addEdge(vertexSecondPathThree, vertexFour);

        //simple direct path
        Assertions.assertEquals(List.of(vertexOne, vertexFirstPathTwo),
                newGraph.getPath(vertexOne, vertexFirstPathTwo),
                "Direct vertex to vertex path should be found");
        Assertions.assertEquals(List.of(vertexOne, vertexFirstPathTwo, vertexFour),
                newGraph.getPath(vertexOne, vertexFour),
                "Correct path out of two should be found");
        Assertions.assertEquals(Collections.emptyList(),
                newGraph.getPath(vertexFirstPathTwo, vertexSecondPathThree),
                "Empty list should be returned for impossible path");

//        adding a path
        newGraph.addEdge(vertexFirstPathTwo, vertexSecondPathTwo);
        Assertions.assertEquals(List.of(vertexFirstPathTwo, vertexSecondPathTwo, vertexSecondPathThree),
                newGraph.getPath(vertexFirstPathTwo, vertexSecondPathThree),
                "New path should be found");

        Assertions.assertEquals(Collections.emptyList(),
                newGraph.getPath(vertexSecondPathTwo, vertexFirstPathTwo),
                "Empty list should be returned for impossible path");
    }

    @org.junit.jupiter.api.Test
    void testLoops() {
        DirectedGraph<Integer> newGraph = new DirectedGraph<>();
        Integer vertexOne = 1;
        newGraph.addVertex(vertexOne);
        Assertions.assertEquals(Collections.emptyList(),
                newGraph.getPath(vertexOne, vertexOne),
                "Empty list should be returned for nonexistant path");

        newGraph.addEdge(vertexOne, vertexOne);
        Assertions.assertEquals(List.of(vertexOne, vertexOne),
                newGraph.getPath(vertexOne, vertexOne),
                "Looping edge can be used for path");

    }

    @org.junit.jupiter.api.Test
    void diviedGraphPaths() {
        DirectedGraph<Integer> newGraph = new DirectedGraph<>();
        Integer vertexOne = 1;
        Integer vertexTwo = 2;
        Integer vertexThree = 3;
        Integer vertexFour = 4;

        newGraph.addVertex(vertexOne);
        newGraph.addVertex(vertexTwo);
        newGraph.addVertex(vertexThree);
        newGraph.addVertex(vertexFour);

//      1 -> 2
        newGraph.addEdge(vertexOne, vertexTwo);
//      3 -> 4
        newGraph.addEdge(vertexThree, vertexFour);

        Assertions.assertEquals(List.of(vertexOne, vertexTwo),
                newGraph.getPath(vertexOne, vertexTwo),
                "Path in first half of the graph should be found");
        Assertions.assertEquals(List.of(vertexThree, vertexFour),
                newGraph.getPath(vertexThree, vertexFour),
                "Path in second half of the graph should be found");
    }
}