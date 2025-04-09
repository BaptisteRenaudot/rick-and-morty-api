import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.ViewModel

class LocationDetailsViewModel :
    ViewModel<LocationDetailsViewModel.LocationDetailsContract>(LocationDetailsContract.Loading) {
    private val locationRepository: LocationRepository by inject()

    fun init(locationId: Int) {
        locationId?.let { id ->
            fetchData(
                source = {
                    locationRepository.getLocation(id)
                }
            ) {
                onSuccess { location ->
                    updateState {
                        LocationDetailsContract.Loaded(
                            name = location.name,
                            type = location.type,
                            dimension = location.dimension,
                            residents = location.residents,
                            isLoading = false
                        )
                    }
                }
                onFailure {
                    updateState {
                        LocationDetailsContract.Error(
                            message = it.message ?: it.toString()
                        )
                    }
                }
            }
        }
    }

    interface LocationDetailsContract {
        object Loading : LocationDetailsContract

        data class Error(val message: String) : LocationDetailsContract


        data class Loaded(
            val name: String = "",
            val type: String = "",
            val dimension: String = "",
            val residents: List<Character> = emptyList(),
            val isLoading: Boolean = true,
            val error: String? = null
        ) : LocationDetailsContract
    }
}