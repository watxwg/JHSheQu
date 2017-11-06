
package com.jhcms.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jhcms.common.widget.CustomViewpager;
import com.jhcms.common.widget.ListViewForScrollView;
import com.jhcms.waimaiV3.R;
@SuppressLint("ValidFragment")
public class MallProuductDetailsFragment extends Fragment {
private ListViewForScrollView listview;
    private CustomViewpager vp;

    public MallProuductDetailsFragment(CustomViewpager vp) {
        this.vp = vp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mall_prouduct_details, null);
        vp.setObjectForPosition(view,0);
        inintview(view);
        return view;
    }

    private void inintview(View view) {
        listview= (ListViewForScrollView) view.findViewById(R.id.listview);
        listview.setAdapter(new apter());
        listview.setFocusable(false);
    }
class  apter extends BaseAdapter{

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholde viewholde=null;
        if(convertView==null){
            viewholde=new viewholde();
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.item_mall_prouductdetail_adapter,null);
            viewholde.image= (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewholde);
        }else {
            viewholde= (apter.viewholde) convertView.getTag();
        }
        return convertView;
    }

    class viewholde{
        private ImageView image;
    }
}
}
