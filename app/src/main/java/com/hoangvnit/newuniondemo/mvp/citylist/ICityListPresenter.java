package com.hoangvnit.newuniondemo.mvp.citylist;

import com.hoangvnit.newuniondemo.base.IBasePresenter;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public interface ICityListPresenter extends IBasePresenter<ICityListView> {

    void init();

    void updateLimit(int value);

    void addOrganization(Organization organization);

    void updateOrganization(Organization organization, int position);

    void deleteOrganization(int position);
}
