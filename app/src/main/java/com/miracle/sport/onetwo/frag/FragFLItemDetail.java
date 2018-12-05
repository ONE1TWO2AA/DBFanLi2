package com.miracle.sport.onetwo.frag;

import android.content.Intent;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miracle.R;
import com.miracle.base.GOTO;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.databinding.FragmentFlItemDetailBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.activity.SimpleWebCommentActivity;
import com.miracle.sport.home.adapter.FBFLListAdapter;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.onetwo.inter.CallBackListener;
import com.miracle.sport.onetwo.util.RandUtils;
import com.miracle.sport.onetwo.view.MImgView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/5.
 *
 * 列表 1
 */

public class FragFLItemDetail extends HandleFragment<FragmentFlItemDetailBinding> {
//    public CpListItemAdapter mAdapter;
    public static final int MSG_WHAT_KEY_TITLE = 1;
    public static final String ARG_BUNDLE_KEY_TITLE = "ARG_BUNDLE_KEY_TITLE";
    public static final String ARG_BUNDLE_KEY_FLITEM = "ARG_BUNDLE_KEY_FLITEM";

    public String reqKey = "1";
    public ZPageLoadCallback callBack;

    private com.youth.banner.Banner banner;
    public FBFLListAdapter mAdapter;
    Football item;

    public CallBackListener callBackListener;
    public CallBackListener getCallBackListener() {
        return callBackListener;
    }

    public FragFLItemDetail() {
        super();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_fl_item_detail;
    }

    @Override
    public void initView() {
        Log.i("TAG", "initView: xxxxxxxxxxx 2");
//        mAdapter = new FBFLListAdapter(mContext);
//        mAdapter.addHeaderView(initBanner());
//        mAdapter.setOnItemClickListener(this);

        if(getArguments() == null)
           return;

        if(!getArguments().containsKey(ARG_BUNDLE_KEY_FLITEM))
            return;

        String contentTitle = getArguments().getString(ARG_BUNDLE_KEY_TITLE, "抢劵");
        setTitle(contentTitle);

        Object obj = getArguments().get(ARG_BUNDLE_KEY_FLITEM);
        if(obj instanceof  Football){
            item = (Football) obj;
        }
        setContentData();

//        DividerItemDecoration div = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//        div.setDrawable(getResources().getDrawable(R.drawable.recycle_divier_shape));
        //FootNewsPostActivity
//        binding.tvCategoryTitle.setText(R.string.main_title_1);

//        initCallback();
//        loadData();
    }

    private void setContentData(){
        MImgView iv1_1 =  binding.iv11;
        iv1_1.setEnableOffset(false);

        binding.tvTitle.setText(item.getTitle());
        String thumb = item.getThumb();
        if(TextUtils.isEmpty(thumb)){
//            helper.setGone(R.id.iv1_1, false);
        }else{
            GlideApp.with(mContext).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into(iv1_1);
        }

        RandUtils.initFixedRan(item.getTitle().hashCode());
        binding.flItemPeople.setText(""+((int)(RandUtils.fixedRan.nextFloat() * 1000) + 500)+ "人已买");
        binding.flItemNowPrice.setText(item.getTime());
        binding.flItemSubPrice.setText(""+item.getCoupon()); //XXX 0
        BigDecimal bigDecimal = new BigDecimal("0.0");
        try{
            String orgPrice = item.getAuthor().substring(1,item.getAuthor().length());
            bigDecimal = new BigDecimal(orgPrice);
            BigDecimal sub = new BigDecimal(item.getCoupon());
            bigDecimal = bigDecimal.subtract(sub);
        }catch (Exception e){
            e.printStackTrace();
        }
        SpannableString spannableString = new SpannableString(item.getAuthor());
//        SpannableString spannableString = new SpannableString("￥" + bigDecimal.toPlainString());
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.flItemOrgPrice.setText(spannableString);

        Random random = RandUtils.random;
        Date date1 = new Date();
        Date date2 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.DAY_OF_YEAR, -random.nextInt(10));
        date1.setTime(calendar.getTimeInMillis());
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.roll(Calendar.DAY_OF_YEAR, +random.nextInt(20));
        date2.setTime(calendar.getTimeInMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ((TextView)binding.getRoot().findViewById(R.id.fl_item_sub_time)).setText(sdf.format(date1) +" - " + sdf.format(date2));

        binding.getRoot().findViewById(R.id.fl_item_detail_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    //CommonUtils.getUserId()
//                    showLoading();
                    ZClient.getService(SportService.class).toBuyYHJ(item.getId()).enqueue(new ZCallback<ZResponse>() {
                        @Override
                        protected void onSuccess(ZResponse zResponse) {
                        }

                        @Override
                        protected void onFinish(Call<ZResponse> call) {
                            super.onFinish(call);
//                            showContent();
                        }
                    });
                }
            }
        });
        randOther();

        binding.getRoot().findViewById(R.id.fl_detail_text_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SimpleWebCommentActivity.class).putExtra("id", item.getId()));
            }
        });

    }

    private void randOther(){
        RandUtils.initFixedRan(item.getTitle().hashCode());
        LinearLayout other_ll = binding.getRoot().findViewById(R.id.other_ll);
        int i = 0;
        for( ;i < other_ll.getChildCount(); i++){
            other_ll.getChildAt(i).setVisibility(RandUtils.fixedRan.nextBoolean() ? View.VISIBLE : View.GONE);
        }
    }

    public String getReqKey() {
        return reqKey;
    }

    private void initCallback() {

    }

//    private View initBanner() {
//        View header = LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, null);
//        banner = header.findViewById(R.id.banner);
//        ArrayList images = new ArrayList<>();
//        images.add(R.mipmap.b3);
//        images.add(R.mipmap.b5);
//        banner.setImages(images).setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, ImageView imageView) {
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
//            }
//        });
//        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//        banner.setDelayTime(1000 * 3);
//        banner.start();
//        return header;
//    }

    @Override
    public void initListener() {
    }

    @Override
    public void onClick(View view) {

    }

//    public void setReqKey(String reqKey) {
//        if(!this.reqKey.equals(reqKey) && mAdapter != null) {
//            mAdapter.getData().clear();
//            mAdapter.notifyDataSetChanged();
//        }
//        this.reqKey = reqKey;
//        if(callBack != null)
//            callBack.onRefresh();
//    }

//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        if (position > adapter.getData().size())
//            return;
//
//        Object o = adapter.getData().get(position);
//        if (o instanceof Football) {
//            Football pd = (Football) o;
//            if (pd.getId() != 0) {
////                Intent pdAct = new Intent(getActivity(), FootNewsPostActivity.class);
////                pdAct.putExtra(FootNewsPostActivity.EXTRA_KEY_POSTID, pd.getId());
////                startActivity(pdAct);
//                Intent pdAct = new Intent(getActivity(), SimpleWebCommentActivity.class);
//                pdAct.putExtra("id", pd.getId());
//                startActivity(pdAct);
//            }
//        }
//    }

    @Override
    public void loadData() {
//        callBack.onRefresh();
    }

    @Override
    public void onHandleMessage(Message msg) {
//        if(msg.what == MSG_WHAT_KEY_REQKEY){
//            setReqKey(msg.arg1+"");
//        }
    }

}
