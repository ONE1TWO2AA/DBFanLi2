package com.miracle.sport.home.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.databinding.F1LotteryBindingImpl;
import com.miracle.sport.home.bean.FLItem;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.onetwo.util.RandUtils;
import com.miracle.sport.onetwo.view.MImgView;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FLListAdapter extends RecyclerViewAdapter<FLItem> {
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

    public FLListAdapter(Context context) {
        super(R.layout.item_fl);
        this.context = context;
    }

    public void resetParallaxImgView(Rect rc){
        this.rc = rc;
        Iterator<Map.Entry<Integer, WeakReference<MImgView>>> it = allImgView.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,WeakReference<MImgView>> e = it.next();
            WeakReference<MImgView> pr = e.getValue();
            MImgView m = pr.get();
            if(m == null){
                pr.clear();
                Log.d("TAG", "onScrolled:  remove()");
                it.remove();
                continue;
            }
            m.updateProgress(rc);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, FLItem item) {
        MImgView iv1_1 =  helper.getView(R.id.iv1_1);
        WeakReference wiv1_1 = addToPR(iv1_1);
        iv1_1.setEnableOffset(false);

        helper.setText(R.id.tvTitle, item.getTitle());
        String thumb = item.getThumb();
        if(TextUtils.isEmpty(thumb)){
            helper.setGone(R.id.iv1_1, false);
        }else{
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .listener(new MRequestListener(wiv1_1))
                    .into(iv1_1);
            helper.setGone(R.id.iv1_1, true);
        }

        helper.setText(R.id.fl_item_people, ""+((int)(RandUtils.random.nextFloat() * 1000) + 500)+ "人已买");
        helper.setText(R.id.fl_item_now_price, item.getAdd_time());
        helper.setText(R.id.fl_item_sub_price, "XX "+item.getCoupon());
        java.math.BigDecimal bigDecimal = new BigDecimal("0.0");
        try{
            String orgPrice = item.getAuthor().substring(1,item.getAuthor().length());
            bigDecimal = new BigDecimal(orgPrice);
            BigDecimal sub = new BigDecimal(item.getCoupon());
            bigDecimal = bigDecimal.subtract(sub);
        }catch (Exception e){
            e.printStackTrace();
        }
//        SpannableString spannableString = new SpannableString("￥" + bigDecimal.toPlainString());
        SpannableString spannableString = new SpannableString(item.getAuthor());
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        helper.setText(R.id.fl_item_org_price, spannableString);
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
