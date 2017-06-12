package com.example.a51425.mainuiframe.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *  BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected Handler mUiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealWith(msg);
        }
    };



    protected View view;
    private Unbinder mUnbinder;
    protected boolean isFirst=true;
    private InputMethodManager mIm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        view = getContentView();
        setContentView(view);
        //butterknife绑定
        mUnbinder = ButterKnife.bind(view.getContext(), view);
        initView();
        initListener();
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (isFirst) {
            initData();
            isFirst=false;
        }else{
             needRefresh();
        }
    }

    /**
     * 需要每次都刷新的时候，里面和initData中有重合的地方可以封装一下使用
     */
    protected void needRefresh() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity结束时移除handler中的消息
        if (mUiHandler!=null){
            mUiHandler.removeCallbacksAndMessages(null);
        }
        //Activity结束时解除butterknife的绑定
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        AppManager.getAppManager().removeStack(this);


    }

    /**
     * Activity跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public void jumpToActivity(Context context, Class<?> targetActivity,
                               Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param context
     * @param targetActivity
     * @param requestCode
     * @param bundle
     */
    public void jumpToActivityForResult(Context context,
                                        Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * 必须实现，返回想要展示的view
     * @return
     */
    protected abstract View getContentView();

    /**
     * 进行数据的更新
     */
    protected  void initData(){}

    /**
     * 初始化View相关的代码写在这个方法中
     */
    private void initView() {

    }

    /**
     * 初始化Listener的代码写在这个方法中
     */
    protected void initListener() {
    }


    /**
     * 处理handler逻辑，
     * @param msg
     */
    protected void dealWith(Message msg) {

    }

}
