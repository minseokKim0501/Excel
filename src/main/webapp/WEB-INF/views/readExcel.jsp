<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }
        div {
            margin: 50px auto;
            padding: 20px;
            width: 50%;
            text-align: center;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.2);
        }
        h1 {
            color: #007bff;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div>
    <h1>전송이 완료되었습니다.</h1>
    <ul>
        <li>${dataList[0]}</li>
        <li>${dataList[1]}</li>
        <li>${dataList[2]}</li>
        <li>${dataList[3]}</li>
        <li>${dataList[4]}</li>
    </ul>
</div>
</body>
</html>
