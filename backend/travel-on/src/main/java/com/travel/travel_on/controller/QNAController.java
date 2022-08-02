package com.travel.travel_on.controller;

import com.travel.travel_on.dto.QNADto;
import com.travel.travel_on.dto.UserDto;
import com.travel.travel_on.entity.QNA;
import com.travel.travel_on.model.service.QNAServiceImpl;
import com.travel.travel_on.model.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/qna")
@Slf4j
public class QNAController {

    @Autowired
    private UserServiceImpl usvc;

    @Autowired
    private QNAServiceImpl qsvc;


    @ApiOperation(value = "QNA 리스트 조회: QNA 글 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> searchQNA(@PathVariable String id, @RequestParam(value = "keyword", required = false)String keyword){
        try {
            UserDto userDto = usvc.select(id);
            if(userDto == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            System.out.println("ㅎㅇ");

            List<QNA> list;
            if(keyword == null || keyword.isEmpty()){
                list = qsvc.selectAll(userDto.toEntity(), "null");
            }else{
                list = qsvc.selectAll(userDto.toEntity(), keyword);
            }
            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            log.info("QNAList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글쓰기: QNA를 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> regist(UserDto userDto, QNADto qnaDto){
        try{
            int result = qsvc.write(userDto, qnaDto);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 QNA 조회")
    @GetMapping("/detail/{qnaId}")
    public ResponseEntity<?> select(@PathVariable Integer qnaId){
        try{
            QNADto result = qsvc.selectOne(qnaId);
            return new ResponseEntity<QNADto>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(QNADto qnaDto){
        try{
            int result = qsvc.update(qnaDto);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: QNA 삭제")
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

}
