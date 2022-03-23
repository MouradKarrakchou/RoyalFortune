//transform: rotate(90deg);
jQuery(document).ready(function($) {
    window.speed = 300;
    //$("#boat").animate({ top: y, left: x }, 1200);
    init()
});


class Boat {
    constructor(x, y, orientation) {
        let boatHTML = document.getElementById("boat");
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.getX = function() { return translateX(this.x); };
        this.getY = function() { return translateY(this.y); };
        window.sea = document.getElementById('sea');
    }
}

class Checkpoint {
    constructor(x, y, radius) {
        this.position = getCalcPosition(x, y, radius);
        //this.position = new Position(x, y, 0);
        this.radius = (radius * 0.5);
    }
}
class Position {
    constructor(x, y, orientation) {
        this.x = (x * 0.5) + window.sea.offsetWidth / 2;
        this.y = (y * 0.5) + window.sea.offsetHeight / 2;
        this.orientation = orientation;
    }
}

class Reef_Circle {
    constructor(radius, x, y, orientation) {
        this.type = "Reef_Circle"
        this.position = new Position(x, y, orientation);
        this.circle = new Circle(radius);
    }
}

class Reef_Rectangle {
    constructor(width, height, x, y, orientation) {
        this.type = "Reef_Rectangle"
        this.position = new Position(x, y, orientation);
        this.rectangle = new Rectangle(width, height);
    }
}

class Stream {
    constructor(width, height, strength, x, y, orientation) {
        this.type = "Stream"
        this.position = new Position(x, y, orientation);
        this.rectangle = new Rectangle(width, height);
        this.strength = strength;
    }
}

class Rectangle {
    constructor(width, height) {
        this.width = width * 0.5;
        this.height = height * 0.5;
    }
}

class Circle {
    constructor(radius) {
        this.radius = radius * 0.5;
    }
}

function init() {
    window.checkpoints = [];
    window.seaEntities = [];
    window.cameraLock = false;
    addListener();
    window.boat = new Boat(0, 0, 0);
    window.saveMyLife = document.getElementById("saveMyLife");
    window.lastX = translateX(0);
    window.lastY = translateY(0);
    document.getElementById('boat').style.transform = "rotate(-90deg)";
    $("#boat").animate({ top: window.lastY, left: window.lastX }, 1200, function() {
        scrollToTheBoat(window.lastX / 2, window.lastY / 2);
    });

}

function scrollToTheBoat(x, y) {
    window.scroll(x + x / 2, y + y / 2);
}


function move(input) {
    for (let i = 0; i < input.length; i++) {
        storeCurrentPosition();
        updateBoatPosition(input, i);
        drawpath(window.boat.getX(), window.boat.getY());
        drawBoat();
        if (window.cameraLock)
            scrollToTheBoat(window.lastX / 2, window.lastY / 2);
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
        let parameters = input[i].split(';');
        window.checkpoints.push(new Checkpoint(parameters[0], parameters[1], parameters[2]));
        let check = "<div id='" + i + "' class='checkpoint'></div>"
        checkpointList.innerHTML += check;
    }
}

function createSeaEntities(input) {
    let seaEntitiesList = document.getElementById('sea');
    let seaEntitie;
    for (let i = 0; i < input.length; i++) {
        let parameters = input[i].split(';');
        if (parameters[0] == "reef") {
            if (parameters[1] == "rect") {
                window.seaEntities.push(new Reef_Rectangle(parameters[2], parameters[3], parameters[4], parameters[5], parameters[6]));
                seaEntitie = "<div id='seaEnt_" + i + "' class='seaEntitie Reef'></div>"
            } else if (parameters[1] == "circle") {
                window.seaEntities.push(new Reef_Circle(parameters[2], parameters[3], parameters[4], parameters[5]));
                seaEntitie = "<div id='seaEnt_" + i + "' class='seaEntitie Reef Reef_Circle'></div>"
            }
        } else if (parameters[0] == "stream") {
            window.seaEntities.push(new Stream(parameters[1], parameters[2], parameters[3], parameters[4], parameters[5], parameters[6]));
            seaEntitie = "<div id='seaEnt_" + i + "' class='seaEntitie Stream'></div>"
        }
        seaEntitiesList.innerHTML += seaEntitie;
    }
}

function animateCheckpoints() {
    $('.checkpoint').each(function() {
        let id = $(this).attr("id");
        let checkpoint = window.checkpoints[id];
        let radius = checkpoint.radius;
        //$(this).animate({ top: window.checkpoints[id].y, left: window.checkpoints[id].x }, 1000);
        $(this).css({ top: checkpoint.position.y - (radius / 2), left: checkpoint.position.x - (radius / 2) });
        $(this).css({ height: radius, width: radius });
    });
}

function animateSeaEntities() {
    $('.seaEntitie').each(function() {
        let id = $(this).attr("id").slice(7);
        let seaEntite = window.seaEntities[id];
        if (seaEntite.type == "Reef_Rectangle") {
            $(this).css('transform', 'rotate(' + seaEntite.position.orientation + 'rad)');
            $(this).animate({ top: seaEntite.position.y - (seaEntite.rectangle.height / 2), left: seaEntite.position.x - (seaEntite.rectangle.width / 2) }, 1000);
            $(this).animate({ height: seaEntite.rectangle.height, width: seaEntite.rectangle.width }, 1000);
        } else if (seaEntite.type == "Reef_Circle") {
            $(this).animate({ top: seaEntite.position.y - (seaEntite.circle.radius / 2), left: seaEntite.position.x - (seaEntite.circle.radius / 2) }, 1000);
            $(this).animate({ height: seaEntite.circle.radius, width: seaEntite.circle.radius }, 1000);
        } else if (seaEntite.type == "Stream") {
            $(this).css('transform', 'rotate(' + seaEntite.position.orientation + 'rad)');
            $(this).animate({ top: seaEntite.position.y - (seaEntite.rectangle.height / 2), left: seaEntite.position.x - (seaEntite.rectangle.width / 2) }, 1000);
            $(this).animate({ height: seaEntite.rectangle.height, width: seaEntite.rectangle.width }, 1000);
        }
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
        let input = document.querySelector('#log').value
            /*let index = input.indexOf("---");
            let checkpoints = input.slice(0, index);
            let coord = input.slice(index + 1);
            let score = document.getElementById("score");*/
        console.log(input);
        let inputArray = input.split("---");
        let checkpoints = removeEmpty(inputArray[0].split("\n"));
        let seaEntities = removeEmpty(inputArray[1].split("\n"));
        let coord = removeEmpty(inputArray[2].split("\n"));

        /*console.log("checkpoints:\n" + checkpoints);
        console.log("seaEntities:\n" + seaEntities);
        console.log("coord:\n" + coord);*/

        console.log("---compute score---");
        score.innerText = "Number of round: " + countRound(coord);
        console.log("---create checkpoint---");
        createCheckpoints(checkpoints);
        console.log("---animate checkpoint---");
        animateCheckpoints();
        console.log("---create SeaEntities---");
        createSeaEntities(seaEntities);
        console.log("---animate SeaEntities---");
        animateSeaEntities();
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

function removeEmpty(list) {
    let array = [];
    for (let i = 0; i < list.length; i++) {
        if (list[i] != "") array.push(list[i]);
    }
    return array;
}

function getCalcPosition(x, y, radius) {
    let vectorX = /*translateX(0)*/ -parseInt(x);
    let vectorY = /*translateY(0)*/ -parseInt(y);
    let norm = Math.sqrt(vectorX * vectorX + vectorY * vectorY);

    let unitX = vectorX / norm;
    let unitY = vectorY / norm;
    let newX = parseInt(x) + (radius * 0.5) * unitX;
    let newY = parseInt(y) + (radius * 0.5) * unitY;
    let fictiousPosition = new Position(newX, newY, 0);
    return fictiousPosition;
}