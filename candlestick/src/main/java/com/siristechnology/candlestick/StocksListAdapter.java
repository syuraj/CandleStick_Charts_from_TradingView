package com.siristechnology.candlestick;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StocksListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private IStocksListListener stocksListListener;

    public StocksListAdapter(Context context, List<String> stocks) {
        super(context, R.layout.stock_item, stocks);

        this.context = context;
        this.stocksListListener = (IStocksListListener) context;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stock_item, parent, false);

        final String stock = this.getItem(position);

        TextView text_title = (TextView) rowView.findViewById(R.id.text_title);
        text_title.setText(stock);

        ImageView view = (ImageView) rowView.findViewById(R.id.image_action_delete);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stocksListListener.DeleteStock(stock);
            }
        });

        return rowView;
    }

    public interface IStocksListListener {
        public void DeleteStock(String stock);
    }
}
