import java.util.*;

public class FordFulkerson {
    public static int maxFlow(int[][] capacity, int source, int sink){
        int[][] residualCapacity = new int[capacity.length][capacity[0].length];
        for ( int i = 0 ; i < capacity.length ; i++ ){
            for ( int j = 0 ; j < capacity[0].length ; j++ ){
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        HashMap<Integer,Integer> parent = new HashMap<>();
        List<List<Integer>> augmentedPaths = new ArrayList<>();
        int maxFlow = 0;
        while ( BFS(residualCapacity,parent,source,sink) ){
            List<Integer> path = new ArrayList<>();
            int flow = Integer.MAX_VALUE;

            int v = sink;
            while ( v != source ){
                path.add(v);
                int u = parent.get(v);
                if ( flow > residualCapacity[u][v] ){
                    flow = residualCapacity[u][v];
                }
                v = u;
            }

            path.add(source);
            Collections.reverse(path);
            augmentedPaths.add(path);

            maxFlow += flow;

            v = sink;
            while ( v != source ){
                int u = parent.get(v);
                residualCapacity[u][v] -= flow;
                residualCapacity[v][u] += flow;
                v = u;
            }
        }
        printAugmentedPaths(augmentedPaths);
        return maxFlow;
    }

    public static void printAugmentedPaths(List<List<Integer>> ls){
        Iterator it = ls.iterator();
        while ( it.hasNext() ){
            List<Integer> cl = (List) it.next();
            for ( int i : cl ){
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    public static boolean BFS(int[][] residualCapacity, HashMap<Integer,Integer> parent, int source, int sink){
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        boolean foundAugmentedPath = false;
        while ( !queue.isEmpty() ){
            int u = queue.poll();
            for ( int v = 0 ; v < residualCapacity.length ; v++ ){
                if ( visited.contains(v) == false && residualCapacity[u][v] > 0 ){
                    parent.put(v,u);
                    visited.add(v);
                    queue.add(v);
                    if ( v == sink ){
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }
        return foundAugmentedPath;
    }

    public static void main(String[] args){
        int[][] capacity = {
                {0,3,0,3,0,0,0},
                {0,0,4,0,0,0,0},
                {3,0,0,1,2,0,0},
                {0,0,0,0,2,6,0},
                {0,1,0,0,0,0,1},
                {0,0,0,0,0,0,9},
                {0,0,0,0,0,0,0}
        };
        System.out.println(maxFlow(capacity,0,6));
    }
}
