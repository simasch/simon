package ch.simas.monitor.boundry;

import ch.simas.monitor.control.MeasurementService;
import ch.simas.monitor.entity.Measurement;
import java.util.Collections;
import java.util.Date;
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
    public List<Measurement> getMeasurements(
            @RequestParam(value = "url", required = false) String url,
            @RequestParam(value = "maxResults", required = false, defaultValue = "20") Integer maxResults,
            @RequestParam(value = "dateFrom", required = false) Date dateFrom,
            @RequestParam(value = "dateTo", required = false) Date dateTo) {
        if (url == null) {
            return measurementService.findAll();
        } else {
            List<Measurement> measurements = measurementService.find(url, maxResults, dateFrom, dateTo);
            Collections.sort(measurements, (Measurement o1, Measurement o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
            return measurements;
        }
    }
}
