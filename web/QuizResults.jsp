<%-- 
    Document   : QuizDetails
    Created on : Jun 11, 2023, 7:11:15 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="${pageContext.request.contextPath}/js/jquery-3.7.0.min.js"></script>
        <link rel="stylesheet" href="css/tailwind.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <title>Quiz Details</title>
    </head>
    <body class="flex flex-col min-h-screen">
        <header class="absolute top-0 text-4xl font-lg font-medium w-full bg-white border border-gray-300 shadow p-4 mb-4">Quiz Name: Results</header>
        <div class="flex items-center justify-center h-screen">
            <div class="flex">
                <div class="bg-blue-300 w-64 h-70 m-3 border border-gray-300 shadow p-6 flex items-center justify-center flex-col">
                    <div class="text-6xl text-lg font-bold p-4 text-white">9.5</div>
                    <div class="text-lg text-2x1 font-medium text-white">Score</div>
                </div>
                <div class="flex flex-col">
                    <div class="bg-green-500 w-40 h-40 m-3 bg-white border border-gray-300 shadow p-6 flex items-center justify-center flex-col">
                        <div class="text-2xl text-lg font-bold text-white">11 June</div>
                        <div class="text-2xl text-lg font-bold text-white">7:21 AM</div>
                        <div class="text-sm text-2x1 font-medium text-white pt-5">Started on</div>    
                    </div>
                    <div class="bg-green-500 w-40 h-40 m-3 bg-white border border-gray-300 shadow p-6 flex items-center justify-center flex-col">
                        <div class="text-2xl text-lg font-bold text-white">11 June</div>
                        <div class="text-2xl text-lg font-bold text-white">7:34 AM</div>
                        <div class="text-sm text-2x1 font-medium text-white pt-5">Finished on</div>   
                    </div>
                </div>
                <div class="flex flex-col">
                    <div class="bg-green-500 w-40 h-40 m-3 bg-white border border-gray-300 shadow p-6 flex items-center justify-center flex-col">
                        <div class="text-4xl text-lg font-bold p-3 text-white">13p53s</div>
                        <div class="text-sm text-2x1 font-medium text-white pt-5">Time taken</div>    
                    </div>
                    <div class="bg-green-500 w-40 h-40 m-3 bg-white border border-gray-300 shadow p-6 flex items-center justify-center flex-col">
                        <div class="text-4xl text-lg font-bold p-3 text-white">15</div>
                        <div class="text-sm text-2x1 font-medium text-white pt-5">Correct answer</div>    
                    </div>
                </div>
            </div>
        </div>
        <footer class="absolute bottom-0 w-full bg-white text-white p-4 flex justify-between border border-gray-300 shadow mt-4">
            <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded">View Details <i class="fa-solid fa-clipboard-list"></i></button>
            <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Back <i class="fa-solid fa-rotate-left"></i></button>
        </footer>
    </body>


</html>
