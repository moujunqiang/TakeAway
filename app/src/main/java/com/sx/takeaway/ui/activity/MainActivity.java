package com.sx.takeaway.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sx.takeaway.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mMainBottomeSwitcherContainer = (LinearLayout) findViewById(R.id.main_bottome_switcher_container);
        setListener();
    }

    /**
     * 打造一个通用的底部导航
     */
    private void setListener() {
        //获取底部导航容器中的子View的数量
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        //遍历循环
        for (int i = 0; i < childCount; i++) {
            //拿到每一个子View对象，不包括孙子对象
            FrameLayout childAt = (FrameLayout) mMainBottomeSwitcherContainer.getChildAt(i);
            //为每一个子view设置点击事件
            childAt.setOnClickListener(onClickListener);
        }

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //改变ui
            //拿到点击的子View的索引
            int index = mMainBottomeSwitcherContainer.indexOfChild(v);
            changeUI(index);
            //切换Fragment
        }
    };

    /**
     * 改变index对应的孩子的状态，包括这个孩子中可能有的多个子控件的状态。
     * 改变其他孩子的状态，包括其他孩子下层孩子的状态
     * 点击一次之后状态设置(enable = false)
     *
     * @param index
     */
    private void changeUI(int index) {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //判断是否点击过了
            if (i == index) {
                //点击过了,不可以再点击
                mMainBottomeSwitcherContainer.getChildAt(i).setEnabled(false);
                setEnable(mMainBottomeSwitcherContainer.getChildAt(i), false);
            } else {
                mMainBottomeSwitcherContainer.getChildAt(i).setEnabled(true);
                setEnable(mMainBottomeSwitcherContainer.getChildAt(i), true);

            }
        }
    }

    /**
     * 递归判断每个item中的状态并作出改变
     * 由于我们处理一个通用的代码，那么Item可能会有很多层，所以我们需要使用递归
     *
     * @param item
     * @param b
     */
    private void setEnable(View item, boolean b) {
        item.setEnabled(b);
        //如果条目是ViewGroup，需要递归改变内部子View的状态
        if (item instanceof ViewGroup) {
            int childCount = ((ViewGroup) item).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnable(((ViewGroup) item).getChildAt(i), b);
            }
        }
    }


}
