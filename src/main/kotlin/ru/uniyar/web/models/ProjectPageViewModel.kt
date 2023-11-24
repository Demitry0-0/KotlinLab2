package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel
import ru.uniyar.domain.models.SponsorModel
import ru.uniyar.dto.ProjectSponsors

class ProjectPageViewModel(model: ProjectSponsors) : ViewModel {
    val project: ProjectModel
    val sponsors: List<SponsorModel>
    val leftSum: Long
    val totalSum: Long

    init {
        project = model.project
        sponsors = model.sponsors
        leftSum = model.leftSum
        totalSum = model.totalSum
    }
}
