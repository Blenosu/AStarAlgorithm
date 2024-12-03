package Programs.AStar;

import java.util.*;

public class AStarPathFinder {
    private final MazeAStar maze;
    private final List<String> progress = new ArrayList<>(); // To store progress for visualization

    public AStarPathFinder(MazeAStar maze) {
        this.maze = maze;
    }

    public List<int[]> findPath(int[] start, int[] target) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        
        // PriorityQueue for Empty spaces first
        Set<String> closedSet = new HashSet<>();

        openSet.add(new Node(start, null, 0, heuristic(start, target)));
       
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Add to visualization progress
            progress.add(Arrays.toString(current.position));

            // Reconstruct the path
            if (Arrays.equals(current.position, target)) {
                List<int[]> path = reconstructPath(current);
                return path;
            }

            closedSet.add(Arrays.toString(current.position));

            for (int[] direction : maze.directions) {
                int[] neighbor = {current.position[0] + direction[0], current.position[1] + direction[1]};

                if (maze.isValid(neighbor) && !closedSet.contains(Arrays.toString(neighbor))) {
                	
                    int g = current.g + 1; // Increment cost
                    int h = heuristic(neighbor, target);
                    
                    openSet.add(new Node(neighbor, current, g, g + h));
                }
            }
        }
        return null; // No path found
    }


    public void printProgress() {
        System.out.println(String.join(" -> ", progress));
    }

    private int heuristic(int[] current, int[] target) {
        // Heuristic value for both straight and diagonal movements
        return Math.max(
            Math.abs(current[0] - target[0]), 
            Math.abs(current[1] - target[1]));
    }
    
    private List<int[]> reconstructPath(Node node) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(node.position);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
    

    private static class Node {
        int[] position;
        Node parent;
        int g; // Cost from start
        int f; // Total cost (g + h)

        Node(int[] position, Node parent, int g, int f) {
            this.position = position;
            this.parent = parent;
            this.g = g;
            this.f = f;
        }
    }
}
