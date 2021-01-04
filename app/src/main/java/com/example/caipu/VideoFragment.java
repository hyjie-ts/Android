package com.example.caipu;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class VideoFragment extends Fragment {

    private static final String TAG = "--------------->";
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    public String source1 = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";

    public MyLayoutManager myLayoutManager;
    private OrientationUtils orientationUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_palyer, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        initView();
        initListener();
        return view;
    }

    private void initView() {

        myLayoutManager = new MyLayoutManager(this.getActivity(), OrientationHelper.VERTICAL, false);

        mAdapter = new VideoFragment.MyAdapter();
        mRecyclerView.setLayoutManager(myLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        myLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean isNext) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);

                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                playVideo(index);
            }
        });
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public MyAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //增加封面
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.drawable.video_icon_f);
            holder.videoPlayer.setThumbImageView(imageView);
            //增加title
            holder.videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
            //设置返回键
//            holder.videoPlayer.getBackButton().setVisibility(View.VISIBLE);
            //设置旋转
            orientationUtils = new OrientationUtils(getActivity(), holder.videoPlayer);
            //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
            holder.videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orientationUtils.resolveByClick();
                }
            });
            //是否可以滑动调整
            holder.videoPlayer.setIsTouchWiget(true);

            holder.videoPlayer.startPlayLogic();
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_thumb;
            ImageView img_play;
            StandardGSYVideoPlayer videoPlayer;
            RelativeLayout rootView;

            public ViewHolder(View itemView) {
                super(itemView);
                videoPlayer = itemView.findViewById(R.id.video_view);
            }
        }
    }
    private void releaseVideo(int index) {
        View itemView = mRecyclerView.getChildAt(index);
        final StandardGSYVideoPlayer videoPlayer = itemView.findViewById(R.id.video_view);
        videoPlayer.onVideoPause();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(int position) {
        View itemView = mRecyclerView.getChildAt(0);
        final StandardGSYVideoPlayer videoPlayer = itemView.findViewById(R.id.video_view);

        videoPlayer.setUp(source1, true, "推荐视频");

        videoPlayer.startPlayLogic();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }
}
