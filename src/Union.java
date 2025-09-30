import java.util.*;

/**
 * This is implementation of disjoint set union algorithm.
 * 2 important components are for each node we must have its rank and parent
 */
public class Union {

    static Map<Integer, Integer> rank = new HashMap<>();
    static Map<Integer, Integer> parent = new HashMap<>();

    public static void main(String[] args) {
        // requirements :
        // totalNodes : 5 means nodes are : (1,2,3,4,5)
        // unionCommand : [(1,2) , (5,3) , (1,4) , (3,4)]
        unionFind();
    }

    // starting point of algorithm
    public static void unionFind() {
        int totalNodes = 5;
        init(totalNodes);

        List<List<Integer>> unionCommand = new ArrayList<>(
                Arrays.asList(
                        Arrays.asList(1,2),
                        Arrays.asList(5,3),
                        Arrays.asList(1,4),
                        Arrays.asList(3,4)
                )
        );
        for(List<Integer> command : unionCommand) {
            System.out.println("Logs before command : " + command);
            printLogs(totalNodes);
            int nodeA = command.get(0);
            int nodeB = command.get(1);

            union(nodeA, nodeB);

            System.out.println("Logs after command : " + command);
            printLogs(totalNodes);

            System.out.println("\n*********************\n");
        }


        System.out.println("FINISHED");
        for(int i=1 ; i<=totalNodes ; i++) {
            System.out.println("Parent of node: " + i + " --> " + findParent(i));
        }
    }

    private static void union(int a , int b) {
        a = findParent(a);
        b = findParent(b);

        int rankA = getRank(a);
        int rankB = getRank(b);

        if(rankA > rankB) {
            setParent(b, a);
        } else if(rankA < rankB) {
            setParent(a, b);
        } else {
            setParent(b, a);
            // increment rank in case equal ranks are present
            setRank(a, rankA+1);
        }
    }

    private static int findParent(int a) {
        int parentA = parent.get(a);
        if(parentA == a) return parentA;

        // this is path compression
        int actualParentA = findParent(parentA);
        // replace direct parent to path compressed parent
        parent.put(a, actualParentA);

        return actualParentA;
    }









    /**
     * Helper methods below
     */
    private static void init(int totalNodes) {
        for(int i=1 ; i<=totalNodes ; i++) {
            rank.put(i , 0);
            parent.put(i , i);
        }
    }

    private static void printLogs(int nodes) {
        for(int i=1 ; i<=nodes ; i++) {
            System.out.println("Parent of node: " + i + " --> " + parent.get(i));
        }
        System.out.println("\t\t --- --- --- ");
    }

    private static int getRank(int a) {
        return rank.getOrDefault(a , 0);
    }

    private static void setRank(int node , int rankValue) {
        rank.put(node, rankValue);
    }

    private static void setParent(int node, int parentValue) {
        parent.put(node, parentValue);
    }
}
