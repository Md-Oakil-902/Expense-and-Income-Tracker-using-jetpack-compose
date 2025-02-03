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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.oakil.incomeandexpensetracker.ui.theme.Zinc
import com.oakil.incomeandexpensetracker.widget.ExpenseTextView


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBarImage) = createRefs()
            Image(painter = painterResource(R.drawable.ic_topbar), contentDescription = null,
                modifier = Modifier.constrainAs(topBarImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 64.dp, start = 16.dp, end = 16.dp
                )
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                Column() {
                    ExpenseTextView(text = "Good Afternoon", fontSize = 16.sp, color = Color.White)
                    ExpenseTextView(text = "Oakil", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            CardItem(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                })

        }

    }

}

@Composable
fun CardItem(modifier: Modifier) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 140.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Zinc)
            .padding(16.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Column {
                ExpenseTextView("Total Balance", fontSize = 16.sp, color = Color.White)
                ExpenseTextView("$ 5000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Image(
                painter = painterResource(R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            )

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterStart),
                title = "Income",
                "8000",
                image = R.drawable.ic_income
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                "8000",
                image = R.drawable.ic_expense
            )

        }


    }

}

@Composable
fun TransactionList(modifier: Modifier) {
    Column(modifier = modifier.padding(top = 360.dp, start = 16.dp, end = 16.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ExpenseTextView(text = "Recent Transaction", fontSize = 20.sp)
            ExpenseTextView(
                text = "See all", fontSize = 16.sp, color = Color.Black, modifier = Modifier.align(
                    Alignment.CenterEnd
                )
            )
        }
        TransactionItem(
            title = "Netflix", amount = "- 20$", icon = R.drawable.ic_netflix,
            date = "Today",
            color = Color.Red,

            )
        TransactionItem(
            title = "Netflix", amount = "- 20$", icon = R.drawable.ic_netflix,
            date = "Today",
            color = Color.Red,

            )
        TransactionItem(
            title = "Paypall", amount = "+ 266$", icon = R.drawable.ic_paypal,
            date = "Today",
            color = Color.Green,

            )
        TransactionItem(
            title = "Starbucks", amount = "- 20$", icon = R.drawable.ic_starbucks,
            date = "Today",
            color = Color.Green,

            )

    }

}

@Composable
fun TransactionItem(  title: String, amount: String, icon: Int, date: String, color: Color) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(50.dp))
            Column(modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)) {
                ExpenseTextView(text = title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                ExpenseTextView(text = date, fontSize = 12.sp)
            }
        }
        ExpenseTextView(
            text = amount,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color, fontWeight = FontWeight.Medium
        )
    }

}


@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, image: Int) {
    Column(modifier = modifier) {
        Row {
            Image(painter = painterResource(image), contentDescription = null)
            ExpenseTextView(text = title, fontSize = 18.sp, color = Color.White)

        }
        ExpenseTextView(text = amount, fontSize = 18.sp, color = Color.White)
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewHome() {
    HomeScreen()

}