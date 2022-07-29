package com.travel.travel_on.controller;

import com.travel.travel_on.dto.FAQ;
import com.travel.travel_on.dto.Notice;
import com.travel.travel_on.dto.QNA;
import com.travel.travel_on.model.service.QNAServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/qna")
public class QNAController {

    @Autowired
    private QNAServiceImpl qsvc;

    @ApiOperation(value = "QNA 리스트 조회: QNA 글 조회 및 페이징, 검색")
    @PostMapping("/page")
    public ResponseEntity<?> searchQNA(String keyword, @PageableDefault(sort = "qnaId") Pageable pageable){
        QNABoard result = new QNABoard();
        result.PQ = qsvc.selectQNA(keyword, pageable);
        result.previous = pageable.previousOrFirst().getPageNumber(); // 이전 버튼용
        result.next = pageable.next().getPageNumber(); // 다음 버튼용

        return new ResponseEntity<QNABoard>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "글쓰기: 공지사항을 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(QNA qna){
        try{
            int result = qsvc.write(qna);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 글 조회")
    @GetMapping("/detail/{qnaId}")
    public ResponseEntity<?> select(@PathVariable Integer qnaId){
        try{
            QNA result = qsvc.selectOne(qnaId);
            return new ResponseEntity<QNA>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: 공지사항 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(QNA qna){
        try{
            int result = qsvc.update(qna);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: 글 삭제")
    @DeleteMapping("delete/{qnaId}")
    public ResponseEntity<?> delete(@PathVariable Integer qnaId){
        try{
            int result = qsvc.delete(qnaId);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Getter
    @Setter
    static class QNABoard{
        Page<QNA> PQ;
        int previous;
        int next;

        public  QNABoard(){

        }

        public QNABoard(Page<QNA> PQ, int previous, int next){
            PQ = this.PQ;
            previous = this.previous;
            next = this.next;
        }
    }
}
