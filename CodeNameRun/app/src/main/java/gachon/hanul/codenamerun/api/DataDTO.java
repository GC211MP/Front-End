package gachon.hanul.codenamerun.api;


//->게임이 끝나면, (user_id, user_name, stage, distance,score, 칼로리) 서버에 보낸다.
//추가 수정이 필요하다.
public class DataDTO {

    @Override
    public String toString() {
        return "DataDTO{" +
                "user_name='" + user_name + '\'' +
                ", stage_id=" + stage_id +
                ", distance=" + distance +
                ", calorie=" + calorie +
                ", score=" + score +
                '}';
    }

    // Attribute
    private String user_name;
    private int stage_id;
    private int distance;
    private int calorie;
    private int score;

    public DataDTO(String user_name, int stage_id, int distance, int calorie, int score) {
        this.user_name = user_name;
        this.stage_id = stage_id;
        this.distance = distance;
        this.calorie = calorie;
        this.score = score;
    }

    // Getter
    public  Integer getStage_id() {
        return stage_id;
    }
    public Integer getDistance() {
        return distance;
    }
    public int getCalorie() {
        return calorie;
    }
    public int getScore() {
        return score;
    }
    public String getUser_name() {
        return user_name;
    }


    // Setter
    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }
    public void setDistance(int dis) {
        this.distance = dis;
    }
    public void setStage_id(int level) {
        this.stage_id = level;
    }
    public void setScore(int sc) {
        this.score = sc;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
