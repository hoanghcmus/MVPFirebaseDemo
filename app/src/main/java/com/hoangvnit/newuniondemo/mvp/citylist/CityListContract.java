package com.hoangvnit.newuniondemo.mvp.citylist;

import com.hoangvnit.newuniondemo.base.BaseContract;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/25/16.
 */

public class CityListContract {

    public interface CityListPresenter extends BaseContract.BasePresenter<CityListView> {

        void init();

        void loadMore();

        void addOrganization(Organization organization);

        void updateOrganization(Organization organization, int position);

        void deleteOrganization(int position);
    }

    public interface CityListView extends BaseContract.BaseView {

        void setListOrganization(BaseAdapter<Organization, OrganizationViewHolder> mCityListFirebaseAdapter);

        void scrollListCityToPosition(int position);
    }
}
