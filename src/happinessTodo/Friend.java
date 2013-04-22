package happinessTodo;

/**
 * Created with IntelliJ IDEA.
 * User: sdas
 * Date: 4/14/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
import javafx.collections.*;

/** Sample com.happiness.db.data for a table view */
public class Friend {
    final static public ObservableList data = FXCollections.observableArrayList(
            new Friend("George", "Movie Ticket", true),
            new Friend("Irene", "Pay Raise", false),
            new Friend("Ralph", "Return my Douglas Adams Books", false),
            new Friend("Otto", "Game of Golf", true),
            new Friend("Sally", "$12,045.98", false),
            new Friend("Antoine", "Latte", true)
    );

    final private String  name;
    final private String  owesMe;
    final private boolean willPay;
    public Friend(String name, String owesMe, boolean willPay) {
        this.name = name; this.owesMe = owesMe; this.willPay = willPay;
    }
    public String  getName()    { return name;    }
    public String  getOwesMe()  { return owesMe;  }
    public boolean getWillPay() { return willPay; }
}