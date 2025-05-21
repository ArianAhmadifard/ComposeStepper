package com.arian.composestepper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arian.composestepper.ui.theme.ComposeStepperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeStepperTheme {
                ComposeSteppers()
            }
        }
    }
}

@Composable
fun ComposeSteppers() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // --- Example 1: Using Int points with default styling ---
            var selectedIntIndex by remember { mutableStateOf(0) }
            val intPoints = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            Spacer(Modifier.height(32.dp))
            Text(text = "Int Stepper (Default Style)", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            ComposeStepper(
                points = intPoints,
                selectedIndex = selectedIntIndex,
                onStepSelected = { selectedIntIndex = it },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Selected Value: ${intPoints[selectedIntIndex]}", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Divider(Modifier.background(Color.Gray))
            Spacer(modifier = Modifier.height(16.dp))
            // --- Example 2: Using String points with custom styling ---
            var selectedStringIndex by remember { mutableStateOf(1) }
            val stringPoints = listOf("S", "M", "L", "XL", "XXL")

            Text(text = "String Stepper (Custom Style)", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            ComposeStepper(
                points = stringPoints,
                selectedIndex = selectedStringIndex,
                onStepSelected = { selectedStringIndex = it },
                modifier = Modifier.fillMaxWidth(),
                unselectedCircleRadius = 6.dp,
                selectedCircleRadius = 12.dp,
                barHeight = 6.dp,
                tooltipSize = 60.dp,
                tooltipPadding = 12.dp,
                arrowHeight = 8.dp,
                arrowWidth = 16.dp,
                unselectedLineColor = Color(0xFFE0E0E0), // Lighter gray
                progressBarColor = Color(0xFF4CAF50), // Green progress bar
                unselectedCircleColor = Color(0xFF9E9E9E), // Gray unselected circles
                selectedCircleColor = Color(0xFF2196F3), // Blue selected circle
                tooltipBackgroundColor = Color(0xFF3F51B5), // Indigo tooltip background
                tooltipTextColor = Color.White,
                arrowColor = Color(0xFF3F51B5) // Indigo arrow
            )
            Text(text = "Selected Value: ${stringPoints[selectedStringIndex]}", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Divider(Modifier.background(Color.Gray))
            Spacer(modifier = Modifier.height(16.dp))

            // --- Example 3: Using Float points with another custom style ---
            var selectedFloatIndex by remember { mutableStateOf(2) }
            val floatPoints = listOf(0.5f, 1.0f, 1.5f, 2.0f, 2.5f, 3.0f)

            Text(text = "Float Stepper (Another Custom Style)", fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))
            ComposeStepper(
                points = floatPoints,
                selectedIndex = selectedFloatIndex,
                onStepSelected = { selectedFloatIndex = it },
                modifier = Modifier.fillMaxWidth(),
                unselectedCircleRadius = 3.dp,
                selectedCircleRadius = 8.dp,
                barHeight = 3.dp,
                tooltipSize = 50.dp,
                tooltipPadding = 10.dp,
                arrowHeight = 5.dp,
                arrowWidth = 10.dp,
                unselectedLineColor = Color(0xFFB0BEC5), // Blue Gray
                progressBarColor = Color(0xFFFF5722), // Deep Orange
                unselectedCircleColor = Color(0xFF78909C), // Light Blue Gray
                selectedCircleColor = Color(0xFFFF9800), // Orange
                tooltipBackgroundColor = Color(0xFF673AB7), // Deep Purple
                tooltipTextColor = Color.White,
                arrowColor = Color(0xFF673AB7) // Deep Purple
            )
            Text(text = "Selected Value: ${floatPoints[selectedFloatIndex]}", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Divider(Modifier.background(Color.Gray))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

