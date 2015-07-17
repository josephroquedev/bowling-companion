package ca.josephroque.bowlingcompanion.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.josephroque.bowlingcompanion.R;

/**
 * Created by Joseph Roque on 15-03-13.
 * <p/>
 * Manages names of bowlers or leagues/events and their associated averages for a ListView. Offers
 * a callback interface {@link NameAverageAdapter.NameAverageEventHandler} to handle interaction
 * events.
 */
public class NameAverageAdapter
        extends RecyclerView.Adapter<NameAverageAdapter.NameAverageViewHolder>
        implements View.OnClickListener
{

    /** Identifies output from this class in Logcat. */
    @SuppressWarnings("unused")
    private static final String TAG = "NameAverageAdapter";

    /** Indicates data represents bowlers. */
    public static final byte DATA_BOWLERS = 0;
    /** Indicates data represents leagues and events. */
    public static final byte DATA_LEAGUES_EVENTS = 1;

    /** Instance of handler for callback on user action. */
    private NameAverageEventHandler mEventHandler;

    /** List of names which will be displayed. */
    private List<String> mListNames;
    /** List of averages which will be displayed, in an order relative to mListNames. */
    private List<Short> mListAverages;

    /** Type of data being represented by this object. */
    private byte mDataType;

    /**
     * Subclass of RecyclerView.ViewHolder to manage view which will display an image,
     * and text to the user.
     */
    public static final class NameAverageViewHolder extends RecyclerView.ViewHolder
    {
        /** Displays an image representing the type of data in the row. */
        private ImageView mImageViewType;
        /** Displays the name of the data in the row. */
        private TextView mTextViewName;
        /** Displays the average of the data in the row. */
        private TextView mTextViewAverage;

        /**
         * Calls super constructor and gets instances of ImageView and TextView objects
         * for member variables from itemLayoutView.
         *
         * @param itemLayoutView layout view containing views to display data
         */
        private NameAverageViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            mImageViewType = (ImageView) itemLayoutView.findViewById(R.id.iv_nameavg_type);
            mTextViewName = (TextView) itemLayoutView.findViewById(R.id.tv_nameavg_name);
            mTextViewAverage = (TextView) itemLayoutView.findViewById(R.id.tv_nameavg_average);
        }
    }

    /**
     * Sets member variables to parameters.
     *
     * @param handler handles on click/long click events on views
     * @param listNames list of names to be displayed in RecyclerView
     * @param listAverages list of averages, relative to listNames to be displayed
     * @param dataType type of data being managed by this object
     */
    public NameAverageAdapter(NameAverageEventHandler handler,
                              List<String> listNames,
                              List<Short> listAverages,
                              byte dataType)
    {
        mEventHandler = handler;
        mListNames = listNames;
        mListAverages = listAverages;
        mDataType = dataType;
    }

    @Override
    public NameAverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_name_average, parent, false);
        return new NameAverageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NameAverageViewHolder holder, final int position)
    {
        //Sets text/images depending on data type
        switch (mDataType)
        {
            case DATA_BOWLERS:
                holder.mTextViewName.setText(mListNames.get(position));
                holder.mImageViewType.setImageResource(R.drawable.ic_person_black_24dp);
                break;
            case DATA_LEAGUES_EVENTS:
                holder.mTextViewName.setText(mListNames.get(position).substring(1));
                holder.mImageViewType.setImageResource(
                        mListNames.get(position).startsWith("L")
                                ? R.drawable.ic_l_black_24dp
                                : R.drawable.ic_e_black_24dp);
                break;
            default: throw new IllegalStateException("invalid mDataType: " + mDataType);
        }
        holder.mTextViewAverage.setText("Avg: "
                + String.valueOf(mListAverages.get(position)));

        //Sets actions on click/touch events
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        //Calls relevant event handler method
        mEventHandler.onNAItemClick(mEventHandler.getNAViewPositionInRecyclerView(v));
    }

    @Override
    public int getItemCount()
    {
        return mListNames.size();
    }

    /**
     * Provides methods to implement functionality when items
     * in the RecyclerView are interacted with.
     */
    public interface NameAverageEventHandler
    {

        /**
         * Called when an item in the RecyclerView is clicked.
         *
         * @param position position of the item in the list
         */
        void onNAItemClick(final int position);

        /**
         * Should be used to return RecyclerView#getChildPosition(v) on the
         * recycler view which uses this adapter.
         *
         * @param v the view to get the position of
         * @return position of v in the parent RecyclerView
         */
        int getNAViewPositionInRecyclerView(View v);
    }
}
