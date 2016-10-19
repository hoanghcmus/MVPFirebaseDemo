package com.hoangvnit.newuniondemo.base;

/**
 * Created by hoang on 10/18/16.
 */

public interface IBasePresenter<V extends IBaseView> {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
