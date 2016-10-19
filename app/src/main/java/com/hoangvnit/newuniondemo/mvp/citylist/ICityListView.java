package com.hoangvnit.newuniondemo.mvp.citylist;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.hoangvnit.newuniondemo.base.IBaseView;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public interface ICityListView extends IBaseView {
    void setListOrganization(FirebaseRecyclerAdapter<Organization, OrganizationViewHolder> mCityListFirebaseAdapter);

    void scrollListCityToPosition(int position);
}
