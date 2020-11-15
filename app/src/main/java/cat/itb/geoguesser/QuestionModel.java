package cat.itb.geoguesser;

public class QuestionModel {
    private Integer question;
    private Integer answer;
    private Integer option1;
    private Integer option2;
    private Integer option3;
    private Integer option4;

    public QuestionModel(Integer question, Integer answer, Integer option1, Integer option2, Integer option3, Integer option4) {
        this.question = question;
        this.answer = answer;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public Integer getQuestion() {
        return question;
    }

    public Integer getAnswer() {
        return answer;
    }

    public Integer getOption1() {
        return option1;
    }

    public Integer getOption2() {
        return option2;
    }

    public Integer getOption3() {
        return option3;
    }

    public Integer getOption4() {
        return option4;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public void setOption1(Integer option1) {
        this.option1 = option1;
    }

    public void setOption2(Integer option2) {
        this.option2 = option2;
    }

    public void setOption3(Integer option3) {
        this.option3 = option3;
    }

    public void setOption4(Integer option4) {
        this.option4 = option4;
    }
}

