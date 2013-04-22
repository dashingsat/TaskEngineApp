package com.happiness.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class DBConnection{

    static DB  taskEngineDBInstance=null ;
    static DBCollection  tasks  = null;
    static Process pwd;

    public static DBCollection getDBcolelction() throws Exception {
        if(tasks != null)
        {
            return  tasks;
        }

        else{
            try {
                taskEngineDBInstance = getTaskEngineDBInstance();
                tasks = taskEngineDBInstance.getCollection("tasks");
                return tasks;
            } catch (Exception e) {
                throw new Exception("Could not get the collection:"+e);  //To change body of catch statement use File | Settings | File Templates.
            }
        }


    }

    public static Task<Void> task = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            startMongoDBAsADaemon();

            return null;
        }
    } ;
    public static DB getTaskEngineDBInstance () throws Exception {


        if(taskEngineDBInstance !=null)
        {
            return taskEngineDBInstance;
        }

        else{

            try {
                MongoClient mongoClient = new MongoClient( "localhost" );
                taskEngineDBInstance = mongoClient.getDB("taskManagerDB");
                return taskEngineDBInstance;
            } catch (UnknownHostException e) {
               throw new Exception("Could not establish connection to the database");
            }
        }

    }

    public static List<String> startMongoDBAsADaemon() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd","/C","mongod.exe", "--dbpath",
                "../../data");
        //  String[] command = {"CMD", "/C", "dir"};
        // ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File("TaskEngineApp/src/com/happiness/db/mongodb/bin"));
        processBuilder.redirectErrorStream(true);
        pwd = processBuilder.start();
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(pwd.getInputStream()));
        String output;
        List<String> lines = new ArrayList<String>();
        while ((output = outputReader.readLine()) != null) {
            lines.add(output.toString());
            //System.out.println("Lines:"+lines);
        }
        //  pwd.waitFor();
        //System.out.println( pwd.exitValue());
        return lines;
    }

    public static void main(String[] args){
        File f = new File("TaskEngineApp/src/com/happiness/db/mongodb");
        System.out.println(f.exists());
       try {
           startMongoDBAsADaemon();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static Boolean shutdownDatabase(){
         //task.cancel();
        taskEngineDBInstance.command(new BasicDBObject("shutdown",1));
        pwd.destroy();

        return true;
    }

}