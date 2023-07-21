<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>액셀 파일 업로드</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        form {
            width: 50%;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.2);
        }

        input[type=file] {
            display: block;
            margin-bottom: 10px;
        }

        input[type=submit] {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #3e8e41;
        }
    </style>
</head>
<body>
<h1>액셀 파일 업로드</h1>
<form action="/storeExcel" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".xls,.xlsx">
    <br><br>
    <input type="submit" value="파일 업로드">
</form>
<button type="button">검증</button>
</body>
</html>
