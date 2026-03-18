var input = document.getElementById("myInput");
input.addEventListener("input", myFunction);

function myFunction(e) {
    var filter = e.target.value.toUpperCase();

    var list = document.getElementById("container");
    var divs = list.getElementsByTagName("div");
    for (var i = 0; i < divs.length; i++) {
        var a = divs[i].getElementsByTagName("span")[0];

        if (a) {
            if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                divs[i].style.display = "";
            } else {
                divs[i].style.display = "none";
            }
        }
    }
}
function myFunction2(e) {
    var filter = e.target.value.toUpperCase();

    var list = document.getElementById("container2");
    var divs = list.getElementsByTagName("div");
    for (var i = 0; i < divs.length; i++) {
        var a = divs[i].getElementsByTagName("span")[0];

        if (a) {
            if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                divs[i].style.display = "";
            } else {
                divs[i].style.display = "none";
            }
        }
    }
}

