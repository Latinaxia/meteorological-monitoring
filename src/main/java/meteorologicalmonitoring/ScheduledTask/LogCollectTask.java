package meteorologicalmonitoring.ScheduledTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

//定时任务，每小时整点执行一次日志收集任务
@Component
public class LogCollectTask {
    private static final String BASE_PATH = "acmos/Log/Collect";
    
    // 日期格式器
    private static final DateTimeFormatter YEAR_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM");
    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'.log'");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Scheduled(cron = "0 0 * * * *") // 每小时整点执行
    public void writeLogEntry() {
        LocalDateTime now = LocalDateTime.now();
        
        // 构建文件路径
        String filePath = buildFilePath(now);
        
        // 创建目录和文件
        createFileIfNotExists(filePath);
        
        // 写入日志内容
        writeToFile(filePath, generateLogContent(now));
    }

    private String buildFilePath(LocalDateTime time) {
        return BASE_PATH + "/" 
               + YEAR_MONTH_FORMAT.format(time) + "/" 
               + FILE_NAME_FORMAT.format(time);
    }

    private void createFileIfNotExists(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("文件创建失败", e);
        }
    }

    private String generateLogContent(LocalDateTime time) {
        LocalDateTime nextTime = time.truncatedTo(ChronoUnit.HOURS).plusHours(1);
        return String.format("%s,正常,LGR,数据采集成功 完成打包,LGR-04下次报文时间:%s\n",
                TIMESTAMP_FORMAT.format(time),
                TIMESTAMP_FORMAT.format(nextTime));
    }

    private void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath),
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("日志写入失败", e);
        }
    }
}
