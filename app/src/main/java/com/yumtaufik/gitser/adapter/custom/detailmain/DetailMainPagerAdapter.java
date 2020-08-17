package com.yumtaufik.gitser.adapter.custom.detailmain;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yumtaufik.gitser.fragment.detailmain.DetailMainFollowersFragment;
import com.yumtaufik.gitser.fragment.detailmain.DetailMainFollowingFragment;
import com.yumtaufik.gitser.fragment.detailmain.DetailMainReposFragment;
import com.yumtaufik.gitser.fragment.detailprofile.DetailProfileFollowersFragment;
import com.yumtaufik.gitser.fragment.detailprofile.DetailProfileFollowingFragment;

public class DetailMainPagerAdapter extends FragmentStatePagerAdapter {

    private final int numbOfTabs;
    String username;

    public DetailMainPagerAdapter(@NonNull FragmentManager fm, int numbOfTabs, String username) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numbOfTabs = numbOfTabs;
        this.username = username;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DetailMainFollowingFragment.newInstance(username);

            case 1:
                return DetailMainFollowersFragment.newInstance(username);

            case 2:
                return DetailMainReposFragment.newInstance(username);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
