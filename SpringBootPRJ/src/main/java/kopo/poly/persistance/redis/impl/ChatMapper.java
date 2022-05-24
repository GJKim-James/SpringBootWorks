package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.ChatDTO;
import kopo.poly.persistance.redis.IChatMapper;
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
@Component("ChatMapper")
public class ChatMapper implements IChatMapper {

    // RedisDB 객체 가져오기(@Autowired와 똑같은 기능)
    public final RedisTemplate<String, Object> redisDB;

    public ChatMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    @Override
    public Set<String> getRoomList() throws Exception {

        log.info(this.getClass().getName() + ".getRoomList(채팅방 리스트 가져오기) Start!");

        // RedisDB에서 이름이 'Chat'으로 시작하는 Key만 가져오기
        Set<String> rSet = (Set) redisDB.keys("Chat*");

        log.info(this.getClass().getName() + ".getRoomList(채팅방 리스트 가져오기) End!");

        return rSet;

    }

    @Override
    public int insertChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertChat(채팅 대화 내용 저장하기) Start!");

        int res = 0;

        // 채팅방 키(key) 정보
        String roomKey = CmmUtil.nvl(pDTO.getRoomKey());

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // ChatDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class)); // JSON 타입

        // RedisDB에 채팅 대화 내용 저장하기
        redisDB.opsForList().leftPush(roomKey, pDTO);

        res = 1;

        log.info(this.getClass().getName() + ".insertChat(채팅 대화 내용 저장하기) End!");

        return res;

    }

    @Override
    public List<ChatDTO> getChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getChat(채팅 대화 내용 가져오기) Start!");

        // RedisDB에서 가져온 결과를 저장할 객체
        List<ChatDTO> rList = null;

        // 채팅방 키(key) 정보
        String roomKey = CmmUtil.nvl(pDTO.getRoomKey());

        // RedisDB의 키 데이터 타입을 String으로 정의(항상 String으로 설정함)
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

        // ChatDTO에 저장된 데이터를 자동으로 JSON으로 변경하기
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class)); // JSON 타입

        if (redisDB.hasKey(roomKey)) {

            // RedisDB에 저장된 전체 레코드 가져오기
            rList = (List) redisDB.opsForList().range(roomKey, 0, -1);

        }

        log.info(this.getClass().getName() + ".getChat(채팅 대화 내용 가져오기) End!");

        return rList;

    }

    @Override
    public boolean setTimeOutHour(String roomKey, int hours) throws Exception {
        log.info(this.getClass().getName() + ".setTimeOutHour(데이터 유효시간을 시간 단위로 설정하기) Start!");
        return redisDB.expire(roomKey, hours, TimeUnit.HOURS); // 데이터 유효시간(TTL)을 시간(Hours) 단위로 설정
    }

    @Override
    public boolean setTimeOutMinute(String roomKey, int minutes) throws Exception {
        log.info(this.getClass().getName() + ".setTimeOutMinute(데이터 유효시간을 분 단위로 설정하기) Start!");
        return redisDB.expire(roomKey, minutes, TimeUnit.MINUTES); // 데이터 유효시간(TTL)을 분(Minutes) 단위로 설정
    }

}
