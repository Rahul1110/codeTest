package rahul.com.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView txtName=findViewById(R.id.txt_name);
        TextView txtHeight=findViewById(R.id.txt_height);
        TextView txtMass=findViewById(R.id.txt_mass);
        TextView txtCreated=findViewById(R.id.txt_create);
        txtName.setText(getIntent().getStringExtra("NAME"));
        txtHeight.setText(getIntent().getStringExtra("HEIGHT"));
        txtMass.setText(getIntent().getStringExtra("MASS"));
        txtCreated.setText(getIntent().getStringExtra("CREATED"));

    }
}
