package happinessTodo;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/14/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

// demonstrates highlighting rows in a tableview based upon the com.happiness.db.data values in the rows.
public class DebtCollectionTable extends Application {
    public static void main(String[] args) throws Exception { launch(args); }
    public void start(final Stage stage) throws Exception {
        stage.setTitle("So called friends . . .");

        // create a table.
        TableView<Friend> table = new TableView(Friend.data);
        table.getColumns().addAll(makeStringColumn("Name", "name", 150), makeStringColumn("Owes Me", "owesMe", 300), makeBooleanColumn("Will Pay Up", "willPay", 150));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(250);

        stage.setScene(new Scene(table));
        stage.getScene().getStylesheets().add(getClass().getResource("reaper.css").toExternalForm());
        stage.show();

        // highlight the table rows depending upon whether we expect to get paid.
        int i = 0;
        for (Node n: table.lookupAll("TableRow")) {
            if (n instanceof TableRow) {
                TableRow row = (TableRow) n;
                if (table.getItems().get(i).getWillPay()) {
                    row.getStyleClass().add("willPayRow");
                } else {
                    row.getStyleClass().add("wontPayRow");
                }
                i++;
                if (i == table.getItems().size())
                    break;
            }
        }
    }

    private TableColumn<Friend, String> makeStringColumn(String columnName, String propertyName, int prefWidth) {
        TableColumn<Friend, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<Friend, String>(propertyName));
        column.setCellFactory(new Callback<TableColumn<Friend, String>, TableCell<Friend, String>>() {
            @Override public TableCell<Friend, String> call(TableColumn<Friend, String> soCalledFriendStringTableColumn) {
                return new TableCell<Friend, String>() {
                    @Override public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                        }
                    }
                };
            }
        });
        column.setPrefWidth(prefWidth);
        column.setSortable(false);
        return column;
    }

    private TableColumn<Friend, Boolean> makeBooleanColumn(String columnName, String propertyName, int prefWidth) {
        TableColumn<Friend, Boolean> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<Friend, Boolean>(propertyName));
        column.setCellFactory(new Callback<TableColumn<Friend, Boolean>, TableCell<Friend, Boolean>>() {
            @Override public TableCell<Friend, Boolean> call(TableColumn<Friend, Boolean> soCalledFriendBooleanTableColumn) {
                return new TableCell<Friend, Boolean>() {
                    @Override public void updateItem(final Boolean item, final boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                            this.getStyleClass().add(item ? "willPayCell" : "wontPayCell");
                        }
                    }
                };
            }
        });
        column.setPrefWidth(prefWidth);
        column.setSortable(false);
        return column;
    }
}