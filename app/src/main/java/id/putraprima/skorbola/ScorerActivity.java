package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class ScorerActivity extends AppCompatActivity implements Validator.ValidationListener
{

    public static final String SCORER_KEY = "add";
    Validator validator;
    @NotEmpty
    EditText scorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        scorer =findViewById(R.id.ETscorer);

        //validator
        validator =new Validator(this);
        validator.setValidationListener(this);
    }

    public void handleScorerOK(View view) {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent();
        intent.putExtra(SCORER_KEY, scorer.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
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
}
