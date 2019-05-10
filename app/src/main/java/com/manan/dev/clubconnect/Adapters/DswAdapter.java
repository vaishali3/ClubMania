package com.manan.dev.clubconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.manan.dev.clubconnect.R;

import java.util.ArrayList;

public class DswAdapter extends RecyclerView.Adapter<com.manan.dev.clubconnect.Adapters.DswAdapter.ItemRowHolder> {

        private ArrayList<String> dataList;
        private Context mContext;

        public DswAdapter(Context context, ArrayList<String> dataList) {
            this.dataList = dataList;
            this.mContext = context;
        }

        @Override
        public com.manan.dev.clubconnect.Adapters.DswAdapter.ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_dsw, null);
            com.manan.dev.clubconnect.Adapters.DswAdapter.ItemRowHolder mh = new com.manan.dev.clubconnect.Adapters.DswAdapter.ItemRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(com.manan.dev.clubconnect.Adapters.DswAdapter.ItemRowHolder itemRowHolder, int i) {
            itemRowHolder.notificationText.setText(dataList.get(i));
        }
        @Override
        public int getItemCount() {
            return (null != dataList ? dataList.size() : 0);
        }

        public class ItemRowHolder extends RecyclerView.ViewHolder {

            public TextView notificationText;


            public ItemRowHolder(View view) {
                super(view);

                this.notificationText= (TextView) view.findViewById(R.id.notification_text);

            }

        }
}
