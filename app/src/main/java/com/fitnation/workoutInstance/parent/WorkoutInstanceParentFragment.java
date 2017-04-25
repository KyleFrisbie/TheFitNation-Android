package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceParentFragment extends BaseFragment implements WorkoutInstanceParentContract.View {

    public WorkoutInstanceParentFragment() {
    }

    public static WorkoutInstanceParentFragment newInstance(Context context){
        WorkoutInstanceParentFragment fragment = new WorkoutInstanceParentFragment();
        fragment.setPresenter(new WorkoutInstanceParentPresenter(context, fragment));
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_instance_fragment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void setPresenter(WorkoutInstanceParentContract.Presenter presenter) {

    }

    @Override
    public BaseActivity getBaseActivity() {
        return null;
    }
}
