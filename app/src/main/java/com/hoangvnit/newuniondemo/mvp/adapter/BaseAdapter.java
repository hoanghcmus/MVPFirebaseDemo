package com.hoangvnit.newuniondemo.mvp.adapter;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.hoangvnit.newuniondemo.mvp.holder.BaseViewHolder;
import com.hoangvnit.newuniondemo.mvp.model.BaseModel;

/**
 * Created by hoang on 10/19/16.
 */

public abstract class BaseAdapter<M extends BaseModel, H extends BaseViewHolder> extends FirebaseRecyclerAdapter<M, H> {

    public BaseAdapter(Class<M> modelClass, int modelLayout, Class<H> viewHolderClass, DatabaseReference ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }
}
