package core

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class ExecutorServiceFactory {

    fun newSingleThreadScheduledThreadPool(): ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
}