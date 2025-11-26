const body = document.querySelector('body');

const crearNieve = () => {
    var copo = document.createElement('i');
    copo.className = "copo";
    var x = innerWidth * Math.random();
    var size = (Math.random() * 8) + 2;
    var z = Math.round(Math.random()) * 100;
    var delay = Math.random() * 5;
    var anima = (Math.random() * 5) + 5;
    
    
    copo.style.left = x + 'px';
    copo.style.width = size + 'px';
    copo.style.height = size + 'px';
    copo.style.zindex = z;
    copo.style.animationDelay = delay + 's';
    copo.style.animationDuration = anima + 's';
    
    body.appendChild(copo);
    
    setTimeout(() => {
        copo.remove();
    }, anima * 1000);
    
};

setInterval(crearNieve, 100);