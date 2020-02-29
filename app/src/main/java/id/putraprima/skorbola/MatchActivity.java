package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.putraprima.skorbola.model.Model;

import static id.putraprima.skorbola.MainActivity.MODEL_KEY;

public class MatchActivity extends AppCompatActivity {
    Model model;

    private TextView TVhometeam;
    private TextView TVawayteam;

    private TextView TVscorehome;
    private TextView TVscoreaway;

    private ImageView IVhomelogo;
    private ImageView IVawaylogo;

    private TextView TVscoreraway;
    private TextView TVscorerhome;

    private int scorehome = 0;
    private int scoreaway = 0;

    String result;
    public static final String DATA_KEY = "data";
    public static final String SCORER_KEY = "add";
    public static final int REQUEST_CODE_HOME_KEY = 1;
    public static final int REQUEST_CODE_AWAY_KEY = 2 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        TVhometeam = findViewById(R.id.txt_home);
        TVawayteam = findViewById(R.id.txt_away);
        TVscorehome = findViewById(R.id.score_home);
        TVscoreaway = findViewById(R.id.score_away);
        IVhomelogo = findViewById(R.id.home_logo);
        IVawaylogo = findViewById(R.id.away_logo);
        TVscoreraway = findViewById(R.id.scorernameaway);
        TVscorerhome = findViewById(R.id.scorernamehome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            model = extras.getParcelable(MODEL_KEY);

            TVhometeam.setText(model.getHometeam());
            TVawayteam.setText(model.getAwayteam());
            Bitmap bmphome = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("HomeImage"), 0, getIntent().getByteArrayExtra("HomeImage").length);
            IVhomelogo.setImageBitmap(bmphome);
            Bitmap bmpaway = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("AwayImage"), 0, getIntent().getByteArrayExtra("AwayImage").length);
            IVawaylogo.setImageBitmap(bmpaway);
        }
    }


    public void handleHomeScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent,1);
    }

    public void handleAwayScore(View view) {
        Intent intent = new Intent(this, ScorerActivity.class);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if(requestCode == REQUEST_CODE_HOME_KEY){
            if(resultCode == RESULT_OK){
                //scorehome++;
                //set score
                model.addHomeScore(data.getStringExtra(SCORER_KEY));
                TVscorehome.setText(String.valueOf(model.getScorehome()));
                TVscorerhome.setText(model.homeScorer());
                //set nama pencetak gol
                //TVscorehome.setText(String.valueOf(model.getHomeScorer()));

            }
        }else if(requestCode == REQUEST_CODE_AWAY_KEY){
            if(resultCode == RESULT_OK) {
                //scoreaway++;
                //set score
                model.addAwayScore(data.getStringExtra(SCORER_KEY));
                TVscoreaway.setText(String.valueOf(model.getScoreaway()));
                TVscoreraway.setText(model.awayScorer());
                //set nama pencetak gol
                //awayScorer.setText(String.valueOf(away_scorer));
            }
        }

    }


    public void handleSubmit(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT,result);
        if (model.getScorehome()>model.getScoreaway()){

            result="pemenangnya adalah "+ model.getHometeam();
            intent.putExtra(ResultActivity.EXTRA_RESULT,result);

        }else if(model.getScoreaway()>model.getScorehome()){

            result="pemenangnya adalah "+model.getAwayteam();
            intent.putExtra(ResultActivity.EXTRA_RESULT,result);

        }else if(model.getScoreaway()==model.getScorehome()){

            result="skor seri";
            intent.putExtra(ResultActivity.EXTRA_RESULT,result);
        }
        startActivity(intent);

    }
}
