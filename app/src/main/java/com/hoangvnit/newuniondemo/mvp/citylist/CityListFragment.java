package com.hoangvnit.newuniondemo.mvp.citylist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.base.BaseFragment;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hoang on 10/18/16.
 */

public class CityListFragment extends BaseFragment implements ICityListView {

    @Bind(R.id.list_city)
    RecyclerView mRCCityList;

    private ICityListPresenter mCityListPresenter;

    public RecyclerView getmRCCityList() {
        return mRCCityList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCityListPresenter = new CityListPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_city_list;
    }

    @Override
    protected void initView() {
        if (mCityListPresenter != null) {
            mCityListPresenter.init();
        }
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
    public void setListOrganization(FirebaseRecyclerAdapter<Organization, OrganizationViewHolder> mCityListFirebaseAdapter) {
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRCCityList.setLayoutManager(mLinearLayoutManager);
        mRCCityList.setAdapter(mCityListFirebaseAdapter);
    }

    @Override
    public void scrollListCityToPosition(int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRCCityList.getLayoutManager();
        linearLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    @OnClick(R.id.btn_add)
    void showDialogAddOrganization() {
        if (mCityListPresenter != null) {
            mCityListPresenter.showDialogAddOrganization(getContext());
        }
    }
}
