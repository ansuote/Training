package com.lkl.ansuote.traning.module.rxtest

import android.os.Bundle
import android.view.View
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.rx_test_activity.*

/**
 *
 *
 * @author huangdongqiang
 * @date 13/08/2018
 */
class RxTestActivity : BaseActivity(), View.OnClickListener {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.rx_test_activity)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_create_observable -> {
                createObservable()
            }
            btn_concat -> {
                contact()
            }
            btn_merge -> {
                merge()
            }

            btn_zip -> {
                zip()
            }

            btn_flatmap -> {
                flatmap()
            }
        }
    }


    /**
     * 创建操作符
     */
    private fun createObservable() {
        Observable.create<String> {
            it.onNext("【Apple 这个是数据源】")
            Logger.i("事件产生时候的线程 -- " + Thread.currentThread().toString())
            it.onComplete()
        }.subscribeOn(Schedulers.io())  //指定事件产生的线程，以第一次为准
                .doOnNext{
                    Logger.i("doOnNext 可以作为数据保存 -- it = $it")
                }
                .map {
                    "$it-- 在这里增加了 Map 操作"
                    //0 // 直接转化类型
                }
                .observeOn(AndroidSchedulers.mainThread()) //指定后续事件的线程，可以多次调度
                .doOnSubscribe {
                    Logger.i("doOnSubscribe -- 打开加载对话框  --- currentThread = " + Thread.currentThread().toString())
                }
                .doFinally {
                    Logger.i("doFinally -- 关闭加载对话框  --- currentThread = " + Thread.currentThread().toString())
                }
                .subscribe({
                    Logger.i("onNext 接收 -- $it --- currentThread = " + Thread.currentThread().toString())
                }, {
                    Logger.i("onError 接收 -- $it")
                }, {
                    Logger.i("onComplete 接收  --- currentThread = " + Thread.currentThread().toString())
                }, {
                    Logger.i("onSubscribe 接收 -- $it  --- currentThread = " + Thread.currentThread().toString())
                })
    }

    /**
     * contact 可以作为 顺序执行
     * 利用 concat 的必须调用 onComplete 后才能订阅下一个 Observable 的特性.
     * onNext 则会中断，不会访问接下来的 observable
     * 可以顺序执行不同类型的 observable ,返回数据类型为 Any
     */
    fun contact() {
        Observable.concat(getUserUrlObservable(), getUserTokenObservable())
                .doOnSubscribe {
                    url = ""
                }
                .doFinally {

                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.i("contact 获取的结果为 = $it")
                },{
                    Logger.i(it.toString())
                })

    }

    private fun getUserUrlObservable(): Observable<String> {

        return Observable.create<String> {
            url = getUserUrl()
            if (url.isNullOrBlank()) {
                it.onError(Throwable("url.isNullOrBlank"))
            } else {
                it.onComplete()
            }
        }.subscribeOn(Schedulers.newThread())
    }

    private fun getUserTokenObservable(): Observable<String> {
        return Observable.create<String> {
            it.onNext(getUserToken(url))
        }.subscribeOn(Schedulers.newThread())
    }

    /**
     * 模拟获取一个 URL
     */
    private fun getUserUrl():String {
        Logger.i("进入 getUserUrl --- currentThread = " + Thread.currentThread().toString())
        Thread.sleep(3 * 1000)

        return "www.google.com"
    }

    /**
     * 模拟获取一个 urserToken
     */
    private fun getUserToken(url: String?): String {
        Logger.i("进入 getUserToken --- currentThread = " + Thread.currentThread().toString())

        Thread.sleep(5 * 1000)

        return "这个一个 userToken"
    }

    //------------------------------------------------------------------------------------------//

    /**
     * merge 并发执行
     * 无序
     * 有几个 observable 就回调几次 onNext
     *
     * mergeDelayError
     * mergeDelayError操作符类似于merge操作符，唯一不同就是如果在合并途中出现错误，不会立即发射错误通知，
     * 而是保留错误直到合并后的Observable将所有的数据发射完成，此时才会将onError提交给订阅者。
     */
    private fun merge() {
        Observable.merge(getTitleDataObservable(), getContentDataObservable())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //更新界面
                    when(it) {
                        is TitleData -> {}
                        is ContentData -> {}
                    }
                    Logger.i("onNext = " + it.toString() + " --- currentThread = " + Thread.currentThread().toString())
                },{
                    Logger.i("onError = " + it.toString() + " --- currentThread = " + Thread.currentThread().toString())
                },{
                    Logger.i("onComplete" + " --- currentThread = " + Thread.currentThread().toString())
                })
    }


    private fun getTitleDataObservable(): Observable<TitleData>{
        return Observable.create<TitleData> {

            it.onNext(getTitleData())
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
    }

    private fun getContentDataObservable(): Observable<ContentData> {
        return Observable.create<ContentData> {
            it.onNext(getContentData())
            it.onComplete()
        }.subscribeOn(Schedulers.newThread())
    }

    /**
     * 模拟获取标题数据
     */
    private fun getTitleData(): TitleData {
        Logger.i("进入 getTitleData --- currentThread = " + Thread.currentThread().toString())

        Thread.sleep(5 * 1000)
        return TitleData("Title1")
    }

    /**
     * 模拟获取标题数据
     */
    private fun getContentData(): ContentData {
        Logger.i("进入 getContentData --- currentThread = " + Thread.currentThread().toString())
        Thread.sleep(6 * 1000)
        return ContentData("Content1")
    }

    /**
     * 标题数据结构
     */
    data class TitleData(val title:String)

    /**
     * 内容数据结构
     */
    data class ContentData(val content: String)


    //------------------------------------------------------------------------------------------//

    /**
     * 并发
     * 合并成一个操作符，再发射。
     * 只调用一次 onNext
     */
    private fun zip() {
//        Observable.zip(getTitleDataObservable(), getContentDataObservable(), object : BiFunction<TitleData, ContentData, String> {
//            override fun apply(t1: TitleData, t2: ContentData): String {
//                Logger.i("lkl")
//                return t1.title + t2.content
//            }
//
//
//        }).subscribe {
//
//        }

        Observable.zip(getTitleDataObservable(),
                getContentDataObservable(),
                BiFunction<TitleData, ContentData, String> { t1, t2 ->
                    Logger.i("t1 + t2 合并")
                    t1.title + t2.content
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Logger.i("onNext = " + it.toString() + " --- currentThread = " + Thread.currentThread().toString())
                },{
                    Logger.i("onError = " + it.toString() + " --- currentThread = " + Thread.currentThread().toString())
                },{
                    Logger.i("onComplete" + " --- currentThread = " + Thread.currentThread().toString())
                })


    }

    /**
     * 返回一个 Observable
     * 实现嵌套请求
     * 无序，（有序可以用 concatMap 代替）
     */
    private fun flatmap() {
        Observable.create<List<TitleData>> {

            it.onNext(mutableListOf<TitleData>().apply {
                add(TitleData("title_flatmap_01"))
                add(TitleData("title_flatmap_02"))
            })
            it.onComplete()

        }.flatMap(Function<List<TitleData>, ObservableSource<TitleData>> {
            Observable.fromIterable(it) //遍历发射
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.i("subscribe = ${it.title}")
                },{
                    Logger.i("onError = $it")
                },{
                    Logger.i("onComplete")
                })
    }

}
