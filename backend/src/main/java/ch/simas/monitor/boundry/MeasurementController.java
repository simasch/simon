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
    public List<Measurement> get(@RequestParam(value = "url") String url) {
        if (url != null) {
            return measurementService.findByUrl(url);
        } else {
            return measurementService.findAll();
        }
    }

}
