package example.com.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tags(

    @SerialName("tags") val list: List<String> = mutableListOf(),
)