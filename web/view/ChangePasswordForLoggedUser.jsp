<%@ page import="entity.User" %>
<%@ page import="utility.Utility" %><%--
  Created by IntelliJ IDEA.
  User: tuann
  Date: 6/16/2023
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Change Password</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">

</head>
<body>
<%
    User userLogged = (User) request.getAttribute("userLogged");
    String Error  = (String) request.getSession().getAttribute("ErrorWhenChangePass");
%>

<script src="../js/ResetPassForLoggedUser.js"></script>
<div class="flex items-center min-h-screen bg-gray-50">
    <div class="flex-1 h-full max-w-4xl mx-auto bg-white rounded-lg shadow-xl">
        <div class="flex flex-col md:flex-row">
            <div class="flex items-center justify-center p-12 sm:p-12 md:w-1/2 mx-auto"> <!-- Added 'mx-auto' class -->
                <div class="w-full">


                    <div class="flex justify-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-20 h-20 text-blue-600" fill="none"
                             viewBox="0 0 24 24" stroke="currentColor">
                            <path d="M12 14l9-5-9-5-9 5 9 5z"/>
                            <path
                                    d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z"/>
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222"/>
                        </svg>
                    </div>
                    <h1 class="mb-4 text-2xl font-bold text-center text-gray-700">
                        Change Your Password
                    </h1>
                    <form action="ChangePassForLoggedUser" method="POST">
                        <input class="hidden" type="text" value="<%=Utility.decode(userLogged.getPassword())%>"
                               id="lasspass"
                               name="lasspass" hidden="">
                        <input class="hidden" type="text" value="<%=userLogged.getId()%>" id="IdOfUserToChange"
                               name="IdOfUserToChange" hidden="">
                        <input type="text" class="hidden" value="<%=userLogged.getUsername()%>"
                               name="UsernameOfLoggedUser">
                        <%if (Error != null) {%>

                            <div class="flex p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400"
                                 role="alert">
                                <svg class="flex-shrink-0 inline w-4 h-4 mr-3 mt-[2px]" aria-hidden="true"
                                     xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                                </svg>
                                <span class="sr-only">Danger</span>
                                <div>
                                    <p class="font-bold">Error </p>
                                    <p class="font-medium">Not Match With Previous Password </p>
                                </div>
                            </div>

                        <%}%>
                        <div>

                            <label class="block mt-4 text-sm">Current Password</label>
                            <input class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                   placeholder="" type="password" name="CurentPassword" id="CurentPassword"
                                   required/>
                            <p id="password-error-check" class="text-red-500 text-sm hidden">Not match previous
                                password</p>
                        </div>
                        <div>

                            <label class="block mt-4 text-sm">Password</label>
                            <input class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                   placeholder="" type="password" name="newpassword" id="newpassword"
                                   oninput="validatePasswordCharacter1()"/>
                            <p id="password-error-input" class="text-red-500 text-sm hidden">Invalid password. It should
                                contain a special character like "@ _ & %", at least one number, and at least one
                                uppercase character.</p>
                        </div>
                        <div>
                            <label class="block mt-4 text-sm">Repeat Password</label>
                            <input class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                   placeholder="" type="password" name="repeat-password" id="repeat-password"
                                   oninput="validatePassword1()" required/>
                            <p id="password-error" class="text-red-500 text-sm hidden">Passwords do not match</p>
                        </div>
                        <button class="block w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-center text-white transition-colors duration-150 bg-blue-600 border border-transparent rounded-lg active:bg-blue-600 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue"
                                id="login-button" disabled>
                            Reset Password
                        </button>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/index.js"></script>

</body>

</html>
