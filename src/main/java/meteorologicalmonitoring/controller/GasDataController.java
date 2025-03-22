package meteorologicalmonitoring.controller;

import meteorologicalmonitoring.entity.VO.GasData;
import meteorologicalmonitoring.entity.VO.Result;
import meteorologicalmonitoring.service.GasDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//温室气体
@RestController
public class GasDataController {

    @Autowired
    private GasDataService gasDataService;

    @GetMapping("/api/gasdata")
    public Result getGasData() {
        GasData data = gasDataService.getNextData();
        if (data != null) {
            return Result.ok(data);
        } else {
            throw new RuntimeException("No more data available.");
        }
    }
}