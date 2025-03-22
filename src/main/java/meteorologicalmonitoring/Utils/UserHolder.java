package meteorologicalmonitoring.Utils;

import meteorologicalmonitoring.entity.BO.User;

/*
获取当前用户信息的工具类,UserDTO是用户信息的DTO类
 */
public class UserHolder {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();

    public static void saveUser(User user){
        tl.set(user);
    }

    public static User getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
