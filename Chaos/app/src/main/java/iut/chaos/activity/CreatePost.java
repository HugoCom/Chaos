package iut.chaos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import iut.chaos.R;
import iut.chaos.model.DataManager;
import iut.chaos.model.Post;

public class CreatePost extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }

    public void createPost(View view) {
        EditText text = findViewById(R.id.text_create_post);
        TextView error = findViewById(R.id.text_to_long_error);

        if (text.getText().length() <= 200) {
            Post p = DataManager.getInstance().createPost(text.getText().toString(), DataManager.getInstance().getLoggedUser());
            DataManager.getInstance().writePost(p);
            finish();
        }
        else {
            String errorS = "Your post is too long (" + text.getText().length() + "). 200 characters allowed.";
            error.setText(errorS);
        }
    }

}