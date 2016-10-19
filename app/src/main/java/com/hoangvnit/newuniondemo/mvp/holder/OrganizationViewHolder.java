package com.hoangvnit.newuniondemo.mvp.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hoangvnit.newuniondemo.R;

import butterknife.Bind;

/**
 * Created by hoang on 10/18/16.
 */

public class OrganizationViewHolder extends BaseViewHolder {
    @Bind(R.id.txt_first)
    public TextView mTxtFirst;

    @Bind(R.id.txt_second)
    public TextView mTxtSecond;

    @Bind(R.id.txt_third)
    public TextView mTxtThird;

    @Bind(R.id.btn_add)
    public Button mBtnAdd;

    @Bind(R.id.btn_edit)
    public Button mBtnEdit;

    @Bind(R.id.btn_delete)
    public Button mBtnDelete;


    public OrganizationViewHolder(View itemView) {
        super(itemView);
    }
}
