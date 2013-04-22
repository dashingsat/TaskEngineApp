/**
 * Sample Skeleton for "happinessTodo.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package happinessTodo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.happiness.db.DBConnection;
import com.mongodb.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;


public class Fire
        implements Initializable {

    @FXML //  fx:id="Cancel"
    private static Button Cancel; // Value injected by FXMLLoader

    @FXML //  fx:id="Category"
    private ComboBox<String> Category; // Value injected by FXMLLoader

    @FXML //  fx:id="ImageHeader"
    private AnchorPane ImageHeader; // Value injected by FXMLLoader

    @FXML //  fx:id="actualTasks"
    private TableView<Tasks> actualTasks; // Value injected by FXMLLoader

    @FXML //  fx:id="addTask"
    private Button addTask; // Value injected by FXMLLoader

    @FXML //  fx:id="addTaskBox"
    private HBox addTaskBox; // Value injected by FXMLLoader

    @FXML //  fx:id="cancelStack"
    private StackPane cancelStack; // Value injected by FXMLLoader

    @FXML //  fx:id="deleteRow"
    private TableColumn<?, ?> deleteRow; // Value injected by FXMLLoader

    @FXML //  fx:id="editColumn"
    private TableColumn<?, ?> editColumn; // Value injected by FXMLLoader

    @FXML //  fx:id="enterTask"
    private TextArea enterTask; // Value injected by FXMLLoader

    @FXML //  fx:id="header"
    private AnchorPane header; // Value injected by FXMLLoader

    @FXML //  fx:id="priority"
    private TableColumn<Tasks, String> priority; // Value injected by FXMLLoader

    @FXML //  fx:id="taskPriority"
    private ComboBox<?> taskPriority; // Value injected by FXMLLoader

    @FXML //  fx:id="saveStack"
    private StackPane saveStack; // Value injected by FXMLLoader

    @FXML //  fx:id="saveTask"
    private Button saveTask; // Value injected by FXMLLoader

    @FXML //  fx:id="taskDetailColumn"
    private TableColumn<Tasks, String> taskDetailColumn; // Value injected by FXMLLoader

    @FXML //  fx:id="taskInformation"
    private Group taskInformation; // Value injected by FXMLLoader

    private static ObservableList<Tasks> taskList ;
    private ObservableList<String> taskCategoriesOrPriorities;
    private ObservableList<String> taskCategories;
    public static Thread DBthread;
    static String option=null;
    @FXML //  fx:id="tasks"
    private ListView<String> tasks; // Value injected by FXMLLoader

    @FXML //  fx:id="taxanomy"
    private ComboBox<?> taxanomy; // Value injected by FXMLLoader

    // Handler for ComboBox[fx:id="taxanomy"] onAction
    public void showList(ActionEvent event) {

       Object categoryOrPriorityObject= taxanomy.getValue();

       if(categoryOrPriorityObject != null){

            String categoryOrPriority = (String) categoryOrPriorityObject;
            option = categoryOrPriority;

            taskCategoriesOrPriorities.clear();
            taskCategoriesOrPriorities.addAll(getAllCategoriesOrPriorites(categoryOrPriority));

        }
    }

    private ObservableList<String> getAllCategoriesOrPriorites(String categoryOrPriority) {
        if(categoryOrPriority==null || categoryOrPriority.equalsIgnoreCase(""))
        {
            categoryOrPriority = "category";
        }

        BasicDBObject query = null;
        DBCollection taskCollection = null;
        try {
            taskCollection = DBConnection.getDBcolelction();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if(taskCollection != null){
            if(categoryOrPriority.equalsIgnoreCase("category")){
                List<String> tempList= taskCollection.distinct("category");

                if(tempList != null) {
                    ObservableList<String> tempObservableList = FXCollections.observableList(tempList);
                    return tempObservableList;
                }

               // return (ObservableList<String>) tempList;
            }

            if(categoryOrPriority.equalsIgnoreCase("priority")){
                  List<String> tempList = taskCollection.distinct("priority");
                   if(tempList != null){
                       ObservableList<String> tempObservableList = FXCollections.observableList(tempList);
                       return tempObservableList;
                   }

            }
        }


        return null;  //To change body of created methods use File | Settings | File Templates.
    }
                                                                                                              // Handler for Button[fx:id="addTask"] onAction
    public void showAddTaskBox(ActionEvent event) {
        addTaskBox.setVisible(true);
    }



    // Handler for TextArea[fx:id="enterTask"] onKeyTyped
    public void taskDataEntered(KeyEvent event) {

        String taskDetail = enterTask.getText();

        if(taskDetail != null && taskDetail.trim().length()>=1)
        {
            saveStack.setDisable(false);
            cancelStack.setDisable(false);
        }

       else if(taskDetail == null || taskDetail.trim().length()==0){
            saveStack.setDisable(true);
            cancelStack.setDisable(true);

        }

    }


    public void cancelTask(ActionEvent event){

         addTaskBox.setVisible(false);

    }
    // Handler for Button[fx:id="saveTask"] onAction
    public void saveTask(ActionEvent event) {

        String taskDetail =  enterTask.getText();
        Object priorityValue =  taskPriority.getValue();
        Object taskCategoryValue =  Category.getValue();

        String  priority = "Medium";
        String  category = "Personal";

        if(priorityValue != null)
        {
            priority = (String) priorityValue;
        }


        if(taskCategoryValue != null){
            category = (String)taskCategoryValue ;
        }

        System.out.println("Task:"+taskDetail+"Pri:"+priority+"taskcateg:"+category);

         Boolean inserted = insert(taskDetail,category,priority);

         if(inserted){
             taskList.add(new Tasks(taskDetail,category,priority));
             if(option != null){
                 if(option.equalsIgnoreCase("category")){
                     if(!taskCategoriesOrPriorities.contains(category)){
                         taskCategoriesOrPriorities.add(category);
                         taskCategories.clear();
                         taskCategories.addAll(taskCategoriesOrPriorities);
                         populateTaskViews(category,"Any");

                     }
                 }

                 if(option.equalsIgnoreCase("priority")) {
                     if(!taskCategoriesOrPriorities.contains(priority)){
                         taskCategoriesOrPriorities.add(priority);
                         populateTaskViews("Any",priority);

                     }
                 }


             }

             saveStack.setDisable(true);
             cancelStack.setDisable(true);
             addTaskBox.setVisible(false);
         }

    }

    public static void populateTaskViews(String category,String priority) {

         if(category == null){
             category = "Personal";
         }

        if(priority == null){
            priority="Medium"; }

         taskList.clear();
         taskList.addAll(getAllTasks(category,priority));
    }

    private static ObservableList<Tasks> getAllTasks(String category, String priority) {
        ObservableList tempList = FXCollections.observableArrayList();
        BasicDBObject query = null;
        DBCollection taskCollection = null;


        try {
            taskCollection = DBConnection.getDBcolelction();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(taskCollection != null){


            if(category.equalsIgnoreCase("any") && priority.equalsIgnoreCase("any"))  {
               query = new BasicDBObject();
            }

            else if(category.equalsIgnoreCase("any")){
                query = new BasicDBObject("priority",priority);//
            }

            else if(priority.equalsIgnoreCase("any"))
             query = new BasicDBObject("category",category);//                    append("k", new BasicDBObject("$gt", 10));

            else{
                query = new BasicDBObject("priority",priority).append("category",category);
            }
            DBCursor cursor = taskCollection.find(query);

            try {
                while(cursor.hasNext()) {
                  DBObject result=cursor.next();
                  String taskContentData = (String) result.get("taskContent");
                  String categoryData = (String) result.get("category");
                  String priorityData = (String) result.get("priority");
                  tempList.add(new Tasks(taskContentData,categoryData,priorityData));
                }
            } finally {
                cursor.close();
            }

        }


    return tempList;
    }
    private Boolean insert(String taskDetail, String category, String priority) {

        DBCollection  tasks = null;

        Boolean inserted = false;
        try {
              tasks = DBConnection.getDBcolelction();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try{

            BasicDBObject  doc = new BasicDBObject("taskContent",taskDetail).append("category",category).append("priority",priority);
            tasks.insert(doc,WriteConcern.SAFE);
            inserted = true;
            System.out.println("Data inserted");

        }
        catch(Exception e){

        }

        return inserted;  //To change body of created methods use File | Settings | File Templates.
    }


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert Cancel != null : "fx:id=\"Cancel\" was not injected: check your FXML file 'happinessTodo.fxml'.";
       // assert Category != null : "fx:id=\"Category\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert ImageHeader != null : "fx:id=\"ImageHeader\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert actualTasks != null : "fx:id=\"actualTasks\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert addTask != null : "fx:id=\"addTask\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert addTaskBox != null : "fx:id=\"addTaskBox\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert cancelStack != null : "fx:id=\"cancelStack\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert deleteRow != null : "fx:id=\"deleteRow\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert editColumn != null : "fx:id=\"editColumn\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert enterTask != null : "fx:id=\"enterTask\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert header != null : "fx:id=\"header\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert priority != null : "fx:id=\"priority\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert taskPriority != null : "fx:id=\"taskPriority\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert saveStack != null : "fx:id=\"saveStack\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert saveTask != null : "fx:id=\"saveTask\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert taskDetailColumn != null : "fx:id=\"taskDetailColumn\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert taskInformation != null : "fx:id=\"taskInformation\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert tasks != null : "fx:id=\"tasks\" was not injected: check your FXML file 'happinessTodo.fxml'.";
        assert taxanomy != null : "fx:id=\"taxanomy\" was not injected: check your FXML file 'happinessTodo.fxml'.";


        try {


            DB dbConnection = DBConnection.getTaskEngineDBInstance();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       // actualTasks.getColumns().addAll(taskDetailColumn,priority);
        initializeApp();
        // populateTaskViews("Any", "Any");
        // initialize your logic here: all @FXML variables will have been injected


    }

    private void initializeApp() {
        setTaskTableBinding();
        setTaskTreeBinding();

    }

    private void setTaskTreeBinding() {

        taskCategoriesOrPriorities = FXCollections.observableArrayList();

        taskCategories = FXCollections.observableArrayList(getAllCategoriesOrPriorites("category"));
        Category.setItems(taskCategories);
        tasks.setItems(taskCategoriesOrPriorities);
        /*tasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if(option.equalsIgnoreCase("category"))
                populateTaskViews(s2,"Any");

                if(option.equalsIgnoreCase("priority")){
                    populateTaskViews("Any",s2);
                }
            }
        });  */

        tasks.setCellFactory(new Callback<ListView<String>,
                ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {

                return new myListCellButton();
            }
        }
        );

    }

    private void setTaskTableBinding() {
        taskList = FXCollections.observableArrayList();
        taskDetailColumn.setCellValueFactory( new PropertyValueFactory<Tasks, String>("taskContent"));
        priority.setCellValueFactory(new PropertyValueFactory<Tasks, String>("priority"));
        actualTasks.setItems(taskList);
    }


}
