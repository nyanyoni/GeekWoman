package geekwoman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import twitter4j.Status;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jp.co.geekwoman.R.layout.activity_detail);

        Intent intent = getIntent();
        Status result = (Status) intent.getSerializableExtra("data");

        TextView textView = (TextView) findViewById(jp.co.geekwoman.R.id.detail_text_view);
        textView.setText(result.getText());

        ImageView imageView = (ImageView) findViewById(jp.co.geekwoman.R.id.detail_image_view);
        Glide.with(this)
                .load(result.getUser().getOriginalProfileImageURL())
                .into(imageView);
    }
}
