package com.ict311task2dawkins.android.myrecieptmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ict311task2dawkins.android.myrecieptmanager.Control.Alert;
import com.ict311task2dawkins.android.myrecieptmanager.Control.Alertable;
import com.ict311task2dawkins.android.myrecieptmanager.model.Reciept;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class ItemUiFragment extends Fragment {
    private static final String ARG_RECIPT_ID = "recipt_id";
    private static final String DIALOG_DATE = "dialogDate";
    private static final int REQUEST_DATE = 0;
    private final int REQUEST_IMAGE_CAPTURE = 1;

    private Alertable alertable;
    private Reciept mreciept;
    private View shopNameTextEdit;
    private View commentTextEdit;
    private View dateButton;
    private View locationTextView;
    private View locationButton;
    private View generateReportButton;
    private View titleTextEdit;
    private ImageView reciptPhotoImageView;
    private GoogleApiClient mClient;
    private String testLocation;


    public static ItemUiFragment newInstance(UUID reciptId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPT_ID, reciptId);


        ItemUiFragment fragment = new ItemUiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID reciptId = (UUID) getArguments().getSerializable(ARG_RECIPT_ID);
        mreciept = ItemLab.get(getActivity()).getRecipt(reciptId);
        alertable = new Alertable();

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        LocationRequest request = LocationRequest.create();
                        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        request.setNumUpdates(1);
                        request.setInterval(0);

                        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                mreciept.setLocation(location.getLatitude()+","+location.getLongitude());
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

    }

    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();

        ItemLab.get(getActivity()).updateRecipt(mreciept);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_ui, container, false);


        //Functional widgets

        //Title functionality
        titleTextEdit = view.findViewById(R.id.title_text_edit);
        ((EditText) titleTextEdit).setText(mreciept.getTitle());
        ((EditText) titleTextEdit).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mreciept.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Shop Name functionality
        shopNameTextEdit = view.findViewById(R.id.shop_name_text_edit);
        ((EditText) shopNameTextEdit).setText(mreciept.getShopeName());
        ((EditText) shopNameTextEdit).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mreciept.setShopeName(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Comment functionality
        commentTextEdit = view.findViewById(R.id.comment_text_edit);
        ((EditText) commentTextEdit).setText(mreciept.getComment());
        ((EditText) commentTextEdit).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mreciept.setComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dateButton = view.findViewById(R.id.date_button);
        ((Button) dateButton).setText(mreciept.getDate().toString());
        dateButton.setOnClickListener(view1 -> new Alert(refObj -> {
            FragmentManager fragmentManager = getFragmentManager();
            DialogDateFragment dialogDateFragment = DialogDateFragment.newInstance(mreciept.getDate());
            dialogDateFragment.setTargetFragment(ItemUiFragment.this, REQUEST_DATE);
            dialogDateFragment.show(fragmentManager, DIALOG_DATE);
        }, alertable, dateButton));

        locationTextView = view.findViewById(R.id.location_text_view);

        locationButton = view.findViewById(R.id.map_button);
        ((Button)locationButton).setOnClickListener(view1 -> ((TextView)locationTextView).setText(mreciept.getLocation()));

        generateReportButton = view.findViewById(R.id.generate_report_button);

        reciptPhotoImageView = (ImageView) view.findViewById(R.id.reciept_image);
        reciptPhotoImageView.setOnClickListener(view1 -> {
            // insert photo code xddddd
            dispatchTakePictureIntent();
        });
        if(load() != null){
            reciptPhotoImageView.setImageBitmap(load());
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DialogDateFragment.EXTRA_DATE);
            mreciept.setDate(date);
            ((Button)dateButton).setText(mreciept.getDate().toString());
        }
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            save(imageBitmap);
            reciptPhotoImageView.setImageBitmap(load());
        }
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!= null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private Bitmap load(){
        FileInputStream inputStream = null;
        try{
            inputStream = new FileInputStream(createFile(mreciept.getUuid().toString()+".png"));
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } return null;
    }

    private void save(Bitmap image){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(createFile(mreciept.getUuid().toString()+".png"));
            image.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                if(fileOutputStream != null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private File createFile(String fileName){
        File directory = getContext().getDir("images", Context.MODE_PRIVATE);
        if(!directory.exists() && !directory.mkdirs()){
            Log.e("IMAGE_SAVER","Error creating directory " + directory);
        }

        return new File(directory, fileName);
    }



}
