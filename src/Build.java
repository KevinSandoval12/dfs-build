import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {


    printShortWords(vertex, k, new HashSet<>());
  }

  private static void printShortWords(Vertex<String> vertex, int k, HashSet<String> seen) {
    if (vertex == null) return;
    if (seen.contains(vertex.data)) return;

    seen.add(vertex.data);

    if (vertex.data.length() < k){
      System.out.println(vertex.data);
    };

    for (Vertex<String> neighbor : vertex.neighbors) {
      printShortWords(neighbor, k, seen);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {

    return longestWord(vertex, new HashSet<>(), "");
    
  }

  private static String longestWord(Vertex<String> vertex, HashSet<String> seen, String max) {
    if (vertex == null) return "";
    if (seen.contains(vertex.data)) return "";

    seen.add(vertex.data);

    if (vertex.data.length() > max.length()) {
      max = vertex.data;
    }

    for (Vertex<String> neighbor : vertex.neighbors) {
      String result = longestWord(neighbor, seen, max);
      if (result.length() > max.length()) {
        max = result;
      }
    }

    return max;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {

    printSelfLoopers(vertex, new HashSet<>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, HashSet<Vertex<T>> seen){
    if (vertex == null) return;
    if (seen.contains(vertex)) return;

    seen.add(vertex);

    if (vertex.neighbors.contains(vertex)) {
      System.out.println(vertex.data);
    }
    for (Vertex<T> neighbor : vertex.neighbors) {
      printSelfLoopers(neighbor, seen);
    }
    
  }

  

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {

    return canReach(start, destination, new HashSet<>());
  }

  private static boolean canReach(Airport start, Airport destination, HashSet<Airport> seen) {
    if (start == destination) return true;
    if (seen.contains(start)) return false;

    seen.add(start);

    for (Airport neighbor : start.getOutboundFlights()) {
      if (canReach(neighbor, destination, seen)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    HashSet<T> seen = new HashSet<>();
    unreachable(graph, starting, seen);

    HashSet<T> result = new HashSet<>(graph.keySet());
    result.removeAll(seen);

    return result;
  }

  private static <T> void unreachable(Map<T, List<T>> graph, T current, HashSet<T> seen) {
    if (current == null || seen.contains(current)) return;

    seen.add(current);

    for (T neighbor : graph.getOrDefault(current, new ArrayList<>())) {
        unreachable(graph, neighbor, seen);
    }
  }
}
