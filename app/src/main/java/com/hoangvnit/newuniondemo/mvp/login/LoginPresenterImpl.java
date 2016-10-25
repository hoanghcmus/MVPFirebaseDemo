package com.hoangvnit.newuniondemo.mvp.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.base.BaseFragment;
import com.hoangvnit.newuniondemo.setting.FRAGMENT_ID;
import com.hoangvnit.newuniondemo.util.LogUtil;

/**
 * Created by hoang on 10/18/16.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {

    private LoginFragment mILoginView;

    public LoginPresenterImpl(LoginFragment loginView) {
        mILoginView = loginView;
    }

    @Override
    public void loginGoogle() {
        if (mILoginView != null) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mILoginView.getGoogleApiClient());
            mILoginView.startActivityForResult(signInIntent, mILoginView.getRcSignInCode());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            LogUtil.e("Google Sign In failed.");
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        if (mILoginView != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mILoginView.getFirebaseAuth().signInWithCredential(credential).addOnCompleteListener(mILoginView.getActivity(),
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                mILoginView.showLongToast(mILoginView.getStr(R.string.msg_firebase_auth_failed));
                            } else {
                                BaseFragment cityListFragment = mILoginView.getFragment(FRAGMENT_ID.CITY_LIST_FRAGMENT);
                                mILoginView.replaceFragment(cityListFragment, mILoginView.getContainerID(), false, FRAGMENT_ID.CITY_LIST_FRAGMENT.getKey());
                            }
                        }
                    });
        }
    }

    @Override
    public void onAttach(LoginContract.LoginView view) {

    }

    @Override
    public void onDetach(LoginContract.LoginView view) {

    }
}
