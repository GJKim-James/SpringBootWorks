package kopo.poly.service.impl;

import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.service.IMyRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("MyRedisService")
public class MyRedisService implements IMyRedisService {

    // RedisMapper 객체 가져오기
    @Resource(name = "MyRedisMapper")
    private IMyRedisMapper myRedisMapper;

    @Override
    public int saveRedisString() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisString Start!");

        String redisKey = "myRedis_String"; // 저장할 키(key) 이름

        RedisDTO pDTO = new RedisDTO();
        pDTO.setTest_text("난 String 타입으로 저장할 일반 문자열이다."); // 저장할 데이터

        int res = myRedisMapper.saveRedisString(redisKey, pDTO); // 저장하기

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return res;
    }

    @Override
    public RedisDTO getRedisString() throws Exception {
        log.info(this.getClass().getName() + ".getRedisString Start!");

        String redisKey = "myRedis_String"; // 가져올 Redis 키(key)

        RedisDTO rDTO = myRedisMapper.getRedisString(redisKey);

        if (rDTO == null) {
            rDTO = new RedisDTO();
        }

        log.info(this.getClass().getName() + ".getRedisString End!");

        return rDTO;
    }

    @Override
    public int saveRedisStringJSON() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisStringJSON Start!");

        String redisKey = "myRedis_String_JSON"; // 저장할 키(key) 이름

        RedisDTO pDTO = new RedisDTO();
        pDTO.setTest_text("난 String 타입의 JSON 형태로 저장할 일반 문자열이다.");
        pDTO.setName("김광진");
        pDTO.setAddr("수원");
        pDTO.setEmail("gjk0635@naver.com");

        int res = myRedisMapper.saveRedisStringJSON(redisKey, pDTO);

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return res;
    }

    @Override
    public RedisDTO getRedisStringJSON() throws Exception {
        log.info(this.getClass().getName() + ".getRedisStringJSON Start!");

        String redisKey = "myRedis_String_JSON"; // 가져올 Redis 키(key)

        RedisDTO rDTO = myRedisMapper.getRedisStringJSON(redisKey); // 가져올 Redis 키의 값(value)

        if (rDTO == null) {
            rDTO = new RedisDTO();
        }

        log.info(this.getClass().getName() + ".getRedisStringJSON End!");

        return rDTO;
    }

    @Override
    public int saveRedisList() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisList Start!");

        String redisKey = "myRedis_List"; // 저장할 키(key) 이름

        List<RedisDTO> pList = new LinkedList<>(); // pList 변수 구현 방법을 LinkedList를 사용하여 순서가 변경되지 않도록 설정함

        for (int i = 0; i < 10; i++) {
            RedisDTO pDTO = new RedisDTO();
            pDTO.setTest_text(i + "번째 데이터입니다."); // myRedis_List에 저장할 값(value)

            pList.add(pDTO); // pDTO를 pList에 담기
            pDTO = null;
        }

        int res = myRedisMapper.saveRedisList(redisKey, pList);

        log.info(this.getClass().getName() + ".saveRedisList End!");

        return res;
    }

    @Override
    public List<String> getRedisList() throws Exception {
        log.info(this.getClass().getName() + ".getRedisList Start!");

        String redisKey = "myRedis_List"; // 가져올 Redis 키(key)

        List<String> rList = myRedisMapper.getRedisList(redisKey);

        if (rList == null) {
            rList = new LinkedList<>();
        }

        log.info(this.getClass().getName() + ".getRedisList End!");

        return rList;
    }

    @Override
    public int saveRedisListJSON() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisListJSON Start!");

        String redisKey = "myRedis_List_JSON"; // 저장할 키(key) 이름

        List<RedisDTO> pList = new LinkedList<>(); // pList 변수 구현 방법을 LinkedList를 사용하여 순서가 변경되지 않도록 설정함

        for (int i = 0; i < 10; i++) {
            RedisDTO pDTO = new RedisDTO();
            pDTO.setTest_text(i + "번째 데이터입니다."); // myRedis_List_JSON에 저장할 값(value)
            pDTO.setName("김광진[" + i + "]");
            pDTO.setAddr("수원");
            pDTO.setEmail("gjk0635@naver.com");

            pList.add(pDTO); // pDTO를 pList에 담기
            pDTO = null;
        }

        int res = myRedisMapper.saveRedisListJSON(redisKey, pList);

        log.info(this.getClass().getName() + ".saveRedisList End!");

        return res;
    }

    @Override
    public List<RedisDTO> getRedisListJSON() throws Exception {
        log.info(this.getClass().getName() + ".getRedisListJSON Start!");

        String redisKey = "myRedis_List_JSON"; // 가져올 Redis 키(key)

        List<RedisDTO> rList = myRedisMapper.getRedisListJSON(redisKey);

        if (rList == null) {
            rList = new LinkedList<>();
        }

        log.info(this.getClass().getName() + ".getRedisListJSON End!");

        return rList;
    }

    @Override
    public int saveRedisListJSONLambda() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisListJSONLambda Start!");

        String redisKey = "myRedis_List_JSON_Lambda"; // 저장할 키(key) 이름

        List<RedisDTO> pList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            RedisDTO pDTO = new RedisDTO();
            pDTO.setTest_text(i + "번째 데이터입니다."); // myRedis_List_JSON_Lambda에 저장할 값(value)
            pDTO.setName("김광진[" + i + "]");
            pDTO.setAddr("수원");
            pDTO.setEmail("gjk0635@naver.com");

            pList.add(pDTO);
            pDTO = null;
        }

        int res = myRedisMapper.saveRedisListJSONLambda(redisKey, pList);

        log.info(this.getClass().getName() + ".saveRedisListJSONLambda End!");

        return res;
    }

    @Override
    public List<RedisDTO> getRedisListJSONLambda() throws Exception {
        log.info(this.getClass().getName() + ".getRedisListJSONLambda Start!");

        String redisKey = "myRedis_List_JSON_Lambda"; // 가져올 Redis 키(key)

        List<RedisDTO> rList = myRedisMapper.getRedisListJSON(redisKey); // 이전에 만든 getRedisListJSON 호출

        if (rList == null) {
            rList = new LinkedList<>();
        }

        log.info(this.getClass().getName() + ".getRedisListJSONLambda End!");

        return rList;
    }

    @Override
    public int saveRedisHash() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisHash Start!");

        String redisKey = "myRedis_Hash"; // 저장할 키(key) 이름

        RedisDTO pDTO = new RedisDTO();

        // myRedis_Hash에 저장할 값(value)
        pDTO.setName("김광진");
        pDTO.setAddr("수원");
        pDTO.setEmail("gjk0635@naver.com");

        int res = myRedisMapper.saveRedisHash(redisKey, pDTO);

        log.info(this.getClass().getName() + ".saveRedisHash End!");

        return res;
    }

    @Override
    public RedisDTO getRedisHash() throws Exception {
        log.info(this.getClass().getName() + ".getRedisHash Start!");

        String redisKey = "myRedis_Hash"; // 가져올 Redis 키(key)

        RedisDTO rDTO = myRedisMapper.getRedisHash(redisKey);

        if (rDTO == null) {
            rDTO = new RedisDTO();
        }

        log.info(this.getClass().getName() + ".getRedisHash End!");

        return rDTO;
    }

    @Override
    public int saveRedisSetJSONLambda() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda Start!");

        String redisKey = "myRedis_Set_JSON"; // 저장할 키(key) 이름

        Set<RedisDTO> pSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            RedisDTO pDTO = new RedisDTO();
            pDTO.setTest_text(i + "번째 데이터입니다."); // myRedis_Set_JSON에 저장할 값(value)
            pDTO.setName("김광진[" + i + "]");
            pDTO.setAddr("수원");
            pDTO.setEmail("gjk0635@naver.com");

            pSet.add(pDTO);
            pDTO = null;
        }

        int res = myRedisMapper.saveRedisSetJSONLambda(redisKey, pSet);

        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda End!");

        return res;
    }

    @Override
    public Set<RedisDTO> getRedisSetJSONLambda() throws Exception {
        log.info(this.getClass().getName() + ".getRedisSetJSONLambda Start!");

        String redisKey = "myRedis_Set_JSON"; // 가져올 Redis 키(key)

        Set<RedisDTO> rSet = myRedisMapper.getRedisSetJSONLambda(redisKey);

        if (rSet == null) {
            rSet = new HashSet<>();
        }

        log.info(this.getClass().getName() + ".getRedisSetJSONLambda End!");

        return rSet;
    }

}
