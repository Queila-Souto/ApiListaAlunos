package com.listaVip.cadastro.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier() {
        verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new JacksonFactory())
                .setAudience(Collections.singletonList("SEU_CLIENT_ID_WEB_DO_GOOGLE.apps.googleusercontent.com"))
                .build();
    }

    public GoogleIdToken.Payload verify(String idTokenString) throws Exception {
        GoogleIdToken idToken = verifier.verify(idTokenString);
        return idToken != null ? idToken.getPayload() : null;
    }
}
