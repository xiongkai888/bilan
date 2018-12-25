package com.lanmei.bilan.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.AddDynamicApi;
import com.lanmei.bilan.event.AddDynamicEvent;
import com.lanmei.bilan.event.LocationChooseEvent;
import com.lanmei.bilan.event.TaskEvent;
import com.lanmei.bilan.helper.BGASortableNinePhotoHelper;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.CompressPhotoUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SimpleTextWatcher;
import com.xson.common.utils.IntentUtil;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 发表动态
 */
public class PublishDynamicActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.et_moment_add_content)
    EditText mContentEt;
    @InjectView(R.id.location_tv)
    TextView mLocationTv;
    @InjectView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;//拖拽排序九宫格控件
    BGASortableNinePhotoHelper mPhotoHelper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_publish_dynamic;
    }



    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        initPermission();
        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.publish_dynamic);
        actionbar.setHomeAsUpIndicator(R.mipmap.back);
        initPhotoHelper();
        EventBus.getDefault().register(this);
        mContentEt.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s+"")){
                    return;
                }
                if (s.length() == 250){
                    UIHelper.ToastMessage(PublishDynamicActivity.this,getString(R.string.limit_char));
                }
            }
        });
    }


    private void initPhotoHelper() {
        mPhotoHelper = new BGASortableNinePhotoHelper(this, mPhotosSnpl);
        // 设置拖拽排序控件的代理
        mPhotoHelper.setDelegate(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish, menu);
        return true;
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        mPhotoHelper.onClickAddNinePhotoItem(sortableNinePhotoLayout, view, position, models);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickDeleteNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotoHelper.onClickNinePhotoItem(sortableNinePhotoLayout, view, position, model, models);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoHelper.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_publish:
                //发布的时候
                final String title = mContentEt.getText().toString();
                if (StringUtils.isEmpty(title)) {
                    UIHelper.ToastMessage(this, R.string.say_something);
                    return super.onOptionsItemSelected(item);
                }
                if (mPhotoHelper.getItemCount() != 0) {
                    new CompressPhotoUtils().CompressPhoto(this, mPhotoHelper.getData(), new CompressPhotoUtils.CompressCallBack() {
                        @Override
                        public void success(List<String> list) {
                            if (isFinishing()) {
                                return;
                            }
                            ajaxHttp(title,list);
                        }
                    }, "6");
                }else {
                    ajaxHttp(title,null);
                }



                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void ajaxHttp(String title,List<String> list) {

        HttpClient httpClient = HttpClient.newInstance(this);
        AddDynamicApi api = new AddDynamicApi();
        api.title = title;
        api.uid = api.getUserId(this);
        api.file = CommonUtils.getStringArr(list);
        api.city = CommonUtils.getStringByTextView(mLocationTv);
        httpClient.request(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (PublishDynamicActivity.this.isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new TaskEvent());//调用任务界面中的方法taskEvent()
                UIHelper.ToastMessage(PublishDynamicActivity.this, response.getInfo());
                EventBus.getDefault().post(new AddDynamicEvent());
                onBackPressed();
            }
        });
    }


//    private static final int PERMISSION_LOCATION = 100;

    @OnClick(R.id.location_tv)
    public void showLocation() {
        IntentUtil.startActivity(this, SearchPositionActivity.class);
    }

//    private LocationService locationService;

//    private void initPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_LOCATION);
//            } else {
//                initBaiDu();
//            }
//        } else {
//            initBaiDu();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_LOCATION){
//            initBaiDu();
//        }
//    }

//    private void initBaiDu() {
//        // -----------location config ------------
//        locationService = new LocationService(getApplicationContext());//放在SattingApp里面有问题
//        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        //注册监听
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        locationService.start();// 定位SDK
//    }




//    /*****
//     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
//     */
//    private BDLocationListener mListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (location == null) {
//                return;
//            }
//            if (StringUtils.isEmpty(location.getCity())){
//                return;
//            }
//            mLocationTv.setText(location.getCity()+location.getDistrict()+location.getStreet()+location.getStreetNumber());
//        }
//
//        public void onConnectHotSpotMessage(String s, int i) {
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        locationService.unregisterListener(mListener);
//        locationService.stop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void locationChooseEvent(LocationChooseEvent event) {
        mLocationTv.setText(event.getAddress());
    }

}
