package com.shinaier.laundry.snlfactory.ordermanage.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinaier.laundry.snlfactory.R;
import com.shinaier.laundry.snlfactory.ordermanage.entities.ColorSettingEntity;
import com.shinaier.laundry.snlfactory.view.PinnedHeaderExpandableListView;
import com.shinaier.laundry.snlfactory.view.WrapHeightGridView;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/3.
 */

public class ColorExpandableAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    private PositionListener listener;
    public interface PositionListener{
        void onClick(int groupPosition, int childPosition, ImageView iv);
    }

    public void setPositionListener(PositionListener listener){
        this.listener = listener;
    }
    private Context context;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;
    private List<String> groupData;
    private List<List<ColorSettingEntity>> childData;

    public ColorExpandableAdapter(List<List<ColorSettingEntity>> childData, List<String> groupData
            , Context context, PinnedHeaderExpandableListView listView){
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
        this.listView = listView;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child, null);
        }
        WrapHeightGridView gridView = (WrapHeightGridView) convertView .findViewById(R.id.check_clothes_img);
        ColorsAdapter adapter = new ColorsAdapter(context,childData.get(groupPosition));
        gridView.setAdapter(adapter);// Adapter
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              final ImageView viewById = (ImageView) adapterView.getChildAt(i).findViewById(R.id.iv_question_info_select);
                if(listener != null){
                    listener.onClick(groupPosition,i, viewById);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }

        ImageView iv = (ImageView)view.findViewById(R.id.groupIcon);

        if (isExpanded) {
            iv.setImageResource(R.drawable.btn_browser);
        }
        else{
            iv.setImageResource(R.drawable.btn_browser2);
        }

        TextView text = (TextView)view.findViewById(R.id.groupto);
        text.setText(groupData.get(groupPosition));
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.group, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition,
                                int childPosition, int alpha) {
        String groupData = this.groupData.get(groupPosition);
        ((TextView) header.findViewById(R.id.groupto)).setText(groupData);

    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition)>=0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
