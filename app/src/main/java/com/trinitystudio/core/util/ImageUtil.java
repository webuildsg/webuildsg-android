package com.trinitystudio.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.trinitystudio.core.GlobalConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    
	/**
	 * Resize and crop image, create file and display thumbnail on page
	 * @param String
	 */
	public static boolean processBitmapForUpload(Context ctx, final ImageView ivThumbnail, String inputImagePath, String outputImagePath)
	{
        File f = new File(inputImagePath);
        ExifInterface input_exif = null, output_exif = null;
        
        try
        {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_SIZE = GlobalConstant.ATTACH_PHOTO_SIZE_WIDTH;

            //Find the correct scale value. It should be the power of 2.
            float width_tmp = o.outWidth, height_tmp=o.outHeight;
            float scale = 1f;
            if(width_tmp > REQUIRED_SIZE)
            {
                scale = REQUIRED_SIZE / width_tmp;
            }
            width_tmp = width_tmp * scale;
            height_tmp = height_tmp * scale;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            //o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            //Calculate rotation
            int rotation = 0;
    		try {
    			input_exif = new ExifInterface(inputImagePath);
    			int orientation = input_exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
    			
    			if (orientation== ExifInterface.ORIENTATION_ROTATE_90) rotation = 90;
    			else if (orientation== ExifInterface.ORIENTATION_ROTATE_180) rotation = 180;
    			else if (orientation== ExifInterface.ORIENTATION_ROTATE_270) rotation = 270;
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
            // recreate the new Bitmap
            Bitmap thumbnail = resizeImage(bitmap, (int)width_tmp, (int)height_tmp, rotation);
    		File oldFile = new File(outputImagePath);
    		if (!oldFile.getParentFile().exists()) oldFile.getParentFile().mkdirs();
    		if (oldFile.exists()) oldFile.delete();
            
    		try
    		{
    			FileOutputStream stream = new FileOutputStream(outputImagePath);
    			thumbnail.compress(CompressFormat.JPEG, 60, stream);
    			if (ivThumbnail!=null)
        		{
    				ivThumbnail.setImageBitmap(thumbnail);
        		}
    			
    			// write exif to output image
    			try
    			{
    				if(input_exif != null)
    				{
    					output_exif = new ExifInterface(outputImagePath);
                        if(input_exif.getAttribute(ExifInterface.TAG_DATETIME) != null)
    					    output_exif.setAttribute(ExifInterface.TAG_DATETIME, input_exif.getAttribute(ExifInterface.TAG_DATETIME));
                        if(input_exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) != null)
    					    output_exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, input_exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
                        if(input_exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) != null)
    					    output_exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, input_exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
    					
    					output_exif.saveAttributes();
    				}
    			}
    			catch(Exception e)
    			{
    				e.printStackTrace();
    			}
    			
    			return true;
    		}
    		catch (FileNotFoundException e)
    		{
    			Log.e("thumbnail", "FileNotFoundException");
    		}
        }
        catch (FileNotFoundException e)
        {
        	Toast.makeText(ctx, "Your picture format is not recognized.", Toast.LENGTH_LONG).show();
        }

        return false;
	}
	

    /**
     * Resize, rotate, crop image
     * @param thumbnail
     * @param newWidth
     * @param newHeight
     * @param cropWidth
     * @param cropHeight
     * @param rotateDegree
     * @return
     */
    public static Bitmap resizeImage(Bitmap thumbnail, int newWidth, int newHeight, int rotateDegree)
    {
        int width = thumbnail.getWidth();
        int height = thumbnail.getHeight();
        
//        boolean isPortrait = true;
       
        // calculate the scale
        float scale;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        
        if ( widthScale>heightScale )
        {
            scale = widthScale;
        }
        else
        {
            scale = heightScale;
        }
        
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scale, scale);
        
        // rotate the Bitmap
        matrix.postRotate(rotateDegree);
 
        // recreate the new Bitmap
        Bitmap resizedThumbnail = Bitmap.createBitmap(thumbnail, 0, 0, width, height, matrix, true);
        
        int scaledWidth = resizedThumbnail.getWidth();
        int scaledHeight = resizedThumbnail.getHeight();

        Bitmap croppedThumbnail = Bitmap.createBitmap(resizedThumbnail, (scaledWidth - newWidth) / 2, (scaledHeight - newHeight) / 2, newWidth, newHeight, null, true);
        
        return croppedThumbnail;
    }
    
}