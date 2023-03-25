import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import presentation.BaseOutlinedTextField

@Composable
@Preview
fun App() {

    var tradeAmount by remember { mutableStateOf(0.00f) }
    var riskAmount by remember { mutableStateOf(0.00f) }
    var estimatedGain by remember { mutableStateOf(0.00f) }

    var entryPrice by remember { mutableStateOf(0.00f) }
    var stopLossPrice by remember { mutableStateOf(0.00f) }
    var targetPrice by remember { mutableStateOf(0.00f) }

    var rNumber by remember { mutableStateOf(0.00f) }
    var numberOfShares by remember { mutableStateOf(0.00f) }

    var showResult by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BaseOutlinedTextField("Risk: amount") { amount -> riskAmount = amount }
                BaseOutlinedTextField("Entry: price") { amount -> entryPrice = amount }
                BaseOutlinedTextField("Stop Loss: price") { amount -> stopLossPrice = amount }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 8.dp),
            ) {
                Button(onClick = {
                    rNumber = entryPrice - stopLossPrice
                    numberOfShares = riskAmount.div(rNumber)
                    tradeAmount =  entryPrice * numberOfShares
                    targetPrice = entryPrice + rNumber
                    showResult = true
                }) {
                    Text("Calculate")
                }

                Button(onClick = {
                    showResult = false
                }, modifier = Modifier.padding(start = 32.dp)) {
                    Text("Clear")
                }
            }

            AnimatedVisibility(visible = showResult, enter = fadeIn(), exit = fadeOut()) {
                Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        "Risk $riskAmount ÷ R $rNumber × Entry Price $entryPrice = Trade Amount $tradeAmount",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        "1R Target Price: ${targetPrice} → Gain : ${(numberOfShares*(entryPrice + rNumber) - tradeAmount).div(tradeAmount)*100}%",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        "2R Target Price: ${targetPrice + rNumber} → Gain : ${(numberOfShares*(entryPrice + 2*rNumber) - tradeAmount).div(tradeAmount)*100}%",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        "2.4R Target Price: ${targetPrice + 1.4*rNumber} → Gain : ${(numberOfShares*(entryPrice + 2.4*rNumber) - tradeAmount).div(tradeAmount)*100}%",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

            }

        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
