import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class JsonSimpleReadExample {

    public static void main(String[] args) {

        int i=0;

        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader("./simple_data_output.json");

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            // System.out.println(jsonObject);

            String name = (String) jsonObject.get("firstname");
            System.out.println(name);

            long age = (Long) jsonObject.get("age");
            System.out.println(age);

            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("messages");            
            System.out.println(msg);

            System.out.println("######## Iterator #######");

            Iterator<String> iterator = msg.iterator();

            while (iterator.hasNext()) {

                System.out.println(i+" "+iterator.next());
                
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


}