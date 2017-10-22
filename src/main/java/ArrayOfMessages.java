import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayOfMessages {
    private static final ArrayOfMessages instance = new ArrayOfMessages();
    private static List<JSONObject> list = new ArrayList<JSONObject>();
    private static boolean notBusy=true;

    private ArrayOfMessages(){

    }

    public static List<JSONObject> getList(){
        return list;
    }
    public static void addElement(JSONObject o){
        list.add(o);
        if (notBusy) {
            Timer t = new Timer();
            t.start();
            notBusy=false;
        }

    }

    public static void setNotBusy(boolean notBusy) {
        ArrayOfMessages.notBusy = notBusy;
    }

    public static void clear(){
        list = new ArrayList<JSONObject>();
    }


}
