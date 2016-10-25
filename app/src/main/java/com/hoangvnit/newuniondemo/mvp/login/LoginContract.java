package com.hoangvnit.newuniondemo.mvp.login;

import android.content.Intent;

import com.hoangvnit.newuniondemo.base.BaseContract;

/**
 * Created by hoang on 10/25/16.
 */

public class LoginContract {

    public interface LoginPresenter extends BaseContract.BasePresenter<LoginView> {

        void loginGoogle();

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface LoginView extends BaseContract.BaseView {
    }
}
