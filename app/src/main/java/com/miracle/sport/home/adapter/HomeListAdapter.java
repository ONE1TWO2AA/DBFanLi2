package com.miracle.sport.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.TimeUtils;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.onetwo.view.MImgView;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class HomeListAdapter extends RecyclerViewAdapter<Football> {
    private Context context;
    public Map<Integer,WeakReference<MImgView>> allImgView = new HashMap();
    public Rect rc;

    class MRequestListener implements RequestListener {
        public WeakReference<MImgView> miv;

        public MRequestListener(WeakReference<MImgView> miv) {
            this.miv = miv;
        }

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            Log.d(TAG, "onLoadFailed() called with: e = [" + e + "], model = [" + model + "], target = [" + target + "], isFirstResource = [" + isFirstResource + "]");
            if(miv != null && miv.get() != null && rc != null)
                miv.get().setEnableOffset(false);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            Log.d(TAG, "onResourceReady() called with: resource = [" + resource + "], model = [" + model + "], target = [" + target + "], dataSource = [" + dataSource + "], isFirstResource = [" + isFirstResource + "]");
            if(miv != null && miv.get() != null && rc != null){
                miv.get().setEnableOffset(true);
                miv.get().updateProgress(rc);
            }
            return false;
        }
    }

    public HomeListAdapter(Context context) {
        super(R.layout.item_home);
        this.context = context;
    }

    public void resetParallaxImgView(Rect rc){
        this.rc = rc;
        for(Map.Entry<Integer,WeakReference<MImgView>> e : allImgView.entrySet()){
            WeakReference<MImgView> pr = e.getValue();
            MImgView m = pr.get();
            if(m == null){
                pr.clear();
//                    Log.d("TAG", "onScrolled:  remove()");
                allImgView.remove(e.getKey());
                continue;
            }
            m.updateProgress(rc);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Football item) {
        ImageView iv1_1 =  helper.getView(R.id.iv1_1);
        MImgView iv1_2 =  helper.getView(R.id.iv1_2);
        MImgView iv2_2 =  helper.getView(R.id.iv2_2);
        MImgView iv1 =  helper.getView(R.id.iv1);
        MImgView iv2 =  helper.getView(R.id.iv2);
        MImgView iv3 =  helper.getView(R.id.iv3);
        WeakReference wiv1_2 = addToPR(iv1_2);
        WeakReference wiv2_2 = addToPR(iv2_2);
        WeakReference wiv1 =  addToPR(iv1);
        WeakReference wiv2 =  addToPR(iv2);
        WeakReference wiv3 =  addToPR(iv3);
        iv1_2.setEnableOffset(false);
        iv2_2.setEnableOffset(false);
        iv1.setEnableOffset(false);
        iv2.setEnableOffset(false);
        iv3.setEnableOffset(false);

        helper.setText(R.id.tvTitle, item.getTitle());
//        try {
//            Long longTime = TimeUtils.stringToLong(item.getTime(),"yyyy-MM-dd HH:mm:ss");
//            helper.setText(R.id.tvTime, TimeUtils.getShortTime(longTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        helper.setText(R.id.tvTime, "");
//        helper.setText(R.id.tvAuthor,CommonUtils.getAppName(context));
        helper.setText(R.id.im_comment_num ,item.getComment_num()+"");
        helper.setText(R.id.im_click_num ,item.getClick_num()+"");
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if(null == item.getImages()){
            if(TextUtils.isEmpty(thumb)){

                helper.setGone(R.id.iv1_1, false);
            }else{
                GlideApp.with(context).load(thumb)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into(iv1_2);
                helper.setGone(R.id.iv1_1, true);
            }
            helper.setGone(R.id.iv1_2, false);
            helper.setGone(R.id.iv2_2, false);
            helper.setGone(R.id.iv1, false);
            helper.setGone(R.id.iv2, false);
            helper.setGone(R.id.iv3, false);
        }else {
            if (1 == item.getImages().length) {
                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }
                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into(iv1_1);
                helper.setGone(R.id.iv1_1, true);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else if (2 == item.getImages().length) {
                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }
                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .listener(new MRequestListener(wiv1_2))
                        .into(iv1_2);
                GlideApp.with(context).load(item.getImages()[1])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .listener(new MRequestListener(wiv2_2))
                        .into(iv2_2);
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, true);
                helper.setGone(R.id.iv2_2, true);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else if (3 <= item.getImages().length) {

                String urlLoad = "";
                if (!TextUtils.isEmpty(thumb)) {
                    urlLoad = thumb;
                } else {
                    urlLoad = item.getImages()[0];
                }

                GlideApp.with(context).load(urlLoad)
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .listener(new MRequestListener(wiv1))
                        .into(iv1);
                GlideApp.with(context).load(item.getImages()[1])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .listener(new MRequestListener(wiv2))
                        .into(iv2);
                GlideApp.with(context).load(item.getImages()[2])
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .listener(new MRequestListener(wiv3))
                        .into(iv3);
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, true);
                helper.setGone(R.id.iv2, true);
                helper.setGone(R.id.iv3, true);
            } else if (0 == item.getImages().length && !TextUtils.isEmpty(item.getThumb())) {
                GlideApp.with(context).load(item.getThumb())
                        .placeholder(R.mipmap.defaule_img)
                        .error(R.mipmap.empty)
                        .into(iv1_1);
                helper.setGone(R.id.iv1_1, true);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            } else {
                helper.setGone(R.id.iv1_1, false);
                helper.setGone(R.id.iv1_2, false);
                helper.setGone(R.id.iv2_2, false);
                helper.setGone(R.id.iv1, false);
                helper.setGone(R.id.iv2, false);
                helper.setGone(R.id.iv3, false);
            }
        }
    }

    private WeakReference addToPR(MImgView miv) {
        if(!allImgView.containsKey(miv.hashCode())){
            WeakReference pr = new WeakReference(miv);
            pr.enqueue();
            allImgView.put(miv.hashCode(), pr);
            return pr;
        }else {
            return allImgView.get(miv.hashCode());
        }
    }
}
