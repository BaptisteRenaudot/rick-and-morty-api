package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.EpisodeResponse
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.IdListValidator
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.location.Location

internal class LocationAPI(private val client: HttpClient){
    /**
     * Retrieves multiple locations by their IDs.
     *
     * This function accepts a list of location IDs (`ids`) and makes an API call to fetch the corresponding
     * locations. The API expects a string of IDs separated by commas, such as "/location/1,2,3".
     *
     * @param ids A list of location IDs to be retrieved.
     * @return A paginated response containing the requested episodes as `EpisodeResponse`.
     *
     * @throws Exception if the request fails or the response status is not `HttpStatusCode.OK`.
     */
    suspend fun getLocationsFromIds(@MustBeCommaSeparatedIds ids: String): List<LocationResponse> {

        IdListValidator.assertValid(ids)

        return client
            .get("location/$ids")
            .accept(HttpStatusCode.OK)
            .body()
    }

    /**
     * Fetches the details of a location with the given ID.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [LocationResponse] representing the details of the location.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()
}