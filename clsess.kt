class CompanyViewModel @Inject constructor(
    private val companyProvider: CompanyProvider
): ViewModel() {

    companion object {
        const val VIEW_PROGRESS = 0
        const val VIEW_DATA = 1
        const val VIEW_ERROR = 2
    }

    var company = MutableLiveData<CompanyRequisites>()
    val displayedChild = MutableLiveData<Int>()

    private var companyDisposable: Disposable? = null

    fun onViewCreated() {
        loadCompanyRequisites()
    }

    fun onRetryButtonClick() {
        loadCompanyRequisites()
    }

    private fun loadCompanyRequisites() {
        displayedChild.value = VIEW_PROGRESS

        companyDisposable?.dispose()
        companyDisposable = companyProvider.getCompanyRequisites()
                .compose(asyncSingle())
                .subscribe({
                    displayedChild.value = VIEW_DATA
                    company.value = it
                },
                        {
                            Timber.e(it)
                            displayedChild.value = VIEW_ERROR
                        })
    }
   //...
}






class CompanyActivity : BaseActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, CompanyActivity::class.java)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
            .get(CompanyViewModel::class.java)
    }

    private var companyRequisites: CompanyRequisites? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        initToolbar()
        viewModelSubscribe()
        fullscreenErrorView.setRetryClickListener { viewModel.onRetryButtonClick() }
        viewModel.onViewCreated()
    }

    private fun viewModelSubscribe() {
        viewModel.company.observe(this, Observer { setCompany(it) })

        viewModel.displayedChild.observe(this, Observer {
            if (viewFlipper.displayedChild != it) {
                viewFlipper.displayedChild = it
            }
        })
    }
   //...
}

