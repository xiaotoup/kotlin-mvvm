package com.zh.config.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.tools.PictureFileUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zh.common.GlideEngine
import com.zh.common.R
import com.zh.common.base.BaseFragment
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.GlideManager
import com.zh.common.view.dialog.BottomDialog
import kotlinx.android.synthetic.main.fragment_picture.*
import java.util.*

/**
 * 公用图片选择
 */
class PictureFragment(
    override val layoutRes: Int = R.layout.fragment_picture,
    override val viewModel: NormalViewModel = NormalViewModel()
) : BaseFragment<ViewDataBinding, NormalViewModel>() {

    private var isShowAdd = true //是否显示添加图片按钮
    private val margin = SizeUtils.dp2px(5f)
    private val mAdapter by lazy { PictureAdapter() }

    var deleteImg = R.mipmap.delete_black//删除按钮
    var takeImg = R.mipmap.iv_take_photo//选择图片按钮
    var needShowTake = true//是否需要显示选择图片按钮
    var maxSelectNum = 9 //最大选择数量,默认9张
    var isEnableCrop: Boolean = false//是否开启裁剪,默认关闭
    var circleDimmedLayer: Boolean = false//是否开启圆形裁剪
    var isShowCamera: Boolean = true//列表是否显示拍照按钮,默认开启
    var selectionMode: Int = PictureConfig.MULTIPLE//单选or多选,默认多选
    var maxVideoSelectNum: Int = 9//视频最大选择数量,默认9个
    var videoMaxSecond: Int = 60//查询多少秒以内的视频, 默认60s
    var recordVideoSecond: Int = 60//录制视频秒数, 默认60s
    var mimeType: Int = PictureMimeType.ofImage()//媒体类型,默认图片

    /**
     * 获取选择的图片
     */
    fun getPictureList(): MutableList<LocalMedia> = selectList

    override fun initView(savedInstanceState: Bundle?) {
        //TODO 两边距离 总10
        mItemHelper.attachToRecyclerView(recyclerView)
        recyclerView.setPadding(margin, margin, 0, margin)
        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int) {
                if (isShowAdd && position == selectList.size) {
                    openPicture()
                } else {
                    previewPicture(position)
                }
            }
        })
    }

    /**
     * 选择相册弹框
     */
    fun showSelectDialog() {
        activity?.let {
            BottomDialog.Builder(it)
                .setContentList(arrayOf("相机", "相册"))
                .setPositivePositionItem { _, which ->
                    when (which) {
                        0 -> openCamera()
                        1 -> openPicture()
                    }
                }.create().show()
        }
    }

    /**
     * 打开相册
     */
    private fun openPicture() {
        PictureSelector.create(this)
            .openGallery(mimeType)//相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
            .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
            .isCamera(isShowCamera)//列表是否显示拍照按钮
            .isEnableCrop(isEnableCrop)//是否开启裁剪
            .circleDimmedLayer(true)// 是否开启圆形裁剪
            .isZoomAnim(true)//图片选择缩放效果
            .imageFormat(if (Build.VERSION.SDK_INT < 29) PictureMimeType.JPEG else PictureMimeType.PNG_Q)//拍照图片格式后缀,默认jpeg, PictureMimeType.PNG，Android Q使用PictureMimeType.PNG_Q
            .selectionMode(selectionMode)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
            .isSingleDirectReturn(true)//PictureConfig.SINGLE模式下是否直接返回
            .maxSelectNum(maxSelectNum)//最大选择数量,默认9张
            .minSelectNum(1)// 最小选择数量
            .maxVideoSelectNum(maxVideoSelectNum)//视频最大选择数量
            .minVideoSelectNum(1)//视频最小选择数量
            .videoMaxSecond(videoMaxSecond)// 查询多少秒以内的视频
            .videoMinSecond(1)// 查询多少秒以内的视频
            .selectionMedia(selectList)//是否传入已选图片
            .isCompress(true)//是否压缩
            .synOrAsy(true)//开启同步or异步压缩
            .recordVideoSecond(recordVideoSecond)//录制视频秒数 默认60s
            .previewEggs(true)//预览图片时是否增强左右滑动图片体验
            .isGif(true)//是否显示gif
            .circleDimmedLayer(circleDimmedLayer)// 是否开启圆形裁剪
            .previewImage(true)//是否预览图片
            .previewVideo(true)//是否预览视频
            .enablePreviewAudio(true)//是否预览音频
            .hideBottomControls(true)//显示底部uCrop工具栏
            .isMultipleSkipCrop(true)//多图裁剪是否支持跳过
            .isDragFrame(true)//是否可拖动裁剪框(固定)
            .showCropGrid(false)//是否显示裁剪矩形网格
            .isMultipleRecyclerAnimation(true)// 多图裁剪底部列表显示动画效果
            .isOriginalImageControl(true)//开启原图选项
            .isAndroidQTransform(true)//Android Q版本下是否需要拷贝文件至应用沙盒内
            .isMaxSelectEnabledMask(true)//选择条件达到阀时列表是否启用蒙层效果
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    /**
     * 单独使用相机
     */
    private fun openCamera() {
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())//单独使用相机 媒体类型 PictureMimeType.ofImage()、ofVideo()
            .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
            .isEnableCrop(isEnableCrop)//是否开启裁剪
            .circleDimmedLayer(circleDimmedLayer)// 是否开启圆形裁剪
            .isCompress(true)//是否压缩
            .synOrAsy(true)//开启同步or异步压缩
            .isDragFrame(true)//是否可拖动裁剪框(固定)
            .showCropGrid(false)//是否显示裁剪矩形网格
            .isAndroidQTransform(true)//Android Q版本下是否需要拷贝文件至应用沙盒内
            .forResult(PictureConfig.REQUEST_CAMERA)
    }

    /**
     * 预览图片
     */
    private fun previewPicture(position: Int) {
        PictureSelector.create(this)
            .themeStyle(R.style.picture_default_style)
            .isNotPreviewDownload(true) //可自定长按保存路径
            .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
            .openExternalPreview(position, selectList)
    }

    /**
     * 预览视频
     */
    private fun previewVideo(videoPath: String) {
        PictureSelector.create(this).externalPictureVideo(videoPath)
    }

    private var selectList = mutableListOf<LocalMedia>()
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RxAppCompatActivity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.REQUEST_CAMERA, PictureConfig.CHOOSE_REQUEST -> {//返回图片
                    selectList = PictureSelector.obtainMultipleResult(data)
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //包括裁剪和压缩后的缓存，要在上传成功后调用，type 指的是图片or视频缓存取决于你设置的ofImage或ofVideo 注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(context, mimeType)
        // 清除所有缓存 例如：压缩、裁剪、视频、音频所生成的临时文件
        PictureFileUtils.deleteAllCacheDirFile(context)
    }

    interface OnItemClickListener {
        fun onItemClick(adapter: RecyclerView.Adapter<*>, view: View, position: Int)
    }

    inner class PictureAdapter : RecyclerView.Adapter<BaseViewHolder>() {

        //TODO 显示4张图片
        private var width = (ScreenUtils.getScreenWidth() - margin * 4) / 4
        private var mOnItemClickListener: OnItemClickListener? = null

        fun setOnItemClickListener(listener: OnItemClickListener?) {
            this.mOnItemClickListener = listener
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return BaseViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.item_zh_picture, parent, false)
            )
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            //显示图片
            val ivImage: ImageView = holder.getView(R.id.ivImage)
            val lp = ivImage.layoutParams as FrameLayout.LayoutParams
            lp.rightMargin = margin
            lp.bottomMargin = margin
            lp.width = width
            lp.height = width
            ivImage.layoutParams = lp
            if (selectList.size != holder.layoutPosition) {
                GlideManager.loadUrl(selectList[holder.layoutPosition].compressPath, ivImage)
            }

            //点击删除
            val ivDelete: ImageView = holder.getView(R.id.ivDelete)
            ivDelete.setImageResource(deleteImg)
            ivDelete.setOnClickListener {
                selectList.removeAt(holder.layoutPosition)
                notifyItemRemoved(holder.layoutPosition)
            }

            //控制显示添加图片按钮
            if (isShowAdd && selectList.size == holder.layoutPosition) {
                ivDelete.visibility = View.GONE
                ivImage.scaleType = ImageView.ScaleType.FIT_XY
                ivImage.setImageResource(takeImg)
            } else {
                ivDelete.visibility = View.VISIBLE
            }

            //长按拖拽
            holder.itemView.setOnLongClickListener {
                if (selectList.size > holder.layoutPosition) {
                    mItemHelper.startDrag(holder)
                }
                false
            }

            //点击事件
            holder.itemView.setOnClickListener {
                mOnItemClickListener?.onItemClick(
                    this@PictureAdapter,
                    holder.itemView,
                    holder.layoutPosition
                )
            }
        }

        override fun getItemCount(): Int {
            var mCount = 0
            if (needShowTake && selectList.size < maxSelectNum) {
                isShowAdd = true
                mCount = selectList.size + 1
            } else {
                isShowAdd = false
                mCount = selectList.size
            }
            return mCount
        }
    }

    var mItemHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if (recyclerView.layoutManager is GridLayoutManager) {
                val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                val swipeFlags = 0
                makeMovementFlags(dragFlags, swipeFlags)
            } else {
                val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = 0
                makeMovementFlags(dragFlags, swipeFlags)
            }
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //得到当拖拽的viewHolder的Position
            val fromPosition: Int = viewHolder.adapterPosition
            //拿到当前拖拽到的item的viewHolder
            val toPosition: Int = target.adapterPosition
            if (toPosition >= selectList.size) return false
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(selectList, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(selectList, i, i - 1)
                }
            }
            mAdapter.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Log.e("tag", "拖拽完成 方向$direction")
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) viewHolder?.itemView?.setBackgroundColor(
                Color.LTGRAY
            )
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.setBackgroundColor(0)
        }

        //重写拖拽不可用
        override fun isLongPressDragEnabled(): Boolean {
            return false
        }
    })
}