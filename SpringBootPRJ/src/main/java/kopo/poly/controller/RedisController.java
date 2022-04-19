package kopo.poly.controller;

import kopo.poly.dto.RedisDTO;
import kopo.poly.service.IMyRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class RedisController {

    // 서비스 객체 가져오기
    @Resource(name = "MyRedisService")
    private IMyRedisService myRedisService;

    /**
     * RedisDB에 문자열 저장하기
     */
    @GetMapping(value = "redis/saveRedisString")
    public String saveRedisString() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisString Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisString(); // 서비스 호출

        if (res == 1) {
            msg = "RedisDB에 문자열 데이터 저장하기 success";

        } else {
            msg = "RedisDB에 문자열 데이터 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return msg;
    }

    /**
     * RedisDB에서 문자열로 저장된 값 가져오기
     */
    @GetMapping(value = "redis/getRedisString")
    public RedisDTO getRedisString() throws Exception {

        log.info(this.getClass().getName() + ".getRedisString Start!");

        RedisDTO rDTO = myRedisService.getRedisString();

        log.info(this.getClass().getName() + ".getRedisString End!");

        return rDTO;
    }

    /**
     * RedisDB에 문자열을 JSON 형태로 저장하기
     */
    @GetMapping(value = "redis/saveRedisStringJSON")
    public String saveRedisStringJSON() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisStringJSON Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisStringJSON();

        if (res == 1) {
            msg = "RedisDB에 JSON 형태로 저장하기 success";

        } else {
            msg = "RedisDB에 JSON 형태로 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return msg;
    }
}
