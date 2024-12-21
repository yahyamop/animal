// Generated by view binder compiler. Do not edit!
package com.example.animalgame.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.animalgame.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CelebrationDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView celebrationImage;

  @NonNull
  public final TextView congratsText;

  @NonNull
  public final MaterialButton homeButton;

  @NonNull
  public final TextView levelCompletedText;

  @NonNull
  public final MaterialButton nextLevelButton;

  private CelebrationDialogBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView celebrationImage, @NonNull TextView congratsText,
      @NonNull MaterialButton homeButton, @NonNull TextView levelCompletedText,
      @NonNull MaterialButton nextLevelButton) {
    this.rootView = rootView;
    this.celebrationImage = celebrationImage;
    this.congratsText = congratsText;
    this.homeButton = homeButton;
    this.levelCompletedText = levelCompletedText;
    this.nextLevelButton = nextLevelButton;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CelebrationDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CelebrationDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.celebration_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CelebrationDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.celebrationImage;
      ImageView celebrationImage = ViewBindings.findChildViewById(rootView, id);
      if (celebrationImage == null) {
        break missingId;
      }

      id = R.id.congratsText;
      TextView congratsText = ViewBindings.findChildViewById(rootView, id);
      if (congratsText == null) {
        break missingId;
      }

      id = R.id.homeButton;
      MaterialButton homeButton = ViewBindings.findChildViewById(rootView, id);
      if (homeButton == null) {
        break missingId;
      }

      id = R.id.levelCompletedText;
      TextView levelCompletedText = ViewBindings.findChildViewById(rootView, id);
      if (levelCompletedText == null) {
        break missingId;
      }

      id = R.id.nextLevelButton;
      MaterialButton nextLevelButton = ViewBindings.findChildViewById(rootView, id);
      if (nextLevelButton == null) {
        break missingId;
      }

      return new CelebrationDialogBinding((LinearLayout) rootView, celebrationImage, congratsText,
          homeButton, levelCompletedText, nextLevelButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
