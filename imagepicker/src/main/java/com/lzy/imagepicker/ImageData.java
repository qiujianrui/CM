package com.lzy.imagepicker;

import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jere on 2016/11/15.
 * 用于Intent传递数据过大 本地保存数据
 */

public class ImageData {
    public static List<ImageItem> mData=new ArrayList<>();

    public static List<ImageItem> getData(){
        return ImageData.mData;
    }

    public static void setmData(List<ImageItem> mData) {
        ImageData.mData.clear();
        ImageData.mData.addAll(mData);
    }
}
