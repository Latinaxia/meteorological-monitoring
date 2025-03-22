package meteorologicalmonitoring.entity.DTO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

//前后端传输数据的类
//这个是修改密码时用的DTO
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

        private Integer id;
        private String username;
        private String password;
        private String newPassword;


}
