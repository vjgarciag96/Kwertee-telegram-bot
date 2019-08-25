package tees.data.repository

import tees.data.local.TeeDO
import tees.data.local.TeesLocalDataSource
import tees.data.local.toDO
import tees.data.remote.PromotedTeesDto
import tees.data.remote.TeeDto
import tees.data.remote.TeesRemoteDataSource

class TeesRepository(
    private val teesRemoteDataSource: TeesRemoteDataSource,
    private val teesLocalDataSource: TeesLocalDataSource
) {

    fun fetchGoneForever(): List<TeeData> = fetchTees(
        localDataSourceQuery = teesLocalDataSource::fetchGoneForever,
        remoteDataSourceQuery = teesRemoteDataSource::fetchGoneForever
    )

    fun fetchLastChance(): List<TeeData> = fetchTees(
        localDataSourceQuery = teesLocalDataSource::fetchLastChance,
        remoteDataSourceQuery = teesRemoteDataSource::fetchLastChance
    )

    fun fetchPromoted(): PromotedTeesData {
        val localPromotedTees = teesLocalDataSource.fetchPromoted()

        if (localPromotedTees != null) {
            return localPromotedTees.toDataModel()
        }

        val remotePromotedTees = teesRemoteDataSource.fetchPromoted()
        storePromoted(remotePromotedTees)
        return remotePromotedTees.toDataModel()
    }

    private fun fetchTees(
        localDataSourceQuery: () -> List<TeeDO>?,
        remoteDataSourceQuery: () -> List<TeeDto>
    ): List<TeeData> {
        val localTees = localDataSourceQuery()

        if(localTees == null) {
            return remoteDataSourceQuery().map(TeeDto::toDataModel)
        }

        return localTees.map(TeeDO::toDataModel)
    }

    private fun storePromoted(promotedTees: PromotedTeesDto) {
        val storageTees = promotedTees.toDO()
        teesLocalDataSource.putPromoted(storageTees)
    }
}