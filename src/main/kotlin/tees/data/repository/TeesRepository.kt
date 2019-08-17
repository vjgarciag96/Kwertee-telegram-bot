package tees.data.repository

import tees.data.remote.TeesRemoteDataSource

class TeesRepository(private val teesRemoteDataSource: TeesRemoteDataSource) {

    suspend fun fetchGoneForever(): List<TeeData> = teesRemoteDataSource.fetchGoneForever()
}