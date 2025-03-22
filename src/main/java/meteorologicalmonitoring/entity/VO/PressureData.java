package meteorologicalmonitoring.entity.VO;

import lombok.*;

import java.time.LocalDateTime;


//VO为响应类
//这个是返回气压数据的VO
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PressureData {
    private String date;
    private String time;
    private double whMainPressure;
    private double tMainPressure;
    private double wlMainPressure;
    private double whBackupPressure;
    private double tBackupPressure;
    private double wlBackupPressure;

    //进气压力
    private double highLayerIntakePressure;

    //泄压流量
    private double highLayerDepressurizationFlow;

    //旁路流量
    private double highLayerBypassFlow;

    //干燥气压力
    private double highLayerDryGasPressure;

    //干燥气流量
    private double highLayerDryGasFlow;



    private double lowLayerIntakePressure;
    private double lowLayerDryGasPressure;
    private double lowLayerDepressurizationFlow;
    private double lowLayerDryGasFlow;
    private double lowLayerBypassFlow;
}