<%--
    Document   : userImg
    Created on : May 12, 2023, 11:58:14 AM
    Author     : tuann
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:x-transition="http://www.w3.org/1999/xhtml">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <link rel="stylesheet" href="css/tailwind.css">
    </head>
    <body>

        <%
        HttpSession httpSession = request.getSession();
        Object obj = httpSession.getAttribute("greeting");
        %>
        <script src="js/alpine.js"></script>
        <div class="flex flex-wrap place-items-start">
            <section class="relative mx-auto">
                <!-- navbar -->
                <nav class="flex justify-between bg-blue-600 text-white w-screen">
                    <div class="px-5 xl:px-12 py-6 flex w-full h-20 items-center">
                        <a class="text-3xl font-bold font-heading" href="#">
                            <img class="h-12 w-15" src="img/logo2.png" alt="logo">

                        </a>
                        <!-- Nav Links -->
                        <ul class="hidden md:flex px-4 mx-auto font-semibold font-heading space-x-12">
                            <li><a class="hover:text-gray-200" href="#">Home</a></li>
                            <li><a class="hover:text-gray-200" href="#">Category</a></li>
                            <li><a class="hover:text-gray-200" href="#">Collections</a></li>
                            <li><a class="hover:text-gray-200" href="#">Contact Us</a></li>
                        </ul>
                        <!-- Header Icons -->
                        <div class="xl:flex items-center space-x-5 items-center">

                            <!-- Sign In / Register      -->
                            <div class="  flex justify-center items-center ">
                                <div x-data="{ open: false }" class="">
                                    <div @click="open = !open" class="  py-3"
                                          :class="{'border-indigo-700 transform transition duration-300 ': open}"
                                          x-transition:enter-end="transform opacity-100 scale-100"
                                          x-transition:leave="transition ease-in duration-75"
                                          x-transition:leave-start="transform opacity-100 scale-100">
                                        <div class="flex justify-center items-center space-x-3 cursor-pointer">
                                            <div class="w-12 h-12 rounded-full overflow-hidden border-2 dark:border-white border-gray-900">
                                                <img src="img/profile.jpg" alt="" class="w-full h-full object-cover">
                                            </div>
                                            <div class="font-semibold dark:text-white text-gray-900 text-lg">


                                                <div class="cursor-pointer">
                                                    <%if (obj == null) {%>
                                                    <a href="login">Login</a>
                                                    <%} else {%>
                                                    <div class="cursor-pointer"><%=obj%></div>
                                                    <%}%>

                                                </div>

                                            </div>
                                        </div>
                                        <%if (obj != null) { %>
                                        <div x-show="open" x-transition:enter="transition ease-out duration-100"
                                             x-transition:enter-start="transform opacity-0 scale-95"
                                             x-transition:enter-end="transform opacity-100 scale-100"
                                             x-transition:leave="transition ease-in duration-75"
                                             x-transition:leave-start="transform opacity-100 scale-100"
                                             x-transition:leave-end="transform opacity-0 scale-95"
                                             class="absolute w-50 px-5 py-3 dark:bg-gray-800 bg-black rounded-lg shadow border dark:border-transparent mt-5">
                                            <ul class="space-y-3 dark:text-white">
                                                <li class="font-medium">
                                                    <a href="forgotpassword"
                                                       class="flex items-center transform transition-colors duration-200 border-r-4 border-transparent hover:border-indigo-700">
                                                        <div class="mr-3">
                                                            <svg class="w-6 h-6" fill="none" stroke="currentColor"
                                                                 xmlns="http://www.w3.org/2000/svg">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                                                            </svg>
                                                        </div>
                                                        Change Password
                                                    </a>
                                                </li>
                                                <li class="font-medium">
                                                    <a href="#"
                                                       class="flex items-center transform transition-colors duration-200 border-r-4 border-transparent hover:border-indigo-700">
                                                        <div class="mr-3">
                                                            <svg class="w-6 h-6" fill="currentColor"
                                                                 xmlns="http://www.w3.org/2000/svg">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path>
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path>
                                                            </svg>
                                                        </div>
                                                        Setting
                                                    </a>
                                                </li>
                                                <hr class="dark:border-gray-700">
                                                <li class="font-medium">
                                                    <a href="logout"
                                                       class="flex items-center transform transition-colors duration-200 border-r-4 border-transparent hover:border-red-600">
                                                        <div class="mr-3 text-red-600">
                                                            <svg class="w-6 h-6" fill="none" stroke="currentColor"
                                                                 xmlns="http://www.w3.org/2000/svg">
                                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                                  stroke-width="2"
                                                                  d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
                                                            </svg>
                                                        </div>
                                                        Logout
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                        <% } %>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>

                </nav>

            </section>
        </div>
    </body>
</html>
