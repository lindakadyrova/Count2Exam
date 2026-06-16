import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadyrova.count2exam.ui.components.AppHeader

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            AppHeader()

            IconButton(
                onClick = { },
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menü öffnen"
                )
            }
        }
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "3 Prüfungen offen",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "nächste in 7t 8h 25min",
            fontSize = 25.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(90.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp)
        ){
            Text(
                text = "Prüfung hinzufügen"
            )
        }
        Spacer(modifier = Modifier.height(17.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "Prüfung bearbeiten"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}