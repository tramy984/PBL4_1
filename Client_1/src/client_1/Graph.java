/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DrawDothi extends JPanel 
{
    private final int[][] adjacencyMatrix;
    private Point[] vertices;
     private final String[] labels;

    public DrawDothi(int[][] adjacencyMatrix) 
    {
        this.adjacencyMatrix = adjacencyMatrix;
        this.vertices = new Point[adjacencyMatrix.length];
        this.labels = generateLabels(adjacencyMatrix.length);  // Tạo nhãn cho các đỉnh
        setPreferredSize(new Dimension(400, 400));
        calculateVerticesPositions();
    }

    private void calculateVerticesPositions() {
        int radiusX = 150;
        int radiusY = 100;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int n = adjacencyMatrix.length;

        vertices = new Point[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int) (radiusX * Math.cos(angle));
            int y = centerY + (int) (radiusY * Math.sin(angle));
            vertices[i] = new Point(x, y);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        calculateVerticesPositions(); 

        g.setColor(Color.YELLOW);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);

        // Vẽ các đỉnh
        for (int i = 0; i < vertices.length; i++) {
            // Vẽ đỉnh dưới dạng hình tròn
            g.fillOval(vertices[i].x -7, vertices[i].y-7 , 16, 16);
            // Vẽ tên đỉnh lên trên hình tròn
            g2d.setColor(Color.BLACK);  // Đặt màu cho tên đỉnh
            g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
            // Điều chỉnh tọa độ để tên đỉnh nằm ngoài
            int offset = 25;  // Khoảng cách ra ngoài
            int labelX = vertices[i].x + (int) (offset * Math.cos(2 * Math.PI * i / vertices.length));
            int labelY = vertices[i].y + (int) (offset * Math.sin(2 * Math.PI * i / vertices.length));
            g.drawString(labels[i], labelX, labelY);
            g.setColor(Color.YELLOW);
        }

        // Draw edges
        for (int i = 0; i < adjacencyMatrix.length; i++) 
        {
            for (int j = i + 1; j < adjacencyMatrix[i].length; j++) 
            {
                if (adjacencyMatrix[i][j] > 0) 
                {
                    //g.setColor(adjacencyMatrix[i][j] == 1 ? Color.BLUE : Color.RED);
                    g.setColor(Color.red);
                    g2d.setStroke(new BasicStroke(2));
                    
                    g2d.drawLine(vertices[i].x, vertices[i].y, vertices[j].x, vertices[j].y);
                    g2d.setColor(Color.BLACK);  // Đặt màu cho trọng số
                    g2d.setFont(new Font("SansSerif", Font.ITALIC, 12));
                    g2d.drawString(String.valueOf(adjacencyMatrix[i][j]), 
                        (vertices[i].x + vertices[j].x) / 2, 
                        (vertices[i].y + vertices[j].y) /2-3);
                }
            }
        }
    }

    private String[] generateLabels(int numLabels) 
    {
        String[] labels = new String[numLabels];
        for (int i = 0; i < numLabels; i++) {
            labels[i] = String.valueOf((char) ('A' + i));
        }
        return labels;
    }
    
}
public class Graph extends javax.swing.JFrame 
{
    private String fileName;
    private int lenght;
    private int[][] adjacencyMatrix;
    
    public Graph() {
        initComponents();
        initializeGraph();  
    }
    public Graph(String Filename, int n)
    {
        this.fileName=Filename;
        this.lenght=n;
        initComponents();
        initializeGraph();
    }
    private void initializeGraph() {
        // Define adjacency matrix
        ReadFile rf= new ReadFile();
        
        adjacencyMatrix = rf.read(fileName, lenght);

        // Create an instance of DrawDothi with the adjacency matrix
        DrawDothi graphPanel = new DrawDothi(adjacencyMatrix);

        // Add the DrawDothi panel to jPanel3
        jPanel3.setLayout(new BorderLayout());
        jPanel3.add(graphPanel, BorderLayout.CENTER);
        
        jPanel2.setLayout(new FlowLayout());
        ButtonGroup group = new ButtonGroup();
        for (int i = 1; i < lenght; i++) {
            char label = (char) ('A' + i);
            JRadioButton button = new JRadioButton(String.valueOf(label));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle button click
                    System.out.println("Button " + button.getText() + " clicked");
                }
            });
            group.add(button);
            jPanel2.add(button);
        }
        
        // Add labels and text fields to jPanel1
        jPanel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 1; i < lenght; i++) {
            char label = (char) ('A' + i);
            JLabel nodeLabel = new JLabel(String.valueOf(label));
            JTextField textField1 = new JTextField(10);
            JTextField textField2 = new JTextField(10);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(textField1);
            panel.add(textField2);

            gbc.gridx = 0;
            gbc.gridy = i - 1;
            gbc.weightx = 0.1;
            jPanel1.add(nodeLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1;
            jPanel1.add(panel, gbc);

        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Định đường phân tán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 169, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đích ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        jPanel3.setMaximumSize(new java.awt.Dimension(30767, 30767));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Graph().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
