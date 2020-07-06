package sg.edu.rp.c346.id18015497.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import androidx.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText weightEt, heightEt;
    TextView dateTv, bmiTv, bmiResultTv;
    Button calcBtn, resetBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightEt = findViewById(R.id.weightEditText);
        heightEt = findViewById(R.id.heightEditText);
        dateTv = findViewById(R.id.dateAnsTextView);
        bmiTv = findViewById(R.id.bmiAnsTextView);
        calcBtn = findViewById(R.id.calcBtn);
        resetBtn = findViewById(R.id.resetBtn);
        bmiResultTv = findViewById(R.id.bmiResultTextView);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        final SharedPreferences.Editor prefEdit = prefs.edit();


        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = weightEt.getText().toString();
                String heightStr = heightEt.getText().toString();
                String bmiResult = null;

                float weightf = Float.parseFloat(weightStr);
                float heightf = Float.parseFloat(heightStr);
                String dateStr = getCurrentDate();

                float bmi = weightf / (heightf * heightf);

                if (bmi < 18.5) {
                    bmiResult = "You are underweight.";
                }

                else if (bmi >= 18.5 && bmi <= 24.9) {
                    bmiResult = "You are normal.";
                }

                else if (bmi >= 25 && bmi <= 29.9) {
                    bmiResult = "You are overweight.";
                }

                else {
                    bmiResult = "You are obese.";
                }

                String bmiString = String.format("%.3f",bmi);

                prefEdit.putFloat("bmifloat", bmi);
                prefEdit.putString("datestring", dateStr);
                prefEdit.putString("bmiresult", bmiResult);

                dateTv.setText(getCurrentDate());
                bmiTv.setText(bmiString);
                bmiResultTv.setText(bmiResult);

                prefEdit.commit();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightEt.setText("");
                heightEt.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String dateAns = prefs.getString("datestring", "");
        float bmi = prefs.getFloat("bmifloat",0);
        String bmiResultStr = prefs.getString("bmiresult", "");

        String bmiString = String.format("%.3f",bmi);

        dateTv.setText(dateAns);
        bmiTv.setText(bmiString);
        bmiResultTv.setText(bmiResultStr);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private String getCurrentDate() {
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);
        return datetime;
    }
}
