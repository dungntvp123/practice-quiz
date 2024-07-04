<%-- 
    Document   : forgotpass
    Created on : May 12, 2023, 9:46:59 AM
    Author     : tuann
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password</title>
        <link rel="stylesheet" href="css/tailwind.css">
    </head>
    <body>
        <div class="flex items-center min-h-screen bg-gray-50">
            <div class="flex-1 h-full max-w-4xl mx-auto bg-white rounded-lg shadow-xl">
                <div class="flex flex-col md:flex-row">
                    <div class="flex items-center justify-center p-12 sm:p-12 md:w-1/2 mx-auto"> <!-- Added 'mx-auto' class -->
                        <div class="w-full">
                            <div class="flex justify-center">
                                <svg xmlns="http://www.w3.org/2000/svg" class="w-20 h-20 text-blue-600" fill="none"
                                     viewBox="0 0 24 24" stroke="currentColor">
                                <path d="M12 14l9-5-9-5-9 5 9 5z" />
                                <path
                                    d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z" />
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                      d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222" />
                                </svg>
                            </div>
                            <h1 class="mb-4 text-2xl font-bold text-center text-gray-700">
                                Forgot Your Password
                            </h1>

                            <form action="forgotpassword" method="POST">
                                <div>
                                    <label class="block text-sm">
                                        Email
                                    </label>
                                    <input type="email"
                                           name="email"
                                           id="emailInput"
                                           class="w-full px-4 py-2 text-sm border rounded-md focus:border-blue-400 focus:outline-none focus:ring-1 focus:ring-blue-600"
                                           placeholder="Enter your email here..." required="You must enter your email to continue"/>
                                    <span id="emailError" class="text-red-500"></span>
                                </div>
                                <button class="block w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-center text-white transition-colors duration-150 bg-blue-600 border border-transparent rounded-lg active:bg-blue-600 hover:bg-blue-700 focus:outline-none focus:shadow-outline-blue"
                                        type="submit">
                                    Send OTP
                                </button>
                            </form>



                            <hr class="my-8"/>


                            <div class="flex items-center justify-center gap-4">

                                <!-- Google button -->

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            var emailInput = document.getElementById("emailInput");
            var emailError = document.getElementById("emailError");
            var domain = "@fpt.edu.vn";
            var domain2 = "@fu.edu.vn";

            emailInput.addEventListener("input", function () {
                var email = emailInput.value;
                if (email.endsWith(domain)||email.endsWith(domain2)) {
                    emailError.textContent = "";
                    emailInput.setCustomValidity("");
                } else {
                    emailError.textContent = "Invalid email domain. Expected: " + domain + " or " + domain2;
                    emailInput.setCustomValidity("Invalid email domain");
                }
            });

            emailInput.addEventListener("invalid", function (event) {
                event.preventDefault();
            });
        </script>
    </body>
</html>
