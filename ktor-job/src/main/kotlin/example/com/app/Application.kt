package example.com.app

import example.com.data.db.*
import example.com.data.network.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun main() {
    //H2 connection
    val databasePath = File("data/test").absolutePath
    val jdbcUrl = "jdbc:h2:file:$databasePath"
    println("Database path: $databasePath")
    Database.connect(jdbcUrl, driver = "org.h2.Driver", user = "admin")

    //Ktor server
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        module()
    }.start(wait = true)

    //Creation scheme in DB
    transaction {
        SchemaUtils.create(
            CompaniesTable,
            VacanciesTable,
            ResumesTable,
            EducationsTable,
            ExperiencesTable,
            TagsTable
        )
    }
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    configureRouting()
}