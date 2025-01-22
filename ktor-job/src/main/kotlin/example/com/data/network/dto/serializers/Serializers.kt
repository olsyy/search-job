package example.com.data.network.dto.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter


object DateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    override fun deserialize(decoder: Decoder): LocalDate {
        val dateString = decoder.decodeString()
        return LocalDate.parse(dateString, formatter)
    }
    override fun serialize(encoder: Encoder, value: LocalDate) {
        val dateString = value.format(formatter)
        encoder.encodeString(dateString)
    }
}

object YearSerializer  : KSerializer<Year> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Year", PrimitiveKind.STRING)
    private val formatter = DateTimeFormatter.ofPattern("yyyy")
    override fun deserialize(decoder: Decoder): Year {
        val dateString = decoder.decodeString()
        return Year.parse(dateString)
    }
    override fun serialize(encoder: Encoder, value: Year) {
        val dateString = value.format(formatter)
        encoder.encodeString(dateString)
    }
}

object YearMonthSerializer  : KSerializer<YearMonth> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("YearMonth", PrimitiveKind.STRING)
    private val formatter = DateTimeFormatter.ofPattern("MM.yyyy")
    override fun deserialize(decoder: Decoder): YearMonth {
        val dateString = decoder.decodeString()
        return YearMonth.parse(dateString, formatter)
    }
    override fun serialize(encoder: Encoder, value: YearMonth) {
        val dateString = value.format(formatter)
        encoder.encodeString(dateString)
    }
}
