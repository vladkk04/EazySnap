package com.arkul.eazysnap.presentation.screen.auth.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.arkul.eazysnap.domain.model.Country
import eazysnap.composeapp.generated.resources.Res
import eazysnap.composeapp.generated.resources.country_dial_codes
import eazysnap.composeapp.generated.resources.ic_ua
import eazysnap.composeapp.generated.resources.label_phone_number
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneNumberTextField(
    countries: Array<Country>,
    modifier: Modifier = Modifier
) {
    var selectedCountry by remember { mutableStateOf(countries.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var textFieldPosition by remember { mutableStateOf(Offset.Zero) }

    var value by remember { mutableStateOf("+${selectedCountry.code}") }

    var textFieldValueState by remember(selectedCountry) {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    val density = LocalDensity.current

    TextField(
        value = textFieldValueState,
        onValueChange = { textFieldValueState = it },
        singleLine = true,
        leadingIcon = {
            LeadingIcon(
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                selectedCountry = selectedCountry
            )
        },
        label = { Text(stringResource(Res.string.label_phone_number)) },
        textStyle = TextStyle(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .onFocusChanged { if (it.isFocused) isExpanded = false }
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size
                textFieldPosition = coordinates.positionInParent()
            }
    )
    CountryDropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false },
        onItemClick = {
            selectedCountry = it
            value = "+${it.code}"
        },
        countries = countries,
        modifier = Modifier
            .width(with(density) { textFieldSize.width.toDp() })
            .offset(
                with(density) { textFieldPosition.x.toDp() },
                with(density) { textFieldPosition.y.toDp() + textFieldSize.height.toDp() + 4.dp })
    )
}

@Composable
private fun LeadingIcon(
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedCountry: Country,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp, 0.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onExpandedChange(!isExpanded) }
    ) {
        FlagIcon(selectedCountry.flag)
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "Trailing icon for exposed dropdown menu",
            tint = Color.White,
            modifier = Modifier.rotate(if (isExpanded) 180f else 0f)
        )
    }
}

@Composable
private fun CountryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (Country) -> Unit,
    countries: Array<Country>,
    modifier: Modifier = Modifier,
) {
    var searchValue by remember { mutableStateOf("") }

    val filteredCountries = remember(searchValue) {
        countries.filter {
            it.name.contains(searchValue, ignoreCase = true)
        }.sortedBy { it.name }
    }

    if (expanded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismissRequest() }
                .background(Color.Transparent)
        ) {
            Column(
                modifier = modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                SearchTextField(onValueChange = { query ->
                    searchValue = query.toString()
                })
                Divider()
                LazyColumn {
                    items(
                        count = filteredCountries.size,
                        key = { index -> filteredCountries[index].name }
                    ) { index ->
                        val country = filteredCountries[index]
                        DropdownMenuItem(
                            onClick = {
                                onItemClick(country)
                                onDismissRequest()
                            }
                        ) {
                            FlagIcon(country.flag, country.name)
                            Text(
                                text = stringResource(
                                    Res.string.country_dial_codes,
                                    country.name,
                                    country.code
                                )
                            )
                        }
                    }
                }
                if (filteredCountries.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {}
                            .fillMaxSize()
                    ) {
                        Text("No result")
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    onValueChange: (CharSequence) -> Unit,
    modifier: Modifier = Modifier
) {
    val textState = rememberTextFieldState()

    LaunchedEffect(textState.text) {
        onValueChange(textState.text)
    }

    BasicTextField(
        state = textState,
        lineLimits = TextFieldLineLimits.SingleLine,
        decorator = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .padding(MenuDefaults.DropdownMenuItemContentPadding)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon",
                )
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (textState.text.isEmpty()) {
                        Text(text = "Search for Country")
                    }
                    innerTextField()
                }
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        textState.edit { this.replace(0, this.length, "") }
                    }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun FlagIcon(
    resource: DrawableResource,
    contentDescription: String = "Flag",
    modifier: Modifier = Modifier
) {
    Icon(
        painterResource(resource),
        contentDescription = contentDescription,
        tint = Color.Unspecified,
        modifier = modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(2.dp))
    )
}
