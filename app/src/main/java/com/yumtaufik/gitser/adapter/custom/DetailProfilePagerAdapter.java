package com.yumtaufik.gitser.adapter.custom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yumtaufik.gitser.fragment.FollowersFragment;
import com.yumtaufik.gitser.fragment.FollowingFragment;

public class DetailProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final int numbOfTabs;
    String username;

    public DetailProfilePagerAdapter(@NonNull FragmentManager fm, int numbOfTabs, String username) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numbOfTabs = numbOfTabs;
        this.username = username;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FollowingFragment.newInstance(username);

            case 1:
                return FollowersFragment.newInstance(username);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
