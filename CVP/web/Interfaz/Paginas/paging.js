function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    this.divisores = 0;
    this.cantidadDivs = 20;
    this.init = function () {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        this.divisores = Math.ceil(this.pages / this.cantidadDivs);

        this.inited = true;

    }
    this.showRecords = function (from, to) {
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    }
    this.showDivs = function (pageNumber) {
        var divisorMostrar = Math.ceil(pageNumber / this.cantidadDivs);
        for (var i = 1; i <= this.divisores; i++) {
            if (divisorMostrar == i) {
                document.getElementById('dv' + i).style.display = '-webkit-inline-box';
            } else {
                document.getElementById('dv' + i).style.display = 'none';
            }
        }
    }
    this.showPage = function (pageNumber) {
        if (!this.inited) {
            alert("Error 1 !");
            return;
        }
        var oldPageAnchor = document.getElementById('pg' + this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        oldPageAnchor.style.color = '#bbb';
        this.showDivs(pageNumber);
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg' + this.currentPage);
        newPageAnchor.className = 'pg-selected';
        newPageAnchor.style.color = '#007C2A';
        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    }
    this.prev = function () {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }
    this.first = function () {
        if (this.currentPage > 1)
            this.showPage(1);
    }
    this.next = function () {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }
    this.last = function () {
        if (this.currentPage < this.pages) {
            this.showPage(this.pages);
        }
    }
    this.showPageNav = function (pagerName, positionId) {
        if (!this.inited) {
            alert("Error 2 !");
            return;
        }
        var element = document.getElementById(positionId);
        var pagerHtml = '<span onclick="' + pagerName + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i title="Primero" style="color:#007C2A;">Primero </i></span>';
        pagerHtml += '<span onclick="' + pagerName + '.prev();" class="pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><i style="color:#007C2A;" title="Anterior">&#8920;</i></span> ';
        var divisor = 1;
        var idDivisor = 1;
        for (var page = 1; page <= this.pages; page++) {
            if (idDivisor == 1) {
                pagerHtml += '<div id="dv' + divisor + '">';
            }
            idDivisor = idDivisor + 1;
            pagerHtml += '.<span style="display: inline-table;font-weight: bold;color:#bbb" id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i>' + page + '</i></span> ';
            if (idDivisor == this.cantidadDivs + 1) {
                idDivisor = 1;
                divisor = divisor + 1
                pagerHtml += '</div>';
            } else if (page == this.pages) {
                pagerHtml += '</div>';
            }
        }
        pagerHtml += ' <span onclick="' + pagerName + '.next();" class="pg-normal" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><i style="color:#007C2A;" title="Siguiente">&#8921;</i></span> ';
        pagerHtml += '<span onclick="' + pagerName + '.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i title="Ultimo" style="color:#007C2A;"> Ultimo</i><i style="color:#007C2A;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</i><i style="color:#007C2A;">(' + this.pages + ')</i></span>';
        element.innerHTML = pagerHtml;
    }
}