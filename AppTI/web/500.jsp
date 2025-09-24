<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <link rel="stylesheet" href="Interface/Content/Assets/modules/fontawesome/css/all.min.css">
        <title>500 | NEXUS</title>
        <style>

            *{box-sizing:border-box;margin:0;padding:0}
            html,body{height:100%}

            body{
                font-family: Inter, "Segoe UI", Roboto, "Helvetica Neue", Arial;
                background: radial-gradient(circle at 20% 20%, rgba(11,0,37,1) 0%, rgba(2,6,16,1) 60%);
                color:#fff;
                min-height:100vh;
                display:flex;
                align-items:center;
                justify-content:center;
                overflow:hidden;
            }

            .wrap{position:relative;width:min(980px,92vw);max-width:980px;padding:40px;display:grid;grid-template-columns: 1fr 420px;gap:28px;align-items:center;}
            @media (max-width:900px){.wrap{grid-template-columns: 1fr; text-align:center}.illustration{order:-1;margin-bottom:6px}}
            .content{padding:20px 10px;}

            .code404{font-weight:800;font-size:clamp(56px,10vw,110px);line-height:0.85;color:#33bf98;position:relative;letter-spacing:2px;display:inline-block;transform-origin:center;margin-bottom:10px;}
            .code404::before,.code404::after{content:attr(data-text);position:absolute;left:0;top:0;width:100%;height:100%;mix-blend-mode:screen;opacity:0.9;pointer-events:none;}
            .code404::before{color:#18efd0;transform:translate(-6px,-3px);clip-path:rect(0,9999px,60px,0);animation:glitchTop 2.6s infinite linear;}
            .code404::after{color:#ff6b9a;transform:translate(6px,3px);clip-path:rect(80px,9999px,140px,0);animation:glitchBottom 3s infinite linear;}

            @keyframes glitchTop{0%{transform:translate(-6px,-3px)}20%{transform:translate(6px,2px)}40%{transform:translate(-4px,-1px)}60%{transform:translate(8px,-2px)}80%{transform:translate(-2px,3px)}100%{transform:translate(-6px,-3px)}}
            @keyframes glitchBottom{0%{transform:translate(6px,3px)}25%{transform:translate(-8px,-2px)}50%{transform:translate(5px,1px)}75%{transform:translate(-3px,4px)}100%{transform:translate(6px,3px)}}

            .subtitle{font-size:clamp(16px,2.2vw,20px);color:#33bf98;font-weight:700;margin:6px 0 14px;text-transform:uppercase;letter-spacing:1.2px;}
            .description{color:#cfd6dd;line-height:1.5;margin-bottom:18px;font-size:clamp(14px,1.7vw,16px);}
            .actions{display:flex;gap:12px;align-items:center;flex-wrap:wrap;}

            .btn{display:inline-flex;align-items:center;gap:10px;background:#33bf98;color:#0b0025;padding:12px 18px;border-radius:10px;font-weight:700;text-decoration:none;box-shadow:0 6px 18px rgba(51,191,152,0.12);transition:transform .14s ease, box-shadow .14s ease;}
            .btn:hover{transform:translateY(-4px);box-shadow:0 18px 40px rgba(51,191,152,0.18)}
            .btn.secondary{background:transparent;color:#cfd6dd;border:1px solid rgba(255,255,255,0.06);font-weight:600;}

            .illustration{display:flex;align-items:center;justify-content:center;gap:12px;padding:18px;}
            .appti{width:280px;max-width:85%;filter:drop-shadow(0 12px 30px rgba(0,0,0,0.6));animation:float 4s ease-in-out infinite;}
            @keyframes float{0%{transform:translateY(0)}50%{transform:translateY(-12px)}100%{transform:translateY(0)}}

            #bg-canvas{position:fixed;inset:0;z-index:-2;pointer-events:none;opacity:0.9;}
            .sr-only{position:absolute;left:-9999px;top:auto;width:1px;height:1px;overflow:hidden;}
        </style>
    </head>
    <body>
        <canvas id="bg-canvas"></canvas>

        <main class="wrap" role="main" aria-labelledby="title500">
            <section class="content" aria-label="Información error">
                <h1 id="title500" class="code404" data-text="500">500</h1>

                <div class="subtitle">Error interno del servidor</div>

                <p class="description">
                    Ha ocurrido un error inesperado en el servidor.<br>
                    Por favor, intenta nuevamente más tarde.
                </p>

                <div class="actions">
                    <a class="btn btn-green" href="Start?opt=1"><i class="fas fa-home"></i> Inicio</a>
                    <span class="">Nuestro equipo técnico ya fue notificado.</span>
                </div>
            </section>

            <aside class="illustration" aria-hidden="false">
                <img src="Interface/Imagen/Logo_app/LogoSideW.fw.png" alt="NEXUS logo" class="appti">
            </aside>
        </main>

        <p class="sr-only">Error 500 - Error interno en NEXUS</p>

        <script>
            (function () {
                const canvas = document.getElementById('bg-canvas'), ctx = canvas.getContext('2d');
                let w, h, particles;
                function resize() {
                    w = canvas.width = innerWidth;
                    h = canvas.height = innerHeight;
                }
                window.addEventListener('resize', resize, {passive: true});
                resize();
                function rand(min, max) {
                    return Math.random() * (max - min) + min
                }
                function init() {
                    particles = [];
                    const count = Math.floor((w * h) / 60000);
                    for (let i = 0; i < count; i++) {
                        particles.push({x: rand(0, w), y: rand(0, h), r: rand(0.8, 2.6), vx: rand(-0.1, 0.1), vy: rand(-0.2, -0.6), alpha: rand(0.15, 0.7)})
                    }
                }
                init();
                function draw() {
                    ctx.clearRect(0, 0, w, h);
                    for (const p of particles) {
                        ctx.beginPath();
                        ctx.fillStyle = `rgba(51,191,152,${p.alpha})`;
                        ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
                        ctx.fill();
                        p.x += p.vx;
                        p.y += p.vy;
                        if (p.y < -20) {
                            p.y = h + 20;
                            p.x = rand(0, w)
                        }
                        if (p.x < -20)
                            p.x = w + 20;
                        if (p.x > w + 20)
                            p.x = -20;
                    }
                    requestAnimationFrame(draw);
                }
                draw();
            })();
        </script>
    </body>
</html>
