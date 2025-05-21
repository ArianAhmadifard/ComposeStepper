# Compose Stepper

A customizable stepper component built with Jetpack Compose for selecting and displaying steps in a horizontal progress bar style.

## Features

- Supports generic step types (Int, String, Float, etc.)  
- Customizable appearance: circle sizes, colors, tooltip size, and more  
- Displays a progress bar connecting steps  
- Handles user interaction and step selection  
- Easy to integrate in any Jetpack Compose project


## 📸 Screenshots 

<img src="https://github.com/ArianAhmadifard/ComposeStepper/blob/master/2001.jpg" alt="Screenshot 1" width="300"/>

## 🎨 Customization Parameters

| Parameter               | Type               | Default                       | Description                                                      |
|-------------------------|--------------------|-------------------------------|------------------------------------------------------------------|
| `points`                | `List<T>`          | **Required**                  | List of steps/items to display                                   |
| `selectedIndex`         | `Int`              | **Required**                  | Currently selected step index                                    |
| `onStepSelected`        | `(Int) -> Unit`    | **Required**                  | Callback when a step is selected                                 |
| `modifier`              | `Modifier`         | `Modifier = Modifier`         | Compose UI modifier                                              |
| `unselectedCircleRadius`| `Dp`               | `4.dp`                       | Radius of unselected step circles                                |
| `selectedCircleRadius`  | `Dp`               | `10.dp`                      | Radius of selected step circle                                   |
| `barHeight`             | `Dp`               | `4.dp`                       | Height of the progress bar line                                  |
| `tooltipSize`           | `Dp`               | `40.dp`                      | Size of the tooltip showing the step value                      |
| `tooltipPadding`        | `Dp`               | `8.dp`                       | Padding inside the tooltip                                       |
| `arrowHeight`           | `Dp`               | `6.dp`                       | Height of the tooltip arrow                                      |
| `arrowWidth`            | `Dp`               | `12.dp`                      | Width of the tooltip arrow                                       |
| `unselectedLineColor`   | `Color`            | `Color(0xFFEFF2F5)`          | Color of the unselected (base) progress line                     |
| `progressBarColor`      | `Color`            | `Color(0xFFEFF2F5)`          | Color of the progress bar line                                   |
| `unselectedCircleColor` | `Color`            | `Color(0xFF3A3A3D).copy(alpha = 0.4f)` | Color of unselected circles                          |
| `selectedCircleColor`   | `Color`            | `Color(0xFF3A3A3D)`          | Color of selected circle                                         |
| `tooltipBackgroundColor`| `Color`            | `Color.Black`                | Background color of the tooltip                                  |
| `tooltipTextColor`      | `Color`            | `Color.White`                | Text color inside the tooltip                                    |
| `arrowColor`            | `Color`            | `Color.Black`                | Color of the tooltip arrow                                       |



License

This project is licensed under the Apache License 2.0.
You are free to use, modify, and distribute this software under the terms of the license.
See the LICENSE file for details.




      
