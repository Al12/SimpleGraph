package test;

import main.DirectedGraph;
import main.UndirectedGraph;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {

    @org.junit.jupiter.api.Test
    void addVertex() {
        UndirectedGraph<Integer> emptyGraph = new UndirectedGraph<>();

        Integer vertex = 1;

        assertTrue(emptyGraph.addVertex(vertex), "Empty graph should return true on adding new vertex");
        assertFalse(emptyGraph.addVertex(vertex), "Non-empty graph should return false on adding existing vertex");
    }

    @org.junit.jupiter.api.Test
    void addEdge() {
        UndirectedGraph<Integer> newGraph = new UndirectedGraph<>();
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
        UndirectedGraph<Integer> newGraph = new UndirectedGraph<>();
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
        Assertions.assertEquals(List.of(vertexFirstPathTwo, vertexOne),
                newGraph.getPath(vertexFirstPathTwo, vertexOne),
                "Reverse vertex to vertex path should be found");
        Assertions.assertEquals(List.of(vertexOne, vertexFirstPathTwo, vertexFour),
                newGraph.getPath(vertexOne, vertexFour),
                "Correct path out of two should be found");
        Assertions.assertEquals(List.of(vertexFirstPathTwo, vertexFour, vertexSecondPathThree),
                newGraph.getPath(vertexFirstPathTwo, vertexSecondPathThree),
                "Correct path out of two should be found");

//        adding a path
        newGraph.addEdge(vertexFirstPathTwo, vertexSecondPathTwo);
        Assertions.assertEquals(List.of(vertexFirstPathTwo, vertexSecondPathTwo),
                newGraph.getPath(vertexFirstPathTwo, vertexSecondPathTwo),
                "New path should be found");

        Integer singleVertex = 999;
        newGraph.addVertex(singleVertex);
        Assertions.assertEquals(Collections.emptyList(),
                newGraph.getPath(vertexOne, singleVertex),
                "Empty list should be returned for impossible path");
    }
}