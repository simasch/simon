(function (exports) {
    exports.app = new Vue({
        el: '#content',
        data: {
            hosts: null,
            dataReady: false,
            interval: null,
            autoRefresh: false,
            lastRefresh: null
        },
        ready: function () {
            this.fetchData();
        },
        methods: {
            fetchData: function () {
                var vm = this;
                this.$http.get('check/latest').then(function (response) {
                    vm.hosts = response.body;
                    vm.lastRefresh = formatTimestamp(new Date());
                    vm.dataReady = true;
                }, function (response) {
                    console.log(response.status);
                });
            },
            switchAutoRefresh: function () {
                this.autoRefresh = !this.autoRefresh;

                if (this.autoRefresh) {
                    var vm = this;
                    this.interval = setInterval(function () {
                        vm.fetchData();
                    }, 10000);
                } else {
                    clearInterval(this.interval);
                }
            }
        }
    });
})(window);

function formatTimestamp(date) {
    return pad(date.getDate()) + '.' + pad((date.getMonth() + 1)) + '.' + date.getFullYear() + ' '
        + pad(date.getHours()) + ':' + pad(date.getMinutes()) + ':' + pad(date.getSeconds());
}

function pad(value) {
    if (value < 10) {
        return '0' + value;
    } else {
        return value;
    }
}
