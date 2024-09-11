import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSimpleWriteExample {

    public static void main(String[] args) {

        JSONObject obj = new JSONObject();
        obj.put("firstname", "Kasikrit");
        obj.put("age", 23);

        //System.out.println(obj);

        JSONArray list = new JSONArray();
        list.add("Write: This is the text 1");
        list.add("Write: This is the text 2");
        list.add("Write: This is the text 3");

        //System.out.println(list);
        
        obj.put("messages", list);
        
        System.out.println(obj);

        System.out.println("Completed");

        try (FileWriter file = new FileWriter("./simple_data_output.json")) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            System.out.println("Handle exception here");
            e.printStackTrace();
        }

    }

}