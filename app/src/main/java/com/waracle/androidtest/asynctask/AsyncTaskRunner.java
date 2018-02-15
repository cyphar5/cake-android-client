package com.waracle.androidtest.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.waracle.androidtest.MainFragment;
import com.waracle.androidtest.constant.Constants;
import com.waracle.androidtest.interfaces.AsyncTaskListener;
import com.waracle.androidtest.util.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by achau on 14-02-2018.
 */

public class AsyncTaskRunner extends AsyncTask<Void,Void,Void> {

    private MainFragment fragment;// = new MainFragment();
    private AsyncTaskListener asyncTaskListener ;
    String mServer_response;
    Context context ;

    public AsyncTaskRunner(Context context, MainFragment mf  , AsyncTaskListener asyncTaskListener){
        this.context = context ;
        fragment = mf;
        this.asyncTaskListener = asyncTaskListener ;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncTaskListener.onProgress();
        Util.showLoadingProgress(true , context);
    }

    @Override
    protected Void doInBackground(Void... objects) {

        try {
            loadMyData();
        } catch (Exception e) {
            Log.e(MainFragment.TAG, e.getMessage());
        }

        asyncTaskListener.onProgress();

        return null;
    }

    @Override
    protected void onPostExecute(Void o) {
        super.onPostExecute(o);
        try {
            fragment.setItems(new JSONArray(mServer_response));
            Util.showLoadingProgress(false, context);
            asyncTaskListener.onProgress();
        }
        catch (JSONException e){

        }
    }

//    private JSONArray loadData() throws IOException, JSONException {
//        HttpURLConnection urlConnection = (HttpURLConnection) ( new URL(Constants.JSON_URL)).openConnection();
//        urlConnection.setRequestMethod("GET");
//        urlConnection.setDoInput(true);
//        urlConnection.setDoOutput(true);
//        urlConnection.connect();
//        try {
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//
//            // Can you think of a way to improve the performance of loading data
//            // using HTTP headers???
//                    // check if we got a compressed resonse back
//
//
//            // Also, Do you trust any utils thrown your way????
//
//            byte[] bytes = StreamUtils.readUnknownFully(in);
//
//            // Read in charset of HTTP content.
//            String charset = Util.parseCharset(urlConnection.getRequestProperty("Content-Type"));
//
//            // Convert byte array to appropriate encoded string.
//            String jsonText = new String(bytes, charset);
//
//            // Read string as JSON.
//            return new JSONArray(jsonText);
//        } finally {
//            urlConnection.disconnect();
//        }
//    }



    private void loadMyData() throws Exception , JSONException {
        URL url;
        String server_response;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(Constants.JSON_URL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
          //  con.setRequestProperty("User-Agent", USER_AGENT);
            urlConnection.setInstanceFollowRedirects(true);

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                server_response = Util.readStream(urlConnection.getInputStream());
                Log.d("MF_FRAG","calling set items");
                mServer_response = server_response;
              //  Log.v("CatalogClient", server_response);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
