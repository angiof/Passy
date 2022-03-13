package passy.prog.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import passy.prog.db.EntityPassword

class BasePerformances {
    val DB_KEY: String = "db_Key"

    companion object {

    }


    open fun DBInsertOptimizer(
        viewModelPassword: ViewModelPassword,
        user: String,
        password: String,
        colors: String?,
        ic_signature: String?
    ) = runBlocking(Dispatchers.IO) {
        newSingleThreadContext(DB_KEY).use { dbCall ->
            launch(dbCall) {

                viewModelPassword.insertPasswordViewModel(
                    EntityPassword(
                        0,
                        user,
                        password,
                        colors,
                        ic_signature
                    )
                )

            }

        }

    }
}