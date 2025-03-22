package meteorologicalmonitoring.controller;

import lombok.extern.slf4j.Slf4j;
import meteorologicalmonitoring.entity.VO.Result;
import meteorologicalmonitoring.entity.DTO.ChangePasswordDTO;
import meteorologicalmonitoring.entity.BO.User;
import meteorologicalmonitoring.service.AdminService;
import org.springframework.web.bind.annotation.*;



//用户的crud功能
@RestController
@Slf4j
@RequestMapping("/user")
public class AdminController {

    //构造器注入
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public Result hello(){
        return Result.ok("hello!");
    }


//    @PostMapping("/register")
//    public Result register(@RequestParam("username") String username, @RequestParam("password") String password){
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        log.info("register user: " + user);
//        return Result.ok(adminService.register(user));
//    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        log.info("register user: " + user);
        return Result.ok(adminService.register(user));
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("login user: " + user);
        return Result.ok(adminService.login(user));
    }

   @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        log.info("change password: " + changePasswordDTO);
        return Result.ok(adminService.changePassword(changePasswordDTO));
    }
}
