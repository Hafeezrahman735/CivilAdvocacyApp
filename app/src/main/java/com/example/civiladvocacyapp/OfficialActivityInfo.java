package com.example.civiladvocacyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class OfficialActivityInfo extends AppCompatActivity {

    ConstraintLayout layout;

    //String FACEBOOK_URL = "https://www.facebook.com/";
    String facebookId;
    //String twitterAppUrl = "twitter://user?screen_name=";
    String twitterWebUrl = "https://twitter.com/";
    String twitterId;
    String youtubeId;
    String callNum;
    String emailAddress;
    String geoAddress;
    String websiteLink;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_info);

        layout = findViewById(R.id.layout);
        imageView = findViewById(R.id.profile_pic);

        Intent intent = getIntent();
        if (intent.hasExtra(Offical.class.getName())) {

            Offical o = (Offical) intent.getSerializableExtra(Offical.class.getName());
            if (o == null)
                return;
            if (o.getPhotoUrl().equals("")){
                imageView.setImageResource(R.drawable.missing);
            } else {
                downloadImage(o.getPhotoUrl());
            }

            TextView userLocation = findViewById(R.id.current_location);
            userLocation.setText(o.getNormilizedAddress());

            TextView officeName = findViewById(R.id.name_inOffice);
            officeName.setText(o.getName());

            TextView office = findViewById(R.id.office);
            office.setText(o.getOffice());

            TextView party = findViewById(R.id.party);
            party.setText(o.getParty());

            ImageView party_pic = findViewById(R.id.party_pic);

            if (o.getParty().equals("Democratic Party")){
                layout.setBackgroundColor(Color.BLUE);
                party_pic.setImageResource(R.drawable.dem_logo);
            } else if (o.getParty().equals("Republican Party")){
                layout.setBackgroundColor(Color.RED);
                party_pic.setImageResource(R.drawable.rep_logo);
            } else {
                layout.setBackgroundColor(Color.BLACK);
            }

            TextView officeAddress = findViewById(R.id.office_address);

            TextView address = findViewById(R.id.address);
            address.setText(o.getAddress());

            if (o.getAddress().equals("")){
                address.setVisibility(View.GONE);
                officeAddress.setVisibility(View.GONE);
            } else {
                geoAddress = o.getAddress();
            }

            TextView officePhone = findViewById(R.id.office_phone);

            TextView phone = findViewById(R.id.phone_num);
            phone.setText(o.getPhone());

            if (o.getPhone().equals("")) {
                phone.setVisibility(View.GONE);
                officePhone.setVisibility(View.GONE);
            } else {
                callNum = o.getPhone();
            }
            TextView officeEmail = findViewById(R.id.office_email);

            TextView email = findViewById(R.id.email_address);
            email.setText(o.getEmail());

            if (o.getEmail().equals("")){
                email.setVisibility(View.GONE);
                officeEmail.setVisibility(View.GONE);
            } else {
                emailAddress = o.getEmail();
            }

            TextView officeWebsite = findViewById(R.id.office_website);

            TextView website = findViewById(R.id.website);
            website.setText(o.getWebsite());

            if (o.getWebsite().equals("")){
                website.setVisibility(View.GONE);
                officeWebsite.setVisibility(View.GONE);
            } else {
                websiteLink = o.getWebsite();
            }

            ImageView fbPic = findViewById(R.id.facebook);
            ImageView ttPic = findViewById(R.id.twitter);
            ImageView ytPic = findViewById(R.id.youtube);

            if (o.getFacebook().equals("")){
                fbPic.setVisibility(View.INVISIBLE);
            } else {
                //FACEBOOK_URL = FACEBOOK_URL + o.getFacebook();
                facebookId = o.getFacebook();
            }

            if (o.getTwitter().equals("")){
                ttPic.setVisibility(View.INVISIBLE);
            } else {
                //twitterAppUrl = twitterAppUrl + o.getTwitter();
                //twitterWebUrl = twitterWebUrl + o.getTwitter();
                twitterId = o.getTwitter();
            }

            if (o.getYoutube().equals("")){
                ytPic.setVisibility(View.INVISIBLE);
            } else {
                youtubeId = o.getYoutube();
            }

        }

    }

    private void downloadImage(String urlString) {
        //start = System.currentTimeMillis();
        Glide.with(this)
                .load(urlString)
                .into(imageView);
    }

    public void clickFacebook(View v){
        // You need the FB user's id for the url

        Intent intent;

        // Check if FB is installed, if not we'll use the browser
        if (isPackageInstalled("com.facebook.katana")) {
            String urlToUse = "fb://facewebmodal/f?href=" + "https://www.facebook.com/" + facebookId;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + facebookId));
        }

        // Check if there is an app that can handle fb or https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (fb/https) intents");
        }
    }

    public void clickTwitter(View v){

        Intent intent;
        // Check if Twitter is installed, if not we'll use the browser
        if (isPackageInstalled("com.twitter.android")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterId));
        } else {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + twitterId));
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (twitter/https) intents");
        }
    }

    public void clickYoutube(View v){

        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + youtubeId));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + youtubeId)));
        }

    }

    public void clickCall(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + callNum));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_DIAL (tel) intents");
        }
    }

    public void clickEmail(View v){
        String[] addresses = new String[]{"srahman13@hawk.iit.edu", emailAddress};

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "This comes from EXTRA_SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "Email text body from EXTRA_TEXT...");

        // Check if there is an app that can handle mailto intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles SENDTO (mailto) intents");
        }
    }

    public void clickAddress(View v){
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(geoAddress));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);

        // Check if there is an app that can handle geo intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void clickWebsite(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteLink));

        // Check if there is an app that can handle https intents
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            makeErrorAlert("No Application found that handles ACTION_VIEW (https) intents");
        }
    }

    public boolean isPackageInstalled(String packageName) {
        try {
            return getPackageManager().getApplicationInfo(packageName, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void makeErrorAlert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(msg);
        builder.setTitle("No App Found");

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}