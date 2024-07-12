package com.example.demo.ht.com;

import java.io.*;

public class BufferedReaderExample {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\111.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
