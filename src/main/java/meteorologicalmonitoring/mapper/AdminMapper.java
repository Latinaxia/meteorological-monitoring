package meteorologicalmonitoring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import meteorologicalmonitoring.entity.BO.User;
import org.apache.ibatis.annotations.Mapper;

//mybatisplus接口，自带crud方法
@Mapper
public interface AdminMapper extends BaseMapper<User> {
}
