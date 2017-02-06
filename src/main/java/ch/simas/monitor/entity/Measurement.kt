package ch.simas.monitor.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Measurement(@Id @GeneratedValue var id: Int = 0,
                       var name: String = "",
                       var url: String = "",
                       var status: String = "",
                       var duration: Long? = 0,
                       var timestamp: LocalDateTime = LocalDateTime.now()) {

    val formattedTimestamp: String
        get() = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(timestamp)

    val isoTimestamp: String
        get() = DateTimeFormatter.ISO_DATE_TIME.format(timestamp)

}
