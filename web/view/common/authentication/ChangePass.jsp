<%-- 
    Document   : ChangePass
    Created on : May 12, 2023, 1:01:06 PM
    Author     : tuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Change Password</title>
        <link rel="stylesheet" href="css/tailwind.css">

    </head>
    <body>
        <%String email = request.getParameter("email");%>
        <%String confirm = request.getParameter("confirm");%>
        <script src="js/index.js"></script>
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
                            <form action="changepassword" method="POST">
                                <div>
                                    <input type="hidden" name="confirm" value="<%=confirm%>">
                                    <input type="hidden" name="email" value="<%=email%>">
                                    <label class="block mt-4 text-sm">Password</label>
                                    <input class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                           placeholder="" type="password" name="password" id="password" oninput="validatePasswordCharacter()"/>
                                    <p id="password-error-input" class="text-red-500 text-sm hidden">Invalid password. It should contain a special character like "@ _ & %", at least one number, and at least one uppercase character.</p>
                                </div>
                                <div>
                                    <label class="block mt-4 text-sm">Repeat Password</label>
                                    <input class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                           placeholder="" type="password" name="repeat-password" id="repeat-password"
                                           oninput="validatePassword()"/>
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

    </body>
    
    <script>
        const element = document.getElementById("login-button");
        element.addEventListener("click", myFunction);

        function myFunction() {
            alert("Your password has been change!");
        }
    </script>
</html>