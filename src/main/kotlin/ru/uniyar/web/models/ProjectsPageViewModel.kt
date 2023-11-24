package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.ProjectModel

class ProjectsPageViewModel(val projectModels: List<ProjectModel>) : ViewModel
