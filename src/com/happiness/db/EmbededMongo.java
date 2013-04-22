package com.happiness.db;

import com.mongodb.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/17/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmbededMongo {
    public static void startMongoDBAsADaemon() throws IOException, InterruptedException {
       ProcessBuilder processBuilder = new ProcessBuilder("cmd","/C","mongod.exe", "--dbpath",
                "../../com.happiness.db.data");
      //  String[] command = {"CMD", "/C", "dir"};
       // ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File("com.happiness.db.mongodb"));
                processBuilder.redirectErrorStream(true);
        Process pwd = processBuilder.start();
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(pwd.getInputStream()));
        String output;
        List<String> lines = new ArrayList<String>();
        while ((output = outputReader.readLine()) != null) {
            lines.add(output.toString());
           // System.out.println("Lines:"+lines);
        }
      //  pwd.waitFor();
        System.out.println( pwd.exitValue());
    }


    public static void main(String[] args){
        try {
            EmbededMongo.startMongoDBAsADaemon();

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
