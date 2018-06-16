package com.tongminhnhut.admin_luanvan.Server;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tongminhnhut.admin_luanvan.DAL.SignInDAL;
import com.tongminhnhut.admin_luanvan.Model.Token;

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refresherToken = FirebaseInstanceId.getInstance().getToken();
        updateToServer(refresherToken);
    }

    private void updateToServer(String refresherToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");
        Token token = new Token(refresherToken, true);
        tokens.child(SignInDAL.currentUser.getPhone()).setValue(token);
    }
}
