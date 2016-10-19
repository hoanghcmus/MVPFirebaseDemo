package com.hoangvnit.newuniondemo.mvp.citylist;

import android.content.Context;

import com.hoangvnit.newuniondemo.base.IBasePresenter;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public interface ICityListPresenter extends IBasePresenter<ICityListView> {

    void init();

    void showDialogAddOrganization(Context context);

    void showDialogEditOrganization(Context context, Organization organization, int position);

    void addOrganization(Organization organization);

    void updateOrganization(Organization organization, int position);

    void deleteOrganization(int position);
}
