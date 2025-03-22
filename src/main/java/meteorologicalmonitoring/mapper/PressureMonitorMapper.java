package meteorologicalmonitoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import meteorologicalmonitoring.entity.DTO.PressureMonitorDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PressureMonitorMapper extends BaseMapper<PressureMonitorDTO> {
}
