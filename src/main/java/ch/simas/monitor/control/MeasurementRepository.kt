package ch.simas.monitor.control

import ch.simas.monitor.entity.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Repository
open class MeasurementRepository(@PersistenceContext val em: EntityManager) {

    @Transactional
    open fun createMeasurement(name: String, url: String, status: String, time: Long) {
        val measurement = Measurement()
        measurement.name = name
        measurement.url = url
        measurement.status = status
        measurement.duration = time
        measurement.timestamp = LocalDateTime.now()

        em.persist(measurement)
    }

    open fun find(url: String, maxResults: Int, dateFrom: LocalDateTime?, dateTo: LocalDateTime?): List<Measurement> {
        var queryString = "select m from Measurement m where m.url = :url "
        if (dateFrom != null) {
            queryString += " and m.timestamp >= :dateFrom"
        }
        if (dateTo != null) {
            queryString += " and m.timestamp <= :dateTo"
        }
        queryString += " order by m.timestamp desc"

        val q = em.createQuery(queryString, Measurement::class.java)
        q.setParameter("url", url)
        if (dateFrom != null) {
            q.setParameter("dateFrom", dateFrom)
        }
        if (dateTo != null) {
            q.setParameter("dateTo", dateTo)
        }
        q.maxResults = maxResults
        return q.resultList
    }

}
