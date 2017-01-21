package tw.org.iii.brad08;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private EditText input;
    private TextView hist;
    private String textAnswer, textGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText)findViewById(R.id.input);
        hist = (TextView)findViewById(R.id.hist);

        textAnswer = createAnswer(3);
        Log.v("brad", textAnswer);
    }
    public void doGuess(View v){
        textGuess = input.getText().toString();
        String result = checkAB(textAnswer, textGuess);

        showResult(textGuess + " -> " + result);

        input.setText("");
        hist.append(textGuess + " -> " + result + "\n");
    }

    private void showResult(String mesg){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(mesg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private static String createAnswer(int n){
        HashSet<Integer> set = new HashSet<>();
        while (set.size()<n){
            set.add((int)(Math.random()*10));
        }
        StringBuffer sb = new StringBuffer();
        for (Integer temp : set){sb.append(temp);}
        return sb.toString();
    }

    private static String checkAB(String a, String g){
        int A, B; A=B=0;
        for (int i=0; i<a.length(); i++){
            if (a.charAt(i) == g.charAt(i)){
                A++;
            }else if (a.indexOf(g.charAt(i))>=0){
                B++;
            }
        }
        return A + "A" + B + "B";
    }

}
