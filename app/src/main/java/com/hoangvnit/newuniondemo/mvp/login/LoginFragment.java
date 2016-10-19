package com.hoangvnit.newuniondemo.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.base.BaseFragment;
import com.hoangvnit.newuniondemo.util.LogUtil;

import butterknife.OnClick;

/**
 * Created by hoang on 10/18/16.
 */

public class LoginFragment extends BaseFragment implements ILoginView,
        GoogleApiClient.OnConnectionFailedListener {

    private ILoginPresenter mILoginPresenter;

    private GoogleApiClient mGoogleApiClient;

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    private static final int RC_SIGN_IN = 9001;

    public int getRcSignInCode() {
        return RC_SIGN_IN;
    }

    private FirebaseAuth mFirebaseAuth;

    public FirebaseAuth getFirebaseAuth() {
        return mFirebaseAuth;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mILoginPresenter = new LoginPresenter(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    @Override
    protected void initView() {
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogUtil.w("GoogleApiClient connect failed!");
    }

    @OnClick(R.id.btn_login_google)
    void loginGoogle() {
        if (mILoginPresenter != null) {
            mILoginPresenter.loginGoogle();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mILoginPresenter != null) {
            mILoginPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}
