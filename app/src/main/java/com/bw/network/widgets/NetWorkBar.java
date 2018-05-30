package com.bw.network.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bw.network.R;

public class NetWorkBar extends FrameLayout{
    //默认不显示
    private boolean isShow = false;
    //标题文字
    private String netText;
    //图片的显示
    private ImageView mImgView;
    //图片
    private int imageView;
    //文字显示
    private TextView mNetTv;
    //文字颜色
    private int netTextColor;
    //显示控件
    private LinearLayout mNetLl;
    //文字大小
    private float netTextSize;

    private int backgroundColor;

    public NetWorkBar(@NonNull Context context) {
        this(context,null);
    }

    public NetWorkBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetWorkBar);
        //默认不显示
        isShow = typedArray.getBoolean(R.styleable.NetWorkBar_isShow,false);
        netText = typedArray.getString(R.styleable.NetWorkBar_netText);
        netTextColor = typedArray.getColor(R.styleable.NetWorkBar_netTextColor,getResources().getColor(R.color.colorPrimary));
        imageView = typedArray.getResourceId(R.styleable.NetWorkBar_netImgView,R.drawable.ic_launcher_foreground);
        backgroundColor = typedArray.getColor(R.styleable.NetWorkBar_backgroundColor,getResources().getColor(R.color.colorPrimary));
        //netTextSize = typedArray.getFloat(R.styleable.NetWorkBar_netTextSize,14.0f);
        netTextSize = typedArray.getDimension(R.styleable.NetWorkBar_netTextSize,14.0f);
        initView(context);
        typedArray.recycle();
    }

    private void initView(final Context context){
        View view = View.inflate(context,R.layout.layout_net_bar,this);
        mNetTv = view.findViewById(R.id.mNetTv);
        mImgView = view.findViewById(R.id.mImgView);
        mNetLl  = view.findViewById(R.id.mNetLl);

        if(isShow){
            mNetLl.setVisibility(View.VISIBLE);
        }else{
            mNetLl.setVisibility(View.GONE);
        }

        if(netText!=null){
            mNetTv.setText(netText);
        }

        if(netTextColor!=0){
            mNetTv.setTextColor(netTextColor);
        }

        if(imageView!=0){
            mImgView.setImageResource(imageView);
        }

        if(backgroundColor!=0){
            mNetLl.setBackgroundColor(backgroundColor);
        }

        if(netTextSize!=0){
            mNetTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,netTextSize);
        }
    }

    public void showNetWorkBar(boolean isShow){
        if(isShow){
            mNetLl.setVisibility(View.VISIBLE);
        }else{
            mNetLl.setVisibility(View.GONE);
        }
    }
}
