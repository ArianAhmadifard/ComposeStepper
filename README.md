# Compose Stepper

A customizable stepper component built with Jetpack Compose for selecting and displaying steps in a horizontal progress bar style.

## Features

- Supports generic step types (Int, String, Float, etc.)  
- Customizable appearance: circle sizes, colors, tooltip size, and more  
- Displays a progress bar connecting steps  
- Handles user interaction and step selection  
- Easy to integrate in any Jetpack Compose project

## Usage Example

```kotlin
@Composable
fun SampleStepper() {
    val steps = listOf("S", "M", "L", "XL")
    var selectedStep by remember { mutableStateOf(0) }

    ComposeStepper(
        points = steps,
        selectedIndex = selectedStep,
        onStepSelected = { selectedStep = it }
    )
}

## License

This project is licensed under the Apache License 2.0.
You are free to use, modify, and distribute this software under the terms of the license.
See the LICENSE file for details.
      
