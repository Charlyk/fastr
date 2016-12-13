package com.softrangers.fastr.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softrangers.fastr.api.FastApi;
import com.softrangers.fastr.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by eduard on 10.12.16.
 */

public class FastRApp extends Application {

    public static final String PREFERENCES_NAME = "com.softranger.fasts.FastRApp";
    private static FastRApp instance;
    private static SharedPreferences preferences;

    public static Typeface openSans_bold, openSans_light, openSans_regular, openSans_semibold;

    private static Retrofit retrofit;
    private static User user;

    public static FastRApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // initiate shared preferences
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // initiate fonts
        openSans_bold = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Bold.ttf");
        openSans_light = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
        openSans_regular = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        openSans_semibold = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Semibold.ttf");

        // configure object mapper to auto parse date objects
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.getDefault());
        objectMapper.setDateFormat(dateFormat);

        // create retrofit client
        retrofit = new Retrofit.Builder()
                .baseUrl("http://162.242.203.180:8066")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean("status", false);
    }

    public void setLoginStatus(boolean status) {
        preferences.edit().putBoolean("status", status).apply();
    }

    public void saveUserCredentials(@NonNull String email, @NonNull String password) {
        preferences.edit().putString("email", email).putString("pass", password).apply();
    }

    public String getEmail() {
        return preferences.getString("email", "");
    }

    public String getPassword() {
        return preferences.getString("pass", "");
    }

    public static User user() {
        return user;
    }

    public static void setUser(User user) {
        FastRApp.user = user;
    }

    /**
     * Create an instance of FastS api interface
     * @return {@link FastApi}
     */
    public static FastApi apiInterface() {
        return retrofit.create(FastApi.class);
    }
}
