package gachon.hanul.codenamerun.api;


import java.util.ArrayList;

public class PersonalData {
    public String id;
    public String password;
    public String userName;
    public String sex;
    public int userHeight;
    public int userWeight;
    public ArrayList<Integer> totalDistanceByStage;
    public ArrayList<Integer> totalCalorieByStage;
    public ArrayList<Integer> totalScoreByStage;
    public int totalScore;

    PersonalData(String id, String password, String userName, String sex, int userHeight, int userWeight, ArrayList<Integer> totalDistanceByStage, ArrayList<Integer> totalCalorieByStage, ArrayList<Integer> totalScoreByStage, int totalScore){
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.sex = sex;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.totalDistanceByStage = totalDistanceByStage;
        this.totalCalorieByStage = totalCalorieByStage;
        this.totalScoreByStage = totalScoreByStage;
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "PersonalData{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", userHeight=" + userHeight +
                ", userWeight=" + userWeight +
                ", totalDistanceByStage=" + totalDistanceByStage +
                ", totalCalorieByStage=" + totalCalorieByStage +
                ", totalScoreByStage=" + totalScoreByStage +
                ", totalScore=" + totalScore +
                '}';
    }
}