package irdc.ex04_24;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EX04_24 extends Activity
{
  private Button mButton1;
  private Button mButton2;
  private TextView mTextView1;
  private ImageView mImageView1;
  private int ScaleTimes;
  private int ScaleAngle;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mButton1 =(Button) findViewById(R.id.myButton1);
    mButton2 =(Button) findViewById(R.id.myButton2);
    mTextView1 = (TextView) findViewById(R.id.myTextView1);
    mImageView1 = (ImageView) findViewById(R.id.myImageView1);
    ScaleTimes = 1;
    ScaleAngle = 1;
    
    final Bitmap mySourceBmp = BitmapFactory.decodeResource(getResources(), R.drawable.hippo);
    
    final int widthOrig = mySourceBmp.getWidth(); 
    final int heightOrig = mySourceBmp.getHeight();
    
    /* 程式剛執行，載入預設的Drawable */
    mImageView1.setImageBitmap(mySourceBmp);
    
    /* 向左選轉按鈕 */
    mButton1.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        ScaleAngle--;
        if(ScaleAngle<-5)
        {
          ScaleAngle = -5;
        }
        
        /* ScaleTimes=1，維持1:1的寬高比例*/
        int newWidth = widthOrig * ScaleTimes;
        int newHeight = heightOrig * ScaleTimes;
        
        float scaleWidth = ((float) newWidth) / widthOrig; 
        float scaleHeight = ((float) newHeight) / heightOrig; 
        
        Matrix matrix = new Matrix(); 
        /* 使用Matrix.postScale設定維度 */
        matrix.postScale(scaleWidth, scaleHeight);
        
        /* 使用Matrix.postRotate方法旋轉Bitmap*/
        //matrix.postRotate(5*ScaleAngle);
        matrix.setRotate(5*ScaleAngle);
        
        /* 建立新的Bitmap物件 */ 
        Bitmap resizedBitmap = Bitmap.createBitmap(mySourceBmp, 0, 0, widthOrig, heightOrig, matrix, true);
        /**/
        BitmapDrawable myNewBitmapDrawable = new BitmapDrawable(resizedBitmap); 
        mImageView1.setImageDrawable(myNewBitmapDrawable);
        mTextView1.setText(Integer.toString(5*ScaleAngle)); 
      }      
    });
    
    /* 向右選轉按鈕 */
    mButton2.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        // TODO Auto-generated method stub
        ScaleAngle++;
        if(ScaleAngle>5)
        {
          ScaleAngle = 5;
        }
        
        /* ScaleTimes=1，維持1:1的寬高比例*/
        int newWidth = widthOrig * ScaleTimes;
        int newHeight = heightOrig * ScaleTimes;
        
        /* 計算旋轉的Matrix比例 */ 
        float scaleWidth = ((float) newWidth) / widthOrig; 
        float scaleHeight = ((float) newHeight) / heightOrig; 
        
        Matrix matrix = new Matrix(); 
        /* 使用Matrix.postScale設定維度 */ 
        matrix.postScale(scaleWidth, scaleHeight);
        
        /* 使用Matrix.postRotate方法旋轉Bitmap*/
        //matrix.postRotate(5*ScaleAngle);
        matrix.setRotate(5*ScaleAngle);

        /* 建立新的Bitmap物件 */ 
        Bitmap resizedBitmap = Bitmap.createBitmap(mySourceBmp, 0, 0, widthOrig, heightOrig, matrix, true);
        /**/
        BitmapDrawable myNewBitmapDrawable = new BitmapDrawable(resizedBitmap); 
        mImageView1.setImageDrawable(myNewBitmapDrawable);
        mTextView1.setText(Integer.toString(5*ScaleAngle));
      }
    });
  }
}