package com.arkul.eazysnap.presentation.screen.auth.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuBoxScope
import androidx.compose.material.Icon
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.arkul.eazysnap.domain.model.Country
import eazysnap.composeapp.generated.resources.Res
import eazysnap.composeapp.generated.resources.country_dial_codes
import eazysnap.composeapp.generated.resources.ic_ua
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
    var value by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var barYPosition by remember { mutableStateOf(0) }

    ExposedDropdownMenuBox(
        expanded = false,
        onExpandedChange = {  },
        modifier = modifier.onPlaced {
            barYPosition = it.size.height
        }
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            singleLine = true,
            leadingIcon = {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isExpanded = !isExpanded }
                ) {
                    FlagIcon(Res.drawable.ic_ua)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Trailing icon for exposed dropdown menu",
                        tint = Color.White,
                        modifier = Modifier.rotate(if (isExpanded) 180f else 0f)
                    )
                }
            },
            label = { Text("Phone Number") },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
        )
        CountryDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            onItemClick = {

            },
            countries = countries,
            modifier = Modifier.exposedDropdownSize()
        )
    }
}

@Composable
fun CountryDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (Country) -> Unit,
    countries: Array<Country>,
    modifier: Modifier = Modifier,
) {
    if(expanded) {
        Column(
            modifier = modifier
        ) {
            SearchTextField()
            Divider()
            LazyColumn {
                items(
                    count = countries.size,
                    key = { countries[it].name }
                ) { index ->
                    val country = countries[index]
                    DropdownMenuItem(
                        onClick = {
                            onItemClick(country)
                        }
                    ) {
                        FlagIcon(country.flag, country.name)
                        Text(text = stringResource(Res.string.country_dial_codes, country.name, country.code))
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    onValueChange: (CharSequence) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val textState = rememberTextFieldState()

    textState.edit { onValueChange.invoke(this.originalText) }

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
                    contentDescription = null,
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
            .background(Color.White),
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
        modifier = modifier.clip(RoundedCornerShape(2.dp))
    )
}
