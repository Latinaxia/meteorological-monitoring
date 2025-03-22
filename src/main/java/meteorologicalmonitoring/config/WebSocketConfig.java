package meteorologicalmonitoring.config;

import meteorologicalmonitoring.handler.PressureDataWebSocketHandler;
import meteorologicalmonitoring.handler.WarningWebSocketHandler;
import meteorologicalmonitoring.mapper.AlarmMapper;
import meteorologicalmonitoring.service.FileService;
import meteorologicalmonitoring.service.PressureDataService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import org.springframework.beans.factory.annotation.Autowired;


//websocket路径配置
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final AlarmMapper alarmMapper;

    @Autowired
    public WebSocketConfig(AlarmMapper alarmMapper) {
        this.alarmMapper = alarmMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        WarningWebSocketHandler warningHandler = new WarningWebSocketHandler();
        PressureDataService pressureDataService = new PressureDataService();
        FileService fileService = new FileService();
        registry.addHandler(new PressureDataWebSocketHandler(warningHandler, pressureDataService, alarmMapper, fileService), "/ws/pressure-data").setAllowedOrigins("*");
        registry.addHandler(warningHandler, "/ws/warnings").setAllowedOrigins("*");
    }
}
