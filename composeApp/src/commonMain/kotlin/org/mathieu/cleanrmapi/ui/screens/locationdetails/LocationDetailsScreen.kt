package org.mathieu.cleanrmapi.ui.screens.locationdetails

import LocationDetailsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DoorFront
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LensBlur
import androidx.compose.material.icons.rounded.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.ui.core.composables.Avatar
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.IconWithImage
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

@Composable
fun LocationDetailsScreen(navController: NavController, id: Int) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() }, navController = navController
    ) { state, viewModel ->

        viewModel.init(locationId = id)

        Content(
            state = state,
            onClickBack = navController::popBackStack,
        )
    }
}

@Composable
private fun Content(
    state: LocationDetailsViewModel.LocationDetailsContract = LocationDetailsViewModel.LocationDetailsContract.Loading,
    onClickBack: () -> Unit = { }
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(), contentAlignment = Alignment.Center
    ) {

        BackArrow(
            modifier = Modifier.align(Alignment.TopStart).zIndex(1f), onClick = onClickBack
        )
        when (state) {
            is LocationDetailsViewModel.LocationDetailsContract.Loaded -> {
                SuccessView(
                    state = state
                )
            }

            is LocationDetailsViewModel.LocationDetailsContract.Error -> {
                ErrorView(error = state.message)
            }

            is LocationDetailsViewModel.LocationDetailsContract.Loading -> {
                Text(text = "Loading...")
            }
        }
    }
}

@Composable
private fun SuccessView(
    state: LocationDetailsViewModel.LocationDetailsContract.Loaded,
) {
    var offsetY by remember {
        mutableFloatStateOf(0f)
    }

    Column() {
        AdditionalInfo(
            state = state
        )

        LazyColumn {
            itemsIndexed(state.residents) { index, resident ->
                if (index == 0) {
                    Box(modifier = Modifier.onGloballyPositioned {
                        offsetY = it.positionInParent().y
                    })
                }


                CharacterCard(
                    modifier = Modifier.padding(8.dp), character = resident
                )

            }

        }
    }

}

@Composable
private fun AdditionalInfo(
    state: LocationDetailsViewModel.LocationDetailsContract.Loaded,
) = Row(
    modifier = Modifier.padding(8.dp).fillMaxWidth().height(IntrinsicSize.Max),
    horizontalArrangement = Arrangement.SpaceBetween
) {

    Spacer(Modifier.width(8.dp))

    IconWithImage(
        modifier = Modifier.weight(1f), imageVector = Icons.Rounded.Public, text = state.name
    )

    Spacer(Modifier.width(16.dp))

    IconWithImage(
        modifier = Modifier.weight(1f),
        imageVector = Icons.Rounded.LensBlur, text = state.type
    )

    Spacer(Modifier.width(16.dp))

    IconWithImage(
        modifier = Modifier.weight(1f),
        imageVector = Icons.Rounded.DoorFront,
        text = state.dimension
    )

    Spacer(Modifier.width(8.dp))

}

@Composable
private fun CharacterCard(
    modifier: Modifier, character: Character
) = Column(
    modifier = modifier.shadow(1.dp, spotColor = PrimaryColor).background(SurfaceColor)
        .fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
) {
    Avatar(
        contentScale = ContentScale.Fit,
        url = character.avatarUrl,
    )

    Text(
        text = character.name,
        fontSize = 11.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
private fun ErrorView(error: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = error,
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    )
}