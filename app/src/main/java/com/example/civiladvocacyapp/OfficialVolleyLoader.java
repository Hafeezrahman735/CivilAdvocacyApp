package com.example.civiladvocacyapp;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class OfficialVolleyLoader {

    private static final String DATA_URL = "https://www.googleapis.com/civicinfo/v2/representatives?";
    private static String APIKey = "AIzaSyCezfbtXK0l_Z8hon-XtPAOnILr7oxpGEY";
    private static final String TAG = "CountryLoaderRunnable";


    public static void getSourceData(MainActivity mainAct,String loc){
        RequestQueue queue = Volley.newRequestQueue(mainAct);

        String officialLocation = loc;
        Uri.Builder buildUrl = Uri.parse(DATA_URL).buildUpon();
        buildUrl.appendQueryParameter("key", APIKey);
        buildUrl.appendQueryParameter("address",officialLocation);
        String urlToUse = buildUrl.build().toString();

        Response.Listener<JSONObject> listener =
                response -> handleResults(mainAct, response.toString());

        Response.ErrorListener error = error1 -> {
            Log.d(TAG, "getSourceData: ");
            handleResults(mainAct, null);
            /*
            JSONObject jsonObject;
            try {
                //jsonObject = new JSONObject(new String(error1.networkResponse.data));
                //Log.d(TAG, "getSourceData: " + jsonObject);
                handleResults(mainAct, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }

             */
        };
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

    private static void handleResults(MainActivity mainActivity, String s) {

        if (s == null) {
            Log.d(TAG, "handleResults: Failure in data download");
            //mainActivity.downloadFailed();
            return;
        }

        final ArrayList<Offical> officialList = parseJSON(s);
        if (officialList != null)
            Toast.makeText(mainActivity, "Loaded " + officialList.size() + " Officials.", Toast.LENGTH_LONG).show();
        mainActivity.updateData(officialList);
    }

    private static ArrayList<Offical> parseJSON(String s) {

        ArrayList<Offical> officalArrayList = new ArrayList<>();
        try {
            JSONObject jObjMain = new JSONObject(s);

            JSONObject jLocation = jObjMain.getJSONObject("normalizedInput");

            String line = jLocation.getString("line1");
            String city = jLocation.getString("city");
            String state = jLocation.getString("state");
            String zip = jLocation.getString("zip");



            String normalizedInput = line+" "+city+" "+state+" "+zip;


            JSONArray joffices = jObjMain.getJSONArray("offices");
            JSONArray officials = jObjMain.getJSONArray("officials");
            for (int i = 0; i < joffices.length(); i++) {
                JSONObject office = (JSONObject) joffices.get(i);
                String office_title = office.getString("name");
                JSONObject officialIndices = (JSONObject) joffices.get(i);
                JSONArray index = officialIndices.getJSONArray("officialIndices");

                for(int j = 0; j< index.length(); j++)
                {
                    JSONObject officialData = (JSONObject) officials.get(index.getInt(j));
                    String personname = officialData.getString("name");

                    String address = "";
                    if (officialData.has("address")) {
                        JSONArray jaddress = officialData.getJSONArray("address");
                        JSONObject objAddress = jaddress.getJSONObject(0);
                        String line1 = "";
                        String line2 = "";
                        String line3 = "";

                        if (objAddress.has("line1")) {
                            line1 = objAddress.getString("line1");
                        }
                        if (objAddress.has("line2")) {
                            line2 = objAddress.getString("line2");
                        }
                        if (objAddress.has("line3")) {
                            line3 = objAddress.getString("line3");
                        }

                        String personCity = objAddress.getString("city");
                        String personState = objAddress.getString("state");
                        String personZip = objAddress.getString("zip");

                        address = line1 +" "+ line2 +" "+ line3 + personCity +" "+ personState +" "+ personZip;
                    }
                    String party = "Unknown";
                    if (officialData.has("party")){
                        party = officialData.getString("party");
                    }

                    String phone_num = "";
                    if (officialData.has("phones")){
                        JSONArray Phone = officialData.getJSONArray("phones");
                        phone_num = Phone.getString(0);
                    }

                    String urls_string = "";
                    if (officialData.has("urls")){
                        JSONArray urls = officialData.getJSONArray("urls");
                        urls_string = urls.getString(0);
                    }

                    String email_string = "";
                    if (officialData.has("emails")){
                        JSONArray email = officialData.getJSONArray("emails");
                        email_string = email.getString(0);
                    }
                    String photo_url = "unknown";
                    if (officialData.has("photoUrl")){
                        photo_url = officialData.getString("photoUrl");
                    }
                    String fb = "";
                    String tt = "";
                    String yt = "";

                    if (officialData.has("channels")) {
                        JSONArray channels = officialData.getJSONArray("channels");

                        for (int b = 0; b < channels.length(); b++) {
                            JSONObject socialMedia = (JSONObject) channels.get(b);
                            String type = socialMedia.getString("type");
                            if (type.equals("Facebook")) {
                                fb = socialMedia.getString("id");
                            } else if (type.equals("Twitter")) {
                                tt = socialMedia.getString("id");
                            } else if (type.equals("YouTube")) {
                                yt = socialMedia.getString("id");
                            }
                        }
                    }
                    officalArrayList.add(
                            new Offical(normalizedInput, personname, office_title, party,address, email_string, phone_num,
                                    urls_string, fb, tt, yt, photo_url));


                }
            }



            return officalArrayList;
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
