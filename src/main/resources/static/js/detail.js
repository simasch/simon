(function (exports) {
    exports.app = new Vue({
        el: '#content',
        data: {
            measurements: null,
            dataReady: false
        },
        ready: function () {
            this.fetchData();
        },
        methods: {
            fetchData: function () {
                var xhr = new XMLHttpRequest();
                xhr.open("GET", 'measurements' + location.search, true);
                xhr.setRequestHeader('Accept', 'application/json');
                var vm = this;
                xhr.onload = function () {
                    vm.measurements = JSON.parse(xhr.responseText);
                    vm.dataReady = true;
                };
                xhr.send();
            }
        }
    });
})(window);

