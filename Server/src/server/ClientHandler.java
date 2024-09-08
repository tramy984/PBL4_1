package server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;       // Sử dụng ObjectOutputStream để gửi đối tượng
    private ObjectInputStream in;         // Sử dụng ObjectInputStream để nhận đối tượng
    private List<ClientHandler> clients;
    private javax.swing.JTextArea history; // Tham chiếu đến JTextArea trong Server
    private int[][] matrix;               // Ma trận sẽ được nhận từ client
    
    public ClientHandler(Socket socket, List<ClientHandler> clients, javax.swing.JTextArea history) {
        this.socket = socket;
        this.clients = clients;
        this.history = history;
    }
    
    @Override
    public void run() {
        try {
            // Tạo luồng input/output để truyền/nhận đối tượng
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Nhận dữ liệu từ client (giả sử client gửi ma trận)
            matrix = (int[][]) in.readObject();
            history.append("\nReceived matrix from client " + socket.getInetAddress().getHostAddress() + ":\n");
            printMatrix(matrix);
            
            // Broadcast ma trận cho tất cả các client khác
            broadcastMatrix(matrix);

        } catch (IOException | ClassNotFoundException e) {
            history.append("\nConnection error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                history.append("\nError closing socket: " + e.getMessage());
            }
        }
    }

    // In ma trận nhận được từ client lên JTextArea của server
    private void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int elem : row) {
                history.append(elem + " ");
            }
            history.append("\n");
        }
    }

    // Phát lại ma trận đến tất cả các client khác
    private void broadcastMatrix(int[][] matrix) {
        for (ClientHandler client : clients) {
            if (client != this) {  // Không gửi lại cho chính client đã gửi ma trận
                try {
                    client.out.writeObject(matrix);  // Gửi ma trận tới các client khác
                    client.out.flush();
                } catch (IOException e) {
                    history.append("\nError broadcasting matrix to client: " + e.getMessage());
                }
            }
        }
    }
}
