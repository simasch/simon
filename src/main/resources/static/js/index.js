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
                var xhr = new XMLHttpRequest();
                xhr.open("GET", 'check/latest', true);
                xhr.setRequestHeader('Accept', 'application/json');
                var vm = this;
                xhr.onload = function () {
                    vm.hosts = JSON.parse(xhr.responseText);
                    vm.lastRefresh = formatTimestamp(new Date());
                    vm.dataReady = true;
                };
                xhr.send();
            },
            switchAutoRefresh: function () {
                this.autoRefresh = !this.autoRefresh;

                if (autorefresh) {
                    this.interval = setInterval(function () {
                        this.fetchData();
                    }, 10000);
                } else {
                    clearInterval(interval);
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
