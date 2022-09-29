package com.example.scopetechassignment.presentation.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.scopetechassignment.data.models.db.UserEntity
import com.example.scopetechassignment.data.models.network.Data
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
            CollectDatabaseUserDetails()
        }
    }

    @Composable
    private fun CollectDatabaseUserDetails() {
        val userResponseState = remember { mutableStateOf<List<UserEntity>>(emptyList()) }
        UserListFromDatabase(userList = userResponseState.value, context = this)
        collectLatestLifecycleFlow(viewModel.userDetailsFromDbState) {
            when (it.status) {
                Status.LOADING -> {
                    //Do nothing
                }
                Status.ERROR -> {
                    Toast.makeText(this@MainActivity, it.errorMessage, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    it.data?.let { data ->
                        userResponseState.value = data
                    }
                }
            }

        }
    }

    @Composable
    private fun CollectUserDetails() {
        val userResponseState = remember { mutableStateOf<List<Data>>(emptyList()) }
        UserList(userList = userResponseState.value, context = this)
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
    }
}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
    user: Data,
    context: Context
) {
    MaterialTheme {
        user.owner?.let { owner ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clickable(
                        onClick = {
                            user.userid?.let { userId ->
                                val intent = Intent(context, MapActivity::class.java).apply {
                                    val bundle = Bundle()
                                    bundle.putInt("userId", userId)
                                    putExtras(bundle)
                                }
                                context.startActivity(intent)
                            }
                        },
                        indication = rememberRipple(bounded = true),
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ),
            ) {

                Image(
                    painter = rememberAsyncImagePainter(owner.photo),
                    contentScale = ContentScale.FillWidth,
                    modifier = modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(10.dp),
                    contentDescription = "Profile Image"
                )
                Text(
                    text = "${owner.name} ${owner.surname}",
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                        .padding(top = 10.dp),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }

}

@Composable
fun UserRow(
    modifier: Modifier = Modifier,
    user: UserEntity,
    context: Context
) {
    MaterialTheme {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable(
                    onClick = {
                        user.userId?.let { userId ->
                            val intent = Intent(context, MapActivity::class.java).apply {
                                val bundle = Bundle()
                                bundle.putInt("userId", userId)
                                putExtras(bundle)
                            }
                            context.startActivity(intent)
                        }
                    },
                    indication = rememberRipple(bounded = true),
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ),
        ) {

            Image(
                painter = rememberAsyncImagePainter(user.photoLink),
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .width(80.dp)
                    .height(80.dp)
                    .padding(10.dp),
                contentDescription = "Profile Image"
            )
            Text(
                text = "${user.firstName} ${user.surname}",
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp),
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun UserList(modifier: Modifier = Modifier, userList: List<Data>, context: Context) {
    if (userList.isNotEmpty()) {
        MaterialTheme {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(userList) { user ->
                    UserRow(modifier, user, context)
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun UserListFromDatabase(
    modifier: Modifier = Modifier,
    userList: List<UserEntity>,
    context: Context
) {
    if (userList.isNotEmpty()) {
        MaterialTheme {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(userList) { user ->
                    UserRow(modifier, user, context)
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}