package pifloor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.dift.ui.SwipeToAction;
import pifloor.utils.VirtualGrid;

public class TilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private VirtualGrid gridItems;


    /** References to the views for each data item **/
    public class TileViewHolder extends SwipeToAction.ViewHolder<String> {
        public TextView titleView;

        public TileViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
        }
    }

    /** Constructor **/
    public TilesAdapter(VirtualGrid gridItems) {
        this.gridItems = gridItems;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return gridItems.count();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new TileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String item = gridItems.getAtIndex(position);
        TileViewHolder vh = (TileViewHolder) holder;
        vh.titleView.setText(item);
        vh.data = item;
    }
}