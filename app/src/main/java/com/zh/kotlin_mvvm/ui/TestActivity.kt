package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.kotlin_mvvm.R
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class TestActivity(
    override val layoutRes: Int = R.layout.activity_test,
    override val viewModel: BaseViewModel = BaseViewModel()
) : BaseActivity<ViewDataBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        button11.setOnClickListener {
            bgv.setNumber(bgv.getNumber() + 1)
        }
        button12.setOnClickListener {
            bgv.setNumber(bgv.getNumber() - 1)
        }
        button13.setOnClickListener {
            showLoading()
            GlobalScope.launch {
                delay(1000)
                dismissLoading()
            }
            if (button13.text.equals("有数字")) {
                bgv.setShowNumber(false)
                button13.text = "无数字"
            } else {
                bgv.setShowNumber(true)
                button13.text = "有数字"
            }
        }

//        var lis:MutableList<String> = MutableList(1, 0)
        /*GlobalScope.apply {
            val launch = launch {
                LogUtils.a("--" + Thread.currentThread().name)
                launch(Dispatchers.Main) {
                    Toast.makeText(this@TestActivity, "你好啊", Toast.LENGTH_SHORT).show()
                }
//                lis = mutableListOf()
//                lis.add("sss")
            }
        }*/
//        LogUtils.a(lis.size)
//         GlobalScope.launch {
//             LogUtils.a("111111111111111-" + Thread.currentThread().name)
//             delay(2000)
//             LogUtils.a("222222222222222-" + Thread.currentThread().name)
//             ToastUtils.showShort("111")
//             handler.sendMessage(Message())
//             withContext(Dispatchers.Main){
//                 bgv.setNumber(20)
//             }
//         }
//         ToastUtils.showShort("222")
//         LogUtils.a("3333333333333333-" + Thread.currentThread().name)

        /*GlobalScope.launch {
            LogUtils.a("111111111111111-" + Thread.currentThread().name)
            delay(2000)
            LogUtils.a("222222222222222-" + Thread.currentThread().name)
            testJoin()
        }*/

//      test()
        initData()
    }

    private val handler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            bgv.setNumber(20)
        }
    }

    fun initData() {
        /*GlobalScope.launch {
            test1()
            test2()
            test3()
        }*/
//        thread()
        GlobalScope.launch {
            val t1 = async { test1() }
            val t2 = async { test2() }
            t1.start()
            t2.start()
        }
    }

    private suspend fun test1() {
        delay(1000)
        LogUtils.a("test1")
    }

    private suspend fun test2() {
        delay(3000)
        LogUtils.a("test2")
    }

    private suspend fun test3() {
        delay(2000)
        LogUtils.a("test3")
    }

    private fun thread() {
        thread {
            LogUtils.a("aaaaaaaaa-" + Thread.currentThread().name)
            Thread.sleep(2000)
            LogUtils.a("bbbbbbbbb-" + Thread.currentThread().name)
        }
        LogUtils.a("ccccccccc-" + Thread.currentThread().name)
    }

    private fun test() = runBlocking {
        GlobalScope.launch {
            LogUtils.a("进入阻塞方法里面-" + Thread.currentThread().name)
            delay(5000)
            LogUtils.a("进入阻塞方法里面5000-" + Thread.currentThread().name)
        }
        LogUtils.a("进入阻塞方法外面-" + Thread.currentThread().name)
        delay(2000)
    }

    private suspend fun testJoin() {
        val joo = GlobalScope.launch {
            delay(500)
            LogUtils.a("你好" + Thread.currentThread().name)
        }
        LogUtils.a("小华" + Thread.currentThread().name)
        joo.cancelAndJoin()
        joo.join()
        LogUtils.a("帅" + Thread.currentThread().name)
    }
}