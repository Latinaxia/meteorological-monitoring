package meteorologicalmonitoring.entity.DTO;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

//前后端传输数据的类
//这个是修改监控阈值的DTO
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("pressure_monitor")
public class PressureMonitorDTO {

    private Integer id;

    // WH主的上下限和报警值
    private float whMainUpperLimit;
    private float whMainLowerLimit;
    private float whMainAlarmUpper;
    private float whMainAlarmLower;

    // WH备的上下限和报警值
    private float whBackupUpperLimit;
    private float whBackupLowerLimit;
    private float whBackupAlarmUpper;
    private float whBackupAlarmLower;

    // 进气压力的上下限和报警值
    private float inletPressureUpperLimit;
    private float inletPressureLowerLimit;
    private float inletPressureAlarmUpper;
    private float inletPressureAlarmLower;

    // 泄压流量的上下限和报警值
    private float reliefFlowUpperLimit;
    private float reliefFlowLowerLimit;
    private float reliefFlowAlarmUpper;
    private float reliefFlowAlarmLower;

    // 旁路流量的上下限和报警值
    private float bypassFlowUpperLimit;
    private float bypassFlowLowerLimit;
    private float bypassFlowAlarmUpper;
    private float bypassFlowAlarmLower;

    // 干燥气压力的上下限和报警值
    private float dryGasPressureUpperLimit;
    private float dryGasPressureLowerLimit;
    private float dryGasPressureAlarmUpper;
    private float dryGasPressureAlarmLower;

    // 干燥气流量的上下限和报警值
    private float dryGasFlowUpperLimit;
    private float dryGasFlowLowerLimit;
    private float dryGasFlowAlarmUpper;
    private float dryGasFlowAlarmLower;

}