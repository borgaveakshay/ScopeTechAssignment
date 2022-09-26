package com.example.scopetechassignment.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.scopetechassignment.data.models.Data
import com.example.scopetechassignment.data.models.Owner
import com.example.scopetechassignment.domain.Status
import com.example.scopetechassignment.presentation.util.collectLatestLifecycleFlow
import com.example.scopetechassignment.presentation.viewmodels.GetUserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: GetUserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userResponseState = remember { mutableStateOf<List<Data>>(emptyList()) }
            collectLatestLifecycleFlow(viewModel.userDetailState) {
                when (it.status) {
                    Status.LOADING -> {
                        //Do nothing
                    }
                    Status.ERROR -> {
                        Toast.makeText(this@MainActivity, it.errorMessage, Toast.LENGTH_LONG).show()
                    }
                    Status.SUCCESS -> {
                        it.data?.let { data ->
                            userResponseState.value = data.userData
                        }
                    }
                }
            }
            UserList(userList = userResponseState.value)
        }
    }
}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
    user: Owner
) {
    Row {
        Image(
            painter = rememberAsyncImagePainter(user.photo),
            modifier = modifier
                .width(40.dp)
                .height(40.dp)
                .align(Alignment.CenterVertically)
                .padding(10.dp),
            contentDescription = "Profile Image"
        )
        Text(
            text = "${user.name} ${user.surname}",
            modifier = modifier.wrapContentSize()
        )

    }

}

@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<Data>) {
    if (userList.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(userList) { user ->
                UserRow(modifier, user.owner)
            }
        }
    }
}