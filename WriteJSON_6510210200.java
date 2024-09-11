import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class WriteJSON_6510210200 {

    public static void main(String[] args) {

        // สร้าง JSONArray เพื่อเก็บวัตถุทั้งหมด
        JSONArray peopleList = new JSONArray();

        // วัตถุที่ 1
        JSONObject obj1 = new JSONObject();
        obj1.put("firstname", "Piyachai");
        obj1.put("lastname", "Narongsab");
        obj1.put("age", 20); // ปรับอายุเป็น 20
        obj1.put("student_id", "6510210200");

        // เพิ่มที่อยู่สำหรับวัตถุที่ 1
        JSONObject address1 = new JSONObject();
        address1.put("road", "Khuanlang");
        address1.put("city", "Hatyai");
        address1.put("province", "Songkhla");
        address1.put("postalCode", "90110");

        obj1.put("address", address1);

        // เพิ่มข้อมูลภาษาแทน messages
        JSONArray languages1 = new JSONArray();
        languages1.add("C");
        languages1.add("Python");
        languages1.add("Java");

        obj1.put("language", languages1);

        // เพิ่มวัตถุที่ 1 ลงใน JSONArray
        peopleList.add(obj1);

        // วัตถุที่ 2 (ข้อมูลใหม่)
        JSONObject obj2 = new JSONObject();
        obj2.put("firstname", "Thanachart");
        obj2.put("lastname", "Thammarong");
        obj2.put("age", 30);
        obj2.put("student_id", "6510210480");

        // เพิ่มที่อยู่สำหรับวัตถุที่ 2
        JSONObject address2 = new JSONObject();
        address2.put("road", "Sukhumvit");
        address2.put("city", "Bangkok");
        address2.put("province", "Bangkok");
        address2.put("postalCode", "10110");

        obj2.put("address", address2);

        JSONArray languages2 = new JSONArray();
        languages2.add("C");
        languages2.add("Python");
        languages2.add("Java");

        obj2.put("language", languages2);

        // เพิ่มวัตถุที่ 2 ลงใน JSONArray
        peopleList.add(obj2);

        // ใช้ Gson เพื่อจัดรูปแบบ JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(peopleList);

        // แสดงผลลัพธ์
        System.out.println(prettyJsonString);

        // เขียนข้อมูลลงในไฟล์ JSON
        try (FileWriter file = new FileWriter("./simple_data_output.json")) {
            file.write(prettyJsonString);
            System.out.println("Completed");
        } catch (IOException e) {
            System.out.println("Handle exception here");
            e.printStackTrace();
        }
    }
}
