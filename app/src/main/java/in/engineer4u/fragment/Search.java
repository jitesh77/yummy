package in.engineer4u.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import in.engineer4u.resources.GlobalClass;
import in.engineer4u.yummy.R;
import in.engineer4u.yummy.SearchResult;

/**
 * Created by Rupesh Choudhary on 6/30/2015.
 */
public class Search extends Fragment {

    Button rest,cafe,bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        final GlobalClass global=(GlobalClass) getActivity().getApplicationContext();
        rest= (Button) rootView.findViewById(R.id.restaurant);
        cafe= (Button) rootView.findViewById(R.id.cafe);
        bar= (Button) rootView.findViewById(R.id.bar);


        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternet())
                {
                Intent intent=new Intent(getActivity().getApplicationContext(), SearchResult.class);
                intent.putExtra("type","restaurant");
                startActivity(intent);}
                else
                    Toast.makeText(getActivity(),"No Internet Acess",Toast.LENGTH_LONG).show();
            }
        });

        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternet())
                {
                Intent intent=new Intent(getActivity().getApplicationContext(), SearchResult.class);
                intent.putExtra("type", "cafe");
                startActivity(intent);}
                else
                    Toast.makeText(getActivity(),"No Internet Acess",Toast.LENGTH_LONG).show();
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInternet())
                {
                Intent intent=new Intent(getActivity().getApplicationContext(), SearchResult.class);
                intent.putExtra("type", "bar");
                startActivity(intent);}
                else
                    Toast.makeText(getActivity(),"No Internet Acess",Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private boolean isInternet()
    {
         boolean flag;
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            flag=true;

        } else {
            flag=false;
        }
        return flag;
    }
}
