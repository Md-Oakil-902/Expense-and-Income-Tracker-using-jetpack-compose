package com.oakil.incomeandexpensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.oakil.incomeandexpensetracker.ui.theme.Zinc

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 32.dp, start = 16.dp, end = 16.dp
                )
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Column() {
                    Text(text = "Good Afternoon", fontSize = 16.sp, color = Color.White)
                    Text(text = "Oakil", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            // Remove wrapContentHeight and use proper constraints
            CardItem(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp) // Add margin to avoid overlap
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom, margin = 24.dp) // Proper margin
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            // Ensure enough space for the list below the CardItem
            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })
        }
    }
}


@Composable
fun TransactionItem(title: String, amount: String, icon:Int, date : String) {
    
}

@Composable
fun CardItem(modifier: Modifier) {
    Column(
        modifier = Modifier
            //.padding(top = 80.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Zinc)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column {
                Text(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                Text(
                    text = "$ 5000",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                tile = "Income",
                amount = "$ 99,999",
                image = R.drawable.ic_income
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                tile = "Expense",
                amount = "$ 5,099",
                image = R.drawable.ic_expense
            )

        }

    }

}



@Composable
fun TransactionList(modifier: Modifier) {
    Column(modifier = modifier
        .padding(horizontal = 16.dp)
        .padding(top = 280.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Recent Transactions", fontSize = 20.sp)
            Text(
                text = "See All",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}


@Composable
fun CardRowItem(modifier: Modifier, tile: String, amount: String, image: Int) {

    Column(modifier = modifier) {
        Row {
            Image(painter = painterResource(id = image), contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = tile, fontSize = 18.sp, color = Color.White)
        }

        Text(text = amount, fontSize = 20.sp, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    HomeScreen()
}