package com.ict311task2dawkins.android.myrecieptmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.UUID;

public class ItemUiActivity extends SingleFragmentActivty {

    public static final String EXTRA_RECIPT_ID = "com.ict311task2dawkins.android.myreciptmanager.recipt_id";

    public static Intent newIntent(Context packageContext, UUID reciptId){
        Intent intent = new Intent(packageContext, ItemUiActivity.class);
        intent.putExtra(EXTRA_RECIPT_ID, reciptId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID reciptid = (UUID) getIntent().getSerializableExtra(EXTRA_RECIPT_ID);
        return ItemUiFragment.newInstance(reciptid);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode,GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE, dialogInterface -> finish());
            errorDialog.show();
        }
    }
}
