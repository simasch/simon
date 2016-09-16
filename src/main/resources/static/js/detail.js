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
                var vm = this;
                this.$http.get('measurements' + location.search).then(function (response) {
                    vm.measurements = response.body;
                    vm.dataReady = true;
                }, function (response) {
                    console.log(response.status);
                });
            }
        }
    });
})(window);

