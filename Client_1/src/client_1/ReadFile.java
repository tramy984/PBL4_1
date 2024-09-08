package client;

import java.io.*;

public class ReadFile {
    public int[][] read(String s,int n )
    {
        int[][] array=null;
        try (BufferedReader br = new BufferedReader(new FileReader(s))) 
        {
            // Read the first line and parse it as an integer
            n = Integer.parseInt(br.readLine().trim());

            // Initialize the 2D array
            array = new int[n][n];

            // Read the remaining lines
            for (int i = 0; i < n; i++) {
                String[] line = br.readLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    array[i][j] = Integer.parseInt(line[j]);
                }
            }

           

        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    
    }
    public static void main(String[] args) {
       
    }
}
