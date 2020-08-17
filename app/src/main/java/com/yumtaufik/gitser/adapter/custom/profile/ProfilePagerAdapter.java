package com.yumtaufik.gitser.adapter.custom.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yumtaufik.gitser.fragment.detailprofile.DetailProfileFollowersFragment;
import com.yumtaufik.gitser.fragment.detailprofile.DetailProfileFollowingFragment;
import com.yumtaufik.gitser.fragment.profile.ProfileFollowersFragment;
import com.yumtaufik.gitser.fragment.profile.ProfileFollowingFragment;
import com.yumtaufik.gitser.fragment.profile.ProfileReposFragment;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final int numbOfTabs;
    String username;

    public ProfilePagerAdapter(@NonNull FragmentManager fm, int numbOfTabs, String username) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numbOfTabs = numbOfTabs;
        this.username = username;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProfileFollowingFragment.newInstance(username);

            case 1:
                return ProfileFollowersFragment.newInstance(username);

            case 2:
                return ProfileReposFragment.newInstance(username);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
