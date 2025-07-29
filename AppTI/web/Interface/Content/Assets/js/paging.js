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

    }

    this.showRecords = function(from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    }

    this.showDivs = function(pageNumber){
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for( var i = 1 ; i <= this.divisores ; i++ ){
            if( divisorMostrar == i ){
                document.getElementById('dv0'+i).style.display = '-webkit-inline-box';
            }else{
                document.getElementById('dv0'+i).style.display = 'none';
            }
        }
    }


    this.showPage = function(pageNumber) {
        if (! this.inited) {
            alert("Error 1 !");
            return;
        }

        var oldPageAnchor = document.getElementById('pg0'+this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        oldPageAnchor.style.color = '#b1b1b1';

        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg0'+this.currentPage);
        newPageAnchor.className = 'pg-selected';
        newPageAnchor.style.color = '#FF963F';


        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    }

    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }

    this.first = function() {
        if (this.currentPage > 1)
            this.showPage( 1 );
    }

    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }

    this.last = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    }


    this.showPageNav = function(pagerName, positionId) {
        if (! this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);

        var pagerHtml = '<a class="text-info" onclick="' + pagerName + '.first();" style="size:12px; border:collapse;" onmouseover="this.style.cursor=\'pointer\'"><b title="Primero">Primero</b></a>  ';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="text-info" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Anterior"><i class="fas fa-caret-left"></i></b></span> ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++){
            if( idDivisor==1 ){
                pagerHtml += '<div id="dv0'+divisor+'">';
            }
            idDivisor = idDivisor+1;
            pagerHtml += '<p style="color:#b1b1b1;"><span class="pg-normal0" style="display: inline-table;" id="pg0' + page + '" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;">' + page + '</span>-</p> ';
//            pagerHtml += '<span class="pg-normal0" style="display: inline-table;" id="pg0' + page + '" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;"><a class="#" style="text-decoration: underline;">' + page + '</a>&nbsp;</span> ';
            if(idDivisor==this.cantidadDivs+1){
                idDivisor=1;
                divisor=divisor+1
                pagerHtml += '</div>';
            }else if( page == this.pages ){
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' <a onclick="'+pagerName+'.next();" class="" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b style="" title="Siguiente"><i class="fas fa-caret-right"></i></b></a> ';
        pagerHtml += '<a class="text-info"  onclick="'+pagerName+'.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><b title="Ultimo" style="">Ultimo</b></a>';
        element.innerHTML = pagerHtml;
    }
}
