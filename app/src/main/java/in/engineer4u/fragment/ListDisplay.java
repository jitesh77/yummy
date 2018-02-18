package in.engineer4u.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.engineer4u.yummy.R;
import in.engineer4u.yummy.SearchResult;

/**
 * Created by Rupesh Choudhary on 6/30/2015.
 */
public class ListDisplay extends Fragment {

    private TextView textView;
    private String type="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listdisplay, container, false);

        textView= (TextView) rootView.findViewById(R.id.textView3);
        type=((SearchResult)getActivity()).getType();
        textView.setText(type);

        return rootView;
    }
}
