package ch.simas.monitor.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Measurement : Serializable {

    @Id
    @GeneratedValue
    var id: Int? = null
    var name: String? = null
    var url: String? = null
    var status: String? = null
    var duration: Long? = null
    var timestamp: LocalDateTime? = null

    val formattedTimestamp: String
        get() = DTF.format(timestamp!!)

    val isoTimestamp: String
        get() = DateTimeFormatter.ISO_DATE_TIME.format(timestamp!!)

    companion object {

        private val DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    }
}
