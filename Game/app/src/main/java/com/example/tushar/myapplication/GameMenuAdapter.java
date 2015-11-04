package com.example.tushar.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameMenuAdapter extends BaseAdapter {
    private List<String> mListMenu = new ArrayList<>();
    private Context mContext = null;
    private LayoutInflater inflater = null;

    public GameMenuAdapter(Context context, List<String> mListMenu) {
        this.mListMenu = mListMenu;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mListMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return  mListMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            ViewGroup viewGroup = null;
            convertView = inflater.inflate(R.layout.adapter_game_menu, viewGroup);
            holder.MenuText = (TextView) convertView.findViewById(R.id.menu_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        String menuName = mListMenu.get(position);
        holder.MenuText.setText(menuName);

        return convertView;
    }
    private static class ViewHolder{
        TextView MenuText;
    }
}
