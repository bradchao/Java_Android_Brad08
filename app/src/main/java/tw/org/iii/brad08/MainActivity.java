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
    private TextView hist, info;
    private String textAnswer, textGuess;
    private int count;
    private int d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText)findViewById(R.id.input);
        hist = (TextView)findViewById(R.id.hist);
        info = (TextView)findViewById(R.id.info);

        d = 3;
        newGame(null);
    }

    public void newGame(View v){
        info.setText("目前設定為猜" + d + "碼");
        textAnswer = createAnswer(d);
        count = 0;
        input.setText("");
        hist.setText("");
    }

    public void doGuess(View v){
        textGuess = input.getText().toString();
        String result = checkAB(textAnswer, textGuess);

        input.setText("");
        hist.append(textGuess + " -> " + result + "\n");

        count++;
        if (result.equals(d + "A0B")){
            // WINNER
            showResult("WINNER", true);

        }else if (count == 10){
            // Loser
            showResult("Loser:" +textAnswer, true);

        }else {
            // Normal
            showResult(count + "." + textGuess + " -> " + result, false);
        }

    }

    public void setup(View v){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Setup");
        builder.setItems(new String[]{"3", "4", "5"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                d = i+3;
                newGame(null);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void exit(View v){
        finish();
    }

    private void showResult(String mesg, boolean isRestart){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(mesg);

        if (isRestart) {
            builder.setPositiveButton("restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    newGame(null);
                }
            });
        }else{
            builder.setPositiveButton("OK", null);
        }
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
