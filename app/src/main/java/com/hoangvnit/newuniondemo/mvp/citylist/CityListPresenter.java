package com.hoangvnit.newuniondemo.mvp.citylist;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hoangvnit.newuniondemo.R;
import com.hoangvnit.newuniondemo.mvp.adapter.BaseAdapter;
import com.hoangvnit.newuniondemo.mvp.holder.OrganizationViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.Organization;
import com.hoangvnit.newuniondemo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 10/18/16.
 */

public class CityListPresenter implements ICityListPresenter {

    private CityListFragment mCityListView;

    public static final String ORGANIZATION_CHILD = "organization";

    private DatabaseReference mDatabaseReference;

    private BaseAdapter<Organization, OrganizationViewHolder> mBaseAdapter;

    private String mNextKey = "";

    ValueEventListener mSingleValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LogUtil.d("0. onDataChange");
            List<Organization> listOrganization = new ArrayList<>();
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Organization organization = child.getValue(Organization.class);
                organization.setKey(child.getKey());
                listOrganization.add(0, organization);
            }

            if (listOrganization.size() == 6) {
                mNextKey = listOrganization.get(5).getKey();
                listOrganization.remove(5);
            } else {
                mNextKey = "";
            }

            mBaseAdapter.setData(listOrganization);

            mBaseAdapter.getQuery().removeEventListener(mChildEventListener);
            mBaseAdapter.getQuery().addChildEventListener(mChildEventListener);

            if (mCityListView != null) {
                mCityListView.hideProgress();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            LogUtil.d("1. onChildAdded ");
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            LogUtil.d("2. onChildChanged");
            Organization organization = dataSnapshot.getValue(Organization.class);
            organization.setKey(dataSnapshot.getKey());
            mBaseAdapter.editItem(organization);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            LogUtil.d("3. onChildRemoved");
            Organization organization = dataSnapshot.getValue(Organization.class);
            organization.setKey(dataSnapshot.getKey());
            mBaseAdapter.removeItem(organization);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            LogUtil.d("4. onChildMoved");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            LogUtil.d("5. onCancelled");
        }
    };


    public CityListPresenter(CityListFragment mCityListView) {
        this.mCityListView = mCityListView;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void init() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mBaseAdapter = new BaseAdapter<Organization, OrganizationViewHolder>(
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

                mCityListView.setupClickListenerForCityItemActions(viewHolder, organization, position);
            }
        };

        mBaseAdapter.getQuery().orderByKey().limitToLast(6).addListenerForSingleValueEvent(mSingleValueEventListener);

        mCityListView.setListOrganization(mBaseAdapter);
    }

    @Override
    public void loadMore() {
        if (mCityListView != null)
            mCityListView.showProgress();
        LogUtil.d("Next key to load more: " + mNextKey);
        mBaseAdapter.getQuery().removeEventListener(mSingleValueEventListener);
        mBaseAdapter.getQuery().orderByKey().endAt(mNextKey).limitToLast(6).addListenerForSingleValueEvent(mSingleValueEventListener);
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
    public void addOrganization(final Organization organization) {
        final DatabaseReference databaseReference = mDatabaseReference.child(ORGANIZATION_CHILD).push();
        databaseReference.setValue(organization).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String key = databaseReference.getKey();
                organization.setKey(key);
                mBaseAdapter.addItemTop(organization);
                LogUtil.d("Key have just added: " + key);
                mCityListView.scrollListCityToPosition(0);
            }
        });
    }

    @Override
    public void updateOrganization(Organization organization, int position) {
        int organizationCount = mBaseAdapter.getItemCount();
        if (position < organizationCount) {
            String organizationID = mBaseAdapter.getItem(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).setValue(organization);
        }
    }

    @Override
    public void deleteOrganization(int position) {
        int organizationCount = mBaseAdapter.getItemCount();
        if (position < organizationCount) {
            if (mCityListView != null) mCityListView.showProgress();
            String organizationID = mBaseAdapter.getItem(position).getKey();
            mDatabaseReference.child(ORGANIZATION_CHILD).child(organizationID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (mCityListView != null) mCityListView.hideProgress();
                }
            });
        }
    }
}
