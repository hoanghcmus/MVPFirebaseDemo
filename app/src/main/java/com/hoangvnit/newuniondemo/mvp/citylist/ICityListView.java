package com.hoangvnit.newuniondemo.mvp.citylist;

import com.hoangvnit.newuniondemo.base.IBaseView;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public interface ICityListView extends IBaseView {
    void setListOrganization(BaseAdapter<Organization, OrganizationViewHolder> mCityListFirebaseAdapter);

    void scrollListCityToPosition(int position);
}
