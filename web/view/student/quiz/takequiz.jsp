<%-- 
    Document   : takequiz
    Created on : May 25, 2023, 5:13:59 PM
    Author     : admin
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Question,entity.Selection,java.util.List,java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="${pageContext.request.contextPath}/js/jquery-3.7.0.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <title>Take Quiz</title>

    </head>
    <jsp:include page="../../partials/navbar.jsp"></jsp:include>
    <%Map<Question, List<Selection>> getTest = (Map<Question, List<Selection>>) request.getSession(false).getAttribute("test");%>


    <body class="flex flex-col min-h-screen" id="html-body">
        <form action="doexam" method="post" id="myform">
            <div class="flex items-center justify-center h-screen">
                <div class="bg-white p-6 border border-gray-300 shadow w-3/4">
                    <div class="flex items-center justify-between mb-2">
                        <div class="text-3xl text-lg font-medium text-blue-500"><i class="fa-solid fa-comments"></i><div id="quesnum"></div></div>

                        <div class="flex space-x-4">
                            <div class="text-2xl text-white text-lg font-medium bg-blue-300 p-2 rounded" ><i class="fa-solid fa-map-location-dot"></i><div id="i-th"></div></div>
                            <div id="timer" class="text-2xl text-white text-lg font-medium bg-green-300 p-2 rounded">00:00:00</div>
                            <i class="fa-regular fa-clock text-5xl text-lg font-medium text-green-300"></i>
                        </div>
                    </div>

                    <div class="text-2xl font-lg mb-4 text-gray-700" id="desc" onselectstart="return false"></div>
                    <div id="sls">

                    </div>

                </div>
                <footer class="absolute bottom-0 w-full bg-white text-white p-4 flex justify-between border border-gray-300 shadow mt-4">
                    <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" id="btn-back" type="button"><i class="fa-solid fa-circle-chevron-left"></i> Previous</button>
                    <button id="open-modal-button" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded" type="button">Review Progress <i class="fa-solid fa-bars-progress"></i></button>
                    <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" id="btn-next" type="button">Next <i class="fa-solid fa-circle-chevron-right"></i></button>
                </footer>

                <!-- The modal -->
                <div id="modal" class="fixed z-10 inset-0 overflow-y-auto hidden">
                    <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
                        <div class="fixed inset-0 transition-opacity">
                            <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
                        </div>
                        <span class="hidden sm:inline-block sm:align-middle sm:h-screen">&#8203;</span>
                        <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                            <div class="bg-white px-4 pt-5 pb-4">
                                <div class="flex justify-between mb-4">
                                    <h3 class="text-lg font-medium text-gray-600 text-4x1">Question Navigation</h3>
                                    <input type="submit" id="submit-modal-button" type="submit" class="border border-transparent shadow-sm bg-red-600 text-lg font-medium text-white hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 p-1 rounded mr-2" value="Submit">
                                </div>
                                <ul class="flex flex-wrap items-center -space-x-px" id="ule">

                                </ul>    


                            </div>
                            <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                                <button id="close-modal-button" type="button" class="border border-transparent shadow-sm bg-red-600 text-lg font-medium text-white hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 p-1 rounded">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
    <script>
        <%
            String timeout = (String) request.getSession(false).getAttribute("finishedtime");
            if (timeout == null){
                timeout = "Jun 30, 2023 10:12:00"; //Use this format
            }
        %>
        let timeup = new Date("<%=timeout%>").getTime();
        let counter = setInterval(timer, 200);

        function timer() {
            let now = new Date().getTime();
            if (now > timeup) {
                alert("Your submission has been saved. Back to Main Page");
                const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);

                let answer = sel.join("");
                let current = f("current");

                document.cookie = "" + current + "=" + answer + "; path=/";
                document.getElementById("myform").submit();
                clearInterval(counter);
                return;
            }
            let count = Math.round((timeup - now) / 1000);
            let hours = Math.floor(count / 3600);
            count -= hours * 3600;
            let minutes = Math.floor(count / 60);
            count -= minutes * 60;
            let seconds = count;
            let timerElement = $("#timer");
            timerElement.html(getFormattedTime(hours, minutes, seconds));
            if (minutes < 5) {
                timerElement.addClass("text-red-600");
            }
        }
    </script>
    <script>
        function pad(num, size) {
            num = num.toString();
            while (num.length < size)
                num = "0" + num;
            return num;
        }

        function getFormattedTime(hours, minutes, seconds) {
            let formattedTime = "";
            if (hours > 0) {
                formattedTime += pad(hours, 2) + ":";
            }
            formattedTime += pad(minutes, 2) + ":" + pad(seconds, 2);
            return formattedTime;
        }
    </script>

    <script>

        const openModalButton = document.querySelector('#open-modal-button');
        const closeModalButton = document.querySelector('#close-modal-button');
        const modal = document.querySelector('#modal');

        openModalButton.addEventListener('click', () => {
            modal.classList.remove('hidden');
        });

        closeModalButton.addEventListener('click', () => {
            modal.classList.add('hidden');
        });
    </script>

    <script>

        let obj = {};
        let select = [];
        let clt = [];
        <%for (Map.Entry<Question, List<Selection>> entry : getTest.entrySet()) {
        %>
        obj = {};
        select = [];
        obj.question = <%=entry.getKey()%>;
        <% for (Selection select : entry.getValue()) { %>
        select.push(<%=select.toJSString()%>);
        <% } %>
        obj.selection = select;
        clt.push(obj);

        <% } %>
        for (let i = 1; i <= obj.length; ++i) {
            document.cookie = i + "=; path=/";
        }

        let f = (name) => {
            if (document.cookie === "")
                return;
            let ck = decodeURIComponent(document.cookie).split(";");
            return ck.filter(e => e.trim().indexOf(name + "=") === 0).map(x => x.trim().substring((name + "=").length, x.length))[0];
        };

        clt.forEach(e => {
            let rvelm = document.createElement('li');
            rvelm.setAttribute("class", "mb-5");
            let libtn = document.createElement('button');
            libtn.setAttribute("class", "px-3 py-2 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700");
            libtn.setAttribute("id", "rv" + (Number.parseInt(clt.indexOf(e)) + 1));
            libtn.setAttribute("type", "button");
            libtn.onclick = (e) => {
                const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
                let answer = sel.join("");
                let current = f("current");
                if (sel.length !== 0)
                    document.getElementById("rv" + current).setAttribute('style', 'background-color: orange;');
                else
                    document.getElementById("rv" + current).setAttribute('style', 'background-color: white;');
                document.cookie = "" + current + "=" + answer + "; path=/";
                document.cookie = "current=" + e.target.id.substring(2) + "; path=/";
                modal.classList.add('hidden');
                load();
            };
            libtn.innerHTML = Number.parseInt(clt.indexOf(e)) + 1;
            rvelm.append(libtn);
            document.getElementById("ule").append(rvelm);
            let index = Number.parseInt(clt.indexOf(e)) + 1;
            let fi = f(index);
            if (fi !== undefined) {

                if (f(index.toString()) === "")
                    document.getElementById("rv" + index).setAttribute('style', 'background-color: white;');
                else
                    document.getElementById("rv" + index).setAttribute('style', 'background-color: orange;');
            }
        });

        //        let clt = [{"description" : "Here is the problem description #1", "selection" : ["Answer A1", "Answer B1", "Answer C1", "Answer D1"]},
        //        {"description" : "Here is the problem description #2", "selection" : ["Answer A2", "Answer B2", "Answer C2", "Answer D2"]}];

        let load = () => {

            let cur = f("current");

            document.getElementById("i-th").innerHTML = cur + "/" + clt.length;
            document.getElementById("quesnum").innerHTML = "Question " + cur;
            if (cur == 1)
                document.getElementById("btn-back").hidden = true;
            else
                document.getElementById("btn-back").hidden = false;
            if (cur == clt.length)
                document.getElementById("btn-next").hidden = true;
            else
                document.getElementById("btn-next").hidden = false;
            let index = Number.parseInt(cur) - 1;

            let selectNum = clt[index].selection;
            let form = document.querySelector('#sls');
            form.innerHTML = "";
            let numOfanswer = 0;
            selectNum.forEach(e => {
                if (e.coefficent > 0) {
                    numOfanswer = Number.parseInt(numOfanswer) + 1;
                }
                let div = document.createElement('div');
                div.setAttribute("name", "form-control ml-4");
                let input = document.createElement('input');
                input.type = "checkbox";
                input.setAttribute("name", "checkbox");
                input.value = e.charid;
                input.setAttribute("class", "checkbox checkbox-info");
                input.id = e.charid;
                div.append(input);
                let label = document.createElement('label');
                label.htmlFor = e.charid;
                label.setAttribute("name", "slable");
                label.setAttribute("class", "label-text font-mono text-2xl");
                label.innerHTML = e.description;
                div.append(label);
                form.append(div);

            });
            let QuesDes = document.getElementById("desc");
            QuesDes.innerHTML = "";
            let descirption = clt[index].question.description;
            for (var i = 0; i < descirption.length; i++) {
                let Des = document.createElement('p');
                Des.innerHTML = descirption[i];
                QuesDes.append(Des);
            }
            let Des = document.createElement('p');
            Des.innerHTML = '(Choose ' + numOfanswer + (numOfanswer < 2 ? ' answer)' : ' answers)');
            QuesDes.append(Des);

            limitCheckBox();

            let sl = f(cur);


            Array.prototype.slice.call(document.getElementsByName("checkbox")).forEach(x => x.checked = false);
            if (sl === undefined)
                return;
            for (let i = 0; i < sl.length; ++i) {
                document.getElementById(sl[i]).checked = true;
            }

        };

        function limitCheckBox() {
            var checkBox = document.getElementsByName("checkbox");
            var limitation = 0;
            let current = f("current");
            let selection = clt[Number.parseInt(current) - 1].selection;

            for (var i = 0; i < selection.length; i++) {
                if (selection[i].coefficent > 0) {
                    limitation += 1;
                }
            }
            for (var i = 0; i < checkBox.length; i++) {
                checkBox[i].onclick = function () {
                    var selected = 0;
                    for (var i = 0; i < checkBox.length; i++) {
                        if (checkBox[i].checked == true) {
                            selected += 1;
                        }
                    }
                    if (selected > limitation) {
                        this.checked = false;
                    }
                };
            }
        }

        document.getElementById("btn-next").addEventListener("click", () => {
            const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
            let answer = sel.join("");
            let current = f("current");
            if (sel.length !== 0)
                document.getElementById("rv" + current).setAttribute('style', 'background-color: orange;');
            else
                document.getElementById("rv" + current).setAttribute('style', 'background-color: white;');
            document.cookie = "" + current + "=" + answer + "; path=/";
            document.cookie = "current=" + (Number.parseInt(current) + 1) + "; path=/";
            load();
        });

        document.getElementById("btn-back").addEventListener("click", () => {
            const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
            let answer = sel.join("");
            let current = f("current");
            if (sel.length !== 0)
                document.getElementById("rv" + current).setAttribute('style', 'background-color: orange;');
            else
                document.getElementById("rv" + current).setAttribute('style', 'background-color: white;');
            document.cookie = "" + current + "=" + answer + "; path=/";
            document.cookie = "current=" + (Number.parseInt(current) - 1) + "; path=/";
            load();
        });



        document.getElementById("submit-modal-button").addEventListener("click", () => {
            alert("Your submission has been saved. Back to Main Page");
            const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);

            let answer = sel.join("");
            let current = f("current");

            document.cookie = "" + current + "=" + answer + "; path=/";
        });
        if (f("current") === undefined) {
            document.cookie = "current=1; path=/";
            document.cookie = "fintime=<%=request.getSession().getAttribute("fintime")%>; path=/";
        }
        load();
    </script>


</html>
