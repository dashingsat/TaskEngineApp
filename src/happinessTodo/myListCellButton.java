package happinessTodo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/14/13
 * Time: 7:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class myListCellButton extends ListCell<String> {

    private Fire fire;
    public myListCellButton(){

    }
    public myListCellButton(Fire fire) {

        this.fire = fire;
    }

    @Override
    protected void updateItem(final String item, boolean b) {
        super.updateItem(item, b);
        final Button myButton = new Button();
        if(item != null){
            myButton.setText(item);
            myButton.setMinSize(175, 20);
            myButton.setMaxSize(175, 20);
            myButton.setAlignment(Pos.CENTER);
            myButton.setStyle("-fx-background-color: black");
            myButton.setStyle("-fx-font-weight:bold");
           // myButton.setStyle("-fx-font-size: 10");
            myButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {


                    if(Fire.option.equalsIgnoreCase("category")){
                      Fire.populateTaskViews(myButton.getText(),"Any");
                    }

                    if(Fire.option.equalsIgnoreCase("priority")){
                        Fire.populateTaskViews("Any",myButton.getText());
                    }
                }
            });
            setGraphic(myButton);
        }
    }
}
