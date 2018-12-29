package com.example.itskh.myapplication.FirebaseClasses;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCM extends FirebaseMessagingService {

    //Token :eBzexYcVQU8:APA91bElqrSg6KEBhBwfoV6adUS9VBFdPebvUF2KCn3F-qj-3JMyGGXSO0zgMv_nOBsElH-Vzki_xrVYO1j4eIJ8umCnCz5278ZF103M2KEQ7NEE9tGe2-e-TXdzQTg9R2PMlwDg76fp
    public void onNewToken(String token) {
        Log.d("FCM-Service", "Refreshed token: " + token);
    }
}
