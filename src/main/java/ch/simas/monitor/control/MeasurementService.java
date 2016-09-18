package ch.simas.monitor.control;

import ch.simas.monitor.entity.Measurement;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeasurementService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createMeasurement(String name, String url, String status, Long time) {
        Measurement measurement = new Measurement();
        measurement.setName(name);
        measurement.setUrl(url);
        measurement.setStatus(status);
        measurement.setDuration(time);
        measurement.setTimestamp(new Timestamp(System.currentTimeMillis()));

        em.persist(measurement);
    }

    public List<Measurement> findAll() {
        TypedQuery<Measurement> q = em.createQuery("select m from Measurement m", Measurement.class);
        return q.getResultList();
    }

    public List<Measurement> findByUrl(String url, int maxResults) {
        TypedQuery<Measurement> q = em.createQuery(
                "select m from Measurement m where m.url = :url order by m.timestamp desc",
                Measurement.class);
        q.setParameter("url", url);
        q.setMaxResults(maxResults);
        return q.getResultList();
    }

}
