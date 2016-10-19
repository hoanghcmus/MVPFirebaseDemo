package com.hoangvnit.newuniondemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoangvnit.newuniondemo.setting.FRAGMENT_ID;

import butterknife.ButterKnife;

/**
 * Created by hoang on 10/18/16.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract int getLayoutID();

    protected abstract void initView();

    protected static BaseActivity mActivity;

    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof BaseActivity)
            mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(getLayoutID(), container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    public void showShortToast(String message) {
        if (mActivity != null)
            mActivity.showShortToast(message);
    }

    public void showLongToast(String message) {
        if (mActivity != null)
            mActivity.showLongToast(message);
    }

    public void showProgressDialog() {
        if (mActivity != null)
            mActivity.showProgressDialog();
    }

    public void hideProgressDialog() {
        if (mActivity != null)
            mActivity.hideProgressDialog();
    }

    public String getStr(int resId) {
        if (mActivity != null)
            return mActivity.getStr(resId);
        else return "";
    }

    public void attachFragment(Fragment attachedFragment, int containerID, String fragmentName) {
        if (mActivity != null)
            mActivity.attachFragment(attachedFragment, containerID, fragmentName);
    }

    public void replaceFragment(Fragment newFragment, int containerID, boolean isBackStack, String fragmentName) {
        if (mActivity != null)
            mActivity.replaceFragment(newFragment, containerID, isBackStack, fragmentName);
    }

    public Fragment getCurrentFragment() {
        return mActivity.getCurrentFragment();
    }

    public int getContainerID() {
        return mActivity.getContainerID();
    }

    public BaseFragment getFragment(FRAGMENT_ID id) {
        return mActivity.getFragment(id);
    }
}
