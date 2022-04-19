package kopo.poly.service.impl;

import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.service.IMyRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("MyRedisService")
public class MyRedisService implements IMyRedisService {

    // RedisMapper 객체 가져오기
    @Resource(name = "MyRedisMapper")
    private IMyRedisMapper myRedisMapper;

    @Override
    public int saveRedisString() throws Exception {
        log.info(this.getClass().getName() + ".saveRedisString Start!");

        String redisKey = "myRedis_String"; // 저장할 키

        RedisDTO pDTO = new RedisDTO();
        pDTO.setTest_text("난 String 타입으로 저장할 일반 문자열이다."); // 저장할 데이터

        int res = myRedisMapper.saveRedisString(redisKey, pDTO); // 저장하기

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return res;
    }

    @Override
    public RedisDTO getRedisString() throws Exception {
        log.info(this.getClass().getName() + ".getRedisString Start!");

        String redisKey = "myRedis_String";

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

        String redisKey = "myRedis_String_JSON";

        RedisDTO pDTO = new RedisDTO();
        pDTO.setTest_text("난 String 타입의 JSON 형태로 저장할 일반 문자열이다.");
        pDTO.setName("김광진");
        pDTO.setAddr("수원");
        pDTO.setEmail("gjk0635@naver.com");

        int res = myRedisMapper.saveRedisStringJSON(redisKey, pDTO);

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return res;
    }
}
