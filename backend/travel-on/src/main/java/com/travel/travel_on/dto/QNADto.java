package com.travel.travel_on.dto;

import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QNADto {
    private Integer QNAId;
    private String realId;
    private String nickname;
    private String title;
    private String content;
    private String qnaDate;
    private boolean answerFlag;
    private String answer;
    private String answerDate;

    public QNADto(QNA qna){
        QNAId=qna.getQnaId();
        realId=qna.getUser().getRealId();
        nickname=qna.getNickname();
        title=qna.getTitle();
        content=qna.getContent();
        qnaDate=qna.getQnaDate();
        answerFlag=qna.isAnswerFlag();
        answer=qna.getAnswer();
        answerDate=qna.getAnswerDate();
    }
}
