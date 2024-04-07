package pe.edu.idat.appborabora.viewmodel

/*
class UpdateUserViewModel(private val context: Context) : ViewModel() {

    private val boraBoraClient = BoraBoraClient()

    val apiResponse: MutableLiveData<ApiResponse> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun updateUser(identityDoc: Int, createUserRequest: CreateUserRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = boraBoraClient.getInstanceWithAuth(context).updateUser(identityDoc, createUserRequest).execute()
                if (response.isSuccessful) {
                    apiResponse.postValue(response.body())
                } else {
                    error.postValue(response.errorBody()?.string())
                }
            } catch (e: Exception) {
                error.postValue(e.message)
            }
        }
    }
}
*/
