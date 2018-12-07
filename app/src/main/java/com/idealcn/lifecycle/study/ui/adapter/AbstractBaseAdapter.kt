package com.idealcn.lifecycle.study.ui.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class AbstractBaseAdapter<T,D :ViewDataBinding> : RecyclerView.Adapter<AbstractBaseHolder<D>> {


    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_FOOTER = 1
        const val TYPE_NORMAL = 2
    }

    private var mHeaderView : View? = null
    private var mFooterView : View? = null

    private  var dataList :ArrayList<T>

    constructor(){
        dataList = arrayListOf()
    }

    constructor ( list : ArrayList<T>){
        dataList = list
    }

    override fun onBindViewHolder(holder: AbstractBaseHolder<D>, position: Int) {
        val type = getItemViewType(position)
        when(type){
            TYPE_HEADER -> {

            }
            TYPE_FOOTER -> {

            }
            TYPE_NORMAL -> {

                onBindNormalHolder(holder,holder.layoutPosition,dataList[holder.layoutPosition])
            }
            else -> {

            }
        }

    }

    abstract fun onBindNormalHolder(
        holder: AbstractBaseHolder<D>,
        position: Int,
        t: T
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractBaseHolder<D> {
        val context = parent.context
        val dataBinding :D = DataBindingUtil.inflate(LayoutInflater.from(context),getLayout(),parent,false)
        val holder :AbstractBaseHolder<D>
         holder = AbstractBaseHolder<D>(dataBinding)
        return holder
    }

    override fun getItemCount(): Int {
        var size = dataList.size
        if (getHeaderView()!=null)size += 1
        if (getFooterView()!=null)size += 1
        return  size
    }

    override fun getItemViewType(position: Int): Int {
        if (getHeaderView()!=null && position==0)return TYPE_HEADER
        if (getFooterView()!=null && position == itemCount-1)return TYPE_FOOTER
        return TYPE_NORMAL
    }


    abstract fun getLayout() : Int



    fun  addHeaderView(headerView : View){
        this.mHeaderView = headerView
    }

    fun getHeaderView() :View? {
        return this.mHeaderView
    }


    fun addFooterView(footerView :View){
        this.mFooterView = footerView
    }

    fun  getFooterView() :View?{
        return this.mFooterView
    }

    fun addData(data: ArrayList<T>) {
        val size = dataList.size
//        if (size>0){
            dataList.addAll(size,data)
//        }else
//            dataList.addAll(0,data)
        notifyItemRangeChanged(size,data.size)
    }

    fun addData( index : Int,data : ArrayList<T> ){
        dataList.addAll(index,data)
    }

    fun getData() : ArrayList<T>{
        return dataList
    }

    fun clearData() {
        dataList.clear()
        //这一步是考虑到多个tab共用一个RecyclerView,如果只是清空数据集合,
        // 而不刷新RecyclerView,新加载的数据大小与之前不一致会导致IndexOutOfBound异常
        notifyDataSetChanged()
    }


}