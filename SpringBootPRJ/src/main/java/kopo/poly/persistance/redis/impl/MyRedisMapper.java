package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("MyRedisMapper")
public class MyRedisMapper implements IMyRedisMapper {

    // kopo.poly.config RedisConfiguration에서 설정한 RedisDB 객체 사용
    public final RedisTemplate<String, Object> redisDB;

    public MyRedisMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public int saveRedisString(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".saveRedisString Start!");

        int res = 0;

        String saveData = CmmUtil.nvl(pDTO.getTest_text()); // 저장할 값

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (!redisDB.hasKey(redisKey)) { // 데이터가 존재하지 않으면 저장하기

            // 데이터 저장하기
            redisDB.opsForValue().set(redisKey, saveData);

            // RedisDB에 저장되는 데이터의 유효시간 설정(TTL;Time To Live 설정)
            // 2일이 지나면, 자동으로 데이터가 삭제되도록 설정함
            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info("Save Data!!");

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisString End!");

        return res;
    }

    @Override
    public RedisDTO getRedisString(String redisKey) throws Exception {

        log.info(this.getClass().getName() + ".getRedisString Start!");

        log.info("String redisKey : " + redisKey);
        RedisDTO rDTO = new RedisDTO();

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (redisDB.hasKey(redisKey)) {

            String res = (String) redisDB.opsForValue().get(redisKey);

            log.info("res : " + res);

            // RedisDB에 저장된 데이터를 DTO에 저장하기
            rDTO.setTest_text(res);
        }

        log.info(this.getClass().getName() + ".getRedisString End!");

        return rDTO;
    }

    @Override
    public int saveRedisStringJSON(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".saveRedisStringJSON Start!");

        int res = 0;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (!redisDB.hasKey(redisKey)) {

            // 데이터 저장하기
            redisDB.opsForValue().set(redisKey, pDTO);

            // RedisDB에 저장되는 데이터의 유효시간 설정(TTL;Time To Live 설정)
            // 2일이 지나면, 자동으로 데이터가 삭제되도록 설정함
            redisDB.expire(redisKey, 2, TimeUnit.DAYS);

            log.info("Save Data!!");

            res = 1;
        }

        log.info(this.getClass().getName() + ".saveRedisStringJSON End!");

        return res;
    }

    @Override
    public RedisDTO getRedisStringJSON(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getRedisStringJSON Start!");

        log.info("String redisKey : " + redisKey);

        RedisDTO rDTO = null;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (redisDB.hasKey(redisKey)) {
            rDTO = (RedisDTO) redisDB.opsForValue().get(redisKey); // RedisDB에서 redisKey 가져와서 rDTO에 담기
        }

        log.info(this.getClass().getName() + ".getRedisStringJSON End!");

        return rDTO;
    }

    @Override
    public int saveRedisList(String redisKey, List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisList Start!");

        int res = 0;

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        for (RedisDTO dto : pList) {

            // 오름차순으로 저장하기
//            redisDB.opsForList().rightPush(redisKey, CmmUtil.nvl(dto.getTest_text()));

            // 내림차순으로 저장하기
            redisDB.opsForList().leftPush(redisKey, CmmUtil.nvl(dto.getTest_text()));

        }

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisList End!");

        return res;
    }

    @Override
    public List<String> getRedisList(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getRedisList Start!");

        // 결과값을 저장할 객체
        List<String> rList = null;

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1); // 전체 데이터 가져와서 rList에 담기
            // (List)는 형 변환을 의미한다. 위에 있는 List<String>와 동일한 형으로 변환된다.
        }

        log.info(this.getClass().getName() + ".getRedisList End!");

        return rList;
    }

    @Override
    public int saveRedisListJSON(String redisKey, List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisListJSON Start!");

        int res = 0;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        for (RedisDTO dto : pList) {

            // 오름차순으로 저장하기
            redisDB.opsForList().rightPush(redisKey, dto);

            // 내림차순으로 저장하기
//            redisDB.opsForList().leftPush(redisKey, dto);

        }

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisListJSON End!");

        return res;
    }

    @Override
    public List<RedisDTO> getRedisListJSON(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getRedisListJSON Start!");

        // 결과값을 저장할 객체
        List<RedisDTO> rList = null;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1); // 전체 데이터 가져와서 rList에 담기
            // (List)는 형 변환을 의미한다. 위에 있는 List<RedisDTO>와 동일한 형으로 변환된다.
            // 'List'는 Object(최상위 객체)를 의미하기 때문에 List<RedisDTO>도 받을 수 있다.
        }

        log.info(this.getClass().getName() + ".getRedisListJSON End!");

        return rList;
    }

    @Override
    public int saveRedisListJSONLambda(String redisKey, List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisListJSONLambda Start!");

        int res = 0;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        // 람다식은 순서에 상관없이 저장하기 때문에 오름차순, 내림차순은 중요하지 않음
        // 저장되는 데이터의 순서가 중요하다면 람다식을 사용하지 말 것!
        pList.forEach(dto -> redisDB.opsForList().rightPush(redisKey, dto));

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisListJSONLambda End!");

        return res;
    }

    @Override
    public int saveRedisHash(String redisKey, RedisDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisHash Start!");

        int res = 0;

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        redisDB.opsForHash().put(redisKey, "name", CmmUtil.nvl(pDTO.getName()));
        redisDB.opsForHash().put(redisKey, "email", CmmUtil.nvl(pDTO.getEmail()));
        redisDB.opsForHash().put(redisKey, "addr", CmmUtil.nvl(pDTO.getAddr()));

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 100분으로 정의
        redisDB.expire(redisKey, 100, TimeUnit.MINUTES);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisHash End!");

        return res;
    }

    @Override
    public RedisDTO getRedisHash(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getRedisHash Start!");

        // 결과값을 저장할 객체
        RedisDTO rDTO = new RedisDTO();

        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         * RedisDB에 데이터 저장하기 전에 반드시 저장될 데이터 타입을 사용한 타입과 동일하게 지정해주어야 한다.
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (redisDB.hasKey(redisKey)) {
            String name = CmmUtil.nvl((String) redisDB.opsForHash().get(redisKey, "name"));
            String email = CmmUtil.nvl((String) redisDB.opsForHash().get(redisKey, "email"));
            String addr = CmmUtil.nvl((String) redisDB.opsForHash().get(redisKey, "addr"));

            log.info("name : " + name);
            log.info("email : " + email);
            log.info("addr : " + addr);

            rDTO.setName(name);
            rDTO.setEmail(email);
            rDTO.setAddr(addr);
        }

        log.info(this.getClass().getName() + ".getRedisHash End!");

        return rDTO;
    }

    @Override
    public int saveRedisSetJSONLambda(String redisKey, Set<RedisDTO> pSet) throws Exception {
        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda Start!");

        int res = 0;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        // 데이터 저장하기
        // 람다식은 순서에 상관없이 저장하기 때문에 오름차순, 내림차순은 중요하지 않음
        // 저장되는 데이터의 순서가 중요하다면 람다식을 사용하지 말 것!
        pSet.forEach(dto -> redisDB.opsForSet().add(redisKey, dto));

        // 저장되는 데이터의 유효기간(TTL;Time To Live)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + ".saveRedisSetJSONLambda End!");

        return res;
    }

    @Override
    public Set<RedisDTO> getRedisSetJSONLambda(String redisKey) throws Exception {
        log.info(this.getClass().getName() + ".getRedisSetJSONLambda Start!");

        // 결과값을 저장할 객체
        Set<RedisDTO> rSet = null;

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // RedisDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if (redisDB.hasKey(redisKey)) {
            rSet = (Set) redisDB.opsForSet().members(redisKey);
            // (Set)은 형 변환을 의미한다. 위에 있는 Set<RedisDTO>와 동일한 형으로 변환된다.
            // 'Set'은 Object(최상위 객체)를 의미하기 때문에 Set<RedisDTO>도 받을 수 있다.
        }

        log.info(this.getClass().getName() + ".getRedisSetJSONLambda End!");

        return rSet;
    }

}
