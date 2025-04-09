package org.mathieu.cleanrmapi.domain.location.models

/**
 * Represents a preview of a location in the Rick&Morty universe.
 *
 * @property name The name of the location.
 * @property url The URL pointing to more information about the location.
 */
data class LocationPreview (
    val id: Int,
    val name: String,
)