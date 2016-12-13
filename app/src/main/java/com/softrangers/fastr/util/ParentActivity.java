package com.softrangers.fastr.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eduard Albu on 12/13/16, 12, 2016
 * for project FastR
 * email eduard.albu@gmail.com
 */

public class ParentActivity extends AppCompatActivity {

    @IdRes private int container;

    protected void setContainer(@IdRes int container) {
        this.container = container;
    }

    public void addFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getName());
        transaction.commit();
    }

    protected void addFirstFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(container, fragment);
        transaction.commit();
    }
}
