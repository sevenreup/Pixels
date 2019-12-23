package com.example.pixels.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.pixels.models.GalleryModel;
import com.example.pixels.models.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ImagePicker {
    private Context context;

    private Boolean allLoaded = false;
    private int startingRow = 0;
    private int rowsToLoad = 0;

    public ImagePicker(Context context) {
        this.context = context;
    }

    public List<Image> fetchGalleryImages(int rowsPerLoad) {
        LinkedList<Image> galleryImageUrls = new LinkedList<Image>();
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null , null, orderBy + " DESC");
        if(cursor != null && !allLoaded) {
            int totalRows = cursor.getCount();
            allLoaded = rowsToLoad == totalRows;
            Log.i("allLoaded", allLoaded.toString());

            if (rowsToLoad < rowsPerLoad) {
                rowsToLoad = rowsPerLoad;
            }

            for (int i = startingRow; i < rowsToLoad; i++) {
                cursor.moveToPosition(i);
                int dataColumnIndex  = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int dataColumnIndex1 = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                Image imageItem =
                        new Image(cursor.getInt(dataColumnIndex1), 0, cursor.getString(dataColumnIndex));

                galleryImageUrls.add(imageItem);
            }
            Log.i("TotalGallerySize", totalRows + "");
            Log.i("GalleryStart", startingRow + "");
            Log.i("GalleryEnd", rowsToLoad + "");

            startingRow = rowsToLoad;

            if (rowsPerLoad > totalRows || rowsToLoad >= totalRows)
                rowsToLoad = totalRows;
            else {
                if (totalRows - rowsToLoad <= rowsPerLoad)
                    rowsToLoad = totalRows;
                else
                    rowsToLoad += rowsPerLoad;
            }
            cursor.close();
        }
        return galleryImageUrls;
    }

    public List<GalleryModel.ImageFolder> getAllAlbums() {

        List<GalleryModel.ImageFolder> imageFolders = new ArrayList<>();
        List<String> folderPathList = new ArrayList<>();

        Uri provider = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};

        Cursor cursor = context.getContentResolver().query(provider, projection, null, null, null);

        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                GalleryModel.ImageFolder folder =  new GalleryModel.ImageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String foldern = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String dataPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String folderPath = dataPath.substring(0, dataPath.lastIndexOf(foldern+"/"));
                folderPath = folderPath+foldern+"/";
                if (!folderPathList.contains(folderPath)) {
                    folderPathList.add(folderPath);

                    folder.setPath(folderPath);
                    folder.setFirstPic(dataPath);
                    folder.setFolderName(foldern);
                    imageFolders.add(folder);
                    Log.e("XANX", foldern);
                } else {
                    Log.e("XAV", "her");
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFolders;
    }

    public int getGalleySize () {
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, "$orderBy DESC"
        );

        int rows = cursor.getCount();
        cursor.close();
        return rows;
    }

    public interface ImagePickerListener {
        void singleSelect(Image imageItem);
        void multiSelect(List<Image> imageList);
    }
}
