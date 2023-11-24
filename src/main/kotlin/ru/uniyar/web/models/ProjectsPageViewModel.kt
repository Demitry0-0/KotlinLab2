package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel

class ProjectsPageViewModel(val projects: List<ProjectModel>) : ViewModel
