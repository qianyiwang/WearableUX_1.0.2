package com.example.qianyiwang.syncrc_102;

/**
 * Created by qianyiwang on 2/10/17.
 */

import android.content.Context;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends WearableListView.Adapter {
    private ArrayList<Integer> mItems;
    private final LayoutInflater mInflater;

    public ListAdapter(Context context, ArrayList<Integer> items) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int i) {
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder,
                                 int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        CircledImageView circledView = itemViewHolder.mCircledImageView;
        circledView.setImageResource(mItems.get(position));
        TextView textView = itemViewHolder.mItemTextView;
//        textView.setText(String.format("Item %d", position + 1));
        if(position==0){
            textView.setText("Climate");
        }
        else if(position==1){
            textView.setText("MCS");
        }
        else if(position==2){
            textView.setText("Dest");
        }
        else if(position==3){
            textView.setText("BT audio");
        }
        else if(position==4){
            textView.setText("Phone switch");
        }
        else if(position==5){
            textView.setText("Temp switch");
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private static class ItemViewHolder extends WearableListView.ViewHolder {
        private CircledImageView mCircledImageView;
        private TextView mItemTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCircledImageView = (CircledImageView)
                    itemView.findViewById(R.id.circle);
            mItemTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
