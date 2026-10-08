var picker = new Pikaday(
        {
            field: document.getElementById('datepicker'),
            firstDay: 1,
            minDate: new Date(),
            maxDate: new Date(2050, 12, 31),
            yearRange: [2000, 2050],
            onSelect: function () {
                var date = document.createTextNode(this.getMoment().format('YYYY MMMM Do') + ' ');
            }
        }
);
var picker = new Pikaday(
        {
            field: document.getElementById('datepicker2'),
            firstDay: 1,
            minDate: new Date(),
            maxDate: new Date(),
            yearRange: [2000, 2050],
            onSelect: function () {
                var date = document.createTextNode(this.getMoment().format('YYYY MMMM Do') + ' ');
            }
        }
);
var picker = new Pikaday(
        {
            field: document.getElementById('datepicker3'),
            firstDay: 1,
            minDate: new Date(),
            maxDate: new Date(),
//            minDate: new Date(2050, 12, 31),
//            maxDate: new Date(2050, 12, 31),
            yearRange: [2000, 2050],
            onSelect: function () {
                var date = document.createTextNode(this.getMoment().format('YYYY MMMM Do') + ' ');
            }
        }
);