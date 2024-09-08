package test;
import javax.swing.*;
import java.awt.*;

public class Test extends JPanel {
    private final int[][] adjacencyMatrix;
    private Point[] vertices;

    public Test(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.vertices = new Point[adjacencyMatrix.length];
        setPreferredSize(new Dimension(400, 400));
        calculateVerticesPositions();
    }

    private void calculateVerticesPositions() {
        int radius = 150;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int n = adjacencyMatrix.length;
        
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            vertices[i] = new Point(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateVerticesPositions();  // Recalculate positions if the window is resized
        
        g.setColor(Color.GREEN);

        // Draw vertices
        for (int i = 0; i < vertices.length; i++) {
            g.fillOval(vertices[i].x - 15, vertices[i].y - 15, 30, 30);
            g.drawString(String.valueOf((char)('A' + i)), vertices[i].x - 5, vertices[i].y + 5);
        }

        // Draw edges
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    g.setColor(adjacencyMatrix[i][j] == 1 ? Color.BLUE : Color.RED);
                    g.drawLine(vertices[i].x, vertices[i].y, vertices[j].x, vertices[j].y);
                    g.drawString(String.valueOf(adjacencyMatrix[i][j]), 
                        (vertices[i].x + vertices[j].x) / 2, 
                        (vertices[i].y + vertices[j].y) / 2);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Define adjacency matrix
        int[][] adjacencyMatrix = {
            {0, 2, 0, 3, 0, 3},
            {2, 0, 5, 2, 0, 0},
            {0, 5, 0, 0, 3, 0},
            {3, 2, 0, 0, 1, 4},
            {0, 0, 3, 1, 0, 0},
            {3, 0, 0, 4, 0, 0}
        };

        // Create frame and panel
        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Test(adjacencyMatrix));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
