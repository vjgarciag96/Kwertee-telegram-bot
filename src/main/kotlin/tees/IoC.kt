package tees

import tees.data.remote.teesRemoteDataModule
import tees.data.repository.teesRepositoryModule
import tees.domain.teesDomainModule

val teesModules = teesRemoteDataModule + teesRepositoryModule + teesDomainModule