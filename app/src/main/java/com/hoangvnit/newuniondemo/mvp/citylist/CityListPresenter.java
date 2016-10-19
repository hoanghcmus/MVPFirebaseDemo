package com.hoangvnit.newuniondemo.mvp.citylist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.common.UnionDialogManager;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public class CityListPresenter implements ICityListPresenter {

    private CityListFragment mCityListView;

    public static final String ORGANIZATION_CHILD = "organization";

    private DatabaseReference mDatabaseReference;

    private BaseAdapter<Organization, OrganizationViewHolder> mBaseAdapter;

    public CityListPresenter(CityListFragment mCityListView) {
        this.mCityListView = mCityListView;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void init() {
        if (mCityListView != null) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
            mBaseAdapter = new BaseAdapter<Organization, OrganizationViewHolder>(
                    Organization.class,
                    R.layout.item_organiztion,
                    OrganizationViewHolder.class,
                    mDatabaseReference.child(ORGANIZATION_CHILD)) {

                @Override
                protected void populateViewHolder(OrganizationViewHolder viewHolder, Organization organization, int position) {
                    viewHolder.mTxtFirst.setText(organization.getOrganizationName());
                    viewHolder.mTxtSecond.setText(organization.getPinCode() + " - " +
                            organization.getAddress() + " - " +
                            organization.getCountry() + " - " +
                            organization.getState() + " - " +
                            organization.getCity());
                    viewHolder.mTxtThird.setText(organization.getTime().toString());

                    setupClickListenerForCityItemActions(viewHolder, organization, position);
                }
            };

            mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);

                    int organizationCount = mBaseAdapter.getItemCount();
                    if (positionStart == organizationCount - 1) {
                        mCityListView.scrollListCityToPosition(positionStart);
                    }
                }
            });

            mCityListView.setListOrganization(mBaseAdapter);
        }
    }

    private void setupClickListenerForCityItemActions(OrganizationViewHolder viewHolder, final Organization organization, final int itemPosition) {
        viewHolder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityListView != null) {
                    showDialogAddOrganization(mCityListView.getContext());
                }
            }
        });

        viewHolder.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCityListView != null) {
                    showDialogEditOrganization(mCityListView.getContext(), organization, itemPosition);
                }
            }
        });

        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrganization(itemPosition);
            }
        });
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void showDialogAddOrganization(Context context) {
        UnionDialogManager.shareInstance().showAddOrganizationDialog(context, false, -1, null, new UnionDialogManager.OrganizationDialogListener() {
            @Override
            public void onCreate(Organization organization) {
                addOrganization(organization);
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

    @Override
    public void showDialogEditOrganization(Context context, Organization organization, int position) {
        UnionDialogManager.shareInstance().showAddOrganizationDialog(context, true, position, organization, new UnionDialogManager.OrganizationDialogListener() {
            @Override
            public void onCreate(Organization organization) {
                // Don't implement this callback because this dialog showed for updating
            }

            @Override
            public void onUpdate(Organization organization, int position) {
                updateOrganization(organization, position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void addOrganization(Organization organization) {
        mDatabaseReference.child(ORGANIZATION_CHILD).push().setValue(organization);
    }

    @Override
    public void updateOrganization(Organization organization, int position) {
        int organizationCount = mBaseAdapter.getItemCount();
        if (position < organizationCount) {
            String organizationID = mBaseAdapter.getRef(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).setValue(organization);
        }
    }

    @Override
    public void deleteOrganization(int position) {
        if (mCityListView != null) mCityListView.showProgress();
        int organizationCount = mBaseAdapter.getItemCount();
        if (position < organizationCount) {
            String organizationID = mBaseAdapter.getRef(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (mCityListView != null) mCityListView.hideProgress();
                }
            });
        }
    }
}