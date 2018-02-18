package in.engineer4u.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import in.engineer4u.resources.DrawerList;
import in.engineer4u.yummy.R;

/**
 * Created by Rupesh Choudhary on 7/2/2015.
 */
public class YummyRecycleAdapter extends RecyclerView.Adapter<YummyRecycleAdapter.MyViewHolder> {

    List<DrawerList> data= Collections.emptyList();

    private LayoutInflater inflater;
    private Context context;
    private ClickListener clickListener;

    public YummyRecycleAdapter(Context context,List<DrawerList> data)
    {
        inflater = LayoutInflater.from(context);
        this.data=data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

       View view= inflater.inflate(R.layout.drawer_row, parent, false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        DrawerList current=data.get(i);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title= (TextView) itemView.findViewById(R.id.listText);
            icon= (ImageView) itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null)
                clickListener.itemClicked(v,getPosition());
        }
    }

    public interface ClickListener{
        public void itemClicked(View view,int position);
    }
}
