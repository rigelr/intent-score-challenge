package id.putraprima.skorbola.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Model implements Parcelable {
    private String hometeam;
    private String awayteam;
    private Bitmap awaylogo,homelogo;
    private int scorehome = 0;
    private int scoreaway =0;
    private ArrayList<String> homescorer = new ArrayList<>();
    private ArrayList<String> awayscorer = new ArrayList<>();


    public Model(String hometeam, String awayteam) {
        this.hometeam = hometeam;
        this.awayteam = awayteam;
    }

    protected Model(Parcel in) {
        hometeam = in.readString();
        awayteam = in.readString();
        awaylogo = in.readParcelable(Bitmap.class.getClassLoader());
        homelogo = in.readParcelable(Bitmap.class.getClassLoader());
        homescorer = in.createStringArrayList();
        awayscorer = in.createStringArrayList();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public int getScorehome() {
        return scorehome;
    }

    public void setScorehome(int scorehome) {
        this.scorehome = scorehome;
    }

    public int getScoreaway() {
        return scoreaway;
    }

    public void setScoreaway(int scoreaway) {
        this.scoreaway = scoreaway;
    }

    public static Creator<Model> getCREATOR() {
        return CREATOR;
    }

    public String getHometeam() {
        return hometeam;
    }

    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    public String getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(String awayteam) {
        this.awayteam = awayteam;
    }

    public Bitmap getAwaylogo() {
        return awaylogo;
    }

    public void setAwaylogo(Bitmap awaylogo) {
        this.awaylogo = awaylogo;
    }

    public Bitmap getHomelogo() {
        return homelogo;
    }

    public void setHomelogo(Bitmap homelogo) {
        this.homelogo = homelogo;
    }

    public ArrayList<String> getHomescorer() {
        return homescorer;
    }

    public void setHomescorer(ArrayList<String> homescorer) {
        this.homescorer = homescorer;
    }

    public ArrayList<String> getAwayscorer() {
        return awayscorer;
    }

    public void setAwayscorer(ArrayList<String> awayscorer) {
        this.awayscorer = awayscorer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hometeam);
        dest.writeString(awayteam);
        dest.writeParcelable(awaylogo, flags);
        dest.writeParcelable(homelogo, flags);
        dest.writeStringList(homescorer);
        dest.writeStringList(awayscorer);
    }

    public void addHomeScore(String name) {
        homescorer.add(name);
        scorehome++;
    }

    public void addAwayScore(String name) {
        awayscorer.add(name);
        scoreaway++;
    }


    public String awayScorer(){
        String scorer = "";
        for(String newscoreraway : awayscorer){
            scorer += newscoreraway + "\n";
        }
        return scorer;
    }

    public String homeScorer(){
        String scorer = "";
        for(String newscorerhome : homescorer){
            scorer += newscorerhome + "\n";
        }
        return scorer;
    }
}
