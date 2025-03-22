package meteorologicalmonitoring.entity.BO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;


//对应数据表alarm
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("alarms")
public class Alarm {
    private Long id;
    private String sensor;
    private Double value;
    private LocalDateTime dateTime;

}
