package gachon.hanul.codenamerun.api;


// WriteAgent 가 끝나면, (id, password, user_name, 성별, 키, 몸무게) 를 서버에 보낸다.
public class UserDTO {

    @Override
    public String toString() {
        return "UserDTO{" +
                "user_idx=" + user_idx +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }

    // Attribute
    private int user_idx;
    private String user_id;
    private String user_name;

    public UserDTO(String id, String name) {
        this.user_id=id;
        this.user_name=name;
    }

    // Getter
    public int getUser_idx() { return user_idx; }
    public String getUser_id()
        {
            return user_id;
        }
    public String getUser_name()
        {
            return user_name;
        }

    // Setter
    public void setUser_idx(int userIdx) { this.user_idx = userIdx; }
    public void setUser_id(String id)
    {
        this.user_id=id;
    }
    public void setUser_name(String s)
    {
        this.user_name=s;
    }

}
