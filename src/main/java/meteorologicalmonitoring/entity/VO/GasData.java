package meteorologicalmonitoring.entity.VO;

import lombok.*;

//VO为响应类
//这个是返回co2，ch4 h2o数据的VO
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GasData {
    double co2;
    double ch4;
    double h2o;
}
