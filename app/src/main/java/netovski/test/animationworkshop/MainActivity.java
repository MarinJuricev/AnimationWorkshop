package netovski.test.animationworkshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {


    SimpleLoader simpleLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleLoader = findViewById(R.id.simpleLoader);

        simpleLoader.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                simpleLoader.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                simpleLoader.startProgressAnimation();
                simpleLoader.bounce();
            }
        });

    }

}
