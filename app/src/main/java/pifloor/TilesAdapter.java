package pifloor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.dift.ui.SwipeToAction;

public class TilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> items;


    /**
     * References to the views for each data item
     **/
    public class TileViewHolder extends SwipeToAction.ViewHolder<String> {
        TextView titleView;

        TileViewHolder(View v) {
            super(v);
            titleView = v.findViewById(R.id.title);
        }
    }

    /**
     * Constructor
     **/
    public TilesAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new TileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String item = items.get(position);
        TileViewHolder vh = (TileViewHolder) holder;
        vh.titleView.setText(item);
        vh.data = item;
    }
}