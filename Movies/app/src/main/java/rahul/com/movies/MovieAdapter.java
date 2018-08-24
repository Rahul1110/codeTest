package rahul.com.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> list;
    private LayoutInflater layoutInflater;
    public MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        View vi = view;             //trying to reuse a recycled view
        ViewHolder holder = null;


        if (vi == null) {
            //The view is not a recycled one: we have to inflate
            vi = layoutInflater.inflate(R.layout.single_item, parent, false);

            holder = new ViewHolder();

            // Defined the items of UI
            holder.txtName=(TextView)vi.findViewById(R.id.txt_name);

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.txtName.setText(list.get(position).getName());


        return vi;
    }



    static class ViewHolder  {
        public TextView txtName;

    }

}
