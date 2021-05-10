package gachon.hanul.codenamerun.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//->게임이 끝나면, (user_id, user_name, stage, distance, 칼로리) 서버에 보낸다.
public class DataDAO {
    //Enroll the data
    public boolean create(int userIdx, DataDTO dt) {
        String result = null;
        try {
            URL url=new URL("https://api.gcmp.doky.space/data/");
            JSONObject json = new JSONObject();
            json.put("user_idx", userIdx);
            json.put("stage_id", dt.getStage_id());
            json.put("distance",dt.getDistance());
            json.put("calorie",dt.getCalorie());
            json.put("score",dt.getScore());
            String body = json.toString();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", "length");
            conn.setRequestProperty("Content-Type", "application/json");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.write(body.getBytes("UTF-8"));
            os.flush();
            os.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder builder1 = new StringBuilder();
            while((inputLine = in.readLine()) != null) {
                builder1.append(inputLine);
            }
            result = builder1.toString();
            in.close();
            Log.e("APIManager", result);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Rank 읽어오기 위해서 Rank정보를 가져올 수 있게, 따로 클래스를 가져온다.
    // stage와 feature인 score에 따라, 정보를 순차적으로 가져온다.
    public ArrayList<DataDTO> read(String feature, boolean isAscending, int stage) {
        ArrayList<DataDTO> rk = new ArrayList<DataDTO>(); //Ranking 을 위한  Rank를 담을 class를 arraylist로 선언해서, arraylist에 담는다.
        try {
            Log.e("=====", "https://api.gcmp.doky.space/data?c=" + feature + "&o=" + (isAscending?"asc":"desc") + (stage != -1 ? "&stage=" + stage : ""));
            URL url = new URL("https://api.gcmp.doky.space/data?c=" + feature + "&o=" + (isAscending?"asc":"desc") + (stage != -1 ? "&stage=" + stage : ""));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            String resultJson = "";
            resultJson = builder.toString();
            JSONArray json = new JSONArray(resultJson);

            for (int i = 0; i < json.length(); i++) {
                String userName = json.getJSONObject(i).getString("user_name");
                int stageId = json.getJSONObject(i).getInt("stage_id");
                int distance = json.getJSONObject(i).getInt("distance");
                int calorie = json.getJSONObject(i).getInt("calorie");
                int score = json.getJSONObject(i).getInt("score");
                DataDTO result = new DataDTO(userName, stageId, distance, calorie, score);
                rk.add(result);
            }

        } catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
        }
        return rk;
    }


    // Not Implemented
    // public void update(DataDTO dt, int score) throws ClassNotFoundException, SQLException {}


    // Not Implemented
    // public void delete(DataDTO dt) throws ClassNotFoundException, SQLException {}

}

