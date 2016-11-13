package cse110.liveasy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.content.Intent;
import android.provider.MediaStore;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.ImageView;


import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View.OnClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Text;

public class Questionaire extends AppCompatActivity{

    Boolean contactIsOpen = false;
    Boolean aboutMeIsOpen = false;
    Boolean preferenceIsOpen = false;
    Boolean petPeevesIsOpen = false;
    Boolean allergiesIsOpen = false;

    private Button mTakePhoto;
    private ImageView mImageView;
    private static final String TAG = "upload";
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        TextView eContactText = (TextView)findViewById(R.id.eContactText);
        eContactText.setText("+ Emergency Contact Info");

        TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
        aboutMeText.setText("+ About Me");

        TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
        preferencesText.setText("+ Preferences");

        TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
        petPeevesText.setText("+ Pet Peeves");

        TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
        allergiesText.setText("+ Allergies");




        LinearLayout contactLayout = (LinearLayout)findViewById(R.id.contact_layout);
        contactLayout.setVisibility(LinearLayout.GONE);

        LinearLayout aboutMeLayout = (LinearLayout)findViewById(R.id.about_me_layout);
        aboutMeLayout.setVisibility(LinearLayout.GONE);

        RelativeLayout checkboxes = (RelativeLayout)findViewById(R.id.questionnaire_checkboxes);
        checkboxes.setVisibility(RelativeLayout.GONE);

        LinearLayout petPeevesLayout = (LinearLayout)findViewById(R.id.pet_peeve_layout);
        petPeevesLayout.setVisibility(LinearLayout.GONE);

        LinearLayout allergiesLayout = (LinearLayout)findViewById(R.id.allergies_layout);
        allergiesLayout.setVisibility(LinearLayout.GONE);



        mTakePhoto = (Button) findViewById(R.id.uploadPhotoBtn);
        mImageView = (ImageView) findViewById(R.id.selfie_thumbnail);

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.uploadPhotoBtn:
                        takePhoto();
                        break;
                }
            }
        });


    }

    public void toggleContact(View view){
        closeAll("contact");
        LinearLayout contactLayout = (LinearLayout)findViewById(R.id.contact_layout);
        if(contactIsOpen){
            TextView eContactText = (TextView)findViewById(R.id.eContactText);
            eContactText.setText("+ Emergency Contact Info");
            contactLayout.setVisibility(LinearLayout.GONE);
            contactIsOpen = false;
        }
        else{
            TextView eContactText = (TextView)findViewById(R.id.eContactText);
            eContactText.setText("- Emergency Contact Info");

            contactLayout.setVisibility(LinearLayout.VISIBLE);
            contactIsOpen = true;
        }
    }

    public void toggleAboutMe(View view){
        closeAll("about_me");
        LinearLayout aboutMeLayout = (LinearLayout)findViewById(R.id.about_me_layout);
        if(aboutMeIsOpen){
            TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
            aboutMeText.setText("+ About Me");
            aboutMeLayout.setVisibility(LinearLayout.GONE);
            aboutMeIsOpen = false;
        }
        else{
            TextView aboutMeText = (TextView)findViewById(R.id.about_me_text);
            aboutMeText.setText("- About Me");

            aboutMeLayout.setVisibility(LinearLayout.VISIBLE);
            aboutMeIsOpen = true;
        }
    }

    public void togglePreferences(View view){
        closeAll("preferences");
        RelativeLayout preferencesLayout = (RelativeLayout) findViewById(R.id.questionnaire_checkboxes);
        if(preferenceIsOpen){
            TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
            preferencesText.setText("+ Preferences");
            preferencesLayout.setVisibility(LinearLayout.GONE);
            preferenceIsOpen = false;
        }
        else{
            TextView preferencesText = (TextView)findViewById(R.id.preferences_text);
            preferencesText.setText("- Preferences");

            preferencesLayout.setVisibility(LinearLayout.VISIBLE);
            preferenceIsOpen = true;
        }
    }

    public void togglePetPeeves(View view){
        closeAll("pet_peeves");
        LinearLayout petPeevesLayout = (LinearLayout) findViewById(R.id.pet_peeve_layout);
        if(petPeevesIsOpen){
            TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("+ Pet Peeves");
            petPeevesLayout.setVisibility(LinearLayout.GONE);
            petPeevesIsOpen = false;
        }
        else{
            TextView petPeevesText = (TextView)findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("- Pet Peeves");

            petPeevesLayout.setVisibility(LinearLayout.VISIBLE);
            petPeevesIsOpen = true;
        }
    }

    public void toggleAllergies(View view){
        closeAll("allergies");
        LinearLayout allergiesLayout = (LinearLayout) findViewById(R.id.allergies_layout);
        if(allergiesIsOpen){
            TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
            allergiesText.setText("+ Allergies");
            allergiesLayout.setVisibility(LinearLayout.GONE);
            allergiesIsOpen = false;
        }
        else{
            TextView allergiesText = (TextView)findViewById(R.id.allergies_text);
            allergiesText.setText("- Allergies");

            allergiesLayout.setVisibility(LinearLayout.VISIBLE);
            allergiesIsOpen = true;
        }
    }

    public void closeAll(String exception){
        if(!exception.equals("contact")) {
            LinearLayout contactLayout = (LinearLayout) findViewById(R.id.contact_layout);
            TextView eContactText = (TextView) findViewById(R.id.eContactText);
            eContactText.setText("+ Emergency Contact Info");
            contactLayout.setVisibility(LinearLayout.GONE);
            contactIsOpen = false;
        }

        if(!exception.equals("about_me")) {
            LinearLayout aboutMeLayout = (LinearLayout) findViewById(R.id.about_me_layout);
            TextView aboutMeText = (TextView) findViewById(R.id.about_me_text);
            aboutMeText.setText("+ About Me");
            aboutMeLayout.setVisibility(LinearLayout.GONE);
            aboutMeIsOpen = false;
        }

        if(!exception.equals("preferences")) {
            RelativeLayout preferencesLayout = (RelativeLayout) findViewById(R.id.questionnaire_checkboxes);
            TextView preferencesText = (TextView) findViewById(R.id.preferences_text);
            preferencesText.setText("+ Preferences");
            preferencesLayout.setVisibility(LinearLayout.GONE);
            preferenceIsOpen = false;
        }
        if(!exception.equals("pet_peeves")) {

            LinearLayout petPeevesLayout = (LinearLayout) findViewById(R.id.pet_peeve_layout);
            TextView petPeevesText = (TextView) findViewById(R.id.pet_peeves_text);
            petPeevesText.setText("+ Pet Peeves");
            petPeevesLayout.setVisibility(LinearLayout.GONE);
            petPeevesIsOpen = false;
        }

        if(!exception.equals("allergies")) {
            LinearLayout allergiesLayout = (LinearLayout) findViewById(R.id.allergies_layout);
            TextView allergiesText = (TextView) findViewById(R.id.allergies_text);
            allergiesText.setText("+ Allergies");
            allergiesLayout.setVisibility(LinearLayout.GONE);
            allergiesIsOpen = false;
        }

    }

    // Picture Taking functionality


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                System.out.println("photoFile was not null*****");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory() + "/picupload";
        File dir = new File(storageDir);
        if (!dir.exists())
            dir.mkdir();

        File image = new File(storageDir + "/" + imageFileName + ".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        Log.i(TAG, "photo path = " + mCurrentPhotoPath);
        return image;
    }

    private void takePhoto() {

        dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(TAG, "onActivityResult: " + this);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            setPic();
            //Set up database uploading here
//			Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//			if (bitmap != null) {
//				mImageView.setImageBitmap(bitmap);
//				try {
//					sendPhoto(bitmap);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if(bitmap == null)
        {
            System.out.println("bitmap = null...");
        }

        Matrix mtx = new Matrix();
        mtx.postRotate(90);
        // Rotating Bitmap
        Bitmap rotatedBMP = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap)
            bitmap.recycle();

        mImageView.setImageBitmap(rotatedBMP);
    }














}

