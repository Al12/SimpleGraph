package main;

public class UndirectedGraph<T> extends DirectedGraph<T> {
    @Override
    public boolean addEdge(T first, T second) {
        super.addEdge(second, first);   //adding edges in both directions, only difference
        return super.addEdge(first, second);
    }
}
