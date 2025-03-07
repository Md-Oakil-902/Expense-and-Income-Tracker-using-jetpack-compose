@file:OptIn(ExperimentalMaterial3Api::class)

package com.oakil.incomeandexpensetracker.feature.add_expense

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oakil.incomeandexpensetracker.R
import com.oakil.incomeandexpensetracker.Utils
import com.oakil.incomeandexpensetracker.data.Model.ExpenseEntity
import com.oakil.incomeandexpensetracker.viewModel.AddExpenseViewModel
import com.oakil.incomeandexpensetracker.viewModel.AddExpenseViewModelFactory
import com.oakil.incomeandexpensetracker.widget.ExpenseTextView
import kotlinx.coroutines.launch


@Composable
fun AddExpense(navController: NavController) {

    val viewModel =
        AddExpenseViewModelFactory(LocalContext.current).create(AddExpenseViewModel::class.java)

    val coroutineScope = rememberCoroutineScope()

    Surface() {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (nameRow, list, card, topBarImage) = createRefs()
            Image(painter = painterResource(R.drawable.ic_income),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBarImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, start = 16.dp, end = 18.dp)
                    .constrainAs(nameRow) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            {
                ExpenseTextView(
                    text = "Add Expense",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
                Image(
                    painter = painterResource(R.drawable.dots_menu),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

            }

            DataForm(modifier = Modifier.constrainAs(card) {
                top.linkTo(nameRow.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }, onAddExpenseClick = {
                coroutineScope.launch {
                    if (viewModel.addExpense(it)) {
                        navController.popBackStack()

                    }

                }

            })

        }
    }
}


@Composable
fun DataForm(modifier: Modifier, onAddExpenseClick: (model: ExpenseEntity) -> Unit) {
    val name = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val date = remember { mutableStateOf(0L) }
    val dateDialogVisibility = remember { mutableStateOf(false) }
    val category = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 170.dp)
            .fillMaxWidth()
            .shadow(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        ExpenseTextView(text = "Name", fontSize = 14.sp)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.fillMaxWidth()
        )

        ExpenseTextView(text = "amount", fontSize = 14.sp)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = amount.value,
            onValueChange = { amount.value = it },
            modifier = Modifier.fillMaxWidth()
        )


        //date picker dialog
        ExpenseTextView(text = "Date", fontSize = 14.sp)
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            value = if (date.value == 0L) "" else Utils.formatDateToHumanReadableForm(date.value),
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dateDialogVisibility.value = true },
            enabled = false, colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = Color.Black,
                disabledTextColor = Color.Black
            )
        )

        //drop down category
        ExpenseTextView(text = "Category", fontSize = 14.sp)
        Spacer(modifier = Modifier.size(8.dp))
        ExpenseDropDown(
            listOf("Netflix", "Paypall", "Starbucks", "Salary", "Upwork"),
            onItemSelected = { category.value = it })

        ExpenseTextView(text = "Type", fontSize = 14.sp)
        Spacer(modifier = Modifier.size(8.dp))
        ExpenseDropDown(
            listOf("Income", "Expense"),
            onItemSelected = { type.value = it })


        //type

        Button(
            onClick = {
                val model = ExpenseEntity(
                    null,
                    name.value,
                    amount.value.toDoubleOrNull() ?: 0.0,
                    Utils.formatDateToHumanReadableForm(date.value),
                    category.value,
                    type.value
                )

                onAddExpenseClick(model)
            }, modifier = Modifier
                .clip(RoundedCornerShape(2.dp))
                .fillMaxWidth()
        ) {
            ExpenseTextView(text = "Add Expense", fontSize = 14.sp, color = Color.White)
        }
    }
    if (dateDialogVisibility.value) {
        ExpenseDatePickerDialog(onDateSelected = {
            date.value = it
            dateDialogVisibility.value = false
        }, onDismiss = {
            dateDialogVisibility.value = false
        })

    }
}


@Composable
fun ExpenseDropDown(listOfItem: List<String>, onItemSelected: (item: String) -> Unit) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val selectedItem = remember {
        mutableStateOf<String>(listOfItem[0])
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }) {

        TextField(
            value = selectedItem.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            })

        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {}) {
            listOfItem.forEach {
                DropdownMenuItem(text = { ExpenseTextView(text = it) }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false
                })
            }
        }
    }

}


@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date: Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                ExpenseTextView(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDateSelected(selectedDate) }) {
                ExpenseTextView(text = "Cancel")
            }

        }

    ) {
        DatePicker(state = datePickerState)
    }

}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    AddExpense(navController = rememberNavController())

}