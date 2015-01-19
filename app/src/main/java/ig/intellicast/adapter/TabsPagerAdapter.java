package ig.intellicast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ig.intellicast.fragment.FragmentImages;
import ig.intellicast.fragment.FragmentVideos;

/**
 * Created by Simranjit Kour on 9/1/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Fragment fragmentImages;
    private Fragment fragmentVideos;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                if (fragmentImages == null) {
                    fragmentImages = new FragmentImages();
                }
                return fragmentImages;
            case 1:
                if (fragmentVideos == null) {
                    fragmentVideos = new FragmentVideos();
                }
                return fragmentVideos;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
