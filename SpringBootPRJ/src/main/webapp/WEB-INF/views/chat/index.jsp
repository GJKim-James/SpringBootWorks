<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>채팅방 입장 및 채팅 리스트</title>

        <script src="/js/jquery-3.6.0.min.js"></script>
        <script type="text/javascript">
            let myChatRoom = "";

            // html의 모든 객체(태그)들이 로딩이 완료된 경우 실행함
            $(window).on('load', function () {
                // 화면 로딩이 완료되면 첫 번째로 실행함
                getRoomList(); // 전체 채팅방 리스트 가져오기
            });

            // 전체 채팅방 리스트 가져오기
            function getRoomList() {

                // Ajax 호출
                $.ajax({
                    url: "/chat/roomList", // 채팅방 정보 가져올 URL
                    type: "post", // 전송 방식
                    dataType: "JSON", // 전달 받을 데이터 타입
                    contentType: "application/json; charset=UTF-8",
                    success: function (json) {
                        for (let i = 0; i < json.length; i++) {
                            $("#room_list").append(json[i] + "<br/>"); // 채팅방마다 한 줄씩 추가
                        }
                    }
                })

            }
        </script>
    </head>

    <body>
        <h1>채팅방 전체 리스트</h1>
        <hr>
        <div id="room_list"></div>
        <br>
        <br>
        <h1>채팅방 입장 정보</h1>
        <hr>
        <form name="form" method="post" action="/chat/intro">
            <label for="room_name">채팅방 이름 : </label><input type="text" name="room_name" id="room_name">
            <br>
            <label for="user_name">채팅자 이름 : </label><input type="text" name="user_name" id="user_name">
            <br>
            <input type="submit" value="입장">
        </form>
    </body>

</html>