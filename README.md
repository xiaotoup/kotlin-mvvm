# kotlin-mvvm
kotlin mvvm+dataBinding+retrofit2+ARouter等BaseActivity、BaseFragment、BaseDialogFragment基类封装

Android开发项目基本使用框架，封装了各类组件，在基类实现了沉浸式状态栏，可以自己更改颜色，更高效全能开发框架

![框架所有功能](https://user-images.githubusercontent.com/32659960/140476015-c2c98786-2e17-4871-af63-b67450d34b11.jpg")
<div align=left><img src="https://user-images.githubusercontent.com/32659960/140476015-c2c98786-2e17-4871-af63-b67450d34b11.jpg" width="400" height="800"/></div>


里面封装各种组件：
## RelativeItemView 一个item，左右文字图片一个控件完美使用
```
<com.zh.common.view.RelativeItemView
                    android:layout_width="match_parent"
                    android:layout_height="40dp"
                    app:riv_driverColor="@color/colorPrimaryDark"
                    app:riv_driverMarginHorizontal="20dp"
                    app:riv_driverShow="true"
                    app:riv_editTextGravity="left"
                    app:riv_editTextHint="请输入"
                    app:riv_editTextMarginLeft="50dp"
                    app:riv_editTextPaddingLeft="15dp"
                    app:riv_editTextSize="12sp"
                    app:riv_leftText="微信"
                    app:riv_leftTextColor="@color/colorPrimary"
                    app:riv_leftTextDrawable="@drawable/picture_icon_back_arrow"
                    app:riv_leftTextDrawablePadding="5dp"
                    app:riv_leftTextPaddingLeft="15dp"
                    app:riv_leftTextSize="16sp"
                    app:riv_leftTextStyle="bold"
                    app:riv_rightText="235"
                    app:riv_rightTextColor="@color/color_text_999"
                    app:riv_rightTextDrawable="@drawable/picture_icon_arrow_down"
                    app:riv_rightTextDrawablePadding="5dp"
                    app:riv_rightTextDrawableTint="@color/color_text_999"
                    app:riv_rightTextPaddingRight="15dp"
                    app:riv_rightTextSize="13sp" />
```
### 属性
```
riv_leftImg 左边图片
riv_leftImgWidth 左边图片宽度 
riv_leftImgHeight 左边图片高度 
riv_leftText 左边文字 
riv_leftTextColor 左边文字颜色
riv_leftTextSize 左边文字_字体大小 
riv_leftTextPaddingLeft 左边文字_据左边距离
riv_leftTextDrawable 左边文字_drawableLeft图片 
riv_leftTextDrawableRight 左边文字_drawableLeft图片是否在右边 
riv_leftTextDrawablePadding 左边文字_drawablePadding
riv_leftTextDrawableTint 左边文字_drawableTint
riv_leftTextStyle 左边文字_加粗属性 
riv_editText Edit文字 
riv_editTextColor Edit文字颜色 
riv_editTextHint Edit的hint文字 
riv_editTextHintColor Edit的hint文字颜色
riv_editTextSize Edit文字_字体大小 
riv_editTextEnabled Edit是否可以编辑 
riv_editTextBackground Edit背景 
riv_editTextGravity Edit的gravity位置 
riv_editTextMarginLeft Edit文字_MarginLeft
riv_editTextMarginRight Edit文字_MarginRight 
riv_editTextPaddingLeft Edit文字_PaddingLeft 
riv_editTextPaddingRight Edit文字_PaddingRight 
riv_editTextStyle Edit文字_加粗属性
riv_rightText 右边文字
riv_rightTextHint 右边文字hint
riv_rightTextHintColor 右边文字hint颜色
riv_rightTextColor 右边文字颜色
riv_rightTextSize 右边文字_字体大小
riv_rightTextPaddingRight 右边文字_据左边距离 
riv_rightTextDrawable 右边文字_drawableRight图片 
riv_rightTextDrawablePadding 右边文字_drawablePadding
riv_rightTextDrawableTint 右边文字_drawableTint 
riv_rightTextStyle 右边文字_加粗属性 
riv_driverShow 下划线是否显示
riv_driverColor 下划线颜色 
riv_driverHeight 下划线高度
riv_driverMarginLeft 下划线距离左边距离 
riv_driverMarginRight 下划线距离右边距离
riv_driverMarginHorizontal 下划线距离左右边距离 
```
## TitleBarView 通用标题栏封装
```
<com.zh.common.view.TitleBarView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tb_centerText="测试"
            app:tb_leftImageDrawable="@drawable/picture_icon_back_arrow" />
```
### 属性
```
tb_centerText 中间标题
tb_centerTextColor 中间标题文字颜色 
tb_centerTextSize 中间标题文字_字体大小
tb_rightText 右边文字 
tb_rightTextColor 右边文字颜色 
tb_rightTextSize 右边文字_字体大小
tb_leftText 左边文字
tb_leftTextColor 左边文字颜色 
tb_leftTextSize 左边文字_字体大小 
tb_leftImageDrawable 左边图片
tb_rightImageDrawable 右边图片 
tb_rightImageDrawable2 右边图片2
tb_rightImage2_marginRight 右边图片2_距离右边距离 
tb_divider 底部分割线 
tb_titleBarHeight TitleBar高度
tb_titleBarBackground TitleBar背景色 
```
## 发起网络请求

单独创建接口类
```
@POST(ApiManager.APPLOGIN_URL)
fun login(@Body body: RequestBody): Observable<BaseResponse<LoginBean>>
```
在viewmodle中调用即可
```
doNetRequest(apiService<INetService>().login(BaseMapToBody.convertMapToBody(map)),
            object : BaseObserver<LoginBean>(true) {

                override fun onISuccess(message: String, response: LoginBean) {
                    sid.set(response.bussData)
                    ToastUtils.showShort("code=${message}")
                }

                override fun onIError(e: ApiException) {
                    sid.set(e.message)
                    ToastUtils.showShort("code=${e.message}")
                }
            })
```
###普通类继承 BaseActivity（BaseFragment、BaseDialogFragment 同理）
```
class PictureActivity(
    override val layoutRes: Int = R.layout.activity_picture,
    override val viewModel: BaseViewModel = NormalViewModel()
) : BaseActivity<ViewDataBinding>() {

    private lateinit var pictureFragment: PictureFragment

    override fun initView(savedInstanceState: Bundle?) {
      
    }
}
```

BaseActivity封装
```
abstract class BaseActivity<BINDING : ViewDataBinding> : RxAppCompatActivity(), JumpActivity {

    lateinit var binding: BINDING
    private val isNotAddActivityList = "is_add_activity_list" //是否加入到activity的list，管理
    private var mApplication: BaseApplication? = null
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApplication = application as BaseApplication
        //添加到栈管理
        val isNotAdd = intent.getBooleanExtra(isNotAddActivityList, false)
        synchronized(BaseActivity::class.java) {
            if (!isNotAdd) mApplication?.getActivityList()?.add(this)
        }
        initViewDataBinding()
        initImmersionBars()
        //初始化组件
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
    }

    private fun initViewDataBinding() {
        if (layoutRes != 0) binding = DataBindingUtil.setContentView(this, layoutRes)
        val mViewModel = ViewModelProvider(this, ViewModelFactory(viewModel))[viewModel::class.java]
        //允许设置变量的值而不反映
        binding?.setVariable(viewModelId, mViewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
    }

    @get:LayoutRes
    abstract val layoutRes: Int
    open val viewModel: BaseViewModel = NormalViewModel()
    open val viewModelId = 0
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
        synchronized(BaseActivity::class.java) { mApplication?.getActivityList()?.remove(this) }
        binding?.unbind()
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称 (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).navigation(this, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(this, classActivity), type)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).with(bundle).navigation(this, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(this, classActivity).putExtras(bundle), type)
    }

    /**
     * 语言适配
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LanguageUtil().attachBaseContext(it) })
    }

    /**
     * 点击edittext以外区域隐藏软键盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        try {
            if (window.superDispatchTouchEvent(ev)) {
                return true
            }
        } catch (e: Exception) {
        }
        return onTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }

    private fun getLoadingDialog() {
        loadingDialog ?: also { loadingDialog = LoadingDialog(this) }
    }

    /**
     * 显示加载dialog
     */
    fun showLoading() {
        try {
            getLoadingDialog()
            loadingDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 结束dialog
     */
    fun dismissLoading() {
        try {
            loadingDialog?.let { if (it.isShowing) it.dismiss() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
```
BaseFragment封装
```
abstract class BaseFragment<BINDING : ViewDataBinding> : RxFragment(),
    JumpActivity, ImmersionOwner {

    lateinit var binding: BINDING
    private var rootView: View? = null
    private lateinit var mContext: Context
    private var loadingDialog: LoadingDialog? = null

    //ImmersionBar代理类
    private val mImmersionProxy = ImmersionProxy(this)

    override fun initImmersionBar() {
        initImmersionBars()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ARouter.getInstance().inject(this)
        if (null == rootView) { //如果缓存中有rootView则直接使用
            initViewDataBinding(inflater, container)
            this.rootView = binding.root;
        } else {
            rootView?.let {
                it.parent?.let { it2 -> (it2 as ViewGroup).removeView(it) }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //在OnCreate方法中调用下面方法，然后再使用线程，就能在uncaughtException方法中捕获到异常
        initView(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImmersionProxy.onActivityCreated(savedInstanceState)
    }

    private fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        if (layoutRes != 0) binding =
            DataBindingUtil.inflate(inflater, layoutRes, container, false)
        val mViewModel = ViewModelProvider(this, ViewModelFactory(viewModel))[viewModel::class.java]
        //允许设置变量的值而不反映
        binding?.setVariable(viewModelId, mViewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
    }

    @get:LayoutRes
    abstract val layoutRes: Int
    open val viewModel: BaseViewModel = NormalViewModel()
    open val viewModelId = 0
    abstract fun initView(savedInstanceState: Bundle?)

    open fun getRootView(): View? = rootView

    override fun onResume() {
        super.onResume()
        mImmersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mImmersionProxy.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //为rootView做缓存，在viewpager中使用fragment时可以提升切换流畅度
        rootView?.let {
            it.parent?.let { it2 -> (it2 as ViewGroup).removeView(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
        binding?.unbind()
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称 (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        val intent = Intent(context, getDestination(url))
        startActivityForResult(intent, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(activity, classActivity), type)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        val intent = Intent(context, getDestination(url))
        intent.putExtras(bundle)
        startActivityForResult(intent, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(activity, classActivity).putExtras(bundle), type)
    }

    /**
     * 由于ARouter不支持Fragment startActivityForResult(),需要获取跳转的Class
     * 根据路径获取具体要跳转的class
     */
    private fun getDestination(url: String): Class<*> {
        val postcard = ARouter.getInstance().build(url)
        LogisticsCenter.completion(postcard)
        return postcard.destination
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    override fun onLazyBeforeView() {}

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    override fun onLazyAfterView() {}

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    override fun onVisible() {}

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    override fun onInvisible() {}

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean = true

    private fun getLoadingDialog() {
        loadingDialog ?: also { loadingDialog = LoadingDialog(context!!) }
    }

    /**
     * 显示加载dialog
     */
    fun showLoading() {
        try {
            getLoadingDialog()
            loadingDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 结束dialog
     */
    fun dismissLoading() {
        try {
            loadingDialog?.let { if (it.isShowing) it.dismiss() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
```
viewmodel封装
```
open class BaseViewModel : ViewModel() {

    var pageIndex = 1
    var pageSize = 10
    private var isAddDisposable = false
    private val mCompositeDisposable = CompositeDisposable()

    //添加网络请求到CompositeDisposable
    private fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.also {
            Log.e("--okhttp--", "disposable is add")
            isAddDisposable = true
            it.add(disposable)
        }
    }

    override fun onCleared() {
        //解除网络请求
        mCompositeDisposable.also {
            if (isAddDisposable) {
                Log.e("--okhttp--", "disposable is clear")
                isAddDisposable = false
                mCompositeDisposable.clear()
            }
        }
    }

    /**
     * 实例化网络请求
     * hostUrl 域名, 默认ZjConfig.base_url，需要修改传入新的域名（新的每次都传）
     */
    inline fun <reified T : Any> apiService(hostUrl: String = ZjConfig.base_url): T =
        RetrofitManager.instance.apiService(T::class.java, hostUrl)

    /**
     * 公用的网络请求发起的操作
     * @param observable 发起请求的被观察着
     * @param observer 观察着回调
     */
    fun <R> doNetRequest(observable: Observable<out BaseResponse<R>>, observer: BaseObserver<R>) {
        val subscribeWith = observable
            .compose(ResponseTransformer.instance.handleResult())
            .compose(SchedulerProvider.instance.applySchedulers())
            .subscribeWith(observer)
        subscribeWith.getDisposable()?.let { addSubscribe(it) }
    }
```
