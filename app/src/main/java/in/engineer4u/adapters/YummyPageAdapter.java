package in.engineer4u.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.engineer4u.fragment.Search;
import in.engineer4u.yummy.R;

/**
 * Created by Rupesh Choudhary on 6/30/2015.
 */
public class YummyPageAdapter extends FragmentStatePagerAdapter
{
    int icons[]={R.drawable.ic_tab_search};
    private Context c;

    public YummyPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        c=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Search();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return c.getResources().getStringArray(R.array.tabs)[position];
    }

     public Drawable getIcon(int position)
    {
        Drawable drawable=c.getResources().getDrawable(icons[position]);
        return  drawable;
    }
}
