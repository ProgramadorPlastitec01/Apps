(function($) {
    var Pagify = function(container, itemSelector, perPage) {
        this.container = $(container);
        this.items = $(itemSelector, this.container);
        this.perPage = perPage || 10;
        this.currentPage = 0;
        this.totalPages = Math.ceil(this.items.length / this.perPage);
        
        if (this.items.length <= this.perPage) return; // No paginar si los elementos son menores a perPage

        this.createNavigation();
        this.showItems();
    };

    Pagify.prototype.createNavigation = function() {
        this.container.next('.paginationEvent').remove();
        var pagination = $('<div class="paginationEvent"></div>')
            .append('<a class="nav prev disabled" data-next="false"><i class="fas fa-angle-double-left"></i></a>');

        for (var i = 0; i < this.totalPages; i++) {
            pagination.append('<a class="page' + (i === 0 ? ' current' : '') + '" data-page="' + (i + 1) + '">' + (i + 1) + '</a>');
        }

        pagination.append('<a class="nav next" data-next="true"><i class="fas fa-angle-double-right"></i></a>');
        this.container.after(pagination);

        var that = this;
        pagination.on("click", ".nav", function() {
            that.navigate($(this).data("next"));
        });

        pagination.on("click", ".page", function() {
            that.goToPage($(this).data("page"));
        });
    };

    Pagify.prototype.navigate = function(next) {
        if (next) {
            this.currentPage = Math.min(this.currentPage + 1, this.totalPages - 1);
        } else {
            this.currentPage = Math.max(this.currentPage - 1, 0);
        }
        this.updateNavigation();
        this.showItems();
    };

    Pagify.prototype.goToPage = function(page) {
        this.currentPage = page - 1;
        this.updateNavigation();
        this.showItems();
    };

    Pagify.prototype.showItems = function() {
        this.items.hide().slice(this.currentPage * this.perPage, (this.currentPage + 1) * this.perPage).show();
        this.updateNavigation();
    };

    Pagify.prototype.updateNavigation = function() {
        var pagination = this.container.next('.paginationEvent');
        pagination.find(".page").removeClass("current");
        pagination.find('.page[data-page="' + (this.currentPage + 1) + '"]').addClass("current");
        pagination.find(".nav.prev").toggleClass("disabled", this.currentPage === 0);
        pagination.find(".nav.next").toggleClass("disabled", this.currentPage === this.totalPages - 1);
    };

    $.fn.pagify = function(perPage, itemSelector) {
        return this.each(function() {
            new Pagify(this, itemSelector, perPage);
        });
    };

})(jQuery);

// Inicialización para diferentes contenedores
$(".container").pagify(10, ".single-item");
$(".container2").pagify(5, ".single-item2");
$(".container3").pagify(5, ".single-item3");
