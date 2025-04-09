package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.common.toList
import org.mathieu.cleanrmapi.data.local.DataStore
import org.mathieu.cleanrmapi.data.local.LocationDao
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.local.objects.toDetailedModel
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.remote.LocationAPI
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.Location
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

internal class LocationRepositoryImpl(
    private val dataStore: DataStore,
    private val locationApi: LocationAPI,
    private val locationDao: LocationDao
) : LocationRepository {

    private suspend fun getLocationsFromIdList(@MustBeCommaSeparatedIds idList: String): List<Location> {
        return if (idList.contains(",")) {
            val episodesResponse = locationApi.getLocationsFromIds(ids = idList)
            episodesResponse.map { it.toDBObject().toModel() }
        } else {
            val episodeResponse = locationApi.getLocation(idList.toInt())
            episodeResponse?.toDBObject()?.toModel()?.toList() ?: emptyList()
        }

    }

    /**
     * Orchestrates the retrieval of a LocationObject by attempting to fetch it locally first,
     * then remotely if it's not found in the local storage.
     *
     *  Note: By abstracting away part of the `getLocation` repository implementation logic into a sequential,
     *  clearly-defined process, this approach aims to improve code readability and maintainability.
     *  It leverages the principle of separation of concerns, ensuring efficient data retrieval by minimizing
     *  network requests and providing a robust mechanism for error handling when character data cannot be found.
     *
     * @param locationId The unique identifier for the location to be retrieved. This ID is used first
     * to attempt to fetch the location from local storage and then from a remote source if necessary.
     *
     * @return A LocationObject instance representing the location details. If the location is not found
     * locally, it is fetched from the remote API, converted into a realm object, and saved locally before
     * being returned.
     *
     * @throws Exception when the location cannot be found both locally and remotely.
     *
     */
    private object GetLocationObjectIfExists : KoinComponent {
        private val locationApi: LocationAPI by inject()
        private val locationLocal: LocationDao by inject()

        suspend operator fun invoke(locationId: Int): LocationObject =
            tryToGetLocationLocally(locationId)
                .fetchRemotelyIfNotFound(locationId)
                .throwIfWeCannotFindIt()

        private suspend fun tryToGetLocationLocally(locationId: Int) =
            locationLocal.getLocation(locationId)

        private suspend fun LocationObject?.fetchRemotelyIfNotFound(locationId: Int): LocationObject? {
            if (this != null) return this

            return locationApi.getLocation(id = locationId)
                ?.toDBObject()
                .also { obj ->
                    locationLocal.insert(obj ?: return null)
                }
        }

        private fun LocationObject?.throwIfWeCannotFindIt() = this
            ?: throw Exception("Location not found in local and remote storage.")
    }

    override suspend fun getLocation(id: Int): Location? {
        val locationLocal = GetLocationObjectIfExists(locationId = id)

        return locationLocal.toModel()
    }
}