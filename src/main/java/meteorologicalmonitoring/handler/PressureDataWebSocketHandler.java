package meteorologicalmonitoring.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import meteorologicalmonitoring.entity.DTO.PressureMonitorDTO;
import meteorologicalmonitoring.entity.VO.PressureData;
import meteorologicalmonitoring.mapper.AlarmMapper;
import meteorologicalmonitoring.service.FileService;
import meteorologicalmonitoring.service.PressureDataService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

//实时传输气压信息
@Slf4j
public class PressureDataWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private String filePath = "data/Z_CAWN_I_54826_20250116220000_O_GHG-FLD-PF-XXXX-1013.txt";
    private BufferedReader br;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WarningWebSocketHandler warningHandler;
    private final PressureDataService pressureDataService;
    private final AlarmMapper alarmMapper; // 新增的依赖

    private final FileService fileService; // 新增的依赖

    //构造函数注入
    public PressureDataWebSocketHandler(WarningWebSocketHandler warningHandler,
                                        PressureDataService pressureDataService,
                                        AlarmMapper alarmMapper, FileService fileService) { // 添加新参数
        this.warningHandler = warningHandler;
        this.pressureDataService = pressureDataService;
        this.alarmMapper = alarmMapper; // 初始化新依赖
        this.fileService = fileService;
    }


//    private final WarningWebSocketHandler warningHandler;
//    private final PressureDataService pressureDataService;
//
//    public PressureDataWebSocketHandler(WarningWebSocketHandler warningHandler, PressureDataService pressureDataService) {
//        this.warningHandler = warningHandler;
//        this.pressureDataService = pressureDataService;
//    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("已连接到 WebSocket 客户端");
        sessions.add(session);

        if (br == null) {
            br = new BufferedReader(new FileReader(filePath));
            new Thread(this::readData).start();
        }
    }

    private void readData() {
        log.info("Start reading data from TXT file");
        String currentLine;

        try {
            if (br != null) {
                br.readLine(); // 跳过第一行
            }

            while (true) {
                if (br == null) {
                    break; // 如果 BufferedReader 为 null，则退出循环
                }

                currentLine = br.readLine();
                if (currentLine == null) {
                    break; // 到达文件末尾
                }

                // 假设文件格式为：日期 时间 WH主压力（psi） ... 其他传感器值
                String[] parts = currentLine.split(" ");
                if (parts.length == 18) {
                    PressureData pressureData = new PressureData(
                            parts[0], parts[1], // 日期和时间
                            Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]), Double.parseDouble(parts[6]), Double.parseDouble(parts[7]),
                            Double.parseDouble(parts[8]), Double.parseDouble(parts[9]), Double.parseDouble(parts[10]),
                            Double.parseDouble(parts[11]), Double.parseDouble(parts[12]), Double.parseDouble(parts[13]),
                            Double.parseDouble(parts[14]), Double.parseDouble(parts[15]), Double.parseDouble(parts[16]),
                            Double.parseDouble(parts[17])
                    );

                    // 检查是否超出报警上下限
                    checkForAlarms(pressureData);

                    sendChunkedData(pressureData);
                    log.info("Pressure data 已发送");
                }

                Thread.sleep(1000); // 每秒读取一行
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O error while reading the file: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Data reading interrupted: " + e.getMessage(), e);
        } finally {
            // 只有当没有任何 WebSocket 会话时才关闭 BufferedReader
            if (sessions.isEmpty()) {
                closeBufferedReader();
            }
        }
    }

    private void sendChunkedData(PressureData pressureData) throws IOException {
        // 分块 1：时间相关和主压力
        Map<String, Object> chunk1 = new HashMap<>();
        chunk1.put("date", pressureData.getDate());
        chunk1.put("time", pressureData.getTime());
        chunk1.put("whMainPressure", pressureData.getWhMainPressure());
        chunk1.put("tMainPressure", pressureData.getTMainPressure());
        chunk1.put("wlMainPressure", pressureData.getWlMainPressure());
        chunk1.put("whBackupPressure", pressureData.getWhBackupPressure());
        chunk1.put("tBackupPressure", pressureData.getTBackupPressure());
        chunk1.put("wlBackupPressure", pressureData.getWlBackupPressure());


        // 分块 2：高层传感器
        Map<String, Object> chunk2 = new HashMap<>();
        chunk2.put("highLayerIntakePressure", pressureData.getHighLayerIntakePressure());
        chunk2.put("highLayerDryGasPressure", pressureData.getHighLayerDryGasPressure());
        chunk2.put("highLayerDryGasFlow", pressureData.getHighLayerDryGasFlow());
        chunk2.put("highLayerBypassFlow", pressureData.getHighLayerBypassFlow());
        chunk2.put("highLayerDepressurizationFlow", pressureData.getHighLayerDepressurizationFlow());

        // 分块 3：低层传感器
        Map<String, Object> chunk3 = new HashMap<>();
        chunk3.put("lowLayerIntakePressure", pressureData.getLowLayerIntakePressure());
        chunk3.put("lowLayerDryGasPressure", pressureData.getLowLayerDryGasPressure());
        chunk3.put("lowLayerDryGasFlow", pressureData.getLowLayerDryGasFlow());
        chunk3.put("lowLayerBypassFlow", pressureData.getLowLayerBypassFlow());
        chunk3.put("lowLayerDepressurizationFlow", pressureData.getLowLayerDepressurizationFlow());

        // 将每个分块分别发送
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chunk1)));
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chunk2)));
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(chunk3)));
            }
        }
    }

    private void checkForAlarms(PressureData pressureData) throws IOException {
        // 获取阈值
        PressureMonitorDTO nowMonitor = fileService.readMonitorDataFromFile();

        // 检查 WH主压力是否超出上下限
        double whMainPressure = pressureData.getWhMainPressure();
        if (whMainPressure > nowMonitor.getWhMainAlarmUpper() || whMainPressure < nowMonitor.getWhMainAlarmLower()) {
            handleAlarm("WH主压力", whMainPressure);
        }

        // 检查 WH备压力是否超出上下限
        double whBackupPressure = pressureData.getWhBackupPressure();
        if (whBackupPressure > nowMonitor.getWhBackupAlarmUpper() || whBackupPressure < nowMonitor.getWhBackupAlarmLower()) {
            handleAlarm("WH备压力", whBackupPressure);
        }

        // 检查 进气压力是否超出上下限
        double intakePressure = pressureData.getHighLayerIntakePressure();
        if (intakePressure > nowMonitor.getInletPressureAlarmUpper() || intakePressure < nowMonitor.getInletPressureAlarmLower()) {
            handleAlarm("进气压力", intakePressure);
        }

        // 检查 泄压流量是否超出上下限
        double depressurizationFlow = pressureData.getHighLayerDepressurizationFlow();
        if (depressurizationFlow > nowMonitor.getReliefFlowAlarmUpper() || depressurizationFlow < nowMonitor.getReliefFlowAlarmLower()) {
            handleAlarm("泄压流量", depressurizationFlow);
        }

        // 检查 旁路流量是否超出上下限
        double bypassFlow = pressureData.getHighLayerBypassFlow();
        if (bypassFlow > nowMonitor.getBypassFlowAlarmUpper() || bypassFlow < nowMonitor.getBypassFlowAlarmLower()) {
            handleAlarm("旁路流量", bypassFlow);
        }

        // 检查 干燥气压力是否超出上下限
        double dryGasPressure = pressureData.getHighLayerDryGasPressure();
        if (dryGasPressure > nowMonitor.getDryGasPressureAlarmUpper() || dryGasPressure < nowMonitor.getDryGasPressureAlarmLower()) {
            handleAlarm("干燥气压力", dryGasPressure);
        }

        // 检查 干燥气流量是否超出上下限
        double dryGasFlow = pressureData.getHighLayerDryGasFlow();
        if (dryGasFlow > nowMonitor.getDryGasFlowAlarmUpper() || dryGasFlow < nowMonitor.getDryGasFlowAlarmLower()) {
            handleAlarm("干燥气流量", dryGasFlow);
        }
    }

        private void handleAlarm(String sensor, double value) {
            String warningMessage = createWarningMessage(sensor, value);
            log.info("报警信息已发送");
            warningHandler.sendWarning(warningMessage);

            // 将警报信息存入数据库
            alarmMapper.insertAlarm(sensor, value, LocalDateTime.now());
        }

        private String createWarningMessage(String sensor, double value) {
            return String.format("{\"sensor\": \"%s\", \"value\": %.2f, \"dateTime\": \"%s\"}",
                    sensor, value, java.time.LocalDateTime.now());
        }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("WebSocket 客户端已断开连接");
        sessions.remove(session);
        // 只有当没有任何 WebSocket 会话时才关闭 BufferedReader
        if (sessions.isEmpty()) {
            closeBufferedReader();
        }
    }

    private void closeBufferedReader() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing BufferedReader: " + e.getMessage(), e);
            } finally {
                br = null;
            }
        }
    }
}
