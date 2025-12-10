function Pager1(tableName_1, itemsPerPage_1) {
    this.tableName_1 = tableName_1;
    this.itemsPerPage_1 = itemsPerPage_1;
    this.currentPage_1 = 1;
    this.pages_1 = 0;
    this.inited_1 = false;
    this.divisores_1 = 0;
    this.cantidadDivs_1 = 20;
    this.init_1 = function () {
        var rows_1 = document.getElementById(tableName_1).rows;
        var records_1 = (rows_1.length - 1);
        this.pages_1 = Math.ceil(records_1 / itemsPerPage_1);
        this.divisores_1 = Math.ceil(this.pages_1 / this.cantidadDivs_1);
        this.inited_1 = true;
    }
    this.showRecords_1 = function (from, to) {
        var rows_1 = document.getElementById(tableName_1).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows_1.length; i++) {
            if (i < from || i > to)
                rows_1[i].style.display = 'none';
            else
                rows_1[i].style.display = '';
        }
    }
    this.showDivs_1 = function (pageNumber_1) {
        var divisorMostrar_1 = Math.ceil(pageNumber_1 / this.cantidadDivs_1);
        for (var i = 1; i <= this.divisores_1; i++) {
            if (divisorMostrar_1 == i) {
                document.getElementById('dv1' + i).style.display = '-webkit-inline-box';
            } else {
                document.getElementById('dv1' + i).style.display = 'none';
            }
        }
    }
    this.showPage_1 = function (pageNumber_1) {
        if (!this.inited_1) {
            alert("Error 1 !");
            return;
        }
        var oldPageAnchor_1 = document.getElementById('pg1' + this.currentPage_1);
        oldPageAnchor_1.className = 'pg-normal1';
        oldPageAnchor_1.style.color = '#bbb';
        this.showDivs_1(pageNumber_1);
        this.currentPage_1 = pageNumber_1;
        var newPageAnchor_1 = document.getElementById('pg1' + this.currentPage_1);
        newPageAnchor_1.className = 'pg-selected1';
        newPageAnchor_1.style.color = '#C2185B';
        var from_1 = (pageNumber_1 - 1) * itemsPerPage_1 + 1;
        var to_1 = from_1 + itemsPerPage_1 - 1;
        this.showRecords_1(from_1, to_1);
    }
    this.prev_1 = function () {
        if (this.currentPage_1 > 1)
            this.showPage_1(this.currentPage_1 - 1);
    }
    this.first_1 = function () {
        if (this.currentPage_1 > 1)
            this.showPage_1(1);
    }
    this.next_1 = function () {
        if (this.currentPage_1 < this.pages_1) {
            this.showPage_1(this.currentPage_1 + 1);
        }
    }
    this.last_1 = function () {
        if (this.currentPage_1 < this.pages_1) {
            this.showPage_1(this.pages_1);
        }
    }
    this.showPageNav_1 = function (pagerName_1, positionId_1) {
        if (!this.inited_1) {
            alert("Error 2 !");
            return;
        }
        var element_1 = document.getElementById(positionId_1);
        var pagerHtml_1 = '<span onclick="' + pagerName_1 + '.first();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i title="Primero" style="color:#C2185B;">Primero </i></span>';
        pagerHtml_1 += '<span onclick="' + pagerName_1 + '.prev();" class="pg-normal1" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><i style="color:#C2185B;" title="Anterior">&#8920;</i></span> ';
        var divisor_1 = 1;
        var idDivisor_1 = 1;
        for (var page = 1; page <= this.pages_1; page++) {
            if (idDivisor_1 == 1) {
                pagerHtml_1 += '<div id="dv1' + divisor_1 + '">';
            }
            idDivisor_1 = idDivisor_1 + 1;
            pagerHtml_1 += '.<span style="display: inline-table;font-weight: bold;color:#bbb" id="pg1' + page + '" class="pg-normal1" onclick="' + pagerName_1 + '.showPage_1(' + page + ');"  style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i>' + page + '</i></span> ';
            if (idDivisor_1 == this.cantidadDivs_1 + 1) {
                idDivisor_1 = 1;
                divisor_1 = divisor_1 + 1
                pagerHtml_1 += '</div>';
            } else if (page == this.pages_1) {
                pagerHtml_1 += '</div>';
            }
        }
        pagerHtml_1 += ' <span onclick="' + pagerName_1 + '.next();" class="pg-normal1" style="size:12px;font-family:Wingdings;" onmouseover="this.style.cursor=\'pointer\'"><i style="color:#C2185B;" title="Siguiente">&#8921;</i></span> ';
        pagerHtml_1 += '<span onclick="' + pagerName_1 + '.last();" style="size:12px;" onmouseover="this.style.cursor=\'pointer\'"><i title="Ultimo" style="color:#C2185B;"> Ultimo</i><i style="color:#C2185B;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Paginas</i><i style="color:#C2185B;">(' + this.pages_1 + ')</i></span>';
        element_1.innerHTML = pagerHtml_1;
    }
}