package com.jvcdp.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CmdStreamReader implements Runnable {
    String name;
    InputStream is;
    StringBuffer output= new StringBuffer();
    Thread thread;
    public CmdStreamReader(String name, InputStream is) {
        this.name = name;
        this.is = is;
    }
    public void start () {
        thread = new Thread (this);
        thread.start ();
    }
    public void run () {
        try {
            InputStreamReader isr = new InputStreamReader (is);
            BufferedReader br = new BufferedReader (isr);
            while (true) {
                String s = br.readLine ();
                if ((s == null)||(s.length()==0)) break;
                System.out.println ("[" + name + "] " + s);
                output.append(s);
            }
            is.close ();
        } catch (Exception ex) {
            System.out.println ("Problem reading stream " + name + "... :" + ex);
            ex.printStackTrace ();
        }
    }
}
