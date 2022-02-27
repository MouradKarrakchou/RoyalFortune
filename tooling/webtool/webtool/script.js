//transform: rotate(90deg);
jQuery(document).ready(function($) {
    window.speed = 300;
    //$("#boat").animate({ top: y, left: x }, 1200);
    init()
});


function Boat(x, y, orientation) {
    let boatHTML = document.getElementById("boat");
    this.x = x;
    this.y = y;
    this.orientation = orientation;
    this.getX = function() { return translateX(this.x) }
    this.getY = function() { return translateY(this.y) }
    window.sea = document.getElementById('sea');
}

function Checkpoint(x, y, radius) {
    this.x = (x * 0.5) + window.sea.offsetWidth / 2;
    this.y = (y * 0.5) + window.sea.offsetHeight / 2;
    this.radius = radius;
}

function init() {
    window.checkpoints = [];
    window.cameraLock = true;
    addListener();
    window.boat = new Boat(0, 0, 0);
    window.saveMyLife = document.getElementById("saveMyLife");
    window.lastX = translateX(0);
    window.lastY = translateY(0);
    document.getElementById('boat').style.transform = "rotate(-90deg)";
    $("#boat").animate({ top: window.lastY, left: window.lastX }, 1200, function() {
        scrollToTheBoat();
    });

}

function scrollToTheBoat() {
    $('html, body').animate({
        scrollTop: $('#boat').offset().top - window.lastY / 2,
        scrollLeft: $('#boat').offset().left - window.lastX / 2 // Use element id to get element's location.
    }, 1000);
}

function scrollToTheBoatV2() {
    $('html, body').animate({
        scrollTop: window.lastY / 2,
        scrollLeft: window.lastX / 2 // Use element id to get element's location.
    }, window.speed);
}

function move(input) {
    for (let i = 0; i < input.length; i++) {
        storeCurrentPosition();
        updateBoatPosition(input, i);
        drawpath(window.boat.getX(), window.boat.getY());
        drawBoat();
        if (window.cameraLock)
            scrollToTheBoatV2();
    }
}

function storeCurrentPosition() {
    window.lastX = window.boat.getX();
    window.lastY = window.boat.getY();
}

function updateBoatPosition(input, i) {
    let currentPos = input[i].split(';');
    window.boat.x = currentPos[0];
    window.boat.y = currentPos[1];
    window.boat.orientation = currentPos[2];
}

function drawBoat() {
    let newX = window.boat.getX();
    let newY = window.boat.getY();
    let orientation = window.boat.orientation;
    let rotation = (orientation * 180) / Math.PI;
    $('#boat').animate({ borderSpacing: orientation }, {
        step: function(now, fx) {
            console.log("orientation=" + orientation);
            $(this).css({
                '-webkit-transform': 'rotate(' + rotation + 'deg)',
                '-moz-transform': 'rotate(' + rotation + 'deg)',
                '-o-transform': 'rotate(' + rotation + 'deg)',
                '-ms-transform': 'rotate(' + rotation + 'deg)',
                'transform': 'rotate(' + rotation + 'deg)'
            });
        }
    }, 'linear');
    $("#boat").animate({ top: newY - (window.saveMyLife.height / 2), left: newX - (window.saveMyLife.width / 2) }, window.speed);
}

function translateX(x) {
    return (x * 0.5) + (window.sea.offsetWidth / 2);
}

function translateY(y) {
    return (y * 0.5) + (window.sea.offsetHeight / 2);
}

function createCheckpoints(input) {
    let checkpointList = document.getElementById('sea');
    for (let i = 0; i < input.length; i++) {
        window.checkpoints.push(new Checkpoint(input[i].split(';')[0], input[i].split(';')[1], input[i].split(';')[2]));
        let check = "<div id='" + i + "' class='checkpoint'></div>"
        checkpointList.innerHTML += check;
    }
}

function animateCheckpoints() {
    $('.checkpoint').each(function() {
        let id = $(this).attr("id");
        let radius = window.checkpoints[id].radius;
        //$(this).animate({ top: window.checkpoints[id].y, left: window.checkpoints[id].x }, 1000);
        $(this).animate({ top: window.checkpoints[id].y - radius / 2, left: window.checkpoints[id].x - radius / 2 }, 1000);
        $(this).animate({ height: radius, width: radius }, 1000);
    });
}

function getText(textarea) {
    array = textarea.value.replace(/\s+/g, ' ').split(' ').filter((e) => e.length > 0);

    return array
}

function drawpath(newX, newY) {
    let pathX = window.lastX;
    let pathY = window.lastY;
    let draw = document.getElementById("draw");

    let path = "<path class='absolute' d='M " + pathX + " " + pathY + " L " + Math.round(newX) + " " + Math.round(newY) + "' stroke='red' stroke-width='3' fill='none' />";
    let point = "<circle class='absolute' id='pointA' cx='" + pathX + "' cy='" + pathY + "' r='3' />";
    let point2 = "<circle class='absolute' id='pointA' cx='" + newX + "' cy='" + newY + "' r='3' />";

    draw.innerHTML += path;
    draw.innerHTML += point;
    draw.innerHTML += point2;


}

function addListener() {
    document.querySelector('#start').addEventListener('click', function(event) {
        if (!document.getElementById("cameraLock").checked) window.cameraLock = false;
        let input = getText(document.querySelector('#log'))
        let index = input.indexOf("---");
        let checkpoints = input.slice(0, index);
        let coord = input.slice(index + 1);
        let score = document.getElementById("score");

        console.log("---compute score---");
        score.innerText = "Score: " + countRound(coord);
        console.log("---create checkpoint---");
        createCheckpoints(checkpoints);
        console.log("---animate checkpoint---");
        animateCheckpoints();
        console.log("---move boat---");
        move(coord);
    });

    document.querySelector('#reset').addEventListener('click', function(event) {
        location.reload();
    });
}

function countRound(input) {
    return input.length;
}