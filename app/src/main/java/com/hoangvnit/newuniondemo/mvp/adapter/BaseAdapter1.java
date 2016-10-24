package com.hoangvnit.newuniondemo.mvp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.hoangvnit.newuniondemo.mvp.holder.BaseViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.BaseModel;
import com.hoangvnit.newuniondemo.util.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoang on 10/24/16.
 */

public abstract class BaseAdapter1<M extends BaseModel, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    Class<VH> mViewHolderClass;

    int mModelLayout;

    List<M> mListData = new ArrayList<>();

    private Query mQuery;

    public void setData(List<M> listData) {
        mListData.addAll(listData);
        notifyDataSetChanged();
    }

    public void addItem(M item) {
        mListData.add(item);
        notifyDataSetChanged();
    }

    public void addItemTop(M item) {
        mListData.add(0, item);
        notifyDataSetChanged();
    }

    public void editItem(M item) {
        for (int i = 0; i < mListData.size(); i++) {
            if (mListData.get(i).getKey().equals(item.getKey())) {
                mListData.set(i, item);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void removeItem(M item) {
        for (int i = 0; i < mListData.size(); i++) {
            M data = mListData.get(i);
            if (data.getKey().equals(item.getKey())) {
                mListData.remove(data);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public BaseAdapter1(int modelLayout, Class<VH> viewHolderClass, DatabaseReference ref, OnDataListener onDataListener) {
        this(modelLayout, viewHolderClass, (Query) ref, onDataListener);
    }

    public BaseAdapter1(int modelLayout, Class<VH> viewHolderClass, Query query, OnDataListener onDataListener) {
        mViewHolderClass = viewHolderClass;
        mModelLayout = modelLayout;
        mQuery = query;
        registerListener(query, onDataListener);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        M model = getItem(position);
        populateViewHolder(holder, model, position);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public M getItem(int position) {
        return mListData.get(position);
    }

    public void setLimit(int value) {
        LogUtil.e("hcmus", "New Limit = " + value);
        mQuery.limitToLast(value);
    }

    public interface OnDataListener<M extends BaseModel> {
        void onChildAdded(M model);

        void onChildChanged(M model);

        void onChildRemoved(M model);
    }

    abstract protected void registerListener(Query query, OnDataListener onDataListener);

    abstract protected void populateViewHolder(VH viewHolder, M model, int position);

}
