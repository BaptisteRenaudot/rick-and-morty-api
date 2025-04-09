package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

/**
 *
 * Realm representation of an location with only useful stored information gathered mainly
 * from remote source.
 *
 * @property name Name of the location.
 * @property url The URL of the location.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String,
    val created: String
)

internal fun LocationResponse.toDBObject() = LocationObject(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls(),
    created = created
)

internal suspend fun LocationObject.toModel(
    idsToCharacterConverter: suspend (charactersIds: String) -> List<Character> = { emptyList() }
) = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = idsToCharacterConverter(residentsIds),
)