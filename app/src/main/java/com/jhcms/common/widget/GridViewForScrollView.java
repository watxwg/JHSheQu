package com.jhcms.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**  
 * @ClassName: GridViewForScrollView  
 */
public class GridViewForScrollView extends GridView {
	
	public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);   
    }   

    public GridViewForScrollView(Context context) {
        this(context,null);   
    }   

    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);   
    }   

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }  

}
