package com.praca.dyplomowa.backend.user.usecase

import com.praca.dyplomowa.backend.job.models.JobGetAllResponseCollection
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.user.models.UserGetAllResponse
import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import io.reactivex.rxjava3.core.Single
import org.springframework.stereotype.Service

@Service
class UserUseCase(
        private val userRepository: UserRepository
): IUserUseCase {
    override fun getUsers(): Single<UserGetAllResponseCollection> =
        userRepository.findAll().toList().map {
            UserGetAllResponseCollection(
                    it.map {
                        it.toUserGetAllResponseCollection()
                    }
            )
        }


    private fun User.toUserGetAllResponseCollection() =
            UserGetAllResponse(
                    id = this.id,
                    username = this.username,
                    name = this.name,
                    surname = this.surname
            )
}