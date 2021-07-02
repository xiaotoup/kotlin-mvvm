package com.zh.kotlin_mvvm.service

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.app.KeyguardManager.KeyguardLock
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils


class MyAccessibilityService : AccessibilityService() {

    var mAccessibilityService: AccessibilityService? = null
    var mAccessibilityEvent: AccessibilityEvent? = null

    /**
     * 键盘锁的对象
     */
    private var kl: KeyguardLock? = null

    /**
     * 是否有打开微信页面
     */
    private var isOpenPage = false

    /**
     * 是否点击了红包
     */
    private var isOpenRP = false

    /**
     * 是否点击了开按钮，打开了详情页面
     */
    private var isOpenDetail = false

    /**
     * 红包
     */
    private var rpNode: AccessibilityNodeInfo? = null

    /**
     * 微信几个页面的包名+地址。用于判断在哪个页面
     */
    private val LAUCHER = "com.tencent.mm.ui.LauncherUI"
    private val LUCKEY_MONEY_DETAIL = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI"
    private val LUCKEY_MONEY_RECEIVER = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        updateEvent(event)
        LogUtils.i("onAccessibilityEvent")
    }

    private fun updateEvent(event: AccessibilityEvent?){
        //接收事件
        //接收事件
        val eventType = event!!.eventType
        when (eventType) {
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {

                //通知栏事件
                val texts = event.text
                if (texts.isNotEmpty()) {
                    for (text in texts) {
                        val content = text.toString()
                        //通过微信红包这个关键词来判断是否红包。（如果有个朋友取名叫微信红包的话。。。）
                        val i = text.toString().indexOf("[微信红包]")
                        //如果不是微信红包，则不需要做其他工作了
                        if (i == -1) break
                        if (!TextUtils.isEmpty(content)) {
                            if (isScreenLocked()) {
                                //如果屏幕被锁，就解锁
                                wakeAndUnlock()

                                //打开微信的页面
                                openWeichaPage(event)
                            } else {
                                //屏幕是亮的
                                //打开微信的页面
                                openWeichaPage(event)
                            }
                        }
                    }
                }
            }
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {

                //监测到窗口变化。
                if (isOpenPage) {
                    //如果是本程序打开了微信页面，那就执行去找红包
                    isOpenPage = false
                    val className = event.className.toString()
                    //是否微信聊天页面的类
                    if (className == LAUCHER) {
                        findStuff()
                    }
                }
                if (isOpenRP && LUCKEY_MONEY_RECEIVER == event.className.toString()) {
                    //如果打开了抢红包页面
                    val rootNode1 = rootInActiveWindow
                    if (findOpenBtn(rootNode1)) {
                        //如果找到按钮
                        isOpenDetail = true
                    } else {
                        //回到桌面
                        back2Home()
                    }
                    isOpenRP = false
                }
                if (isOpenDetail && LUCKEY_MONEY_DETAIL == event.className.toString()) {
                    //打开了红包详情页面，看下抢了多少钱
                    findInDatail(rootInActiveWindow)
                    isOpenDetail = false
                    back2Home()
                }
            }
        }
        //释放一下资源。
        releese()
    }

    /**
     * 在红包详情页面寻找抢到多少钱。
     * 实际上不关心的童鞋可以不写这个方法了。
     */
    private fun findInDatail(rootNode: AccessibilityNodeInfo): Boolean {
        for (i in 0 until rootNode.childCount) {
            val nodeInfo = rootNode.getChild(i)
            Log.d(
                "mylog",
                "----" + i + " node == " + nodeInfo.className + " " + nodeInfo.childCount
            )
            if ("android.widget.TextView" == nodeInfo.className.toString()) {
                if ("元" == nodeInfo.text.toString()) {
                    val momeny = rootNode.getChild(i - 1).text.toString().toFloat()
                    Log.d("mylog", "--------momeny == $momeny")
                    return true
                }
                Log.d("mylog", "------textview == " + nodeInfo.text.toString())
            }
            if (findInDatail(nodeInfo)) {
                return true
            }
        }
        return false
    }

    /**
     * 遍历找东西
     */
    private fun findStuff() {
        val rootNode = rootInActiveWindow
        if (findRP(rootNode)) {
            isOpenRP = true
        }
        if (rpNode != null) {
            rpNode?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    /**
     * 在聊天页面迭代找红包
     */
    private fun findRP(rootNode: AccessibilityNodeInfo): Boolean {
        for (i in 0 until rootNode.childCount) {
            val nodeInfo = rootNode.getChild(i)
                ?: //                Log.d("mylog", "--------nodeinfo == null");
                continue
            Log.d(
                "mylog",
                "--------nodeinfo  class = " + nodeInfo.className + " ds = " + nodeInfo.contentDescription
            )
            if ("android.widget.TextView" == nodeInfo.className) {
                Log.d("mylog", "----------textview" + nodeInfo.text)
                if ("微信红包" == nodeInfo.text) {
                    isOpenRP = true
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            }
            if (findRP(nodeInfo)) {
                Log.d("mylog", "----------classname" + nodeInfo.className)
                if ("android.widget.LinearLayout" == nodeInfo.className) {
                    rpNode = nodeInfo
                    Log.d("mylog", "------------LinearLayout")
                    //                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true
                }
            }
        }
        return false
    }

    //
    private fun findOpenBtn(rootNode: AccessibilityNodeInfo): Boolean {
        for (i in 0 until rootNode.childCount) {
            val nodeInfo = rootNode.getChild(i)
            Log.d(
                "mylog",
                "--------RP node className = " + nodeInfo.className + " cd:" + nodeInfo.contentDescription
            )
            //            if ("android.widget.TextView".equals(nodeInfo.getClassName()))
//            {
//                Log.d("mylog", "----------RPtextview" + nodeInfo.getText());
//            }
            if ("android.widget.Button" == nodeInfo.className) {
                Log.d("mylog", "----------RPbutton")
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return true
            }
            findOpenBtn(nodeInfo)
        }
        return false
    }

    //打开微信聊天页面
    private fun openWeichaPage(event: AccessibilityEvent) {
        if (event.parcelableData != null && event.parcelableData is Notification) {
            //得到通知的对象
            val notification: Notification = event.parcelableData as Notification

            //得到通知栏的信息
//            String content = notification.tickerText.toString();
//            String name = content.substring(0, content.indexOf(":"));
//            String scontent = content.substring(content.indexOf(":"), content.length());
//            Log.d("mylog", "------openWeichaPage  name: " + name + " content: " + scontent);
            isOpenPage = true

            //打开通知栏的intent，即打开对应的聊天界面
            val pendingIntent: PendingIntent = notification.contentIntent
            try {
                pendingIntent.send()
            } catch (e: PendingIntent.CanceledException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 系统是否在锁屏状态
     */
    private fun isScreenLocked(): Boolean {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isScreenOn = pm.isScreenOn //如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        return !isScreenOn
    }

    /**
     * 解锁
     */
    @SuppressLint("InvalidWakeLockTag")
    private fun wakeAndUnlock() {
        //获取电源管理器对象
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager

        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        val wl = pm.newWakeLock(
            PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
            "bright"
        )

        //点亮屏幕
        wl.acquire(1000)

        //得到键盘锁管理器对象
        val km = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        kl = km.newKeyguardLock("unLock")

        //解锁
        kl?.disableKeyguard()
    }

    /**
     * 收尾工作
     */
    private fun releese() {
        kl?.reenableKeyguard()
        rpNode = null
    }

    /**
     * 回到系统桌面
     */
    private fun back2Home() {
        val home = Intent(Intent.ACTION_MAIN)
        home.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        home.addCategory(Intent.CATEGORY_HOME)
        startActivity(home)
    }

    /**
     * 当系统连接上你的服务时被调用
     */
    override fun onServiceConnected() {
        Toast.makeText(this, "嘻嘻嘻", Toast.LENGTH_SHORT).show()
        super.onServiceConnected()
    }

    /**
     * 必须重写的方法：系统要中断此service返回的响应时会调用。在整个生命周期会被调用多次。
     */
    override fun onInterrupt() {
        Toast.makeText(this, "我要死鸟", Toast.LENGTH_SHORT).show()
    }

    /**
     * 在系统要关闭此service时调用。
     */
    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "拜拜", Toast.LENGTH_SHORT).show()
        return super.onUnbind(intent)
    }

    /*override fun onInterrupt() {
        LogUtils.i("onInterrupt")
    }

    private fun updateEvent(service: AccessibilityService?, event: AccessibilityEvent?) {
        if (service != null && mAccessibilityService == null) {
            mAccessibilityService = service
        }
        if (event != null) {
            mAccessibilityEvent = event
        }
    }

    //根据text搜索所有符合的节点
    fun findNodesByText(text: String?): List<AccessibilityNodeInfo?>? {
        val nodeInfo: AccessibilityNodeInfo? = getRootNodeInfo()
        if (nodeInfo != null) {
            Log.i("accessibility", "getClassName：" + nodeInfo.className)
            Log.i("accessibility", "getText：" + nodeInfo.text)
            //需要在xml文件中声明权限android:accessibilityFlags="flagReportViewIds"
            // 并且版本大于4.3 才能获取到view 的 ID
            Log.i("accessibility", "getClassName：" + nodeInfo.viewIdResourceName)
            return nodeInfo.findAccessibilityNodeInfosByText(text)
        }
        return null
    }

    //根据ID搜索所有符合的节点
    fun findNodesById(viewId: String?): List<AccessibilityNodeInfo?>? {
        val nodeInfo: AccessibilityNodeInfo? = getRootNodeInfo()
        if (nodeInfo != null) {
            return nodeInfo.findAccessibilityNodeInfosByViewId(viewId)
        }
        return null
    }

    //获取跟节点
    private fun getRootNodeInfo(): AccessibilityNodeInfo? {
        val curEvent = mAccessibilityEvent!!
        var nodeInfo: AccessibilityNodeInfo? = null
        if (mAccessibilityService != null) {
            nodeInfo = mAccessibilityService!!.rootInActiveWindow
        }
        return nodeInfo
    }

    //模拟点击
    private fun performClick(nodeInfos: List<AccessibilityNodeInfo>?): Boolean {
        if (nodeInfos != null && nodeInfos.isNotEmpty()) {
            var node: AccessibilityNodeInfo
            for (i in nodeInfos.indices) {
                node = nodeInfos[i]
                // 获得点击View的类型
                Log.i("accessibility", "getClassName：" + node.className)
                // 进行模拟点击
                if (node.isEnabled) {
                    return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
        }
        return false
    }

    //模拟退出键
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    fun clickBackKey(): Boolean {
        return mAccessibilityService!!.performGlobalAction(GLOBAL_ACTION_BACK)
    }*/
}