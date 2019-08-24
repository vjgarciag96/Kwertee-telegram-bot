package tees.data.repository

import tees.data.local.TeesLocalDataSource
import tees.data.local.toDO
import tees.data.remote.PromotedTeesDto
import tees.data.remote.TeeDto
import tees.data.remote.TeesRemoteDataSource

class TeesRepository(
    private val teesRemoteDataSource: TeesRemoteDataSource,
    private val teesLocalDataSource: TeesLocalDataSource
) {

    suspend fun fetchGoneForever(): List<TeeData> = teesRemoteDataSource.fetchGoneForever().map(TeeDto::toDataModel)

    suspend fun fetchPromoted(): PromotedTeesData {
        val localPromotedTees = teesLocalDataSource.fetchPromoted()

        if (localPromotedTees != null) {
            return localPromotedTees.toDataModel()
        }

        val remotePromotedTees = teesRemoteDataSource.fetchPromoted()
        storePromoted(remotePromotedTees)
        return remotePromotedTees.toDataModel()
    }

    private fun storePromoted(promotedTees: PromotedTeesDto) {
        val storageTees = promotedTees.toDO()
        teesLocalDataSource.putPromoted(storageTees)
    }
}