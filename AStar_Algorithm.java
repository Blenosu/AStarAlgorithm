package Programs.AStar;

import java.util.*;

public class AStar_Algorithm {
    public static void main(String[] args) {
        // Maze dimensions
        int rows = 10;
        int cols = 10;

        // Create and generate the maze
        MazeAStar maze = new MazeAStar(rows, cols);
        maze.generatePathGuaranteedMaze();

        // Define start and target points
        int[] start = {0, 0};
        int[] target = {rows - 1, cols - 1};

        // Display the generated maze
        System.out.println(MazeAStar.blue + "Generated Maze:" + MazeAStar.reset);
        System.out.println(MazeAStar.green + "Green = Start" + MazeAStar.reset);
        System.out.println(MazeAStar.red + "Red = Finish" + MazeAStar.reset);
        maze.printMaze(start, target);

        // Perform A* pathfinding
        AStarPathFinder pathfinder = new AStarPathFinder(maze);
        List<int[]> path = pathfinder.findPath(start, target);

        // Visualize the maze and path
        System.out.println("\nPathfinding:");
        pathfinder.printProgress();

        System.out.println(MazeAStar.cyan + "\nShortest Path:" + MazeAStar.reset);
        if (path != null) {
        	
            // Mark the path in the maze for visualization
            for (int[] step : path) {
                if (!Arrays.equals(step, start) && !Arrays.equals(step, target)) {
                    maze.grid[step[0]][step[1]] = 2; // Mark path cells
                }
            }
            maze.printMaze(start, target); // Highlights start and target cells
            
            // Output total steps // -1 to exclude start
            String ShrtPath = MazeAStar.cyan + "Shortest Path: " + (path.size()-2) + MazeAStar.reset + MazeAStar.red + " + 1 " + MazeAStar.reset;
            String TotalSteps = MazeAStar.cyan + "Total Steps Made: " + (path.size()-1) + MazeAStar.reset;
            
            System.out.println(ShrtPath); 
            System.out.println(TotalSteps);
            
            System.out.println("\nPath Steps:");
            for (int[] step : path) {
                System.out.println(Arrays.toString(step));
            }
        } else {
            System.out.println(MazeAStar.cyan + "No path found." + MazeAStar.reset);
        }
    }
}
