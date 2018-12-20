package com.idealcn.lifecycle.study.ui.mvp.model

import android.arch.lifecycle.ViewModel
import com.idealcn.lifecycle.study.AppHelper
import com.idealcn.lifecycle.study.bean.BaseResponseBean
import com.idealcn.lifecycle.study.bean.ResponseLoginBean
import com.idealcn.lifecycle.study.ext.ext
import com.idealcn.lifecycle.study.http.RetrofitClient
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * @author: guoning
 * @date: 2018/11/30 14:58
 * @description:
 */
class LoginModel : ViewModel(){

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * 将登录返回的数据保存在本地数据库和缓存中
     */
    fun saveUser(user : ResponseLoginBean){
        //保存在本地数据库
        AppHelper.getHelper().getDaoSession().responseLoginBeanDao.insert(user)

        //保存在缓存
        AppHelper.getHelper().saveUser(user)

    }

    fun login(username: String?, password: String?) : Observable<BaseResponseBean<ResponseLoginBean>>{
       return     RetrofitClient.newInstance().api.login(username!!, password!!).ext()
    }
}