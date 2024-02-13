package com.example.taskn22.data.common

import com.example.taskn21.data.remote.common.HandleErrorStates
import com.example.taskn21.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response

class HandleResponse {
    suspend fun <T: Any> safeApiCall(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(response = body))
            } else {
                throw HttpException(response)
            }
        } catch (t: Throwable) {
            emit(Resource.Error(error = HandleErrorStates.handleException(t), throwable = t))
        }finally {
            emit(Resource.Loading(false))
        }
    }
}