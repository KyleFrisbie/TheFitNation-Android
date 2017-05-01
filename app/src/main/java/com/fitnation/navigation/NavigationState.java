package com.fitnation.navigation;

/**
 * Created by Ryan on 4/16/2017.
 */

public class NavigationState {
    private boolean backArrowShown;
    private String title;

    public NavigationState(boolean backArrowShown, String title) {
        this.backArrowShown = backArrowShown;
        this.title = title;
    }

    public boolean isBackArrowShown() {
        return backArrowShown;
    }

    public void setBackArrowShown(boolean backArrowShown) {
        this.backArrowShown = backArrowShown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
