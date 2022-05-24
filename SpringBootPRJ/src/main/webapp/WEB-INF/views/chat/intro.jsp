<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String room_name = CmmUtil.nvl((String) request.getAttribute("SS_ROOM_NAME"));
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>채팅방 입장</title>

        <script src="/js/annyang.js"></script>
        <script src="/js/jquery-3.6.0.min.js"></script>
        <script>
            // html의 모든 객체(태그)들이 로딩이 완료된 경우 실행함
            $(window).on("load", function () {

                // 화면 로딩이 완료되면 첫 번째로 실행함
                getRoomList();

                // 두 번째부터 채팅방 리스트를 5초마다 로딩함
                setInterval("getRoomList()", 5000);

            });

            // 채팅방 리스트 가져오기
            function getRoomList() {

                // Ajax 호출
                $.ajax({
                    url: "/chat/roomList", // 채팅방 정보 가져올 URL
                    type: "post", // 전송 방식
                    dataType: "JSON", // 전달 받을 데이터 타입
                    contentType: "application/json; charset=UTF-8",
                    success: function (json) {

                        let roomList = "";


                        for (let i = 0; i < json.length; i++) {
                            roomList += (json[i] + "<br/>");
                        }

                        $("#room_list").html(roomList);
                        /*
                        Ajax는 비동기 처리 기술로 동시에 여러 개의 Ajax가 실행되면 충돌나서 에러 발생
                        보통 자바스크립트에서 제공하는 Promise를 통해 동시에 실행할 수 있지만,
                        간단한 화면은 Ajax의 실행 성공 함수에 함수 추가하는 것이 좋음
                         */

                        getAllMsg(); // 전체 대화 내용 가져오기

                    }
                })

            }

            // 채팅방 전체 대화 내용 가져오기
            function getAllMsg() {

                // Ajax 호출
                $.ajax({
                    url: "/chat/getMsg",
                    type: "post",
                    dataType: "JSON",
                    data: $("form").serialize(),
                    success: function (json) {
                        $("#view_json").html("getAllMsg : " + json);

                        // 메시지 출력하기
                        doOutputMsg(json);
                    }
                })

            }

            // 대화 메시지 전체 출력하기
            function doOutputMsg(json) {

                // 메시지 대화가 존재하면 출력
                if (json != null) {

                    let totalMsg = "";

                    for (let i = 0; i < json.length; i++) {
                        totalMsg += (json[i].msg + " | " + json[i].userNm + " | " + json[i].dateTime + "<br/>");
                    }

                    // 채팅방마다 한 줄씩 추가
                    $("#total_msg").html(totalMsg);

                }

            }

            /*
            ####################################################################################
                                            음성 인식 관련 소스 시작
            ####################################################################################
             */
            // annyang 라이브러리 실행
            annyang.start({
                autoRestart: true,
                continuous: true
            })

            // 음성 인식 값을 받아오기 위한 객체 생성
            var recognition = annyang.getSpeechRecognizer();

            // 말하는 동안에 인식되는 값 가져오기(허용)
            recognition.interimResults = false;

            // 음성 인식 결과 가져오기
            recognition.onresult = function (event) {

                let final_transcript = "";

                for (let i = event.resultIndex; i < event.results.length; ++i) {
                    if (event.results[i].isFinal) {
                        final_transcript += event.results[i][0].transcript;
                    }
                }

                $("#view_msg").html(final_transcript);
                $("#send_msg").val(final_transcript);

                // Ajax 호출
                $.ajax({
                    url: "/chat/msg",
                    type: "post",
                    dataType: "JSON",
                    data: $("form").serialize(),
                    success: function (json) {
                        $("#view_json").html("msg : " + json);

                        // 메시지 출력하기
                        doOutputMsg(json);
                    }
                })

            }
            /*
            ##################################################################################
                                            음성 인식 관련 소스 끝
            ##################################################################################
             */
        </script>
    </head>

    <body>
        <h1>[<%=room_name%>] 채팅방 '음성 대화' 전체 내용</h1>
        <hr>
        <div id="total_msg"></div>

        <h1>내가 방금 말한 전송 메시지</h1>
        <hr>
        <div id="view_msg"></div>
        <br>
        <h1>채팅방 전체 리스트</h1>
        <hr>
        <div id="room_list"></div>

        <!-- 음성 인식 데이터를 전송하기 위한 폼 -->
        <form name="form" method="post">
            <input type="hidden" name="send_msg" id="send_msg">
        </form>
    </body>

</html>