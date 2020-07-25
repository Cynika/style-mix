package com.cmd;

import java.io.*;

public class Exec {

    public static int main(String[] args) throws IOException {

        final Process process = Runtime.getRuntime().exec(args);
        final BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(),"GBK"));
        int exitCode = 0;
        new Thread() {
            public volatile boolean exit = false;
            public void run() {
                try {
                    String cmdout = "";
                    while (!exit) {
                        if ((cmdout = br.readLine()) != null) {
                            System.out.println(cmdout);
                        } else {
                            exit = true;
                        }
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            exitCode = process.waitFor();
            if (0 == exitCode) {
                process.destroy();
                br.close();
                return 1;
            } else {
                process.destroy();
                br.close();
                return 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
