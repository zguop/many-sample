package com.example.waitou.rxjava.demos.expandablerecycler;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waitou.rxjava.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by stelian on 25/09/2016.
 */

public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context                           mContext;
    private LayoutInflater                    mInflater;
    private HashMap<ListItem, List<ListItem>> mDataSet;
    private List<ListItem>                    mItems;

    public ExpandableRecyclerAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mDataSet = new HashMap<>();
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case ListItem.TYPE_PARENT:
                v = mInflater.inflate(R.layout.row_model_parent_layout, parent, false);
                return new ParentViewHolder(v);
            case ListItem.TYPE_CHILD:
                v = mInflater.inflate(R.layout.row_model_child_layout, parent, false);
                return new ChildViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItem item = getItem(position);
        switch (item.getType()) {
            case ListItem.TYPE_CHILD:
                bindChild((ChildViewHolder) holder, item);
                break;
            case ListItem.TYPE_PARENT:
                bindParent((ParentViewHolder) holder, item);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(@NonNull HashMap<ListItem, List<ListItem>> data) {
        mDataSet.clear();
        mDataSet.putAll(data);

        // process items
        mItems.clear();

        // sort parents by name
        final List<ListItem> parents = new ArrayList<>(mDataSet.keySet());
        Collections.sort(parents, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem listItem, ListItem t1) {
                return listItem.getModel().getTitle().compareTo(t1.getModel().getTitle());
            }
        });

        mItems.addAll(parents);
    }

    private void bindParent(final ParentViewHolder holder, final ListItem item) {
        holder.title.setText(item.getModel().getTitle());

        // don't show expand icon if we don't have children
        final List<ListItem> children = mDataSet.get(item);
        if (children == null || children.isEmpty()) {
            holder.expandToggle.setVisibility(View.INVISIBLE);
        } else {
            holder.expandToggle.setVisibility(View.VISIBLE);

            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleDetails(item, holder);
                }
            });
        }

    }

    private void toggleDetails(ListItem item, ParentViewHolder holder) {
        final List<ListItem> children = mDataSet.get(item);

        final int accentColor = Utils.getAccentColor(mContext);
        final int textColor = Utils.getSecondaryColor(mContext);

        // get the correct position
        int position = holder.getLayoutPosition();

        // check if the next items are children of the clicked item
        int nextPosition = position + 1;

        if (nextPosition == mItems.size()) {
            //  expand
            expandParentRow(holder, accentColor);

            // it's the last holder - safe to add children
            mItems.addAll(children);
            notifyItemRangeInserted(nextPosition, children.size());

        } else {
            // get next item and check it's type
            final ListItem nextItem = mItems.get(nextPosition);

            if (nextItem.getType() == ListItem.TYPE_CHILD) {
                //  collapse
                collapseParentRow(holder, textColor);

                // we need to remove children
                for (int i = 0; i < children.size(); i++) {
                    // we don't need to increment nextPosition because the list gets shorter !
                    mItems.remove(nextPosition);
                }


                notifyItemRangeRemoved(position + 1, children.size());

            } else {
                //  expand
                expandParentRow(holder, accentColor);

                // we need to add children
                int count = nextPosition;
                for (ListItem child : children) {
                    mItems.add(count, child);
                    count++;
                }

                notifyItemRangeInserted(nextPosition, count - nextPosition);
            }

        }
    }

    private void collapseParentRow(ParentViewHolder holder, int textColor) {
        holder.title.setTextColor(textColor);
        holder.expandToggle.setImageResource(R.drawable.ic_expand_more);
        holder.expandToggle.getDrawable().mutate().setColorFilter(null);
    }

    private void expandParentRow(ParentViewHolder holder, int accentColor) {
        holder.title.setTextColor(accentColor);
        holder.expandToggle.setImageResource(R.drawable.ic_expand_less);
        holder.expandToggle.getDrawable().mutate().setColorFilter(accentColor, PorterDuff.Mode.SRC_ATOP);
    }

    private void bindChild(ChildViewHolder holder, ListItem item) {
        holder.title.setText(item.getModel().getTitle());
    }

    private ListItem getItem(int position) {
        return mItems.get(position);
    }


    class ChildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.expand_toggle)
        ImageView expandToggle;

        View rootView;

        public ParentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            rootView = itemView;
        }
    }
}
