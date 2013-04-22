package com.happiness.db;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/1/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args){

        DBCollection tasks = null;
        try {
            DB conn = DBConnection.getTaskEngineDBInstance();
             tasks = DBConnection.getDBcolelction();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (tasks != null) {
            BasicDBObject query = new BasicDBObject("priority","Medium");//
            System.out.println("ok");
            DBCursor cursor = tasks.find(query);

            try {
                while(cursor.hasNext()) {
                    System.out.println(cursor.next().get("category"));
                }
            } finally {
                cursor.close();
            }
        }

       // ObservableList<String>  test =  FXCollections.observableSet()<String>();
    }
}
