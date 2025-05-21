package com.arian.composestepper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs



@Composable
fun <T> ComposeStepper(
    points: List<T>,
    selectedIndex: Int,
    onStepSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    unselectedCircleRadius: Dp = 4.dp,
    selectedCircleRadius: Dp = 10.dp,
    barHeight: Dp = 4.dp,
    tooltipSize: Dp = 40.dp,
    tooltipPadding: Dp = 8.dp,
    arrowHeight: Dp = 6.dp,
    arrowWidth: Dp = 12.dp,
    unselectedLineColor: Color = Color(0xFFEFF2F5), // Light gray color for the base line
    progressBarColor: Color = Color(0xFFEFF2F5), // Light gray color for the progress line
    unselectedCircleColor: Color = Color(0xFF3A3A3D).copy(alpha = 0.4f), // Dark gray with alpha for unselected circles
    selectedCircleColor: Color = Color(0xFF3A3A3D), // Dark gray for selected circle
    tooltipBackgroundColor: Color = Color.Black, // Black for tooltip background
    tooltipTextColor: Color = Color.White, // White for tooltip text
    arrowColor: Color = Color.Black // Black for arrow
) {
    // State to manage the current drag position within the composable
    var dragPosition by remember { mutableStateOf(0f) }

    val currentSelectedIndex = selectedIndex // Use the prop as the source of truth

    BoxWithConstraints(modifier = modifier.height(90.dp)) {
        val widthPx = constraints.maxWidth.toFloat()
        val density = LocalDensity.current

        // Convert Dp values to Px for drawing on Canvas
        val unselectedCircleRadiusPx = with(density) { unselectedCircleRadius.toPx() }
        val selectedCircleRadiusPx = with(density) { selectedCircleRadius.toPx() }
        val tooltipSizePx = with(density) { tooltipSize.toPx() }
        val tooltipPaddingPx = with(density) { tooltipPadding.toPx() }
        val arrowHeightPx = with(density) { arrowHeight.toPx() }
        val arrowWidthPx = with(density) { arrowWidth.toPx() }

        val maxCircleDiameterPx = selectedCircleRadiusPx * 2

        // Spacing calculation adjusted to use remaining width
        val totalCircleWidths = maxCircleDiameterPx * points.size
        val totalSpacingWidth = widthPx - totalCircleWidths
        val spacingPx = if (points.size > 1) totalSpacingWidth / (points.size - 1) else 0f

        // Calculate the x-coordinate for the center of each step
        val stepCentersX = points.mapIndexed { index, _ ->
            selectedCircleRadiusPx + index * (maxCircleDiameterPx + spacingPx)
        }

        // Calculate the selected circle center X based on the current selectedIndex prop
        val currentSelectedCircleX =
            selectedCircleRadiusPx + currentSelectedIndex * (maxCircleDiameterPx + spacingPx)

        // Calculate tooltip X position (centered above the current selected circle)
        val tooltipX = currentSelectedCircleX - tooltipSizePx / 2
        // Calculate tooltip Y position (above the circle, with padding)
        val tooltipY = -(tooltipSizePx + tooltipPaddingPx)

        // Draggable state for horizontal dragging
        val draggableState = rememberDraggableState(
            onDelta = { delta ->
                // Update dragPosition based on the horizontal drag
                dragPosition = (dragPosition + delta).coerceIn(0f, widthPx)

                // Calculate the index based on the drag position
                // Find the closest step center to the current drag position
                val closestStepIndex = stepCentersX.indexOfFirst { centerX ->
                    dragPosition <= centerX + (maxCircleDiameterPx + spacingPx) / 2
                }.coerceAtLeast(0)

                // If drag position goes beyond the last step center, select the last index
                val calculatedIndex = if (dragPosition > stepCentersX.last()) {
                    points.lastIndex
                } else {
                    closestStepIndex
                }

                // Call the parent's onStepSelected if the index changes during drag
                if (calculatedIndex != currentSelectedIndex) {
                    onStepSelected(calculatedIndex)
                }
            }
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(selectedCircleRadius * 2) // Ensure canvas height accommodates the largest circle
                // Re-added the pointerInput for tap gestures
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        points.forEachIndexed { index, _ ->
                            val circleCenterX = stepCentersX[index]
                            // Increased tap target area slightly for easier interaction
                            val tapRadius = selectedCircleRadiusPx * 1.5f
                            if (abs(offset.x - circleCenterX) < tapRadius &&
                                abs(offset.y - size.height / 2) < tapRadius // Also check Y axis
                            ) {
                                onStepSelected(index)
                                return@detectTapGestures // Stop processing after a tap is detected
                            }
                        }
                    }
                }
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { offset ->
                        // Initialize dragPosition to the center of the currently selected step
                        dragPosition = stepCentersX[currentSelectedIndex]
                        // Optionally handle start of drag
                    },
                    onDragStopped = { velocity ->
                        // Optionally handle end of drag
                        // The onDelta lambda already updates the index as we drag
                    }
                )
        ) {
            val yCenter = size.height / 2

            // Draw base line from first to last circle centers (unselected portion)
            val lineStartX = stepCentersX.first()
            val lineEndX = stepCentersX.last()

            drawLine(
                color = unselectedLineColor, // Use custom unselected line color
                start = Offset(lineStartX, yCenter),
                end = Offset(lineEndX, yCenter),
                strokeWidth = barHeight.toPx(),
                cap = StrokeCap.Round
            )

            // Draw progress line up to current selected circle center
            drawLine(
                color = progressBarColor, // Use custom progress bar color
                start = Offset(lineStartX, yCenter),
                end = Offset(currentSelectedCircleX, yCenter),
                strokeWidth = barHeight.toPx(),
                cap = StrokeCap.Round
            )

            // Draw circles, bigger if selected (based on the currentSelectedIndex prop)
            stepCentersX.forEachIndexed { index, circleCenterX ->
                val isSelected = index == currentSelectedIndex
                val radius = if (isSelected) selectedCircleRadiusPx else unselectedCircleRadiusPx
                val color = if (isSelected) selectedCircleColor else unselectedCircleColor

                drawCircle(
                    color = color,
                    radius = radius,
                    center = Offset(circleCenterX, yCenter)
                )
            }
        }

        // Tooltip box ABOVE the current selected circle with vertical offset
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = tooltipX.toInt(),
                        y = tooltipY.toInt()
                    )
                }
                .size(tooltipSize)
                .background(tooltipBackgroundColor, shape = RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = points[currentSelectedIndex].toString(), // Display the value as a String
                color = tooltipTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        // Arrow Canvas positioned below the tooltip and centered on the current selected circle
        Canvas(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = (currentSelectedCircleX - arrowWidthPx / 2).toInt(), // Center the arrow base on the current selected circle
                        y = (tooltipY + tooltipSizePx).toInt() // Position directly below the tooltip
                    )
                }
                .size(width = arrowWidth, height = arrowHeight)
        ) {
            val trianglePath = Path().apply {
                moveTo(0f, 0f) // Top-left corner of the triangle base
                lineTo(arrowWidthPx, 0f) // Top-right corner of the triangle base
                lineTo(arrowWidthPx / 2, arrowHeightPx) // Bottom point of the triangle
                close()
            }
            drawPath(
                path = trianglePath,
                color = arrowColor // Match the tooltip background color
            )
        }
    }
}

// The FontSizeSetting enum is no longer directly used by ComposeStepper,
// but you can keep it if you use it elsewhere in your project.
// If not, you can remove it.
/**
 * Enum class representing different font size settings.
 * Each setting has a [title] for display and a [position] for ordering.
 */
enum class FontSizeSetting(val title: String, val position: Int) {
    SMALL("S", 0),
    MEDIUM("M", 1),
    LARGE("L", 2),
    XLARGE("XL", 3),
    XXLARGE("XXL", 4)
}