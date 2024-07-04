<%-- 
    Document   : index.jsp
    Created on : Jun 5, 2023, 8:28:33 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entity.Question,entity.Selection,java.util.List,java.util.Map" %>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

    </head>
    <%Map<Question, List<Selection>> getTest = (Map<Question, List<Selection>>) request.getAttribute("test");%>
    <jsp:include page="view/unused/NavBar.jsp"></jsp:include>
    <body>
        
            <div>
                <div>

                    <h3 id="desc">
                        Here is the problem description
                    </h3>
                </div>

                <form action="SampleController" method="post" id ="form">
                    <div id="sls"></div>
                    <div style="border: 1px solid black; height: 30px; width: 70px; background-color: lightcoral" id="btn-back"></div>
                    <div style="border: 1px solid black; height: 30px; width: 70px; background-color: lightblue" id="btn-next"></div>
                    <input type="submit" id="submit" value="submit" name="submit">

                </form>
            </div>
        </body> 
        <script>

            let obj = {};
            let select = [];
            let clt = [];
        <%for (Map.Entry<Question, List<Selection>> entry : getTest.entrySet()) {%>
            obj = {};
            select = [];
            obj.question = <%=entry.getKey()%>;
        <% for (Selection select : entry.getValue()) {%>
            select.push(<%=select.toJSString()%>);
        <%}%>
            obj.selection = select;
            clt.push(obj);
        <%}%>
            console.log(clt);

            //        let clt = [{"description" : "Here is the problem description #1", "selection" : ["Answer A1", "Answer B1", "Answer C1", "Answer D1"]},
            //        {"description" : "Here is the problem description #2", "selection" : ["Answer A2", "Answer B2", "Answer C2", "Answer D2"]}];
            let load = () => {
                console.log(decodeURIComponent(document.cookie));
                let cur = f("current");
                let index = Number.parseInt(cur) - 1;
                let selectNum = clt[index].selection;
                let form = document.querySelector('#sls');
                let inp = Array.prototype.slice.call(document.getElementsByName("checkbox"));
                inp.forEach(e => e.remove());
                let slb = Array.prototype.slice.call(document.getElementsByName("slable"));
                console.log(slb);
                slb.forEach(e => {
                    e.remove();
                });
                let brl = Array.prototype.slice.call(document.getElementsByTagName("br"));
                brl.forEach(e => {
                    e.remove();
                });
                selectNum.forEach(e => {
                    let input = document.createElement('input');
                    input.type = "checkbox";
                    input.name = "checkbox";
                    input.value = e.charid;
                    input.setAttribute("class", ""); //insert boosttrap class Ex: "class1 class2"
                    input.id = e.charid;
                    form.append(input);
                    let label = document.createElement('label');
                    label.htmlFor = e.charid;
                    label.setAttribute("name", "slable");
                    label.innerHTML = e.description;
                    form.append(label);
                    form.append(document.createElement('br'));
                });
                document.getElementById("desc").innerHTML = clt[index].question.description;

                let sl = f(cur);
                Array.prototype.slice.call(document.getElementsByName("checkbox")).forEach(x => x.checked = false);
                if (sl === undefined)
                    return;
                for (let i = 0; i < sl.length; ++i) {
                    document.getElementById(sl[i]).checked = true;
                }
            };

            let f = (name) => {
                if (document.cookie === "")
                    return;
                let ck = decodeURIComponent(document.cookie).split(";");
                return ck.filter(e => e.trim().indexOf(name + "=") === 0).map(x => x.trim().substring((name + "=").length, x.length))[0];
            };

            document.getElementById("btn-next").addEventListener("click", () => {
                const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
                let answer = sel.join("");
                let current = f("current");
                document.cookie = "" + current + "=" + answer + "; path=/";
                document.cookie = "current=" + (Number.parseInt(current) + 1) + "; path=/";
                load();
            });

            document.getElementById("btn-back").addEventListener("click", () => {
                const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
                let answer = sel.join("");
                let current = f("current");
                document.cookie = "" + current + "=" + answer + "; path=/";
                document.cookie = "current=" + (Number.parseInt(current) - 1) + "; path=/";
                load();
            });

            document.getElementById("submit").addEventListener("click", () => {
                const sel = Array.prototype.slice.call(document.getElementsByName("checkbox")).filter(e => e.checked === true).map(x => x.value);
                let answer = sel.join("");
                let current = f("current");
                document.cookie = "" + current + "=" + answer + "; path=/";
            });

            if (f("current") === undefined)
                document.cookie = "current=1; path=/";

            load();
    </script>
</html>
