package com.example.pixels.vewmodels;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pixels.Util.Const;
import com.example.pixels.Util.GalleryClasses;
import com.example.pixels.Util.UploadClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GalleryViewModel extends ViewModel {
    public MutableLiveData<List<GalleryClasses.ImageItem>> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<GalleryClasses.ImageFolder>> mutableLiveFolders = new MutableLiveData<>();

    public MutableLiveData<String> selectedImage = new MutableLiveData<>();
    public MutableLiveData<Boolean> openSelector = new MutableLiveData<>();
    public MutableLiveData<Const.UPLOAD> currentPage = new MutableLiveData<>();
    public MutableLiveData<Const.UPBTMSELECTION> bottom_Selected = new MutableLiveData<>();
    public MutableLiveData<Boolean> editMode = new MutableLiveData<>();

    public MutableLiveData<UploadClasses.Post> postInEdit = new MutableLiveData<>();

    private Boolean allLoaded = false;
    private int startingRow = 0;
    private int rowsToLoad = 0;

    public GalleryViewModel() {
        mutableLiveData.setValue(new ArrayList<>());
        postInEdit.setValue(new UploadClasses.Post());
        editMode.setValue(false);
    }

    // Image selection listener
    public void setImage(String image) {
        selectedImage.setValue(image);
    }
    public void openImageSelector(Boolean open) {
        openSelector.setValue(open);
    }
    // Fetching images
    public void getImagesFromGalley(Context context, int pageSize) {
        mutableLiveData.setValue(fetchGalleryImages(context, pageSize));
    }
    public void getFolders(Context context) {
        mutableLiveFolders.setValue(getAllAlbums(context));
    }
    public int getGalleySize (Context context) {
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
    private List<GalleryClasses.ImageItem> fetchGalleryImages(Context context, int rowsPerLoad) {
        LinkedList<GalleryClasses.ImageItem> galleryImageUrls = new LinkedList<GalleryClasses.ImageItem>();
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
                GalleryClasses.ImageItem imageItem =
                        new GalleryClasses.ImageItem(cursor.getInt(dataColumnIndex1), cursor.getString(dataColumnIndex));

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
    private List<GalleryClasses.ImageFolder> getAllAlbums(Context context) {

        List<GalleryClasses.ImageFolder> imageFolders = new ArrayList<>();
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
                GalleryClasses.ImageFolder folder =  new GalleryClasses.ImageFolder();
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

//    private void setS
}
