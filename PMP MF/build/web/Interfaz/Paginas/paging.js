function Pager0(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('pg'+this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg'+this.currentPage);
        newPageAnchor.className = 'pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};

function Pager1(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P1dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P1dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P1pg'+this.currentPage);
        oldPageAnchor.className = 'P1pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P1pg'+this.currentPage);
        newPageAnchor.className = 'P1pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P1pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P1dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P1pg' + page + '" class="P1pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P1pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};

function Pager2(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P6dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P6dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P6pg'+this.currentPage);
        oldPageAnchor.className = 'P6pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P6pg'+this.currentPage);
        newPageAnchor.className = 'P6pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P6pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P6dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P6pg' + page + '" class="P6pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P6pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};
function Pager3(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P3dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P3dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P3pg'+this.currentPage);
        oldPageAnchor.className = 'P3pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P3pg'+this.currentPage);
        newPageAnchor.className = 'P3pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P3pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P3dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P3pg' + page + '" class="P3pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P3pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};
function Pager4(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P4dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P4dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P4pg'+this.currentPage);
        oldPageAnchor.className = 'P4pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P4pg'+this.currentPage);
        newPageAnchor.className = 'P4pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P4pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P4dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P4pg' + page + '" class="P4pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P4pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};
function Pager5(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P5dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P5dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P5pg'+this.currentPage);
        oldPageAnchor.className = 'P5pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P5pg'+this.currentPage);
        newPageAnchor.className = 'P5pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P5pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P5dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P5pg' + page + '" class="P5pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P5pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};
function Pager6(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;


    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);
        this.inited = true;
    },

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    },

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('P6dv'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('P6dv'+i).style.display = 'none';
            }
        }
    },

    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('P6pg'+this.currentPage);
        oldPageAnchor.className = 'P6pg-normal';
        oldPageAnchor.style.color = '#bbb';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('P6pg'+this.currentPage);
        newPageAnchor.className = 'P6pg-selected';
        newPageAnchor.style.color = '#833471';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    },

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    },

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    },

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    },

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    },

    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero" style="color:#000;">Primero |</b></span>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="P6pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Anterior">&#8920;</b></span> | ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="P6dv'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '-<span style="display: inline-table;font-weight: bold;color:#bbb" id="P6pg' + page + '" class="P6pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'">' + page + '</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' | <span onclick="'+pagerName+'.next();" class="P6pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><b style="color:#000;" title="Siguiente">&#8921;</b></span> ';
        pagerHtml += '<span onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="color:#000;">| Ultimo</b><b style="color:#000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</b><b style="color:#833471;">|'+this.pages+'|</b></span>';
        element.innerHTML = pagerHtml;
    };
};