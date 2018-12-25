package com.lanmei.bilan.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lanmei.bilan.R;
import com.lanmei.bilan.api.CompileArticleApi;
import com.lanmei.bilan.api.PublishArticleApi;
import com.lanmei.bilan.bean.NewsJingBean;
import com.lanmei.bilan.event.CompileArticleEvent;
import com.lanmei.bilan.event.PublishArticleEvent;
import com.lanmei.bilan.helper.BGASortableNinePhotoHelper;
import com.lanmei.bilan.utils.CommonUtils;
import com.lanmei.bilan.utils.CompressPhotoUtils;
import com.xson.common.app.BaseActivity;
import com.xson.common.bean.BaseBean;
import com.xson.common.helper.BeanRequest;
import com.xson.common.helper.HttpClient;
import com.xson.common.helper.SimpleTextWatcher;
import com.xson.common.utils.StringUtils;
import com.xson.common.utils.UIHelper;
import com.xson.common.widget.CenterTitleToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 发表文章
 */
public class PublishArticleActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate {

    @InjectView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @InjectView(R.id.article_title_et)
    EditText titleEt;
    @InjectView(R.id.article_content_et)
    EditText contentEt;
    @InjectView(R.id.num_tv)
    TextView numTv;//字个数
    @InjectView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;//拖拽排序九宫格控件
    BGASortableNinePhotoHelper mPhotoHelper;

    NewsJingBean bean;
    boolean isCompile;
    List<String> list;

    @Override
    public int getContentViewId() {
        return R.layout.activity_publish_article;
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            bean = (NewsJingBean) bundle.getSerializable("bean");
        }
        isCompile = (bean != null);
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initPhotoHelper();

        setSupportActionBar(mToolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        if (isCompile) {
            actionbar.setTitle("编辑文章");
            titleEt.setText(bean.getTitle());
            contentEt.setText(bean.getContent());
            list = bean.getFile();
            if (!StringUtils.isEmpty(list)) {
                mPhotoHelper.setDate(list);
            }
        } else {
            actionbar.setTitle(R.string.publish_article);
        }
        actionbar.setHomeAsUpIndicator(R.mipmap.back);

        contentEt.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s)) {
                    numTv.setText(String.format(getString(R.string.num_500), CommonUtils.isZero));
                } else {
                    numTv.setText(String.format(getString(R.string.num_500), String.valueOf(s.length())));
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
                final String title = CommonUtils.getStringByEditText(titleEt);
                if (StringUtils.isEmpty(title)) {
                    UIHelper.ToastMessage(this, R.string.input_article_title);
                    return super.onOptionsItemSelected(item);
                }
                final String content = CommonUtils.getStringByEditText(contentEt);
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(this, R.string.input_article_content);
                    return super.onOptionsItemSelected(item);
                }
                if (mPhotoHelper.getItemCount() == 0) {
                    UIHelper.ToastMessage(this, "请选择上传的图片");
                    return super.onOptionsItemSelected(item);
                }
                if (isCompile) {//编辑 发布文章
                    final List<String> stringList = new ArrayList<>();//
                    List<String> newList = new ArrayList<>();
                    List<String> photoList = mPhotoHelper.getData();
                    int size = photoList.size();
                    for (int i = 0; i < size; i++) {
                        String s = photoList.get(i);
                        if (!StringUtils.isEmpty(s) && (s.startsWith("http"))) {
                            stringList.add(s);//原来的图片地址集合
                        } else {
                            newList.add(s);
                        }
                    }
                    if (StringUtils.isEmpty(newList)) {//没有新的图片
                        compileArticle(title, content, photoList);
                    } else {
                        new CompressPhotoUtils().CompressPhoto(this, newList, new CompressPhotoUtils.CompressCallBack() {
                            @Override
                            public void success(List<String> list) {
                                if (isFinishing()) {
                                    return;
                                }
                                if (!StringUtils.isEmpty(stringList)) {
                                    list.addAll(stringList);
                                }
                                compileArticle(title, content, list);
                            }
                        }, "5");
                    }
                } else {//新发布的文章
                    new CompressPhotoUtils().CompressPhoto(this, mPhotoHelper.getData(), new CompressPhotoUtils.CompressCallBack() {
                        @Override
                        public void success(List<String> list) {
                            if (isFinishing()) {
                                return;
                            }
                            publishArticle(title, content, list);
                        }
                    }, "4");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 新发布的文章文章
     */
    private void publishArticle(String title, String content, List<String> list) {
        HttpClient httpClient = HttpClient.newInstance(this);
        PublishArticleApi api = new PublishArticleApi();
        api.title = title;
        api.content = content;
        api.imgs = CommonUtils.getStringByList(list);
        api.uid = api.getUserId(this);
        httpClient.request(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new PublishArticleEvent());//
                UIHelper.ToastMessage(getContext(), CommonUtils.getString(response));
                onBackPressed();
            }
        });
    }

    /**
     * 编辑文章
     */
    private void compileArticle(String title, String content, List<String> list) {
        HttpClient httpClient = HttpClient.newInstance(this);
        CompileArticleApi api = new CompileArticleApi();
        api.postid = bean.getId();
        api.title = title;
        api.content = content;
        api.imgs = CommonUtils.getStringByList(list);
        api.uid = api.getUserId(this);
        httpClient.request(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (isFinishing()) {
                    return;
                }
                EventBus.getDefault().post(new CompileArticleEvent());//
                UIHelper.ToastMessage(getContext(), CommonUtils.getString(response));
                onBackPressed();
            }
        });
    }


}
