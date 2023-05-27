import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

    public static void main(String[] args) {
        String json = "[{\"фамилия\":\"Иванов\",\"оценка\":\"5\",\"предмет\":\"Математика\"}," +
                "{\"фамилия\":\"Петрова\",\"оценка\":\"4\",\"предмет\":\"Информатика\"}," +
                "{\"фамилия\":\"Краснов\",\"оценка\":\"5\",\"предмет\":\"Физика\"}]";

        String result = parseJson(json);
        System.out.println(result);

        writeToLogFile(result);
    }

    private static String parseJson(String json) {
        StringBuilder builder = new StringBuilder();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String surname = object.getString("фамилия");
                String mark = object.getString("оценка");
                String subject = object.getString("предмет");

                builder.append("Студент ")
                        .append(surname)
                        .append(" получил ")
                        .append(mark)
                        .append(" по предмету ")
                        .append(subject)
                        .append(".")
                        .append(System.lineSeparator());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private static void writeToLogFile(String result) {
        try {
            Logger logger = Logger.getLogger("MyLog");
            FileHandler handler = new FileHandler("log.txt", true);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            logger.addHandler(handler);

            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
            writer.write(result);
            writer.close();

            logger.info("Rezultat uspeshno zapisan v fail.");
            handler.close();
        } catch (IOException e) {
            Logger.getLogger(JsonParser.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}