package example.com.data.network

import example.com.data.network.api.ApiServiceImpl
import example.com.data.network.dto.ResumeDto
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

fun Application.configureRouting() {

    routing {
        baseRoute()
        companiesRoute()
        listVacanciesRoute()
        vacanciesRoute()
        resumesRoute()
    }
}

fun Route.baseRoute() {
    get("/") {
        call.respondText("Hello, world!")
    }
}

fun Route.companiesRoute() {
    val logger = LoggerFactory.getLogger("RoutingLog")
    route("/companies") {
        get("/{companyId}") {
            val companyId = call.parameters["companyId"]?.toIntOrNull()
            val company = ApiServiceImpl.getCompany(companyId) ?: return@get
            logger.info("Id: $companyId, company: $company")
            call.respond(company)
        }
        get {
            val companies = ApiServiceImpl.getCompanyList()
            call.respond(companies)
        }
    }
}

fun Route.listVacanciesRoute() {
    route("/vacancies") {
        get {
            val vacancies = ApiServiceImpl.getVacancyList()
            call.respond(vacancies)
        }
    }
}

fun Route.vacanciesRoute() {
    route("/vacancies/{vacancyId}") {
        get {
            val vacancyId = call.parameters["vacancyId"]?.toIntOrNull()
            val vacancy = ApiServiceImpl.getVacancy(vacancyId) ?: return@get
            call.respond(vacancy)
        }
    }
}

fun Route.resumesRoute() {
    val logger = LoggerFactory.getLogger("RoutingLog")
    route("/resumes") {
        get() {
            val resumesList = ApiServiceImpl.getResumes()
            call.respond(resumesList)
        }

        get("/{resumeId}") {
            val resumeId = call.parameters["resumeId"]?.toIntOrNull() ?: return@get
            val resume = ApiServiceImpl.getResume(resumeId)
            call.respond(resume)
        }

        get("/my") {
            val resume = ApiServiceImpl.getResumes()[0]
            logger.info("Id: ${resume.id}, Resume: $resume")
            call.respond(resume)
        }

        post("/update/{resumeId}") {
            val resumeId = call.parameters["resumeId"]?.toIntOrNull() ?: return@post
            val newResume = call.receive<ResumeDto>()

            ApiServiceImpl.updateResume(newResume)
            val tags = ApiServiceImpl.analyzeResume()
            logger.info("Id: $resumeId, Seniority: $tags, Resume: $newResume")
            call.respond(tags)
        }

        get("/{resumeId}/tags") {
            val resumeId = call.parameters["resumeId"]?.toIntOrNull() ?: return@get
            val tags = ApiServiceImpl.getTags(resumeId)
            call.respondText(tags.toString())
        }
    }
}



