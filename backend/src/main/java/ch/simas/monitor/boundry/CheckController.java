package ch.simas.monitor.boundry;

import ch.simas.monitor.control.MeasurementService;
import ch.simas.monitor.entity.Measurement;
import ch.simas.monitor.xml.Hosts;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckController {

    private final static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

    private final String config;

    @Autowired
    private MeasurementService measurementService;

    public CheckController(@Value("${simon.config.hosts}") String config) {
        this.config = config;
    }

    @RequestMapping("/latest")
    public Hosts getLatest() {
        Hosts hosts = loadConfiguration(config);

        for (Hosts.Group group : hosts.getGroup()) {
            for (Hosts.Group.Host host : group.getHost()) {
                Measurement measurement = measurementService.getLatest(host.getUrl());
                if (measurement != null) {
                    host.setStatus(measurement.getStatus());
                    host.setDuration(measurement.getDuration());
                    host.setTimestamp(SDF.format(measurement.getTimestamp()));
                }
            }
        }
        return hosts;
    }

    @RequestMapping
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void check() {
        try {
            Hosts hosts = loadConfiguration(config);

            for (Hosts.Group group : hosts.getGroup()) {
                for (Hosts.Group.Host host : group.getHost()) {
                    long start = System.currentTimeMillis();
                    URL obj = new URL(host.getUrl());
                    try {
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        int responseCode = con.getResponseCode();
                        host.setStatus("" + responseCode);
                        host.setDuration(System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        host.setStatus(e.getLocalizedMessage());
                    }
                    measurementService.createMeasurement(host.getName(), host.getUrl(), host.getStatus(), host.getDuration());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Hosts loadConfiguration(String configFile) {
        try {
            File file = new File(configFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Hosts.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Hosts hosts = (Hosts) unmarshaller.unmarshal(file);
            return hosts;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
