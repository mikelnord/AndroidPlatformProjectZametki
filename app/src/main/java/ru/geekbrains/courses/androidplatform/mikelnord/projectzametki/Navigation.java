package ru.geekbrains.courses.androidplatform.mikelnord.projectzametki;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack, int orient) {
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(orient == Configuration.ORIENTATION_LANDSCAPE){
            fragmentTransaction.replace(R.id.note, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
}