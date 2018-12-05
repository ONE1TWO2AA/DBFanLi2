package com.miracle.sport.onetwo.frag;

import android.content.Intent;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miracle.R;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.FragChoseTypeBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.onetwo.act.OneFragActivity;
import com.miracle.sport.onetwo.view.MArcMenu;

import java.util.List;

public class FragChoseType extends HandleFragment<FragChoseTypeBinding> {
    @Override
    public void onHandleMessage(Message msg) {

    }

    @Override
    public int getLayout() {
        return R.layout.frag_chose_type;
    }

    @Override
    public void initView() {
        showTitle();
        setTitle("分类选择");
        binding.marcm1.setOnMaxChildeChangeLis(new MArcMenu.onMaxChildeChangeLis() {
            @Override
            public void onchange(int newLen) {
                ((FrameLayout.LayoutParams)binding.getRoot().findViewById(R.id.iv1).getLayoutParams()).setMargins(newLen,newLen,newLen,newLen);
            }
        });
        loadData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadData() {
        super.loadData();
        ZCallback zCallback = new ZCallback<ZResponse<List<ChannerlKey>>>(){
            @Override
            protected void onSuccess(ZResponse<List<ChannerlKey>> zResponse) {
                int i = 0;
                for(ChannerlKey item : zResponse.getData()){
                    //排除 ‘推荐’
                    if(1 != Integer.parseInt(item.getId())){
                        addChildV(item.getName(),Integer.parseInt(item.getId()),item.getPic());
                        i++;
                    }
                }
            }
        };

        zCallback.setCachKey("homepage_fishtype");
        RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getSearchKeys(), zCallback);
    }

    private void addChildV(final String str, final int key, String picUrl){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frag_hs2_item,null);
        ImageView iv = view.findViewById(R.id.main_farg_hs1_iv);
        ((TextView)view.findViewById(R.id.main_farg_hs1_tv1)).setText(str);
        GlideApp.with(mContext).load(picUrl).into(iv);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openArticDetail(key, str);
            }
        });
        binding.marcm1.addView(view);
    }

    private void openArticDetail(int key, String str) {
        Intent i = new Intent(getActivity(), OneFragActivity.class);
        i.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, FragFLItemList.class);
        Message msg = new Message();
        msg.what = FragFLItemList.MSG_WHAT_KEY_REQKEY;
        msg.arg1 = key;
        i.putExtra(OneFragActivity.EXTRA_KEY_MSG, msg);
        i.putExtra(OneFragActivity.EXTRA_KEY_ACT_TITLE, ""+str);
        startActivity(i);
    }
}
