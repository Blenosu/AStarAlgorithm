package Programs.AStar;

import java.util.*;

public class MazeAStar {
    public final int rows;
    public final int cols;
    public final int[][] grid;
    public final int[][] directions = {
        {0, 1}, {1, 0}, {0, -1}, {-1, 0}, // Straight directions
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Diagonal directions
    };

    // ANSI COLOR CODES (IGNORE)
    public static final String reset = "\u001B[0m"; // RESET COLOR
    public static final String blackb = "\033[1;30m"; // BLACK BOLD
    public static final String red = "\u001B[31m"; // RED
    public static final String green = "\u001B[32m"; // GREEN
    public static final String blue = "\u001B[34m"; // BLUE
    public static final String cyan = "\u001B[36m"; // CYAN
    public static final String white = "\u001B[37m"; // WHITE

    public MazeAStar(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
    }

    // Generate a maze with guaranteed connectivity
    public void generatePathGuaranteedMaze() {
        for (int[] row : grid) Arrays.fill(row, 1); // Initially block all cells

        // Carve a guaranteed path using DFS
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0, 0});
        grid[0][0] = 0; // Mark start as walkable

        Random random = new Random();
        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            List<int[]> neighbors = getNeighbors(current);

            for (int[] neighbor : neighbors) {
                int nx = neighbor[0], ny = neighbor[1];
                if (grid[nx][ny] == 1) { // Only carve unvisited cells
                    grid[nx][ny] = 0; // Make walkable
                    stack.push(neighbor);
                }
            }
        }

        // Add some additional random blocking for complexity
        // Divide by 3 to get 33% of the Maze Blocked
        for (int i = 0; i < rows * cols * 9/20; i++) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if (grid[r][c] == 0 && !(r == 0 && c == 0) && !(r == rows - 1 && c == cols - 1)) {
                grid[r][c] = 1;
            }
        }
        grid[rows - 1][cols - 1] = 0; // Ensure target is walkable
    }

    // Get valid neighbors for carving the maze
    private List<int[]> getNeighbors(int[] cell) {
        List<int[]> neighbors = new ArrayList<>();
        for (int[] direction : directions) {
            int nx = cell[0] + direction[0];
            int ny = cell[1] + direction[1];
            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols) {
                neighbors.add(new int[]{nx, ny});
            }
        }
        Collections.shuffle(neighbors); // Randomize the order of neighbors
        return neighbors;
    }

    // Print the maze with start and target marked
    public void printMaze(int[] start, int[] target) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == start[0] && j == start[1]) {
                    System.out.print(green + "[ ± ]" + reset); // Start cell
                } else if (i == target[0] && j == target[1]) {
                    System.out.print(red + "[ ± ]" + reset); // Target cell
                } else if (grid[i][j] == 0) {
                    System.out.print(white + "[ _ ]" + reset); // Walkable cell
                } else if (grid[i][j] == 2) {
                    System.out.print(cyan + "[ ± ]" + reset); // Path cell
                } else {
                    System.out.print(blackb + "[ 0 ]" + reset); // Wall cell
                }
            }
            System.out.println();
        }
    }

    // Check if a position is valid
    public boolean isValid(int[] position) {
        int row = position[0], col = position[1];
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] == 0;
    }
}
