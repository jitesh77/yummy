package in.engineer4u.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.engineer4u.adapters.YummyRecycleAdapter;
import in.engineer4u.resources.DrawerList;
import in.engineer4u.resources.GlobalClass;
import in.engineer4u.util.ImageViewRounded;
import in.engineer4u.yummy.AboutUs;
import in.engineer4u.yummy.Feedback;
import in.engineer4u.yummy.Help;
import in.engineer4u.yummy.Profile;
import in.engineer4u.yummy.R;
import in.engineer4u.yummy.Settings;
import in.engineer4u.yummy.Wishlist;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements YummyRecycleAdapter.ClickListener{

    private RecyclerView recyclerView;
    public static final String PREF_FILE_NAME="testpref";
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";
    private View view;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private YummyRecycleAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    ImageViewRounded imageView;
    TextView name,email;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mUserLearnedDrawer=Boolean.valueOf( readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState!=null)
             mFromSavedInstanceState=true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        imageView= (ImageViewRounded) layout.findViewById(R.id.imageView);
       name= (TextView) layout.findViewById(R.id.textView4);
        email= (TextView) layout.findViewById(R.id.textView5);
        final GlobalClass global = (GlobalClass) getActivity().getApplicationContext();

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new LoadProfileImage(imageView).execute(global.getPhotoId());
           name.setText(global.getName());
            email.setText(global.getEmail());
        }


        recyclerView= (RecyclerView) layout.findViewById(R.id.drawerlist);
        adapter=new YummyRecycleAdapter(getActivity(),getdata());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;

    }

    public static List<DrawerList> getdata(){
        List<DrawerList> data=new ArrayList<DrawerList>();
        //icon and text for nav drawer
        int[] icon={R.drawable.nav_home,R.drawable.nav_profile,R.drawable.nav_fav,R.drawable.nav_setting,R.drawable.nav_help,R.drawable.nav_about,R.drawable.nav_feedback,R.drawable.nav_followus,R.drawable.nav_share,R.drawable.nav_rateus,R.drawable.nav_logout,R.drawable.nav_exit};
        String[] title={"Home","My Profile","My Wishlist","Settings","Help","About Us","Send feedback","Follow Us","Share this app","Rate Us","Sign Out","Exit"};
        for (int i=0;i<icon.length && i<title.length;i++)
        {
            DrawerList current=new DrawerList();
            current.iconId=icon[i];
            current.title=title[i];
            data.add(current);
        }
        return data;
    }


    public void setup(int fragmentID,DrawerLayout drawerLayout, Toolbar toolbar) {
        view=getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && mFromSavedInstanceState)
        {
            mDrawerLayout.openDrawer(view);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();

            }
        });

    }
    public static void saveToPreferences(Context context,String preferenceName,String preferenceValue)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }
    public static String readFromPreferences(Context context,String preferenceName,String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
        switch (position) {
            case 0:
                Toast.makeText(getActivity(),"Home",Toast.LENGTH_SHORT).show();
                break;
            case 1: startActivity(new Intent(getActivity(), Profile.class));
                break;
            case 2:  startActivity(new Intent(getActivity(), Wishlist.class));
                break;
            case 3: startActivity(new Intent(getActivity(), Settings.class));
                break;
            case 4: startActivity(new Intent(getActivity(), Help.class));
                break;
            case 5: startActivity(new Intent(getActivity(), AboutUs.class));
                break;
            case 6:  startActivity(new Intent(getActivity(), Feedback.class));
                break;
            case 7:  Toast.makeText(getActivity(),"Follow us",Toast.LENGTH_SHORT).show();
                break;
            case 8:  shareAppLink();
                break;
            case 9:  Toast.makeText(getActivity(),"Rate us",Toast.LENGTH_SHORT).show();
                break;
            case 10:  Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                break;
            case 11:  exitApp();
                break;
            default: Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void shareAppLink(){
        try
        { Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Yummy");
            String padding = "\nNow restaurants are just one click away... Try It..\n";
            padding = padding + "https://engineer4u.in";
            i.putExtra(Intent.EXTRA_TEXT, padding);
            startActivity(Intent.createChooser(i, "Share via"));
        }
        catch(Exception e)
        {
           //e.toString();
        }
    }

    private void exitApp()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Exit yummy?");
        alertDialogBuilder
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageViewRounded bmImage;

        public LoadProfileImage(ImageViewRounded bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
