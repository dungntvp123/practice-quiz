var addAnswerButton = document.getElementById('addAnswerButton');
var currentOrderIndex = 0;
var orderAlphabets = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
var submitButton = document.getElementById("grid-submitButton");
var questionDescription = document.getElementById("grid-questionName");
var answerDescription = document.getElementsByName("answerForThisQuestion");
var weightTotal = document.getElementsByName("Weight");
submitButton.addEventListener('click', submitForm);
addAnswerButton.addEventListener('click', addAnswer);


function submitForm(e) {
    
    //No correct answer
    let correctAnswers = "";
    let answers = $("input[name='answerForThisQuestion']");

    let Total = 0;

    let weightTotal = 0;

    for (let i = 0; i < answers.length; i++) {
        let answer = answers[i];
        if (answer.className.includes("highlight")) {
            correctAnswers += orderAlphabets[i];

            Total = Total + parseFloat(answer.nextElementSibling.value);        

            weightTotal = weightTotal + answer.nextElementSibling.value;
            console.log(weightTotal)

        }




    }
   
    console.log(Total);
    
    document.getElementById("correct-answers").value = correctAnswers;
    if (correctAnswers.length === 0) {
        //TODO switch to text warning
        e.preventDefault();
        alert("Please select at least 1 correct answer!");
        return false;
    } else if (correctAnswers.length === answers.length){
        e.preventDefault();
        alert("Please have at least 1 answer being incorrect.");
        return false;
    }
    
    else if(Math.round(Total) != 100){
        e.preventDefault();
        alert("The coeficient must be equal 100");
        return false;
    }

}

function addAnswer() {
    addAnswerOnInit("", "0", false);
}

function addAnswerOnInit(details, weight, highlighted) {
    if (currentOrderIndex >= orderAlphabets.length) {
        alert('Maximum number of answers reached (7)');
        return; // Do not add more inputs if already reached 'G'
    }

    var answerContainer = document.createElement('div');
    answerContainer.className = 'flex';

    var orderInput = document.createElement('input');
    orderInput.className = 'appearance-none block w-1/5 bg-gray-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 mr-2 leading-tight focus:outline-none focus:bg-white';
    orderInput.type = 'text';
    var currentIndex = currentOrderIndex % orderAlphabets.length; // To ensure cycling through 'A' to 'G'
    orderInput.value = orderAlphabets[currentIndex];
    orderInput.readOnly = true;

    var answerInput = document.createElement('input');
    answerInput.className = 'appearance-none block w-4/5 bg-gray-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white';
    answerInput.type = 'text';
    answerInput.placeholder = 'Enter the answer details';
    answerInput.name = 'answerForThisQuestion';
    answerInput.required = true;
    if (highlighted) {
        answerInput.classList.add("highlight");
    }
    answerInput.value = details;

    var deleteButton = document.createElement('button');
    deleteButton.className = 'appearance-none block w-1/5 bg-red-200 text-gray-700 border border-red-500 rounded py-3 px-4 mb-3 ml-2 leading-tight focus:outline-none focus:bg-white';
    deleteButton.innerText = 'Delete';

    deleteButton.addEventListener('click', function () {
        answerContainer.remove();
        currentOrderIndex--;

        // Update the order of the existing answer inputs
        var answerInputs = document.getElementsByName('answerForThisQuestion');
        for (var i = 0; i < answerInputs.length; i++) {
            answerInputs[i].previousElementSibling.value = orderAlphabets[i];
        }
    });
    var weighInput = document.createElement("input");
    weighInput.className = 'appearance-none block w-1/5 bg-white-200 text-gray-700 border border-white-500 rounded py-3 px-4 mb-3 ml-2 leading-tight focus:outline-none focus:bg-white';
    weighInput.type = 'number';
    weighInput.name = 'Weight';
    weighInput.value = weight;
    weighInput.step = "0.01"


    weighInput.max = '100';
    weighInput.min = '0';
    weighInput.placeholder = 'Weight';
    weighInput.readOnly = !highlighted
   

    answerContainer.appendChild(orderInput);
    answerContainer.appendChild(answerInput);
    answerContainer.appendChild(weighInput);
    answerContainer.appendChild(deleteButton);

    var questionNameInput = document.getElementById('grid-questionName');
    questionNameInput.parentNode.insertBefore(answerContainer, questionNameInput.nextSibling);

    currentOrderIndex++;

    // Update the order of the existing answer inputs
    var answerInputs = document.getElementsByName('answerForThisQuestion');
    for (var i = 0; i < answerInputs.length; i++) {
        answerInputs[i].previousElementSibling.value = orderAlphabets[i];
    }
    function CheckWeight(){
        var weightInpurValue = document.querySelectorAll("")
    }

    // Add click event listener to the order input
    orderInput.addEventListener('click', function () {
        var correspondingAnswerInput = this.nextElementSibling;
        var correspondingWeightInput = this.nextElementSibling.nextElementSibling;
        correspondingWeightInput.readOnly = !correspondingWeightInput.readOnly
        if (document.getElementById('grid-typeQuestion').value === '0') {
     
            correspondingAnswerInput.classList.toggle('highlight');

            
            if (!correspondingAnswerInput.classList.contains('highlight')) {
                correspondingWeightInput.value = '0';
                let highlightedInputs = document.getElementsByClassName("highlight");
                let weightDivined = (100 / highlightedInputs.length).toFixed(2);
                for (var i = 0; i < highlightedInputs.length; i++) {
                    highlightedInputs[i].nextElementSibling.value = weightDivined;
                    console.log(weightDivined);
                }
            } else {
                let highlightedInputs = document.getElementsByClassName("highlight");
                let weightDivined = (100 / highlightedInputs.length).toFixed(2);
                for (var i = 0; i < highlightedInputs.length; i++) {
                    highlightedInputs[i].nextElementSibling.value = weightDivined;
                    
                }
            }
        } else {
            if (correspondingAnswerInput.classList.contains('highlight')) {
                correspondingWeightInput.value = '0';
            } else {
                //Disable others
                let highlightedInputs = document.getElementsByClassName("highlight");
                if (highlightedInputs.length === 1) {
                    console.log(highlightedInputs[0]);
                }
                if (highlightedInputs.length !== 0) {
                    //highlightedInputs[0].nextElementSibling.readOnly = true;
                    highlightedInputs[0].nextElementSibling.value = '0';
                    highlightedInputs[0].nextElementSibling.readOnly = true
                    highlightedInputs[0].classList.remove("highlight");
                }
                correspondingWeightInput.value = '100';
            }
            correspondingAnswerInput.classList.toggle('highlight');

        }
    });
    var typeQuestionOption = document.getElementById('grid-typeQuestion');
    typeQuestionOption.addEventListener('change', function () {
        var currentValue = this.value;
        if (currentValue === '1') {
            var highlightedInputs = document.getElementsByClassName('highlight');

            // Remove highlight from previously highlighted inputs
            for (var i = 0; i < highlightedInputs.length; i++) {
                highlightedInputs[i].classList.remove('highlight');
                let elementsByName = document.getElementsByName("Weight");
                for (let i = 0; i < elementsByName.length; i++) {
                    let element = elementsByName[i];
                    element.readOnly = true;
                    element.value = 0;
                }

            }
        }
    });
}

function changeSubject(element){
    console.log(element.value)
    let chapters = $(element).find(":selected").data("chapter")
    let gridChapter = document.getElementById("grid-Chapter")
    gridChapter.max = chapters
    gridChapter.value = ""
    gridChapter. placeholder = `Enter chapter (1-${chapters})`
}
