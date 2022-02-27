package com.rec.recipeapp.UserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rec.recipeapp.R;

public class UserProfileFragment extends Fragment {
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    ShapeableImageView profilePic;
    TextView userName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = view.findViewById(R.id.fragment_profile_pic);
        userName = view.findViewById(R.id.fragment_profile_name);

        currentUser = mAuth.getCurrentUser();
        userName.setText(currentUser.getDisplayName());
        if (currentUser.getPhotoUrl() != null)
        {

        }

        return view;
    }
}
