package meteorologicalmonitoring.service;

import meteorologicalmonitoring.entity.VO.GasData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class GasDataService {
    private BlockingQueue<GasData> gasDataQueue = new LinkedBlockingQueue<>();
    private int totalDataCount = 0;

    public GasDataService() {
        loadDataFromFile("data/LGR-20250203-0000-Gust.dat");
    }

    private void loadDataFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+"); // 根据空格分割
                if (values.length >= 22) { // 确保有足够的列
                    GasData data = new GasData();
                    data.setCo2(Double.parseDouble(values[17])); // CO2列
                    data.setCh4(Double.parseDouble(values[19])); // CH4列
                    data.setH2o(Double.parseDouble(values[21]) * 1000); // H2O列
                    gasDataQueue.offer(data);
                    totalDataCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GasData getNextData() {
        return gasDataQueue.poll(); // 获取并移除队列头部的元素
    }

    public int getTotalDataCount() {
        return totalDataCount; // 返回总数据行数
    }
}