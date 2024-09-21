package org.lifesparktech.lt_internal.auth

import io.ktor.server.auth.UserPasswordCredential

interface UserSource {

    fun findUserById(id: Int): User

    fun findUserByCredentials(credential: UserPasswordCredential): User

}