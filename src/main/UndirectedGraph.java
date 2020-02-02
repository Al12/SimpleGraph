package main;

public class UndirectedGraph<T> extends DirectedGraph<T> {
    @Override
    public boolean addEdge(T first, T second) {
        //adding edges in both directions, only difference
        boolean edgeDirectAdd = super.addEdge(first, second);
        boolean edgeReverseAdd = super.addEdge(second, first);
        return edgeDirectAdd || edgeReverseAdd;
    }
}
