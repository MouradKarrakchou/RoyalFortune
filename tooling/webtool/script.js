//transform: rotate(90deg);
jQuery(document).ready(function($) {
    let x = 50;
    let y = 50;
    //$("#boat").animate({ top: y, left: x }, 1200);
    init()
});


function Boat(x, y, orientation) {
    this.x = (x * 0.5) + sea.offsetWidth / 2;
    this.y = (y * 0.5) + sea.offsetHeight / 2;
    this.orientation = orientation;
}

function Checkpoint(x, y) {
    this.x = (x * 0.5) + sea.offsetWidth / 2;
    this.y = (y * 0.5) + sea.offsetHeight / 2;
}

function init() {
    window.checkpoints = [];
    addListener();
    window.boat = new Boat(0, 0, 0);

    //document.getElementById('draw').setAttribute("style", "height:" + window.checkpoint.y * 1.5 + "px!important");
    //document.getElementById('draw').setAttribute("style", "height:" + window.checkpoint.y * 1.5 + "px!important");


    document.getElementById('boat').style.transform = "rotate(-90deg)";
    $("#boat").animate({ top: window.boat.y, left: window.boat.x }, 1200);

}

function move(input) {
    for (let i = 0; i < input.length; i++) {
        let currentPos = input[i].split(';');
        let newX = (currentPos[0] * 0.5) + sea.offsetWidth / 2;
        let newY = (currentPos[1] * 0.5) + sea.offsetHeight / 2;
        let orientation = currentPos[2];
        drawpath(newX, newY);
        window.boat.x = newX;
        window.boat.y = newY;
        window.boat.orientation = orientation;


        $('#boat').animate({ borderSpacing: orientation }, {
            step: function(now, fx) {
                console.log("orientation=" + orientation);
                let rotation = (orientation * 180) / Math.PI;
                $(this).css({
                    '-webkit-transform': 'rotate(' + rotation + 'deg)',
                    '-moz-transform': 'rotate(' + rotation + 'deg)',
                    '-o-transform': 'rotate(' + rotation + 'deg)',
                    '-ms-transform': 'rotate(' + rotation + 'deg)',
                    'transform': 'rotate(' + rotation + 'deg)'
                });
            }
        }, 'linear');
        $("#boat").animate({ top: newY, left: newX }, 500);
    }
}

function createCheckpoints(input) {
    let checkpointList = document.getElementById('checkpointList');
    //checkpointList.style.height = "100px"
    //checkpointList.style.backgroundColor = "green"
    for (let i = 0; i < input.length; i++) {
        window.checkpoints.push(new Checkpoint(input[i].split(';')[0], input[i].split(';')[1]));
        let check = "<div id='" + i + "' class='checkpoint'></div>"
        checkpointList.innerHTML += check;
        /*$("#checkpoint" + i).animate({ top: window.checkpoints[i].y, left: window.checkpoints[i].x }, 1200)
            .queue(function() {
                document.getElementById("res").innerHTML += "<p>Queued Calls of checkpoint" + i + ":" + $(this).queue("fx").length + "</p>";
            });
            */
    }
}

function animateCheckpoints() {
    $('.checkpoint').each(function() {
        let id = $(this).attr("id");
        $(this).animate({ top: window.checkpoints[id].y, left: window.checkpoints[id].x }, 1000);
    });
}

function getText(textarea) {
    array = textarea.value.replace(/\s+/g, ' ').split(' ').filter((e) => e.length > 0);

    return array
}

function drawpath(newX, newY) {
    let pathX = Math.round(window.boat.x);
    let pathY = Math.round(window.boat.y);
    let draw = document.getElementById("draw");

    let path = "<path d='M " + pathX + " " + pathY + " L " + Math.round(newX) + " " + Math.round(newY) + "' stroke='red' stroke-width='3' fill='none' />";
    let point = "<circle id='pointA' cx='" + pathX + "' cy='" + pathY + "' r='3' />";
    let point2 = "<circle id='pointA' cx='" + newX + "' cy='" + newY + "' r='3' />";

    draw.innerHTML += path;
    draw.innerHTML += point;
    draw.innerHTML += point2;


}

function addListener() {
    document.querySelector('#start').addEventListener('click', function(event) {
        let input = getText(document.querySelector('#log'))
        let index = input.indexOf("---");
        let checkpoints = input.slice(0, index);
        let coord = input.slice(index + 1);
        createCheckpoints(checkpoints);
        animateCheckpoints();
        move(coord);
    });

    document.querySelector('#reset').addEventListener('click', function(event) {
        location.reload();
    });
}