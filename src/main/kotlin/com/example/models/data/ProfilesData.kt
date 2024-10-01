package com.example.models.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Profiles : IntIdTable() {
    val user = reference("user", Users)
    val name = varchar("name", 100).nullable()
    val userType = varchar("user_type", 50)
    val phone = varchar("phone", 15).nullable()
    val email = varchar("email", 255).uniqueIndex()
    val address = varchar("address", 255).nullable()
}

class Profile(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Profile>(Profiles)

    var user by User referencedOn Profiles.user
    var name by Profiles.name
    var userType by Profiles.userType
    var phone by Profiles.phone
    var email by Profiles.email
    var address by Profiles.address
}