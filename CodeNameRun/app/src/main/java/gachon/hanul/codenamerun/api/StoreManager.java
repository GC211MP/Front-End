package gachon.hanul.codenamerun.api;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StoreManager {

    // Singleton
    private static StoreManager instance = new StoreManager();

    StoreManager(){}
    private Context context;

    public static StoreManager getInstance(Context context){
        instance.context = context;
        return instance;
    }



    // # Main Features
    // > Implemented with Singleton
    // - set rank data
    // - get rank table data
    // - get all rank table data
    // - get total distance
    // - get total calorie
    // - get total score


    // set rank
    public boolean setRank(DataDTO enrollData){

        // SQLite
        SqliteManager sqm = new SqliteManager(context, "user.db");

        // API Comm.
        UserDAO uDao = new UserDAO();
        UserDTO uDto = uDao.read(sqm.getID());
        int uIdx = uDto.getUser_idx();

        DataDAO dao = new DataDAO();
        boolean res = dao.create(uIdx, enrollData);
        return res;

    }


    // get rank table data
    public ArrayList<DataDTO> getRankTable(String feature, boolean isAsc, int stageId){
        DataDAO dao = new DataDAO();
        return dao.read(feature, isAsc, stageId);
    }


    // get all rank table data
    // feature, isAsc: Order by `feature` with ascending or descending order
    public ArrayList<DataDTO> getAllStageRankTable(String feature, boolean isAsc){
        DataDAO dao = new DataDAO();
        return dao.read(feature, isAsc, -1);
    }


    //이 부분이 근데 sqlite는 context가 parameter로 들어 있어서, 확신 X, 돌아가긴 돈다.
    //sqlite에 사용자 등록
    public boolean enrollUser(SqliteDto sDto){

        // 서버 연동 필요
        UserDAO uDao = new UserDAO();
        boolean isOk = uDao.create(new UserDTO(sDto.getId(), sDto.getName()), sDto.getPassword());

        SqliteManager sqm = new SqliteManager(context,"user.db");
        boolean res = false;
        if(isOk)
            res = sqm.insert(sDto);

        return res;
    }


    //sqlite에서 id를 통해서, 사용자 값을 읽어온다
    public PersonalData readUserData(Integer[] stages) {

        // SQLite
        SqliteManager sqm = new SqliteManager(context, "user.db");
        SqliteDto sDto = sqm.read(sqm.getID());

        ArrayList<Integer> totalDistance = new ArrayList<Integer>();
        for(int i = 0; i < stages.length; i++)
            totalDistance.add(this.getTotalDistance(stages[i]));

        ArrayList<Integer> totalCalorie = new ArrayList<Integer>();
        for(int i = 0; i < stages.length; i++)
            totalCalorie.add(this.getTotalCalorie(stages[i]));

        ArrayList<Integer> totalScore = new ArrayList<Integer>();
        for(int i = 0; i < stages.length; i++)
            totalScore.add(this.getTotalScore(stages[i]));

        int totalAllScore = this.getTotalScore(-1);


        PersonalData res = new PersonalData(
                sDto.getId(),
                sDto.getPassword(),
                sDto.getName(),
                sDto.getSex(),
                sDto.getHeight(),
                sDto.getWeight(),
                totalDistance,
                totalCalorie,
                totalScore,
                totalAllScore
        );

        return res;
    }

    

    //sqlite에서 이름과 비밀번호 수정.
    public boolean updateUserNamePassword(String userId, String name, String pw) {


        // 서버 연동 필요
        UserDAO uDao = new UserDAO();
        boolean isOk = uDao.update(userId, name, pw);

        SqliteManager sqm = new SqliteManager(context, "user.db");

        boolean res = false;
        if(isOk)
            res = sqm.updateNamePassword(userId, name, pw);

        return res;
    }


    //sqlite에서 키와 몸무게 수정.
    public boolean updateUserHeightWeight(int ht, int wt){
        SqliteManager sqm = new SqliteManager(context, "user.db");
        boolean res = sqm.updateHeightWeight(ht, wt);
        return res;
    }


    // - get total distance
    //   - stageId == -1 => total of all stages
    public int getTotalDistance(int stageId){
        try {

            // SQLite
            SqliteManager sqm = new SqliteManager(context, "user.db");
            SqliteDto sDto = sqm.read(sqm.getID());

            // API Comm.
            UserDAO uDao = new UserDAO();
            UserDTO uDto = uDao.read(sqm.getID());
            int uIdx = uDto.getUser_idx();

            Log.e("=====", "https://api.gcmp.doky.space/data/distance?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
            URL url = new URL("https://api.gcmp.doky.space/data/distance?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
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
            JSONObject json = new JSONObject(resultJson);

            return json.getInt("total_distance");

        } catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }


    // - get total calorie
    //   - stageId == -1 => total of all stages
    public int getTotalCalorie(int stageId){
        try {

            // SQLite
            SqliteManager sqm = new SqliteManager(context, "user.db");
            SqliteDto sDto = sqm.read(sqm.getID());

            // API Comm.
            UserDAO uDao = new UserDAO();
            UserDTO uDto = uDao.read(sqm.getID());
            int uIdx = uDto.getUser_idx();

            Log.e("=====", "https://api.gcmp.doky.space/data/calorie?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
            URL url = new URL("https://api.gcmp.doky.space/data/calorie?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
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
            JSONObject json = new JSONObject(resultJson);

            return json.getInt("total_calorie");

        } catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }


    // - get total score
    //   - stageId == -1 => total of all stages
    public int getTotalScore(int stageId){
        try {

            // SQLite
            SqliteManager sqm = new SqliteManager(context, "user.db");
            SqliteDto sDto = sqm.read(sqm.getID());

            // API Comm.
            UserDAO uDao = new UserDAO();
            UserDTO uDto = uDao.read(sqm.getID());
            int uIdx = uDto.getUser_idx();

            Log.e("=====", "https://api.gcmp.doky.space/data/score?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
            URL url = new URL("https://api.gcmp.doky.space/data/score?uidx=" + uIdx + (stageId != -1 ? "&stage=" + stageId : ""));
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
            JSONObject json = new JSONObject(resultJson);

            return json.getInt("total_score");

        } catch (Exception e) {
            Log.e("APIManager", "GET getUser method failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

}