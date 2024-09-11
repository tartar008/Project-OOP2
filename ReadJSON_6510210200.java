import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

public class ReadJSON_6510210200 {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader("./simple_data_output.json");

            // เริ่มต้นด้วยการแปลงไฟล์ JSON เป็น JSONArray
            JSONArray peopleList = (JSONArray) parser.parse(reader);
            
            // วนลูปเพื่อเข้าถึงแต่ละ JSONObject ใน JSONArray
            for (Object obj : peopleList) {
                JSONObject person = (JSONObject) obj;

                String firstname = (String) person.get("firstname");
                String lastname = (String) person.get("lastname");
                String studentId = (String) person.get("student_id");
                long age = (Long) person.get("age");

                System.out.println("Fullname: " + firstname + " " + lastname);
                System.out.println("Student ID: " + studentId);
                System.out.println("Age: " + age);

                JSONArray language = (JSONArray) person.get("language");
                System.out.println("language:");

                Iterator<?> iterator = language.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    System.out.println("  " + i + ": " + iterator.next());
                    i++;
                }

                System.out.println("----------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
