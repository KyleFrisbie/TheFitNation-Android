package com.fitnation.profile;

import com.fitnation.base.BaseFragment;

import java.util.Date;


public class ProfileModel{
    Date mBirthday;
    String mUsername;
    int mHeight_inch;
    int mWeight_lbs;
    String mLifter_type;
    String[] lifter_types = {"Beginner", "Intermediate", "Advanced"};
    ProfilePresenter mPresenter;


    public ProfileModel(ProfilePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    public void updateHeight(int height){
        mHeight_inch = height;
    }

    public void updateWeight(int weight){
        mWeight_lbs = weight;
    }

    public void updateAge(Date date){
        mBirthday = date;
    }

    public void updateLifterType(String l_type){
        mLifter_type = l_type;
    }
}
