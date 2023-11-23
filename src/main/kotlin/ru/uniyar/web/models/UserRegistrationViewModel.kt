package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.domain.models.User

class UserRegistrationViewModel(val user: Map<String, Any?>?=null) : ViewModel
//class UserRegistrationViewModel : ViewModel{
//    val user:Any?
//    constructor(user: User){
//        this.user = user
//    }
//    constructor(user: Map<String, Any?>){
//        this.user = user
//    }
//
//}