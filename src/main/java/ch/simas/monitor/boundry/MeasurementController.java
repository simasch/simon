package ch.simas.monitor.boundry;

import ch.simas.monitor.control.MeasurementService;
import ch.simas.monitor.entity.Measurement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @RequestMapping("/measurements")
    public List<Measurement> getMeasurements(
            @RequestParam(value = "url", required = true) String url,
            @RequestParam(value = "maxResults", required = false, defaultValue = "20") Integer maxResults,
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {

        List<Measurement> measurements = measurementService.find(url, maxResults,
                dateFrom == null ? null : LocalDateTime.of(dateFrom, LocalTime.of(0, 0)),
                dateTo == null ? null : LocalDateTime.of(dateTo, LocalTime.of(23, 59)));
        Collections.sort(measurements, (Measurement o1, Measurement o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
        return measurements;
    }
}
