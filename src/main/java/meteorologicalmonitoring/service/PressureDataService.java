package meteorologicalmonitoring.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import meteorologicalmonitoring.entity.VO.Result;
import meteorologicalmonitoring.entity.VO.PressureData;
import meteorologicalmonitoring.entity.DTO.PressureMonitorDTO;
import meteorologicalmonitoring.mapper.PressureMonitorMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PressureDataService extends ServiceImpl<PressureMonitorMapper, PressureMonitorDTO> {


    //在数据库中修改阈值
    public Result updateMonitor(PressureMonitorDTO pressureMonitorDTO) {
        pressureMonitorDTO.setId(0);
        updateById(pressureMonitorDTO);
        log.info("修改成功");
        return Result.ok("修改成功！");
    }

    //从数据库中获取阈值并用Result包装返回前端
    public Result getNowMonitor() {
        return Result.ok(getById(0));
    }




    public Result readPressureData(String filePath) {
        List<PressureData> dataList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+");
                
                PressureData data = new PressureData();
                // 使用反射设置属性
                String[] propertyNames = {
                    "date", "time", "whMainPressure", "tMainPressure", "wlMainPressure",
                    "whBackupPressure", "tBackupPressure", "wlBackupPressure",
                    "highLayerIntakePressure", "highLayerDryGasPressure",
                    "highLayerDepressurizationFlow", "highLayerDryGasFlow", "highLayerBypassFlow",
                    "lowLayerIntakePressure", "lowLayerDryGasPressure",
                    "lowLayerDepressurizationFlow", "lowLayerDryGasFlow", "lowLayerBypassFlow"
                };
                
                for (int i = 0; i < propertyNames.length; i++) {
                    String propertyName = propertyNames[i];
                    Object value = i < 2 ? values[i] : Double.parseDouble(values[i]);
                    BeanUtils.setProperty(data, propertyName, value);
                }
                
                dataList.add(data);
            }
        } catch (IOException | ReflectiveOperationException e) {
            e.printStackTrace(); // Handle exceptions properly in production code
        }

        return Result.ok(dataList);
    }


}