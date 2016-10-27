package com.hoangvnit.newuniondemo.mvp.citylist;

import android.os.Handler;
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

public class CityListPresenterImpl implements CityListContract.CityListPresenter {

    private CityListFragment mCityListView;

    public static final String ORGANIZATION_CHILD = "organization";

    private DatabaseReference mDatabaseReference;

    private BaseAdapter<Organization, OrganizationViewHolder> mBaseAdapter;

    private String mNextKey = ""; // Key of next item of last item on the list

    private static final int itemPerPage = 5; // item amount per page

    private long itemOrderCount = 0; // Order of item every time call onChildAdded

    private long firstTimeTotal = 0; // Total item loaded only once, on the first init

    private boolean isInit = true; // Flag to determine the fist init

    /**
     * Use this single value listener to load
     * a number of item ({@link CityListPresenterImpl#itemPerPage}) for every time load more
     */
    private ValueEventListener mSingleValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            LogUtil.d("SingleValueEventListener --> onDataChange()");
            List<Organization> listOrganization = new ArrayList<>();
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Organization organization = child.getValue(Organization.class);
                organization.setKey(child.getKey());
                listOrganization.add(0, organization);
            }

            if (listOrganization.size() == itemPerPage + 1) {
                mNextKey = listOrganization.get(itemPerPage).getKey();
                listOrganization.remove(itemPerPage);
            } else {
                mNextKey = "";
            }

            mBaseAdapter.setData(listOrganization);

            if (mCityListView != null) {
                mCityListView.hideProgress();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /**
     * Use this value event listener to get total item on the first time listen
     * and register listener ChildEventListener or items.
     */
    private ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (isInit) {
                LogUtil.d("ValueEventListener --> onDataChange() --> firstTimeTotal: " + firstTimeTotal);
                firstTimeTotal = dataSnapshot.getChildrenCount();
                mBaseAdapter.getQuery().addChildEventListener(mChildEventListener);
                isInit = false;
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    /**
     * Use this to listen the change of items
     */
    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            LogUtil.d(itemOrderCount + ". onChildAdded  --> totalItem = " + firstTimeTotal);
            if (itemOrderCount >= firstTimeTotal) {
                Organization organization = dataSnapshot.getValue(Organization.class);
                organization.setKey(dataSnapshot.getKey());
                mBaseAdapter.addItemTop(organization);
                if (mCityListView != null)
                    mCityListView.showShortToast(organization.getOrganizationName() + " has just been added");
            }
            itemOrderCount++;
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            LogUtil.i("ChildEventListener --> onChildChanged()");
            Organization organization = dataSnapshot.getValue(Organization.class);
            organization.setKey(dataSnapshot.getKey());
            mBaseAdapter.editItem(organization);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            LogUtil.i("ChildEventListener --> onChildRemoved()");
            Organization organization = dataSnapshot.getValue(Organization.class);
            organization.setKey(dataSnapshot.getKey());
            mBaseAdapter.removeItem(organization);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            LogUtil.i("ChildEventListener --> onChildMoved()");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            LogUtil.i("ChildEventListener --> onCancelled()");
        }
    };

    public CityListPresenterImpl(CityListFragment mCityListView) {
        this.mCityListView = mCityListView;
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

        mBaseAdapter.getQuery().orderByKey().limitToLast(itemPerPage + 1).addListenerForSingleValueEvent(mSingleValueEventListener);

        mBaseAdapter.getQuery().addValueEventListener(mValueEventListener);

        mCityListView.setListOrganization(mBaseAdapter);
    }

    @Override
    public void loadMore() {
        if (mCityListView != null)
            mCityListView.showProgress();
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtil.d("Next key to load more: " + mNextKey);
                    mBaseAdapter.getQuery().removeEventListener(mSingleValueEventListener);
                    mBaseAdapter.getQuery().orderByKey().endAt(mNextKey).limitToLast(itemPerPage + 1).addListenerForSingleValueEvent(mSingleValueEventListener);
                }
            }, 500);
        } catch (Exception e) {
            LogUtil.e("Load more exception: " + e.getMessage());
        }
    }

    @Override
    public void addOrganization(final Organization organization) {
        mDatabaseReference.child(ORGANIZATION_CHILD).push().setValue(organization)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (mCityListView != null)
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

    @Override
    public void onAttach(CityListContract.CityListView view) {

    }

    @Override
    public void onDetach(CityListContract.CityListView view) {

    }
}
