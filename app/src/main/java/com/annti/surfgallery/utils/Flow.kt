package com.annti.surfgallery.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun SearchView.onQueryTextChange(): Flow<String> {
    return callbackFlow {
        val textChangedListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySendBlocking(newText.orEmpty())
                return true
            }

        }
        this@onQueryTextChange.setOnQueryTextListener(textChangedListener)
        awaitClose {
            this@onQueryTextChange.setOnQueryTextListener(null)
        }
    }

}