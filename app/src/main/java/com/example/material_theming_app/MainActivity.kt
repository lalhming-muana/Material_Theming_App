package com.example.material_theming_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.material_theming_app.ui.theme.Material_theming_AppTheme
import com.example.material_theming_app.data.Dog
import com.example.material_theming_app.data.dogs
import com.example.material_theming_app.ui.theme.Material_theming_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Material_theming_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp()
                }
            }
        }
    }
}

/**
 * Composable that displays an app bar and a list of dogs.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofAppTopBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    painter = painterResource(R.drawable.ic_woof_logo),
                    contentDescription = null
                )

                Text(
                    text = "Woof",
                    style = MaterialTheme.typography.displayLarge
                )


            }
        },
        modifier = modifier
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofApp() {
    Scaffold(
        topBar = { WoofAppTopBar() }
    ) { contentPadding ->
    LazyColumn(contentPadding = contentPadding,
        modifier = Modifier.padding(16.dp)) {
        items(dogs) {
            DogItem(dog = it, modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        }
    }
    }
}

/**
 * Composable that displays a list item containing a dog icon and their information.
 *
 * @param dog contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var expanded by remember {mutableStateOf(false)}
    Card(modifier = modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        DogIcon(dog.imageResourceId)
        DogInformation(dog.name, dog.age)
        Spacer(modifier = Modifier.weight(1f))
        DogItemButton(expanded = expanded, onClick = { expanded = !expanded})
    }
        if(expanded){
    DogHobby(
        dog.hobbies,
        modifier= Modifier.padding(
            start = dimensionResource(R.dimen.padding_medium),
            top = dimensionResource(R.dimen.padding_small),
            end = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_medium))
        )}

    }
}

@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier
){
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
             imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
             contentDescription = stringResource(R.string.expand_button_content_description),
             tint = MaterialTheme.colorScheme.secondary   )


    }
}

/**
 * Composable that displays a photo of a dog.
 *
 * @param dogIcon is the resource ID for the image of the dog
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogIcon(
    @DrawableRes dogIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        painter = painterResource(dogIcon),

        // Content Description is not needed here - image is decorative, and setting a null content
        // description allows accessibility services to skip this element during navigation.
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}

/**
 * Composable that displays a dog's name and age.
 *
 * @param dogName is the resource ID for the string of the dog's name
 * @param dogAge is the Int that represents the dog's age
 * @param modifier modifiers to set to this composable
 */
@Composable
fun DogInformation(
    @StringRes dogName: Int,
    dogAge: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DogHobby(@StringRes dogHobby: Int,
             modifier: Modifier = Modifier
             ){
    Column (modifier = modifier) {
        Text(text = stringResource(R.string.about),
             style = MaterialTheme.typography.labelSmall)
        Text(text = stringResource(dogHobby),
            style = MaterialTheme.typography.bodyLarge)
}
}
/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun WoofPreview() {
    Material_theming_AppTheme(darkTheme = false) {
        WoofApp()
    }
}

/*
@Preview
@Composable
fun DarkThemeWoofPreview() {
    Material_theming_AppTheme(darkTheme = true) {
        WoofApp()
    }
}
*/