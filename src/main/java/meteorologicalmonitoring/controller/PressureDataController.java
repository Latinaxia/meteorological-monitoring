package meteorologicalmonitoring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import meteorologicalmonitoring.entity.VO.Result;
import meteorologicalmonitoring.entity.DTO.PressureMonitorDTO;
import meteorologicalmonitoring.service.FileService;
import meteorologicalmonitoring.service.PressureDataService;
import org.springframework.web.bind.annotation.*;

import static meteorologicalmonitoring.Utils.SystemConstants.*;

//气压检测，阈值修改等等
@RestController
@Slf4j
public class PressureDataController {

    //构造器注入
    private final PressureDataService pressureDataService;
    private final FileService fileService;

    public PressureDataController(PressureDataService pressureDataService, FileService fileService) {
        this.pressureDataService = pressureDataService;
        this.fileService = fileService;
    }


    private final ObjectMapper objectMapper = new ObjectMapper();

    //获取当前数据
    @GetMapping("/api/pressure-data")
    public Result getPressureData() {
        String filePath = GASE_MONITOR_PATH + DATA_FILE_PATH_FROMER
                + station + DATA_FILE_PATH_AFTER;
        return pressureDataService.readPressureData(filePath);
    }

    //修改阈值
    @PostMapping("api/data/batch-update")
    public Result updateMonitor(@RequestBody PressureMonitorDTO pressureMonitorDTO) {
        log.info("已接受到 {}", pressureMonitorDTO);

        // 将PressureMonitorDTO对象转换为JSON字符串
        try {
            String jsonString = objectMapper.writeValueAsString(pressureMonitorDTO);
            // 保存阈值到json文件中，覆盖原文件
            fileService.saveDataToFile(jsonString, MONITOR_DATA_FILE_PATH);
        } catch (Exception e) {
            log.error("保存阈值到文件时出错: {}", e.getMessage());
            return Result.fail("保存阈值到文件时出错");
        }

        // 返回结果
        return pressureDataService.updateMonitor(pressureMonitorDTO);
    }


    //获取当前阈值
    @GetMapping("api/data/monitor")
    @ResponseBody
    public Result getNowMonitor(){
        return pressureDataService.getNowMonitor();
    }

}