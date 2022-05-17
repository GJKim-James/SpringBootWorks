package kopo.poly.controller;

import kopo.poly.dto.RedisDTO;
import kopo.poly.service.IMyRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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

    /**
     * Redis에 String 타입의 JSON 형태로 저장된 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisStringJSON")
    public RedisDTO getRedisStringJSON() throws Exception {

        log.info(this.getClass().getName() + ".getRedisStringJSON Start!");

        RedisDTO rDTO = myRedisService.getRedisStringJSON();

        log.info(this.getClass().getName() + ".getRedisStringJSON End!");

        return rDTO;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장하기(동기화)
     */
    @GetMapping(value = "redis/saveRedisList")
    public String saveRedisList() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisList Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisList();

        if (res == 1) {
            msg = "키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장하기 success";

        } else {
            msg = "키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisList End!");

        return msg;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 여러 문자열로 저장된 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisList")
    public List<String> getRedisList() throws Exception {

        log.info(this.getClass().getName() + ".getRedisList Start!");

        List<String> rList = myRedisService.getRedisList();

        log.info(this.getClass().getName() + ".getRedisList End!");

        return rList;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장하기(동기화)
     */
    @GetMapping(value = "redis/saveRedisListJSON")
    public String saveRedisListJSON() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisListJSON Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisListJSON();

        if (res == 1 ) {
            msg = "키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장하기 success";

        } else {
            msg = "키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisListJSON End!");

        return msg;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 저장된 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisListJSON")
    public List<RedisDTO> getRedisListJSON() throws Exception {

        log.info(this.getClass().getName() + ".getRedisListJSON Start!");

        List<RedisDTO> rList = myRedisService.getRedisListJSON();

        log.info(this.getClass().getName() + ".getRedisListJSON End!");

        return rList;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기(비동기화)
     */
    @GetMapping(value = "redis/saveRedisListJSONLambda")
    public String saveRedisListJSONLambda() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisListJSONLambda Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisListJSONLambda();

        if (res == 1) {
            msg = "키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기 success";

        } else {
            msg = "키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisListJSONLambda End!");

        return msg;
    }

    /**
     * 키(key)는 List 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장한 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisListJSONLambda")
    public List<RedisDTO> getRedisListJSONLambda() throws Exception {

        log.info(this.getClass().getName() + ".getRedisListJSONLambda Start!");

        List<RedisDTO> rList = myRedisService.getRedisListJSONLambda();

        log.info(this.getClass().getName() + ".getRedisListJSONLambda End!");

        return rList;
    }

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장하기
     */
    @GetMapping(value = "redis/saveRedisHash")
    public String saveRedisHash() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisHash Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisHash();

        if (res == 1) {
            msg = "키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장하기 success";

        } else {
            msg = "키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisHash End!");

        return msg;
    }

    /**
     * 키(key)는 Hash 타입으로, 값(value)은 문자열 형태로 저장된 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisHash")
    public RedisDTO getRedisHash() throws Exception {

        log.info(this.getClass().getName() + ".getRedisHash Start!");

        RedisDTO rDTO = myRedisService.getRedisHash();

        log.info(this.getClass().getName() + ".getRedisHash End!");

        return rDTO;
    }

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기
     */
    @GetMapping(value = "redis/saveRedisSetJSONLambda")
    public String saveRedisSetJSONLambda() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisSetJSONLambda();

        if (res == 1) {
            msg = "키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기 success";

        } else {
            msg = "키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda End!");

        return msg;
    }

    /**
     * 키(key)는 Set 타입으로, 값(value)은 JSON 형태로 람다식을 이용하여 저장한 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisSetJSONLambda")
    public Set<RedisDTO> getRedisSetJSONLambda() throws Exception {

        log.info(this.getClass().getName() + ".getRedisSetJSONLambda Start!");

        Set<RedisDTO> rSet = myRedisService.getRedisSetJSONLambda();

        log.info(this.getClass().getName() + ".getRedisSetJSONLambda End!");

        return rSet;
    }

    /**
     * 키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장하기
     */
    @GetMapping(value = "redis/saveRedisZSetJSON")
    public String saveRedisZSetJSON() throws Exception {

        log.info(this.getClass().getName() + ".saveRedisZSetJSON Start!");

        // 수집 결과 출력
        String msg;

        int res = myRedisService.saveRedisZSetJSON();

        if (res == 1) {
            msg = "키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장하기 success";

        } else {
            msg = "키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장하기 fail";
        }

        log.info(this.getClass().getName() + ".saveRedisZSetJSON End!");

        return msg;
    }

    /**
     * 키(key)는 ZSet 타입으로, 값(value)은 JSON 형태로 저장된 데이터 가져오기
     */
    @GetMapping(value = "redis/getRedisZSetJSON")
    public Set<RedisDTO> getRedisZSetJSON() throws Exception {

        log.info(this.getClass().getName() + ".getRedisZSetJSON Start!");

        Set<RedisDTO> rSet = myRedisService.getRedisZSetJSON();

        log.info(this.getClass().getName() + ".getRedisZSetJSON End!");

        return rSet;
    }

    /**
     * RedisDB에 JSON 구조로 저장된 데이터 삭제하기
     */
    @GetMapping(value = "redis/deleteJSONData")
    public boolean deleteJSONData() throws Exception {

        log.info(this.getClass().getName() + ".deleteJSONData Start!");

        boolean res = myRedisService.deleteJSONData();

        log.info(this.getClass().getName() + ".deleteJSONData End!");

        return res;
    }

    /**
     * RedisDB에 String 구조로 저장된 데이터 삭제하기
     */
    @GetMapping(value = "redis/deleteStringData")
    public boolean deleteStringData() throws Exception {

        log.info(this.getClass().getName() + ".deleteStringData Start!");

        boolean res = myRedisService.deleteStringData();

        log.info(this.getClass().getName() + ".deleteStringData End!");

        return res;
    }

}
