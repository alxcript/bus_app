package com.example.busapp.repositories

import com.example.busapp.models.Bus
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class BusRepository {

    private var busList: CollectionReference = getCollectionReference()

    private fun getCollectionReference(): CollectionReference {
        return Firebase.firestore.collection("buses")
    }

    fun getBusList(): Flow<Result<List<Bus>>> = flow {
        try {
            emit(Result.Loading<List<Bus>>())

            val bookList = busList.get().await().map { document ->
                document.toObject(Bus::class.java)
            }

            emit(Result.Success<List<Bus>>(data = bookList))

        } catch (e: Exception) {
            emit(Result.Error<List<Bus>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}