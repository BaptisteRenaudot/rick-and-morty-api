package org.mathieu.cleanrmapi.ui.screens.locationdetails

import LocationDetailsViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.core.composables.Screen

@Composable
fun LocationDetailsScreen(navController: NavController, id: Int) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        viewModel.init(locationId = id)

        Content(
            state = state
        )
    }
}

@Composable
private fun Content(
    state: LocationDetailsViewModel.LocationDetailsContract = LocationDetailsViewModel.LocationDetailsContract.Loading,
) {
    when (state) {
        is LocationDetailsViewModel.LocationDetailsContract.Loaded -> {
            Text(text = state.name)
            Text(text = state.type)
            Text(text = state.dimension)
        }

        is LocationDetailsViewModel.LocationDetailsContract.Error -> {
            Text(text = state.message)
        }

        is LocationDetailsViewModel.LocationDetailsContract.Loading -> {
            Text(text = "Loading...")
        }
    }
}