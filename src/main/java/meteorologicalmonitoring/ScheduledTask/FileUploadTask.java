package meteorologicalmonitoring.ScheduledTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//定时任务，每小时生成一个日志文件，并写入数据
@Component
public class FileUploadTask {

    // 每小时执行一次
    @Scheduled(cron = "0 0 * * * ?")
    public void generateFileAndWriteData() {
        // 获取当前日期和时间
        Date now = new Date();
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy/MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 构建文件路径
        String basePath = "acmos/Log/FileUpload/";
        String yearMonthPath = yearMonthFormat.format(now);
        String dayPath = dayFormat.format(now);
        String filePath = basePath + yearMonthPath + "/" + dayPath + ".log";

        // 创建目录和文件
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        // 写入数据
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String data = timeFormat.format(now) + ",正常,D:\\acmos\\UploadFile\\LGR,省局服务器,/cawn,FTP,报文,Z_CAWN_I_54826_20250114230000_O_GHG-FLD-CO2CH4-ICOS-3184.txt已上传";
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
