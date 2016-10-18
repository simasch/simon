package ch.simas.monitor.boundry

import ch.simas.monitor.control.MeasurementRepository
import ch.simas.monitor.entity.Measurement
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Collections
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
open class MeasurementController(@Autowired val measurementRepository: MeasurementRepository) {

    @RequestMapping("/measurements")
    fun getMeasurements(
            @RequestParam(value = "url", required = true) url: String,
            @RequestParam(value = "maxResults", required = false, defaultValue = "20") maxResults: Int,
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") dateFrom: LocalDate?,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") dateTo: LocalDate?): List<Measurement> {

        val measurements = measurementRepository.find(
                url,
                maxResults,
                if (dateFrom == null) null else LocalDateTime.of(dateFrom, LocalTime.of(0, 0)),
                if (dateTo == null) null else LocalDateTime.of(dateTo, LocalTime.of(23, 59)))

        Collections.sort(measurements) { o1, o2 -> o1.timestamp.compareTo(o2.timestamp) }
        return measurements
    }
}
