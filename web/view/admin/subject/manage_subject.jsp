<%@ page import="entity.Subject" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 10-Jul-23
  Time: 9:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Subject subject = (Subject) request.getAttribute("subject");
  char mode = (char) request.getAttribute("mode");
  String nameErr = (String) request.getAttribute("nameErr");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Subject</title>
        <meta name="author" content="David Grzyb">
        <meta name="description" content="">

        <!-- Tailwind -->
        <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
        <style>
            @import url('https://fonts.googleapis.com/css?family=Karla:400,700&display=swap');
            .font-family-karla {
                font-family: karla;
            }
            .bg-sidebar {
                background: #3d68ff;
            }
            .cta-btn {
                color: #3d68ff;
            }
            .upgrade-btn {
                background: #1947ee;
            }
            .upgrade-btn:hover {
                background: #0038fd;
            }
            .active-nav-link {
                background: #1947ee;
            }
            .nav-item:hover {
                background: #1947ee;
            }
            .account-link:hover {
                background: #3d68ff;
            }
        </style>
    </head>
    <body class="bg-gray-100 font-family-karla flex">


        <div class="w-full flex flex-col h-screen overflow-y-hidden">
            <jsp:include page="/view/partials/navbar_admin.jsp"></jsp:include>

                <div class="w-full overflow-x-hidden border-t flex flex-col">
                    <main class="w-full flex-grow p-6">
                        <h1 class="text-3xl text-black pb-6"><%=mode == 'A' ? "Add Subject" : "Edit Subject " + subject.getId()%></h1>
                    <div class="container w-full md:w-4/5 xl:w-3/5 mx-auto px-2 rounded shadow bg-gray-100 mt-3">
                        <form method="post">
                            <label for="name">Name</label><input
                                class="w-full px-5 py-1 text-gray-700 bg-gray-200 rounded" id="name" name="name"
                                type="text" value="<%=((mode == 'E')?subject.getName():"")%>" oninput="validateSubjectName()">
                            <p id="name-error" class="text-red-500 text-sm hidden">Subject name length must be equal 6, first 3 characters are letters and the other are number</p>
                            <%if(nameErr != null){%>
                            <p class="text-red-500 text-sm">Subject name is existed</p>
                            <%}%>
                            <label for="chapters">Number Of Chapters</label><input
                                class="w-full px-5 py-1 text-gray-700 bg-gray-200 rounded" id="chapters" name="chapters"
                                type="number" min="0" value="<%=(mode == 'E')?subject.getNumOfChapter():""%>">
                            <p id="chap-error" class="text-red-500 text-sm hidden">Number of chap must contain number</p>
                            <input type="hidden" name="id" value="<%=subject.getId()%>">
                            <button class="px-4 py-1 text-white font-light tracking-wider bg-gray-900 rounded" id="submit-button" type="submit" disabled>
                                Submit
                            </button>
                        </form>
                    </div>

                </main>

            </div>

        </div>
        <script>
            const element = document.getElementById("submit-button");
            element.addEventListener("click", myFunction);

            function myFunction() {
                if (<%=(mode == 'E')%>) {
                    alert("Subject information has been updated");
                } else {
                    alert("New subjec has been added");
                }
            }
            function validateSubjectName() {
                var name = document.getElementById("name");
                var nameErr = document.getElementById("name-error");
                var button = document.getElementById("submit-button");
                var regex = /^[A-Za-z]{3}[0-9]{3}$/;
                if (!regex.test(name.value)) {
                    nameErr.classList.remove("hidden");
                    button.disabled = true;
                } else {
                    nameErr.classList.add("hidden");
                    button.disabled = false;
                }

            }


        </script> 
        <!-- AlpineJS -->
        <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
        <!-- Font Awesome -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" integrity="sha256-KzZiKy0DWYsnwMF+X1DvQngQ2/FxF7MF3Ff72XcpuPs=" crossorigin="anonymous"></script>
        <!-- ChartJS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js" integrity="sha256-R4pqcOYV8lt7snxMQO/HSbVCFRPMdrhAFMH+vr9giYI=" crossorigin="anonymous"></script>

    </body>
</html>
