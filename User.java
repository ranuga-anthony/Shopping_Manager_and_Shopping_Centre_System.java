import java.util.ArrayList;

public class User {
    private String Cus_name;
    private String username;
    private int no_of_purchases = 0;

    public static ArrayList <User> list_of_users = new ArrayList<>();

    public User(String Cus_name, String username){
        this.Cus_name = Cus_name;
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public int getNo_of_purchases() {
        return this.no_of_purchases;
    }

    public void setNo_of_purchases() {
        this.no_of_purchases = this.no_of_purchases + 1 ;
    }
    public static void addUsertoList(User user) {
        boolean userExists = false;
        for (int i = 0; i < list_of_users.size(); i++) {
            if (user.getUsername().equals(list_of_users.get(i).getUsername())) {
                System.out.println("User already exists");
                userExists = true;
            }else{
                list_of_users.add(user);
            }
        }

    }
}
