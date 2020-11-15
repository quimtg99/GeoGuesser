package cat.itb.geoguesser;

import android.icu.text.UFormat;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuizViewModel extends ViewModel {
    public QuestionModel[] llistaPreguntes = {
                new QuestionModel(R.string.questionText1, 3, R.string.option3, R.string.option4, R.string.option1, R.string.option2),
                new QuestionModel(R.string.questionText2, 2, R.string.option13, R.string.option2, R.string.option14, R.string.option4),
                new QuestionModel(R.string.questionText3, 4, R.string.option6, R.string.option9, R.string.option10, R.string.option5),
                new QuestionModel(R.string.questionText4, 2, R.string.option11, R.string.option10, R.string.option6, R.string.option7),
                new QuestionModel(R.string.questionText5, 1, R.string.option15, R.string.option12, R.string.option4, R.string.option13),
                new QuestionModel(R.string.questionText6, 1, R.string.option8, R.string.option14, R.string.option4, R.string.option13),
                new QuestionModel(R.string.questionText7, 3, R.string.option6, R.string.option9, R.string.option11, R.string.option5),
                new QuestionModel(R.string.questionText8, 1, R.string.option1, R.string.option2, R.string.option4, R.string.option8),
                new QuestionModel(R.string.questionText9, 2, R.string.option13, R.string.option4, R.string.option15, R.string.option14),
                new QuestionModel(R.string.questionText10, 4, R.string.option5, R.string.option11, R.string.option6, R.string.option12),
    };
    public ArrayList<QuestionModel> llistaAux = new ArrayList<QuestionModel>(Arrays.asList(llistaPreguntes));



   private int index = 0;
   public Boolean alert = false;

   public void suffleQuestions(){
       Collections.shuffle(llistaAux);
   }

   public void setIndex(int index){
       this.index = index;
   }

   public int getIndex(){
       return index;
   }

   public QuestionModel getActualQuestion(){
       return llistaAux.get(index);
   }

   public void avanzarPregunta(){
       if (index == (llistaPreguntes.length - 1)) {
           index = 0 ;
           alert = true;

       } else {
           index++;
       }
   }
}
