package com.anonymous.anonymous.Chat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anonymous.anonymous.Chat.Model.ChatMessage;
import com.anonymous.anonymous.R;

import java.util.List;

/**
 * Created by timmy on 2017/12/31.
 */

public class ChatBoxAdapter extends RecyclerView.Adapter<ChatBoxAdapter.ViewHolder>{
    private List<ChatMessage> mDataset;
    private Context mContext;
    private static final String TAG = "ChatBoxAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mLastUser;
        public TextView mLastMessage;
        public TextView mLastTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mLastUser = itemView.findViewById(R.id.last_user);
            mLastMessage = itemView.findViewById(R.id.last_message);
            mLastTime = itemView.findViewById(R.id.last_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.w(TAG, getAdapterPosition()+"");
        }
    }

    private void deleteItem(int adapterPosition) {
        mDataset.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public ChatBoxAdapter(Context context, List<ChatMessage> mDataset) {
        this.mContext = context;
        this.mDataset = mDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage message = mDataset.get(position);
        holder.mLastMessage.setText("Test Message");
        holder.mLastUser.setText("Test User");
        holder.mLastTime.setText("Test time");

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
