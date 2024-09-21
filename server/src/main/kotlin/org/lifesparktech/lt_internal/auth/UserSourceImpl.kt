package org.lifesparktech.lt_internal.auth

import io.ktor.server.auth.UserPasswordCredential


class UserSourceImpl : UserSource {

    override fun findUserById(id: Int): User = users.getValue(id)

    override fun findUserByCredentials(credential: UserPasswordCredential): User = testUser

    private val users = listOf(testUser).associateBy(User::id)

}