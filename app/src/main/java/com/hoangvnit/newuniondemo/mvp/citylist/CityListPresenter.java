package com.hoangvnit.newuniondemo.mvp.citylist;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter1;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;

/**
 * Created by hoang on 10/18/16.
 */

public class CityListPresenter implements ICityListPresenter {

    private CityListFragment mCityListView;

    public static final String ORGANIZATION_CHILD = "organization";

    private DatabaseReference mDatabaseReference;

//    private BaseAdapter<Organization, OrganizationViewHolder> mBaseAdapter;

    public CityListPresenter(CityListFragment mCityListView) {
        this.mCityListView = mCityListView;
    }

    @Override
    public void onCreate() {
    }

    private BaseAdapter1<Organization, OrganizationViewHolder> mBaseAdapter1;

    @Override
    public void init() {
//        if (mCityListView != null) {
//            mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//            mBaseAdapter = new BaseAdapter<Organization, OrganizationViewHolder>(
//                    Organization.class,
//                    R.layout.item_organiztion,
//                    OrganizationViewHolder.class,
//                    mDatabaseReference.child(ORGANIZATION_CHILD)) {
//
//                @Override
//                protected void populateViewHolder(OrganizationViewHolder viewHolder, Organization organization, int position) {
//                    viewHolder.mTxtFirst.setText(organization.getOrganizationName());
//                    viewHolder.mTxtSecond.setText(organization.getPinCode() + " - " +
//                            organization.getAddress() + " - " +
//                            organization.getCountry() + " - " +
//                            organization.getState() + " - " +
//                            organization.getCity());
//                    viewHolder.mTxtThird.setText(organization.getTime().toString());
//
//                    mCityListView.setupClickListenerForCityItemActions(viewHolder, organization, position);
//                }
//            };
//
//            mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                @Override
//                public void onItemRangeInserted(int positionStart, int itemCount) {
//                    super.onItemRangeInserted(positionStart, itemCount);
//
//                    int organizationCount = mBaseAdapter.getItemCount();
//                    if (positionStart == organizationCount - 1) {
//                        mCityListView.scrollListCityToPosition(positionStart);
//                    }
//                }
//            });
//
//            mCityListView.setListOrganization(mBaseAdapter);
//        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mBaseAdapter1 = new BaseAdapter1<Organization, OrganizationViewHolder>(
                R.layout.item_organiztion,
                OrganizationViewHolder.class,
                mDatabaseReference.child(ORGANIZATION_CHILD),
                new BaseAdapter1.OnDataListener<Organization>() {
                    @Override
                    public void onChildAdded(Organization model) {
                        mBaseAdapter1.addItemTop(model);
                    }

                    @Override
                    public void onChildChanged(Organization model) {
                        mBaseAdapter1.editItem(model);
                    }

                    @Override
                    public void onChildRemoved(Organization model) {
                        mBaseAdapter1.removeItem(model);
                    }
                }) {
            @Override
            protected void registerListener(Query query, final OnDataListener onDataListener) {

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Organization organization = dataSnapshot.getValue(Organization.class);
                        organization.setKey(dataSnapshot.getKey());
                        onDataListener.onChildAdded(organization);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Organization organization = dataSnapshot.getValue(Organization.class);
                        organization.setKey(dataSnapshot.getKey());
                        onDataListener.onChildChanged(organization);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Organization organization = dataSnapshot.getValue(Organization.class);
                        organization.setKey(dataSnapshot.getKey());
                        onDataListener.onChildRemoved(organization);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            protected void populateViewHolder(OrganizationViewHolder viewHolder, Organization organization, int position) {
                viewHolder.mTxtFirst.setText(organization.getOrganizationName());
                viewHolder.mTxtSecond.setText(organization.getPinCode() + " - " +
                        organization.getAddress() + " - " +
                        organization.getCountry() + " - " +
                        organization.getState() + " - " +
                        organization.getCity());
                viewHolder.mTxtThird.setText(organization.getTime().toString());

                mCityListView.setupClickListenerForCityItemActions(viewHolder, organization, position);
            }
        };

        mCityListView.setListOrganization1(mBaseAdapter1);
    }

    @Override
    public void updateLimit(int value) {
        mBaseAdapter1.setLimit(value);
        if (mCityListView != null) {
            mCityListView.hideProgress();
        }
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
    public void addOrganization(Organization organization) {
        mDatabaseReference.child(ORGANIZATION_CHILD).push().setValue(organization);
    }

//    @Override
//    public void updateOrganization(Organization organization, int position) {
//        int organizationCount = mBaseAdapter.getItemCount();
//        if (position < organizationCount) {
//            String organizationID = mBaseAdapter.getRef(position).getKey();
//            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).setValue(organization);
//        }
//    }
//
//    @Override
//    public void deleteOrganization(int position) {
//        int organizationCount = mBaseAdapter.getItemCount();
//        if (position < organizationCount) {
//            if (mCityListView != null) mCityListView.showProgress();
//            String organizationID = mBaseAdapter.getRef(position).getKey();
//            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (mCityListView != null) mCityListView.hideProgress();
//                }
//            });
//        }
//    }


    @Override
    public void updateOrganization(Organization organization, int position) {
        int organizationCount = mBaseAdapter1.getItemCount();
        if (position < organizationCount) {
            String organizationID = mBaseAdapter1.getItem(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).setValue(organization);
        }
    }

    @Override
    public void deleteOrganization(int position) {
        int organizationCount = mBaseAdapter1.getItemCount();
        if (position < organizationCount) {
            if (mCityListView != null) mCityListView.showProgress();
            String organizationID = mBaseAdapter1.getItem(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (mCityListView != null) mCityListView.hideProgress();
                }
            });
        }
    }
}
