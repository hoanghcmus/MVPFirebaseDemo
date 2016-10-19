package com.hoangvnit.newuniondemo.mvp.login;

import android.content.Intent;

import com.hoangvnit.newuniondemo.base.IBasePresenter;

/**
 * Created by hoang on 10/18/16.
 */

public interface ILoginPresenter extends IBasePresenter<ILoginView> {

    void loginGoogle();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
