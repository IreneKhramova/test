class MainViewModel {
    var repoModel: RepoModel = RepoModel()
    val text = ObservableField<String>()
    val isLoading = ObservableField<Boolean>()

    fun refresh(){
        isLoading.set(true)
        repoModel.refreshData(object : OnDataReadyCallback {
            override fun onDataReady(data: String) {
                isLoading.set(false)
                text.set(data)
            }
        })
    }
}

class RepoModel {

    fun refreshData() : String {
        return "Some new data"
    }
}

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        binding.executePendingBindings()

    }
}
