package com.cmd;


import java.io.IOException;

public class ExexTest {
    public static void main(String args[]) {
        int a = 0;
        try {
            String[] args2 = new String[]{"python", "D:\\stylegan2encoder-master\\mixing.py", "2020", "m2.npy", "w1.npy", "2", "0.5", "4", "0", "0", "0", "0"};
            a = Exec.main(args2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(a);

    }
}
