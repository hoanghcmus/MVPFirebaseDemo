package com.hoangvnit.newuniondemo;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hoangvnit.newuniondemo.base.BaseActivity;
import com.hoangvnit.newuniondemo.base.BaseFragment;
import com.hoangvnit.newuniondemo.setting.FRAGMENT_ID;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            BaseFragment loginFragment = getFragment(FRAGMENT_ID.LOGIN_FRAGMENT);
            attachFragment(loginFragment, getContainerID(), FRAGMENT_ID.LOGIN_FRAGMENT.getKey());
        } else {
            BaseFragment cityListFragment = getFragment(FRAGMENT_ID.CITY_LIST_FRAGMENT);
            attachFragment(cityListFragment, getContainerID(), FRAGMENT_ID.CITY_LIST_FRAGMENT.getKey());
        }
    }
}
