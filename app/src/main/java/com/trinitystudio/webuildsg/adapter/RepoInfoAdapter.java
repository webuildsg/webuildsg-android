package com.trinitystudio.webuildsg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trinitystudio.core.listener.OnItemClickListener;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.model.repos.RepoSingleModel;

import java.util.ArrayList;

/**
 * Created by Licco on 5/1/16.
 */
public class RepoInfoAdapter extends RecyclerView.Adapter<RepoInfoAdapter.ViewHolder> {

    private ArrayList<RepoSingleModel> repoInfoModels;
    private OnItemClickListener onItemClickListener;

    public RepoInfoAdapter() {
        repoInfoModels = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public void setData(ArrayList<RepoSingleModel> info)
    {
        repoInfoModels = info;
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        RepoSingleModel e = repoInfoModels.get(position);
        viewHolder.tvTitle.setText(String.format(viewHolder.itemView.getContext().getString(R.string.repo_list_title), e.getName(), e.getLanguage()));
        viewHolder.tvDate.setText(String.format(viewHolder.itemView.getContext().getString(R.string.updated_on), e.getFormatted_time()));
    }

    @Override
    public int getItemCount() {
        return repoInfoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);

            View rippleView = itemView.findViewById(R.id.ripple_view);

            rippleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null)
                    {
                        if(getAdapterPosition() >= 0)
                            onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
