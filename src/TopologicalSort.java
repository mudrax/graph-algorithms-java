import java.util.*;

public class TopologicalSort {
    private static List<Integer> rawInput = new ArrayList<>();
    private static List<List<Integer>> nodePairs = new ArrayList<>();
    private static Map<Integer , List<Integer>> adjacencyGraph = new HashMap<>();

    private static void takeInput(int n) {
        Scanner sc = new Scanner(System.in);
        for(int i=0 ; i<n ; i++)
            rawInput.add(sc.nextInt());

        sc.close();
    }

    private static List<List<Integer>> parseRawInputToNodePairs(int n) {
        List<List<Integer>> nodePairs = new ArrayList<>();
        for(int i=0 ; i<n ; i+=2) {
            List<Integer> nodePair = new ArrayList<>(Arrays.asList(rawInput.get(i), rawInput.get(i + 1)));
            nodePairs.add(nodePair);
        }
        return nodePairs;
    }

    private static Map<Integer , List<Integer>> getAdjacencyGraph(List<List<Integer>> nodePairs) {
        Map<Integer , List<Integer>> adjacencyGraph = new HashMap<>();
        for(List<Integer> nodePair : nodePairs) {
            List<Integer> existingNeighbours = adjacencyGraph.getOrDefault(nodePair.get(0) , new ArrayList<>());
            existingNeighbours.add(nodePair.get(1));
            adjacencyGraph.put(nodePair.get(0) , existingNeighbours);
        }
        return adjacencyGraph;
    }

    public static List<Integer> getTopologicalSort(Map<Integer , List<Integer>> adjacencyGraph, int totalNodes) {
        List<Integer> topologicalSort = new ArrayList<>();

        // 1. in-degree find
        int[] inDegrees = new int[totalNodes+1];
        for(Integer node : adjacencyGraph.keySet()) {
            List<Integer> neighbours = adjacencyGraph.get(node);
            for(Integer neighbour : neighbours) {
                inDegrees[neighbour]++;
            }
        }

        // 2. put all node with 0 in-degree in queue
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0 ; i<=totalNodes ; i++) {
            if(inDegrees[i] == 0) {
                queue.add(i);
            }
        }

        // 3. check which neighbours has become independent and put independent ones in queue
        // 4. also push the processed node inside result array
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            topologicalSort.add(node);
            for(Integer neighbour : adjacencyGraph.getOrDefault(node , new ArrayList<>())) {
                inDegrees[neighbour]--;
                if(inDegrees[neighbour] == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        // 5. optional check : confirm if it was DAG if not return empty topoSort result and a message
        for(int i=0 ; i<=totalNodes ; i++) {
            if(inDegrees[i] != 0) {
                System.out.println("Cycle detected in the graph : " + adjacencyGraph.toString());
                return new ArrayList<>();
            }
        }

        return topologicalSort;
    }


    public static void main(String[] args) {
        // TOPOLOGICAL SORT ALGORITHM SUMMARY :
        // [1,2] , [2,3] , [3,4] , [4,5] , [2,5] ==> raw input assumption.
        // step 1 : parse to adjacency list.(convert to nodes).
        // step 2 : find in-degrees ==> O(N).
        // step 3 : nodes with 0 in-degree means they are not dependent on anyone else now.
        // step 4 : reduce in-degree of neighbours of independent node. If its 0 they are also independent now.

        System.out.println("Enter number of nodes required...");
        int totalNodes = 0;
        Scanner sc = new Scanner(System.in);
        totalNodes = sc.nextInt();


        System.out.println("Enter number of pairs to Add...");
        int pairsToAdd = 0;
        pairsToAdd = sc.nextInt();

        System.out.println("Enter the graph...");
        takeInput(pairsToAdd*2);
        System.out.println(rawInput.toString());

        nodePairs = parseRawInputToNodePairs(rawInput.size());
        System.out.println(nodePairs.toString());

        adjacencyGraph = getAdjacencyGraph(nodePairs);
        System.out.println(adjacencyGraph.toString());

        List<Integer> topologicalSort = getTopologicalSort(adjacencyGraph, totalNodes);
        System.out.println(topologicalSort.toString());
    }


}
