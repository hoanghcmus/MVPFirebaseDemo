package com.hoangvnit.newuniondemo.base;

/**
 * Created by hoang on 10/25/16.
 */

public class BaseContract {
    public interface BasePresenter<V extends BaseView> {
        void onAttach(V view);

        void onDetach(V view);
    }

    public interface BaseView {
        void showProgress();

        void hideProgress();

    }
}
