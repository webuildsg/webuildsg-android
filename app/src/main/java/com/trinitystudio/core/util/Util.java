package com.trinitystudio.core.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by liccowee on 6/16/15.
 */
public class Util
{
    /**
     * @param timestamp
     * @return
     */
    public static int checkDate(long timestamp) {
        Calendar now = Calendar.getInstance();
        Calendar timeToCheck = Calendar.getInstance();
        timeToCheck.setTimeInMillis(timestamp);

        if(now.get(Calendar.YEAR) <= timeToCheck.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) < timeToCheck.get(Calendar.DAY_OF_YEAR))
        {
            return -1;
        }
        else if(now.get(Calendar.YEAR) >= timeToCheck.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) > timeToCheck.get(Calendar.DAY_OF_YEAR))
        {
            return 1;
        }
        else return 0;
    }

    public static LayoutInflater getLayoutInflater(Context context)
    {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static void increaseClickBound(View v, Resources res)
    {
        Rect rect = new Rect();
        v.getHitRect(rect);
        float a = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, res.getDisplayMetrics());
        rect.left -= a;
        rect.right += a;
        rect.top -= a;
        rect.bottom += a;
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void toast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        toast.show();
    }

    public static void toastShort(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static float getDisplayDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static long getUnixTimestamp()
    {
        return (int)(System.currentTimeMillis() / 1000L);
    }

    public static void tintDrawable(final Drawable drawable, int color)
    {
        Drawable background = drawable;
        background.mutate();
        final Drawable drawableC = DrawableCompat.wrap(background);
        if (background != null) {
            DrawableCompat.setTint(drawableC, color);
        }
    }

    public static void outputBundle(Bundle bundle)
    {
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            System.out.println("Bundle key : " + String.format("%s=%s", key, value.toString()));
        }
    }

    public static String random(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public static Bitmap getBitmapFromPath(String path){
        Bitmap imgthumBitmap=null;
        try
        {
            final int THUMBNAIL_SIZE = 256;

            FileInputStream fis = new FileInputStream(path);
            imgthumBitmap = BitmapFactory.decodeStream(fis);

            imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream();
            imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytearroutstream);
        }
        catch(Exception ex) {

        }
        return imgthumBitmap;
    }

    public static void hideKeyboard(Context c, View v)
    {
        InputMethodManager inputManager = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static String get12hourFormatted(int hourOfDay, int min)
    {
        String day = "am";
        if(hourOfDay >= 12 && hourOfDay % 12 != 0)
            day = "pm";
        int hours = hourOfDay % 12;
        return String.format("%02d:%02d%s", hours, min, day);
    }

    public static String getDateFormatted(Calendar calendar)
    {
        return String.format("%s/%s/%s", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }
}
