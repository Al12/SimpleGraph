package main;

import java.util.*;
import java.util.stream.Collectors;

public class DirectedGraph<T> implements Graph<T> {

    Map<T, Set<T>> edges = new HashMap<>();

    @Override
    public boolean addVertex(T vertex) {
        return (edges.putIfAbsent(vertex, new HashSet<>()) == null);
    }

    @Override
    public boolean addEdge(T first, T second) {
        if (edges.containsKey(first) && edges.containsKey(second)) {
            return edges.get(first).add(second);
        }
        throw new IllegalArgumentException(String.format("Cannot add edge (%s, %s) between vertices not in graph",
                first.toString(), second.toString()));
    }

    @Override
    public List<T> getPath(T first, T second) {
        if (!edges.containsKey(first) || !edges.containsKey(second)) {
            throw new IllegalArgumentException(String.format("Cannot find path (%s, %s) between vertices not in graph",
                    first.toString(), second.toString()));
        }
        if (first == second && edges.get(first).contains(second)) {
            //loop edge case
            return List.of(first, second);
        }
//      based on Dijkstra's algorithm
//      Initializing distances map and disposable vertex set
        Map<T, Integer> distance = edges.keySet().stream().collect(Collectors.toMap(v -> v, v -> Integer.MAX_VALUE - 1));
        Map<T, T> shortBackPaths = new HashMap<>();
        Set<T> unmappedVertices = new HashSet<>(edges.keySet());
        // first vertex distance is known and 0
        distance.put(first, 0);
        // repeat n times for each vertex starting with the first
        for (int i = 0; i < distance.size(); ++i) {
            // finding vertex closest to first, but not mapped yet
            T closestVertex = Collections.min(unmappedVertices, Comparator.comparing(distance::get));
            edges.get(closestVertex).forEach(v -> {
                if (distance.get(v) > distance.get(closestVertex) + 1) {
//                    better path is found, updating
                    distance.put(v, distance.get(closestVertex) + 1);
                    shortBackPaths.put(v, closestVertex);
                }
            });
            unmappedVertices.remove(closestVertex);
        }
//        collecting a path to a single list, vertex by vertex
        List<T> wayBack = new LinkedList<>();
        T nextVertex = second;
        while (nextVertex != first) {
            wayBack.add(nextVertex);
            nextVertex = shortBackPaths.get(nextVertex);
//            stuck cases, not all paths actually exist
            if (nextVertex == null) {
                break;
            }
        }
//        path was found if next vertex is the goal
        if (nextVertex == first && wayBack.size() > 0) {
            wayBack.add(first); // completing the path
            Collections.reverse(wayBack);   // to actual vertices order
            return wayBack;
        } else {
            return Collections.emptyList();
        }
    }

}
