package meteorologicalmonitoring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

@Mapper
public interface AlarmMapper {

    //自定义方法
    @Insert("INSERT INTO alarms(sensor, value, dateTime) VALUES(#{sensor}, #{value}, #{dateTime})")
    void insertAlarm(@Param("sensor") String sensor, @Param("value") Double value, @Param("dateTime") LocalDateTime dateTime);
}
