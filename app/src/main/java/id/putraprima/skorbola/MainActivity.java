package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import id.putraprima.skorbola.model.Model;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;
    Model model;

    public static final String MODEL_KEY = "MODEL_KEY";
    private static final int GALLERY_REQUEST_CODE_AWAY = 1;
    private static final int GALLERY_REQUEST_CODE_HOME = 2;
    private static final String TAG = MatchActivity.class.getCanonicalName();

    Uri imageUri = null;
    Bitmap bitmaphome;
    Bitmap bitmapaway;
    @NotEmpty
    private EditText awayteamInput;
    @NotEmpty
    private EditText hometeamInput;
    private ImageView awaylogoInput;
    private ImageView homelogoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        awayteamInput= findViewById(R.id.away_team);
        hometeamInput= findViewById(R.id.home_team);

        awaylogoInput= findViewById(R.id.away_logo);
        homelogoInput= findViewById(R.id.home_logo);

        //validator

        validator =new Validator(this);
        validator.setValidationListener(this);
    }

    public void handleHomeLogo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE_HOME);
    }

    public void handleAwayLogo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE_AWAY);
    }

    public void handleNext(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String hometeam = hometeamInput.getText().toString();
        String awayteam = awayteamInput.getText().toString();


        ByteArrayOutputStream baoshome = new ByteArrayOutputStream();
        ByteArrayOutputStream baosaway = new ByteArrayOutputStream();
        bitmaphome.compress(Bitmap.CompressFormat.PNG, 50, baoshome);
        bitmapaway.compress(Bitmap.CompressFormat.PNG, 50, baosaway);

        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra(MODEL_KEY, new Model(awayteam, hometeam));

        //galeri
        intent.putExtra("HomeImage", baoshome.toByteArray());
        intent.putExtra("AwayImage", baosaway.toByteArray());
        startActivity(intent);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
        if(errors.size() > 0){
            if (errors.get(0).getView() instanceof EditText) {
                errors.get(0).getView().requestFocus();
            }
            else {
                String message = errors.get(0).getCollatedErrorMessage(this);
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE_AWAY) {
            if (data != null) {
                try {
                     imageUri = data.getData();
                    bitmapaway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    awaylogoInput.setImageBitmap(bitmapaway);

                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE_HOME) {
            if (data != null) {
                try {
                     imageUri = data.getData();
                    bitmaphome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    homelogoInput.setImageBitmap(bitmaphome);

                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

}
