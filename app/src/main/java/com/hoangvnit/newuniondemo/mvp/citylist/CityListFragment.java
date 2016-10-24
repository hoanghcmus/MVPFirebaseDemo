package com.hoangvnit.newuniondemo.mvp.citylist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.base.BaseFragment;
import com.hoangvnit.newuniondemo.common.EndlessScrollListener;
import com.hoangvnit.newuniondemo.common.UnionDialogManager;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter1;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;
import com.hoangvnit.newuniondemo.util.LogUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by hoang on 10/18/16.
 */

public class CityListFragment extends BaseFragment implements ICityListView {

    @Bind(R.id.list_city)
    RecyclerView mRCCityList;

    private ICityListPresenter mCityListPresenter;

    private LinearLayoutManager mLinearLayoutManager;

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
        initListView();
        if (mCityListPresenter != null) {
            mCityListPresenter.init();
        }
    }

    private void initListView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
//        mLinearLayoutManager.setReverseLayout(true);
//        mLinearLayoutManager.setStackFromEnd(true);

        mRCCityList.setLayoutManager(mLinearLayoutManager);
        mRCCityList.addOnScrollListener(new EndlessScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                showProgress();
                showLongToast("page: " + page + "    --  totalItemsCount" + totalItemsCount);
                LogUtil.e("hcmus", "page: " + page + "    --  totalItemsCount" + totalItemsCount);
                mCityListPresenter.updateLimit(page * 10);

            }
        });
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
        mRCCityList.setAdapter(mCityListFirebaseAdapter);
    }

    public void setListOrganization1(BaseAdapter1<Organization, OrganizationViewHolder> baseAdapter1) {
        mRCCityList.setAdapter(baseAdapter1);
    }

    @Override
    public void scrollListCityToPosition(int position) {
        mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    @OnClick(R.id.btn_add)
    void showDialogAddOrganization() {

        UnionDialogManager.shareInstance().showAddOrganizationDialog(getContext(), false, -1, null, new UnionDialogManager.OrganizationDialogListener() {
            @Override
            public void onCreate(Organization organization) {
                if (mCityListPresenter != null)
                    mCityListPresenter.addOrganization(organization);
            }

            @Override
            public void onUpdate(Organization organization, int position) {
                // Don't implement this callback because this dialog showed for creating
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void showDialogEditOrganization(Context context, Organization organization, int position) {
        UnionDialogManager.shareInstance().showAddOrganizationDialog(context, true, position, organization, new UnionDialogManager.OrganizationDialogListener() {
            @Override
            public void onCreate(Organization organization) {
                // Don't implement this callback because this dialog showed for updating
            }

            @Override
            public void onUpdate(Organization organization, int position) {
                if (mCityListPresenter != null)
                    mCityListPresenter.updateOrganization(organization, position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void setupClickListenerForCityItemActions(OrganizationViewHolder viewHolder, final Organization organization, final int itemPosition) {
        viewHolder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddOrganization();
            }
        });

        viewHolder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEditOrganization(getContext(), organization, itemPosition);
            }
        });

        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityListPresenter != null)
                    mCityListPresenter.deleteOrganization(itemPosition);
            }
        });
    }
}
