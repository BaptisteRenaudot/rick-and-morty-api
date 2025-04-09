package org.mathieu.cleanrmapi.domain.location

interface LocationRepository {
    /**
     * Fetches the details of a location with the given ID.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [Location] representing the details of the location.
     */
    suspend fun getLocation(id: Int): Location
}