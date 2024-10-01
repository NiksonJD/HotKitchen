package com.example.repository

import com.example.models.data.Profile
import com.example.models.data.Profiles
import com.example.models.data.User
import com.example.models.data.Users
import com.example.models.dto.ProfileDTO
import com.example.models.dto.UserDTO
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

object UserDb {
    fun findUser(userDTO: UserDTO): User? = transaction {
        User.find { (Users.email eq userDTO.email) and (Users.password eq userDTO.password) }.singleOrNull()
    }

    fun findUserByEmail(email: String): User? = transaction { User.find { Users.email eq email }.singleOrNull() }

    fun addUser(userDTO: UserDTO): User = transaction {
        User.new {
            email = userDTO.email
            password = userDTO.password
            userType = userDTO.userType
        }
    }

    fun findProfileByUser(user: User): Profile? = transaction {
        Profile.find { Profiles.user eq user.id }.singleOrNull()
    }

    fun profileDTOByUser(user: User): ProfileDTO? {
        val profile = findProfileByUser(user) ?: return null
        return ProfileDTO(profile.name, profile.userType, profile.phone, profile.email, profile.address)
    }

    fun updateProfile(user: User, profileDTO: ProfileDTO): Profile? = transaction {
        findProfileByUser(user)?.let { profile ->
            if (profile.email != profileDTO.email) {
                return@transaction null
            }
            profile.apply {
                name = profileDTO.name
                userType = profileDTO.userType
                phone = profileDTO.phone
                address = profileDTO.address
            }
        } ?: Profile.new {
            this.user = user
            this.name = profileDTO.name
            this.userType = profileDTO.userType
            this.email = profileDTO.email
            this.phone = profileDTO.phone
            this.address = profileDTO.address
        }
    }

    fun deleteUser(user: User) {
        transaction {
            findProfileByUser(user)?.delete()
            user.delete()
        }
    }
}