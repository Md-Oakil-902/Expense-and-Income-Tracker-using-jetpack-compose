package com.oakil.incomeandexpensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.ui.theme.Zinc
import com.oakil.incomeandexpensetracker.viewModel.HomeViewModel
import com.oakil.incomeandexpensetracker.viewModel.HomeViewModelFactory
import com.oakil.incomeandexpensetracker.widget.ExpenseTextView


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: HomeViewModel = HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
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

            val state = viewModel.expense.collectAsState(initial = emptyList())
            val expenses = viewModel.getTotalExpense(state.value)
            val income = viewModel.getTotalIncome(state.value)
            val balance = viewModel.getBalance(state.value)

            CardItem(
                modifier = Modifier
                    .constrainAs(card) {
                        top.linkTo(nameRow.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, balance, income, expenses)


            TransactionList(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(list) {
                    top.linkTo(card.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }, list = state.value, viewModel)

        }

    }

}



@Composable
fun CardItem(modifier: Modifier, balance: String, income: String, expenses: String) {
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
                ExpenseTextView(balance, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                income,
                image = R.drawable.ic_income
            )
            CardRowItem(
                modifier = Modifier.align(Alignment.CenterEnd),
                title = "Expense",
                expenses,
                image = R.drawable.ic_expense
            )

        }


    }

}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: HomeViewModel) {
    LazyColumn(modifier = modifier.padding(top = 360.dp, start = 16.dp, end = 16.dp)) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                ExpenseTextView(text = "Recent Transaction", fontSize = 20.sp)
                ExpenseTextView(
                    text = "See all", fontSize = 16.sp, color = Color.Black, modifier = Modifier.align(
                        Alignment.CenterEnd
                    )
                )
            }
        }
        items(list) { item ->
            TransactionItem(
                title = item.title!!,
                amount = item.amount.toString(),
                icon = viewModel.getItemIcon(item),
                date = item.date.toString(),
                color = if (item.type == "Income") Color.Green else Color.Red
            )
        }
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


//
//package com.oakil.incomeandexpensetracker
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.constraintlayout.compose.ConstraintLayout
//import androidx.constraintlayout.compose.Dimension
//import com.oakil.incomeandexpensetracker.ui.theme.Zinc
//import com.oakil.incomeandexpensetracker.widget.ExpenseTextView
//
//@Composable
//fun HomeScreen(modifier: Modifier = Modifier) {
//    Surface(modifier = Modifier.fillMaxSize()) {
//        ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//            val (topBarImage, nameRow, card, list) = createRefs()
//
//            Image(
//                painter = painterResource(R.drawable.ic_topbar),
//                contentDescription = null,
//                modifier = Modifier.constrainAs(topBarImage) {
//                    top.linkTo(parent.top)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }.fillMaxWidth(0.8f)
//            )
//
//            Column(
//                modifier = Modifier.constrainAs(nameRow) {
//                    top.linkTo(topBarImage.bottom, margin = 16.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//            ) {
//                ExpenseTextView(text = "Good Afternoon", fontSize = 16.sp, color = Color.White)
//                ExpenseTextView(text = "Oakil", fontSize = 22.sp, fontWeight = FontWeight.Bold)
//            }
//
//            CardItem(
//                modifier = Modifier.constrainAs(card) {
//                    top.linkTo(nameRow.bottom, margin = 16.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
//            )
//
//            TransactionList(
//                modifier = Modifier.constrainAs(list) {
//                    top.linkTo(card.bottom, margin = 16.dp)
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                    bottom.linkTo(parent.bottom)
//                    height = Dimension.fillToConstraints
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun CardItem(modifier: Modifier) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(200.dp)
//            .clip(RoundedCornerShape(16.dp))
//            .background(Zinc)
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Column {
//                ExpenseTextView("Total Balance", fontSize = 16.sp, color = Color.White)
//                ExpenseTextView("$ 5000", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
//            }
//            Image(
//                painter = painterResource(R.drawable.dots_menu),
//                contentDescription = null,
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            CardRowItem(title = "Income", amount = "8000", image = R.drawable.ic_income)
//            CardRowItem(title = "Expense", amount = "8000", image = R.drawable.ic_expense)
//        }
//    }
//}
//
//
//@Composable
//fun TransactionList(modifier: Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            ExpenseTextView(text = "Recent Transactions", fontSize = 20.sp)
//            ExpenseTextView(text = "See all", fontSize = 16.sp, color = Color.Black)
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        TransactionItem(title = "Netflix", amount = "- 20$", icon = R.drawable.ic_netflix, date = "Today", color = Color.Red)
//        TransactionItem(title = "PayPal", amount = "+ 266$", icon = R.drawable.ic_paypal, date = "Today", color = Color.Green)
//        TransactionItem(title = "Starbucks", amount = "- 20$", icon = R.drawable.ic_starbucks, date = "Today", color = Color.Green)
//    }
//}
//
//@Composable
//fun TransactionItem(title: String, amount: String, icon: Int, date: String, color: Color) {
//    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(50.dp))
//        Column(modifier = Modifier.padding(start = 10.dp)) {
//            ExpenseTextView(text = title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//            ExpenseTextView(text = date, fontSize = 12.sp)
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        ExpenseTextView(text = amount, fontSize = 18.sp, color = color, fontWeight = FontWeight.Medium)
//    }
//}
//
//@Composable
//fun CardRowItem(title: String, amount: String, image: Int) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Image(painter = painterResource(image), contentDescription = null, modifier = Modifier.size(24.dp))
//            Spacer(modifier = Modifier.width(8.dp))
//            ExpenseTextView(text = title, fontSize = 18.sp, color = Color.White)
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        ExpenseTextView(text = amount, fontSize = 18.sp, color = Color.White)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewHome() {
//    HomeScreen()
//
//}
