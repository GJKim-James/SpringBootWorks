package kopo.poly.service.impl;

import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.persistance.redis.IMelonCacheMapper;
import kopo.poly.service.IMelonService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("MelonService")
public class MelonService implements IMelonService {

    @Resource(name = "MelonMapper")
    private IMelonMapper melonMapper; // MongoDB에 저장할 Mapper

    @Resource(name = "MelonCacheMapper")
    private IMelonCacheMapper melonCacheMapper; // RedisDB 실행을 위한 Mapper 추가

    @Override
    public int collectMelonSong() throws Exception {

        log.info(this.getClass().getName() + ".collectMelonSong Start!");

        int res = 0;

        List<MelonDTO> pList = new LinkedList<>();

        // 멜론 Top100 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/index.htm";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스를 저장할 변수
        Document doc = Jsoup.connect(url).get();

        // <div class="service_list_song"> 이 태그 내에 있는 HTML 소스만 element에 저장
        Elements element = doc.select("div.service_list_song");

        // 멜론 100위까지 차트
        for (Element songInfo : element.select("div.wrap_song_info")) {

            // 크롤링을 통해 데이터 저장하기
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text()); // 노래
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text()); // 가수

            log.info("song : " + song);
            log.info("singer : " + singer);

            // 가수와 노래 정보가 모두 수집되었다면, 저장하기
            if ((song.length() > 0) && (singer.length() > 0)) {

                MelonDTO pDTO = new MelonDTO();
                pDTO.setCollectTime(DateUtil.getDateTime("yyyyMMddhhmmss"));
                pDTO.setSong(song);
                pDTO.setSinger(singer);

                // 한 번에 여러 개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
                pList.add(pDTO);

            }
        }

        // 생성할 컬렉션명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // MongoDB에 데이터 저장하기
        res = melonMapper.insertSong(pList, colNm);

        // RedisDB에 데이터 저장하기
        res = melonCacheMapper.insertSong(pList, colNm);

        log.info(this.getClass().getName() + ".collectMelonSong End!");

        return res;

    }

    @Override
    public List<MelonDTO> getSongList() throws Exception {

        log.info(this.getClass().getName() + ".getSongList Start!");

        // MongoDB에 저장된 컬렉션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = null;

        // RedisDB에 저장되어 있는지 확인하기(Key 이름은 MongoDB 컬렉션 이름과 동일하게 사용)
        // RedisDB에 값이 있으면 RedisDB에서 가져오고, 없으면 MongoDB에서 가져오기(RedisDB에서 가져오는게 더 빠르기 때문)
        if (melonCacheMapper.getExistKey(colNm)) {
            rList = melonCacheMapper.getSongList(colNm); // RedisDB에서 데이터 가져오기

        } else {
            rList = melonMapper.getSongList(colNm); // MongoDB에서 데이터 가져오기

        }

        if (rList == null) {
            rList = new LinkedList<>();
        }

        log.info(this.getClass().getName() + ".getSongList End!");

        return rList;

    }

    @Override
    public List<MelonDTO> getSingerSongCnt() throws Exception {

        log.info(this.getClass().getName() + ".getSingerSongCnt Start!");

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = melonMapper.getSingerSongCnt(colNm);

        if (rList == null) {
            rList = new LinkedList<>();
        }

        log.info(this.getClass().getName() + ".getSingerSongCnt End!");

        return rList;

    }

    @Override
    public List<MelonDTO> getSingerSong() throws Exception {

        log.info(this.getClass().getName() + ".getSingerSong Start!");

        // MongoDB에 저장된 컬렉션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 수집된 데이터로부터 검색할 가수명
        String singer = "방탄소년단";

        // 결과값
        List<MelonDTO> rList = null;

        // Melon 노래 수집하기
        if (this.collectMelonSong() == 1) {
            rList = melonMapper.getSingerSong(colNm, singer);

            if (rList == null) {
                rList = new LinkedList<>();

            }

        } else {
            rList = new LinkedList<>();

        }

        log.info(this.getClass().getName() + ".getSingerSong End!");

        return rList;
    }

    @Override
    public int collectMelonSongMany() throws Exception {

        log.info(this.getClass().getName() + ".collectMelonSongMany Start!");

        int res = 0;

        List<MelonDTO> pList = new LinkedList<>();

        // 멜론 Top100 중 100위까지 정보 가져오는 페이지
        String url = "https://www.melon.com/chart/index.htm";

        // JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML 소스 저장할 변수
        Document doc = Jsoup.connect(url).get();

        // <div class="service_list_song"> 이 태그 내에서 있는 HTML 소스만 element에 저장됨
        Elements element = doc.select("div.service_list_song");

        // Iterator를 사용하여 멜론 차트 정보 가져오기
        // 멜론 100위까지 차트
        for (Element songInfo : element.select("div.wrap_song_info")) {

            // 크롤링을 통해 데이터 저장하기
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text()); // 노래
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text()); // 가수

            log.info("song : " + song);
            log.info("singer : " + singer);

            // 가수와 노래 정보가 모두 수집되었다면, 저장함
            if ((song.length() > 0) && (singer.length() > 0)) {

                MelonDTO pDTO = new MelonDTO();
                pDTO.setCollectTime(DateUtil.getDateTime("yyyyMMddhhmmss"));
                pDTO.setSong(song);
                pDTO.setSinger(singer);

                // 한번에 여러 개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기
                pList.add(pDTO);

            }

        }

        // 생성할 컬렉션명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // MongoDB에 데이터 저장하기
        res = melonMapper.insertSongMany(pList, colNm);

        log.info(this.getClass().getName() + ".collectMelonSongMany End!");

        return res;

    }

    @Override
    public int updateBTSName() throws Exception {

        log.info(this.getClass().getName() + ".updateBTSName Start!");

        int res = 0;

        // 수정할 컬렉션
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 기존 수집된 멜론 Top100 수집한 컬렉션 삭제하기
        melonMapper.dropMelonCollection(colNm);

        // 멜론 Top100 수집하기
        if (this.collectMelonSong() == 1) {

            // 수집된 데이터로부터 변경을 위해 찾을 가수명
            String singer = "방탄소년단";

            // 수집된 데이터로부터 변경할 가수명
            String updateSinger = "BTS";

            // singer 필드에 저장된 '방탄소년단' 값을 'BTS'로 변경하기
            res = melonMapper.updateSinger(colNm, singer, updateSinger);

        }

        log.info(this.getClass().getName() + ".updateBTSName End!");

        return res;
    }

    @Override
    public int updateAddBTSNickname() throws Exception {

        log.info(this.getClass().getName() + ".updateAddBTSNickname Start!");

        int res = 0;

        // 수정할 컬렉션
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 기존 수집된 멜론 Top100 수집한 컬렉션 삭제하기
        melonMapper.dropMelonCollection(colNm);

        // 멜론 Top100 수집하기
        if (this.collectMelonSong() == 1) {

            // 수집된 데이터로부터 변경을 위해 찾을 가수명
            String singer = "방탄소년단";

            // 수집된 데이터로부터 변경할 가수명
            String nickname = "BTS";

            // nickname 필드를 추가하고, nickname 필드 값은 'BTS'로 저장하기
            res = melonMapper.updateSongAddField(colNm, singer, nickname);

        }

        log.info(this.getClass().getName() + ".updateAddBTSNickname End!");

        return res;
    }

    @Override
    public int updateAddBTSMember() throws Exception {

        log.info(this.getClass().getName() + ".updateAddBTSMember Start!");

        int res = 0;

        // 수정할 컬렉션
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 기존 수집된 멜론 Top100 수집한 컬렉션 삭제하기
        melonMapper.dropMelonCollection(colNm);

        // 멜론 Top100 수집하기
        if (this.collectMelonSong() == 1) {

            // 수집된 데이터로부터 변경을 위해 찾을 가수명
            String singer = "방탄소년단";

            // 추가할 BTS 멤버, String 배열에 저장
            String[] member = {"정국", "뷔", "지민", "슈가", "진", "제이홉", "RM"};

            // MongoDB에 데이터 저장하기
            // Arrays.asList(member) => 배열 형태를 List<String> 타입으로 member 변경하기
            // 'member' 필드 추가하고, List<String> 타입으로 변경한 member를 값으로 추가
            res = melonMapper.updateSongAddListField(colNm, singer, Arrays.asList(member));

        }

        log.info(this.getClass().getName() + ".updateAddBTSMember End!");

        return res;
    }

    @Override
    public int updateManySong() throws Exception {

        log.info(this.getClass().getName() + ".updateManySong Start!");

        int res = 0;

        // 수정할 컬렉션
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 기존 수집된 멜론 Top100 수집한 컬렉션 삭제하기
        melonMapper.dropMelonCollection(colNm);

        // 멜론 Top100 수집하기
        if (this.collectMelonSong() == 1) {

            String singer = "방탄소년단"; // 수정할 가수 이름
            String updateSinger = "BTS"; // 변경될 가수 이름
            String updateSong = "BTS-SONG"; // 변경될 노래 제목

            res = melonMapper.updateManySong(colNm, singer, updateSinger, updateSong);

        }

        log.info(this.getClass().getName() + ".updateManySong End!");

        return res;
    }

    @Override
    public int deleteSong() throws Exception {

        log.info(this.getClass().getName() + ".deleteSong Start!");

        int res = 0;

        // 삭제할 컬렉션
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 수집된 데이터로부터 삭제할 가수명
        String singer = "방탄소년단";

        // MongoDB에 데이터 저장하기
        res = melonMapper.deleteSong(colNm, singer);

        log.info(this.getClass().getName() + ".deleteSong End!");

        return res;
    }

}
