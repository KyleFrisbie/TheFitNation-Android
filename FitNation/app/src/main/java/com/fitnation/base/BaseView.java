package com.fitnation.base;

/**
 * BaseView which all fragments will be implementing
 */
public interface BaseView<T> {
    /**
     * Sets this views presenter
     * @param presenter - presenter to be used
     */
    void setPresenter(T presenter);

    /**
     * Gets the activity
     * @return - activity
     */
    BaseActivity getActivity();
}