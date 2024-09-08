package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testdothi extends JPanel {
    private final int[][][] adjacencyMatrices; // Mảng chứa các ma trận kề
    private final Point[][] vertices; // Mảng chứa vị trí đỉnh của các đồ thị

    public testdothi(int[][][] adjacencyMatrices) {
        this.adjacencyMatrices = adjacencyMatrices;
        this.vertices = new Point[adjacencyMatrices.length][];
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tạo một panel cho mỗi đồ thị
        for (int i = 0; i < adjacencyMatrices.length; i++) {
            vertices[i] = calculateVerticesPositions(adjacencyMatrices[i].length);
            tabbedPane.addTab("Graph " + (i + 1), new GraphPanel(adjacencyMatrices[i], vertices[i]));
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Tạo nút "Kết quả"
        JButton resultButton = new JButton("Kết quả");
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(testdothi.this, 
                    "Đây là kết quả xử lý sau khi nhấn nút.", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Thêm nút vào dưới cùng của panel
        add(resultButton, BorderLayout.SOUTH);
    }

    // Tính toán vị trí các đỉnh theo bố trí vòng tròn
    private Point[] calculateVerticesPositions(int n) {
        Point[] vertices = new Point[n];
        int radius = 150;
        int centerX = 200;
        int centerY = 200;
        
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            vertices[i] = new Point(x, y);
        }
        return vertices;
    }

    // Lớp con để vẽ từng đồ thị
    private static class GraphPanel extends JPanel {
        private final int[][] adjacencyMatrix;
        private final Point[] vertices;

        public GraphPanel(int[][] adjacencyMatrix, Point[] vertices) {
            this.adjacencyMatrix = adjacencyMatrix;
            this.vertices = vertices;
            setPreferredSize(new Dimension(400, 400));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Vẽ các đỉnh
            g.setColor(Color.GREEN);
            for (int i = 0; i < vertices.length; i++) {
                g.fillOval(vertices[i].x - 15, vertices[i].y - 15, 30, 30);
                // Gán nhãn các đỉnh bắt đầu từ 'A'
                g.drawString(String.valueOf((char)('A' + i)), vertices[i].x - 5, vertices[i].y + 5);
            }

            // Vẽ các cạnh
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
    }

    public static void main(String[] args) {
        // Định nghĩa các ma trận kề
        int[][] graph1 = {
            {0, 2, 0, 3, 0, 3},
            {2, 0, 5, 2, 0, 0},
            {0, 5, 0, 0, 3, 0},
            {3, 2, 0, 0, 1, 4},
            {0, 0, 3, 1, 0, 0},
            {3, 0, 0, 4, 0, 0}
        };

        int[][] graph2 = {
            {0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0}
        };

        int[][][] adjacencyMatrices = { graph1, graph2 };

        // Tạo và hiển thị JFrame
        JFrame frame = new JFrame("Multiple Graphs Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new testdothi(adjacencyMatrices));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
