class MainController {
    ...

    fun refreshData() {
        repoModel.refreshData(object : OnDataReadyCallback {
            override fun onDataReady(data: String) {
                mainActivity.onDataReady(data)
            }
        })
    }
}

class MainActivity : AppCompatActivity() {

    var mainController = MainController()
    ...
    
    fun refresh() {
        progressBar.visibility = View.VISIBLE
        mainController.refreshData()
    }
    
    fun onDataReady(data: String) {
        progressBar.visibility = View.GONE
        mainController.text = data
    }
}
