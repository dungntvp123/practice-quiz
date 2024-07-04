let bankTable;
let solTable;

$(document).ready(function() {

    bankTable = $('#bank-table').DataTable({
        responsive: true
    })
        .columns.adjust()
        .responsive.recalc();
    solTable = $('#sol-table').DataTable({
        responsive: true,
        bPaginate: false,
        searching: false
    })
        .columns.adjust()
        .responsive.recalc();
});

function coefficientCaculate(){
    let totalQues = document.getElementsByName("coefficient");
    for (var i = 0;i < totalQues.length ; i++) {
        totalQues[i].value = 1 / totalQues.length;
    }
    console.log(totalQues.length);
}

function bankToSolData(){
    let button = document.createElement("button");
    button.classList.add("bg-green-500","hover:bg-green-700","text-white","font-bold","py-2","px-4","rounded-full")
    button.onclick = bankToSol;
    button.innerText = "+";
    button.type = 'button';
    let row = document.createElement("td");
    row.append(button);
    return row;
}

function solToBankData(){
    let button = document.createElement("button");
    button.classList.add("bg-red-500","hover:bg-red-700","text-white","font-bold","py-2","px-4","rounded-full");
    button.onclick = solToBank;
    button.innerText = "-";
    button.type = 'button';
    let row = document.createElement("td");
    row.classList.add("grid","grid-cols-2","gap-1");
    //Input
    let input = document.createElement(`input`);
    input.type = "number";
    input.min = "0";
    input.name = "coefficient";
    //input.value = "1";
    input.required = true;
    input.step = "any";
    input.classList.add("gx-4","gy-2");
    row.append(input);
    row.append(button);
   
    return row;
}

function bankToSol(){
    let row = event.currentTarget.parentElement.parentElement;
    //Insert Value
    let value = row.children[0].innerText;
    let input = document.createElement("input");
    input.type = 'hidden';
    input.name = 'questions';
    input.value =  value;
    document.getElementById("data-form").append(
        input
    );
    //Button
    let $row = $(row);
    let addRow = bankTable.row($row);
    //Node
    let node = solTable.row.add(addRow.data()).draw().node();
    node.children[node.children.length-1].replaceWith(solToBankData());
    addRow.remove().draw();
    coefficientCaculate();
}

function solToBank(){
    let row = event.currentTarget.parentElement.parentElement;
    //Remove Value
    let value = row.children[0].innerText;
    $(`input[name='questions'][value='${value}']`).remove();
    
    //Button
    let $row = $(row);
    let addRow = solTable.row($row);
    //Node
    let node = bankTable.row.add(addRow.data()).draw().node();
    node.children[node.children.length-1].replaceWith(bankToSolData());
    //console.log(node);
    addRow.remove().draw();
    coefficientCaculate();
}

function submitForm(){
    let startDateString = String($("#StartDate").val());
    let endDateString = String($("#EndDate").val());
    if (startDateString.length !== 0 && endDateString.length !== 0){
        let startDate = new Date(startDateString);
        let endDate = new Date(endDateString);
        if (endDate.getTime() < startDate.getTime()){
            alert("End Date must be after Start Date!");
            event.preventDefault();
            return false;
        }
    }
    //Check number of questions
}
