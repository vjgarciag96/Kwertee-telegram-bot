package tees.data.local

import core.TimeProvider
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tees.domain.TimeToLiveHandler
import tees.data.local.exposed.TeeEntity
import tees.data.local.exposed.TeesTable

class TeesLocalDataSource(
    private val timeProvider: TimeProvider,
    private val timeToLiveHandler: TimeToLiveHandler
) {

    fun fetchPromoted(): PromotedTeesDO? = transaction {
        val storageTeesResult = TeesTable.selectAll()

        if (areValidTees(storageTeesResult)) {
            val timeToLive = storageTeesResult.first().getTimeToLive()

            val goneForeverTees = storageTeesResult.filter {
                it[TeesTable.type] == TypeDO.GONE_FOREVER
            }.map(ResultRow::toTeeDO)

            val lastChanceTees = storageTeesResult.filter {
                it[TeesTable.type] == TypeDO.LAST_CHANCE
            }.map(ResultRow::toTeeDO)

            return@transaction PromotedTeesDO(
                timeToLive = timeToLive,
                goneForeverTees = goneForeverTees,
                lastChanceTees = lastChanceTees
            )
        }

        return@transaction null
    }

    fun fetchGoneForever(): List<TeeDO>? = fetchTees(TypeDO.GONE_FOREVER)

    fun fetchLastChance(): List<TeeDO>? = fetchTees(TypeDO.LAST_CHANCE)

    private fun fetchTees(type: TypeDO): List<TeeDO>? = transaction {
        val storageTees = TeesTable.select {
            TeesTable.type eq type
        }

        if (areValidTees(storageTees)) {
            return@transaction storageTees.map(ResultRow::toTeeDO)
        }

        return@transaction null
    }

    fun putPromoted(promotedTees: PromotedTeesDO) = transaction {
        clearPromoted()
        storePromoted(promotedTees)
    }


    private fun areValidTees(storageTees: Query): Boolean {
        val anyStorageTee = storageTees.firstOrNull() ?: return false

        val timeToLive = anyStorageTee.getTimeToLive()
        val storageTimestamp = anyStorageTee.getStorageTimestamp()

        return timeToLiveHandler.isValid(storageTimestamp, timeToLive)
    }

    private fun clearPromoted(): Int = TeesTable.deleteAll()

    private fun storePromoted(promotedTees: PromotedTeesDO) {
        val storageTimestamp = timeProvider.currentTimeMillis()
        val timeToLive = promotedTees.timeToLive
        putTees(promotedTees.goneForeverTees, TypeDO.GONE_FOREVER, timeToLive, storageTimestamp)
        putTees(promotedTees.lastChanceTees, TypeDO.LAST_CHANCE, timeToLive, storageTimestamp)
    }

    private fun putTees(tees: List<TeeDO>, type: TypeDO, timeToLive: Int, storageTimestamp: Long) {
        tees.forEach { tee -> putTee(tee, type, timeToLive, storageTimestamp) }
    }

    private fun putTee(tee: TeeDO, type: TypeDO, timeToLive: Int, storageTimestamp: Long): TeeEntity =
        TeeEntity.new {
            eurPrice = tee.eurPrice
            usdPrice = tee.usdPrice
            gbpPrice = tee.gbpPrice
            title = tee.title
            imageUrl = tee.imageUrl
            this.timeToLive = timeToLive
            this.storageTimestamp = storageTimestamp
            this.type = type
        }
}

