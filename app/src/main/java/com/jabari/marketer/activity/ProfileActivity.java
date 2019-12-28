package com.jabari.marketer.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jabari.marketer.R;
import com.jabari.marketer.custom.GlobalVariables;
import com.jabari.marketer.custom.PrefManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    private FloatingActionButton fab_profile,fab_my_activity,fab_register;
    private ImageView img_profile;
    private int GALLERY = 1, CAMERA = 2;
    private Uri imageUri;
    private Button edit,delete;
    private EditText sheba;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setViews();
        onclickFab();
        deleteSheba();
        editSheba(sheba.getText().toString());
        requestMultiplePermissions();

        if (checkPref()) {

            sheba.setText(GlobalVariables.sheba);
            img_profile.setImageBitmap(decodeBase64(GlobalVariables.bitmaps));
        }
       }
    private Boolean checkPref() {
        PrefManager prefManager = new PrefManager(getBaseContext());
        String sheba = "";
        String bitmap = "";
        sheba = prefManager.getSheba();
        bitmap = prefManager.getBitmap();

        if (TextUtils.isEmpty(sheba) || TextUtils.isEmpty(bitmap)) {
            return false;
        } else if (!TextUtils.isEmpty(sheba)) {
            GlobalVariables.sheba = sheba;
            GlobalVariables.bitmaps = bitmap;

        }
        return true;
    }

    private void setViews(){
        fab_my_activity = findViewById(R.id.fab_my_activity);
        fab_profile = findViewById(R.id.fab_profile);
        fab_register = findViewById(R.id.fab_register);
        img_profile = findViewById(R.id.iv_pro);
        edit = findViewById(R.id.btn_edit_sheba);
        sheba = findViewById(R.id.et_sheba);
        delete = findViewById(R.id.btn_delete_sheba);
        fab_profile.bringToFront();
        fab_my_activity.bringToFront();
        fab_register.bringToFront();

    }
    private void onclickFab(){

        fab_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_register.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_profile.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(ProfileActivity.this,MarketerActivity.class));
            }
        });
        fab_my_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab_my_activity.setBackgroundTintList(getResources().getColorStateList(R.color.green));
                fab_profile.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
    }

    public void OnclickFabImage(View view){

        showPictureDialog();
    }

    public void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(R.string.select_action));
        String[] pictureDialogItems = {
                getResources().getString(R.string.select_photo),
        getResources().getString(R.string.capture_photo)};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }}

                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void editSheba(final String text){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sheba.setText(text);
                saveSheba(text);

            }
        });
    }

    private void deleteSheba(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheba.setText("");
            }
        });
    }
    public void takePhotoFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                final Uri contentURI = data.getData();

                try {

                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle(getResources().getString(R.string.save_photo));
                    dialog.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            img_profile.setImageBitmap(drawCircularImg(bitmap));

                        }
                    });

                    dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {


            try {
                final Bitmap thumbnail;
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(getResources().getString(R.string.save_photo));
                dialog.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        img_profile.setImageBitmap(drawCircularImg(thumbnail));
                    }
                });

                dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private Bitmap drawCircularImg(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        Bitmap _bmp = Bitmap.createScaledBitmap(output, 200, 200, false);
        GlobalVariables.bitmap = _bmp;
        saveImage(encodeTobase64(_bmp));
        return _bmp;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    private void saveImage(String bitmap){

        PrefManager prefManager = new PrefManager(getBaseContext());
        prefManager.setBitmapString(bitmap);

    }

    private void saveSheba(String sheba){
        PrefManager prefManager = new PrefManager(getBaseContext());

        prefManager.setSheba(sheba);

    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

}
