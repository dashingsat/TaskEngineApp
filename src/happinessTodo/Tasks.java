package happinessTodo;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/6/13
 * Time: 9:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class Tasks {

   private SimpleStringProperty taskContent;
   private SimpleStringProperty priority;
   private SimpleStringProperty category;

    public Tasks(String taskContent, String category, String priority) {
        this.taskContent = new SimpleStringProperty(taskContent);
        this.priority = new SimpleStringProperty(priority);
        this.category = new SimpleStringProperty(category);
    }

    public String getTaskContent() {
        return taskContent.get();
    }

    public SimpleStringProperty taskContentProperty() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent.set(taskContent);
    }

    public String getPriority() {
        return priority.get();
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }
}
