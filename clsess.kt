class CompanyActivity : BaseProgressActivity() {

    private var companyDisposable: Disposable? = null
    private var companyRequisites: CompanyRequisites? = null

    @Inject
    private lateinit var companyProvider: CompanyProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        activityComponent().inject(this)

        initToolbar()
        fullscreenErrorView.setRetryClickListener { loadCompanyRequisites() }
        loadCompanyRequisites()
    }
    
    //...

    private fun loadCompanyRequisites() {
        showProgressView()

        companyDisposable?.dispose()
        companyDisposable = companyProvider.getCompanyRequisites()
                .compose(asyncSingle())
                .subscribe({
                    showDataView()
                    setCompany(it)
                },
                        {
                            Timber.e(it)
                            showErrorView(it)
                        })
    }
    // ...
}
