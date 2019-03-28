   package com.lzy.imagepicker;

   import android.app.Activity;
   import android.content.Intent;
   import android.graphics.Bitmap;
   import android.graphics.BitmapFactory;
   import android.os.Bundle;
   import android.support.v7.app.AppCompatActivity;
   import android.view.KeyEvent;
   import android.view.View;
   import android.widget.TextView;

   import com.bumptech.glide.Glide;
   import com.lzy.imagepicker.ui.ImageGridActivity;


   import uk.co.senab.photoview.PhotoView;

   public class DetailImgActivity extends AppCompatActivity {
       public static final int DETAILIMG_REQUEST = 3331;
       public static final int DEl_RESULT = 3332;
       private PhotoView ImgView;
       private View ll_base_back;
       private TextView tv_base_title,tv_base_righthandle;
       public static void Initialization(Activity activity, String img, String title, int requestCode){
           Intent intent=new Intent(activity, DetailImgActivity.class);
           intent.putExtra("img_url",img);
           intent.putExtra("title",title);
           activity.startActivityForResult(intent, requestCode);
           activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
       }
       @Override
       protected void onCreate(Bundle savedInstanceState) {
//           useImmerseStatusBar(true,R.color.base_bg_gray);
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_detail_img);
           tv_base_righthandle= (TextView) findViewById(R.id.tv_base_righthandle);
           tv_base_righthandle.setVisibility(View.VISIBLE);
           tv_base_righthandle.setText("删除");
           tv_base_title= (TextView) findViewById(R.id.tv_base_title);
           tv_base_righthandle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   setResult(DEl_RESULT);
                   finish();
                   overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
               }
           });
           tv_base_title.setText("图片详情");
           ImgView= (PhotoView) findViewById(R.id.ImgView);
           ll_base_back= findViewById(R.id.ll_base_back);
           ll_base_back.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   finish();
                   overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
               }
           });
               Glide.with(this).load(getIntent().getStringExtra("img_url")).into(ImgView);
       }

       @Override
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_BACK )
           {
               finish();
               overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
           }
           return false;
       }

       @Override
       protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);

       }


   }
