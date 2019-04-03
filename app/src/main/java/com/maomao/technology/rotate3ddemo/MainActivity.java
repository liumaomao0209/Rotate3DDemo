package com.maomao.technology.rotate3ddemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends Activity {
    boolean isDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        final ImageView card = findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用post方法取card的宽高
                card.post(new Runnable() {
                    @Override
                    public void run() {
                        //取card中心点
                        final float centerX = card.getWidth() / 2f;
                        final float centerY = card.getHeight() / 2f;
                        // 构建3D旋转动画对象，旋转角度为0到90度
                        final Rotate3dAnimation rotation = new Rotate3dAnimation(MainActivity.this, 0, 90, centerX, centerY,
                                0f, false);
                        // 动画持续时间500毫秒
                        rotation.setDuration(500);
                        // 动画完成后保持完成的状态
                        rotation.setFillAfter(true);
                        rotation.setInterpolator(new AccelerateInterpolator());
                        card.startAnimation(rotation);
                        //监听器  翻转到90度的时候 卡面图片改变 然后将卡牌从270度翻转到360度刚好转回来
                        //这里注意不是90-180度,因为90-180翻转过来的图片是左右相反的镜像图
                        rotation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                //正反面判断
                                if (isDark) {
                                    isDark = false;
                                } else {
                                    isDark = true;
                                }
                                //点正面切换背面,反之亦然
                                if (isDark) {
                                    Glide.with(MainActivity.this).load(R.drawable.light).into(card);
                                } else {
                                    Glide.with(MainActivity.this).load(R.drawable.dark).into(card);
                                }
                                //270度翻转到360度
                                final Rotate3dAnimation rotation = new Rotate3dAnimation(MainActivity.this, 270, 360, centerX, centerY,
                                        0f, true);
                                rotation.setDuration(500);
                                // 动画完成后保持完成的状态
                                rotation.setFillAfter(false);
                                card.startAnimation(rotation);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });

                    }
                });
            }
        });
    }
}
