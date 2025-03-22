package meteorologicalmonitoring.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import meteorologicalmonitoring.Utils.JwtUtils;
import meteorologicalmonitoring.entity.BO.User;
import meteorologicalmonitoring.entity.DTO.ChangePasswordDTO;
import meteorologicalmonitoring.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

//用户的service层
@Service
public class AdminService extends ServiceImpl<AdminMapper, User> {
    public Object register(User user) {
        //判断用户是否存在
        User u = query().eq("username",user.getUsername()).one();

        if(u != null){
            return "用户已存在!";
        }

        save(user);
        return "注册成功！";
    }

    public Object login(User user) {

        //判断用户是否存在
        User u = query().eq("username",user.getUsername()).one();

        if(u == null){
            return "用户不存在!";
        }

        //校验密码
        String password = user.getPassword();
        if(password == null || password.isEmpty()){
            return "密码不能为空!";
        }

        if(!password.equals(u.getPassword())){
            return "密码错误!";
        }

        //转换为map
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());

        // 生成JWT
        String token = JwtUtils.generateJwt(claims, user.getId());

        //返回token
        return token;
    }

    public Object changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = query().eq("username",changePasswordDTO.getUsername()).one();

        if(user == null){
            return "用户不存在!";
        }

        //校验密码
        String password = changePasswordDTO.getPassword();
        if(!password.equals(user.getPassword())){
            return "原密码错误!";
        }

        //更新密码
        user.setPassword(changePasswordDTO.getNewPassword());
        updateById(user);

        return "密码修改成功!";
    }
}
