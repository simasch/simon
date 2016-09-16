package ch.simas.monitor.boundry;

import ch.simas.monitor.control.MeasurementService;
import ch.simas.monitor.entity.Measurement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @RequestMapping("/measurements")
    public List<Measurement> latestResults(@RequestParam(value = "url", required = false) String url) {
        if (url == null) {
            return measurementService.findAll();
        } else {
            return measurementService.findByUrl(url);
        }
    }
}
