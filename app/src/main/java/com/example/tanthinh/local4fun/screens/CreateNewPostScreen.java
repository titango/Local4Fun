package com.example.tanthinh.local4fun.screens;

import android.support.v4.app.FragmentManager;

import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.ScreenFragment.ExploreScreenFragment;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.example.tanthinh.local4fun.services.UploadImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CreateNewPostScreen extends AppCompatActivity {
    private static final String TAG = CreateNewPostScreen.class.getSimpleName();
    private EditText editTextPostName, editTextMeetingPoint, editTextSummary,
            editTextDescription, editTextPlanLocation1, editTextPlanLocation1Desc,
            editTextPlanLocation2, editTextPlanLocation2Desc,
            editTextPlanLocation3, editTextPlanLocation3Desc,
            editTextPlanLocation4, editTextPlanLocation4Desc,
            editTextPlanLocation5, editTextPlanLocation5Desc;
    private Spinner spinnerTour, spinnerDuration, spinnerPrice;
    private Button btnCreatePost;
    private TextView txtSelectedImage;
    private ImageView btnAddImages, imgViewImage;
    private ImageButton close_icon_btn;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private DatePickerDialog picker;
    private EditText eText1;
    private EditText eText2;
    private long bookingMinDate = 0;
    private long bookingMaxDate = 0;

//    private Toolbar topToolBar;

    AlertDialog alertDialog;

    private String userId;

    private Uri filePath;
    Uri downloadUrl;

    private final int PICK_IMAGE_REQUEST = 4;

    FirebaseStorage storage;
    StorageReference storageReference;

    private  String imgPath;

    StorageReference pathReference;
    StorageReference ref;

    final ArrayList<String> plan = new ArrayList<>();
    final ArrayList<String> planDesc = new ArrayList<>();
    final ArrayList<String> pictures = new ArrayList<>();

    List fileNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post_screen);

//        topToolBar = (Toolbar) findViewById(R.id.my_toolbar);


        close_icon_btn = (ImageButton)findViewById(R.id.back_arrow_btn);

        editTextPostName = (EditText)findViewById(R.id.editTextPostName);
        editTextMeetingPoint = (EditText)findViewById(R.id.editTextMeetingPoint);
        editTextSummary = (EditText)findViewById(R.id.editTextSummary);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        editTextPlanLocation1 = (EditText)findViewById(R.id.editTextPlanLocation1);
        editTextPlanLocation1Desc = (EditText)findViewById(R.id.editTextPlanLocation1Desc);
        editTextPlanLocation2 = (EditText)findViewById(R.id.editTextPlanLocation2);
        editTextPlanLocation2Desc = (EditText)findViewById(R.id.editTextPlanLocation2Desc);
        editTextPlanLocation3 = (EditText)findViewById(R.id.editTextPlanLocation3);
        editTextPlanLocation3Desc = (EditText)findViewById(R.id.editTextPlanLocation3Desc);
        editTextPlanLocation4 = (EditText)findViewById(R.id.editTextPlanLocation4);
        editTextPlanLocation4Desc = (EditText)findViewById(R.id.editTextPlanLocation4Desc);
        editTextPlanLocation5 = (EditText)findViewById(R.id.editTextPlanLocation5);
        editTextPlanLocation5Desc = (EditText)findViewById(R.id.editTextPlanLocation5Desc);
        spinnerTour = (Spinner)findViewById(R.id.spinnerTour);
        spinnerDuration = (Spinner)findViewById(R.id.spinnerDuration);
        spinnerPrice = (Spinner)findViewById(R.id.spinnerPrice);
        btnAddImages = (ImageView)findViewById(R.id.btnAddImages);
        imgViewImage = (ImageView)findViewById(R.id.imgViewImage);
        txtSelectedImage = (TextView)findViewById(R.id.txtSelectedImage);



        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("posts");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

//        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Post post = null;
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    post = child.getValue(Post.class);
//                    userId = child.getKey();
//                    //txtDetails.setText(user.name + ", " + user.email);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        close_icon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        btnCreatePost = (Button)findViewById(R.id.btnCreatePost);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                plan.clear();
                planDesc.clear();

                userId = Singleton.getInstance().loginUser.getId();
                String postName = editTextPostName.getText().toString();
                String tourType = spinnerTour.getSelectedItem().toString();
                String description = editTextDescription.getText().toString();
                String summary = editTextSummary.getText().toString();
                Double duration = Double.parseDouble(spinnerDuration.getSelectedItem().toString());
                Double price = Double.parseDouble(spinnerPrice.getSelectedItem().toString());
                String location = editTextMeetingPoint.getText().toString();

                if(editTextPlanLocation1.getText().toString().trim().length() != 0){
                    plan.add(editTextPlanLocation1.getText().toString());
                }
                if(editTextPlanLocation2.getText().toString().trim().length() != 0){
                    plan.add(editTextPlanLocation2.getText().toString());
                }
                if(editTextPlanLocation3.getText().toString().trim().length() != 0){
                    plan.add(editTextPlanLocation3.getText().toString());
                }
                if(editTextPlanLocation4.getText().toString().trim().length() != 0){
                    plan.add(editTextPlanLocation4.getText().toString());
                }
                if(editTextPlanLocation5.getText().toString().trim().length() != 0){
                    plan.add(editTextPlanLocation5.getText().toString());
                }

                if(editTextPlanLocation1Desc.getText().toString().trim().length() != 0){
                    planDesc.add(editTextPlanLocation1Desc.getText().toString());
                }
                if(editTextPlanLocation2Desc.getText().toString().trim().length() != 0){
                    planDesc.add(editTextPlanLocation2Desc.getText().toString());
                }
                if(editTextPlanLocation3Desc.getText().toString().trim().length() != 0){
                    planDesc.add(editTextPlanLocation3Desc.getText().toString());
                }
                if(editTextPlanLocation4Desc.getText().toString().trim().length() != 0){
                    planDesc.add(editTextPlanLocation4Desc.getText().toString());
                }
                if(editTextPlanLocation5Desc.getText().toString().trim().length() != 0){
                    planDesc.add(editTextPlanLocation5Desc.getText().toString());
                }


                //pictures.add(downloadUrl.toString());

                //pictures.add(storageReference.child("images/").getDownloadUrl().toString());
                //storageReference = storage.getReferenceFromUrl("gs://local4fun.appspot.com");
                //     pathReference = storageReference.child("images/");
                //    pictures.add(pathReference.getDownloadUrl().toString());



                if(TextUtils.isEmpty(postName) || TextUtils.isEmpty(description) ||
                        TextUtils.isEmpty(summary) || TextUtils.isEmpty(location) ||
                        editTextPlanLocation1.getText().toString().trim().length() == 0 ||
                        editTextPlanLocation1Desc.getText().toString().trim().length() == 0) {
                    Toast.makeText(CreateNewPostScreen.this, "Complete the form", Toast.LENGTH_SHORT).show();
                }else{
                    createPost(userId, postName, tourType, description, summary, duration, price, location,
                            plan, planDesc, pictures, bookingMinDate, bookingMaxDate);

                    alertDialog();
                }


//                if (TextUtils.isEmpty(userId)) {


//                }
//                else {
//                    updatePost(userId, postName, tourType, duration, price, location);
//                }

                UploadImage uploadImageService = new UploadImage();
                imgPath = uploadImageService.uploadImage(CreateNewPostScreen.this, filePath);
                Log.e("HungDebug: ", imgPath);
                alertDialog();

//                uploadImage();

            }
        });

        // Calendar
        eText1=(EditText) findViewById(R.id.editText1);
        eText1.setInputType(InputType.TYPE_NULL);
        eText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateNewPostScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText1.setText((monthOfYear + 1) + "/" + dayOfMonth+ "/" + year);
                                Calendar cal = Calendar.getInstance();
                                cal.set(year, monthOfYear, dayOfMonth);
                                bookingMinDate = cal.getTimeInMillis();
                                Log.w("bookingMinDate", String.valueOf(bookingMinDate));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                picker.show();
            }
        });

        eText2=(EditText) findViewById(R.id.editText2);
        eText2.setInputType(InputType.TYPE_NULL);
        eText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateNewPostScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText2.setText((monthOfYear + 1) + "/" + dayOfMonth+ "/" + year);
                                Calendar cal = Calendar.getInstance();
                                cal.set(year, monthOfYear, dayOfMonth);
                                bookingMaxDate = cal.getTimeInMillis();
                                Log.w("bookingMaxDate", String.valueOf(bookingMaxDate));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                picker.show();
            }
        });


    }




    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
       // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Log.e("D", filePath + "");
                imgViewImage.setImageBitmap(bitmap);
                txtSelectedImage.append(filePath.getLastPathSegment() + "\n");

                uploadImage();

                if (filePath != null && data.getClipData() != null) {

                    int totalImageSelected = data.getClipData().getItemCount();

                    for (int i = 0; i < totalImageSelected; i++) {
                        Uri fileUri = data.getClipData().getItemAt(i).getUri();

                        String fileName = getFileName(fileUri);
                        fileNameList.add(fileName);
                    }
                    }



                    }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1){
                result = result.substring(cut + 1);
            }
        }

        return result;

    }

    private void uploadImage() {


        if(filePath != null)
        {
            // final ProgressDialog progressDialog = new ProgressDialog(this);
            // progressDialog.setTitle("Creating Post...");
            // progressDialog.show();

            // Create the file metadata
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpeg")
                    .build();

            ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //  progressDialog.dismiss();
                            //  Toast.makeText(CreateNewPostScreen.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = uri;
                                    pictures.add(downloadUrl.toString());
                                    //  Log.e("Image Url", pictures.get(0));

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //  progressDialog.dismiss();
                            Toast.makeText(CreateNewPostScreen.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            // progressDialog.setMessage("Post Created "+(int)progress+"%");
                        }
                    });
        }

    }


    private void createPost(String userId, String postName, String tourType, String description,
                            String summary, Double duration, Double price, String location, ArrayList<String> plan,
                            ArrayList<String> planDesc, ArrayList<String> pictures, long minDate, long maxDate) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
//        if (TextUtils.isEmpty(userId)) {
//            userId = mFirebaseDatabase.push().getKey();
//        }
//


        Post post = new Post(userId, postName, tourType, description, summary, duration, price, location,
                plan, planDesc, pictures);
        post.setMinDate(minDate);
        post.setMaxDate(maxDate);

        FireBaseAPI.insertPost(post);
    }

    private void addPostChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                // Check for null
                if (post == null) {
                    Log.e(TAG, "Post data is null!");
                    return;
                }

                Log.e(TAG, "Post data is changed!" + post.getUserId() + ", " + post.getTitle());



                // clear edit text
                editTextPostName.setText("");
                editTextMeetingPoint.setText("");
                editTextDescription.setText("");
                spinnerDuration.setSelection(0);
                spinnerPrice.setSelection(0);
                spinnerTour.setSelection(0);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

//    private void updatePost(String userId, String postName, String tourType,
//                            Double duration, Double price, String location) {
//        // updating the user via child nodes
//        if (postName != "")
//            mFirebaseDatabase.child(postName).child("title").setValue(postName);
//
//        if (tourType != "")
//            mFirebaseDatabase.child(tourType).child("tour").setValue(tourType);
//
//        if(userId != "")
//            mFirebaseDatabase.child(userId).child("userId").setValue(userId);
//
//        if(duration != 0)
//            mFirebaseDatabase.child(String.valueOf(duration)).child("hours").setValue(duration);
//
//        if(price !=0)
//            mFirebaseDatabase.child(String.valueOf(price)).child("pricePerPerson").setValue(price);
//
//        if(location != "")
//            mFirebaseDatabase.child(location).child("location").setValue(location);
//
//    }

//    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        mFirebaseDatabase.child("users").child(userId).setValue(user);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alertDialog != null && alertDialog.isShowing()){
            alertDialog.dismiss();
        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("New Post was successfully created");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

//                        TimerTask task = new TimerTask() {
//                            @Override
//                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
//                            }
//                        };
//                        Timer timer = new Timer();
//                        timer.schedule(task, 3000);

                    }
                });

        alertDialog=dialog.create();
        alertDialog.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(18);
        textView.setTextColor(Color.parseColor("#3F5C73"));
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        textView.setTypeface(boldTypeface);
        Button mButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mButton.setTextColor(Color.WHITE);
        mButton.setBackgroundColor(Color.parseColor("#6895BB"));
        //alertDialog.getWindow().getAttributes();



    }

}