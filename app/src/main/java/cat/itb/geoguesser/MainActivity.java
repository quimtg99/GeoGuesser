package cat.itb.geoguesser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView progressText;
    private Button primerButton;
    private Button segundoButton;
    private Button tercerButton;
    private Button cuartoButton;
    private Button hintButton;
    private ProgressBar progressBar;
    private TextView puntosText;
    private QuizViewModel quizViewModel = null;
    private float puntos = 0;
    private Integer pistas = 3;
    private boolean hintAct = true;
    private boolean noPoint = false;
    private Integer millis = 0;
    private Integer temps = 15000;
    private boolean timeIsUp = false;
    private boolean noRes = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (quizViewModel == null){
            quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        }

        hintButton = findViewById(R.id.hintButton);
        primerButton = findViewById(R.id.primerButton);
        segundoButton = findViewById(R.id.segundoButton);
        tercerButton = findViewById(R.id.tercerButton);
        cuartoButton = findViewById(R.id.cuartoButton);
        questionText = findViewById(R.id.questionText);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        puntosText = findViewById(R.id.puntosText);

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pistas >= 1 && hintAct) {

                    pistas--;
                    noPoint = true;
                    hintAct = false;
                    hintButton.setEnabled(hintAct);

                    adivinarRespuesta();
                }
            }
        });

        primerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer b = 1;
                pressButton(b);
            }
        });

        segundoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer b = 2;
                pressButton(b);
            }
        });

        tercerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer b = 3;
                pressButton(b);
            }
        });

        cuartoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer b = 4;
                pressButton(b);
            }
        });
        if (savedInstanceState != null){
            puntos = savedInstanceState.getFloat("resPuntos");
            pistas = savedInstanceState.getInt("resPistas");
            millis = savedInstanceState.getInt("resMillis");
            hintAct = savedInstanceState.getBoolean("hint");
            puntosText.setText(puntos + "");
            hintButton.setText("Pistas restantes: " + pistas);
            noRes = false;
        } else {
            quizViewModel.suffleQuestions();
        }
        refrescarPantalla();
    }
    CountDownTimer timer = new CountDownTimer(temps, 1000) {

        public void onTick(long millisUntilFinished) {
                millis++;
                progressBar.setProgress(millis);
        }

        @Override
        public void onFinish() {
            millis = 0;
            progressBar.setProgress(millis);
            resta();
            timeIsUp = true;
            hintButton.setEnabled(false);
            adivinarRespuesta();
        }
    };

    public void refrescarPantalla() {
        puntosText.setText(puntos + "");
        progressText.setText("Pregunta " + (quizViewModel.getIndex() + 1) + " de " + quizViewModel.llistaPreguntes.length);
        questionText.setText(getString(quizViewModel.getActualQuestion().getQuestion()));
        primerButton.setText(quizViewModel.getActualQuestion().getOption1());
        segundoButton.setText(quizViewModel.getActualQuestion().getOption2());
        tercerButton.setText(quizViewModel.getActualQuestion().getOption3());
        cuartoButton.setText(quizViewModel.getActualQuestion().getOption4());

        //temps = 15000;
        //if (noRes) {
            millis = 0;
            timeIsUp = false;
            noRes = false;
        //}
        timer.start();
        progressBar.setProgress(millis);



        //dades del botó de les pistas
        hintButton.setText("Pistas restantes: " + pistas);
        noPoint = false;
        if (pistas >= 1) {
            hintAct = true;
        }
        hintButton.setEnabled(hintAct);
        if (!hintAct) {
            hintButton.setBackgroundColor(Color.GRAY);
        } else {
            hintButton.setBackgroundColor(getColor(R.color.colorPrimary));
        }
    }
    public void pressButton(Integer a) {
        timer.cancel();
        if (quizViewModel.getActualQuestion().getAnswer() == a){
            Toast.makeText(MainActivity.this, "CORRECT!", Toast.LENGTH_SHORT).show();
            if (!noPoint && !timeIsUp) {
                puntos++;
            }
        } else {
            Toast.makeText(MainActivity.this, "INCORRCT", Toast.LENGTH_SHORT).show();
            resta();
        }

        quizViewModel.avanzarPregunta();

        if (quizViewModel.alert) {
            dialogText();
            puntos = 0;
            pistas = 3;
            quizViewModel.suffleQuestions();
            quizViewModel.alert = false;
        } else {
            refrescarPantalla();
        }
    }
    public void dialogText(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setMessage("Quieres volver a intentarlo?")
                .setCancelable(false)
                .setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("REINTENTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        quizViewModel.suffleQuestions();
                        refrescarPantalla();
                    }
                });
        AlertDialog title = alerta.create();
        title.setTitle("Tu puntuación ha sido " + (int)(puntos*10) + " de 100");
        title.show();
    }
    public void adivinarRespuesta(){
        if (quizViewModel.getActualQuestion().getAnswer() == 1) {
            hintButton.setText(quizViewModel.getActualQuestion().getOption1());
        }
        if (quizViewModel.getActualQuestion().getAnswer() == 2) {
            hintButton.setText(quizViewModel.getActualQuestion().getOption2());
        }
        if (quizViewModel.getActualQuestion().getAnswer() == 3) {
            hintButton.setText(quizViewModel.getActualQuestion().getOption3());
        }
        if (quizViewModel.getActualQuestion().getAnswer() == 4) {
            hintButton.setText(quizViewModel.getActualQuestion().getOption4());
        }
    }
    public void resta(){
        if (puntos > 0) {
            puntos = (float) (puntos - 0.5);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("resMillis", millis);
        outState.putBoolean("hint", hintAct);
        outState.putInt("resPistas", pistas);
        outState.putFloat("resPuntos", puntos);
    }
}