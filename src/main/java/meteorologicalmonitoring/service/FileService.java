package meteorologicalmonitoring.service;

import lombok.extern.slf4j.Slf4j;
import meteorologicalmonitoring.entity.DTO.PressureMonitorDTO;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//专门用于文件操作的类，包括读取和存储监控阈值
@Slf4j
@Service
public class FileService {

    // 存储阈值到json文件中
    public void saveDataToFile(String pressureMonitorDTO, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // 将PressureMonitorDTO对象转换为字符串
            fileWriter.write(pressureMonitorDTO);
            log.info("数据已成功保存到文件 {}", filePath);
        } catch (IOException e) {
            log.error("保存数据到文件时发生错误: {}", e.getMessage());
        }
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    //从json文件中读取阈值
    public PressureMonitorDTO readMonitorDataFromFile() throws IOException {
        File file = new File("data/monitorData.json");
        return objectMapper.readValue(file, PressureMonitorDTO.class);
    }


}
