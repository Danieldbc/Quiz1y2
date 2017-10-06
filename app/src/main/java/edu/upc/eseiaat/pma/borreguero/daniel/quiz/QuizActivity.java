package edu.upc.eseiaat.pma.borreguero.daniel.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.CorrectionInfo;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int id_rb[]={
            R.id.answer1,R.id.answer2,R.id.answer3,R.id.answer4
    };

    private TextView text_question;
    private String[] question;
    private String[] answers;
    private String[] current_answer;
    private RadioButton[] radioButtons;
    private int iCurrentQuestion=0;
    private String correctAnswer;
    private boolean[] aciertos;
    private RadioGroup group;
    private Button btn_check,btn_prev;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        radioButtons=new RadioButton[id_rb.length];
        for(int i=0;i<id_rb.length;i++){
            radioButtons[i]=(RadioButton)findViewById(id_rb[i]);
        }

        question=getResources().getStringArray(R.array.questions);
        answers=getResources().getStringArray(R.array.answers);
        text_question=(TextView) findViewById(R.id.text_question);
        aciertos=new boolean[question.length];
        group=(RadioGroup)findViewById(R.id.answer_group);
        btn_check=(Button)findViewById(R.id.btn_check);
        btn_prev=(Button)findViewById(R.id.btn_previous);
        current_answer=new String[question.length];
        inizializar();



        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datos();

                if (iCurrentQuestion < question.length - 1) {
                    iCurrentQuestion++;
                    inizializar();
                } else {
                    int correct=0,incorrect=0;
                    for (boolean b:aciertos){
                        if (b) correct++;
                        else incorrect++;
                    }
                    String result=String.format("Correctas: %d-- Incorrectas %d",correct,incorrect);
                    Toast.makeText(QuizActivity.this,result,Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (iCurrentQuestion>0){
                datos();
                iCurrentQuestion--;
                inizializar();
            }
            }
        });
    }

    private void datos() {
        int id=group.getCheckedRadioButtonId();
        if (id==-1){
            current_answer[iCurrentQuestion]=null;
        }else{
            RadioButton r = (RadioButton) findViewById(id);
            String opcion = r.getText().toString();
            current_answer[iCurrentQuestion]=opcion;
        }


        if (correctAnswer.equals(current_answer[iCurrentQuestion])) {
            aciertos[iCurrentQuestion]=true;
        } else {
            aciertos[iCurrentQuestion]=false;
        }
    }

    private void inizializar() {
        text_question.setText(question[iCurrentQuestion]);
        String[] ans=answers[iCurrentQuestion].split(";");
        group.clearCheck();

        for (int i=0;i<id_rb.length;i++){
            radioButtons[i].setText(ans[i]);
            if (radioButtons[i].getText().equals(current_answer[iCurrentQuestion])){
                radioButtons[i].setChecked(true);
            }

        }

        correctAnswer=ans[ans.length-1];

        if (iCurrentQuestion==0){
            btn_prev.setVisibility(View.INVISIBLE);

        }else{
           btn_prev.setVisibility(View.VISIBLE);
        }

        if (iCurrentQuestion==question.length-1){
            btn_check.setText(R.string.finish);
        }else{
            btn_check.setText(R.string.check);
        }

    }
}
