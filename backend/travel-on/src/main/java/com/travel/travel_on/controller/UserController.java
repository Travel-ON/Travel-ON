package com.travel.travel_on.controller;

import com.travel.travel_on.dto.Alarm;
import com.travel.travel_on.dto.User;
import com.travel.travel_on.dto.UserAchievement;
import com.travel.travel_on.dto.Visitation;
import com.travel.travel_on.model.service.AlarmService;
import com.travel.travel_on.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService usvc;

    @Autowired
    private AlarmService asvc;

    @ApiOperation(value = "회원가입: 사용자 정보를 삽입한다", response = Integer.class)
    @PostMapping("/regist")
    public ResponseEntity<?> regist(User user) {
        try {
            int result = usvc.insert(user);
            return new ResponseEntity<Integer>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "로그인: 사용자 정보를 조회한다", response = Integer.class)
    @PostMapping("/login")
    public ResponseEntity<?> login(String id, String password) {
        try {
            User user = usvc.select(id);
            int result = 0;
            if (user != null && user.getPassword().equals(password)) {
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "아이디 중복검사: 회원 정보에 중복되는 {id}가 있는지 조회한다", response = Integer.class)
    @PostMapping("/idcheck")
    public ResponseEntity<?> idcheck(String id) {
        try {
            User user = usvc.select(id);
            int result = 0;
            if (user != null) {
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            } else { // 아이디 사용 가능: 0 반환
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "닉네임 중복검사: 회원 정보에 중복되는 {nickname}이 있는지 조회한다", response = Integer.class)
    @PostMapping("/nickcheck")
    public ResponseEntity<?> nickcheck(String nickname) {
        try {
            User user = usvc.selectByNickname(nickname);
            int result = 0;
            if (user != null) {
                return new ResponseEntity<Integer>(++result, HttpStatus.OK);
            } else { // 닉네임 사용 가능: 0 반환
                return new ResponseEntity<Integer>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 조회: 사용자 정보를 조회한다", response = User.class)
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        try {
            User user = usvc.select(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원정보 수정: 사용자 정보를 수정한다", response = Integer.class)
    @PutMapping("/modify")
    public ResponseEntity<?> modify(User user) {
        try {
            int result = usvc.update(user);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "회원 탈퇴: 사용자 정보를 삭제한다", response = Integer.class)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            int result = usvc.delete(id);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "칭호 변경: 사용자 칭호를 수정한다", response = Integer.class)
    @PutMapping("/title")
    public ResponseEntity<?> modifyTitle(String id, String title) {
        try {
            User user = usvc.select(id);
            user.setUserTitle(title);
            int result = usvc.update(user);
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "칭호 조회: 사용자가 얻은 칭호리스트를 조회한다", response = List.class)
    @PostMapping("/title")
    public ResponseEntity<?> selectTitle(String id, String sidoName) {
        try {
            User user = usvc.select(id);
            List<UserAchievement> list = usvc.selectUserAchievement(user.getUserId(), sidoName);
            return new ResponseEntity<List<UserAchievement>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "비밀번호 찾기: 비밀번호를 초기화한 후 이메일로 전송한다", response = Integer.class)
    @PostMapping("/email")
    public ResponseEntity<?> resetPassword(String id, String email) {
        try {
            int result = 1;
            User user = usvc.select(id);
            if(user!=null&&user.getEmail().equals(email)){
                // 인증키 6자리 랜덤으로 생성 후 초기화
                String authKey = Integer.toString( ThreadLocalRandom.current().nextInt(100000, 1000000) );
                user.setPassword(authKey);
                usvc.update(user);
                usvc.sendMail(email,
                        "[Travel-ON] 비밀번호 초기화",
                        "안녕하세요 Travel-ON 입니다\n" +
                                "인증번호는 " + authKey + " 입니다.\n" +
                                "로그인 후 비밀번호를 변경해주세요.\n"+
                                "Travel-ON을 이용해주셔서 감사합니다."
                        );
                //이메일 전송
                result = 0;
            }
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "여행횟수 조회: 사용자의 지역별 여행횟수를 조회한다", response = List.class)
    @GetMapping("/trophy/{id}")
    public ResponseEntity<?> selectTrophy(@PathVariable String id) {
        try {
            User user = usvc.select(id);
            List<Visitation> list = usvc.selectVisitation(user.getUserId());
            return new ResponseEntity<List<Visitation>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "여행횟수 업데이트: 사용자의 위치인증한 지역의 여행횟수를 1 증가시킨다", response = Integer.class)
    @PostMapping("/trophy")
    public ResponseEntity<?> updateTrophy(String id, String sidoName) {
        try {
            int result = 0;
            User user = usvc.select(id);
            // 여행횟수 업데이트
            int count = usvc.updateVisitation(user.getUserId(), sidoName);
            // 업적 기준 확인
            String title = usvc.selectAchievement(count);
            // 칭호 획득
            if (title != null) {
                // 사용자 테이블에 칭호 넣기, 알림 업데이트
                UserAchievement userAchievement = UserAchievement.builder()
                        .userId(user.getUserId())
                        .sidoName(sidoName)
                        .title(title)
                        .build();
                usvc.insertUserAchievement(userAchievement);
                // 알림 내용 추가 (알린 내용 수정)
//                asvc.insert(user,"칭호획득: ["+sidoName+" "+title+"]");
                asvc.insert(user.getUserId(),"칭호획득: ["+sidoName+" "+title+"]");
            }
            return new ResponseEntity<Integer>(result, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
