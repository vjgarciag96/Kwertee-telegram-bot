package tees

import tees.data.local.teesLocalDataModule
import tees.data.remote.teesRemoteDataModule
import tees.data.repository.teesRepositoryModule
import tees.domain.teesDomainModule

val teesModules = teesLocalDataModule + teesRemoteDataModule + teesRepositoryModule + teesDomainModule