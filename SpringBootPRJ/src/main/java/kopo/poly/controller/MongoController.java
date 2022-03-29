package kopo.poly.controller;

import kopo.poly.service.IMongoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController // return 값이 DTO면, DTO에 존재하는 모든 변수를 자동으로 JSON으로 변환해주는 Spring의 @Responsebody와 똑같은 역할
public class MongoController {

    // Map 객체를 사용한 데이터 처리
    @Resource(name = "MongoService")
    private IMongoService mongoService;

    @GetMapping(value = "mongo/test")
    public String test() throws Exception {

        log.info(this.getClass().getName() + ".test Start!");

        mongoService.mongoTest();

        log.info(this.getClass().getName() + ".test End!");

        return "MongoDB Test Data 저장 완료!! --> Studio 3T MONGODB_TEST 컬렉션 확인!";
    }
}
