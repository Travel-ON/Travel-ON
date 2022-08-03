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
import java.util.Map;
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


    @ApiOperation(value = "QNA 리스트 조회: QNA 글 조회(검색가능)", response = List.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> searchQNA(@PathVariable String id, @RequestParam(value = "keyword", required = false)String keyword){
        try {
            UserDto userDto = usvc.select(id);
            if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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
    public ResponseEntity<?> regist(QNADto qnaDto){
        try{
            UserDto userDto = usvc.select(qnaDto.getRealId());
            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            boolean result = qsvc.write(userDto, qnaDto);

            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 QNA 조회", response = QNADto.class)
    @GetMapping("/detail/{qnaId}")
    public ResponseEntity<?> select(@PathVariable Integer qnaId){
        try{
            QNADto result = qsvc.selectOne(qnaId);

            if(result == null){
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<QNADto>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: QNA 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(QNADto qnaDto){
        try{
            boolean result = qsvc.update(qnaDto);
            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: QNA 삭제")
    @DeleteMapping("delete/{qnaId}")
    public ResponseEntity<?> delete(@PathVariable Integer qnaId){
        try{
            boolean result = qsvc.delete(qnaId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 관리자모드 QNA리스트 조회", response = List.class)
    @GetMapping("/admin")
    public ResponseEntity<?> adminSelect(@RequestParam(value = "keyword", required = false)String keyword){
        try{
            List<QNA> list;
            if(keyword == null || keyword.isEmpty()){
                list = qsvc.adminSelectAll("null");
            }else{
                list = qsvc.adminSelectAll(keyword);
            }
            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            log.info("QNAList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 관리자모드 답변대기글 조회", response = List.class)
    @GetMapping("/admin/answer")
    public ResponseEntity<?> adminSelect(){
        try{
            List<QNA> list = qsvc.noneAnswerAll();

            List<QNADto> result = list.stream()
                    .map(r -> new QNADto(r))
                    .collect(Collectors.toList());
            log.info("QNAList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
