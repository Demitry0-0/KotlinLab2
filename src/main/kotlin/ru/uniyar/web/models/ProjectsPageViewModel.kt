package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.Project

class ProjectsPageViewModel(val projects: List<Project>) : ViewModel
