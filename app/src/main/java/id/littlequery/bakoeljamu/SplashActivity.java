package id.littlequery.bakoeljamu;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImageView);

        // Create fade-in animation
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        fadeIn.setDuration(1000); // Duration of fade-in effect

        // Create zoom-in animation
        ObjectAnimator zoomInX = ObjectAnimator.ofFloat(logo, "scaleX", 0.0f, 1.0f);
        ObjectAnimator zoomInY = ObjectAnimator.ofFloat(logo, "scaleY", 0.0f, 1.0f);
        zoomInX.setInterpolator(new AccelerateDecelerateInterpolator());
        zoomInY.setInterpolator(new AccelerateDecelerateInterpolator());
        zoomInX.setDuration(1500); // Duration of zoom-in effect
        zoomInY.setDuration(1500); // Duration of zoom-in effect

        // Start the animations
        fadeIn.start();
        zoomInX.start();
        zoomInY.start();

        // Navigate to MainActivity after the animations
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // Duration for which the splash screen is displayed
    }
}