package com.yumtaufik.gitser.adapter.custom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yumtaufik.gitser.fragment.FollowersFragment;
import com.yumtaufik.gitser.fragment.FollowingFragment;

public class DetailProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final int numbOftabs;

    public DetailProfilePagerAdapter(@NonNull FragmentManager fm, int numbOftabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numbOftabs = numbOftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FollowingFragment();

            case 1:
                return new FollowersFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numbOftabs;
    }
}
