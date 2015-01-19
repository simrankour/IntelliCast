package ig.intellicast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ig.intellicast.R;
import ig.intellicast.utils.ImageDownloaderTask;

/**
 * Created by Simranjit Kour on 6/1/15.
 */
public class CustomImageListAdapter extends BaseAdapter {

    private ArrayList listData;
    Context context1;
    private LayoutInflater layoutInflater;

    public CustomImageListAdapter(Context context, ArrayList listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        context1 = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_image_thumbnail_layout, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView_thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder.imageView != null) {
            new ImageDownloaderTask(holder.imageView).execute(listData.get(position).toString());
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
