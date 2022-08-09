package com.travel.travel_on.controller;

import com.travel.travel_on.auth.JwtUserDetails;
import com.travel.travel_on.dto.*;
import com.travel.travel_on.entity.Place;
import com.travel.travel_on.entity.VisitExpected;
import com.travel.travel_on.entity.VisitPlace;
import com.travel.travel_on.model.service.PlannerService;
import com.travel.travel_on.model.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/plan")
@Slf4j
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "방문장소 조회: 방문장소 리스트 조회", response = List.class)
    @GetMapping("/page")
    public ResponseEntity<?> searchVisitPlace(@ApiIgnore Authentication authentication){
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<VisitPlace> list;

            list = plannerService.selectVisitAll(userDto.toEntity());


            List<VisitPlaceDto> result = list.stream()
                    .map(r -> new VisitPlaceDto(r))
                    .collect(Collectors.toList());
            log.info("VisitPlaceList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "방문장소 조회: 방문장소 필터 리스트 조회", response = List.class)
    @PostMapping("/page/filter")
    public ResponseEntity<?> searchVisitFilter(@ApiIgnore Authentication authentication, @RequestBody FilterDto filter){
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<VisitPlace> list;
            list = plannerService.selectVisitFilter(filter);

            List<VisitPlaceDto> result = list.stream()
                    .map(r -> new VisitPlaceDto(r))
                    .collect(Collectors.toList());
            log.info("VisitPlaceList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "방문예정장소 조회: 방문예정장소 리스트 조회", response = List.class)
    @GetMapping("/expect/page")
    public ResponseEntity<?> searchVisitExpected(@ApiIgnore Authentication authentication){
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<VisitExpected> list;
            list = plannerService.selectExpectedAll(userDto.toEntity());

            List<VisitExpectedDto> result = list.stream()
                    .map(r -> new VisitExpectedDto(r))
                    .collect(Collectors.toList());
            log.info("VisitExpectedList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "방문예정장소 조회: 방문예정장소 필터 리스트 조회", response = List.class)
    @PostMapping("expect/page/filter")
    public ResponseEntity<?> searchExpectedFilter(@ApiIgnore Authentication authentication, @RequestBody FilterDto filter){
        try {
            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            List<VisitExpected> list;
            list = plannerService.selectExpectedFilter(filter);

            List<VisitExpectedDto> result = list.stream()
                    .map(r -> new VisitExpectedDto(r))
                    .collect(Collectors.toList());
            log.info("VisitExpectedList : {}", result.toString());
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글쓰기: 방문장소를 작성한다.")
    @PostMapping("/regist")
    public ResponseEntity<?> registVisitPlace(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            log.info("방문장소 작성하기");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Double rate = null;
            if(param.get("ratePoint") != null){
               rate = Double.parseDouble(param.get("ratePoint"));
            }
            VisitPlaceDto visitPlaceDto = new VisitPlaceDto(0, param.get("visitedPlace"), rate, param.get("review"), param.get("sidoName"), param.get("gugunName"), param.get("visitDate"));

            Place place = new Place();
            place.setVisitPlace(param.get("visitedPlace"));
            place.setSidoName(param.get("sidoName"));
            place.setGugunName(param.get("gugunName"));
            plannerService.writePlace(place);
            boolean result = plannerService.writeVisit(userDto, visitPlaceDto);

            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글쓰기: 방문예정장소를 작성한다.")
    @PostMapping("/expect/regist")
    public ResponseEntity<?> registExpectedPlace(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            log.info("방문예정장소 글쓰기");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VisitExpectedDto visitExpectedDto = new VisitExpectedDto(0, param.get("expectedPlace"), param.get("sidoName"), param.get("gugunName"), param.get("expectedDate"));

            Place place = new Place();
            place.setVisitPlace(param.get("expectedPlace"));
            place.setSidoName(param.get("sidoName"));
            place.setGugunName(param.get("gugunName"));
            plannerService.writePlace(place);
            boolean result = plannerService.writeExpected(userDto, visitExpectedDto);

            if(result){
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 방문장소 조회", response = QNADto.class)
    @GetMapping("/detail/{visitPlaceId}")
    public ResponseEntity<?> selectVisitOne(@ApiIgnore Authentication authentication, @PathVariable Integer visitPlaceId){
        try{
            log.info("방문장소 상세 조회");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VisitPlaceDto result = plannerService.selectVisitOne(visitPlaceId);

            if(result == null){
                return  new ResponseEntity<>(HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<VisitPlaceDto>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 조회: 선택한 방문예정장소 조회", response = QNADto.class)
    @GetMapping("/expect/detail/{visitExpectedId}")
    public ResponseEntity<?> selectExpectedOne(@ApiIgnore Authentication authentication, @PathVariable Integer visitExpectedId){
        try{
            log.info("방문예정장소 상세 조회");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VisitExpectedDto result = plannerService.selectExpectedOne(visitExpectedId);

            if(result == null){
                return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<VisitExpectedDto>(result, HttpStatus.OK);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: 방문장소 글 수정")
    @PutMapping("/modify")
    public ResponseEntity<?> modifyVisit(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> param){
        try{
            log.info("방문장소 수정");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VisitPlaceDto visitPlaceDto = plannerService.selectVisitOne(Integer.parseInt(param.get("visitPlaceId")));

            visitPlaceDto.setVisitPlaceId(Integer.parseInt(param.get("visitPlaceId")));
            visitPlaceDto.setVisitedPlace(param.get("visitedPlace"));
            visitPlaceDto.setRatePoint(Double.parseDouble(param.get("ratePoint")));
            visitPlaceDto.setReview(param.get("review"));
            visitPlaceDto.setSidoName(param.get("sidoName"));
            visitPlaceDto.setGugunName(param.get("gugunName"));
            visitPlaceDto.setVisitDate(param.get("visitDate"));

            boolean result = plannerService.updateVisit(visitPlaceDto);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 수정: 방문예정장소 글 삭제 후 방문장소로 이동")
    @PutMapping("/expect/modify/{visitExpectedId}")
    public ResponseEntity<?> modifyExpect(@ApiIgnore Authentication authentication, @PathVariable Integer visitExpectedId){
        try{
            log.info("방문예정장소 수정");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            VisitExpectedDto visitExpectedDto = plannerService.selectExpectedOne(visitExpectedId);

            VisitPlaceDto visit = new VisitPlaceDto();
            visit.setVisitedPlace(visitExpectedDto.getExpectedPlace());
            visit.setVisitDate(visitExpectedDto.getExpectedDate());
            visit.setSidoName(visitExpectedDto.getSidoName());
            visit.setGugunName(visitExpectedDto.getGugunName());

            if(!plannerService.writeVisit(userDto, visit)){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            boolean result = plannerService.deleteExpected(visitExpectedId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: 방문장소 글 삭제")
    @DeleteMapping("/delete/{visitPlaceId}")
    public ResponseEntity<?> deleteVisit(@ApiIgnore Authentication authentication, @PathVariable Integer visitPlaceId){
        try{
            log.info("방문장소 삭제");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            boolean result = plannerService.deleteVisit(visitPlaceId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 삭제: 방문예정장소 글 삭제")
    @DeleteMapping("/expect/delete/{visitExpectedId}")
    public ResponseEntity<?> deleteExpected(@ApiIgnore Authentication authentication, @PathVariable Integer visitExpectedId){
        try{
            log.info("방문장소 삭제");

            JwtUserDetails userDetails = (JwtUserDetails)authentication.getDetails();
            String userId = userDetails.getUsername();
            UserDto userDto = userService.select(userId);

            if(userDto == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            boolean result = plannerService.deleteExpected(visitExpectedId);
            if(result){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "글 완성: 키워드 자동완성")
    @PostMapping("/auto")
    public ResponseEntity<?> autoKeyword(@RequestParam(value = "key", required = false) String keyword){
        try{
            if(keyword == null)  return new ResponseEntity<>(HttpStatus.CONFLICT);
            log.info("자동완성");

            List<Place> list = plannerService.autoKeyward(keyword);

            return new ResponseEntity<List>(list, HttpStatus.OK);
        }catch (Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
