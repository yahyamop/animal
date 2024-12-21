package com.example.animalgame;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    private int currentLevel = 1;
    private final int maxLevel = 4;
    private ImageView levelImage;
    private GridLayout letterGrid;
    private LinearLayout letterBoxesContainer;
    private TextView levelText;
    private MaterialButton deleteButton;
    private MaterialButton exitButton;
    private CardView imageCard;
    private StringBuilder currentWord;
    private ArrayList<TextView> letterBoxes;
    private LinearProgressIndicator levelProgress;

    private static class Level {
        String word;
        String imageUrl;

        Level(String word, String imageUrl) {
            this.word = word;
            this.imageUrl = imageUrl;
        }
    }

    private final Map<Integer, Level> levels = new HashMap<Integer, Level>() {{
        put(1, new Level("LION", "https://upload.wikimedia.org/wikipedia/commons/7/73/Lion_waiting_in_Namibia.jpg"));
        put(2, new Level("ELEPHANT", "https://upload.wikimedia.org/wikipedia/commons/3/37/African_Bush_Elephant.jpg"));
        put(3, new Level("GIRAFFE", "https://upload.wikimedia.org/wikipedia/commons/e/e0/Giraffa_camelopardalis_angolensis.jpg"));
        put(4, new Level("PENGUIN", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a3/Aptenodytes_forsteri_-Snow_Hill_Island%2C_Antarctica_-adults_and_juvenile-8.jpg/1280px-Aptenodytes_forsteri_-Snow_Hill_Island%2C_Antarctica_-adults_and_juvenile-8.jpg"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "Starting onCreate");
            
            setContentView(R.layout.activity_game);
            Log.d(TAG, "Content view set");

            // Initialize views
            initializeViews();
            Log.d(TAG, "Views initialized");

            // Setup buttons
            setupButtons();
            Log.d(TAG, "Buttons setup complete");

            // Start first level
            setupLevel(currentLevel);
            Log.d(TAG, "First level setup complete");

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Error starting the game: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeViews() {
        try {
            levelImage = findViewById(R.id.levelImage);
            letterGrid = findViewById(R.id.letterGrid);
            letterBoxesContainer = findViewById(R.id.letterBoxesContainer);
            levelText = findViewById(R.id.levelText);
            deleteButton = findViewById(R.id.deleteButton);
            exitButton = findViewById(R.id.exitButton);
            imageCard = findViewById(R.id.imageCard);
            currentWord = new StringBuilder();
            letterBoxes = new ArrayList<>();

            // Initialize progress indicator
            levelProgress = findViewById(R.id.levelProgress);
            if (levelProgress != null) {
                levelProgress.setMax(maxLevel);
                levelProgress.setProgress(currentLevel);
            }

            if (levelImage == null) Log.e(TAG, "levelImage is null");
            if (letterGrid == null) Log.e(TAG, "letterGrid is null");
            if (letterBoxesContainer == null) Log.e(TAG, "letterBoxesContainer is null");
            if (levelText == null) Log.e(TAG, "levelText is null");
            if (deleteButton == null) Log.e(TAG, "deleteButton is null");
            if (exitButton == null) Log.e(TAG, "exitButton is null");
            if (imageCard == null) Log.e(TAG, "imageCard is null");

        } catch (Exception e) {
            Log.e(TAG, "Error in initializeViews: " + e.getMessage(), e);
            throw e;
        }
    }

    private void setupButtons() {
        try {
            if (exitButton != null) {
                exitButton.setOnClickListener(v -> {
                    Log.d(TAG, "Exit button clicked");
                    finish();
                });
            }

            if (deleteButton != null) {
                deleteButton.setOnClickListener(v -> {
                    Log.d(TAG, "Delete button clicked");
                    if (currentWord.length() > 0) {
                        // Get the last letter before removing it
                        char lastLetter = currentWord.charAt(currentWord.length() - 1);
                        
                        // Re-enable the button for this letter
                        for (int i = 0; i < letterGrid.getChildCount(); i++) {
                            View child = letterGrid.getChildAt(i);
                            if (child instanceof TextView) {
                                TextView button = (TextView) child;
                                if (button.getText().toString().charAt(0) == lastLetter) {
                                    button.setBackgroundResource(R.drawable.letter_button_background);
                                    button.setTextColor(ContextCompat.getColor(this, R.color.letter_box_text));
                                    break;
                                }
                            }
                        }
                        
                        // Remove the last letter from currentWord
                        currentWord.setLength(currentWord.length() - 1);
                        updateLetterBoxes();
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in setupButtons: " + e.getMessage(), e);
            throw e;
        }
    }

    private void setupLevel(int level) {
        try {
            if (levelText != null) {
                levelText.setText("Level " + level);
                Log.d(TAG, "Level text set to: Level " + level);
            }

            // Update progress indicator
            if (levelProgress != null) {
                levelProgress.setProgress(level);
            }

            // Update background color based on level
            int backgroundColorRes;
            switch(level) {
                case 1:
                    backgroundColorRes = R.color.level_1_background;
                    break;
                case 2:
                    backgroundColorRes = R.color.level_2_background;
                    break;
                case 3:
                    backgroundColorRes = R.color.level_3_background;
                    break;
                case 4:
                    backgroundColorRes = R.color.level_4_background;
                    break;
                default:
                    backgroundColorRes = R.color.level_1_background;
            }
            getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(this, backgroundColorRes));

            // Load level image
            Level currentLevel = levels.get(level);
            if (currentLevel != null) {
                Glide.with(this)
                    .load(currentLevel.imageUrl)
                    .transform(new CenterCrop())
                    .into(levelImage);
                createLetterBoxes(currentLevel.word);
            }

            // Reset current word
            currentWord.setLength(0);
            updateLetterBoxes();

            // Animate the card
            animateCard();

        } catch (Exception e) {
            Log.e(TAG, "Error in setupLevel: " + e.getMessage(), e);
            Toast.makeText(this, "Error in setting up level: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private TextView createLetterBox() {
        TextView letterBox = new TextView(this);
        int boxSize = (int) (getResources().getDisplayMetrics().density * 36); 
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(boxSize, boxSize);
        params.setMargins(4, 4, 4, 4); 
        letterBox.setLayoutParams(params);
        letterBox.setGravity(Gravity.CENTER);
        letterBox.setTextSize(18); 
        letterBox.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        letterBox.setBackgroundResource(R.drawable.letter_box_background);
        letterBox.setTextColor(ContextCompat.getColor(this, R.color.letter_box_text));
        return letterBox;
    }

    private TextView createLetterButton(String letter) {
        TextView letterButton = new TextView(this);
        int buttonSize = (int) (getResources().getDisplayMetrics().density * 42); 
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = buttonSize;
        params.height = buttonSize;
        params.setMargins(4, 4, 4, 4); 
        letterButton.setLayoutParams(params);
        letterButton.setText(letter.toUpperCase());
        letterButton.setGravity(Gravity.CENTER);
        letterButton.setTextSize(20); 
        letterButton.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        letterButton.setBackgroundResource(R.drawable.letter_button_background);
        letterButton.setTextColor(ContextCompat.getColor(this, R.color.letter_box_text));
        
        letterButton.setOnClickListener(v -> onLetterClick(letter));
        return letterButton;
    }

    private void createLetterBoxes(String word) {
        letterBoxesContainer.removeAllViews();
        letterBoxes.clear();

        // Create letter boxes for the word
        for (int i = 0; i < word.length(); i++) {
            TextView letterBox = createLetterBox();
            letterBoxes.add(letterBox);
            letterBoxesContainer.addView(letterBox);
        }

        // Add the word letters
        List<Character> wordLetters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            wordLetters.add(c);
        }

        // Add exactly 2 random letters
        List<Character> allLetters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            if (!word.contains(String.valueOf(c))) {
                allLetters.add(c);
            }
        }
        Collections.shuffle(allLetters);
        // Add exactly 2 random letters
        wordLetters.addAll(allLetters.subList(0, 2));
        
        // Shuffle all letters
        Collections.shuffle(wordLetters);

        // Create buttons for all letters
        letterGrid.removeAllViews();
        for (char letter : wordLetters) {
            TextView button = createLetterButton(String.valueOf(letter));
            letterGrid.addView(button);
        }
    }

    private void onLetterClick(String letter) {
        Level level = levels.get(currentLevel);
        if (level != null && currentWord.length() < level.word.length()) {
            // Find the clicked button
            TextView clickedButton = null;
            for (int i = 0; i < letterGrid.getChildCount(); i++) {
                View child = letterGrid.getChildAt(i);
                if (child instanceof TextView) {
                    TextView button = (TextView) child;
                    if (button.getText().toString().charAt(0) == letter.charAt(0) && button.isEnabled()) {
                        clickedButton = button;
                        break;
                    }
                }
            }

            if (clickedButton != null) {
                clickedButton.setBackgroundResource(R.drawable.letter_button_background_disabled);
                clickedButton.setTextColor(ContextCompat.getColor(this, R.color.letter_box_text_disabled));
                currentWord.append(letter);
                updateLetterBoxes();
                
                // Check word automatically when all letters are filled
                if (currentWord.length() == level.word.length()) {
                    checkWord(level.word);
                }
            }
        }
    }

    private void updateLetterBoxes() {
        for (int i = 0; i < letterBoxes.size(); i++) {
            TextView box = letterBoxes.get(i);
            box.setText(i < currentWord.length() ? String.valueOf(currentWord.charAt(i)) : "");
        }
    }

    private void resetAllLetterButtons() {
        for (int i = 0; i < letterGrid.getChildCount(); i++) {
            View child = letterGrid.getChildAt(i);
            if (child instanceof TextView) {
                TextView button = (TextView) child;
                button.setBackgroundResource(R.drawable.letter_button_background);
                button.setTextColor(ContextCompat.getColor(this, R.color.letter_box_text));
            }
        }
    }

    private void checkWord(String targetWord) {
        if (currentWord.toString().equalsIgnoreCase(targetWord)) {
            showCelebrationDialog();
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
            currentWord.setLength(0);
            updateLetterBoxes();
            resetAllLetterButtons();
        }
    }

    private void showCelebrationDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.celebration_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Start celebration animation
        ImageView celebrationImage = dialog.findViewById(R.id.celebrationImage);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(celebrationImage, View.SCALE_X, 0f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(celebrationImage, View.SCALE_Y, 0f, 1.2f, 1f);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.start();
        scaleY.start();

        // Update text based on game progress
        TextView congratsText = dialog.findViewById(R.id.congratsText);
        TextView levelCompletedText = dialog.findViewById(R.id.levelCompletedText);
        MaterialButton nextLevelButton = dialog.findViewById(R.id.nextLevelButton);
        MaterialButton homeButton = dialog.findViewById(R.id.homeButton);

        if (currentLevel < maxLevel) {
            congratsText.setText("Well done!");
            levelCompletedText.setText("You have completed level " + currentLevel + " successfully!");
            nextLevelButton.setText("Next Level");
            nextLevelButton.setOnClickListener(v -> {
                currentLevel++;
                setupLevel(currentLevel);
                dialog.dismiss();
            });
        } else {
            congratsText.setText("Congratulations!");
            levelCompletedText.setText("You have completed all levels!");
            nextLevelButton.setText("Finish Game");
            nextLevelButton.setOnClickListener(v -> {
                dialog.dismiss();
                finish();
            });
        }

        // Add home button click listener
        homeButton.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }

    private void animateCard() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageCard, View.SCALE_X, 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageCard, View.SCALE_Y, 0.8f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageCard, View.ALPHA, 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
