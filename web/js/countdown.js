let countDownTime = new Date("May 29, 2023 11:00:00").getTime()
let countDownInterval = setInterval(timeCalculation, 1000)
function timeCalculation() {
    let nowTime = new Date().getTime()
    let gap = Math.ceil((countDownTime - nowTime) / 1000)
    if (gap < 0) {
        console.log("Submit!")
        return
    }
    // console.log(gap)
    let hours = Math.floor(gap/3600)
    gap -= hours * 3600
    let minutes = Math.floor(gap/60)
    gap -= minutes * 60
    let seconds = gap
    console.log(`${pad(hours,2)}:${pad(minutes,2)}:${pad(seconds,2)}`)
}

function pad(num, size) {
    num = num.toString();
    while (num.length < size) num = "0" + num;
    return num;
}

//Countdown every 1000 miliseconds
